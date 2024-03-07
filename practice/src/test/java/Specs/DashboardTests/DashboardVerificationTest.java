package Specs.DashboardTests;

import org.openqa.selenium.WebElement;
import org.prac.pageobjects.home.AboutPageObject;
import org.prac.pageobjects.home.DashboardPageObject;
import org.prac.pageobjects.login.LoginPageObject;
import org.prac.pageobjects.util.UtilsPageObject;
import org.prac.services.DriverManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardVerificationTest extends DriverManager {
    private DashboardPageObject dashboardPageObject;
    private UtilsPageObject utilsPageObject;
    private AboutPageObject aboutPageObject;
    String email = getDataFromFile().getProperty("email");
    String password = getDataFromFile().getProperty("password");

    private static final Logger logger = Logger.getLogger(DashboardVerificationTest.class.getName());

    @BeforeClass ()
    public void initialization() throws InterruptedException {
        LoginPageObject loginPageObject = new LoginPageObject(getWebDriver());
        dashboardPageObject = new DashboardPageObject(getWebDriver());
        aboutPageObject = new AboutPageObject(getWebDriver());
        utilsPageObject = new UtilsPageObject(getWebDriver());
        loginPageObject.login(email, password);
        Assert.assertEquals(dashboardPageObject.getValueOFProductsHeading(), "Products");
    }

    @Test(testName = "Verify products on dashboard", description = "Verify if the user is able to view products on the dashboard",suiteName = "Dashboard Tests", priority = 1)
    public void verifyProductsOnDashboard(){
        String readProductNameFromFile = getDataFromFile().getProperty("productNames");
        String[] productNames = readProductNameFromFile.split(",\\s*");

        for (String productName : productNames) {
            WebElement inventoryItemElem = dashboardPageObject.getInventoryItemElem(productName);
            Assert.assertNotNull(inventoryItemElem, productName + " is not visible on the dashboard");
        }

    }

    @Test(testName = "Verify The sorting of products ", description = "Verify if the social links are visible on the dashboard",suiteName = "Dashboard Tests", priority = 2)
    public void verifyTheProductSorting() throws InterruptedException {

        String readItemsFromFile = getDataFromFile().getProperty("sortOptions");
        // Select sorting options one by one and verify the sorting
        String[] sortingOptions = readItemsFromFile.split(",\\s*");
        for (String option : sortingOptions) {
            boolean isSortedByName = dashboardPageObject.areProductsSortedByName(option);
            delay();
            boolean isPriceSorted = true;

            if (option.equals("Price (low to high)") || option.equals("Price (high to low)")) {
                delay();
                Map.Entry<Double, Double> priceRange = dashboardPageObject.getProductPriceRange(option);
                double lowestPrice = priceRange.getKey();
                double highestPrice = priceRange.getValue();
                logger.log(Level.INFO, () -> "Products are sorted correctly by: " + lowestPrice);
                logger.log(Level.INFO, () -> "Products are sorted correctly by: " + highestPrice);
                isPriceSorted = option.equals("Price (low to high)") ? lowestPrice <= highestPrice : highestPrice <= lowestPrice;
            }

            if (isSortedByName && isPriceSorted) {
                logger.log(Level.INFO, () -> "Products are sorted correctly by: " + option);
            } else {
                logger.log(Level.SEVERE, () -> "Products are not sorted correctly by: " + option);
                Assert.fail("Products are not sorted correctly by: " + option);
            }
        }
    }

    @Test(testName = "Verify Navigation of About link", description = "Verify if the user is able to navigate to the About page from the dashboard",suiteName = "Dashboard Tests", priority = 3)
    public void verifyNavigationOfAboutLink(){
        dashboardPageObject.clickBurgerBtnElem();
        dashboardPageObject.clickAboutSideBarLinkElem();
        Assert.assertEquals(getWebDriver().getCurrentUrl(), getDataFromFile().getProperty("aboutUs_url"));
        Assert.assertTrue(aboutPageObject.getAboutPageLogoElem().isDisplayed(), "Logo is displayed");
        Assert.assertEquals(aboutPageObject.getValueOFAboutPageHeading(), getDataFromFile().getProperty("heading_txt"));

    }

    @Test(testName = "Verify social links", description = "Verify if the social links are visible on the dashboard", priority = 4)
    public void verifySocialLinks() throws InterruptedException {
        navigateToURL(getDataFromFile().getProperty("dashboard_url"));
        delay();
        dashboardPageObject.clickTwitterLinkElem();
        delay();
        String twitterUrl = utilsPageObject.handleNextTab(); // Keep the new tab open

        Assert.assertEquals(twitterUrl, getDataFromFile().getProperty("twitter_url"));

        delay();
        dashboardPageObject.clickFaceBookLinkElem();
        delay();
        String facebookUrl = utilsPageObject.handleNextTab();
        Assert.assertEquals(facebookUrl, getDataFromFile().getProperty("faceBook_url"));
        delay();
        dashboardPageObject.clickLinkedInLinkElem();
        delay();
        utilsPageObject.handleNextTab(false);
        Assert.assertTrue(dashboardPageObject.getLinkedinHeadingElem().isDisplayed());
        delay();
    }



}
