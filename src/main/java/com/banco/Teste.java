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
package com.banco;

import com.banco.controller.ControllerBanco;
import com.banco.controller.ControllerPessoa;
import com.banco.model.BancoBrasil;
import com.banco.model.Pessoa;
import java.text.ParseException;

/**
 *
 * @author NOTEDESENVSP1
 */
public class Teste {

    public static void main(String[] args) throws ParseException {

        /*System.out.println(new ControllerPessoa().listar());
        
        Pessoa pessoa = new ControllerPessoa().listar().get(0);
        new ControllerBanco().depositar(1000, pessoa, true, 0);
        new ControllerBanco().depositar(1000, pessoa, false, 0);
        pessoa = new ControllerPessoa().listar().get(0);
        new ControllerBanco().transferirContaDiferente(pessoa, true, 0, 67010, 401523, 100);*/
        
        System.out.println(new ControllerBanco().contasPessoa(1));

    }

}
