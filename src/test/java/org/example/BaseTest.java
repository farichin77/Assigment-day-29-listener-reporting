//package org.example;
//
//import org.apache.commons.io.FileUtils;
//import org.openqa.selenium.OutputType;
//import org.openqa.selenium.TakesScreenshot;
//import org.openqa.selenium.WebDriver;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//
//import java.io.File;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//public class BaseTest {
//
//    protected WebDriver driver;
//    protected List<Map<String, String>> testData;
//
//    protected String excelPath;
//    protected String sheetName;
//
//
//    public void setExcel(String excelPath, String sheetName) {
//        this.excelPath = excelPath;
//        this.sheetName = sheetName;
//    }
//
//    @BeforeClass
//    public void setup() throws IOException {
//        // Inisialisasi WebDriver
//        driver = DriverSetup.getDriver();
//        driver.get("https://www.saucedemo.com/");
//
//        // Load excel
//        File excelFile = new File(excelPath);
//        System.out.println("Load Excel: " + excelFile.getAbsolutePath());
//
//        if (!excelFile.exists() || !excelFile.canRead()) {
//            throw new RuntimeException("Excel tidak ditemukan atau tidak bisa dibaca");
//        }
//
//        testData = ExcelDataProvider.readExcelData(excelPath, sheetName);
//
//        if (testData == null || testData.isEmpty()) {
//            throw new RuntimeException("Tidak ada data pada sheet '" + sheetName + "'");
//        }
//
//        System.out.println("Memuat total " + testData.size() + " baris test data...");
//    }
//
//    @AfterClass
//    public void teardown() {
//        if (driver != null) {
//            DriverSetup.quitDriver();
//            System.out.println("Driver ditutup");
//        }
//    }
//
//
//    public String takeScreenshot(String testName) {
//        File dir = new File("test-output/screenshots");
//        if (!dir.exists()) dir.mkdirs();
//
//        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String filePath = "test-output/screenshots/" + testName + "_" + timestamp + ".png";
//
//        try {
//            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//            File dest = new File(filePath);
//            FileUtils.copyFile(src, dest);
//
//            System.out.println(">>> Screenshot disimpan di: " + filePath);
//            return filePath;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//
//    public String getFailureReason(String testName) {
//
//        return "";
//    }
//}

package org.example;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BaseTest {

    protected WebDriver driver;
    protected List<Map<String, String>> testData;

    protected String excelPath;
    protected String sheetName;

    public void setExcel(String excelPath, String sheetName) {
        this.excelPath = excelPath;
        this.sheetName = sheetName;
    }

    @BeforeClass
    public void setup() throws IOException {
        driver = DriverSetup.getDriver();
        driver.get("https://www.saucedemo.com/");

        File excelFile = new File(excelPath);
        System.out.println("Load Excel: " + excelFile.getAbsolutePath());

        if (!excelFile.exists() || !excelFile.canRead()) {
            throw new RuntimeException("Excel tidak ditemukan atau tidak bisa dibaca");
        }

        testData = ExcelDataProvider.readExcelData(excelPath, sheetName);

        if (testData == null || testData.isEmpty()) {
            throw new RuntimeException("Tidak ada data pada sheet '" + sheetName + "'");
        }

        System.out.println("Memuat total " + testData.size() + " baris test data...");
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            DriverSetup.quitDriver();
            System.out.println("Driver ditutup");
        }
    }

    // Digunakan oleh listener untuk screenshot
    public String takeScreenshot(String testName) {
        File dir = new File("test-output/screenshots");
        if (!dir.exists()) dir.mkdirs();

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filePath = "test-output/screenshots/" + testName + "_" + timestamp + ".png";

        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(filePath);
            FileUtils.copyFile(src, dest);

            System.out.println(">>> Screenshot disimpan di: " + filePath);
            return filePath;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Bisa di override oleh test untuk memberikan alasan kegagalan
    public String getFailureReason(String testName, Throwable t) {
        // misal, ambil message dari Throwable
        return t != null ? t.getMessage() : "";
    }

}

