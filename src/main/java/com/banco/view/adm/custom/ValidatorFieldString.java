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

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

/**
 *
 * @author NOTEDESENVSP1
 */
public class ValidatorFieldString implements IValidator<String> {

    String validator;

    public ValidatorFieldString(String validator) {
        this.validator = validator;
    }

    @Override
    public void validate(IValidatable<String> validatable) {
        final String valor = validatable.getValue();
        final boolean match = valor.matches("[0-9]+");
        switch (validator) {
            case "Ag":
                if (match == false) error(validatable, "A agencia deve conter apenas numeros");
                break;
            case "Co":
                if (match == false) error(validatable, "A conta deve conter apenas numeros");
                break;
            case "Ce":
                if (match == false) error(validatable, "O cep deve conter apenas numeros");
                break;
            case "Ru":
                if (match == false) error(validatable, "A rua deve conter apenas numeros");
                break;
        }
    }

    private void error(IValidatable<String> validatable, String errorMessage) {
        ValidationError error = new ValidationError();
        error.setMessage(errorMessage);
        validatable.error(error);
    }

}
