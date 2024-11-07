package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.Objects;

public class quality_assurance_page {

    public static void main(String[] args) {

        // Initialize the ChromeDriver
        WebDriver driver = new ChromeDriver();

//Test 3-4 Quality Assurance page
        try {
        // Navigate to the Quality Assurance careers page
        driver.get("https://useinsider.com/careers/quality-assurance/");

        // Maximize and accept cookies the browser window
        driver.manage().window().maximize();
        WebElement AcceptAllCookies = driver.findElement(By.id("wt-cli-accept-all-btn"));
        AcceptAllCookies.click();

        //Click on the "See all QA jobs" button
        WebElement seeAllQAJobsButton = driver.findElement(By.linkText("See all QA jobs"));
        seeAllQAJobsButton.click();

        // Select "Istanbul, Turkey" from the Location filter
        WebElement locationFilter = driver.findElement(By.id("filter-by-location"));

        // Check if the dropdown is hidden via the aria-hidden attribute
        if (Objects.equals(locationFilter.getAttribute("aria-hidden"), "true")) {
             //If hidden, set aria-hidden attribute too false to make it visible
            ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('aria-hidden', 'false');", locationFilter);
        }

        // Find the specific option for the desired location, e.g., "Istanbul"
        List<WebElement> options = locationFilter.findElements(By.tagName("option"));
        for (WebElement option : options) {
            if (option.getText().contains("Istanbul")) { // Adjust location name as needed
                option.click(); // Click to select the location
                break;
            }
        }

        // Select "Quality Assurance" from the Department filter
        WebElement departmentFilter = driver.findElement(By.id("filter-by-department"));
            Thread.sleep(3000);

            // Check if the dropdown is hidden via the aria-hidden attribute
            if (Objects.equals(departmentFilter.getAttribute("aria-hidden"), "true")) {
                //If hidden, set aria-hidden attribute too false to make it visible
                ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('aria-hidden', 'false');", departmentFilter);
            }

        WebElement qaOption = departmentFilter.findElement(By.xpath("//option[text()='Quality Assurance']"));
        qaOption.click();

        // Check for the presence of job listings
        List<WebElement> jobListings = driver.findElements(By.id("jobs-list"));

        if (!jobListings.isEmpty()) {
            System.out.println("Job listings are present for the specified filters.");
            for (WebElement listing : jobListings) {
                System.out.println("- " + listing.getText()); // Print each job listing's text

                WebElement viewRoleLink =  listing.findElement(By.xpath("//a[text()='View Role']"));

                // Get the href attribute of the link
                String hrefValue = viewRoleLink.getAttribute("href");

        // Check if the href contains "lever.co"
                assert hrefValue != null;
                if (hrefValue.contains("lever.co")) {
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
    } finally {
        // Close the browser
        driver.quit();
    }}
}



