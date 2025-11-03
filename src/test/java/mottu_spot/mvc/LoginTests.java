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

        // Dado que esteja na página de login
        driver.get(baseUrl + "/login");


        // Quando preencher o formulário com credenciais válidas de usuário comum e enviar
        WebElement usernameInput = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.id("username"))
        );
        usernameInput.sendKeys("user");
        
        driver.findElement(By.id("password")).sendKeys("comum123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        // Então o login é bem-sucedido e o usuário é redirecionado para a página inicial
        wait.until(ExpectedConditions.urlContains("/"));
        
        assertTrue(driver.getCurrentUrl().contains("/"));
        
        System.out.println("Login comum realizado com sucesso!");
    }

    @Test
    @DisplayName("Login com credenciais inválidas")
    void testLoginComCredenciaisInvalidas() {

        // Dado que esteja na página de login
        driver.get(baseUrl + "/login");

        // Quando preencher o formulário com credenciais inválidas e enviar
        WebElement usernameInput = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.id("username"))
        );
        usernameInput.sendKeys("invalidUser");
        
        driver.findElement(By.id("password")).sendKeys("wrongPassword");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Então uma mensagem de erro é exibida
        WebElement errorMessage = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.id("error-message"))
        );
        
        assertTrue(errorMessage.isDisplayed());
        
        System.out.println("Mensagem de erro exibida para credenciais inválidas.");
    }

    @Test
    @DisplayName("Logout com Sucesso")
    void testLogoutComSucesso() {
        // Dado que esteja logado como admin
        realizarLoginAdmin();

        // Quando clicar no botão de logout
        WebElement botaoLogout = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.id("btn-logout"))
        );
        botaoLogout.click();

        // Então o usuário é deslogado e redirecionado para a página de login
        wait.until(ExpectedConditions.urlContains("/login"));

        assertTrue(driver.getCurrentUrl().contains("/login"));

        System.out.println("Logout realizado com sucesso!");
    }

}