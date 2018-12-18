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
package com.banco.view.adm.form;

import com.banco.controller.ControllerPessoa;
import com.banco.model.BancoBrasil;
import com.banco.model.Pessoa;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.validation.validator.StringValidator;

/**
 *
 * @author NOTEDESENVSP1
 */
public class AddEditPessoa extends Panel {

    WebMarkupContainer bodyMarkup;
    Form form;
    TextField cpf;
    TextField cep;
    TextField uf;
    PasswordTextField senha;
    Label btnLabel;
    AjaxButton submit;

    ControllerPessoa cp;
    Pessoa pessoa;
    BancoBrasil banco;

    List<Character> tipo = new ArrayList<>();
    JavaScriptResourceReference js = new JavaScriptResourceReference(AddEditPessoa.class, "AddEditPessoa.js");

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(JavaScriptReferenceHeaderItem.forReference(js));
    }

    public AddEditPessoa(String id, Integer idPessoa) {

        super(id);

        tipo.add('U');
        tipo.add('A');

        cp = new ControllerPessoa();
        senha = new PasswordTextField("senha");

        try {

            if (idPessoa == null) {
                pessoa = new Pessoa();
                banco = new BancoBrasil();
                btnLabel = new Label("btnLabel", Model.of("Adicionar"));
                senha.setRequired(true);
            } else {
                pessoa = cp.procurarId(idPessoa);
                banco = pessoa.getContaL().get(0);
                btnLabel = new Label("btnLabel", Model.of("Editar"));
                senha.setRequired(false);
            }

        } catch (Exception e) {
            pessoa = new Pessoa();
            banco = new BancoBrasil();
            btnLabel = new Label("btnLabel", Model.of("Adicionar"));
            senha.setRequired(true);
        }

        bodyMarkup = new WebMarkupContainer("bodyMarkup");

        form = new Form("form", new CompoundPropertyModel<>(pessoa));

        cpf = new TextField("cpf");
        cpf.add(StringValidator.maximumLength(11));
        cep = new TextField("cep");
        cep.add(StringValidator.maximumLength(8));
        uf = new TextField("uf");
        uf.add(StringValidator.maximumLength(2));
        submit = new AjaxButton("submit", form) {

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                banco.setPessoa(pessoa);
                pessoa.getContaL().add(banco);
                new ControllerPessoa().adicionarOuEditar(pessoa);
                fecharModal(target);
            }

            @Override
            protected void onError(AjaxRequestTarget target) {
                super.onError(target); //To change body of generated methods, choose Tools | Templates.
            }

        };
        submit.add(btnLabel);

        form.add(new TextField("nome"));
        form.add(cpf);
        form.add(cep);
        form.add(new TextField("endereco"));
        form.add(new TextField("numero"));
        form.add(new TextField("bairro"));
        form.add(new TextField("cidade"));
        form.add(uf);
        form.add(new EmailTextField("email"));
        form.add(new TextField("telefone"));
        form.add(new DropDownChoice("tipoConta", tipo));
        form.add(senha);

        form.add(new TextField("agencia", new PropertyModel(banco, "agencia")));
        form.add(new TextField("conta", new PropertyModel(banco, "conta")));

        form.add(submit);

        bodyMarkup.add(form);
        add(bodyMarkup);

    }

    public void fecharModal(AjaxRequestTarget target) {
    }

}
