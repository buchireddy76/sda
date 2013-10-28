package com.wwt.sda;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ReviewOrderDialog {

	protected final WebDriver driver;

	@FindBy(xpath = "//*[@class='modal hide ng-scope in']//*[@class='modal-header']/h3")
	private WebElement reviewOrderDlgHeader;

	@FindBy(xpath="//*[@class='modal hide ng-scope in']//*[@class='btn btn-primary']")
	private WebElement enterShipInfoBtn;

	@FindBy(xpath="//*[@class='modal hide ng-scope in']//*[@class='btn']")
	private WebElement backToBOMBtn;

	public ReviewOrderDialog(WebDriver driver) {
		this.driver = driver;
	}

	public void clickEnterShipInfoBtn() {
		enterShipInfoBtn.click();
	}

	public void clickBackToBOMBtn() {
		backToBOMBtn.click();
}

	// verify the Header name on the Review Order Dialog
	public boolean verifyDialogHeader() {
       	String titleName = reviewOrderDlgHeader.getText();
       	System.out.println("Verifying the Header name on Review Order Dialog :"+titleName);
    	if(titleName.contains("Review Order")){
    		return true;
    	}
    	else{
    		System.out.println("Title name of the Review Order Dialog is : "+titleName);
    		return false;
    	}
	}

    public boolean verifyEnterShipInfoBtn()
    {
       	String textName = enterShipInfoBtn.getText();
       	System.out.println("Verifying " +textName+ " button on Review Order Dialog ");
    	if(textName.equals("Enter Shipping Info"))
    		return true;
    	else {
    		System.out.println("Button name is : " +textName );
    		return false;
    	}
    }

    public boolean verifyBacktoBOMBtn()
    {
       	String textName = backToBOMBtn.getText();
       	System.out.println("Verifying " +textName+ " button on Review Order Dialog ");
    	if(textName.equals("Back to BOM"))
    		return true;
    	else {
    		System.out.println("Button name is : " +textName );
    		return false;
    	}
    }
}