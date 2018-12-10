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
public abstract class ContaDAOImpl implements ContaDAO, Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "agencia", length = 5, nullable = false)
    private int agencia;

    @Column(name = "conta", unique = true, length = 6, nullable = false)
    private int conta;

    @Column(name = "poupanca")
    private double poupanca;

    @Column(name = "corrente")
    private double corrente;

    @Column(name = "estadoConta")
    private boolean estadoConta;

    public ContaDAOImpl() {
    }

    public ContaDAOImpl(Integer id, int agencia, int conta, double poupanca, double corrente, boolean estadoConta) {
        this.id = id;
        this.agencia = agencia;
        this.conta = conta;
        this.poupanca = poupanca;
        this.corrente = corrente;
        this.estadoConta = estadoConta;
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
    public int getAgencia() {
        return agencia;
    }

    /**
     * @param agencia the agencia to set
     */
    @Override
    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }

    /**
     * @return the conta
     */
    @Override
    public int getConta() {
        return conta;
    }

    /**
     * @param conta the conta to set
     */
    @Override
    public void setConta(int conta) {
        this.conta = conta;
    }

    /**
     * @return the poupanca
     */
    @Override
    public double getValorPoupanca() {
        return poupanca;
    }

    /**
     * @param poupanca the poupanca to set
     */
    @Override
    public void setValorPoupanca(double poupanca) {
        this.poupanca = poupanca;
    }

    /**
     * @return the corrente
     */
    @Override
    public double getValorCorrente() {
        return corrente;
    }

    /**
     * @param corrente the corrente to set
     */
    @Override
    public void setValorCorrente(double corrente) {
        this.corrente = corrente;
    }

    /**
     * @return the estadoConta
     */
    @Override
    public boolean isEstadoConta() {
        return estadoConta;
    }

    /**
     * @param estadoConta the estadoConta to set
     */
    @Override
    public void setEstadoConta(boolean estadoConta) {
        this.estadoConta = estadoConta;
    }

}
