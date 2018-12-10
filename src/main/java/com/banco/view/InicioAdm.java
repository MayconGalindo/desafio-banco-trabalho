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
package com.banco.view;

import com.banco.model.Pessoa;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

/**
 *
 * @author NOTEDESENVSP1
 */
public final class InicioAdm extends WebPage {

    WebMarkupContainer markup;
    Label lbl; 
    
    public InicioAdm(Pessoa pessoa) {
        
        super();
        
        markup = new WebMarkupContainer("markup");
        lbl =  new Label("lbl", Model.of(pessoa.getNome()));
        
        markup.add(lbl);
        
        add(markup);
        
    }
    
    public InicioAdm(PageParameters params) {
        
    }
}
