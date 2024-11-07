package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
//Test 1 Open Click Company
        driver.get("https://useinsider.com/");

        //companyButton = driver.findElement("")
        WebElement companyButton = driver.findElement(By.id("navbarDropdownMenuLink"));
        //WebElement companyButton = driver.findElement(By.linkText("Company"));
        //WebElement companyButton = driver.findElement(By.xpath("//a[text()=' Company ']"));
        companyButton.click();
        String indiderUrl = "https://useinsider.com/";

//Test 2 Click Career  Locations, Teams and Life at Insider blocks
        // TO DO normal click on Career button
        //WebElement careerButton = driver.findElement(By.id("navbarNavDropdown"));
        //WebElement careersLink = driver.findElement(By.xpath("//a[text()='Careers']"));
        //careersLink.click();

        driver.get("https://useinsider.com/careers/");

    // Check Career page for "Locations" block
        try {
            // Open the careers page
            driver.get("https://useinsider.com/careers/");

            // Array of city names to check
            // TO DO Automatically grab cities from the page
            String[] cities = {"New York", "Sao Paulo", "London", "Paris", "Amsterdam", "Barcelona", "Helsinki", "Warsaw", "Sydney", "Dubai", "Tokyo", "Seoul", "Singapore", "Bangkok", "Jakarta", "Taipei", "Manila", "Kuala Lumpur",
                    "Ho Chi Minh City", "Istanbul", "Ankara", "Mexico City", "Lima", "Buenos Aires", "Bogota", "Santiago" };
            // List to store missing locations
            List<String> missingLocations = new ArrayList<>();

            // Loop through each city and check if the "Locations" block is present for that city
            for (String city : cities) {
                // Construct the XPath dynamically with the current city
                String xpathExpression = String.format("//p[text()='%s']", city);
                List<WebElement> locationBlock = driver.findElements(By.xpath(xpathExpression));

                // Check if the location block for the city is present
                if (locationBlock.isEmpty()) {
                    missingLocations.add(city);
                }

            }
            // Output the results
            if (missingLocations.isEmpty()) {
                System.out.println("All locations are present on the page.");
            } else {
                System.out.println("The following locations are missing:");
                for (String missing : missingLocations) {
                    System.out.println("- " + missing);
                }
            }

            // Check Career page for "Teams" block
            List<WebElement> teamsBlock = driver.findElements(By.xpath("//a[text()='See all teams']"));
            boolean isTeamsBlockPresent = !teamsBlock.isEmpty();
            System.out.println("The 'Teams' block is " + (isTeamsBlockPresent ? "present" : "not present") + " on the page.");

            // Check Career page for "Life at Insider" block
            List<WebElement> lifeAtInsiderBlock = driver.findElements(By.xpath("//h2[text()='Life at Insider']"));
            boolean isLifeAtInsiderBlockPresent = !lifeAtInsiderBlock.isEmpty();
            System.out.println("The 'Life at Insider' block is " + (isLifeAtInsiderBlockPresent ? "present" : "not present") + " on the page.");

        } finally {
            // Close the browser
            //driver.quit();
        }

    //Test 3 Quality Assurance page

        try {
            // Navigate to the Quality Assurance careers page
            driver.get("https://useinsider.com/careers/quality-assurance/");

            // Maximize the browser window
            driver.manage().window().maximize();

            // Click on the "See all QA jobs" button
            //WebElement seeAllQAJobsButton = driver.findElement(By.linkText("See all QA jobs"));
            //seeAllQAJobsButton.click();


            // Wait for the page to load
            Thread.sleep(3000); // Adjust the sleep time as necessary

            // TO DO To click correctly to All QA

            driver.get("https://useinsider.com/careers/open-positions/?department=qualityassurance");

            // Select "Istanbul, Turkey" from the Location filter
            //WebElement locationFilter = driver.findElement(By.id("filter-by-location"));
            WebElement locationFilter = driver.findElement(By.id("select2-filter-by-location-container"));


            // Check if the dropdown is hidden via the aria-hidden attribute
            if (Objects.equals(locationFilter.getAttribute("aria-hidden"), "true")) {
                // If hidden, set aria-hidden attribute to false to make it visible
                ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('aria-hidden', 'false');", locationFilter);
            }

            // Wait for the page to load
            Thread.sleep(3000); // Adjust the sleep time as necessary

            locationFilter.click();

            // Find the specific option for the desired location, e.g., "Istanbul"
            List<WebElement> options = locationFilter.findElements(By.tagName("option"));
            for (WebElement option : options) {
                if (option.getText().contains("Istanbul")) { // Adjust location name as needed
                    option.click(); // Click to select the location
                    break;
                }
            }

            //WebElement istanbulOption = locationFilter.findElement(By.xpath("//option[text()='Istanbul, Turkey']"));
            //istanbulOption.click();

            // Select "Quality Assurance" from the Department filter
            WebElement departmentFilter = driver.findElement(By.id("department-filter"));
            departmentFilter.click();
            WebElement qaOption = departmentFilter.findElement(By.xpath("//option[text()='Quality Assurance']"));
            qaOption.click();

            // Check for the presence of job listings
            List<WebElement> jobListings = driver.findElements(By.cssSelector(".career-position-list .position"));

            if (!jobListings.isEmpty()) {
                System.out.println("Job listings are present for the specified filters.");
            } else {
                System.out.println("No job listings found for the specified filters.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }

}