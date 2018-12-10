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
package com.banco.model;

import com.banco.model.dao.PessoaDAOImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author NOTEDESENVSP1
 */
@Entity
@Table(name = "pessoa")
public class Pessoa extends PessoaDAOImpl implements Serializable {

    @OneToMany(mappedBy = "pessoa",
            orphanRemoval = true,
            targetEntity = BancoBrasil.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private List<BancoBrasil> contaL = new ArrayList<BancoBrasil>();

    public Pessoa() {
        super();
    }

    public Pessoa(Integer id, char tipoConta, String nome, String cpf, String cep, String endereco, int numero, 
            String bairro, String cidade, String uf, String email, int telefone, String senha) {
        
        super(id, tipoConta, nome, cpf, cep, endereco, numero, 
                bairro, cidade, uf, email, telefone, senha);

    }

    @Override
    public String toString() {
        return "\n ID: " + getId() + " Tipo: " + getTipoConta() + " Nome: " + getNome() + " Senha: " + getSenha() + " Cpf: " + getCpf() +  " Cep: " + getCep() + " email: " + getEmail() + " tel: " + getTelefone() + 
               "\n Conta: " + getContaL();
    }
    
    /**
     * @return the contaL
     */
    public List<BancoBrasil> getContaL() {
        return contaL;
    }

    /**
     * @param contaL the contaL to set
     */
    public void setContaL(List<BancoBrasil> contaL) {
        this.contaL = contaL;
    }
    
}
