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

import com.banco.model.Pessoa;
import com.banco.model.Transferencia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
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
    public void adicionarOuEditar(Pessoa pessoa) {

        try {
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
            session.beginTransaction();
            pessoa = (Pessoa) session.get(Pessoa.class, id);
            session.delete(pessoa);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println(e);
        } finally {
            closeSession();
        }

    }

    @Override
    public List<Pessoa> listar() {

        try {
            session.beginTransaction();
            list = session.createCriteria(Pessoa.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
            session.getTransaction().commit();
            return list;
        } catch (HibernateException e) {
            System.out.println(e);
            return null;
        } finally {
            closeSession();
        }

    }

    public List<String> listarCpf() {

        try {
            session.beginTransaction();
            list = session.createCriteria(Pessoa.class).list();
            session.getTransaction().commit();

            List<String> cpf = new ArrayList();
            for (Pessoa pessoa : list) {
                cpf.add(pessoa.getCpf());
            }
            return cpf;
        } catch (HibernateException e) {
            System.out.println(e);
            return null;
        } finally {
            closeSession();
        }

    }

    @Override
    public List<Pessoa> procurar(String cpf) {

        try {
            session.beginTransaction();
            if (cpf != null) {
                list = session.createCriteria(Pessoa.class).add(Restrictions.like("cpf", cpf, MatchMode.ANYWHERE)).list();
            } else {
                list = session.createCriteria(Pessoa.class).list();
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

    public Pessoa procurarId(Integer id) {

        try {
            session.beginTransaction();
            pessoa = (Pessoa) session.createCriteria(Pessoa.class).add(Restrictions.eq("id", id)).uniqueResult();
            session.getTransaction().commit();
            return pessoa;
        } catch (HibernateException e) {
            System.out.println(e);
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
            session.getTransaction().commit();
            return list;
        } catch (HibernateException e) {
            System.out.println(e);
            return null;
        } finally {
            closeSession();
        }

    }

    public boolean validarLogin(String cpf, String senha) {

        try {
            session.beginTransaction();
            pessoa = (Pessoa) session.createCriteria(Pessoa.class).add(Restrictions.eq("cpf", cpf)).add(Restrictions.eq("senha", senha)).uniqueResult();
            session.getTransaction().commit();
            return senha.equals(pessoa.getSenha());
        } catch (NullPointerException | HibernateException e) {
            System.out.println(e);
            return false;
        } finally {
            closeSession();
        }

    }

    public Pessoa logar(String cpf, String senha) {

        try {
            session.beginTransaction();
            pessoa = (Pessoa) session.createCriteria(Pessoa.class).add(Restrictions.eq("cpf", cpf)).add(Restrictions.eq("senha", senha)).uniqueResult();
            session.getTransaction().commit();
            return pessoa;
        } catch (HibernateException e) {
            System.out.println(e);
            return null;
        } finally {
            closeSession();
        }

    }
    
    public List<Transferencia> listarTransferencia() {

        try {
            session.beginTransaction();
            transf = session.createCriteria(Transferencia.class).list();
            session.getTransaction().commit();
            return transf;
        } catch (HibernateException e) {
            System.out.println(e);
            return null;
        } finally {
            closeSession();
        }

    }

    public List<Transferencia> filtrarTransferencia(Transferencia transferencia) {

        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Transferencia.class);
            if (!transferencia.getCpfDestinatario().equals("*")) criteria.add(Restrictions.eq("cpfDestinatario", transferencia.getCpfDestinatario()));
            if (!transferencia.getCpfRemetente().equals("*")) criteria.add(Restrictions.eq("cpfRemetente", transferencia.getCpfRemetente()));
            if (!transferencia.getTipoTranferencia().equals("*")) criteria.add(Restrictions.eq("tipoTranferencia", transferencia.getTipoTranferencia()));
            if (transferencia.getValor() != 0) criteria.add(Restrictions.gt("valor", transferencia.getValor()));
            transf = criteria.list();
            session.getTransaction().commit();
            return transf;
        } catch (HibernateException e) {
            System.out.println(e);
            return null;
        } finally {
            closeSession();
        }

    }

}
