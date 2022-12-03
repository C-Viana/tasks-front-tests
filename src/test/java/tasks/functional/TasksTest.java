package tasks.functional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TasksTest {
	
	public WebDriver getDriver() {
		if(System.getProperty("webdriver.chrome.driver") == null)
			System.setProperty("webdriver.chrome.driver", "C:\\Desenvolvimento\\WebDrivers\\chromedriver_108.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("http://localhost:8901/tasks/");
		return driver;
	}
	
	@Test
	public void t01naoDeveCriarTarefaComDataPassada() {
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
	public void t02naoDeveCriarTarefaSemNome() {
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
	public void t03naoDeveCriarTarefaSemData() {
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
	public void t04deveCriarTarefaComSucesso() {
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
	
//	public void t05deveRemoverTarefaComSucesso() {
//		WebDriver driver = getDriver();
//		driver.findElement(By.xpath("//td[text()='Teste funcional']/following-sibling::td/a")).click();
//		Assert.assertEquals("Success!", driver.findElement(By.id("message")).getText().trim());
//	}
	
}
