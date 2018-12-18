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
public interface TransferenciaDAO {
    
    public Integer getId();

    public void setId(Integer id);
    
    public String getCpfRemetente();

    public void setCpfRemetente(String cpfRemetente);

    public String getTipoTranferencia();

    public void setTipoTranferencia(String tipoTranferencia);
    
    public Double getValor();

    public void setValor(Double valor);
    
    public String getCpfDestinatario();

    public void setCpfDestinatario(String cpfDestinatario);
    
    public String getDataTransf();
    
    public void setDataTransf(String dataTransf);
    
}
