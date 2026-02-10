package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SeleniumJupiter.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class CreateProductFunctionalTest {

    @LocalServerPort
    private int port;

    @Value("${app.baseUrl:http://localhost}")
    private String baseUrl;

    private String fullUrl;

    @BeforeEach
    void setUp() {
        fullUrl = baseUrl + ":" + port;
    }

    @Test
    void createProduct_success(ChromeDriver driver) {
        // Open create page
        driver.get(fullUrl + "/product/create");

        // Fill form
        driver.findElement(By.id("nameInput"))
                .sendKeys("Selenium Product");

        driver.findElement(By.id("quantityInput"))
                .sendKeys("5");

        // Submit
        driver.findElement(By.tagName("button")).click();

        // Redirected to list page
        assertTrue(driver.getCurrentUrl().contains("/product/list"));

        // Verify product appears
        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Selenium Product"));
    }
}
