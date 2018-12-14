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
import com.banco.model.Pessoa;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author NOTEDESENVSP1
 */
public abstract class ControllerBancoImpl extends SessionGenerator implements ControllerDAO<BancoBrasil>, Serializable {

    @Override
    public void adicionarOuEditar(BancoBrasil pessoa) {

        try {

            Session session = getSession();
            session.beginTransaction();
            session.saveOrUpdate(pessoa);
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

            Session session = getSession();
            session.beginTransaction();
            BancoBrasil pessoa = (BancoBrasil) session.get(BancoBrasil.class, id);
            session.delete(pessoa);
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

            Session session = getSession();
            session.beginTransaction();
            List<BancoBrasil> list = session.createCriteria(BancoBrasil.class).list();
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

        List<BancoBrasil> list = null;
        Session ses = getSession();
        ses.beginTransaction();

        try {

            if (conta != null) {
                list = ses.createCriteria(BancoBrasil.class).add(Restrictions.like("conta", conta)).list();
            }
            ses.getTransaction().commit();

            return list;

        } catch (NullPointerException | HibernateException e) {
            System.out.println(e);
            list = ses.createCriteria(BancoBrasil.class).list();
            ses.getTransaction().commit();
            return list;
        } finally {
            closeSession();
        }

    }
    
    public BancoBrasil procurarId(Integer id) {
        
        try {

            BancoBrasil banco;
            Session ses = getSession();
            ses.beginTransaction();
            banco = (BancoBrasil) ses.createCriteria(BancoBrasil.class).add(Restrictions.eq("id", id)).uniqueResult();
            ses.getTransaction().commit();

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

            List<BancoBrasil> list;
            Session ses = getSession();
            ses.beginTransaction();
            list = ses.createCriteria(BancoBrasil.class).add(Restrictions.eq("cidade", cidades)).list();
            ses.getTransaction().commit();

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

            double saldo;
            Session session = getSession();
            session.beginTransaction();
            BancoBrasil transferencia = (BancoBrasil) session.createCriteria(BancoBrasil.class)
                    .add(Restrictions.eq("agencia", agencia)).add(Restrictions.eq("conta", conta)).uniqueResult();

            if (transferencia.getConta() == conta) {

                if (acao) {

                    saldo = pessoa.getContaL().get(contaUser).getValorCorrente() - valor;
                    if (saldo >= 0 && valor > 0) {
                        pessoa.getContaL().get(contaUser).setValorCorrente(saldo);
                        transferencia.setValorCorrente(transferencia.getValorCorrente() + valor);
                    } else {
                        return "Não é possivel transferir esse valor";
                    }

                } else {

                    saldo = pessoa.getContaL().get(contaUser).getValorPoupanca() - valor;
                    if (saldo >= 0 && valor > 0) {
                        pessoa.getContaL().get(contaUser).setValorPoupanca(saldo);
                        transferencia.setValorPoupanca(transferencia.getValorPoupanca() + valor);
                    } else {
                        return "Não é possivel transferir esse valor";
                    }
                }

            }

            session.update(pessoa);
            session.update(transferencia);
            session.getTransaction().commit();
            return "Valor transferido";

        } catch (NullPointerException | HibernateException e) {
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

            double saldo;

            if (!acao) {
                saldo = pessoa.getContaL().get(conta).getValorPoupanca() - valor;
                if (saldo >= 0 && valor > 0) {
                    pessoa.getContaL().get(conta).setValorPoupanca(saldo);
                    valor = pessoa.getContaL().get(conta).getValorCorrente() + valor;
                    pessoa.getContaL().get(conta).setValorCorrente(valor);
                } else {
                    return "Não é possivel transferir esse valor";
                }

            } else {
                saldo = pessoa.getContaL().get(conta).getValorCorrente() - valor;
                if (saldo >= 0 && valor > 0) {
                    pessoa.getContaL().get(conta).setValorCorrente(saldo);
                    valor = pessoa.getContaL().get(conta).getValorPoupanca() + valor;
                    pessoa.getContaL().get(conta).setValorPoupanca(valor);
                } else {
                    return "Não é possivel transferir esse valor";
                }
            }

            Session session = getSession();
            session.beginTransaction();
            session.update(pessoa);
            session.getTransaction().commit();

            return "Valor transferido";

        } catch (Exception e) {
            System.out.println(e);
            return "---------------------Falhou: " + e.toString();
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
            } else {
                valor = pessoa.getContaL().get(conta).getValorPoupanca() + valor;
                pessoa.getContaL().get(conta).setValorPoupanca(valor);
            }

            Session session = getSession();
            session.beginTransaction();
            session.update(pessoa);
            session.getTransaction().commit();
            return "Deposito realizado";

        } catch (HibernateException e) {
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

            double saldo;

            if (acao) {
                saldo = pessoa.getContaL().get(conta).getValorCorrente() - valor;
                if (saldo >= 0 && valor > 0) {
                    pessoa.getContaL().get(conta).setValorCorrente(saldo);
                } else {
                    return "Não é possivel saquar esse valor";
                }

            } else {
                saldo = pessoa.getContaL().get(conta).getValorPoupanca() - valor;
                if (saldo >= 0 && valor > 0) {
                    pessoa.getContaL().get(conta).setValorPoupanca(saldo);
                } else {
                    return "Não é possivel saquar esse valor";
                }
            }

            Session session = getSession();
            session.beginTransaction();
            session.update(pessoa);
            session.getTransaction().commit();
            return "Valor saquado";

        } catch (HibernateException e) {
            System.out.println(e);
            return e.toString();
        } finally {
            closeSession();
        }

    }

}
