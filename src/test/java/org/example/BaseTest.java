package org.example;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class BaseTest {

    protected WebDriver driver;
    protected List<Map<String, String>> testData;

    private final String excelPath;
    private final String sheetName;

    // Constructor untuk menerima path Excel dan sheet name
    public BaseTest(String excelPath, String sheetName) {
        this.excelPath = excelPath;
        this.sheetName = sheetName;
    }

    @BeforeClass
    public void setup() throws IOException {
        // Inisialisasi driver
        driver = DriverSetup.getDriver();

        // Buka URL
        driver.get("https://www.saucedemo.com/");

        // Load Excel data
        File excelFile = new File(excelPath);
        System.out.println("Memuat data test dari: " + excelFile.getAbsolutePath());
        System.out.println("File exists: " + excelFile.exists());
        System.out.println("File can read: " + excelFile.canRead());
        System.out.println("File length: " + excelFile.length() + " bytes");

        if (!excelFile.exists() || !excelFile.canRead()) {
            throw new RuntimeException("File Excel tidak ditemukan atau tidak bisa dibaca: " + excelPath);
        }

        try {
            testData = ExcelDataProvider.readExcelData(excelPath, sheetName);

            if (testData == null || testData.isEmpty()) {
                throw new RuntimeException("Tidak ada data test yang ditemukan di sheet '" + sheetName + "'");
            }

            System.out.println("Berhasil memuat " + testData.size() + " baris data test dari sheet '" + sheetName + "'");
        } catch (Exception e) {
            System.err.println("Gagal memuat data test: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            DriverSetup.quitDriver();
            System.out.println("Driver ditutup");
        }
    }
}

