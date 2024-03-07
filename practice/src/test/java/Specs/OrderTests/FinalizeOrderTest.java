package Specs.OrderTests;

import org.prac.models.checkOutInfo;
import org.prac.pageobjects.cart.CartPageObject;
import org.prac.pageobjects.checkout.ConfirmOrderDetailsPageObject;
import org.prac.pageobjects.common.CommonPageObject;
import org.prac.pageobjects.home.DashboardPageObject;
import org.prac.pageobjects.login.LoginPageObject;
import org.prac.pageobjects.order.FinalizeOrderPageObject;
import org.prac.services.DriverManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FinalizeOrderTest extends DriverManager {
    private final String[] items = {"Sauce Labs Backpack", "Sauce Labs Bike Light"};
    private ConfirmOrderDetailsPageObject confirmOrderDetailsPageObject;
    private FinalizeOrderPageObject finalizeOrderPageObject;
    private CommonPageObject commonPageObject;
    private final checkOutInfo info = new checkOutInfo("Hamza", "Shakil", "75300");

    String email = getDataFromFile().getProperty("email");
    String password = getDataFromFile().getProperty("password");


    @BeforeClass
    public void initialization() throws InterruptedException {
        LoginPageObject loginPageObject = new LoginPageObject(getWebDriver());
        DashboardPageObject dashboardPageObject = new DashboardPageObject(getWebDriver());
        commonPageObject = new CommonPageObject(getWebDriver());
        CartPageObject cartPageObject = new CartPageObject(getWebDriver());
        finalizeOrderPageObject = new FinalizeOrderPageObject(getWebDriver());
        confirmOrderDetailsPageObject = new ConfirmOrderDetailsPageObject(getWebDriver());
        loginPageObject.login(email, password);
        delay();
        Assert.assertEquals(dashboardPageObject.getValueOFProductsHeading(), "Products");
    }

    @Test(testName = "Verify The Finish CTA", description = "Verify if the user able to navigate to Overview Screen",suiteName = "Order Tests", priority = 1)
    public void verifyTheFinishCTA() throws InterruptedException {
        commonPageObject.addItemToCart(items,info,true,true,true);
        delay();
        Assert.assertEquals(confirmOrderDetailsPageObject.getValueMainOfHeading(), "Checkout: Overview");
        confirmOrderDetailsPageObject.clickFinishBtnElem();
    }
    @Test(testName = "Verify The Final Message", description = "Verify if the user able to navigate to Overview Screen",suiteName = "Order Tests", priority = 2)
    public void verifyTheFinalMessage() throws InterruptedException {
        Assert.assertEquals(finalizeOrderPageObject.getValueOFCompleteHeading(), "Thank you for your order!");
        delay();
    }
}
