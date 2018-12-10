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

import com.banco.model.Pessoa;
import com.banco.view.usuario.Depositar;
import com.banco.view.usuario.Saquar;
import com.banco.view.usuario.Transferir;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.WebPage;

/**
 *
 * @author NOTEDESENVSP1
 */
public final class InicioUsuario extends WebPage {

    Pessoa pessoa;
    WebMarkupContainer markBody;
    ModalWindow pnlTra;
    ModalWindow pnlDep;
    ModalWindow pnlSqr;

    public InicioUsuario(Pessoa pessoa) {

        super();

        markBody = new WebMarkupContainer("markupBody");

        pnlTra = new ModalWindow("pnlTra");
        pnlTra.setOutputMarkupId(true);
        pnlTra.setResizable(false);
        pnlTra.setTitle("Transferencia");
        pnlTra.setContent(new Transferir(pnlTra.getContentId()));
        markBody.add(pnlTra);
        markBody.add(new AjaxLink("btnTra") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                pnlTra.show(target);
            }

        });
        
        pnlDep = new ModalWindow("pnlDep");
        pnlDep.setOutputMarkupId(true);
        pnlDep.setResizable(false);
        pnlDep.setTitle("Depositar");
        pnlDep.setContent(new Depositar(pnlDep.getContentId()));
        markBody.add(pnlDep);
        markBody.add(new AjaxLink("btnDep") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                pnlDep.show(target);
            }

        });
        
        pnlSqr = new ModalWindow("pnlSqr");
        pnlSqr.setOutputMarkupId(true);
        pnlSqr.setResizable(false);
        pnlSqr.setTitle("Saquar");
        pnlSqr.setContent(new Saquar(pnlSqr.getContentId()));
        markBody.add(pnlSqr);
        markBody.add(new AjaxLink("btnSqr") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                pnlSqr.show(target);
            }

        });

        add(markBody);

    }

    public InicioUsuario(PageParameters params) {

    }
}
