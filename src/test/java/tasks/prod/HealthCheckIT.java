package tasks.prod;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class HealthCheckIT {
	
	public WebDriver getDriver() throws MalformedURLException {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setCapability("platformName", "Linux");
		chromeOptions.setAcceptInsecureCerts(true);
		WebDriver driver = new RemoteWebDriver(new URL("http://127.0.0.1:4444/"), chromeOptions);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("http://192.168.0.103:9999/tasks/");
		return driver;
	}
	
	@Test
	public void heathCheckProd() throws MalformedURLException, InterruptedException {
		WebDriver driver = getDriver();
		try {
			String textBuildVersion = driver.findElement(By.id("version")).getText();
			Assert.assertTrue(textBuildVersion.startsWith("build"));
		} finally {
			driver.quit();
			driver = null;
		}
	}
	
	
	
}
