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
package com.banco.model.dao;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author NOTEDESENVSP1
 */
@MappedSuperclass
public abstract class ContatoDAOImpl implements ContaDAO, Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "agencia", length = 5, nullable = false)
    private Integer agencia;

    @Column(name = "conta", unique = true, length = 6, nullable = false)
    private Integer conta;

    public ContatoDAOImpl() {
    }

    public ContatoDAOImpl(Integer agencia, Integer conta) {
        this.id = null;
        this.agencia = agencia;
        this.conta = conta;
    }

    /**
     * @return the id
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the agencia
     */
    @Override
    public Integer getAgencia() {
        return agencia;
    }

    /**
     * @param agencia the agencia to set
     */
    @Override
    public void setAgencia(Integer agencia) {
        this.agencia = agencia;
    }

    /**
     * @return the conta
     */
    @Override
    public Integer getConta() {
        return conta;
    }

    /**
     * @param conta the conta to set
     */
    @Override
    public void setConta(Integer conta) {
        this.conta = conta;
    }
    
}
