package Specs.AuthenticationTests;

import org.prac.pageobjects.home.DashboardPageObject;
import org.prac.pageobjects.login.LoginPageObject;
import org.prac.services.DriverManager;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginTest extends DriverManager {

    private LoginPageObject loginPageObject;
    private DashboardPageObject dashboardPageObject;
    private String email;
    private String password;

    private final String userNameErrorMsg = getErrorFromFile().getProperty("userNameErrorMsg");

    @BeforeClass
    public void initialization() {
        loginPageObject = new LoginPageObject(getWebDriver());
        dashboardPageObject = new DashboardPageObject(getWebDriver());
        email = getDataFromFile().getProperty("email");
        password = getDataFromFile().getProperty("password");
    }

    @BeforeMethod
    public void navigateToLoginPage() {
        navigateToURL(getDataFromFile().getProperty("base_Url"));
    }
    @Test(testName = "AuthenticationTests With Valid Credentials", description = "Login Test with Valid Credentials", suiteName = "AuthenticationTests", priority = 1)
    public void loginWithValidCredentials() throws InterruptedException {
        loginPageObject.login(email,password);
        delay();
        Assert.assertEquals(dashboardPageObject.getValueOFProductsHeading(), "Products");
    }


    @Test(testName = "AuthenticationTests With Blank Form", description = "Login Test with blank Credentials", suiteName = "AuthenticationTests", priority = 2)
    public void loginWithBlankCredentials() throws InterruptedException {
        loginPageObject.clickLoginButton();
        delay();
        Assert.assertEquals(loginPageObject.getValueOfErrorMessage(), userNameErrorMsg);
    }

    @Test(testName = "AuthenticationTests With User Name Only", description = "Login Test with username only", suiteName = "AuthenticationTests", priority = 3)
    public void loginWithUserNameOnly() throws InterruptedException {
        loginPageObject.login("", password);
        loginPageObject.clickLoginButton();
        delay();
        Assert.assertEquals(loginPageObject.getValueOfErrorMessage(),userNameErrorMsg);
    }

    @Test(testName = "AuthenticationTests With password Only", description = "Login Test with password only", suiteName = "Authentication Tests", priority = 4)
    public void loginWithPasswordOnly() throws InterruptedException {
        loginPageObject.login(email, "");
        loginPageObject.clickLoginButton();
        delay();
        Assert.assertEquals(loginPageObject.getValueOfErrorMessage(), getErrorFromFile().getProperty("passwordErrorMsg"));
    }
    @Test(testName = "AuthenticationTests With Invalid Credentials", description = "Login Test with Invalid Credentials", suiteName = "Authentication Tests", priority = 5)
    public void loginWithInValidCredentials() throws InterruptedException {
        loginPageObject.login(email+"s", password);
        delay();
        Assert.assertEquals(loginPageObject.getValueOfErrorMessage(), getErrorFromFile().getProperty("InvalidCredsErrorMsg"));
    }
}
