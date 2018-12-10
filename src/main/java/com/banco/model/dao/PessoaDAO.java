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

/**
 *
 * @author NOTEDESENVSP1
 */
public interface PessoaDAO {
    
    /** Exemplo básico de um comentário em JavaDoc
     * @return  */
    public Integer getId();

    public void setId(Integer id);
    
    public char getTipoConta();

    public void setTipoConta(char tipo);

    public String getNome();

    public void setNome(String nome);
    
    public String getCpf();

    public void setCpf(String cpf);
    
    public String getCep();

    public void setCep(String cep);
    
    public String getEndereco();

    public void setEndereco(String endereco);
    
    public int getNumero();

    public void setNumero(int numero);
    
    public String getBairro();

    public void setBairro(String bairro);
    
    public String getCidade();

    public void setCidade(String cidade);
    
    public String getUf();

    public void setUf(String uf);
    
    public String getEmail();

    public void setEmail(String email);
    
    public int getTelefone();

    public void setTelefone(int telefone);
    
    public String getSenha();

    public void setSenha(String senha);
    
}
