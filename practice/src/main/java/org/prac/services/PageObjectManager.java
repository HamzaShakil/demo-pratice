package org.prac.services;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class PageObjectManager {
    protected WebDriver driver;

    public PageObjectManager(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    protected void delay() throws InterruptedException {
        Thread.sleep(2000); // Delay for 20 seconds
    }
}
