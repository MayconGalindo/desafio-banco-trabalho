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
package com.banco.view.usuario;

import com.banco.controller.ControllerBanco;
import com.banco.model.BancoBrasil;
import com.banco.model.Pessoa;
import com.banco.view.usuario.custom.LinkSelecionar;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;

/**
 *
 * @author NOTEDESENVSP1
 */
public final class ListarConta extends Panel {

    WebMarkupContainer markup;
    PageableListView lv;
    LinkSelecionar selecionar;
    Label lblSelecionar;
    
    public ListarConta(String id, Pessoa pessoa, String func, String tipo, boolean inicio) {

        super(id);

        markup = new WebMarkupContainer("bodyMarkup");
        
        lblSelecionar = new Label("label", Model.of("Escolher"));
        markup.add(lblSelecionar);
        
        IModel lista = new LoadableDetachableModel() {

            @Override
            protected Object load() {
                return new ControllerBanco().contasPessoa(pessoa.getId());
            }

        };

        lv = new PageableListView<BancoBrasil>("list", lista, 6) {

            @Override
            protected void populateItem(ListItem<BancoBrasil> item) {

                BancoBrasil conta = item.getModelObject();

                selecionar = new LinkSelecionar("selecionar", pessoa, func, tipo, conta.getId());

                if (inicio == true) {
                    selecionar.setVisible(false);
                    lblSelecionar.setVisibilityAllowed(false);
                }

                item.add(new Label("agencia", conta.getAgencia()));
                item.add(new Label("conta", conta.getConta()));
                item.add(new Label("corrente", conta.getValorCorrente()));
                item.add(new Label("poupanca", conta.getValorPoupanca()));
                item.add(selecionar);

            }

        };
        lv.setOutputMarkupPlaceholderTag(true);
        lv.setOutputMarkupId(true);

        markup.add(lv);
        markup.add(new AjaxPagingNavigator("pag", lv));

        add(markup);

    }
    
}