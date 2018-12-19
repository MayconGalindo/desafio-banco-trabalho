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
package com.banco.view.filtro;

import com.banco.controller.ControllerPessoa;
import com.banco.model.Transferencia;
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
public final class InicioTransferencia extends WebPage {

    WebMarkupContainer bodyMarkup;
    PageableListView listView;
    List<Transferencia> refreshLista = new ControllerPessoa().listarTransferencia();
    
    public InicioTransferencia() throws ParseException{
        
        super();
        
        bodyMarkup = new WebMarkupContainer("bodyMarkup");
        bodyMarkup.setOutputMarkupId(true);

        bodyMarkup.add(new AdmHeader("header", true));

        IModel lista = new LoadableDetachableModel() {

            @Override
            protected Object load() {
                return refreshLista;
            }

        };

        bodyMarkup.add(new FiltroTransferencia("tableHeader"){
            
            @Override
            public void atualizarLista(AjaxRequestTarget target, List<Transferencia> lista) {
                refreshLista = lista;
                target.add(bodyMarkup);
            }
            
        });
        
        listView = new PageableListView<Transferencia>("list", lista, 30) {

            @Override
            protected void populateItem(ListItem<Transferencia> item) {

                Transferencia transferencia = item.getModelObject();

                item.add(new Label("id", transferencia.getId()));
                item.add(new Label("cpfR", transferencia.getCpfRemetente()));
                item.add(new Label("cpfD", transferencia.getCpfDestinatario()));
                item.add(new Label("tipo", transferencia.getTipoTranferencia()));
                item.add(new Label("valor", transferencia.getValor()));
                item.add(new Label("data", transferencia.getDataTransf()));

            }

        };
        listView.setOutputMarkupPlaceholderTag(true);
        listView.setOutputMarkupId(true);

        bodyMarkup.add(listView);
        bodyMarkup.add(new AjaxPagingNavigator("pag", listView));

        add(bodyMarkup);
        
    }
    
    public InicioTransferencia(PageParameters params) {
        //TODO:  process page parameters
    }
}
