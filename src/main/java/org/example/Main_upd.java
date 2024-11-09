package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main_upd {
    public static void main(String[] args) {
        String insiderUrl = "https://useinsider.com/";
        String browser = System.getProperty("browser", "chrome");
        boolean isHeadless = false; // Set this to true or false based on your requirements
        WebDriver driver = null;

        // Try to initialize the WebDriver automatically (if supported by Selenium 4)
        driver = createDriverAutomatically(browser,isHeadless);

        // Fallback to manual setup if automatic creation fails
        if (driver == null) {
            driver = setupDriver(browser);
        }

        try {
            // Open the URL and maximize the browser window
            driver.get(insiderUrl);
            driver.manage().window().maximize();

            // Accept cookies
            WebElement acceptAllCookies = driver.findElement(By.id("wt-cli-accept-all-btn"));
            acceptAllCookies.click();

            // Click on the "Company" link
            WebElement companyLink = driver.findElement(By.xpath("//a[normalize-space(text())='Company' and @id='navbarDropdownMenuLink']"));
            companyLink.click();

            // Click on the "Careers" link
            WebElement careersLink = driver.findElement(By.xpath("//a[text()='Careers']"));
            careersLink.click();

            // Array of city names to check
            String[] cities = {"New York", "Sao Paulo", "London", "Paris", "Amsterdam", "Barcelona", "Helsinki", "Warsaw", "Sydney",
                    "Dubai", "Tokyo", "Seoul", "Singapore", "Bangkok", "Jakarta", "Taipei", "Manila", "Kuala Lumpur",
                    "Ho Chi Minh City", "Istanbul", "Ankara", "Mexico City", "Lima", "Buenos Aires", "Bogota", "Santiago"};
            List<String> missingLocations = new ArrayList<>();

            // Check for the "Locations" block for each city
            for (String city : cities) {
                String xpathExpression = String.format("//p[text()='%s']", city);
                List<WebElement> locationBlock = driver.findElements(By.xpath(xpathExpression));

                if (locationBlock.isEmpty()) {
                    missingLocations.add(city);
                }
            }

            // Print the results for missing locations
            if (missingLocations.isEmpty()) {
                System.out.println("All locations are present on the page.");
            } else {
                System.out.println("The following locations are missing:");
                for (String missing : missingLocations) {
                    System.out.println("- " + missing);
                }
            }

            // Check "Teams" block
            List<WebElement> teamsBlock = driver.findElements(By.xpath("//a[text()='See all teams']"));
            boolean isTeamsBlockPresent = !teamsBlock.isEmpty();
            System.out.println("The 'Teams' block is " + (isTeamsBlockPresent ? "present" : "not present") + " on the page.");

            // Check "Life at Insider" block
            List<WebElement> lifeAtInsiderBlock = driver.findElements(By.xpath("//h2[text()='Life at Insider']"));
            boolean isLifeAtInsiderBlockPresent = !lifeAtInsiderBlock.isEmpty();
            System.out.println("The 'Life at Insider' block is " + (isLifeAtInsiderBlockPresent ? "present" : "not present") + " on the page.");

        } catch (Exception e) {
            // Take a screenshot in case of an exception
            takeScreenshot(driver, "error_screenshot");
            e.printStackTrace();
        } finally {
            // Close the browser
            if (driver != null) {
                driver.quit();
            }
        }
    }

    // Method to automatically create the driver if Selenium 4 or higher is used
    public static WebDriver createDriverAutomatically(String browser,boolean isHeadless) {
        try {
            switch (browser.toLowerCase()) {
                case "chrome":
                // Set up ChromeOptions
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if (isHeadless) {
                        chromeOptions.addArguments("--headless"); // Enable headless mode
                        chromeOptions.addArguments("--disable-gpu"); // Recommended for headless mode
                    }
                    return new ChromeDriver(chromeOptions); // Selenium 4 supports direct creation

                case "firefox":
                    // Set up FirefoxOptions
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (isHeadless) {
                        // Enable headless mode for Firefox
                        firefoxOptions.addArguments("--headless");
                    }
                    return new FirefoxDriver(firefoxOptions); // Selenium 4 supports direct creation

                default:
                    System.out.println("Unsupported browser for automatic setup: " + browser);
                    return null;
            }
        } catch (Exception e) {
            System.out.println("Automatic driver creation failed for browser: " + browser);
            return null;
        }
    }

    // Fallback method for manual setup using System.setProperty
    public static WebDriver setupDriver(String browser) {
        WebDriver driver;
        try {
            switch (browser.toLowerCase()) {
                case "chrome":
                    // Set the path to the ChromeDriver executable
                    System.setProperty("webdriver.chrome.driver", "C:\\path\\to\\chromedriver.exe");
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    // Set the path to the GeckoDriver executable
                    System.setProperty("webdriver.gecko.driver", "C:\\path\\to\\geckodriver.exe");
                    driver = new FirefoxDriver();
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browser);
            }
            System.out.println("Browser initialized using manual setup: " + browser);
        } catch (Exception e) {
            System.out.println("Failed to manually set up driver for browser: " + browser);
            e.printStackTrace();
            driver = null;
        }
        return driver;
    }

    // Method to take a screenshot
    public static void takeScreenshot(WebDriver driver, String fileName) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileHandler.copy(screenshot, new File(fileName + ".png"));
            System.out.println("Screenshot saved as: " + fileName + ".png");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
