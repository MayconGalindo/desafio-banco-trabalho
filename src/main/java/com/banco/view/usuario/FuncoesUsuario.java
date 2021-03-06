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

import com.banco.model.Pessoa;
import com.banco.view.usuario.custom.HeaderUsuario;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.resource.CssResourceReference;

/**
 *
 * @author NOTEDESENVSP1
 */
public final class FuncoesUsuario extends WebPage {

    WebMarkupContainer markBody;
    ModalWindow list;
    AjaxLink corrente, poupanca, corrente_poupanca, poupanca_corrente;

    String lblCorrente, lblPoupanca;
    CssResourceReference css = new CssResourceReference(FuncoesUsuario.class, "Style.css");

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(CssReferenceHeaderItem.forReference(css));
    }

    public FuncoesUsuario(Pessoa pessoa, String funcao) {

        super();

        corrente_poupanca = new AjaxLink("corrente_poupanca") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                list.setContent(new ListarConta(list.getContentId(), pessoa, funcao, "CP", false));
                list.show(target);
            }

        };
        corrente_poupanca.setOutputMarkupId(true);

        poupanca_corrente = new AjaxLink("poupanca_corrente") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                list.setContent(new ListarConta(list.getContentId(), pessoa, funcao, "PC", false));
                list.show(target);
            }

        };
        poupanca_corrente.setOutputMarkupId(true);

        if (funcao.equals("Mes") || funcao.equals("Dif")) {
            lblCorrente = "Corrente para Corrente";
            lblPoupanca = "Poupança para Poupança";
        } else {
            lblCorrente = "Corrente";
            lblPoupanca = "Poupança";
            corrente_poupanca.setVisible(false);
            poupanca_corrente.setVisible(false);
        }

        markBody = new WebMarkupContainer("bodyMarkup");

        markBody.add(new HeaderUsuario("header", pessoa));

        list = new ModalWindow("lista");
        list.setTitle("Escolha a conta");
        list.setOutputMarkupId(true);
        markBody.add(list);

        corrente = new AjaxLink("corrente") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                list.setContent(new ListarConta(list.getContentId(), pessoa, funcao, "CC", false));
                list.show(target);
            }

        };
        corrente.add(new Label("lblCorrente", lblCorrente));
        markBody.add(corrente);

        poupanca = new AjaxLink("poupanca") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                list.setContent(new ListarConta(list.getContentId(), pessoa, funcao, "PP", false));
                list.show(target);
            }

        };
        poupanca.add(new Label("lblPoupanca", lblPoupanca));
        markBody.add(poupanca);

        markBody.add(corrente_poupanca);
        markBody.add(poupanca_corrente);

        add(markBody);

    }

    public FuncoesUsuario(PageParameters params) {
    }
}