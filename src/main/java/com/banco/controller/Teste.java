package com.banco.controller;

import com.banco.model.BancoBrasil;
import com.banco.model.Pessoa;

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
/**
 *
 * @author NOTEDESENVSP1
 */
public class Teste {

    public static void main(String[] args) {

        String nome = "Maycon", cep = "06405070", endereco = "Rua Mar das Filipinas ", bairro = "Parque Ribeiro de Lima",
                cidade = "Barueri", uf = "SP", email = "maycongalindo@hotmail.com", senha = "abc", cpf = "46132749829";
        Integer idp = 1, numero = 152, telefone = 958755058;
        char tipo = 'U';
        /*
        Pessoa p = new Pessoa(idp, tipo, nome, cpf, cep, endereco, numero, bairro, cidade, uf, email, telefone, senha);
        
        Integer idc = 1, agencia = 67202, conta = 366625;
        boolean b = true;
        
        BancoBrasil bc = new BancoBrasil(idc, agencia, conta, 0, 0, b);
        
        bc.setPessoa(p);
        p.getContaL().add(bc);*/

        ControllerPessoa cp = new ControllerPessoa();
        Pessoa p = cp.logar(cpf, senha);

        System.out.println("--------------------- " + new ControllerBanco().transferirContaDiferente(p, true, 0, 67010, 401523, 50.50));
        System.out.println(new ControllerPessoa().listar());
    
        //new ControllerBanco().depositar(300, p, false, 0);
    }

}
