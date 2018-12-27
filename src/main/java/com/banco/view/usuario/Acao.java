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

import com.banco.controller.ControllerBanco;
import com.banco.controller.ControllerPessoa;
import com.banco.model.BancoBrasil;
import com.banco.model.Contato;
import com.banco.model.Pessoa;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.util.string.Strings;

/**
 *
 * @author NOTEDESENVSP1
 */
public final class Acao extends Panel {

    IRequestablePage page;
    WebMarkupContainer markup;
    FeedbackPanel feedback;
    Form form;
    ModalWindow aviso;

    Label lblTed;
    Label lblBanco;
    Label lblAgencia;
    Label lblConta;

    NumberTextField banco;
    AjaxCheckBox checkBoxTed;
    AutoCompleteTextField<Integer> agencia;
    AutoCompleteTextField<Integer> contaTransf;
    NumberTextField inp;
    AjaxButton submit;

    List<Contato> contatos;
    ControllerBanco cb;
    ControllerPessoa cp;

    BancoBrasil valor;
    Pessoa pessoa;

    String mensagem;
    int numeroBanco;
    boolean ted = false;

    /**
     *
     * @param id
     * @param sessao
     * @param funcao
     * @param tipo
     * @param conta
     */
    public Acao(String id, Pessoa sessao, String funcao, String tipo, int conta) {

        super(id);

        contatos = new ControllerPessoa().listarContato(sessao.getId());
        valor = new BancoBrasil();

        cb = new ControllerBanco();
        cp = new ControllerPessoa();

        markup = new WebMarkupContainer("bodyMarkup");
        markup.add(new FeedbackPanel("feedback"));

        aviso = new ModalWindow("aviso") {

        };
        aviso.setTitle("Aviso");
        markup.add(aviso);

        form = new Form("form", new CompoundPropertyModel<>(valor));

        lblTed = new Label("lblTed", Model.of("Ted(R$5,00) : "));
        checkBoxTed = new AjaxCheckBox("checkBoxTed", new PropertyModel<Boolean>(this, "ted")) {

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                if (banco.isEnabled()) {
                    banco.setEnabled(false);
                } else {
                    banco.setEnabled(true);
                }
                target.add(banco);
            }

        };
        checkBoxTed.setOutputMarkupId(true);

        lblBanco = new Label("lblBanco", Model.of("Banco: "));
        banco = new NumberTextField("banco", new PropertyModel<>(this, "numeroBanco"));
        banco.setMinimum(1);
        banco.setOutputMarkupId(true);
        banco.setEnabled(false);

        lblAgencia = new Label("lblAgencia", Model.of("Agencia: "));
        agencia = new AutoCompleteTextField("agencia") {

            @Override
            protected Iterator getChoices(String input) {

                if (Strings.isEmpty(input)) {
                    List<String> emptyList = Collections.emptyList();
                    return emptyList.iterator();
                }

                List<Integer> choices = new ArrayList<>(10);

                for (final Contato contato : contatos) {
                    final String agencia = contato.getAgencia().toString();

                    if (agencia.toUpperCase().startsWith(input.toUpperCase())) {
                        choices.add(contato.getAgencia());
                        if (choices.size() == 10) {
                            break;
                        }
                    }
                }
                return choices.iterator();
            }

        };
        agencia.setOutputMarkupId(true);

        lblConta = new Label("lblConta", Model.of("Conta: "));
        contaTransf = new AutoCompleteTextField("conta") {

            @Override
            protected Iterator getChoices(String input) {

                if (Strings.isEmpty(input)) {
                    List<String> emptyList = Collections.emptyList();
                    return emptyList.iterator();
                }

                List<Integer> choices = new ArrayList<>(10);

                for (final Contato contato : contatos) {
                    final String conta = contato.getConta().toString();

                    if (conta.toUpperCase().startsWith(input.toUpperCase())) {
                        choices.add(contato.getConta());
                        if (choices.size() == 10) {
                            break;
                        }
                    }
                }
                return choices.iterator();
            }

        };
        contaTransf.setOutputMarkupId(true);

        if (funcao.equals("Dif")) {
            agencia.setRequired(true);
            contaTransf.setRequired(true);
        } else {
            lblTed.setVisible(false);
            checkBoxTed.setVisible(false);
            lblBanco.setVisible(false);
            banco.setVisible(false);
            lblAgencia.setVisible(false);
            agencia.setVisible(false);
            lblConta.setVisible(false);
            contaTransf.setVisible(false);
            agencia.setRequired(false);
            contaTransf.setRequired(false);
        }

        inp = new NumberTextField("corrente");
        inp.setMinimum(0.1);
        inp.setOutputMarkupId(true);

        form.add(lblTed);
        form.add(checkBoxTed);
        form.add(lblBanco);
        form.add(banco);

        form.add(inp);
        form.add(lblAgencia);
        form.add(agencia);

        form.add(lblConta);
        form.add(contaTransf);

        submit = new AjaxButton("submit", form) {

            @Override
            protected void onSubmit(AjaxRequestTarget target) {

                switch (funcao) {
                    case "Dif":
                        if (ted) {
                            mensagem = cb.transferirTed(sessao, tipo, conta, valor.getAgencia(), valor.getConta(), valor.getValorCorrente());
                        } else {
                            mensagem = cb.transferirContaDiferente(sessao, tipo, conta, valor.getAgencia(), valor.getConta(), valor.getValorCorrente());
                        }
                        break;

                    case "Mes":
                        mensagem = cb.transferirMesmaConta(valor.getValorCorrente(), sessao, tipo, conta);
                        break;

                    case "Dep":
                        mensagem = cb.depositar(valor.getValorCorrente(), sessao, tipo, conta);
                        break;

                    case "Saq":
                        mensagem = cb.saquar(valor.getValorCorrente(), sessao, tipo, conta);
                        break;
                }

                aviso.setContent(new Label(aviso.getContentId(), Model.of(mensagem + ", voltando ao inicio")));
                aviso.show(target);
                info(mensagem);

                try {
                    TimeUnit.SECONDS.sleep(5);
                    pessoa = cp.logar(sessao.getCpf(), sessao.getSenha());
                    page = new InicioUsuario(pessoa);
                    setResponsePage(page);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Acao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            protected void onError(AjaxRequestTarget target) {
            }

        };
        submit.setEnabled(false);
        submit.setOutputMarkupId(true);
        form.add(submit);

        form.add(new AjaxCheckBox("checkBoxSubmit", Model.of(false)) {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                if (submit.isEnabled()) {
                    submit.setEnabled(false);
                } else {
                    submit.setEnabled(true);
                }
                target.add(submit);
            }
        });

        markup.add(aviso);
        markup.add(form);
        add(markup);

    }
}