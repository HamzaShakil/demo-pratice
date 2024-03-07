package Specs.AuthenticationTests;

import org.prac.pageobjects.home.DashboardPageObject;
import org.prac.pageobjects.login.LoginPageObject;
import org.prac.services.DriverManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LogoutTest extends DriverManager {

    private LoginPageObject loginPageObject;
    private DashboardPageObject dashboardPageObject;

    @BeforeClass
    public void initialization() throws InterruptedException {
        loginPageObject = new LoginPageObject(getWebDriver());
        dashboardPageObject = new DashboardPageObject(getWebDriver());
        String email = getDataFromFile().getProperty("email");
        String password = getDataFromFile().getProperty("password");
        loginPageObject.login(email,password);
    }

    @Test(testName = "Verify the logout CTA", description = "Verify if the user able to logout successfully", suiteName = "Authentication Tests", priority = 1)
    public void verifyTheLogoutCTA() throws InterruptedException {
        dashboardPageObject.logoutFromPortal();
        delay();
        Assert.assertEquals(loginPageObject.getLoginBtnElem().getAttribute("value"), "Login");
    }
}
