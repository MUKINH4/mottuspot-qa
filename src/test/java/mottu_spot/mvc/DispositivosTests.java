package mottu_spot.mvc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DispositivosTests extends BaseTest {
    
    @Test
    @DisplayName("Acionar dispositivo")
    void testAcionarDispositivo() {
        // Dado que esteja logado como admin
        realizarLoginAdmin();

        // Quando acessar um pátio que tenha motos e localizar o botão localizar
        driver.findElement(By.id("patio-1")).click();

        WebElement btnLocalizar = driver.findElement(By.id("btn-1"));

        // Então aperte o botão localizar para ativar o dispositivo
        btnLocalizar.click();
    }

}
