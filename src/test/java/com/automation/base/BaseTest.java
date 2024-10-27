package com.automation.base;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.ISelect;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automation.utility.ExtentReportsUtility;
import com.google.common.io.Files;

import LogTests.LogTests;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	protected static WebDriver driver = null;
	private WebDriverWait wait=null;
	protected Logger mylog=LogManager.getLogger(BaseTest.class);
	//private Logger mylog=LogManager.getLogger(BaseTest.class);
	protected ExtentReportsUtility extentReportUtility = ExtentReportsUtility.getInstance();

	public void launchBrowser(String browserName) {
		switch (browserName.toLowerCase()) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		case "edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;

		default:
			break;
		}
	}

	public void goToUrl(String url) throws InterruptedException {
		driver.get(url);
		mylog.info(url + "is entered");
		Thread.sleep(5000);

	}

	public void enterText(WebElement ele, String data, String objectName) {
		if (ele.isDisplayed()) {
			ele.clear();
			ele.sendKeys(data);
			mylog.info("data is entered in the " + objectName);
		} else {
			mylog.error(objectName + " textbox is not diplayed");
		}
	}

	public void clickElement(WebElement ele, String objectName) {
		if (ele.isEnabled()) {
			ele.click();
			mylog.info(objectName + " button is clicked");
		} else {
			mylog.error(objectName + " button is not diplayed");
		}
	}

	public void selectElement(WebElement ele, String objectName) {
		if (!ele.isSelected()) {
			ele.click();
			mylog.info(objectName + " button is selected");
		} else {
			mylog.error(objectName + " button is already selected");
		}

	}

	public String selectByValue(WebElement ele, String value) {
		Select select = new Select(ele);
		// select.deselectAll();
		select.selectByValue(value);
		return value;
	}

	public int selectByIndex(WebElement ele, int value) {
		Select select = new Select(ele);
		// select.deselectAll();
		select.selectByIndex(value);
		return value;
	}

	public String selectByVisibleText(WebElement ele, String value) {
		Select select = new Select(ele);
		// select.deselectAll();
		select.selectByVisibleText(value);

		return value;
	}

	public void closeDriver() {
		driver.close();
	}

	public void maximizeScreen() {
		driver.manage().window().maximize();

	}

	public Alert switchToAlert() {
		Alert alert = driver.switchTo().alert();
		mylog.info("The curser is moved switched to alert");
		return alert;
	}

	public void alertGetText(Alert alert, String objectname) {
		mylog.info("Getting the text in the " + objectname + "alert");
		String text = alert.getText();
		mylog.info("Alert text is " + text);
	}

	public void acceptAlert(Alert alert, String objectname) {
		alert.accept();
		mylog.info("alert" + objectname + "has been accepted");
	}

	public void dismissAlert(Alert alert, String objectname) {
		alert.dismiss();
		mylog.info("alert " + objectname + "has been accepted");
	}

	public String getTextFromWebElement(WebElement ele, String objectName) {
		String text = null;
		if (ele.isDisplayed()) {
			text = ele.getText();
			mylog.info("text is extracted from " + objectName);
		} else {
			mylog.error("text is not extracted from " + objectName);
		}

		return text;

	}

	public void mouseOverElement(WebElement ele, String objectName) {
		Actions action = new Actions(driver);
		action.moveToElement(ele).build().perform();
		mylog.info("curser moved to element = " + objectName);
	}

	public void quitDriver() {
		driver.quit();
	}

	public void waitForVisibility(WebElement ele, long timeInsec, String objectName) {
		mylog.info(objectName + "waiting for visibility for maximum of " + timeInsec + "sec");
		 wait = new WebDriverWait(driver, timeInsec);
		wait.until(ExpectedConditions.invisibilityOf(ele));
	}

	public void waitForAlertToPresent(long timeInsec, String objectName) {
		mylog.info(objectName + "waiting for Alert to be present " + timeInsec + "sec");
		wait = new WebDriverWait(driver, timeInsec);
		wait.until(ExpectedConditions.alertIsPresent());
	}

	public void waitForElementToBeClicked(WebElement ele, long timeInsec, String objectName) {
		mylog.info(objectName + "waiting for elemnt to be clicked " + timeInsec + "sec");
		 wait = new WebDriverWait(driver, timeInsec);
		wait.until(ExpectedConditions.elementToBeClickable(ele));
	}

	public void waitFortextToBePresent(WebElement ele, long timeInsec, String text, String objectName) {
		mylog.info(objectName + "waiting for string to be presend " + timeInsec + "sec");
		wait = new WebDriverWait(driver, timeInsec);
		wait.until(ExpectedConditions.textToBePresentInElement(ele, text));
	}

	public void waitForWindowtToBePresent(long timeInsec, int numOfWindows, String objectName) {
		mylog.info(objectName + "waiting  to be presend " + timeInsec + "sec");
		wait = new WebDriverWait(driver, timeInsec);
		wait.until(ExpectedConditions.numberOfWindowsToBe(numOfWindows));
	}

	public void implicitWait(long timeInsec) {
		driver.manage().timeouts().implicitlyWait(timeInsec, TimeUnit.SECONDS);
	}

	public void takeScreenshot(String filepath) {

		TakesScreenshot screenCapture = (TakesScreenshot) driver;// typcasting webdriver driver to takescreenshot type
		File src = screenCapture.getScreenshotAs(OutputType.FILE);// screenshot
		File destFile = new File(filepath);
		try {
			Files.copy(src, destFile);
			mylog.info("screen captured");
		} catch (IOException e) { // TODO Auto-generated catch block e.printStackTrace();
			mylog.error("problem occured during screenshot taking");
		}
	}
	
	public void takePartialScreenshot(WebElement ele, String filepath) {
		File src = ele.getScreenshotAs(OutputType.FILE); // getScreenshotAs method is used directely to take the screenshot of webelement.
		File destFile = new File(filepath);
		try {
			Files.copy(src, destFile);
			mylog.info("screen captured");
		} catch (IOException e) { // TODO Auto-generated catch block e.printStackTrace();
			mylog.error("problem occured during screenshot taking");
		}
	}
}
