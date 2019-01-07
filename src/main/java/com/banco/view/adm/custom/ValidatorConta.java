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
public class ValidatorConta implements IValidator<String> {

    String validator;

    public ValidatorConta(String validator) {
        this.validator = validator;
    }

    @Override
    public void validate(IValidatable<String> validatable) {
        try {
            final String valor = validatable.getValue();
            boolean match = valor.matches("[0-9]+");
            System.out.println(match);
            switch (validator) {
                case "A":
                    if (valor.length() != 5 && match == false) error(validatable, "A agencia deve conter 5 digitos");
                    break;
                case "C":
                    if (valor.length() != 6 && match == false) error(validatable, "A conta deve conter 6 digitos");
                    break;
                case "T":
                    if (valor.length() != 9 && match == false) error(validatable, "Telefone invalido");
                    break;
            }
            } catch (Exception e) {
                System.out.println(e);
            }
    }

    private void error(IValidatable<String> validatable, String errorKey) {
        ValidationError error = new ValidationError();
        error.setMessage(errorKey);
        validatable.error(error);
    }

}
