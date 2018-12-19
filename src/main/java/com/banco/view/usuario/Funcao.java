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
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

/**
 *
 * @author NOTEDESENVSP1
 */
public final class Funcao extends WebPage {

    WebMarkupContainer markBody;
    ModalWindow list;
    String lblCorrente;
    String lblPoupanca;
    AjaxLink corrente;
    AjaxLink poupanca;

    public Funcao(Pessoa pessoa, String funcao) {

        super();

        if (funcao.equals("Mes")) {
            lblCorrente = "Corrente para Poupança";
            lblPoupanca = "Poupança para Corrente";
        } else if (funcao.equals("Dif")) {
            lblCorrente = "Corrente para Corrente";
            lblPoupanca = "Poupança para Poupança";
        }  else {
            lblCorrente = "Corrente";
            lblPoupanca = "Poupança";
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
                list.setContent(new ListarConta(list.getContentId(), pessoa, funcao, true, false));
                list.show(target);
            }

        };
        corrente.add(new Label("lblCorrente", lblCorrente));
        markBody.add(corrente);

        poupanca = new AjaxLink("poupanca") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                list.setContent(new ListarConta(list.getContentId(), pessoa, funcao, false, false));
                list.show(target);
            }
            
        };
        poupanca.add(new Label("lblPoupanca", lblPoupanca));
        markBody.add(poupanca);

        add(markBody);

    }

    public Funcao(PageParameters params) {
        //TODO:  process page parameters
    }
}
