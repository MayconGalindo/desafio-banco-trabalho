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

import com.banco.controller.relatorio.Relatorio;
import com.banco.view.adm.form.AddEditPessoa;
import com.banco.view.adm.form.Delete;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.util.resource.AbstractResourceStreamWriter;

/**
 *
 * @author NOTEDESENVSP1
 */
public class Funcoes extends Panel {

    ModalWindow modalDel;
    ModalWindow modalEdit;
    WebMarkupContainer bodyMarkup;

    public Funcoes(String id, Integer idPessoa, boolean pessoaOuBanco) {

        super(id);

        bodyMarkup = new WebMarkupContainer("webmark");
        bodyMarkup.setOutputMarkupId(true);

        modalDel = new ModalWindow("modalDel");
        modalDel.setOutputMarkupId(true);
        modalDel.setResizable(false);
        modalDel.setTitle("Excluir");
        modalDel.setContent(new Delete(modalDel.getContentId(), idPessoa, pessoaOuBanco) {

            @Override
            public void fecharModal(AjaxRequestTarget target) {
                modalDel.close(target);
                atualizarLista(target);
            }

        });
        bodyMarkup.add(modalDel);
        bodyMarkup.add(new AjaxLink("linkDel") {

            @Override
            public void onClick(AjaxRequestTarget art) {
                modalDel.show(art);
            }

        });

        modalEdit = new ModalWindow("modalEdit");
        modalEdit.setOutputMarkupId(true);
        modalEdit.setResizable(false);
        modalEdit.setTitle("Editar");
        modalEdit.setContent(new AddEditPessoa(modalEdit.getContentId(), idPessoa) {

            @Override
            public void fecharModal(AjaxRequestTarget target) {
                modalEdit.close(target);
                atualizarLista(target);
            }

        });
        bodyMarkup.add(modalEdit);
        bodyMarkup.add(new AjaxLink("linkEdit") {

            @Override
            public void onClick(AjaxRequestTarget art) {
                modalEdit.show(art);
            }

        });

        bodyMarkup.add(new Link<Void>("excel") {

            @Override
            public void onClick() {
                gerarRelatorio(idPessoa, true, pessoaOuBanco);
            }

        });

        bodyMarkup.add(new Link<Void>("pdf") {

            @Override
            public void onClick() {
                gerarRelatorio(idPessoa, false, pessoaOuBanco);
            }

        });

        add(bodyMarkup);

    }

    void gerarRelatorio(Integer id, boolean excelOuPdf, boolean pessoaOuBanco) {

        byte[] bit;
        String arq;

        if (excelOuPdf) {
            if (pessoaOuBanco) {
                bit = new Relatorio().gerarExcelPessoa(id);
                arq = "Relatorio ID " + id + ".xls";
            } else {
                bit = new Relatorio().gerarExcelConta(id);
                arq = "Relatorio Conta " + id + ".xls";
            }
        } else {
            if (pessoaOuBanco) {
                bit = new Relatorio().gerarPdfPessoa(id);
                arq = "Relatorio ID " + id + ".pdf";
            } else {
                bit = new Relatorio().gerarPdfConta(id);
                arq = "Relatorio Conta " + id + ".pdf";
            }
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

    public void atualizarLista(AjaxRequestTarget target) {
    }

}