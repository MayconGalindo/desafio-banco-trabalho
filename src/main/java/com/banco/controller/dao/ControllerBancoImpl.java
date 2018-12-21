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
    BancoBrasil banco;
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
            banco = (BancoBrasil) session.get(BancoBrasil.class, id);
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
            banco = (BancoBrasil) session.get(BancoBrasil.class, id);
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

        session.beginTransaction();

        try {
            if (conta == null || conta == 0) {
                list = session.createCriteria(BancoBrasil.class).list();
            } else {
                list = session.createCriteria(BancoBrasil.class).add(Restrictions.like("conta", conta)).list();
            }
            return list;
        } catch (NullPointerException | HibernateException e) {
            System.out.println(e);
            return session.createCriteria(BancoBrasil.class).list();
        } finally {
            closeSession();
        }

    }

    public BancoBrasil procurarId(Integer id) {

        try {
            session.beginTransaction();
            banco = (BancoBrasil) session.get(BancoBrasil.class, id);
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
    public String transferirContaDiferente(Pessoa pessoa, boolean acao, int contaUser, int agencia, int conta, final double valor) {

        try {

            Contato contato;
            session.beginTransaction();
            banco = (BancoBrasil) session.get(BancoBrasil.class, contaUser);
            BancoBrasil contaAlvo = (BancoBrasil) session.createCriteria(BancoBrasil.class)
                    .add(Restrictions.eq("agencia", agencia)).add(Restrictions.eq("conta", conta)).uniqueResult();

            if (contaAlvo.isEstadoConta()) {

                if (contaAlvo.getConta() == conta) {

                    if (acao) {

                        saldo = banco.getValorCorrente() - valor;
                        if (saldo >= 0 && valor > 0) {
                            banco.setValorCorrente(saldo);
                            contaAlvo.setValorCorrente(contaAlvo.getValorCorrente() + valor);
                            transferencia = new Transferencia(pessoa.getCpf(), "Transferecia Conta Diferente(Corrente para Corrente)", valor, contaAlvo.getPessoa().getCpf());
                        } else {
                            return "Não é possivel transferir esse valor";
                        }

                    } else {

                        saldo = banco.getValorPoupanca() - valor;
                        if (saldo >= 0 && valor > 0) {
                            banco.setValorPoupanca(saldo);
                            contaAlvo.setValorPoupanca(contaAlvo.getValorPoupanca() + valor);
                            transferencia = new Transferencia(pessoa.getCpf(), "Transferecia Conta Diferente(Poupança para Poupança)", valor, contaAlvo.getPessoa().getCpf());
                        } else {
                            return "Não é possivel transferir esse valor";
                        }
                    }
                }

                session.update(banco);
                session.update(contaAlvo);
                session.save(transferencia);
                session.getTransaction().commit();
                try {
                    session = getSession();
                    session.beginTransaction();
                    contato = new Contato(agencia, conta, pessoa);
                    session.save(contato);
                    session.getTransaction().commit();
                } catch (HibernateException e) {
                    System.out.println(e);
                    System.out.println("Conta ja adicionada aos contatos");
                }

                return "Valor transferido";

            } else {
                return "Conta escolhida esta desativada";
            }

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
    public String transferirMesmaConta(final double valor, Pessoa pessoa, boolean acao, int conta) {

        try {

            session.beginTransaction();
            banco = (BancoBrasil) session.get(BancoBrasil.class, conta);

            if (!acao) {
                saldo = banco.getValorPoupanca() - valor;
                if (saldo >= 0 && valor > 0) {
                    banco.setValorPoupanca(saldo);
                    saldo = banco.getValorCorrente() + valor;
                    banco.setValorCorrente(saldo);
                    transferencia = new Transferencia(pessoa.getCpf(), "Transferecia Mesma Conta(Poupança para Corrente)", valor, pessoa.getCpf());
                } else {
                    return "Não é possivel transferir esse valor";
                }

            } else {
                saldo = banco.getValorCorrente() - valor;
                if (saldo >= 0 && valor > 0) {
                    banco.setValorCorrente(saldo);
                    saldo = banco.getValorPoupanca() + valor;
                    banco.setValorPoupanca(saldo);
                    transferencia = new Transferencia(pessoa.getCpf(), "Transferecia Mesma Conta(Corrente para Poupança)", valor, pessoa.getCpf());
                } else {
                    return "Não é possivel transferir esse valor";
                }
            }

            session.update(banco);
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
    public String depositar(final double valor, Pessoa pessoa, boolean acao, int conta) {

        try {

            session.beginTransaction();
            banco = (BancoBrasil) session.get(BancoBrasil.class, conta);

            if (acao) {
                saldo = banco.getValorCorrente() + valor;
                banco.setValorCorrente(saldo);
                transferencia = new Transferencia(pessoa.getCpf(), "Desposito(Corrente)", valor, pessoa.getCpf());
            } else {
                saldo = banco.getValorPoupanca() + valor;
                banco.setValorPoupanca(saldo);
                transferencia = new Transferencia(pessoa.getCpf(), "Desposito(Poupança)", valor, pessoa.getCpf());
            }

            session.update(banco);
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
    public String saquar(final double valor, Pessoa pessoa, boolean acao, int conta) {

        try {

            session.beginTransaction();
            banco = (BancoBrasil) session.get(BancoBrasil.class, conta);

            if (acao) {
                saldo = banco.getValorCorrente() - valor;
                if (saldo >= 0 && valor > 0) {
                    banco.setValorCorrente(saldo);
                    transferencia = new Transferencia(pessoa.getCpf(), "Saque(Corrente)", valor, pessoa.getCpf());
                } else {
                    return "Não é possivel saquar esse valor";
                }

            } else {
                saldo = banco.getValorPoupanca() - valor;
                if (saldo >= 0 && valor > 0) {
                    banco.setValorPoupanca(saldo);
                    transferencia = new Transferencia(pessoa.getCpf(), "Saque(Poupanca)", valor, pessoa.getCpf());
                } else {
                    return "Não é possivel saquar esse valor";
                }
            }

            session.update(banco);
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

    public List<BancoBrasil> contasPessoa(int id) {

        try {

            session.beginTransaction();
            Query query = session.createQuery("FROM BancoBrasil where pessoa_id = :id").setParameter("id", id);
            list = query.list();

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isEstadoConta() == false) {
                    list.remove(i);
                }
            }

            return list;

        } catch (HibernateException e) {
            System.out.println(e);
            return null;
        } finally {
            closeSession();
        }

    }

}