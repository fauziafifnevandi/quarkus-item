package org.acme.service;

import com.opencsv.CSVWriter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.acme.model.Item;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

@ApplicationScoped
public class ExportService {
    public Response exportPdfItem() throws JRException{
        File file = new File("src/main/resources/jasperreport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(Item.listAll());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>() , jrBeanCollectionDataSource);
        byte[] jasperResult = JasperExportManager.exportReportToPdf(jasperPrint);
        return Response.ok().type("application/pdf").entity(jasperResult).build();
    }

    public Response exportExcelItem() throws IOException {
        ByteArrayOutputStream outputStream = excelItem();
        return Response.ok()
                .type("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .header("Content-Disposition", "attachment; filename=\"item_list_excel.xlsx\"")
                .entity(outputStream.toByteArray()).build();
    }

    public ByteArrayOutputStream excelItem() throws IOException {
        List<Item> itemList = Item.listAll();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("data");
        int rownum = 0;
        Row row = sheet.createRow(rownum++);
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("name");
        row.createCell(2).setCellValue("count");
        row.createCell(3).setCellValue("price");
        row.createCell(4).setCellValue("description");
        row.createCell(5).setCellValue("type");
        row.createCell(6).setCellValue("createdAt");
        row.createCell(7).setCellValue("updatedAt");
        for(Item item : itemList){
            row = sheet.createRow(rownum++);
            row.createCell(0).setCellValue(item.id);
            row.createCell(1).setCellValue(item.name);
            row.createCell(2).setCellValue(item.count);
            row.createCell(3).setCellValue(item.price);
            row.createCell(4).setCellValue(item.description);
            row.createCell(5).setCellValue(item.type);
            row.createCell(6).setCellValue(item.createdAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")));
            row.createCell(7).setCellValue(item.createdAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")));
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        return outputStream;
    }

    public Response exportCsvItem() throws IOException {
        List<Item> itemList = Item.listAll();
        File file = File.createTempFile("temp", "");
        FileWriter outputfile = new FileWriter(file);
        CSVWriter writer = new CSVWriter(outputfile);
        String[] headers = {"id", "name", "count", "price","type","description", "createdAt", "updateAt"};
        writer.writeNext(headers);
        for(Item item : itemList){
            String[] data = {
                    item.id.toString(),
                    item.name,
                    item.count,
                    item.price,
                    item.type,
                    item.description,
                    item.createdAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")),
                    item.updatedAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"))
            };
            writer.writeNext(data);
        }
        writer.close();
        return Response.ok()
                .type("text/csv")
                .header("Content-Disposition", "attachment; filename=\"item_list.csv\"")
                .entity(file).build();

    }
}
