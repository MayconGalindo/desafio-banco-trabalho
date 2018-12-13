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

import com.banco.TelaLogin;
import com.banco.model.Pessoa;
import com.banco.view.InicioUsuario;
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

    ModalWindow lista;
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

        lista = new ModalWindow("lista");
        lista.setTitle("Minhas Contas");
        lista.setContent(new ListarConta(lista.getContentId(), pessoa, null, true, true));
        bodyMarkup.add(lista);

        bodyMarkup.add(new AjaxLink("contas") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                lista.show(target);
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
}
