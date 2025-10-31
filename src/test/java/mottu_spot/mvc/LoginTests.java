package mottu_spot.mvc;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

class LoginTests extends BaseTest {

    @Test
    @DisplayName("Login Admin com Sucesso")
    void testLoginAdminComSucesso() {
        realizarLoginAdmin();
    }

    @Test
    @DisplayName("Login usuário comum")
    void testLoginUsuarioComumSucesso() {
        driver.get(baseUrl + "/login");

        WebElement usernameInput = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.id("username"))
        );
        usernameInput.sendKeys("user");
        
        driver.findElement(By.id("password")).sendKeys("comum123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        wait.until(ExpectedConditions.urlContains("/"));
        
        assertTrue(driver.getCurrentUrl().contains("/"));
        
        System.out.println("Login comum realizado com sucesso!");
    }

}