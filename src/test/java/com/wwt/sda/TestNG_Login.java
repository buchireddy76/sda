package com.wwt.sda;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.wwt.sda.LoginPage;
import com.wwt.sda.GetWebDriverType;

public class TestNG_Login {

	FileInputStream ConfigFIS = null, HomeFIS = null;
	protected WebDriver driver = null;

	Properties scriptproperties = null;
	public Options options = null;
	String BROWSER = null;

	DriverConfiguration driverPropertiesConfiguration = null;
	LoginPage logIn = null;
	HomePage homePage = null;
	WaitSleep waitDriver = null;
	String userId;
	String passWord;
	String invalidUserId;
	String invalidPassword;

	@BeforeTest
	public void pageObjectsCreation() throws Exception {
		ConfigFIS = new FileInputStream(".\\Configuration.properties");
		scriptproperties = new Properties();
		scriptproperties.load(ConfigFIS);
		BROWSER = scriptproperties.getProperty("CONFIG_BROWSERTYPE");

		GetWebDriverType driver1 = PageFactory.initElements(driver, GetWebDriverType.class);
		driver = driver1.getDriver(BROWSER);

		driverPropertiesConfiguration = PageFactory.initElements(driver, DriverConfiguration.class);
		driverPropertiesConfiguration.driverConfiguration(scriptproperties);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS); 

		logIn = PageFactory.initElements(driver, LoginPage.class);
		homePage = PageFactory.initElements(driver, HomePage.class);
		waitDriver = PageFactory.initElements(driver, WaitSleep.class);

		userId = scriptproperties.getProperty("CONFIG_LOGIN_USERNAME");
		passWord = scriptproperties.getProperty("CONFIG_LOGIN_PASSWORD");
		invalidUserId = scriptproperties.getProperty("CONFIG_INVALID_USERNAME");
		invalidPassword = scriptproperties.getProperty("CONFIG_INVALID_PASSWORD");
	}
	
	@Test(priority = 1, enabled = true)
	public void SDA_Invalid_Login_01() throws Exception {

		logIn.ClickOnlogIn(invalidUserId, passWord);	
 		assertTrue(logIn.verifyDisplyTextOnLogin("Login failed, please try again."), " Not able to Login with the given credentials");
	}

	@Test(priority = 2, enabled = true)
	public void SDA_Invalid_Password_02() throws Exception {

		logIn.ClickOnlogInTest(userId, invalidPassword);	
 		assertTrue(logIn.verifyDisplyTextOnLogin("Login failed, please try again."), " Not able to Login with the given credentials");
	}

	@Test(priority = 3, enabled = true)
	public void SDA_Login_03() throws Exception {

		logIn.ClickOnlogInTest(userId, passWord);	
		assertTrue(homePage.verifyDisplyUserName(" Buchi Ramidi "),"Application is logged with another user");
	}
}