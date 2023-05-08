package br.com.ada.testeautomatizado;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.HashMap;

public class TesteInterface {


    @Autowired
    private WebDriver driver;

    @Disabled
    public void testandoGoogle() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "/home/alexaraujo/selenium/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

        openUrl("https://google.com/");

        String titulo = driver.getTitle();

        Assertions.assertEquals("Google", titulo);

        WebElement searchField = driver.findElement(By.name("q"));
        WebElement btnSearch = driver.findElement(By.name("btnI"));

        searchField.sendKeys("Ada Let's Code");
        btnSearch.click();

        long start = System.currentTimeMillis();

        WebElement element = driver.findElement(By.xpath("//*[contains(@class, 'sc-d86dda7f-1 cmmKr')]"));

        Assertions.assertEquals("Ada, a Nova Educação", element.getText());

        long end = System.currentTimeMillis();

        System.out.printf("\n\nTempo %d \n", (end - start)/1000);

    }

    @Disabled
    public void testandoMercadoLivre() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "/home/alexaraujo/selenium/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-user-media-security");
        HashMap<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 1);
        prefs.put("profile.default_content_settings.cookies", 2);
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

        openUrl("https://www.mercadolivre.com.br/");

        WebElement searchField = driver.findElement(By.id("cb1-edit"));
        WebElement btnSearch = driver
                .findElement(By.xpath("//*[contains(@class, 'nav-search-btn')]"));

        searchField.sendKeys("PS5");
        btnSearch.click();

        WebElement element = driver.findElement(By.xpath("//*[contains(@class, 'ui-search-search-result__quantity-results shops-custom-secondary-font')]"));
        Assertions.assertEquals("755 resultados", element.getText());

    }

    private void openUrl(String url) {
        driver.get(url);
    }

    @AfterEach
    public void after() {
        driver.quit();
    }

}
