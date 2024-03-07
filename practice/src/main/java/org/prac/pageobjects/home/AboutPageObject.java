package org.prac.pageobjects.home;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.prac.pageobjects.util.UtilsPageObject;
import org.prac.services.PageObjectManager;


public class AboutPageObject extends PageObjectManager {

    UtilsPageObject utilityPageObject = new UtilsPageObject(driver);
    public AboutPageObject(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//header/div[1]/div[1]/div[1]/div[1]/a[1]/div[1]/span[1]/img[1]")
    private WebElement aboutPageLogoElem;

    public WebElement getAboutPageLogoElem(){
        return utilityPageObject.isElementVisible(this.aboutPageLogoElem);
    }
    @FindBy(xpath = "//body/div[@id='__next']/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]")
    private WebElement aboutPageHeadingElem;

    public WebElement getAboutPageHeadingElem(){
        return utilityPageObject.isElementVisible(this.aboutPageHeadingElem);
    }
    public String getValueOFAboutPageHeading(){
        this.getHeadingLastSpanElem();
        return this.getAboutPageHeadingElem().getText();
    }

    @FindBy(xpath = "//body/div[@id='__next']/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/h1[2]/span[29]")
    private WebElement headingLastSpanElem;

    public WebElement getHeadingLastSpanElem(){
        return utilityPageObject.isElementVisible(this.headingLastSpanElem);
    }

}
