/**
 * 
 */
package com.wwt.sda;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SerialNumbersPage {

	protected final WebDriver driver;

	@FindBy(xpath = "//*[@class='table table-striped']//*[@class='center']//input[@class='input-medium ng-pristine ng-valid']")
	private WebElement inputSerialNo;

	@FindBy(xpath = "//*[@class='table table-striped']//*[@class='center']//button[contains(text(),'1')]")
	private WebElement btn1;

	@FindBy(xpath = "//*[@class='well']//button[contains(text(),'Save')]")
	private WebElement btnSave;

	@FindBy(xpath = "//*[@class='row-fluid serial-header']/h3")
	private WebElement pageHeader;

	@FindBy(xpath = "//*[@class='well']//*[@class='alert alert-success']")
	private WebElement saveConfirm;
	
	@FindBy(xpath = "//*[@class='table table-striped']//*[@class='alert alert-info alert-no-close']//*[@class='ng-binding']")
	private List<WebElement>replacedUser;
	
	@FindBy(xpath = "//*[@class='table table-striped']//*[@ng-show='canReplaceSerials']//*[@class='btn btn-warning']")
	private WebElement replaceBtn;

	@FindBy(xpath = "//*[@class='row-fluid serial-header']//a[contains(text(),'Back to Order')]")
	private WebElement backToOrderBtn;

	public SerialNumbersPage(WebDriver driver) {
		this.driver = driver;
	}

	public void inputSerialNumber(String newSerial) {
		inputSerialNo.clear();
		inputSerialNo.sendKeys(newSerial);
	}

	public void clickSectorBtn() {
		btn1.click();
	}

	public void clickReplaceBtn() throws InterruptedException {
		Thread.sleep(2000);
		replaceBtn.click();
	}

	public void clickSaveBtn() {
		btnSave.click();
	}

	public void clickBackToOrderBtn() {
		backToOrderBtn.click();
	}
	// Verify the Title of Serial Number page
    public boolean verifyPageHeader()
    {
       	String textName = pageHeader.getText();
		System.out.println("Name of the Serial Numbers page header is :" +textName);
    	if(textName.contains("Serial Numbers")){
    		return true;
    	}
    	else
    		return false;
    }
    
	// Verifying the text after saving the Serial Nos. 
    public boolean verifyTextOnSave() throws InterruptedException
    {
    	Thread.sleep(2000);
       	String str = saveConfirm.getText();
       	String textName = str.trim();
		System.out.println("Verifying the text after saving the Serial nos :" +textName);
    	if(textName.contains("Saved successfully.")){
    		return true;
    	}
    	else
    		return false;
    }

	// Verifying the date update in the Kit column after saving the serial. 
    public boolean verifyReplacedUser(String userID)
    {
       	String userReplaced = replacedUser.get(0).getText();
		System.out.println("Verifying the User Name who has replaced :" +userReplaced);
		if(userReplaced.equals(userID)){
    		return true;
    	}
    	else
    		return false;
    }
    
	// Verifying the date update in the Kit column after saving the serial. 
    public boolean verifyReplacedDate()
    {
       	String replacedDate = replacedUser.get(1).getText();
		final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		final String dateString = dateFormat.format(Calendar.getInstance().getTime()); 
		System.out.println("Verifying the if the user has replaced on Current Date " +replacedDate);
		if(replacedDate.equals(dateString)){
    		return true;
    	}
    	else
    		return false;
    }
    
}



