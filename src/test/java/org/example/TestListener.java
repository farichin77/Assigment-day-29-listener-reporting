//package org.example;
//
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;
//import com.aventstack.extentreports.Status;
//import com.aventstack.extentreports.reporter.ExtentSparkReporter;
//import com.aventstack.extentreports.reporter.configuration.Theme;
//import org.openqa.selenium.OutputType;
//import org.openqa.selenium.TakesScreenshot;
//import org.openqa.selenium.WebDriver;
//import org.testng.ITestContext;
//import org.testng.ITestListener;
//import org.testng.ITestResult;
//
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public class TestListener implements ITestListener {
//
//    private static ExtentReports extent;
//    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
//
//    private static final String REPORT_DIR = "test-output/ExtentReport";
//    private static final String SCREENSHOT_DIR = REPORT_DIR + "/screenshots";
//
//    @Override
//    public void onStart(ITestContext context) {
//        try {
//            Files.createDirectories(Paths.get(REPORT_DIR));
//            Files.createDirectories(Paths.get(SCREENSHOT_DIR));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
//        String reportPath = REPORT_DIR + "/ExtentReport_" + timestamp + ".html";
//
//        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
//        spark.config().setDocumentTitle("Automation Test Report");
//        spark.config().setReportName("Regression Test Execution");
//        spark.config().setTheme(Theme.DARK);
//        spark.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");
//
//        extent = new ExtentReports();
//        extent.attachReporter(spark);
//        extent.setSystemInfo("OS", System.getProperty("os.name"));
//        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
//        extent.setSystemInfo("User", System.getProperty("user.name"));
//        extent.setSystemInfo("Environment", System.getProperty("env", "staging"));
//    }
//
//    @Override
//    public void onTestStart(ITestResult result) {
//        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
//        test.assignCategory(result.getMethod().getGroups());
//        String desc = result.getMethod().getDescription();
//        if (desc != null && !desc.isEmpty()) {
//            test.info("Description: " + desc);
//        }
//        extentTest.set(test);
//    }
//
//    @Override
//    public void onTestSuccess(ITestResult result) {
//        extentTest.get().log(Status.PASS, "Test Passed");
//        extentTest.get().info("Execution time: " + (result.getEndMillis() - result.getStartMillis()) + " ms");
//    }
//
//    @Override
//    public void onTestFailure(ITestResult result) {
//        extentTest.get().log(Status.FAIL, "Test Failed");
//
//        // Menambahkan penyebab error / deskripsi kegagalan
//        String failureReason = "";
//        Object instance = result.getInstance();
//        try {
//            if (instance instanceof BaseTest) {
//                BaseTest base = (BaseTest) instance;
//                // Ambil error message / keterangan khusus dari test
//                failureReason = base.getFailureReason(result.getMethod().getMethodName());
//            }
//        } catch (Exception e) {
//            failureReason = "Gagal mengambil keterangan error: " + e.getMessage();
//        }
//
//        if (!failureReason.isEmpty()) {
//            extentTest.get().fail("Failure Reason: " + failureReason);
//        }
//
//        // Stacktrace asli
//        extentTest.get().fail(result.getThrowable());
//
//        // Screenshot
//        try {
//            WebDriver driver = null;
//            if (instance instanceof BaseTest) {
//                driver = ((BaseTest) instance).driver;
//            }
//            if (driver != null) {
//                String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
//                String screenshotName = result.getMethod().getMethodName() + "_" + timestamp;
//                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
//                String screenshotPath = SCREENSHOT_DIR + "/" + screenshotName + ".png";
//                Files.write(Paths.get(screenshotPath), screenshot);
//                extentTest.get().addScreenCaptureFromPath("screenshots/" + screenshotName + ".png", "Failed Screenshot");
//            }
//        } catch (Exception e) {
//            extentTest.get().warning("Failed to capture screenshot");
//        }
//    }
//
//    @Override
//    public void onTestSkipped(ITestResult result) {
//        extentTest.get().log(Status.SKIP, "Test Skipped");
//        extentTest.get().skip(result.getThrowable());
//    }
//
//    @Override
//    public void onFinish(ITestContext context) {
//        extent.flush();
//    }
//}

package org.example;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestListener implements ITestListener {

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    private static final String REPORT_DIR = "test-output/ExtentReport";
    private static final String SCREENSHOT_DIR = REPORT_DIR + "/screenshots";

    @Override
    public void onStart(ITestContext context) {
        try {
            Files.createDirectories(Paths.get(REPORT_DIR));
            Files.createDirectories(Paths.get(SCREENSHOT_DIR));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String reportPath = REPORT_DIR + "/ExtentReport_" + timestamp + ".html";

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setDocumentTitle("Automation Test Report");
        spark.config().setReportName("Regression Test Execution");
        spark.config().setTheme(Theme.DARK);
        spark.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");

        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("User", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", System.getProperty("env", "staging"));
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        test.assignCategory(result.getMethod().getGroups());

        if (result.getMethod().getDescription() != null) {
            test.info("Description: " + result.getMethod().getDescription());
        }

        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test Passed");
        extentTest.get().info("Execution time: " + (result.getEndMillis() - result.getStartMillis()) + " ms");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().log(Status.FAIL, "Test Failed");

        Object instance = result.getInstance();
        String failureReason = "";

        // Ambil keterangan kegagalan dari BaseTest (custom)
        if (instance instanceof BaseTest) {
            BaseTest base = (BaseTest) instance;
            failureReason = base.getFailureReason(result.getMethod().getMethodName(), result.getThrowable());
        }

        if (!failureReason.isEmpty()) {
            extentTest.get().fail("Failure Reason: " + failureReason);
        }

        // Stacktrace asli
        extentTest.get().fail(result.getThrowable());

        // Tangkap screenshot
        try {
            WebDriver driver = null;
            if (instance instanceof BaseTest) {
                driver = ((BaseTest) instance).driver;
            }

            if (driver != null) {
                String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
                String screenshotName = result.getMethod().getMethodName() + "_" + timestamp;
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                String screenshotPath = SCREENSHOT_DIR + "/" + screenshotName + ".png";

                Files.write(Paths.get(screenshotPath), screenshot);

                // Tambahkan ke report relatif terhadap folder report
                extentTest.get().addScreenCaptureFromPath("screenshots/" + screenshotName + ".png", "Failed Screenshot");
            }
        } catch (Exception e) {
            extentTest.get().warning("Failed to capture screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().log(Status.SKIP, "Test Skipped");
        extentTest.get().skip(result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
    }
}



