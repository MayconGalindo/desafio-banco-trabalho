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
public abstract class PessoaDAOImpl implements PessoaDAO, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "tipo_conta", nullable = false)
    private char tipoConta;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cpf", unique = true, length = 11, nullable = false)
    private String cpf;

    @Column(name = "cep", length = 8)
    private String cep;

    @Column(name = "endereco", nullable = false)
    private String endereco;

    @Column(name = "numero", nullable = false)
    private int numero;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "uf", length = 2)
    private String uf;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "telefone", length = 9)
    private int telefone;

    @Column(name = "senha", nullable = false)
    private String senha;

    public PessoaDAOImpl() {
    }

    public PessoaDAOImpl(Integer id, char tipoConta, String nome, String cpf, String cep, String endereco, 
            int numero, String bairro, String cidade, String uf, String email, int telefone, String senha) {
        
        this.id = id;
        this.tipoConta = tipoConta;
        this.nome = nome;
        this.cpf = cpf;
        this.cep = cep;
        this.endereco = endereco;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
        
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
     * @return the tipoConta
     */
    @Override
    public char getTipoConta() {
        return tipoConta;
    }

    /**
     * @param tipoConta the tipoConta to set
     */
    @Override
    public void setTipoConta(char tipoConta) {
        this.tipoConta = tipoConta;
    }

    /**
     * @return the nome
     */
    @Override
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the cpf
     */
    @Override
    public String getCpf() {
        return cpf;
    }

    /**
     * @param cpf the cpf to set
     */
    @Override
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * @return the cep
     */
    @Override
    public String getCep() {
        return cep;
    }

    /**
     * @param cep the cep to set
     */
    @Override
    public void setCep(String cep) {
        this.cep = cep;
    }

    /**
     * @return the endereco
     */
    @Override
    public String getEndereco() {
        return endereco;
    }

    /**
     * @param endereco the endereco to set
     */
    @Override
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * @return the numero
     */
    @Override
    public int getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    @Override
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * @return the bairro
     */
    @Override
    public String getBairro() {
        return bairro;
    }

    /**
     * @param bairro the bairro to set
     */
    @Override
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    /**
     * @return the cidade
     */
    @Override
    public String getCidade() {
        return cidade;
    }

    /**
     * @param cidade the cidade to set
     */
    @Override
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    /**
     * @return the uf
     */
    @Override
    public String getUf() {
        return uf;
    }

    /**
     * @param uf the uf to set
     */
    @Override
    public void setUf(String uf) {
        this.uf = uf;
    }

    /**
     * @return the email
     */
    @Override
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the telefone
     */
    @Override
    public int getTelefone() {
        return telefone;
    }

    /**
     * @param telefone the telefone to set
     */
    @Override
    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    /**
     * @return the senha
     */
    @Override
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    @Override
    public void setSenha(String senha) {
        this.senha = senha;
    }

    

}
