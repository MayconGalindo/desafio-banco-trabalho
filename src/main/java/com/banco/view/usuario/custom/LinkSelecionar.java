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
package com.banco.view.usuario.custom;

import com.banco.model.Pessoa;
import com.banco.view.usuario.Acao;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;

/**
 *
 * @author NOTEDESENVSP1
 */
public class LinkSelecionar extends Panel {

    WebMarkupContainer markup;
    ModalWindow acao;

    public LinkSelecionar(String id, Pessoa pessoa, String func, String tipo, Integer idConta) {

        super(id);

        markup = new WebMarkupContainer("bodyMarkup");

        acao = new ModalWindow("acao");
        acao.setTitle("Preencha os campos");
        markup.add(acao);

        markup.add(new AjaxLink("selecionar") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                fecharJanela(target);
                acao.setContent(new Acao(acao.getContentId(), pessoa, func, tipo, idConta));
                acao.show(target);
            }

        });

        add(markup);
        
    }
    
    public void fecharJanela(AjaxRequestTarget target){
    }
    
}