package br.com.ada.testeautomatizado;

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
    public void testandoGoogle(){

        System.setProperty("webdriver.chrome.driver", "/home/alexaraujo/selenium/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);

        driver.get("https://google.com");

        String titulo = driver.getTitle();

        Assertions.assertEquals("Google", titulo);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        WebElement searchField = driver.findElement(By.name("q"));
        WebElement btnSearch = driver.findElement(By.name("btnI"));

        searchField.sendKeys("Ada Let's Code");
        btnSearch.click();

        driver.manage().timeouts().scriptTimeout(Duration.ofMillis(15000));

        WebElement element = driver.findElement(By.className("sc-d86dda7f-1 cmmKrF"));

        Assertions.assertEquals("Ada, a Nova Educação", element.getText());

        //driver.close();

    }

}
