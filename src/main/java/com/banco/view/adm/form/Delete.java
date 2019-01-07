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
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

/**
 *
 * @author NOTEDESENVSP1
 */
public class Delete extends Panel {

    WebMarkupContainer bodyMarkup;
    Form form;
    AjaxButton submit;
    String label, lblLink;

    public Delete(String id, int idAlvo, boolean pessoaOuBanco, boolean desOuExc, boolean ativDesa, String nome) {

        super(id);

        if (pessoaOuBanco) {
            label = "Deseja excluir: " + nome;
            lblLink = "Excluir";
        } else {
            if (desOuExc) {
                if (ativDesa) {
                    label = "Deseja desativar a conta: " + idAlvo;
                    lblLink = "Desativar";
                } else {
                    label = "Deseja ativar a conta: " + idAlvo;
                    lblLink = "Ativar";
                }
            } else {
                label = "Deseja excluir a conta: " + idAlvo;
                lblLink = "Excluir";
            }
        }

        bodyMarkup = new WebMarkupContainer("bodyMarkup");

        form = new Form("form");
        submit = new AjaxButton("excluir", Model.of(lblLink), form) {
            
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                
                 if (pessoaOuBanco) {
                    new ControllerPessoa().excluir(idAlvo);
                } else {
                    if (desOuExc) {
                        new ControllerBanco().ativarOuDesativar(idAlvo);
                    } else {
                        new ControllerBanco().excluir(idAlvo);
                    }
                }
                fecharModal(target);
            }
        };
        
        form.add(submit);

        bodyMarkup.add(new Label("funcLabel", Model.of(label)));
        bodyMarkup.add(form);
        
        add(bodyMarkup);
    }

    public void fecharModal(AjaxRequestTarget target) {
    }

}