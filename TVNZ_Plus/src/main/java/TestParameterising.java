import dev.failsafe.internal.util.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class TestParameterising {

    ChromeDriver driver = null;
    public void openDriver(){
        WebDriverManager.chromedriver().setup();
        driver= new ChromeDriver();
        driver.navigate().to("https://www.tvnz.co.nz");
        driver.manage().window().fullscreen();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        System.out.println("Site loading successful....");
    }
    public void closeDriver(){
        driver.close();
        driver.quit();
    }


    public void registrationPageLogoPresent(){
        openDriver();
        driver.findElement(By.xpath("//a[text()='Login']")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.findElement(By.xpath("//span[text()='Sign up now']")).click();
        if(driver.findElement(By.xpath("//img[@alt='TVNZ Plus Logo']")).isDisplayed()){
            Assert.isTrue(true,"Element present");
            System.out.println("Element present");
        }

        closeDriver();
    }

//    public void registraion(String randomAlphabeticString, String randomAlphabeticString1, String s, String valueOf, String male, String randomAlphabeticString2) {
//        openDriver();
//        driver.findElement(By.xpath("//a[text()='Login']")).click();
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
//        driver.findElement(By.xpath("//span[text()='Sign up now']")).click();
//        driver.findElement(By.id("email")).sendKeys(email_address);
//        driver.findElement(By.id("password")).sendKeys(password);
//        driver.findElement(By.id("firstName")).sendKeys(fname);
//        driver.findElement(By.id("lastName")).sendKeys(lname);
//        driver.findElement(By.xpath("//div[@name='yearOfBirth']")).click();
//        driver.findElement(By.xpath("//div[.='"+yearOfBirth+"']")).click();
//        driver.findElement(By.xpath("//div[@name='gender']")).click();
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        driver.findElement(By.xpath("//div[@id='gender']/div[.='"+ gender+"']")).click();
//        driver.findElement(By.cssSelector("div:nth-child(1) > .Checkbox .Checkbox-label")).click();
//        driver.findElement(By.cssSelector("div:nth-child(2) .Checkbox-label")).click();
//        driver.findElement(By.xpath("//span[text()='Sign Me Up']")).click();
//
//        closeDriver();
//    }
}
