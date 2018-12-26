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
package com.banco;

import com.banco.controller.relatorio.Relatorio;
import com.banco.model.Transferencia;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author NOTEDESENVSP1
 */
public class Teste {
    
    public static void main(String[] args) {
        
        try {
            
            List<Transferencia> transferencias = new ArrayList<>();
            
            Transferencia a = new Transferencia("1", "1", 1.0, "1");
            Transferencia b = new Transferencia("2", "2", 2.0, "2");
            
            transferencias.add(a);
            transferencias.add(b);
            
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(transferencias);
            Map<String, Object> map = new HashMap();
            map.put("DataSource", dataSource);

            String path = "RelatorioPdfTransferencia.jrxml";
            
            InputStream arquivo = Relatorio.class.getResourceAsStream(path);
            JasperReport report = (JasperReport) JasperCompileManager.compileReport(arquivo);
            JasperPrint print = JasperFillManager.fillReport(report, map, dataSource);

            String arq = "Relatorio Transferencia.pdf";
            File pdf = new File(arq);
            FileOutputStream output = new FileOutputStream(pdf);

            JasperExportManager.exportReportToPdfStream(print, output);

        } catch (JRException | IOException | ParseException e) {
            System.out.println(e);
        }
        
    }
    
}
