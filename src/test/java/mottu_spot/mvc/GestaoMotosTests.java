package mottu_spot.mvc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

public class GestaoMotosTests extends BaseTest {
    @Test
	@DisplayName("Adicionar Moto")
	void testAdicionarNovaMoto() {
		// Dado que esteja logado como admin
		realizarLoginAdmin();

		// Quando entrar em um pátio
		driver.findElement(By.id("patio-1")).click();

		// Então clique no botão adicionar moto e então preencha os campos e clique em salvar moto
		driver.findElement(By.id("adicionar-moto")).click();

		driver.findElement(By.id("placa")).sendKeys("ABC-1234");
		driver.findElement(By.id("descricao")).sendKeys("Boa para uso.");

		driver.findElement(By.cssSelector("button[type='submit']")).click();
	}
}
