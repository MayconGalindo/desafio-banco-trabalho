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
package com.banco.view.adm.custom;

import com.banco.TelaLogin;
import com.banco.controller.ControllerBanco;
import com.banco.controller.ControllerPessoa;
import com.banco.model.BancoBrasil;
import com.banco.model.Pessoa;
import com.banco.view.InicioAdm;
import com.banco.view.InicioUsuario;
import com.banco.view.adm.ContaAdm;
import com.banco.view.adm.form.AddEditConta;
import com.banco.view.adm.form.AddEditPessoa;
import com.banco.view.filtro.InicioTransferencia;
import com.banco.view.usuario.ListarConta;

import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.component.IRequestablePage;

import java.text.ParseException;
import java.util.List;

/**
 *
 * @author NOTEDESENVSP1
 */
public class AdmHeader extends Panel {

    ModalWindow lista;
    WebMarkupContainer bodyMarkup;
    IRequestablePage page;
    Form searchForm;
    ModalWindow formAdd;
    Pessoa pessoa;
    BancoBrasil banco;
    Component add;
    CompoundPropertyModel model;
    PropertyModel txtSearch;
    String titulo;
    List searchList;

    public AdmHeader(String id, boolean pagina) throws ParseException {

        super(id);

        pessoa = new Pessoa();
        banco = new BancoBrasil();

        bodyMarkup = new WebMarkupContainer("bodyMarkup");

        formAdd = new ModalWindow("formAdd");

        if (pagina) {
            titulo = "Adicionar Pessoa";
            add = new AddEditPessoa(formAdd.getContentId(), null) {

                @Override
                public void fecharModal(AjaxRequestTarget target) {
                    formAdd.close(target);
                    atualizarLista(target);
                }

            };
            txtSearch = new PropertyModel<>(pessoa, "cpf");
        } else {
            titulo = "Adicionar Conta";
            add = new AddEditConta(formAdd.getContentId()){

                @Override
                public void fecharModal(AjaxRequestTarget target) {
                    formAdd.close(target);
                    atualizarLista(target);
                }

            };
            txtSearch = new PropertyModel<>(banco, "conta");
        }

        formAdd.setTitle(titulo);
        formAdd.setContent(add);
        bodyMarkup.add(formAdd);

        bodyMarkup.add(new AjaxLink("btnAdd") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                formAdd.show(target);
                atualizarLista(target);
            }

        });

        searchForm = new Form<>("searchForm");

        searchForm.add(new TextField<>("txtSearch", txtSearch));
        searchForm.add(new AjaxButton("btnSearch", searchForm) {

            @Override
            protected void onSubmit(AjaxRequestTarget target) {

                if (pagina) {
                    searchList = new ControllerPessoa().procurar(pessoa.getCpf());
                } else {
                    searchList = new ControllerBanco().procurar(banco.getConta());
                }

                procurarLista(target, searchList);

            }

            @Override
            protected void onError(AjaxRequestTarget target) {
                super.onError(target);
            }

        });
        bodyMarkup.add(searchForm);

        bodyMarkup.add(new AjaxLink("usuario") {

            @Override 
            public void onClick(AjaxRequestTarget target) {
                try {
                    page = new InicioAdm();
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                setResponsePage(page);
            }

        });

        bodyMarkup.add(new AjaxLink("conta") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                try {
                    page = new ContaAdm();
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                setResponsePage(page);
            }

        });

        bodyMarkup.add(new AjaxLink("transf") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                try {
                    page = new InicioTransferencia("");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                setResponsePage(page);
            }

        });

        bodyMarkup.add(new AjaxLink("sair") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                page = new TelaLogin();
                setResponsePage(page);
                Session.get().invalidateNow();
            }

        });

        add(bodyMarkup);

    }

    public void atualizarLista(AjaxRequestTarget target) {
    }

    public void procurarLista(AjaxRequestTarget target, List list) {
    }

}
