package mottu_spot.mvc;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseTest {
    protected WebDriver driver;
	protected Wait<WebDriver> wait;
    protected static String baseUrl = "http://localhost:8080";

    @BeforeEach
	void setUp() {
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(3));
	}

	@AfterEach
	void tearDown() {
		driver.quit();
	}


    protected void realizarLoginAdmin() {
		driver.get(baseUrl + "/login");
        
        WebElement usernameInput = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.id("username"))
        );
        usernameInput.sendKeys("admin");
        
        driver.findElement(By.id("password")).sendKeys("admin123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        wait.until(ExpectedConditions.urlContains("/"));
        
        assertTrue(driver.getCurrentUrl().contains("/"));
        
        System.out.println("Login admin realizado com sucesso!");
	}
}
