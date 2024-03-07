package org.prac.pageobjects.cart;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.prac.pageobjects.home.DashboardPageObject;
import org.prac.services.PageObjectManager;

public class CartPageObject extends PageObjectManager {

    public CartPageObject(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "continue-shopping")
    private WebElement continueBtnElem;
    public WebElement getContinueBtnElem(){
        return this.continueBtnElem;
    }
    public void clickContinueShoppingBtnElem(){
         this.continueBtnElem.click();
    }

    @FindBy(id = "checkout")
    private WebElement checkoutBtnElem;

    public WebElement getCheckoutBtnElem(){
        return this.checkoutBtnElem;
    }
    public void clickCheckOutBtnElem(){
        getCheckoutBtnElem().click();
    }

    private final DashboardPageObject dashbPageObject = new DashboardPageObject(driver);

}
