package org.prac.pageobjects.order;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.prac.pageobjects.util.UtilsPageObject;
import org.prac.services.PageObjectManager;

public class FinalizeOrderPageObject extends PageObjectManager {
    public FinalizeOrderPageObject(WebDriver driver) {
        super(driver);
    }
    UtilsPageObject utilityPageObject = new UtilsPageObject(driver);
    @FindBy(className = "complete-header")
    private WebElement completeHeadingTxtElem;
    public WebElement getCompleteHeadingTxtElem(){
        return utilityPageObject.isElementVisible(this.completeHeadingTxtElem);
    }
    public String getValueOFCompleteHeading(){
        return this.getCompleteHeadingTxtElem().getText();
    }
    @FindBy(id = "back-to-products")
    private WebElement backToProductsBtnElem;
    public WebElement getBackToProductsBtnElem(){
        return utilityPageObject.isElementVisible(this.backToProductsBtnElem);
    }
    public void clickBackToProductsBtnElem(){
        this.getBackToProductsBtnElem().click();
    }
    @FindBy(className = "complete-text")
    private WebElement completeTxtElem;
    public WebElement getCompleteTxtElem(){
        return utilityPageObject.isElementVisible(this.completeTxtElem);
    }
    @FindBy(id = "shopping_cart_container")
    private WebElement cartBtnElem;


}
