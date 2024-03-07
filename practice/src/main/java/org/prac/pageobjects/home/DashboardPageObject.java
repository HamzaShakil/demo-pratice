package org.prac.pageobjects.home;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.prac.models.product;
import org.prac.pageobjects.util.UtilsPageObject;
import org.prac.services.PageObjectManager;
import org.testng.annotations.Optional;

import java.util.*;
import java.util.stream.Collectors;


public class DashboardPageObject extends PageObjectManager {

    UtilsPageObject utilityPageObject = new UtilsPageObject(driver);
    public DashboardPageObject(WebDriver driver) {
        super(driver);
    }
    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutSideBarLinkElem;
    public WebElement getLogoutSideBarLinkElem(){
        return utilityPageObject.isElementVisible(utilityPageObject.isElementVisible(this.logoutSideBarLinkElem));
    }
    public void clickLogoutSideBarLinkElem(){
         utilityPageObject.isElementClickable(getLogoutSideBarLinkElem()).click();
    }
    @FindBy(xpath = "//span[contains(text(),'Products')]")
    private WebElement productsHeadingElem;
    public WebElement getAddBtnElem(String itemId){
        return utilityPageObject.isElementVisible(this.driver.findElement(By.xpath("//*[contains(@id, '" + itemId + "')]")));
    }
    public WebElement getInventoryItemElem(String itemName){
        return utilityPageObject.isElementVisible(driver.findElement(By.xpath("//div[@class='inventory_item_label']//div[contains(text(), '" + itemName + "')]")));
    }
    public void clickInventoryItemElem(String itemName){
        getInventoryItemElem(itemName).click();
    }

    public WebElement getProductsHeadingElem(){
        return utilityPageObject.isElementVisible(this.productsHeadingElem);
    }
    public String getValueOFProductsHeading(){
        return getProductsHeadingElem().getText();
    }

    @FindBy(xpath = "//a[@id='about_sidebar_link']")
    private WebElement aboutSideBarLinkElem;

    public WebElement getAboutSideBarLinkElem(){
        return utilityPageObject.isElementVisible(this.aboutSideBarLinkElem);
    }
    public void clickAboutSideBarLinkElem(){
        getAboutSideBarLinkElem().click();
    }
    @FindBy(xpath = "//a[contains(text(),'Twitter')]")
    private WebElement twitterLinkElem;

    public WebElement getTwitterLinkElem(){
        return utilityPageObject.isElementVisible(this.twitterLinkElem);
    }
    public void clickTwitterLinkElem(){
        getTwitterLinkElem().click();
    }
    @FindBy(xpath = "//a[contains(text(),'Facebook')]")
    private WebElement faceBookLinkElem;

    public WebElement getFaceBookLinkElem(){
        return utilityPageObject.isElementVisible(this.faceBookLinkElem);
    }
    public void clickFaceBookLinkElem(){
        getFaceBookLinkElem().click();
    }
    @FindBy(xpath = "//a[contains(text(),'LinkedIn')]")
    private WebElement linkedInLinkElem;

    public WebElement getLinkedInLinkElem(){
        return utilityPageObject.isElementVisible(this.linkedInLinkElem);
    }
    public void clickLinkedInLinkElem(){
        getLinkedInLinkElem().click();
    }

    @FindBy(id = "react-burger-menu-btn")
    private WebElement burgerBtnElem;

    public WebElement getBurgerBtnElem(){
        return utilityPageObject.isElementVisible(this.burgerBtnElem);
    }
    public void clickBurgerBtnElem(){
        getBurgerBtnElem().click();
    }

    @FindBy(id = "ember30")
    private WebElement linkedinPageHeadingElem;

    @FindBy(className = "product_sort_container")
    private WebElement productSortContainerElem;

    public WebElement getProductSortContainerElem(){
        return utilityPageObject.isElementVisible(this.productSortContainerElem);
    }

    @FindBy(className = "inventory_item_name")
    private WebElement inventoryItemNameElem;

    public WebElement getInventoryItemNameElem(){
        return utilityPageObject.isElementVisible(this.inventoryItemNameElem);
    }
    @FindBy(className = "linkedin-text")
    private WebElement linkedinHeadingElem;

    public WebElement getLinkedinHeadingElem(){
        return utilityPageObject.isElementVisible(this.linkedinHeadingElem);
    }

    @FindBy(className = "inventory_item")
    private WebElement inventoryItemElem;

    public List<WebElement> getInventoryItemElem(){
        return driver.findElements(By.className("inventory_item"));
    }
    public WebElement getLinkedinPageHeadingElem(){
        return utilityPageObject.isElementVisible(this.linkedinPageHeadingElem);
    }


    public String getValueOFLinkedinPageHeading(){
        return this.getLinkedinPageHeadingElem().getText();
    }


    public void addAndRemoveItemsInCart(String[] itemNames, Integer endIndex, @Optional Boolean removeFromCart) throws InterruptedException {
        boolean removeFromCartEnabled = removeFromCart != null && removeFromCart;

        int actualEndIndex = endIndex != null ? endIndex : itemNames.length - 1;

        for (int i = 0; i <= actualEndIndex; i++) {
            String itemName = itemNames[i];
            String modifiedItemName = itemName.replace(" ", "-").toLowerCase();
            String firstPart = removeFromCartEnabled ? "remove-" : "add-to-cart-";
            String itemId = firstPart + modifiedItemName;
            WebElement addOrRemoveCartButton = (getAddBtnElem(itemId));
            if (addOrRemoveCartButton != null) {
                delay();
                addOrRemoveCartButton.click();
            }
        }

    }
    public void logoutFromPortal(){
        clickBurgerBtnElem();
        clickLogoutSideBarLinkElem();
    }
    public boolean areProductsSortedByName(String sortingOption) {
        // Select the sorting option
        WebElement dropdownElement = getProductSortContainerElem();
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText(sortingOption);

        // Wait for the inventory to be sorted
        String inventoryNameElem = "inventory_item_name";
        String inventoryPriceElem = "inventory_item_price";
        utilityPageObject.waitForAllElem(inventoryNameElem,"class");

        // Get the list of product elements
        List<WebElement> productElements = getInventoryItemElem();

        // Extract product details (name and price)
        List<product> products = new ArrayList<>();
        for (WebElement productElement : productElements) {
            String name = productElement.findElement(By.className(inventoryNameElem)).getText();
            String priceString = productElement.findElement(By.className(inventoryPriceElem)).getText();
            double price = Double.parseDouble(priceString.substring(1)); // Remove the dollar sign and parse as double
            products.add(new product(name, price));
        }

        // Sort the products based on the selected sorting option
        switch (sortingOption) {
            case "Name (Z to A)":
                products.sort(Comparator.comparing(product::getName).reversed());
                break;
            case "Price (low to high)":
                products.sort(Comparator.comparing(product::getPrice));
                break;
            case "Price (high to low)":
                products.sort(Comparator.comparing(product::getPrice).reversed());
                break;
            default:
                products.sort(Comparator.comparing(product::getName));
                break;
        }

        // Verify the sorting order
        for (int i = 0; i < products.size(); i++) {
            String productNameOnPage = productElements.get(i).findElement(By.className(inventoryNameElem)).getText();
            if (!productNameOnPage.equals(products.get(i).getName())) {
                return false;
            }
        }

        return true;
    }
    public Map.Entry<Double, Double> getProductPriceRange(String sortingOption) {
        // Select the sorting option
        WebElement dropdownElement = getProductSortContainerElem();
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText(sortingOption);

        // Wait for the inventory to be sorted
        String inventoryPriceElem = "inventory_item_price";
        utilityPageObject.waitForAllElem(inventoryPriceElem, "class");

        // Get the list of product elements
        List<WebElement> productElements = getInventoryItemElem();

        // Extract product prices
        List<Double> prices = productElements.stream()
                .map(productElement -> productElement.findElement(By.className(inventoryPriceElem)).getText())
                .map(priceString -> Double.parseDouble(priceString.substring(1))) // Remove the dollar sign and parse as double
                .collect(Collectors.toList());

        // Sort the prices based on the selected sorting option
        switch (sortingOption) {
            case "Price (low to high)":
                prices.sort(Comparator.naturalOrder());
                break;
            case "Price (high to low)":
                prices.sort(Comparator.reverseOrder());
                break;
            default:
                // For other sorting options, do nothing as the prices are already in the expected order
                break;
        }

        // Get the lowest and highest prices
        double lowestPrice = prices.get(0);
        double highestPrice = prices.get(prices.size() - 1);

        return new AbstractMap.SimpleEntry<>(lowestPrice, highestPrice);
    }

}
