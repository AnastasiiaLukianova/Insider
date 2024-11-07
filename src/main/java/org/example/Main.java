package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import static org.junit.Assert.assertEquals;


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
        //WebElement careerButton = driver.findElement(By.id("navbarNavDropdown"));
        //WebElement careersLink = driver.findElement(By.xpath("//a[text()='Careers']"));
        //careersLink.click();

        driver.get("https://useinsider.com/careers/");



    // Check Career page for "Locations" block
        try {
            // Open the careers page
            driver.get("https://useinsider.com/careers/");

            // Array of city names to check
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

           /* // Locate the element with id "location-slider"
            WebElement careerLocationElement = driver.findElement(By.id("location-slider"));

            // Find all <p> tags within this element
            List<WebElement> paragraphElements = careerLocationElement.findElements(By.tagName("p"));

            // Extract text from each <p> tag and store in a list
            List<String> paragraphTexts = new ArrayList<>();
            for (WebElement paragraph : paragraphElements) {
                paragraphTexts.add(paragraph.getText());
            }

            // Print out the extracted texts
            System.out.println("Texts from <p> tags within 'career-our-location' element:");
            for (String text : paragraphTexts) {
                System.out.println(text);
            }*/


        } finally {
            // Close the browser
            driver.quit();
        }
    }

}