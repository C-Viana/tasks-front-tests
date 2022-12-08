package tasks.functional;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TasksTest {
	
	public WebDriver getDriver() throws MalformedURLException {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setAcceptInsecureCerts(true);
//		chromeOptions.setCapability("platformName", "Linux");
//		WebDriver driver = new RemoteWebDriver(new URL("http://127.0.0.1:4444/"), chromeOptions);
		System.setProperty("webdriver.chrome.driver", "C:\\Desenvolvimento\\WebDrivers\\chromedriver_108.exe");
		WebDriver driver = new ChromeDriver(chromeOptions);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
		driver.get("http://192.168.0.103:8901/tasks/");
		return driver;
	}
	
	@Test
	public void t01naoDeveCriarTarefaComDataPassada() throws MalformedURLException, InterruptedException {
		WebDriver driver = getDriver();
		try {
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("Teste funcional");
			driver.findElement(By.id("dueDate")).sendKeys(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now().minusDays(1)));
			driver.findElement(By.id("saveButton")).click();
			Assert.assertEquals("Due date must not be in past", driver.findElement(By.id("message")).getText().trim());
		} finally {
			driver.quit();
			driver = null;
		}
	}
	
	@Test
	public void t02naoDeveCriarTarefaSemNome() throws MalformedURLException, InterruptedException {
		WebDriver driver = getDriver();
		try {
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("dueDate")).sendKeys(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now().plusDays(2)));
			driver.findElement(By.id("saveButton")).click();
			Assert.assertEquals("Fill the task description", driver.findElement(By.id("message")).getText().trim());
		} finally {
			driver.quit();
			driver = null;
		}
	}
	
	@Test
	public void t03naoDeveCriarTarefaSemData() throws MalformedURLException, InterruptedException {
		WebDriver driver = getDriver();
		try {
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("Teste funcional");
			driver.findElement(By.id("saveButton")).click();
			Assert.assertEquals("Fill the due date", driver.findElement(By.id("message")).getText().trim());
		} finally {
			driver.quit();
			driver = null;
		}
	}
	
	@Test
	public void t04deveCriarTarefaComSucesso() throws MalformedURLException, InterruptedException {
		WebDriver driver = getDriver();
		try {
			driver.findElement(By.id("addTodo")).click();
			String taskDescription = "Teste funcional"+new Random().nextInt();
			driver.findElement(By.id("task")).sendKeys(taskDescription);
			driver.findElement(By.id("dueDate")).sendKeys(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now().plusDays(2)));
			driver.findElement(By.id("saveButton")).click();
			Assert.assertEquals("Success!", driver.findElement(By.id("message")).getText().trim());
		} finally {
			driver.quit();
			driver = null;
		}
	}
	
	@Test
	public void t05deveRemoverTarefaComSucesso() throws MalformedURLException, InterruptedException {
		WebDriver driver = getDriver();
		
		try {
			if( driver.findElements(By.xpath("//td[text()='Tarefa para remover']")).size() == 0 ) {
				driver.findElement(By.id("addTodo")).click();
				driver.findElement(By.id("task")).sendKeys("Tarefa para remover");
				driver.findElement(By.id("dueDate")).sendKeys(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now().plusDays(2)));
				driver.findElement(By.id("saveButton")).click();
			}
			driver.findElement(By.xpath("//td[text()='Tarefa para remover']/following-sibling::td/a")).click();
			Assert.assertEquals("Success!", driver.findElement(By.id("message")).getText().trim());
		} finally {
			driver.quit();
			driver = null;
		}
	}
	
}
