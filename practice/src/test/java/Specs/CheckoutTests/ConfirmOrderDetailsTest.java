package Specs.CheckoutTests;

import org.prac.models.checkOutInfo;
import org.prac.pageobjects.checkout.ConfirmOrderDetailsPageObject;
import org.prac.pageobjects.common.CommonPageObject;
import org.prac.pageobjects.home.DashboardPageObject;
import org.prac.pageobjects.login.LoginPageObject;
import org.prac.services.DriverManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ConfirmOrderDetailsTest extends DriverManager {
    String readItemsFromFile = getDataFromFile().getProperty("items");
    private final String[] items = readItemsFromFile.split(",\\s*");
    private CommonPageObject commonPageObject;
    private ConfirmOrderDetailsPageObject confirmOrderDetailsPageObject;
    private final checkOutInfo info = new checkOutInfo("Hamza", "Shakil", "75300");

    String email = getDataFromFile().getProperty("email");
    String password = getDataFromFile().getProperty("password");

    String Price = "$29.99";

    @BeforeClass
    public void initialization() throws InterruptedException {
        LoginPageObject loginPageObject = new LoginPageObject(getWebDriver());
        DashboardPageObject dashboardPageObject = new DashboardPageObject(getWebDriver());
        commonPageObject = new CommonPageObject(getWebDriver());
        confirmOrderDetailsPageObject = new ConfirmOrderDetailsPageObject(getWebDriver());
        loginPageObject.login(email, password);
        delay();
        Assert.assertEquals(dashboardPageObject.getValueOFProductsHeading(), "Products");
    }

    @Test(testName = "Verify The Continue CTA", description = "Verify if the user able to navigate to Overview Screen",suiteName = "Checkout Tests",priority = 1)
    public void verifyTheContinueCTA() throws InterruptedException {
        commonPageObject.addItemToCart(items,info,true,true,true);
        delay();
        Assert.assertEquals(confirmOrderDetailsPageObject.getValueMainOfHeading(), "Checkout: Overview");
    }

    @Test(testName = "Verify The CartTests Info", description = "Verify if the user able to View the cart Info on the Screen",suiteName = "Checkout Tests", priority = 2)
    public void verifyCartItemsOnViewScreen() throws InterruptedException {
        String[] itemsDisplayedInCart = commonPageObject.getItemsDisplayedInCart(items, 0);
        delay();
        Assert.assertEquals(items[0], itemsDisplayedInCart[0]);
    }

    @Test(testName = "Verify The payment Information", description = "Verify if the user able to view payment Information",suiteName = "Checkout Tests", priority = 3)
    public void verifyThePaymentInformation() {

        // Verify the original item price with price visible on payment screen
        Assert.assertEquals(confirmOrderDetailsPageObject.getValueOfItemPrice(), Price);
        Assert.assertEquals(confirmOrderDetailsPageObject.getValueOFTaxPrice(),
                confirmOrderDetailsPageObject.calculateTheTaxValue());


    }

}
