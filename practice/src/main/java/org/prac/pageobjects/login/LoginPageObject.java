package org.prac.pageobjects.login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.prac.pageobjects.util.UtilsPageObject;
import org.prac.services.PageObjectManager;

public class LoginPageObject extends PageObjectManager {

    UtilsPageObject utilityPageObject = new UtilsPageObject(driver);

    String time = "800";
    public LoginPageObject(WebDriver driver) {
        super(driver);
    }
    @FindBy(id = "user-name")
    private WebElement userNameTxtField;

    @FindBy(id = "password")
    private WebElement passwordTxtField;

    @FindBy(id = "login-button")
    private WebElement submitLoginBtnElem;

    @FindBy(className = "app_logo")
    private WebElement appLogoElem;

    @FindBy (xpath="//*[@id=\"login_button_container\"]/div/form/div[3]/h3")
    private WebElement errorMessageElem;
    public WebElement getErrorMessageElem(){return this.errorMessageElem;}

    public String getValueOfErrorMessage() {
        return getErrorMessageElem().getText();
    }


    public WebElement getUserEmailTxtFieldElem(){
        return utilityPageObject.isElementVisible(this.userNameTxtField);
    }
    public void setUserEmailTxtFieldElem(String username) {
        getUserEmailTxtFieldElem().clear();
        getUserEmailTxtFieldElem().sendKeys(username);
    }
    public WebElement getPasswordTxtFieldElem(){
        return utilityPageObject.isElementVisible(this.passwordTxtField);
    }
    public void setPasswordTxtFieldElem(String password) {
        getPasswordTxtFieldElem().clear();
        getPasswordTxtFieldElem().sendKeys(password);
    }

    public WebElement getLoginBtnElem(){return utilityPageObject.isElementVisible(this.submitLoginBtnElem);}

    public void enterCredentials(String username, String password) throws InterruptedException {
        // Delay for 08 seconds
        Thread.sleep(Long.parseLong(time));
        setUserEmailTxtFieldElem(username);
        setPasswordTxtFieldElem(password);
    }

    public void login(String username, String password) throws InterruptedException {
        enterCredentials(username,password);
        clickLoginButton();
    }

    public void clickLoginButton() {
        getLoginBtnElem().click();
    }

}
