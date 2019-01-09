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

import com.banco.model.Contato;
import com.banco.model.Pessoa;
import com.banco.model.Transferencia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author NOTEDESENVSP1
 */
public abstract class ControllerPessoaImpl extends SessionGenerator implements ControllerDAO<Pessoa>, Serializable {

    List<Pessoa> list;
    List<Transferencia> transf;
    Session session = getSession();
    Pessoa pessoa;

    @Override
    public Boolean adicionarOuEditar(Pessoa pessoa) {

        try {
            session.beginTransaction();
            session.saveOrUpdate(pessoa);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            System.out.println(e);
            return false;
        } finally {
            closeSession();
        }

    }

    public int adicionar(Pessoa pessoa) {

        try {
            session.beginTransaction();
            session.save(pessoa);
            session.getTransaction().commit();
            return 0;
        } catch (HibernateException e) {
            return 2;
        } finally {
            closeSession();
        }

    }
    
    @Override
    public void excluir(int id) {

        try {
            session.beginTransaction();
            pessoa = (Pessoa) session.get(Pessoa.class, id);
            session.delete(pessoa);
            session.getTransaction().commit();
        } catch (HibernateException e) {
        } finally {
            closeSession();
        }

    }

    @Override
    public List<Pessoa> listar() {

        try {
            session.beginTransaction();
            return session.createCriteria(Pessoa.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        } catch (HibernateException e) {
            return null;
        } finally {
            closeSession();
        }

    }

    public List<String> listarCpf() {

        try {
            session.beginTransaction();
            list = session.createCriteria(Pessoa.class).list();
            List<String> cpf = new ArrayList();
            for (Pessoa p : list) {
                cpf.add(p.getCpf());
            }
            return cpf;
        } catch (HibernateException e) {
            return null;
        } finally {
            closeSession();
        }

    }

    public List<String> filtrarCpf(String cpf, boolean RemeOuDest) {

        try {
            List<String> lista = new ArrayList();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Transferencia.class);
            if (RemeOuDest) {
                transf = criteria.add(Restrictions.eq("cpfRemetente", cpf)).list();
                for (Transferencia t : transf) {
                    if (!lista.contains(t.getCpfDestinatario())) {
                        lista.add(t.getCpfDestinatario());
                    }
                }
            } else {
                transf = criteria.add(Restrictions.eq("cpfDestinatario", cpf)).list();
                for (Transferencia t : transf) {
                    if (!lista.contains(t.getCpfRemetente())) {
                        lista.add(t.getCpfRemetente());
                    }
                }
            }

            return lista;
        } catch (HibernateException e) {
            return null;
        } finally {
            closeSession();
        }

    }

    @Override
    public List<Pessoa> procurar(String cpf) {

        session.beginTransaction();
        try {
            if (cpf != null) {
                list = session.createCriteria(Pessoa.class).add(Restrictions.like("cpf", cpf, MatchMode.ANYWHERE)).list();
            } else {
                list = session.createCriteria(Pessoa.class).list();
            }
            return list;
        } catch (NullPointerException | HibernateException e) {
            return session.createCriteria(Pessoa.class).list();
        } finally {
            closeSession();
        }

    }

    public Pessoa procurarId(Integer id) {

        try {
            session.beginTransaction();
            pessoa = (Pessoa) session.get(Pessoa.class, id);
            return pessoa;
        } catch (HibernateException e) {
            return null;
        } finally {
            closeSession();
        }

    }

    @Override
    public List<Pessoa> filtrar(String filtros) {

        try {
            session.beginTransaction();
            list = session.createCriteria(Pessoa.class).add(Restrictions.eq("cidade", filtros)).list();
            return list;
        } catch (HibernateException e) {
            return null;
        } finally {
            closeSession();
        }

    }

    public boolean validarLogin(String cpf, String senha) {

        try {
            session.beginTransaction();
            pessoa = (Pessoa) session.createCriteria(Pessoa.class).add(Restrictions.eq("cpf", cpf)).add(Restrictions.eq("senha", senha)).uniqueResult();
            return senha.equals(pessoa.getSenha());
        } catch (NullPointerException | HibernateException e) {
            return false;
        } finally {
            closeSession();
        }

    }

    public Pessoa logar(String cpf, String senha) {

        try {
            session.beginTransaction();
            pessoa = (Pessoa) session.createCriteria(Pessoa.class).add(Restrictions.eq("cpf", cpf)).add(Restrictions.eq("senha", senha)).uniqueResult();
            return pessoa;
        } catch (HibernateException e) {
            return null;
        } finally {
            closeSession();
        }

    }

    public List<Contato> listarContato(Integer id) {

        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM Contato where pessoa_id = :id").setParameter("id", id);
            return query.list();
        } catch (HibernateException | NullPointerException e) {
            System.out.println(e);
            List<Contato> lista = new ArrayList();
            lista.add(new Contato(0, 0));
            return lista;
        } finally {
            closeSession();
        }

    }

    public List<Transferencia> listarTransferencia(String cpf) {

        try {
            session.beginTransaction();
            if (cpf.length() == 0) {
                return session.createCriteria(Transferencia.class).list();
            } else {
                Criteria criteria = session.createCriteria(Transferencia.class);
                Criterion crit1 = Restrictions.eq("cpfRemetente", cpf);
                Criterion crit2 = Restrictions.eq("cpfDestinatario", cpf);
                return criteria.add(Restrictions.or(crit1, crit2)).list();
            }
        } catch (HibernateException e) {
            return null;
        } finally {
            closeSession();
        }

    }

    public List<Transferencia> filtrarTransferencia(Transferencia transferencia) {

        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Transferencia.class);
            if (!transferencia.getCpfDestinatario().equals("*")) {
                criteria.add(Restrictions.eq("cpfDestinatario", transferencia.getCpfDestinatario()));
            }
            if (!transferencia.getCpfRemetente().equals("*")) {
                criteria.add(Restrictions.eq("cpfRemetente", transferencia.getCpfRemetente()));
            }
            if (!transferencia.getTipoTranferencia().equals("*")) {
                criteria.add(Restrictions.eq("tipoTranferencia", transferencia.getTipoTranferencia()));
            }
            if (transferencia.getValor() != 0) {
                criteria.add(Restrictions.gt("valor", transferencia.getValor()));
            }
            transf = criteria.list();
            return transf;
        } catch (HibernateException e) {
            return null;
        } finally {
            closeSession();
        }

    }

    public List<Transferencia> filtrarTransferenciaUsuario(Transferencia transferencia, String cpf) {

        try {

            session.beginTransaction();
            Criteria criteria = session.createCriteria(Transferencia.class);
            String rem = transferencia.getCpfRemetente(), des = transferencia.getCpfDestinatario();

            if (rem.equals("*") && des.equals("*") || !rem.equals(cpf) && !des.equals(cpf)) {
                Criterion crit1 = Restrictions.eq("cpfRemetente", cpf);
                Criterion crit2 = Restrictions.eq("cpfDestinatario", cpf);
                criteria.add(Restrictions.or(crit1, crit2));
            } else {
                if (!rem.equals("*")) {
                    criteria.add(Restrictions.eq("cpfRemetente", rem));
                }
                if (!des.equals("*")) {
                    criteria.add(Restrictions.eq("cpfDestinatario", des));
                }
            }
            if (!transferencia.getTipoTranferencia().equals("*")) {
                criteria.add(Restrictions.eq("tipoTranferencia", transferencia.getTipoTranferencia()));
            }
            if (transferencia.getValor() != 0) {
                criteria.add(Restrictions.gt("valor", transferencia.getValor()));
            }

            return criteria.list();
        } catch (HibernateException e) {
            return null;
        } finally {
            closeSession();
        }

    }

}