package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CheckoutTest extends BaseTest {

    private static final Logger log = LogManager.getLogger(CheckoutTest.class);

    public CheckoutTest() {
        super("src/test/resources/Data/test_data.xlsx", "CekOut_Data");
    }

    @DataProvider(name = "checkoutData")
    public Object[][] checkoutDataProvider() {
        Object[][] data = new Object[testData.size()][1];
        for (int i = 0; i < testData.size(); i++) {
            data[i][0] = testData.get(i);
        }
        return data;
    }

    @Test(dataProvider = "checkoutData")
    public void testCheckout(Map<String, String> row) {
        String username = row.get("username");
        String password = row.get("password");
        String product = row.get("product");
        String firstName = row.get("first_name");
        String lastName = row.get("last_name");
        String postalCode = row.get("postal_code");
        String expectedStatus = row.get("expected_status");

        log.info("--------------------------------------------------");
        log.info("[TEST CASE] User: '{}' | Product: '{}' | First Name: '{}' | Last Name: '{}' | Postal Code: '{}' | Expected: {}",
                username.isEmpty() ? "(empty)" : username,
                product,
                firstName.isEmpty() ? "(empty)" : firstName,
                lastName.isEmpty() ? "(empty)" : lastName,
                postalCode.isEmpty() ? "(empty)" : postalCode,
                expectedStatus);

        // LOGIN
        driver.get("https://www.saucedemo.com/");
        LoginPage loginPage = new LoginPage(driver);
        assertThat(loginPage.isLoginPageDisplayed()).as("Login page harus tampil").isTrue();
        loginPage.login(username, password);

        assertThat(loginPage.getErrorMessage()).as("Login harus berhasil").isEmpty();

        // ADD PRODUCT
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addProductToCart(product);
        productsPage.goToCart();

        // CART PAGE → CHECKOUT
        CartPage cartPage = new CartPage(driver);
        assertThat(cartPage.isCartPageDisplayed()).as("Cart page harus tampil").isTrue();
        cartPage.clickCheckout();

        // CHECKOUT PAGE → FILL FORM
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.waitForPageToLoad();
        checkoutPage.fillCheckoutInfo(firstName, lastName, postalCode);
        checkoutPage.clickContinue();

        // VALIDASI
        String actualError = checkoutPage.getErrorMessage();
        if (expectedStatus.equalsIgnoreCase("SUCCESS")) {
            assertThat(actualError).isEmpty();
            log.info("[PASS] Checkout berhasil");
        } else {
            assertThat(actualError).containsIgnoringCase(expectedStatus.replace("Error: ", ""));
            log.info("[PASS] Checkout gagal sesuai harapan: '{}'", actualError);
        }

        log.info("--------------------------------------------------\n");
    }
}