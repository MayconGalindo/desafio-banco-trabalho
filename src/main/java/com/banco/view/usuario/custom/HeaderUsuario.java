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
package com.banco.view.usuario.custom;

import java.text.ParseException;

import com.banco.TelaLogin;
import com.banco.model.Pessoa;
import com.banco.view.usuario.InicioUsuario;
import com.banco.view.filtro.InicioTransferencia;
import com.banco.view.usuario.ListarConta;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.Session;

/**
 *
 * @author NOTEDESENVSP1
 */
public final class HeaderUsuario extends Panel {

    ModalWindow listaC;
    ModalWindow listaT;
    WebMarkupContainer bodyMarkup;
    IRequestablePage page;

    public HeaderUsuario(String id, Pessoa pessoa) {

        super(id);

        bodyMarkup = new WebMarkupContainer("bodyMarkup");

        bodyMarkup.add(new AjaxLink("inicio") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                page = new InicioUsuario(pessoa);
                setResponsePage(page);
            }

        });

        listaC = new ModalWindow("listaC");
        listaC.setTitle("Minhas Contas");
        listaC.setContent(new ListarConta(listaC.getContentId(), pessoa, null, "", true));
        bodyMarkup.add(listaC);

        bodyMarkup.add(new AjaxLink("contas") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                listaC.show(target);
            }

        });

        bodyMarkup.add(new AjaxLink("transferencia") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                try {
                    page = new InicioTransferencia(pessoa.getCpf());
                } catch (ParseException e) {
					e.printStackTrace();
				}
                setResponsePage(page);
            }

        });
        
        bodyMarkup.add(new AjaxLink("sair") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                getSession().invalidate();
                page = new TelaLogin();
                setResponsePage(page);
            }

        });

        add(bodyMarkup);

    }
}
