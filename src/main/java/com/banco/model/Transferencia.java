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

import com.banco.model.dao.TransferenciaDAOImpl;
import java.io.Serializable;
import java.text.ParseException;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author NOTEDESENVSP1
 */
@Entity
@Table(name = "transferencia")
public class Transferencia extends TransferenciaDAOImpl implements Serializable{

    public Transferencia(String cpfRemetente, String tipoTranferencia, Double valor, String cpfDestinatario) throws ParseException {
        super(cpfRemetente, tipoTranferencia, valor, cpfDestinatario);
    }
    
    public Transferencia() {
        super();
    }

    @Override
    public String toString() {
        return "Remetente: " + getCpfRemetente() + " Destinatario: " + getCpfDestinatario();
    }
    
}
