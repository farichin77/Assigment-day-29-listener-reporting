package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelDataProvider {
    public static List<Map<String, String>> readExcelData(String filePath, String sheetName) throws IOException {
        System.out.println("Membaca file Excel: " + filePath);
        System.out.println("Mencari sheet: " + sheetName);
        
        // Cek apakah file ada
        File file = new File(filePath);
        System.out.println("File exists: " + file.exists());
        System.out.println("File can read: " + file.canRead());
        System.out.println("File size: " + file.length() + " bytes");
        
        List<Map<String, String>> data = new ArrayList<>();
        
        if (!file.exists()) {
            throw new IOException("File not found: " + filePath);
        }
        
        if (!file.canRead()) {
            throw new IOException("Cannot read file (permission denied): " + filePath);
        }
        
        System.out.println("File exists and is readable. Size: " + file.length() + " bytes");

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            System.out.println("Jumlah sheet dalam workbook: " + workbook.getNumberOfSheets());
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                String currentSheetName = workbook.getSheetName(i);
                System.out.println("Sheet " + i + ": '" + currentSheetName + "'" + 
                                 (currentSheetName.equals(sheetName) ? " (SESUAI DENGAN YANG DICARI)" : ""));
            }
            
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IOException("Sheet '" + sheetName + "' not found in the workbook");
            }
            Row headerRow = sheet.getRow(0);
            DataFormatter formatter = new DataFormatter();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Map<String, String> rowData = new HashMap<>();
                for (int j = 0; j < headerRow.getLastCellNum(); j++) {
                    Cell headerCell = headerRow.getCell(j);
                    Cell dataCell = row.getCell(j);
                    String header = formatter.formatCellValue(headerCell)
                            .trim()
                            .toLowerCase()
                            .replaceAll("\\s+", "_");

                    String value = (dataCell != null) ? formatter.formatCellValue(dataCell).trim() : "";
                    rowData.put(header, value);
                }
                data.add(rowData);
            }
        }
        return data;
    }
}
