/*
 * Copyright 2019 NOTEDESENVSP1.
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
import com.banco.controller.ControllerPessoa;
import com.banco.model.Pessoa;
import com.banco.view.adm.InicioAdm;
import com.banco.view.usuario.InicioUsuario;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.component.IRequestablePage;

/**
 *
 * @author NOTEDESENVSP1
 */
public final class TelaAviso extends Panel {

    WebMarkupContainer bodyMarkup;
    IRequestablePage page;
    Form form;

    ControllerPessoa cp = new ControllerPessoa();

    public TelaAviso(String id, Pessoa pessoa, String mensagem) {

        super(id);

        bodyMarkup = new WebMarkupContainer("bodyMarkup");
        bodyMarkup.add(new Label("mensagem", Model.of(mensagem)));

        form = new Form("form") {

            @Override
            protected void onSubmit() {
                getSession().invalidate();
                page = new TelaLogin();
                setResponsePage(page);
            }

        };

        form.add(new AjaxLink("inicio") {
            
            @Override
            public void onClick(AjaxRequestTarget target) {
                Pessoa p = cp.logar(pessoa.getCpf(), pessoa.getSenha());
                page = new InicioUsuario(p);
                setResponsePage(page);
            }

        });

        bodyMarkup.add(form);

        add(bodyMarkup);

    }
}