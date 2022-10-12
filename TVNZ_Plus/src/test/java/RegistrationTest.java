import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RegistrationTest {

    public String RandomAlphabeticString(int stringLength) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
                .limit(stringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public int GenerateBirthYear(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }

    @BeforeEach
    public void startBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.tvnz.co.nz/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @AfterEach
    public void quitBrowser() {
        driver.close();
        driver.quit();
    }

    WebDriver driver = null;
    static String email = null;
    static String password = null;


    @Test
    public void registrationWithCodeInTestMethod() {
        String gender = "Male";
        email = RandomAlphabeticString(5) + "@aaa.com";
        password = RandomAlphabeticString(8);
        if (driver.findElement(By.xpath("//a[text()='Login']")).isDisplayed()) {
            driver.findElement(By.xpath("//a[text()='Login']")).click();
            driver.findElement(By.xpath("//span[text()='Sign up now']")).click();
            driver.findElement(By.id("email")).sendKeys(email);
            driver.findElement(By.id("password")).sendKeys(password);
            driver.findElement(By.id("firstName")).sendKeys(RandomAlphabeticString(4));
            driver.findElement(By.id("lastName")).sendKeys(RandomAlphabeticString(3));
            driver.findElement(By.xpath("//div[@name='yearOfBirth']")).click();
            driver.findElement(By.xpath("//div[.='" + String.valueOf(GenerateBirthYear(1920, 2000)) + "']")).click();
            driver.findElement(By.xpath("//div[@name='gender']")).click();
            driver.findElement(By.xpath("//div[@id='gender']/div[.='" + gender + "']")).click();
            driver.findElement(By.xpath("//label[@for='houseRules']//span")).click();
            driver.findElement(By.xpath("//label[@for='latestNews']/div/div")).click();
            driver.findElement(By.xpath("//button[.='Sign Me Up']")).click();
        } else {
            System.out.println("page not displayed");
        }
    }

    @Test
    public void logIn() throws InterruptedException {
//        Assertions.assertTrue(driver.findElement(By.xpath()));
        System.out.println(driver.findElement(By.xpath("//li[@id='Categories']//a[text()='Natural World']")).isDisplayed());
        driver.findElement(By.xpath("//a[text()='Login']")).click();
        System.out.println(driver.getCurrentUrl());
        driver.findElement(By.xpath("//div[@class='Input-error']")).isDisplayed();
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.xpath("//span[.='Login']/..")).click();
    }

    @Test
    public void categoriesDropdown() {

        List<WebElement> elements = driver.findElements(By.xpath("//li[@id='Categories']//li//a"));
        for (WebElement e : elements ) {
            System.out.println(e.getAttribute("text"));
        }
    }

    @Test
    public void getTopPicks() {

        List<WebElement> topPicks = driver.findElements(By.xpath("//h2[@data-anchor='TopPicks']/../../..//div[@class='swiper-wrapper']//a/div"));
        for (WebElement e : topPicks) {
            System.out.println(e.getAttribute("id"));
        }
    }

    @Test
    //Homework
    //print items in movies belt
    public void getMoviesBeltItems() {

        Actions builder = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60),Duration.ofSeconds(5));
        builder.moveToElement(driver.findElement(By.xpath("//h2[@id='anchor-Movies']/../../..//div[@class='swiper-wrapper']//a/div"))).build().perform();

        WebElement element = driver.findElement(By.xpath("//h2[@data-anchor='Movies']/../../..//span[@aria-label='Next slide']/div[@class='swiper-button-next swiper-no-swiping']"));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        while (element.isDisplayed()){
            element.click();
            //      Thread.sleep(1000);
        }

        List<WebElement> moviesBelt = driver.findElements(By.xpath("//h2[@data-anchor='Movies']/../../..//div[@class='swiper-wrapper']//a/div"));
        int count = 0;
        for (WebElement e : moviesBelt) {
            System.out.println(e.getAttribute("id"));
            count ++;
        }
        System.out.println(count);
    }


    @Test
    //Exercise
    //Hover over on a belt item and get the description
    public void getMoviesBeltItemDescription() {

        Actions builder = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60),Duration.ofSeconds(5));
        builder.moveToElement(driver.findElement(By.xpath("//div[@id='Entrapment']"))).build().perform();

        List<WebElement> moviesBelt = driver.findElements(By.xpath("//div[@id='Entrapment']//div[@class='QuickInfo-overview']/child::*"));
              for (WebElement e : moviesBelt) {
            System.out.println(e.getAttribute("QuickInfo-synopsis"));
                   }

    }

    @Test
    public void viewMoreClick() throws InterruptedException {
        Actions builder = new Actions(driver);
        List<WebElement> moviesBelt = driver.findElements(By.xpath("//h2[@id='anchor-Movies']/../../..//div[@class='swiper-wrapper']//a/div"));
        Thread.sleep(3000);
        builder.moveToElement(moviesBelt.get(0)).perform();

        Thread.sleep(3000);

    }

    @Test
    //Getting the synopsis
    public void gettingSynopsis() throws InterruptedException {
        Actions builder = new Actions(driver);
        List<WebElement> movies = driver.findElements(By.xpath("//h2[@id='anchor-Movies']/../../..//div[@class='swiper-wrapper']//a/div"));
        Thread.sleep(3000);
        builder.moveToElement(movies.get(2)).perform();
        Thread.sleep(3000);
        System.out.println(movies.get(2).getAttribute("innerHTML"));
        System.out.println(movies.get(2).findElement(By.cssSelector("div.QuickInfo-synopsis")).getText());
        Thread.sleep(3000);

    }



    @Test
    //Homework 7/10/22
    //Getting screenshot of categories list drop down

    public void screenShotCategoryList() throws InterruptedException, IOException {
        Actions builder = new Actions(driver);
        List<WebElement> categories = driver.findElements(By.xpath("//a[@title='Categories']"));
        Thread.sleep(3000);
        builder.moveToElement(categories.get(0)).perform();
        Thread.sleep(3000);
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File screenShotFile = screenshot.getScreenshotAs(OutputType.FILE);
        File destination = new File("/Users/ibandra/Automation/TVNZ_Plus/target/categoryList.png");
        FileUtils.copyFile(screenShotFile, destination);

    }
}