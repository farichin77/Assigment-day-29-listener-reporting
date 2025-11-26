package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverSetup {

    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            ChromeOptions options = new ChromeOptions();

            // MODE INCOGNITO
            options.addArguments("--incognito");

            // MATIKAN PASSWORD MANAGER CHROME
            options.addArguments("disable-infobars");
            options.addArguments("--disable-save-password-bubble");
            options.addArguments("--disable-notifications");

            // Matikan fitur Chrome yg suka ngeluarin popup save password
            options.setExperimentalOption("prefs", new java.util.HashMap<String, Object>() {{
                put("credentials_enable_service", false);
                put("profile.password_manager_enabled", false);
            }});

            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}

