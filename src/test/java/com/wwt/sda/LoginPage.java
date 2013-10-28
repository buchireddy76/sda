package com.wwt.sda;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {

	protected final WebDriver driver;

	@FindBy(id = "content")
	private WebElement iframe1;

	@FindBy(id = "credentials")
	private WebElement iframe2;

	@FindBy(id = "username")
	private WebElement userNameTxt;

	@FindBy(id = "password")
	private WebElement passwordTxt;

	@FindBy(css = "#submit")
	private WebElement login;

	@FindBy(xpath = "//*[@class='instructions']")
	private WebElement txtMessage;

	@FindBy(xpath = "//*[@id='user-menu']//*[@class='dropdown-toggle']")
	private WebElement userName;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	public void ClickOnlogIn(String usrName, String pwd) throws Exception {

		driver.switchTo().frame(iframe1);
		driver.switchTo().frame(iframe2);

		userNameTxt.sendKeys(usrName);
		passwordTxt.sendKeys(pwd);

		System.out.println("Logging with username : " +usrName+ " and "+pwd);
		login.click();
	}
	
	public void ClickOnlogInTest(String usrName, String pwd) throws Exception {

		userNameTxt.sendKeys(usrName);
		passwordTxt.sendKeys(pwd);

		System.out.println("Logging with username : " +usrName+ " and "+pwd);
		login.click();
	}

	public boolean verifyDisplyTextOnLogin(String expectedInvalidMsg)
    {
    	boolean verifyTextMsg = false;
       	String textName = txtMessage.getText();
       	System.out.println("Login Failed : "+textName);
    	if(textName.equals(expectedInvalidMsg))
    	{
    		verifyTextMsg = true;
    	}
    	return verifyTextMsg;
    }

    public boolean verifyDisplyUserName(String expectedUserName)
    {
    	boolean verifyUserName = false;
       	String usrName = userName.getText();
       	System.out.println("Successfully Logged in with  : "+usrName);
    	if(usrName.equals(expectedUserName))
    	{
    		verifyUserName = true;
    	}
    	return verifyUserName;
    }
}
