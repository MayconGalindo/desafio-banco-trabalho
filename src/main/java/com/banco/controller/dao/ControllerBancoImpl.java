/*
 * Copyright 2018 NOTEDESENVSP1.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.banco.controller.dao;

import com.banco.model.BancoBrasil;
import com.banco.model.Contato;
import com.banco.model.Pessoa;
import com.banco.model.Transferencia;
import java.io.Serializable;
import java.text.ParseException;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author NOTEDESENVSP1
 */
public abstract class ControllerBancoImpl extends SessionGenerator implements ControllerDAO<BancoBrasil>, Serializable {

    Transferencia transferencia;
    Session session = getSession();
    List<BancoBrasil> list;
    double saldo;

    @Override
    public void adicionarOuEditar(BancoBrasil banco) {

        try {
            session.beginTransaction();
            session.saveOrUpdate(banco);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            closeSession();
        }

    }

    @Override
    public void excluir(int id) {

        try {
            session.beginTransaction();
            BancoBrasil banco = (BancoBrasil) session.get(BancoBrasil.class, id);
            session.delete(banco);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            closeSession();
        }

    }

    public void ativarOuDesativar(int id) {

        try {
            session.beginTransaction();
            BancoBrasil banco = (BancoBrasil) session.get(BancoBrasil.class, id);
            if (banco.isEstadoConta()) {
                banco.setEstadoConta(false);
            } else {
                banco.setEstadoConta(true);
            }
            session.update(banco);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            closeSession();
        }
    }

    @Override
    public List<BancoBrasil> listar() {

        try {

            session.beginTransaction();
            list = session.createCriteria(BancoBrasil.class).list();
            session.getTransaction().commit();

            return list;

        } catch (HibernateException e) {

            System.out.println(e);
            return null;

        } finally {
            closeSession();
        }

    }

    @Override
    public List<BancoBrasil> procurar(Integer conta) {

        try {

            session.beginTransaction();
            if (conta == null || conta == 0) {
                list = session.createCriteria(BancoBrasil.class).list();
            } else {
                list = session.createCriteria(BancoBrasil.class).add(Restrictions.like("conta", conta)).list();
            }
            session.getTransaction().commit();

            return list;

        } catch (NullPointerException | HibernateException e) {
            System.out.println(e);
            return null;
        } finally {
            closeSession();
        }

    }

    public BancoBrasil procurarId(Integer id) {

        try {

            BancoBrasil banco;
            session.beginTransaction();
            banco = (BancoBrasil) session.createCriteria(BancoBrasil.class).add(Restrictions.eq("id", id)).uniqueResult();
            session.getTransaction().commit();

            return banco;

        } catch (HibernateException e) {
            System.out.println(e);
            return null;
        } finally {
            closeSession();
        }

    }

    @Override
    public List<BancoBrasil> filtrar(String cidades) {

        try {
            session.beginTransaction();
            list = session.createCriteria(BancoBrasil.class).add(Restrictions.eq("cidade", cidades)).list();
            session.getTransaction().commit();
            return list;
        } catch (HibernateException e) {
            System.out.println(e);
            return null;
        } finally {
            closeSession();
        }

    }

    /**
     *
     * @param pessoa
     * @param acao
     * @param contaUser
     * @param agencia
     * @param conta
     * @param valor
     * @return
     */
    public String transferirContaDiferente(Pessoa pessoa, boolean acao, int contaUser, int agencia, int conta, double valor) {

        try {

            Contato contato;
            session.beginTransaction();
            BancoBrasil contaAlvo = (BancoBrasil) session.createCriteria(BancoBrasil.class)
                    .add(Restrictions.eq("agencia", agencia)).add(Restrictions.eq("conta", conta)).uniqueResult();

            if (contaAlvo.getConta() == conta) {

                if (acao) {

                    saldo = pessoa.getContaL().get(contaUser).getValorCorrente() - valor;
                    if (saldo >= 0 && valor > 0) {
                        pessoa.getContaL().get(contaUser).setValorCorrente(saldo);
                        contaAlvo.setValorCorrente(contaAlvo.getValorCorrente() + valor);
                        transferencia = new Transferencia(pessoa.getCpf(), "Transferecia Conta Diferente(Corrente)", valor, contaAlvo.getPessoa().getCpf());
                        if (contatoExistente(pessoa, agencia, conta)) {
                            contato = new Contato(agencia, conta, pessoa);
                            pessoa.getContatoL().add(contato);
                            session.save(contato);
                        }
                    } else {
                        return "Não é possivel transferir esse valor";
                    }

                } else {

                    saldo = pessoa.getContaL().get(contaUser).getValorPoupanca() - valor;
                    if (saldo >= 0 && valor > 0) {
                        pessoa.getContaL().get(contaUser).setValorPoupanca(saldo);
                        contaAlvo.setValorPoupanca(contaAlvo.getValorPoupanca() + valor);
                        transferencia = new Transferencia(pessoa.getCpf(), "Transferecia Conta Diferente(Poupança)", valor, contaAlvo.getPessoa().getCpf());
                        if (contatoExistente(pessoa, agencia, conta)) {
                            contato = new Contato(agencia, conta, pessoa);
                            pessoa.getContatoL().add(contato);
                            session.save(contato);
                        }
                    } else {
                        return "Não é possivel transferir esse valor";
                    }
                }
            }

            session.update(pessoa);
            session.update(contaAlvo);
            session.save(transferencia);
            session.getTransaction().commit();
            return "Valor transferido";

        } catch (NullPointerException | HibernateException | ParseException e) {
            System.out.println(e);
            return "Conta errada ou inexistente";
        } finally {
            closeSession();
        }
    }

    /**
     *
     * @param valor
     * @param pessoa
     * @param acao false: transfere para corrente
     * @param conta
     * @return
     */
    public String transferirMesmaConta(double valor, Pessoa pessoa, boolean acao, int conta) {

        try {

            if (!acao) {
                saldo = pessoa.getContaL().get(conta).getValorPoupanca() - valor;
                if (saldo >= 0 && valor > 0) {
                    pessoa.getContaL().get(conta).setValorPoupanca(saldo);
                    valor = pessoa.getContaL().get(conta).getValorCorrente() + valor;
                    pessoa.getContaL().get(conta).setValorCorrente(valor);
                    transferencia = new Transferencia(pessoa.getCpf(), "Transferecia Mesma Conta(Poupança para Corrente)", valor, pessoa.getCpf());
                } else {
                    return "Não é possivel transferir esse valor";
                }

            } else {
                saldo = pessoa.getContaL().get(conta).getValorCorrente() - valor;
                if (saldo >= 0 && valor > 0) {
                    pessoa.getContaL().get(conta).setValorCorrente(saldo);
                    valor = pessoa.getContaL().get(conta).getValorPoupanca() + valor;
                    pessoa.getContaL().get(conta).setValorPoupanca(valor);
                    transferencia = new Transferencia(pessoa.getCpf(), "Transferecia Mesma Conta(Corrente para Poupança)", valor, pessoa.getCpf());
                } else {
                    return "Não é possivel transferir esse valor";
                }
            }

            session.beginTransaction();
            session.update(pessoa);
            session.save(transferencia);
            session.getTransaction().commit();

            return "Valor transferido";

        } catch (ParseException e) {
            System.out.println(e);
            return e.toString();
        } finally {
            closeSession();
        }

    }

    /**
     *
     * @param valor
     * @param pessoa
     * @param acao
     * @param conta
     * @return
     */
    public String depositar(double valor, Pessoa pessoa, boolean acao, int conta) {

        try {

            if (acao) {
                valor = pessoa.getContaL().get(conta).getValorCorrente() + valor;
                pessoa.getContaL().get(conta).setValorCorrente(valor);
                transferencia = new Transferencia(pessoa.getCpf(), "Desposito(Corrente)", valor, pessoa.getCpf());
            } else {
                valor = pessoa.getContaL().get(conta).getValorPoupanca() + valor;
                pessoa.getContaL().get(conta).setValorPoupanca(valor);
                transferencia = new Transferencia(pessoa.getCpf(), "Desposito(Poupança)", valor, pessoa.getCpf());
            }

            session.beginTransaction();
            session.update(pessoa);
            session.save(transferencia);
            session.getTransaction().commit();
            return "Deposito realizado";

        } catch (HibernateException | ParseException e) {
            System.out.println(e);
            return e.toString();
        } finally {
            closeSession();
        }

    }

    /**
     *
     * @param valor
     * @param pessoa
     * @param acao
     * @param conta
     * @return
     */
    public String saquar(double valor, Pessoa pessoa, boolean acao, int conta) {

        try {

            if (acao) {
                saldo = pessoa.getContaL().get(conta).getValorCorrente() - valor;
                if (saldo >= 0 && valor > 0) {
                    pessoa.getContaL().get(conta).setValorCorrente(saldo);
                    transferencia = new Transferencia(pessoa.getCpf(), "Saque(Corrente)", valor, pessoa.getCpf());
                } else {
                    return "Não é possivel saquar esse valor";
                }

            } else {
                saldo = pessoa.getContaL().get(conta).getValorPoupanca() - valor;
                if (saldo >= 0 && valor > 0) {
                    pessoa.getContaL().get(conta).setValorPoupanca(saldo);
                    transferencia = new Transferencia(pessoa.getCpf(), "Saque(Poupanca)", valor, pessoa.getCpf());
                } else {
                    return "Não é possivel saquar esse valor";
                }
            }

            session.beginTransaction();
            session.update(pessoa);
            session.save(transferencia);
            session.getTransaction().commit();
            return "Valor saquado";

        } catch (HibernateException | ParseException e) {
            System.out.println(e);
            return e.toString();
        } finally {
            closeSession();
        }

    }
    
    public List<BancoBrasil> contasPessoa(int id){
        
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM BancoBrasil where pessoa_id = :id");
            query.setParameter("id", id);
            list = query.list();
            session.getTransaction().commit();
            return list;
        } catch (HibernateException e) {
            System.out.println(e);
            return null;
        } finally {
            closeSession();
        }
        
    }

    private boolean contatoExistente(Pessoa pessoa, int agencia, int conta) throws IndexOutOfBoundsException{

        for (int i = 0; i < pessoa.getContatoL().size(); i++) {
            if (pessoa.getContatoL().get(i).getAgencia() == agencia && pessoa.getContatoL().get(i).getConta() == conta) {
                return false;
            }
        }
        return true;
    }

}
