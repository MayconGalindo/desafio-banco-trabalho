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

import com.banco.model.BancoBrasil;
import com.banco.model.Pessoa;
import com.banco.view.usuario.FuncoesUsuario;
import com.banco.view.usuario.custom.HeaderUsuario;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.component.IRequestablePage;

/**
 *
 * @author NOTEDESENVSP1
 */
public final class InicioUsuario extends WebPage {

    Pessoa pessoa;
    BancoBrasil banco;
    WebMarkupContainer markBody;
    IRequestablePage page;

    public InicioUsuario(Pessoa pessoa) {

        super();

        markBody = new WebMarkupContainer("bodyMarkup");

        markBody.add(new HeaderUsuario("header", pessoa));

        markBody.add(new AjaxLink("btnTraM") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                page = new FuncoesUsuario(pessoa, "Mes");
                setResponsePage(page);
            }

        });

        markBody.add(new AjaxLink("btnTraD") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                page = new FuncoesUsuario(pessoa, "Dif");
                setResponsePage(page);
            }

        });

        markBody.add(new AjaxLink("btnDep") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                page = new FuncoesUsuario(pessoa, "Dep");
                setResponsePage(page);
            }

        });

        markBody.add(new AjaxLink("btnSqr") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                page = new FuncoesUsuario(pessoa, "Saq");
                setResponsePage(page);
            }

        });

        add(markBody);

    }

    public InicioUsuario(PageParameters params) {

    }
}
