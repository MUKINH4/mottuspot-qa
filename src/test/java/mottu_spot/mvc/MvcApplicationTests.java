package mottu_spot.mvc;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MvcApplicationTests {

	WebDriver driver;
	Wait<WebDriver> wait;

    @BeforeEach
	void setUp() {
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(3));
	}

	@AfterEach
	void tearDown() {
		driver.quit();
	}

	private static String baseUrl = "http://localhost:8080";

	@Test
	@DisplayName("Login Admin com Sucesso")
    void testLoginAdminComSucesso() {
        System.out.println("Iniciando teste de login admin");
        
        realizarLoginAdmin();

    }

	@Test
	@DisplayName("Pátio vazio")
	void testPatioVazio() {
		realizarLoginAdmin();

		// Dado que esteja num pátio vazio (ex: pátio 10)
		WebElement acessarPatioBotao = driver.findElement(By.id("patio-10"));
		acessarPatioBotao.click();

		// Quando o texto for igual a "Nenhum veículo encontrado nesse pátio"
		assert driver.findElement(By.id("patio-vazio")).isDisplayed();
		
		// Então não existem motos no patio
	}

	@Test
	@DisplayName("Pátio com motos")
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

	void realizarLoginAdmin() {
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
