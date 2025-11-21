package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class LoginTest extends BaseTest {

    private static final Logger log = LogManager.getLogger(LoginTest.class);

    public LoginTest() {
        // Gunakan file Excel LoginData.xlsx dan sheet "LoginData"
        super("src/test/resources/Data/LoginData.xlsx", "LoginData");
    }

    @DataProvider(name = "loginData")
    public Object[][] loginDataProvider() {
        // Ambil dari testData yang sudah dibaca di BaseTest
        Object[][] data = new Object[testData.size()][1];
        for (int i = 0; i < testData.size(); i++) {
            data[i][0] = testData.get(i);
        }
        return data;
    }

    @Test(dataProvider = "loginData")
    public void testLoginWithDataDriven(Map<String, String> testDataRow) {
        String username = testDataRow.get("username");
        String password = testDataRow.get("password");
        String expected = testDataRow.get("expected").toUpperCase();

        log.info("--------------------------------------------------");
        log.info("[TEST CASE] Username: '{}' | Password: '{}' | Expected: {}",
                username.isEmpty() ? "(empty)" : username,
                password.isEmpty() ? "(empty)" : password,
                expected);

        driver.get("https://www.saucedemo.com/");
        LoginPage loginPage = new LoginPage(driver);

        assertThat(loginPage.isLoginPageDisplayed())
                .as("Login page harus ditampilkan")
                .isTrue();

        loginPage.login(username, password);
        String actualError = loginPage.getErrorMessage();

        log.info("[RESULT] Actual error message: '{}'", actualError.isEmpty() ? "(none)" : actualError);

        switch (expected) {
            case "SUCCESS" -> {
                assertThat(actualError).isEmpty();
                log.info("[PASS] Login berhasil");
            }
            case "INVALID" -> {
                assertThat(actualError)
                        .as("[Error message harus muncul untuk login invalid user]")
                        .isNotEmpty();
                log.info("[PASS] Login gagal sesuai harapan (invalid credentials)");
            }
            case "REQUIRED USERNAME" -> {
                assertThat(actualError)
                        .containsIgnoringCase("username is required");
                log.info("[PASS] Login gagal sesuai harapan (username kosong)");
            }
            case "REQUIRED PASSWORD" -> {
                assertThat(actualError)
                        .containsIgnoringCase("password is required");
                log.info("[PASS] Login gagal sesuai harapan (password kosong)");
            }
            default -> log.warn("[WARN] Unknown expected value: {}", expected);
        }

        log.info("--------------------------------------------------\n");
    }
}

