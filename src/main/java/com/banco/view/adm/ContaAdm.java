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
package com.banco.view.adm;

import com.banco.controller.ControllerBanco;
import com.banco.model.BancoBrasil;
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
public class ContaAdm extends WebPage {

    WebMarkupContainer bodyMarkup;
    PageableListView listView;
    List<BancoBrasil> refreshLista = new ControllerBanco().listar();

    public ContaAdm() throws ParseException{

        super();
        
        bodyMarkup = new WebMarkupContainer("bodyMarkup");
        bodyMarkup.setOutputMarkupId(true);

        bodyMarkup.add(new AdmHeader("header", false) {

            @Override
            public void atualizarLista(AjaxRequestTarget target) {
                refreshLista = new ControllerBanco().listar();
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

        listView = new PageableListView<BancoBrasil>("list", lista, 30) {

            @Override
            protected void populateItem(ListItem<BancoBrasil> item) {

                BancoBrasil banco = item.getModelObject();
                String estado;
                if (banco.isEstadoConta()) {
                    estado = "Sim";
                } else {
                    estado = "Não";
                }
                item.add(new Label("id", banco.getId()));
                item.add(new Label("agencia", banco.getAgencia()));
                item.add(new Label("conta", banco.getConta()));
                item.add(new Label("estadoConta", estado));
                item.add(new Label("cpf", banco.getPessoa().getCpf()));
                item.add(new Funcoes("funcoes", banco.getId(), false, banco.isEstadoConta()) {

                    @Override
                    public void atualizarLista(AjaxRequestTarget target) {
                        refreshLista = new ControllerBanco().listar();
                        target.add(bodyMarkup);
                    }
                    
                    

                });

            }

        };
        listView.setOutputMarkupId(true);

        bodyMarkup.add(listView);
        bodyMarkup.add(new AjaxPagingNavigator("pag", listView));

        add(bodyMarkup);

    }

    public ContaAdm(PageParameters params) {
        //TODO:  process page parameters
    }
}
