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
package com.banco.view;

import com.banco.controller.ControllerBanco;
import com.banco.controller.ControllerPessoa;
import com.banco.model.Pessoa;
import com.banco.view.adm.FuncoesAdm;
import com.banco.view.adm.custom.AdmHeader;
import java.text.ParseException;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

/**
 *
 * @author NOTEDESENVSP1
 */
public final class InicioAdm extends WebPage {

    WebMarkupContainer bodyMarkup;
    PageableListView listView;
    List<Pessoa> refreshLista = new ControllerPessoa().listar();

    public InicioAdm() throws ParseException{

        super();

        bodyMarkup = new WebMarkupContainer("bodyMarkup");
        bodyMarkup.setOutputMarkupId(true);

        bodyMarkup.add(new AdmHeader("header", true) {

            @Override
            public void atualizarLista(AjaxRequestTarget target) {
                refreshLista = new ControllerPessoa().listar();
                target.add(bodyMarkup);
            }

            @Override
            public void procurarLista(AjaxRequestTarget target, List list) {
                refreshLista = list;
                target.add(bodyMarkup);
            }
            
        });

        IModel lista = new LoadableDetachableModel() {

            @Override
            protected Object load() {
                return refreshLista;
            }

        };

        listView = new PageableListView<Pessoa>("list", lista, 30) {

            @Override
            protected void populateItem(ListItem<Pessoa> item) {

                Pessoa pessoa = item.getModelObject();

                item.add(new Label("id", pessoa.getId()));
                item.add(new Label("nome", pessoa.getNome()));
                item.add(new Label("cpf", pessoa.getCpf()));
                item.add(new Label("cep", pessoa.getCep()));
                item.add(new Label("endereco", pessoa.getEndereco() + ", " + pessoa.getNumero()));
                item.add(new Label("email", pessoa.getEmail()));
                item.add(new Label("tipoConta", pessoa.getTipoConta()));
                item.add(new Label("contas", new ControllerBanco().contasPessoa(pessoa.getId()).size()));
                item.add(new FuncoesAdm("funcoes", pessoa.getId(), true, true) {

                    @Override
                    public void atualizarLista(AjaxRequestTarget target) {
                        refreshLista = new ControllerPessoa().listar();
                        target.add(bodyMarkup);
                    }

                });

            }

        };
        listView.setOutputMarkupPlaceholderTag(true);
        listView.setOutputMarkupId(true);

        bodyMarkup.add(listView);
        bodyMarkup.add(new AjaxPagingNavigator("pag", listView));

        add(bodyMarkup);

    }

    public InicioAdm(PageParameters params) {
    }
}