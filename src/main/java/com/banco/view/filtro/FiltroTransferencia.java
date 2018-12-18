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
package com.banco.view.filtro;

import com.banco.controller.ControllerPessoa;
import com.banco.model.Transferencia;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;

/**
 *
 * @author NOTEDESENVSP1
 */
public class FiltroTransferencia extends Panel {
    
    WebMarkupContainer bodyMarkup;
    Form form;
    LoadableDetachableModel detachableModel;
    Transferencia transferencia;
    List<Double> valor = new ArrayList<>();
    List<String> tipo = new ArrayList<>();
    List<Transferencia> transferencias;

    public FiltroTransferencia(String id) throws ParseException {
        
        super(id);
        
        tipo.add("Transferecia Conta Diferente(Corrente)"); tipo.add("Transferecia Conta Diferente(Poupança)");
        tipo.add("Transferecia Mesma Conta(Poupança para Corrente)"); tipo.add("Transferecia Mesma Conta(Corrente para Poupança)");
        tipo.add("Desposito(Corrente)"); tipo.add("Desposito(Poupança)");
        tipo.add("Saque(Corrente)"); tipo.add("Saque(Poupança)");
        tipo.add("*");
        
        double val = 0;
        for (int i = 0; i < 10; i++) {
            valor.add(val);
            val = val + 5000;
        }
        
        transferencia = new Transferencia("*", "*", 0.0, "*");
        
        bodyMarkup = new WebMarkupContainer("bodyMarkup");
        bodyMarkup.setOutputMarkupId(true);
        
        detachableModel = new LoadableDetachableModel<List<String>>() {
            @Override
            protected List<String> load() {
                List<String> list = new ControllerPessoa().listarCpf();
                list.add("*");
                return list;
            }
        };
        
        form = new Form("form", new CompoundPropertyModel<>(transferencia));
        
        form.add(new DropDownChoice("cpfRemetente", detachableModel));
        form.add(new DropDownChoice("cpfDestinatario", detachableModel));
        form.add(new DropDownChoice("tipoTranferencia", tipo));
        form.add(new DropDownChoice("valor", valor));
        
        form.add(new AjaxButton("submit", form) {
           
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                transferencias = new ControllerPessoa().filtrarTransferencia(transferencia);
                atualizarLista(target, transferencias);
            }

            @Override
            protected void onError(AjaxRequestTarget target) {
                super.onError(target); //To change body of generated methods, choose Tools | Templates.
            }
           
        });
        
        bodyMarkup.add(form);
        add(bodyMarkup);
        
    }
    
    public void atualizarLista(AjaxRequestTarget target, List<Transferencia> lista){
    }
    
}
