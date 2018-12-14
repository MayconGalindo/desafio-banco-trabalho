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
import com.banco.model.Pessoa;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
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
    String label;
    
    public Delete(String id, int idAlvo, boolean pessoaOuBanco) {
        
        super(id);
    
        bodyMarkup = new WebMarkupContainer("bodyMarkup");
        
        if (pessoaOuBanco) {
            label = "Deseja excluir o Id: " + idAlvo;
        } else {
            label = "Deseja desativar a conta: " + idAlvo;
        }
        
        bodyMarkup.add(new Label("btnLabel", Model.of(label)));
        bodyMarkup.add(new AjaxButton("excluir") {
        
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                if (pessoaOuBanco) new ControllerPessoa().excluir(idAlvo);
                else new ControllerBanco().excluir(idAlvo);
                
                fecharModal(target);
            }
        
        });
        add(bodyMarkup);
    }
    
    public void fecharModal(AjaxRequestTarget target){
    }
     
}