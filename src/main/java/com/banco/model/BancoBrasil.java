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

import com.banco.model.dao.ContaDAOImpl;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author NOTEDESENVSP1
 */
@Entity
@Table(name = "banco_brasil")
public class BancoBrasil extends ContaDAOImpl implements Serializable {

    @ManyToOne
    @JoinColumn(name = "pessoa_id", nullable = true)
    private Pessoa pessoa;

    public BancoBrasil() {
        super();
    }

    public BancoBrasil(Integer id, int agencia, int conta, double poupanca, double corrente, boolean estadoConta) {
        super(id, agencia, conta, poupanca, corrente, estadoConta);
    }
    
    @Override
    public String toString() {
        return " ID: " + getId() + " Agencia: " + getAgencia() + " Conta: " + getConta() + " Poupança: " + getValorPoupanca() + " Corrente: " + getValorCorrente() + ", ";
    }

    /**
     * @return the pessoa
     */
    public Pessoa getPessoa() {
        return pessoa;
    }

    /**
     * @param pessoaId
     */
    public void setPessoa(Pessoa pessoaId) {
        this.pessoa = pessoaId;
    }
    
    

}
