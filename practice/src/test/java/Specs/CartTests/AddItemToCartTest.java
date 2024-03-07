package Specs.CartTests;

import org.prac.pageobjects.cart.CartPageObject;
import org.prac.pageobjects.common.CommonPageObject;
import org.prac.pageobjects.home.DashboardPageObject;
import org.prac.pageobjects.login.LoginPageObject;
import org.prac.services.DriverManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AddItemToCartTest extends DriverManager {

    String readItemsFromFile = getDataFromFile().getProperty("items");
    private final String[] items = readItemsFromFile.split(",\\s*");

    private DashboardPageObject dashboardPageObject;
    private CartPageObject cartPageObject;
    String email = getDataFromFile().getProperty("email");
    String password= getDataFromFile().getProperty("password");

    private CommonPageObject commonPageObject;

    @BeforeClass
    public void initialization() throws InterruptedException {
        LoginPageObject loginPageObject = new LoginPageObject(getWebDriver());
        dashboardPageObject = new DashboardPageObject(getWebDriver());
        cartPageObject = new CartPageObject(getWebDriver());
        commonPageObject = new CommonPageObject(getWebDriver());
        loginPageObject.login(email, password);
        delay();
        Assert.assertEquals(dashboardPageObject.getValueOFProductsHeading(), "Products");

    }

    @Test(testName = "Add First Item To CartTests", description = "I add the Sauce Labs Backpack to the cart", suiteName = "Cart Tests", priority = 1)
    public void iAddItemToTheCart() throws InterruptedException {
        dashboardPageObject.addAndRemoveItemsInCart(items, 0,null); // Add only the first item
        delay();
        Assert.assertEquals(commonPageObject.getCartCount(), "1");
        commonPageObject.clickCartBtnElem();
        String[] itemsDisplayedInCart = commonPageObject.getItemsDisplayedInCart(items,0);
        delay();
        Assert.assertEquals(items[0], itemsDisplayedInCart[0]);

    }

    @Test(testName = "Add Second Item To CartTests", description = "I add the Sauce Labs Bike Light to the cart",suiteName = "Cart Tests", priority = 2)
    public void iAddSecondItemToTheCart() throws InterruptedException {
        cartPageObject.clickContinueShoppingBtnElem();
        String[] secondItem = {items[1]}; // Create an array with only the second item
        dashboardPageObject.addAndRemoveItemsInCart(secondItem, 0,null); // Add only the second item
        delay();
        Assert.assertEquals(commonPageObject.getCartCount(), "2");
        commonPageObject.clickCartBtnElem();
        String[] itemsDisplayedInCart = commonPageObject.getItemsDisplayedInCart(items, null);
        delay();
        Assert.assertEquals(items, itemsDisplayedInCart);

    }
}
