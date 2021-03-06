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

import com.banco.model.Transferencia;
import com.banco.controller.ControllerBanco;
import com.banco.controller.ControllerPessoa;
import com.banco.model.BancoBrasil;
import com.banco.model.Pessoa;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author NOTEDESENVSP1
 */
public class Relatorio implements Serializable {

    private boolean validarExcel(Pessoa pessoa) {

        final String emailValidator = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", integerValidator = "[0-9]+", stringValidator = "[A-Za-z]+";

        final String nome = pessoa.getNome(), cpf = pessoa.getCpf(), email = pessoa.getEmail(), telefone = Integer.toString(pessoa.getTelefone());
        final String cep = pessoa.getCep(), endereco = pessoa.getEndereco(), cidade = pessoa.getCidade(), bairro = pessoa.getBairro();
        final String rua = Integer.toString(pessoa.getNumero()), uf = pessoa.getUf(), tipo = Character.toString(pessoa.getTipoConta()), senha = pessoa.getSenha();

        try {
            if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || telefone.isEmpty() || cep.isEmpty() || endereco.isEmpty() || 
                    cidade.isEmpty() || bairro.isEmpty() || rua.isEmpty() || uf.isEmpty() || tipo.isEmpty() || senha.isEmpty()) {
                return false;
            }
            if (!nome.matches(stringValidator) || !cidade.matches(stringValidator) || !bairro.matches(stringValidator) || !uf.matches(stringValidator)) {
                return false;
            }
            if (cpf.length() != 11 || !cpf.matches(integerValidator) || cep.length() != 8 || !cep.matches(integerValidator)) {
                return false;
            }
            if (!email.matches(emailValidator) || uf.length() != 2 || !rua.matches(integerValidator)) {
                return false;
            }
            if (telefone.length() != 9 || !telefone.matches(integerValidator)) {
                return false;
            }
            return (tipo.equals("A") || tipo.equals("U"));
        } catch (Exception e) {
            return false;
        }
    }

    public int inserirPessoaExcel(File file) {

        List<Pessoa> pessoas = new ArrayList();

        try {

            XSSFWorkbook workBook = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet sheet = workBook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            int linha = 0;
            while (rowIterator.hasNext()) {
                int dados = 1;
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                if (linha > 0) {
                    Pessoa pessoa = new Pessoa();
                    while (cellIterator.hasNext()) {

                        Cell cell = cellIterator.next();
                        DataFormatter formatter = new DataFormatter();

                        switch (dados) {
                            case 1:
                                pessoa.setNome(cell.getStringCellValue());
                                break;
                            case 2:
                                pessoa.setCpf(formatter.formatCellValue(cell));
                                break;
                            case 3:
                                pessoa.setEmail(cell.getStringCellValue());
                                break;
                            case 4:
                                pessoa.setTelefone((int) cell.getNumericCellValue());
                                break;
                            case 5:
                                pessoa.setCep(formatter.formatCellValue(cell));
                                break;
                            case 6:
                                pessoa.setEndereco(cell.getStringCellValue());
                                break;
                            case 7:
                                pessoa.setNumero((int) cell.getNumericCellValue());
                                break;
                            case 8:
                                pessoa.setBairro(cell.getStringCellValue());
                                break;
                            case 9:
                                pessoa.setCidade(cell.getStringCellValue());
                                break;
                            case 10:
                                pessoa.setUf(cell.getStringCellValue());
                                break;
                            case 11:
                                pessoa.setTipoConta(cell.getStringCellValue().charAt(0));
                                break;
                            case 12:
                                pessoa.setSenha(formatter.formatCellValue(cell));
                                break;
                        }
                        dados++;
                    }
                    if (validarExcel(pessoa) == false) {
                        return 1;
                    }
                    pessoas.add(pessoa);
                }
                linha++;
            }

            for (Pessoa p : pessoas) {
                if (new ControllerPessoa().adicionar(p) == 2) {
                    return 2;
                }
            }

            return 0;

        } catch (IOException ex) {
            return 3;
        }
    }

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
            row.createCell(7).setCellValue("Rua");
            row.createCell(8).setCellValue("Bairro");
            row.createCell(9).setCellValue("Cidade");
            row.createCell(10).setCellValue("UF");
            row.createCell(11).setCellValue("Tipo Conta");

            Pessoa pessoa = new ControllerPessoa().procurarId(id);

            row = sheet.createRow(1);

            row.createCell(0).setCellValue(pessoa.getId());
            row.createCell(1).setCellValue(pessoa.getNome());
            row.createCell(2).setCellValue(pessoa.getCpf());
            row.createCell(3).setCellValue(pessoa.getEmail());
            row.createCell(4).setCellValue(pessoa.getTelefone());
            row.createCell(5).setCellValue(pessoa.getCep());
            row.createCell(6).setCellValue(pessoa.getEndereco());
            row.createCell(7).setCellValue(pessoa.getNumero());
            row.createCell(8).setCellValue(pessoa.getBairro());
            row.createCell(9).setCellValue(pessoa.getCidade());
            row.createCell(10).setCellValue(pessoa.getUf());
            row.createCell(11).setCellValue(Character.toString(pessoa.getTipoConta()));

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

            String ativado;
            if (banco.isEstadoConta()) {
                ativado = "Sim";
            } else {
                ativado = "Não";
            }

            row.createCell(0).setCellValue(banco.getId());
            row.createCell(1).setCellValue(banco.getConta());
            row.createCell(2).setCellValue(banco.getAgencia());
            row.createCell(3).setCellValue(ativado);
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

    public byte[] gerarExcelTransferencia(List<Transferencia> transferencias) {

        try {

            HSSFWorkbook work = new HSSFWorkbook();
            HSSFSheet sheet = work.createSheet("Relatorio Transferencia");

            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("Id");
            row.createCell(1).setCellValue("Cpf Remetente");
            row.createCell(2).setCellValue("Cpf Destinatario");
            row.createCell(3).setCellValue("Tipo");
            row.createCell(4).setCellValue("Valor");
            row.createCell(5).setCellValue("Data");

            int i = 1;
            for (Transferencia transferencia : transferencias) {
                row = sheet.createRow(i);
                row.createCell(0).setCellValue(transferencia.getId());
                row.createCell(1).setCellValue(transferencia.getCpfRemetente());
                row.createCell(2).setCellValue(transferencia.getCpfDestinatario());
                row.createCell(3).setCellValue(transferencia.getTipoTranferencia());
                row.createCell(4).setCellValue(transferencia.getValor());
                row.createCell(5).setCellValue(transferencia.getDataTransf());
                i++;
            }

            for (i = 0; i < row.getHeight(); i++) {
                sheet.autoSizeColumn(i);
            }

            String file = "Relatorio Transferencia.xls";
            File excel = new File(file);
            FileOutputStream out = new FileOutputStream(excel);
            work.write(out);
            return Files.readAllBytes(excel.toPath());

        } catch (IOException e) {
            System.out.println(e);
            return null;
        }

    }

    public byte[] gerarPdfTransferencia(Transferencia transferencia, boolean adm, String cpf) {

        try {

            String rem = transferencia.getCpfRemetente(), des = transferencia.getCpfDestinatario();
            Map<String, Object> map = new HashMap<>();

            if (adm == false) {
                if (rem.equals("*") && des.equals("*") || !rem.equals(cpf) && !des.equals(cpf)) {
                    map.put("nulo", true);
                    map.put("reme", cpf);
                    map.put("dest", cpf);
                } else {
                    map.put("nulo", false);
                    map.put("reme", rem);
                    map.put("dest", des);
                }
            } else {
                map.put("nulo", false);
                map.put("reme", rem);
                map.put("dest", des);
            }

            map.put("adm", adm);
            map.put("tipo", transferencia.getTipoTranferencia());
            map.put("valor", transferencia.getValor());

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wicket?zeroDateTimeBehavior=convertToNull", "root", "");
            String path = "RelatorioPdfTransferencia.jrxml";

            InputStream arquivo = Relatorio.class.getResourceAsStream(path);
            JasperReport report = (JasperReport) JasperCompileManager.compileReport(arquivo);
            JasperPrint print = JasperFillManager.fillReport(report, map, con);

            String arq = "Relatorio Transferencia.pdf";
            File pdf = new File(arq);
            FileOutputStream output = new FileOutputStream(pdf);

            JasperExportManager.exportReportToPdfStream(print, output);

            return Files.readAllBytes(pdf.toPath());

        } catch (ClassNotFoundException | SQLException | JRException | IOException e) {
            System.out.println(e);
            return null;
        }

    }

}
