package org.prac.pageobjects.util;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.prac.services.PageObjectManager;
import org.testng.annotations.Optional;

import javax.annotation.Nullable;
import java.time.Duration;

public class UtilsPageObject extends PageObjectManager {
    private final WebDriverWait wait;
    private final JavascriptExecutor js;

    public UtilsPageObject(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.js = (JavascriptExecutor) driver;
    }

    public WebElement isElementVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }


    public WebElement waitForElem(String elem, String locate) {
        return switch (locate) {
            case "class" -> wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(elem)));
            case "name" -> wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(elem)));
            case "xpath" -> wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elem)));
            default -> wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(elem)));
        };
    }
    public void waitForAllElem(String elem, String locate) {
        switch (locate) {
            case "class" -> wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className(elem)));
            case "name" -> wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.name(elem)));
            case "xpath" -> wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(elem)));
            default -> wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(elem)));
        }
    }

    public WebElement isElementClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void elementScrollDown(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true)", element);
    }
    public String handleNextTab(@Optional Boolean isNeeded) {
        String originalHandle = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        String currentUrl = driver.getCurrentUrl();

        if (Boolean.TRUE.equals(isNeeded)) {
            // Close the new tab
            driver.close();

            // Switch back to the original tab
            driver.switchTo().window(originalHandle);
        }
        return currentUrl;
    }
    public String handleNextTab() {
        return handleNextTab(true); // Call the other version of the method with isNeeded set to false by default
    }
}
