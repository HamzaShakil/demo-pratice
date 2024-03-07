package org.prac.pageobjects.checkout;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.prac.models.checkOutInfo;
import org.prac.pageobjects.util.UtilsPageObject;
import org.prac.services.PageObjectManager;

public class InitiateCheckoutPageObject extends PageObjectManager {
    public InitiateCheckoutPageObject(WebDriver driver) {
        super(driver);
    }
    UtilsPageObject utilityPageObject = new UtilsPageObject(driver);

    @FindBy(id = "first-name")
    private WebElement firstNameTxtFieldElem;

    public WebElement getFirstNameTxtFieldElem(){
        return utilityPageObject.isElementVisible(this.firstNameTxtFieldElem);
    }
    public void setFirstNameTxtFieldElem(String firstName){
        this.getFirstNameTxtFieldElem().clear();
        this.getFirstNameTxtFieldElem().sendKeys(firstName);
    }

    @FindBy(id = "last-name")
    private WebElement lastNameTxtFieldElem;

    public WebElement getLastNameTxtFieldElem(){
        return utilityPageObject.isElementVisible(this.lastNameTxtFieldElem);}
    public void setLastNameTxtFieldElem(String lastName){
        this.getLastNameTxtFieldElem().clear();
        this.getLastNameTxtFieldElem().sendKeys(lastName);
    }

    @FindBy(id = "postal-code")
    private WebElement postalCodeTxtFieldElem;

    public WebElement getPostalCodeTxtFieldElem(){
        return utilityPageObject.isElementVisible(this.postalCodeTxtFieldElem);
    }
    @FindBy(id = "continue")
    private WebElement continueBtnElem;

    public WebElement getContinueBtnElem(){
        return utilityPageObject.isElementVisible(this.continueBtnElem);
    }
    public void clickContinueBtnElem(){
        this.getContinueBtnElem().click();
    }
    public void setPostalCodeTxtFieldElem(String postCode){
        this.getPostalCodeTxtFieldElem().clear();
        this.getPostalCodeTxtFieldElem().sendKeys(postCode);
    }
    @FindBy(xpath = "//span[contains(text(),'Checkout: Your Information')]")
    private WebElement checkOutHeadingElem;

    public WebElement getCheckOutHeadingElem(){
        return utilityPageObject.isElementVisible(this.checkOutHeadingElem);
    }
    public String getValueOfCheckOutHeading(){
        return this.getCheckOutHeadingElem().getText();
    }


    @FindBy (className="error-message-container")
    private WebElement errorMessageElem;
    public WebElement getErrorMessageElem(){return this.errorMessageElem;}

    public String getValueOfErrorMessage() {
        return getErrorMessageElem().getText();
    }
    public void fillCheckOutInformation(checkOutInfo data){
        setFirstNameTxtFieldElem(data.getFirstName());
        setLastNameTxtFieldElem(data.getLastName());
        setPostalCodeTxtFieldElem(data.getPostalCode());
        clickContinueBtnElem();
    }

}
