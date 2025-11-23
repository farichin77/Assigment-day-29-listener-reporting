package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@Listeners(org.example.TestListener.class)
public class LoginTest extends BaseTest {

    private static final Logger log = LogManager.getLogger(LoginTest.class);

    public LoginTest() {
        setExcel("src/test/resources/Data/LoginData.xlsx", "LoginData");
    }

    @DataProvider(name = "loginData")
    public Object[][] loginDataProvider() {
        Object[][] data = new Object[testData.size()][1];
        for (int i = 0; i < testData.size(); i++) {
            data[i][0] = testData.get(i);
        }
        return data;
    }

    @Test(dataProvider = "loginData")
    public void testLoginWithDataDriven(Map<String, String> row) {

        String username = row.getOrDefault("username", "");
        String password = row.getOrDefault("password", "");
        String expected = row.getOrDefault("expected", "").trim().toUpperCase();

        log.info("--------------------------------------------------");
        log.info("[TEST CASE] Username='{}' | Password='{}' | Expected={}",
                username.isEmpty() ? "(empty)" : username,
                password.isEmpty() ? "(empty)" : password,
                expected);

        driver.get("https://www.saucedemo.com/");
        LoginPage loginPage = new LoginPage(driver);

        assertThat(loginPage.isLoginPageDisplayed())
                .as("Login page harus tampil")
                .isTrue();

        loginPage.login(username, password);

        String actualError = loginPage.getErrorMessage();
        boolean isErrorDisplayed = !actualError.isEmpty();

        log.info("[RESULT] Error: '{}'", isErrorDisplayed ? actualError : "(none)");

        // ==== SWITCH STATEMENT KLASIK ====
        if ("SUCCESS".equals(expected)) {
            assertThat(isErrorDisplayed)
                    .as("Login sukses tidak boleh muncul error message")
                    .isFalse();

            assertThat(driver.getCurrentUrl())
                    .contains("inventory.html");

            log.info("[PASS] Login sukses & masuk ke halaman inventory");

        } else if ("INVALID".equals(expected)) {
            assertThat(isErrorDisplayed)
                    .as("Harus muncul error untuk kredensial invalid")
                    .isTrue();
            log.info("[PASS] Invalid credentials ditolak");

        } else if ("REQUIRED USERNAME".equals(expected)) {
            assertThat(actualError)
                    .containsIgnoringCase("username is required");
            log.info("[PASS] Username kosong → error sesuai");

        } else if ("REQUIRED PASSWORD".equals(expected)) {
            assertThat(actualError)
                    .containsIgnoringCase("password is required");
            log.info("[PASS] Password kosong → error sesuai");

        } else {
            log.error("[ERROR] Expected value tidak dikenali: {}", expected);
            fail("Nilai 'expected' tidak valid. Perbaiki Excel test data.");
        }

        log.info("--------------------------------------------------\n");
    }
}


