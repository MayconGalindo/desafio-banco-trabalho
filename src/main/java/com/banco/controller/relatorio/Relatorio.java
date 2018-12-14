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
package com.banco.controller.relatorio;

import com.banco.controller.ControllerBanco;
import com.banco.controller.ControllerPessoa;
import com.banco.model.BancoBrasil;
import com.banco.model.Pessoa;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author NOTEDESENVSP1
 */
public class Relatorio implements Serializable {

    public byte[] gerarExcelPessoa(Integer id) {

        try {

            HSSFWorkbook work = new HSSFWorkbook();
            HSSFSheet sheet = work.createSheet("Relatorio Cliente");

            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("Id");
            row.createCell(1).setCellValue("Nome");
            row.createCell(2).setCellValue("CPF");
            row.createCell(3).setCellValue("Email");
            row.createCell(4).setCellValue("Telefone");
            row.createCell(5).setCellValue("Cep");
            row.createCell(6).setCellValue("Endereço");
            row.createCell(7).setCellValue("Bairro");
            row.createCell(8).setCellValue("Cidade");
            row.createCell(9).setCellValue("UF");
            row.createCell(10).setCellValue("Tipo Conta");

            Pessoa pessoa = new ControllerPessoa().procurarId(id);

            row = sheet.createRow(1);

            row.createCell(0).setCellValue(pessoa.getId());
            row.createCell(1).setCellValue(pessoa.getNome());
            row.createCell(2).setCellValue(pessoa.getCpf());
            row.createCell(3).setCellValue(pessoa.getEmail());
            row.createCell(4).setCellValue(pessoa.getTelefone());
            row.createCell(5).setCellValue(pessoa.getCep());
            row.createCell(6).setCellValue(pessoa.getEndereco() + ", " + pessoa.getNumero());
            row.createCell(7).setCellValue(pessoa.getBairro());
            row.createCell(8).setCellValue(pessoa.getCidade());
            row.createCell(9).setCellValue(pessoa.getUf());
            row.createCell(10).setCellValue(Character.toString(pessoa.getTipoConta()));

            for (int i = 0; i < row.getHeight(); i++) {
                sheet.autoSizeColumn(i);
            }

            String file = "Relatorio ID " + id + ".xls";
            File excel = new File(file);
            FileOutputStream out = new FileOutputStream(excel);
            work.write(out);
            return Files.readAllBytes(excel.toPath());

        } catch (IOException e) {
            System.out.println(e);
            return null;
        }

    }

    public byte[] gerarPdfPessoa(Integer id) {

        Map<String, Object> map = new HashMap<>();
        Pessoa pessoa = new ControllerPessoa().procurarId(id);

        map.put("id", pessoa.getId());
        map.put("nome", pessoa.getNome());
        map.put("cpf", pessoa.getCpf());
        map.put("tipo_conta", pessoa.getTipoConta());
        map.put("email", pessoa.getEmail());
        map.put("telefone", pessoa.getTelefone());
        map.put("cep", pessoa.getCep());
        map.put("endereco", pessoa.getEndereco());
        map.put("numero", pessoa.getNumero());
        map.put("bairro", pessoa.getBairro());
        map.put("cidade", pessoa.getCidade());
        map.put("uf", pessoa.getUf());

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wicket?zeroDateTimeBehavior=convertToNull", "root", "");
            String path = "RelatorioPdfPessoa.jrxml";

            InputStream arquivo = Relatorio.class.getResourceAsStream(path);
            JasperReport report = (JasperReport) JasperCompileManager.compileReport(arquivo);
            JasperPrint print = JasperFillManager.fillReport(report, map, con);

            String arq = "Relatorio ID " + id + ".pdf";
            File pdf = new File(arq);
            FileOutputStream output = new FileOutputStream(pdf);

            JasperExportManager.exportReportToPdfStream(print, output);

            return Files.readAllBytes(pdf.toPath());

        } catch (JRException | IOException | ClassNotFoundException | SQLException e) {
            System.out.println(e);
            return null;
        }

    }
    
    public byte[] gerarExcelConta(Integer id) {

        try {

            HSSFWorkbook work = new HSSFWorkbook();
            HSSFSheet sheet = work.createSheet("Relatorio Conta");

            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("Id");
            row.createCell(1).setCellValue("Agencia");
            row.createCell(2).setCellValue("Conta");
            row.createCell(3).setCellValue("Ativado");
            row.createCell(4).setCellValue("Dono");

            BancoBrasil banco = new ControllerBanco().procurarId(id);

            row = sheet.createRow(1);

            row.createCell(0).setCellValue(banco.getId());
            row.createCell(1).setCellValue(banco.getConta());
            row.createCell(2).setCellValue(banco.getAgencia());
            row.createCell(3).setCellValue(banco.isEstadoConta());
            row.createCell(4).setCellValue(banco.getPessoa().getCpf());

            for (int i = 0; i < row.getHeight(); i++) {
                sheet.autoSizeColumn(i);
            }

            String file = "Relatorio Conta " + id + ".xls";
            File excel = new File(file);
            FileOutputStream out = new FileOutputStream(excel);
            work.write(out);
            return Files.readAllBytes(excel.toPath());

        } catch (IOException e) {
            System.out.println(e);
            return null;
        }

    }

    public byte[] gerarPdfConta(Integer id) {

        Map<String, Object> map = new HashMap<>();
        BancoBrasil banco = new ControllerBanco().procurarId(id);

        map.put("id", banco.getId());
        map.put("agencia", banco.getAgencia());
        map.put("conta", banco.getConta());
        map.put("ativado", banco.isEstadoConta());
        map.put("cpf", banco.getPessoa().getCpf());

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wicket?zeroDateTimeBehavior=convertToNull", "root", "");
            String path = "RelatorioPdfConta.jrxml";

            InputStream arquivo = Relatorio.class.getResourceAsStream(path);
            JasperReport report = (JasperReport) JasperCompileManager.compileReport(arquivo);
            JasperPrint print = JasperFillManager.fillReport(report, map, con);

            String arq = "Relatorio Conta " + id + ".pdf";
            File pdf = new File(arq);
            FileOutputStream output = new FileOutputStream(pdf);

            JasperExportManager.exportReportToPdfStream(print, output);

            return Files.readAllBytes(pdf.toPath());

        } catch (JRException | IOException | ClassNotFoundException | SQLException e) {
            System.out.println(e);
            return null;
        }

    }

}