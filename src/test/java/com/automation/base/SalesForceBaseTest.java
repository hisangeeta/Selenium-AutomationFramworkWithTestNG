package com.automation.base;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.automation.utility.Constants;
import com.automation.utility.PropertyFileUtility;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SalesForceBaseTest extends BaseTest {
	//private Logger mylog=LogManager.getLogger(SalesForceBaseTest.class);

	public void login() {
		WebElement UserNameTextBox = driver.findElement(By.id("username"));
		// BaseTest bs= new BaseTest();
		enterText(UserNameTextBox, "aama@tekarch.com", "userName");

		WebElement PasswordTextBox = driver.findElement(By.id("password"));
		enterText(PasswordTextBox, "mother@1", "password");

		WebElement RememeberMe = driver.findElement(By.xpath("//*[@id=\"rememberUn\"]"));
		clickElement(RememeberMe, "remember me");

		WebElement Login = driver.findElement(By.id("Login"));
		clickElement(Login, "login");

	}

	public void UsernameDropdown(String Option) {
		List<WebElement> UsernameDropdowns = driver.findElements(By.xpath("//*[@id=\"userNav-menuItems\"]/a"));

		for (WebElement UsernameDropdown : UsernameDropdowns) {
			if (UsernameDropdown.getText().contains(Option)) {
				UsernameDropdown.click();
				mylog.info(UsernameDropdown + "is clicked");
				break;
			}
		}

	}

	@BeforeMethod
	@Parameters("browserName")
	public void setUpBeforeMethod(@Optional("chrome") String name) {
		mylog.info("*********************@BeforeMethod********************* ");
		launchBrowser("chrome");
		maximizeScreen();
		String url = PropertyFileUtility.readDataFromPropertyFile(Constants.APPLICATION_PROPERTIES, "url");
		try {
			goToUrl(url);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@AfterMethod
	public void tearDownMethod() {
		mylog.info("*********************@teardown method********************* ");
		closeDriver();
	}
}
