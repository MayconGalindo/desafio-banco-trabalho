/*
 * Copyright 2019 NOTEDESENVSP1.
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
package com.banco.view.adm.custom;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;

/**
 *
 * @author NOTEDESENVSP1
 */
public class SearchTextField<T> extends TextField<T> {

    private final String placeholder;
    
    public SearchTextField(String id, IModel<T> model, String placeHolder) {
        super(id, model);
        this.placeholder = placeHolder;
    }

    @Override
    protected void onComponentTag(final ComponentTag tag) {
        tag.put("placeholder", this.placeholder);
        super.onComponentTag(tag);
    }

}
