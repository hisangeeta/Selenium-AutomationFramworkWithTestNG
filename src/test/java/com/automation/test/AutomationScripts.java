package com.automation.test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.automation.base.BaseTest;
import com.automation.base.SalesForceBaseTest;
import com.automation.utility.Constants;
import com.automation.utility.PropertyFileUtility;
import com.automation.utility.XLUtility;

public class AutomationScripts extends SalesForceBaseTest {
	//private Logger mylog=LogManager.getLogger(AutomationScripts.class);
	

	@Test
	public void invalid_password_login() {

		WebElement UserNameTextBox = driver.findElement(By.id("username"));
		String username = PropertyFileUtility.readDataFromPropertyFile(Constants.APPLICATION_PROPERTIES, "username");
		enterText(UserNameTextBox, username, "Username");

		WebElement Password = driver.findElement(By.className("label"));
		enterText(Password, "123 ", "Password");

		WebElement Login = driver.findElement(By.id("Login"));
		clickElement(Login, "login Button");

		WebElement Errormessage = driver.findElement(By.id("error"));
		String Actual_Error_msg = getTextFromWebElement(Errormessage, "Actual Error");
		String Expected_Error_Msg = "Please enter your password.";
		Assert.assertEquals(Actual_Error_msg, Expected_Error_Msg);

		/*
		 * alertGetText(switchToAlert(), "Password Missing");
		 * acceptAlert(switchToAlert(), "Password missing");
		 */
		mylog.info("actual message is same as expected");
		extentReportUtility.logTestInfo("actual message is same as expected");

	}
	@DataProvider(name="logindata")
	public Object[][] readData(){


			return XLUtility.readAllDataFromXLToArray(Constants.Salesforce_XLDATA,"Sheet1");
		
	}

@Test(dataProvider="logindata")
	public void valid_login(String username, String password  ) {

		/*String username = PropertyFileUtility.readDataFromPropertyFile(Constants.APPLICATION_PROPERTIES, "username");
		String password = PropertyFileUtility.readDataFromPropertyFile(Constants.APPLICATION_PROPERTIES, "password");*/
		
		/*
		 * launchBrowser("chrome"); maximizeScreen();
		 */
		String url=PropertyFileUtility.readDataFromPropertyFile(Constants.APPLICATION_PROPERTIES, "url");
		//driver.get("https://login.salesforce.com/");
		WebElement UserNameTextBox = driver.findElement(By.id("username"));
		UserNameTextBox.sendKeys(username);

		WebElement PasswordTextBox = driver.findElement(By.id("password"));
		PasswordTextBox.sendKeys(password);

		WebElement RememeberMe = driver.findElement(By.xpath("//*[@id=\"rememberUn\"]"));
		clickElement(RememeberMe, "remember me");

		WebElement Login = driver.findElement(By.id("Login"));
		clickElement(Login, "login Button");
		waitForVisibility(Login, 3, "login button");
		String ExpectedText = "Home Page ~ Salesforce - Developer Edition";
		String ActualText = driver.getTitle();

		mylog.info(ActualText);//log4j
		try {
			Assert.assertEquals(ActualText, ExpectedText);
			extentReportUtility.logTestInfo("Actual title is same as expected so test case passed");//extent report
			
		} catch (Throwable e) {
			String filename=new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
			takeScreenshot(Constants.SCREENSHOTS_DIRECTORY_PATH+filename+".png");
			Assert.fail("Actual text is" + ActualText + "and expected test is" + ExpectedText);
			extentReportUtility.logTestFailedWithException(e);
		}
	}
	
}