package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String insiderUrl = "https://useinsider.com/";

// Initialize the ChromeDriver
        //WebDriver driver = new ChromeDriver();
        WebDriver driver = new FirefoxDriver();


//Test 1 Open Click Company

        driver.get(insiderUrl);

        // Maximize the browser window
        driver.manage().window().maximize();
        WebElement AcceptAllCookies = driver.findElement(By.id("wt-cli-accept-all-btn"));
        AcceptAllCookies.click();

    // Check Career page for "Locations" block
        try {

            WebElement companyLink = driver.findElement(By.xpath("//a[normalize-space(text())='Company' and @id='navbarDropdownMenuLink']"));
            companyLink.click();

//Test 2 Click Career  Locations, Teams and Life at Insider blocks

            //WebElement careerButton = driver.findElement(By.id("navbarNavDropdown"));
            WebElement careersLink = driver.findElement(By.xpath("//a[text()='Careers']"));
            careersLink.click();

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
            driver.quit();
        }
    }

}