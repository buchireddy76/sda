package com.wwt.sda;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ConfirmDialog {

	protected final WebDriver driver;

	@FindBy(xpath = "//*[@class='modal hide ng-scope in']//*[@class='modal-header']")
	private WebElement confirmHeader;

	@FindBy(xpath="//button[contains(text(),'Back to BOM')]")
	private WebElement backToBOMBtn;

	@FindBy(xpath="//button[contains(text(),'Continue')]")
	private WebElement continueBtn;

	public ConfirmDialog(WebDriver driver) {
		this.driver = driver;
	}

	public void clickContinueBtn() {
			continueBtn.click();
	}

	public void clickBackToBOMBtn() {
			backToBOMBtn.click();
	}

	// verifying header on the Confirm Dialog
	public boolean verifyDialogHeader() {
       	String dialogName = confirmHeader.getText();
		System.out.println("Verifying Dialog header name : " +dialogName);
    	if(dialogName.contains("Confirm Order")) {
    		return true;
    	}
    	else{
    		System.out.println("Header name of the Confirm Dialog is : "+dialogName);
    		return false;
    	}
	}
}