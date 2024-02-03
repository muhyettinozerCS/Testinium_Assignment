package com.testinium.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {

    public String getCellValue(String filePath, int sheetIndex, int rowIndex, int columnIndex) {
        String cellValue = null;

        try {
            // Excel dosyasını okuma
            FileInputStream fis = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(fis);

            // İlgili sayfayı al
            Sheet sheet = workbook.getSheetAt(sheetIndex);

            // İlgili satırı al
            Row row = sheet.getRow(rowIndex);

            if (row != null) {
                // Belirli sütundaki hücreyi al
                Cell cell = row.getCell(columnIndex);

                if (cell != null) {
                    // Hücre değerini al
                    cellValue = cell.toString();
                }
            }

            // Workbook ve FileInputStream'ı kapat
            workbook.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cellValue;
    }


}
