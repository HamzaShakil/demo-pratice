package Specs.CheckoutTests;

import org.prac.models.checkOutInfo;

import org.prac.pageobjects.checkout.InitiateCheckoutPageObject;
import org.prac.pageobjects.common.CommonPageObject;
import org.prac.pageobjects.home.DashboardPageObject;
import org.prac.pageobjects.login.LoginPageObject;
import org.prac.services.DriverManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import com.github.javafaker.Faker;
import org.testng.annotations.Test;

import java.util.Locale;

public class InitiateCheckoutTest extends DriverManager {
    String readItemsFromFile = getDataFromFile().getProperty("items");
    private final String[] items = readItemsFromFile.split(",\\s*");

    private final Faker faker = new Faker(Locale.US);
    private InitiateCheckoutPageObject initiateCheckoutPageObject;

    private CommonPageObject commonPageObject;

    String email = getDataFromFile().getProperty("email");
    String password = getDataFromFile().getProperty("password");
    checkOutInfo info = new checkOutInfo(faker.name().firstName(),faker.name().lastName(),faker.address().zipCode());

    @BeforeClass
    public void initialization() throws InterruptedException {
        LoginPageObject loginPageObject = new LoginPageObject(getWebDriver());
        DashboardPageObject dashboardPageObject = new DashboardPageObject(getWebDriver());
        commonPageObject = new CommonPageObject(getWebDriver());
        initiateCheckoutPageObject = new InitiateCheckoutPageObject(getWebDriver());
        loginPageObject.login(email, password);
        delay();
        Assert.assertEquals(dashboardPageObject.getValueOFProductsHeading(), "Products");
    }
    @Test(testName = "Verify the Checkout CTA", description = "Verify if the user able to navigate to Checkout Screen",suiteName = "Checkout Tests", priority = 1)
    public void verifyTheCheckoutCTA() throws InterruptedException {
        commonPageObject.addItemToCart(items,info,true,true,false);
        delay();
        Assert.assertEquals(initiateCheckoutPageObject.getValueOfCheckOutHeading(),"Checkout: Your Information");
    }

    @Test(dataProvider = "validationData", testName = "Verify the validation against the First Name Field", description = "Verify if the user able to view the first name validation error message", suiteName = "Checkout Tests", priority = 2)
    public void verifyValidation(String firstName, String lastName, String postalCode) throws InterruptedException {
        initiateCheckoutPageObject.fillCheckOutInformation(new checkOutInfo(firstName, lastName, postalCode));

        if (firstName.isEmpty()) {
            Assert.assertEquals(initiateCheckoutPageObject.getValueOfErrorMessage(), getErrorFromFile().getProperty("firstNameErrorMsg"));
            delay();
        } else {
            if (lastName.isEmpty()) {
                Assert.assertEquals(initiateCheckoutPageObject.getValueOfErrorMessage(), getErrorFromFile().getProperty("lastNameErrorMsg"));
                delay();
            } else {
                Assert.assertEquals(initiateCheckoutPageObject.getValueOfErrorMessage(), getErrorFromFile().getProperty("postalCodeErrorMsg"));
                delay();
            }
        }
        delay();
    }

    @Test(testName = "Verify The Checkout Process", description = "Verify if the user able to complete the Checkout Process", suiteName = "Checkout Tests", priority = 4)
    public void checkoutProcess() throws InterruptedException {
        initiateCheckoutPageObject.fillCheckOutInformation(info);
        delay();
    }

    @DataProvider(name = "validationData")
    public Object[][] getValidationData() {
        return new Object[][] {
                {"", "", ""},   // First Name, Last Name, Postal Code
                {faker.name().firstName(), "", ""},
                {faker.name().firstName(), faker.name().lastName(), ""}
                // Add more test data as needed
        };
    }

}
