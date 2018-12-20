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

import com.banco.controller.ControllerBanco;
import com.banco.controller.ControllerPessoa;
import com.banco.model.BancoBrasil;
import com.banco.model.Pessoa;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

/**
 *
 * @author NOTEDESENVSP1
 */
public class AddEditConta extends Panel {

    WebMarkupContainer bodyMarkup;
    Form form;
    TextField cpf;
    TextField cep;
    TextField uf;
    PasswordTextField senha;
    Label btnLabel;
    AjaxButton submit;

    ControllerBanco cb;
    Pessoa pessoa;
    BancoBrasil banco;

    public AddEditConta(String id) {

        super(id);

        cb = new ControllerBanco();

        pessoa = new Pessoa();
        banco = new BancoBrasil();
        btnLabel = new Label("btnLabel", Model.of("Adicionar"));

        bodyMarkup = new WebMarkupContainer("bodyMarkup");

        form = new Form("form", new CompoundPropertyModel<>(banco));

        submit = new AjaxButton("submit", form) {

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                pessoa = new ControllerPessoa().procurar(pessoa.getCpf()).get(0);
                new ControllerPessoa().adicionarOuEditar(pessoa);
                banco.setEstadoConta(true);
                banco.setPessoa(pessoa);
                new ControllerBanco().adicionarOuEditar(banco);
                fecharModal(target);
            }

            @Override
            protected void onError(AjaxRequestTarget target) {
                super.onError(target); //To change body of generated methods, choose Tools | Templates.
            }

        };

        form.add(new DropDownChoice("cpf", new PropertyModel<Pessoa>(pessoa, "cpf"), new LoadableDetachableModel<List<String>>() {
            @Override
            protected List<String> load() {
                return new ControllerPessoa().listarCpf();
            }
        }));
        form.add(new TextField("agencia"));
        form.add(new TextField("conta"));
        form.add(submit);

        bodyMarkup.add(form);
        add(bodyMarkup);

    }

    public void fecharModal(AjaxRequestTarget target) {
    }

}
