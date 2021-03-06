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
package com.banco;

import java.text.ParseException;

import com.banco.controller.ControllerPessoa;
import com.banco.model.Pessoa;
import com.banco.view.adm.InicioAdm;
import com.banco.view.usuario.InicioUsuario;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;

/**
 *
 * @author NOTEDESENVSP1
 */
public final class TelaLogin extends WebPage {

    Pessoa pessoa;
    IRequestablePage page;
    PasswordTextField senha;

    CssResourceReference css = new CssResourceReference(TelaLogin.class, "LoginStyle.css");

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(CssReferenceHeaderItem.forReference(css));
    }

    public TelaLogin() {

        super();

        pessoa = new Pessoa();
        WebMarkupContainer markup = new WebMarkupContainer("bodyMarkup");
        FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);
        Form form = new Form("form", new CompoundPropertyModel(pessoa));

        senha = new PasswordTextField("senha");
        senha.setRequired(true);
        senha.setOutputMarkupId(true);
        senha.setResetPassword(true);

        form.add(new RequiredTextField("cpf"));
        form.add(senha);

        form.add(new AjaxButton("submit", form) {

            @Override
            protected void onSubmit(AjaxRequestTarget target) {

                ControllerPessoa cp = new ControllerPessoa();
                if (AutenticarLogin.get().signIn(pessoa.getCpf(), pessoa.getSenha())) {

                    pessoa = new ControllerPessoa().logar(pessoa.getCpf(), pessoa.getSenha());

                    switch (pessoa.getTipoConta()) {

                    case 'A':
                        try {
                            page = new InicioAdm();
                        } catch (ParseException e) {
                            System.out.print(e);
                        }
                        break;

                    case 'U':
                        page = new InicioUsuario(pessoa);
                        break;
                    }
                    setResponsePage(page);

                } else {
                    info("Usuario e/ou senha errada");
                    target.add(feedbackPanel);
                }

            }

            @Override
            protected void onError(AjaxRequestTarget target) {
                target.add(feedbackPanel);
            }

        });

        markup.add(feedbackPanel);
        markup.add(form);
        add(markup);

    }

    public TelaLogin(PageParameters params) {
        // TODO: process page parameters
    }
}
