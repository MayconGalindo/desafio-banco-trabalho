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
package com.banco.view.usuario.validator;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

/**
 *
 * @author NOTEDESENVSP1
 */
public class ValorValidator implements IValidator<Double> {

    @Override
    public void validate(IValidatable<Double> validatable) {
        
        final double valor = validatable.getValue();
        
        if (valor != 0) {
            error(validatable, "Valor deve ser maior que zero");
        }
        
    }

    private void error(IValidatable<Double> validatable, String errorKey) {
        ValidationError error = new ValidationError();
        error.setMessage(errorKey);
        validatable.error(error);
    }

}
