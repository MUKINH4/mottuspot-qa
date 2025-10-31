package mottu_spot.mvc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class GestaoPatioTests extends BaseTest {

    @Test
	@DisplayName("Pátio vazio")
    @Order(3)
	void testPatioVazio() {

        // Dado que esteja logado
		realizarLoginAdmin();

		// Quando consultar pátio vazio (ex: pátio 2)
		WebElement acessarPatioBotao = driver.findElement(By.id("patio-2"));
		acessarPatioBotao.click();

		// Então o texto "Nenhum veículo encontrado nesse pátio" estará a mostra
		assert driver.findElement(By.id("patio-vazio")).isDisplayed();
	}

	@Test
	@DisplayName("Pátio com motos")
    @Order(2)
	void testPatioComMotos() {
		realizarLoginAdmin();

		// Dado que esteja num pátio com motos (ex: pátio 1)
		WebElement acessarPatioBotao = driver.findElement(By.id("patio-1"));
		acessarPatioBotao.click();

		// Quando o display for grid
		assert driver.findElement(By.className("grid")).isDisplayed();
		
		// Então existem motos no patio
	}

	@Test
	@DisplayName("Adicionar Pátio")
    @Order(1)
	void testAdicionarNovoPatio() {
		// Dado que esteja logado como admin
		realizarLoginAdmin();

		// Quando clicar no botão "adicionar pátio"
		WebElement botaoAdicionarPatio = driver.findElement(By.id("adicionar-patio"));
		botaoAdicionarPatio.click();

		// Então preencher os campos e enviar
		driver.findElement(By.id("nome"))
			.sendKeys("Pátio 2");
		driver.findElement(By.id("cep"))
			.sendKeys("41232-321");
		driver.findElement(By.id("logradouro"))
			.sendKeys("Rua dos sem dentes");
		driver.findElement(By.id("numero"))
			.sendKeys("61");
		driver.findElement(By.id("cidade"))
			.sendKeys("Cidade dos Sem dentes");
		driver.findElement(By.id("bairro"))
			.sendKeys("Bairro dos sem dentes");
		driver.findElement(By.id("estado"))
			.sendKeys("Estado dos sem dentes");
		driver.findElement(By.id("pais"))
			.sendKeys("País dos sem dentes");
		driver.findElement(By.id("lotacao"))
			.sendKeys("100");

		WebElement botaoSalvar = driver.findElement(By.className("btn"));
		botaoSalvar.click();

	}

}
