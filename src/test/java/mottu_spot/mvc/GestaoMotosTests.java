package mottu_spot.mvc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

public class GestaoMotosTests extends BaseTest {
	
    @Test
	@DisplayName("Adicionar Moto")
	@Order(1)
	void testAdicionarNovaMoto() {
		// Dado que esteja logado como admin
		realizarLoginAdmin();

		// Quando entrar em um pátio
		driver.findElement(By.id("patio-1")).click();

		// Então clique no botão adicionar moto, preencha os campos e clique em salvar moto
		driver.findElement(By.id("adicionar-moto")).click();

		driver.findElement(By.id("placa")).sendKeys("ABC-1234");
		driver.findElement(By.id("descricao")).sendKeys("Boa para uso.");

		driver.findElement(By.cssSelector("button[type='submit']")).click();
	}

	@Test
	@DisplayName("Editar Moto")
	@Order(2)
	void testEditarMoto() {
		// Dado que esteja logado como admin
		realizarLoginAdmin();

		// Quando entrar em um pátio com motos
		driver.findElement(By.id("patio-1")).click();

		// Então clique no botão editar na moto desejada, altere os campos e salve
		driver.findElement(By.id("editar-moto-1")).click();

		driver.findElement(By.id("descricao")).clear();
		driver.findElement(By.id("descricao")).sendKeys("Descrição atualizada.");

		driver.findElement(By.cssSelector("button[type='submit']")).click();
	}

	@Test
	@DisplayName("Remover Moto")
	@Order(3)
	void testRemoverMoto() {
		// Dado que esteja logado como admin
		realizarLoginAdmin();

		// Quando entrar em um pátio com motos
		driver.findElement(By.id("patio-1")).click();

		// Então clique no botão remover na moto desejada
		driver.findElement(By.id("remover-moto-2")).click();
	}

	
}
