package Specs.CartTests;

import org.prac.pageobjects.common.CommonPageObject;
import org.prac.pageobjects.home.DashboardPageObject;
import org.prac.pageobjects.login.LoginPageObject;
import org.prac.services.DriverManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RemoveItemFromCartTest extends DriverManager {

    String readItemsFromFile = getDataFromFile().getProperty("items");
    private final String[] items = readItemsFromFile.split(",\\s*");
    private DashboardPageObject dashboardPageObject;
    private CommonPageObject commonPageObject;
    String email = getDataFromFile().getProperty("email");
    String password= getDataFromFile().getProperty("password");

    @BeforeClass
    public void initialization() throws InterruptedException {
        LoginPageObject loginPageObject = new LoginPageObject(getWebDriver());
        dashboardPageObject = new DashboardPageObject(getWebDriver());
        commonPageObject = new CommonPageObject(getWebDriver());
        loginPageObject.login(email, password);
        delay();
        Assert.assertEquals(dashboardPageObject.getValueOFProductsHeading(), "Products");

    }
    @Test(testName = "Remove Item from CartTests from DashboardTests", description = "Verify if the user able to remove the item from detail page",suiteName = "Cart Tests",priority = 1)
    public void removeItemFromDashboard() throws InterruptedException {
        // Remove from DashboardTests
        dashboardPageObject.addAndRemoveItemsInCart(items, 0, null);
        delay();
        Assert.assertEquals(commonPageObject.getCartCount(), "1");
        dashboardPageObject.addAndRemoveItemsInCart(items, 0, true);
        delay();
        Assert.assertTrue(commonPageObject.getCartCount().isEmpty());
    }
    @Test(testName = "Remove Item from CartTests from Detail", description = "Verify if the user able to remove the item from detail page",suiteName = "Cart Tests",priority = 2)
    public void removeItemFromDetail() throws InterruptedException {
        // Remove from Detail Page
        String secondItemOfArray = items[1];
        String[] secondItem = {items[1]};// Create an array with only the second item
        dashboardPageObject.clickInventoryItemElem(secondItemOfArray);
        dashboardPageObject.addAndRemoveItemsInCart(secondItem, 0, null);
        Assert.assertEquals(commonPageObject.getCartCount(), "1");
        dashboardPageObject.addAndRemoveItemsInCart(secondItem, 0, true);
        delay();
        Assert.assertTrue(commonPageObject.getCartCount().isEmpty());
    }
    }

