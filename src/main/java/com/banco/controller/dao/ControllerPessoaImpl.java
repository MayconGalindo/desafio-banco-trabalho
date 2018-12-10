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
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author NOTEDESENVSP1
 */
public abstract class ControllerPessoaImpl extends SessionGenerator implements ControllerDAO<Pessoa>, Serializable {

    @Override
    public void adicionarOuEditar(Pessoa pessoa) {

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
            Pessoa pessoa = (Pessoa) session.get(Pessoa.class, id);
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

            Session session = getSession();
            session.beginTransaction();
            List<Pessoa> list = session.createCriteria(Pessoa.class).list();
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
    public List<Pessoa> procurar(String cpf) {

        List<Pessoa> list = null;
        Session session = getSession();
        session.beginTransaction();

        try {

            if (cpf != null) {
                list = session.createCriteria(Pessoa.class).add(Restrictions.like("cpf", cpf)).list();
            }
            session.getTransaction().commit();

            return list;

        } catch (NullPointerException | HibernateException e) {
            System.out.println(e);
            list = session.createCriteria(Pessoa.class).list();
            session.getTransaction().commit();
            return list;
        } finally {
            closeSession();
        }

    }

    @Override
    public List<Pessoa> filtrar(String filtros) {

        try {

            List<Pessoa> list;
            Session ses = getSession();
            ses.beginTransaction();
            list = ses.createCriteria(Pessoa.class).add(Restrictions.eq("cidade", filtros)).list();
            ses.getTransaction().commit();

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

            Pessoa pessoa;
            Session session = getSession();
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
            
            Pessoa pessoa;
            Session session = getSession();
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

}
