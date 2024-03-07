package org.prac.services;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.*;

import java.io.ByteArrayInputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DriverManager {

    private static final Logger logger = Logger.getLogger(DriverManager.class.getName());

    private static WebDriver webDriver;

    public static WebDriver getWebDriver() {
        return webDriver;
    }

    public static Properties getDataFromFile() {
        return FileManager.getProps("data");
    }
    public static Properties getErrorFromFile() {
        return FileManager.getProps("error");
    }

    static String baseUrl = getDataFromFile().getProperty("base_Url");

    @BeforeClass
    @Parameters({"Browser", "Environment"})
    public static void setUp(@Optional("chrome") String browser, @Optional("default") String env) {
        logger.info("Setting up WebDriver");
        try {

            setUpWebDriver(browser);
            setUpEnvironment(env);
            webDriver.manage().window().maximize();
        } catch (Exception e) {
            logger.severe("Error setting up WebDriver: " + e.getMessage());
            tearDown();
        }
    }

    private static void     setUpWebDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                webDriver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                webDriver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                webDriver = new EdgeDriver();
                break;
            default:
                logger.log(Level.SEVERE, () -> "Unsupported browser: " + browser);
        }
    }

    private static void setUpEnvironment(String env) {
        switch (env.toLowerCase()) {
            case "default":
                navigateToURL(baseUrl);
                break;
            case "qa":
                navigateToURL(getDataFromFile().getProperty("qa_base_Url"));
                break;
            default:
                logger.log(Level.SEVERE, () -> "Unsupported Environment: " + env);
                throw new SkipException("Unsupported Environment: " + env);
        }
    }

    public static void navigateToURL(String url) {
        logger.log(Level.INFO, () -> "Navigating to Environment: " + url);
        webDriver.get(url);
    }
    protected void delay() throws InterruptedException {
       Thread.sleep(2000);
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            // Capture screenshot
            TakesScreenshot screenshot = (TakesScreenshot) webDriver;
            byte[] screenshotData = screenshot.getScreenshotAs(OutputType.BYTES);

            // Attach screenshot to Allure report
            Allure.addAttachment("Screenshot", new ByteArrayInputStream(screenshotData));
            Assert.fail("Following: " + result.getName()+" "+"Failed");
            webDriver.manage().deleteAllCookies();
            webDriver.close();
            webDriver.quit();
        }
    }
    @AfterClass
    public static void tearDown() {
        logger.info("tearDown");
        if (webDriver != null) {
            webDriver.manage().deleteAllCookies();
            webDriver.quit();
        }
    }
}
