package org.prac.pageobjects.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.prac.models.checkOutInfo;
import org.prac.pageobjects.cart.CartPageObject;
import org.prac.pageobjects.checkout.InitiateCheckoutPageObject;
import org.prac.pageobjects.home.DashboardPageObject;
import org.prac.pageobjects.util.UtilsPageObject;
import org.prac.services.PageObjectManager;
import org.testng.Assert;
import org.testng.annotations.Optional;

public class CommonPageObject extends PageObjectManager {
    public CommonPageObject(WebDriver driver) {
        super(driver);
    }
    UtilsPageObject utilityPageObject = new UtilsPageObject(driver);
    DashboardPageObject dashboardPageObject = new DashboardPageObject(driver);
    CartPageObject cartPageObject = new CartPageObject(driver);
    InitiateCheckoutPageObject initiateCheckoutPageObject = new InitiateCheckoutPageObject(driver);
    @FindBy(id = "cancel")
    private WebElement cancelBtnElem;
    public WebElement getCancelBtnElem(){
        return utilityPageObject.isElementVisible(this.cancelBtnElem);
    }
    public void clickCancelBtnElem(){
        this.cancelBtnElem.click();
    }

    @FindBy(id = "shopping_cart_container")
    private WebElement cartBtnElem;
    public WebElement getCartBtnElem(){
        return utilityPageObject.isElementVisible(this.cartBtnElem);
    }
    public void clickCartBtnElem(){
        getCartBtnElem().click();
    }
    public String getCartCount() {
        return getCartBtnElem().getText();
    }

    public String[] getItemsDisplayedInCart(String[] items, Integer endIndex) {

        int actualEndIndex = endIndex != null ? endIndex : items.length - 1;
        String[] cartItems = new String[actualEndIndex + 1];

        for (int i = 0; i <= actualEndIndex; i++) {
            String cartItem = driver.findElement(By.xpath("//div[text()='" + items[i] + "']")).getText();
            cartItems[i] = cartItem;
        }
        return cartItems;
    }

    public void addItemToCart(String[]items, checkOutInfo info, @Optional Boolean onlyAddItem, @Optional Boolean checkOutRequired, @Optional Boolean fillInfo) throws InterruptedException {

        if(Boolean.TRUE.equals(onlyAddItem)) {
            dashboardPageObject.addAndRemoveItemsInCart(items, 0, null);
            Assert.assertEquals(this.getCartCount(), "1");
            delay();
            this.clickCartBtnElem();
            String[] itemsDisplayedInCart = this.getItemsDisplayedInCart(items, 0);
            delay();
            Assert.assertEquals(items[0], itemsDisplayedInCart[0]);
        }
        if(Boolean.TRUE.equals(checkOutRequired)) {
            delay();
            cartPageObject.clickCheckOutBtnElem();
        }
        if(Boolean.TRUE.equals(fillInfo)) {
            delay();
            initiateCheckoutPageObject.fillCheckOutInformation(info);
        }
    }

}
