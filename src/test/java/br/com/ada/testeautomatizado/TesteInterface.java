package br.com.ada.testeautomatizado;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;

public class TesteInterface {


    @Autowired
    private WebDriver driver;

    @Test
    public void testandoGoogle() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "/home/alexaraujo/selenium/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

        driver.get("https://google.com/");

        String titulo = driver.getTitle();

        Assertions.assertEquals("Google", titulo);

        WebElement searchField = driver.findElement(By.name("q"));
        WebElement btnSearch = driver.findElement(By.name("btnI"));

        searchField.sendKeys("Ada Let's Code");
        btnSearch.click();

        WebElement element = driver.findElement(By.xpath("//*[contains(@class, 'sc-d86dda7f-1 cmmKr')]"));

        Assertions.assertEquals("Ada, a Nova Educação", element.getText());

    }

    @AfterEach
    public void after(){
        driver.quit();
    }

}
