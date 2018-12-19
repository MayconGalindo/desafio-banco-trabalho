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
import com.banco.view.InicioUsuario;
import com.googlecode.wicket.jquery.ui.widget.dialog.MessageDialog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
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
    Label lblAgencia;
    Label lblConta;
    AutoCompleteTextField<Integer> agencia;
    AutoCompleteTextField<Integer> contaTransf;
    NumberTextField inp;
    MessageDialog aviso;
    
    List<Contato> contatos;
    ControllerBanco cb;
    ControllerPessoa cp;
    BancoBrasil valor;
    Pessoa pessoa;
    String mensagem;

    /**
     *
     * @param id
     * @param sessao
     * @param funcao
     * @param corOuPop true = Corrente, false = Poupança
     * @param conta
     */
    public Acao(String id, Pessoa sessao, String funcao, boolean corOuPop, int conta) {

        super(id);

        contatos = new ControllerPessoa().listarContato(sessao.getId());
        cb = new ControllerBanco();
        cp = new ControllerPessoa();
        valor = new BancoBrasil();
        markup = new WebMarkupContainer("bodyMarkup");
        markup.add(new FeedbackPanel("feedback"));

        form = new Form("form", new CompoundPropertyModel<>(valor));

        lblAgencia = new Label("lblAgencia", Model.of("Agencia: "));
        agencia = new AutoCompleteTextField("agencia"){
            
            @Override
            protected Iterator getChoices(String input) {
                
                if (Strings.isEmpty(input))
                {
                    List<String> emptyList = Collections.emptyList();
                    return emptyList.iterator();
                }

                List<Integer> choices = new ArrayList<>(10);

                for (final Contato contato : contatos)
                {
                    final String agencia = contato.getAgencia().toString();

                    if (agencia.toUpperCase().startsWith(input.toUpperCase()))
                    {
                        choices.add(contato.getAgencia());
                        if (choices.size() == 10)
                        {
                            break;
                        }
                    }
                }

                return choices.iterator();
            }
            
        };
        agencia.setOutputMarkupId(true);
        lblConta = new Label("lblConta", Model.of("Conta: "));
        contaTransf = new AutoCompleteTextField("conta"){
            
            @Override
            protected Iterator getChoices(String input) {
                
                if (Strings.isEmpty(input))
                {
                    List<String> emptyList = Collections.emptyList();
                    return emptyList.iterator();
                }

                List<Integer> choices = new ArrayList<>(10);

                for (final Contato contato : contatos)
                {
                    final String conta = contato.getConta().toString();

                    if (conta.toUpperCase().startsWith(input.toUpperCase()))
                    {
                        choices.add(contato.getConta());
                        if (choices.size() == 10)
                        {
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

        form.add(inp);
        form.add(lblAgencia);
        form.add(lblConta);
        form.add(agencia);
        form.add(contaTransf);
        form.add(new AjaxButton("submit", form) {

            @Override
            protected void onSubmit(AjaxRequestTarget target) {

                System.out.println("Teste OK");

                switch (funcao) {

                    case "Dif":
                        mensagem = cb.transferirContaDiferente(sessao, corOuPop, conta, valor.getAgencia(), valor.getConta(), valor.getValorCorrente());
                        break;

                    case "Mes":
                        mensagem = cb.transferirMesmaConta(valor.getValorCorrente(), sessao, corOuPop, conta);
                        break;

                    case "Dep":
                        mensagem = cb.depositar(valor.getValorCorrente(), sessao, corOuPop, conta);
                        break;

                    case "Saq":
                        mensagem = cb.saquar(valor.getValorCorrente(), sessao, corOuPop, conta);
                        break;

                }

                info(mensagem);
                pessoa = cp.logar(sessao.getCpf(), sessao.getSenha());
                page = new InicioUsuario(pessoa);
                setResponsePage(page);

            }

            @Override
            protected void onError(AjaxRequestTarget target) {
                System.out.println("Teste Erro");
            }

        });

        markup.add(form);
        add(markup);

    }
}
