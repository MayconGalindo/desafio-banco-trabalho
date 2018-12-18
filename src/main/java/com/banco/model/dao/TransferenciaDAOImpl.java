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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
public abstract class TransferenciaDAOImpl implements TransferenciaDAO, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "cpfRemetente", length = 11, nullable = false)
    private String cpfRemetente;

    @Column(name = "tipoTranferencia", nullable = false)
    private String tipoTranferencia;

    @Column(name = "valor", nullable = false)
    private Double valor;

    @Column(name = "cpfDestinatario", length = 11, nullable = false)
    private String cpfDestinatario;

    @Column(name = "dataTransf", length = 19, nullable = false)
    private String dataTransf;

    public TransferenciaDAOImpl(String cpfRemetente, String tipoTranferencia, Double valor, String cpfDestinatario) {
        this.id = null;
        this.cpfRemetente = cpfRemetente;
        this.tipoTranferencia = tipoTranferencia;
        this.valor = valor;
        this.cpfDestinatario = cpfDestinatario;
        this.dataTransf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(LocalDateTime.now());
    }

    public TransferenciaDAOImpl() {
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
     * @return the cpfRemetente
     */
    @Override
    public String getCpfRemetente() {
        return cpfRemetente;
    }

    /**
     * @param cpfRemetente the cpfRemetente to set
     */
    @Override
    public void setCpfRemetente(String cpfRemetente) {
        this.cpfRemetente = cpfRemetente;
    }

    /**
     * @return the tipoTranferencia
     */
    @Override
    public String getTipoTranferencia() {
        return tipoTranferencia;
    }

    /**
     * @param tipoTranferencia the tipoTranferencia to set
     */
    @Override
    public void setTipoTranferencia(String tipoTranferencia) {
        this.tipoTranferencia = tipoTranferencia;
    }

    /**
     * @return the valor
     */
    @Override
    public Double getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    @Override
    public void setValor(Double valor) {
        this.valor = valor;
    }

    /**
     * @return the cpfDestinatario
     */
    @Override
    public String getCpfDestinatario() {
        return cpfDestinatario;
    }

    /**
     * @param cpfDestinatario the cpfDestinatario to set
     */
    @Override
    public void setCpfDestinatario(String cpfDestinatario) {
        this.cpfDestinatario = cpfDestinatario;
    }

    /**
     * @return the dataTransf
     */
    @Override
    public String getDataTransf() {
        return dataTransf;
    }

    /**
     * @param dataTransf the dataTransf to set
     */
    @Override
    public void setDataTransf(String dataTransf) {
        this.dataTransf = dataTransf;
    }

}
