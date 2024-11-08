package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class upd_quality_assurance_page {

    public static void main(String[] args) {
        String browser = System.getProperty("browser", "chrome");
        WebDriver driver = null;

        // Determine Selenium version and set up the driver accordingly
        try {
            // Try automatic driver setup (Selenium 4+)
            driver = createDriverAutomatically(browser);
        } catch (Exception e) {
            System.out.println("Automatic driver creation failed. Falling back to manual setup.");
            driver = setupDriverManually(browser);
        }

        try {
            // Navigate to the Quality Assurance careers page
            driver.get("https://useinsider.com/careers/quality-assurance/");
            driver.manage().window().maximize();

            // Accept cookies
            WebElement acceptAllCookies = driver.findElement(By.id("wt-cli-accept-all-btn"));
            acceptAllCookies.click();

            // Click on the "See all QA jobs" button
            WebElement seeAllQAJobsButton = driver.findElement(By.linkText("See all QA jobs"));
            seeAllQAJobsButton.click();

            // Select "Istanbul, Turkey" from the Location filter
            WebElement locationFilter = driver.findElement(By.id("filter-by-location"));
            if (Objects.equals(locationFilter.getAttribute("aria-hidden"), "true")) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('aria-hidden', 'false');", locationFilter);
            }

            List<WebElement> options = locationFilter.findElements(By.tagName("option"));
            for (WebElement option : options) {
                if (option.getText().contains("Istanbul")) {
                    option.click();
                    break;
                }
            }

            // Select "Quality Assurance" from the Department filter
            WebElement departmentFilter = driver.findElement(By.id("filter-by-department"));
            if (Objects.equals(departmentFilter.getAttribute("aria-hidden"), "true")) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('aria-hidden', 'false');", departmentFilter);
            }

            Thread.sleep(3000);

            WebElement qaOption = departmentFilter.findElement(By.xpath("//option[text()='Quality Assurance']"));
            qaOption.click();

            // Check for the presence of job listings
            List<WebElement> jobListings = driver.findElements(By.id("jobs-list"));
            if (!jobListings.isEmpty()) {
                System.out.println("Job listings are present for the specified filters.");
                for (WebElement listing : jobListings) {
                    System.out.println("- " + listing.getText());
                    WebElement viewRoleLink = listing.findElement(By.xpath(".//a[text()='View Role']"));
                    String hrefValue = viewRoleLink.getAttribute("href");

                    if (hrefValue != null && hrefValue.contains("lever.co")) {
                        System.out.println("The link contains a Lever URL: " + hrefValue);
                    } else {
                        System.out.println("The link does not contain a Lever URL.");
                    }
                }
            } else {
                System.out.println("No job listings found for the specified filters.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            takeScreenshot(driver, "error_screenshot");
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    // Method for automatic driver creation (Selenium 4+)
    public static WebDriver createDriverAutomatically(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                return new ChromeDriver();
            case "firefox":
                return new FirefoxDriver();
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    // Method for manual driver setup (Selenium 3 and below)
    public static WebDriver setupDriverManually(String browser) {
        WebDriver driver;
        try {
            switch (browser.toLowerCase()) {
                case "chrome":
                    System.setProperty("webdriver.chrome.driver", "C:\\path\\to\\chromedriver.exe");
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    System.setProperty("webdriver.gecko.driver", "C:\\path\\to\\geckodriver.exe");
                    driver = new FirefoxDriver();
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browser);
            }
            return driver;
        } catch (Exception e) {
            System.out.println("Failed to manually set up the driver for browser: " + browser);
            e.printStackTrace();
            return null;
        }
    }

    // Method to take a screenshot
    public static void takeScreenshot(WebDriver driver, String fileName) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            String directory = System.getProperty("user.dir") + File.separator;
            FileHandler.copy(screenshot, new File(directory + fileName + ".png"));
            System.out.println("Screenshot saved at: " + directory + fileName + ".png");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
