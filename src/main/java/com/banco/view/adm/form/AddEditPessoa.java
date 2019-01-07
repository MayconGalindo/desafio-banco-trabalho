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
import com.banco.controller.relatorio.Relatorio;
import com.banco.model.BancoBrasil;
import com.banco.model.Pessoa;
import com.banco.view.adm.custom.ValidatorConta;
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
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.validation.validator.StringValidator;
import org.hibernate.HibernateException;

/**
 *
 * @author NOTEDESENVSP1
 */
public class AddEditPessoa extends Panel {

    WebMarkupContainer bodyMarkup;
    Form form, formUpload;
    FeedbackPanel feedbackPanel;

    TextField cpf, cep, uf, nome, cidade, bairro, endereco, agencia, conta, telefone;
    EmailTextField email;
    NumberTextField<Integer>  rua;
    PasswordTextField senha;
    Label btnLabel, agenciaLabel, contaLabel;
    AjaxButton submit;
    private FileUploadField fileUpload;

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

        gerarCampos();

        tipo.add('U');
        tipo.add('A');

        pessoa = new Pessoa();
        banco = new BancoBrasil();

        cp = new ControllerPessoa();

        if (idPessoa == null) {
            btnLabel = new Label("btnLabel", Model.of("Adicionar"));
        } else {
            pessoa = cp.procurarId(idPessoa);
            btnLabel = new Label("btnLabel", Model.of("Editar"));
            agenciaLabel.setVisible(false);
            agencia.setVisible(false);
            contaLabel.setVisible(false);
            conta.setVisible(false);
            formUpload.setVisible(false);
        }

        bodyMarkup.add(feedbackPanel);

        formUpload.add(fileUpload);
        formUpload.add(new AjaxButton("submitFile", formUpload) {

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                try {
                    final FileUpload uploadedFile = fileUpload.getFileUpload();
                    if (uploadedFile != null) {
                        if (new Relatorio().inserirPessoaExcel(uploadedFile.writeToTempFile())) {
                            fecharModal(target);
                        }
                    }
                } catch (HibernateException ex) {
                    info("Usuario(s) já inserido");
                    target.add(feedbackPanel);
                } catch (Exception ex) {
                    info("Checar os valores inseridos no arquivo");
                    target.add(feedbackPanel);
                }
            }

        });
        bodyMarkup.add(formUpload);

        form = new Form("form", new CompoundPropertyModel<>(pessoa));

        submit = new AjaxButton("submit", form) {

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                
                try {
                    if (idPessoa == null) {
                        banco.setPessoa(pessoa);
                        banco.setEstadoConta(true);
                        if (new ControllerPessoa().adicionarOuEditar(pessoa) && new ControllerBanco().adicionarOuEditar(banco)) {
                            fecharModal(target);
                        } else {
                            target.add(feedbackPanel);
                        }
                    } else {
                        if (new ControllerPessoa().adicionarOuEditar(pessoa)) {
                            fecharModal(target);
                        } else {
                            target.add(feedbackPanel);
                        }
                    }
                } catch (Exception e) {
                    info(e);
                    target.add(feedbackPanel);
                }
            }

            @Override
            protected void onError(AjaxRequestTarget target) {
                target.add(feedbackPanel);
            }

        };
        submit.add(btnLabel);

        form.add(nome);
        form.add(cpf);
        form.add(email);
        form.add(telefone);
        form.add(cep);
        form.add(cidade);
        form.add(uf);
        form.add(endereco);
        form.add(rua);
        form.add(bairro);
        form.add(agenciaLabel);
        form.add(agencia);
        form.add(contaLabel);
        form.add(conta);
        form.add(new DropDownChoice("tipoConta", tipo));
        form.add(senha);
        form.add(submit);

        bodyMarkup.add(form);
        add(bodyMarkup);

    }

    public void gerarCampos() {

        bodyMarkup = new WebMarkupContainer("bodyMarkup");

        feedbackPanel = new FeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);
        
        formUpload = new Form("formUpload");
        formUpload.setOutputMarkupId(true);
        formUpload.setMultiPart(true);

        fileUpload = new FileUploadField("fileUpload");

        nome = new TextField("nome");
        nome.setRequired(true);
        
        cpf = new TextField("cpf");
        cpf.add(StringValidator.maximumLength(11));
        cpf.setRequired(true);
        
        telefone = new TextField("telefone");
        //telefone.add(new ValidatorConta("T"));
        
        email = new EmailTextField("email");
        email.setRequired(true);

        endereco = new TextField("endereco");
        endereco.setRequired(true);
        
        rua = new NumberTextField("rua", new PropertyModel(pessoa, "numero"));
        rua.setMinimum(1);
        rua.setRequired(true);
        
        bairro = new TextField("bairro");
        bairro.setRequired(true);

        cidade = new TextField("cidade");
        cidade.setRequired(true);

        cep = new TextField("cep");
        cep.add(StringValidator.maximumLength(8));
        cep.setRequired(true);

        uf = new TextField("uf");
        uf.add(StringValidator.maximumLength(2));
        uf.setRequired(true);

        agenciaLabel = new Label("agenciaLabel", Model.of("Agencia"));
        agencia = new TextField("agencia", new PropertyModel(banco, "agencia"));
        agencia.add(new ValidatorConta("A"));

        contaLabel = new Label("contaLabel", Model.of("Conta"));
        conta = new TextField("conta", new PropertyModel(banco, "conta"));
        conta.add(new ValidatorConta("C"));
        
        senha = new PasswordTextField("senha");
        senha.setRequired(true);

    }

    public void fecharModal(AjaxRequestTarget target) {
    }

}