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
import com.banco.controller.relatorio.Relatorio;
import com.banco.model.Transferencia;
import com.banco.view.adm.custom.AdmHeader;
import com.banco.view.usuario.custom.HeaderUsuario;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.util.resource.AbstractResourceStreamWriter;

/**
 *
 * @author NOTEDESENVSP1
 */
public final class InicioTransferencia extends WebPage {

    WebMarkupContainer bodyMarkup;
    PageableListView listView;
    List<Transferencia> refreshLista;

    public InicioTransferencia(String cpf) throws ParseException {

        super();
        
        bodyMarkup = new WebMarkupContainer("bodyMarkup");
        bodyMarkup.setOutputMarkupId(true);

        if (cpf.length() == 0) {
            refreshLista = new ControllerPessoa().listarTransferencia("");
            bodyMarkup.add(new AdmHeader("header", true));
        } else {
            refreshLista = new ControllerPessoa().listarTransferencia(cpf);
            bodyMarkup.add(new HeaderUsuario("header", new ControllerPessoa().procurar(cpf).get(0)));
        }

        IModel lista = new LoadableDetachableModel() {

            @Override
            protected Object load() {
                return refreshLista;
            }

        };

        bodyMarkup.add(new FiltroTransferencia("tableHeader", cpf) {

            @Override
            public void atualizarLista(AjaxRequestTarget target, List<Transferencia> lista) {
                refreshLista = lista;
                target.add(bodyMarkup);
            }

        });

        bodyMarkup.add(new Link("pdf") {
            @Override
            public void onClick() {
                gerarRelatorio(true);
            }
        });

        bodyMarkup.add(new Link("excel") {
            @Override
            public void onClick() {
                gerarRelatorio(false);
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
    }

    private void gerarRelatorio(boolean tipo) {

        byte[] bit;
        String arq;

        if (tipo) {
            bit = new Relatorio().gerarPdfTransferencia(refreshLista);
            arq = "Relatorio Transferencia.pdf";
        } else {
            bit = new Relatorio().gerarExcelTransferencia(refreshLista);
            arq = "Relatorio Transferencia.xls";
        }

        AbstractResourceStreamWriter stream = new AbstractResourceStreamWriter() {
            @Override
            public void write(OutputStream out) throws IOException {
                out.write(bit);
                out.close();
            }
        };

        ResourceStreamRequestHandler hand = new ResourceStreamRequestHandler(stream, arq);
        hand.setContentDisposition(ContentDisposition.ATTACHMENT);
        hand.setFileName(arq);
        getRequestCycle().scheduleRequestHandlerAfterCurrent(hand);
    }
}