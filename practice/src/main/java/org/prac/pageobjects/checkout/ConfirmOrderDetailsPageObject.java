package org.prac.pageobjects.checkout;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.prac.pageobjects.util.UtilsPageObject;
import org.prac.services.PageObjectManager;

public class ConfirmOrderDetailsPageObject extends PageObjectManager {
    public ConfirmOrderDetailsPageObject(WebDriver driver) {
        super(driver);
    }

    UtilsPageObject utilityPageObject = new UtilsPageObject(driver);

    @FindBy(id = "finish")
    private WebElement finishBtnElem;

    public WebElement getFinishBtnElem(){
        return utilityPageObject.isElementVisible(this.finishBtnElem);
    }

    @FindBy(className = "inventory_item_price")
    private WebElement itemPriceElem;

    public WebElement getItemPriceTxtElem(){
        return utilityPageObject.isElementVisible(this.itemPriceElem);
    }

    public String getValueOfItemPrice(){
        return this.getItemPriceTxtElem().getText();
    }
    @FindBy(xpath = "//*[contains(@class, 'summary_info_label') and contains(@class, 'summary_total_label')]")
    private WebElement totalItemPriceElem;

    public WebElement getTotalItemPriceElem(){
        return utilityPageObject.isElementVisible(this.totalItemPriceElem);
    }
    public String getValueOfTotalItemPrice(){
        return this.getTotalItemPriceElem().getText();
    }

    @FindBy(className = "summary_tax_label")
    private WebElement taxPriceElem;

    public WebElement getTaxPriceElem(){
        return this.utilityPageObject.isElementVisible(taxPriceElem);
    }
    public String getValueOFTaxPrice(){
        return this.getTaxPriceElem().getText();
    }
    public void clickFinishBtnElem(){
        this.getFinishBtnElem().click();
    }

    @FindBy(xpath = "//span[contains(text(),'Checkout: Overview')]")
    private WebElement checkOutHeadingElem;

    public WebElement getHeadingElem(){
        return utilityPageObject.isElementVisible(this.checkOutHeadingElem);
    }

    public String getValueMainOfHeading(){
        return this.getHeadingElem().getText();
    }

    public String calculateTheTaxValue(){
        /* Get the original item price as a string */
        String itemPriceString = this.getValueOfItemPrice();

        // Remove any non-numeric characters and convert to double
        double itemPrice = Double.parseDouble(itemPriceString.replaceAll("[^\\d.]", ""));

        // Calculate 8% of the item price
        double eightPercent = itemPrice * 0.08;

        // Assert that the calculated value matches the expected tax value

        return String.format("Tax: $%.2f", eightPercent);
    }


}
