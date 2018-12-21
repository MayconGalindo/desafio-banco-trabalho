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
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

/**
 *
 * @author NOTEDESENVSP1
 */
public class Delete extends Panel {

    WebMarkupContainer bodyMarkup;
    AjaxLink link;
    String label, lblLink;

    public Delete(String id, int idAlvo, boolean pessoaOuBanco, boolean desOuExc, boolean ativDesa) {

        super(id);

        if (pessoaOuBanco) {
            label = "Deseja excluir a pessoa: " + idAlvo;
            lblLink = "Excluir";
        } else {
            if (desOuExc) {
                if (ativDesa) {
                    label = "Deseja desativar a conta: " + idAlvo;
                    lblLink = "Desativar";
                } else {
                    label = "Deseja Ativar a conta: " + idAlvo;
                    lblLink = "Ativar";
                }
            } else {
                label = "Deseja excluir a conta: " + idAlvo;
                lblLink = "Excluir";
            }
        }

        bodyMarkup = new WebMarkupContainer("bodyMarkup");

        link = new AjaxLink("excluir") {

            @Override
            public void onClick(AjaxRequestTarget target) {
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
        link.add(new Label("label", Model.of(lblLink)));

        bodyMarkup.add(new Label("funcLabel", Model.of(label)));
        bodyMarkup.add(link);

        add(bodyMarkup);
    }

    public void fecharModal(AjaxRequestTarget target) {
    }

}