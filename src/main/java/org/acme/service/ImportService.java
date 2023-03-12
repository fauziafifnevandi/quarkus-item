package org.acme.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.acme.model.Item;
import org.acme.utils.FileFormItem;
import org.acme.utils.ResponseUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ImportService {
    @Transactional
    public Response importExcel(FileFormItem request) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(request.file);
        XSSFWorkbook workbook = new XSSFWorkbook(byteArrayInputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        sheet.removeRow(sheet.getRow(0));
        List<Item> toPersist  = new ArrayList<>();
        for (Row row : sheet) {
            DataFormatter dataFormatter = new DataFormatter();
            Item item = new Item();
            item.name = row.getCell(0).getStringCellValue();
            item.count = dataFormatter.formatCellValue(row.getCell(2));
            item.price = dataFormatter.formatCellValue(row.getCell(3));
            item.description = row.getCell(3).getStringCellValue();
            item.type = row.getCell(4).getStringCellValue();
            toPersist.add(item);
        }
        Item.persist(toPersist);

        return ResponseUtils.createResponse(true,"201","data import excel has successfully");
    }

    @Transactional
    public Response importCSV(FileFormItem request) throws IOException, CsvValidationException {
        File file = File.createTempFile("temp", "");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(request.file);
        CSVReader reader = new CSVReader(new FileReader(file));
        String [] nextLine;
        reader.skip(1);
        List<Item> toPersist = new ArrayList<>();
        while ((nextLine = reader.readNext()) != null) {
            Item item = new Item();
            item.name = nextLine[0].trim();
            item.count = nextLine[1].trim();
            item.price = nextLine[2].trim();
            item.description = nextLine[3].trim();
            item.type = nextLine[4].trim();
            toPersist.add(item);
        }
        Item.persist(toPersist);
        return ResponseUtils.createResponse(true,"201","data import csv has successfully");
    }
}
