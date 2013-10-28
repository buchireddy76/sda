package com.wwt.sda;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ShipmentInfoDialog {

	protected final WebDriver driver;

	@FindBy(xpath = "//*[@class='modal hide ng-scope in']//*[@class='modal-header']/h3")
	private WebElement shipmentInfoHeader;

	@FindBy(xpath="//*[@class='modal hide ng-scope in']//*[@class='btn btn-primary ng-scope ng-binding']")
	private WebElement saveSubmitBtn;

	@FindBy(xpath="//*[@class='modal hide ng-scope in']//*[@class='btn ng-binding']")
	private WebElement backToBOMBtn;

	@FindBy(xpath = "//option[contains(text(),'Dayton EC / Ryder')]")
	private List<WebElement> selectLocationFromOption;

	@FindBy(xpath = "//option[contains(text(),'Galasso Trucking, Inc. - Maspeth, NY')]")
	private List<WebElement> selectToLocationOption;

	@FindBy(xpath= "//*[@class='modal hide ng-scope in']//*[@id='textAreaSpecialRequirements']")
	private WebElement inputSpecialRequirements;

	@FindBy(xpath="//*[@class='modal hide ng-scope in']//*[@id='releaseTypeRadio2']")
	private WebElement radioRequestRelease;

	@FindBy(xpath="//*[@class='modal hide ng-scope in']//*[@id='releaseTypeRadio1']")
	private WebElement radioAutoRelease;

	public ShipmentInfoDialog(WebDriver driver) {
		this.driver = driver;
	}

	// Entering the Special requirements in the Text Area 
	public void enterSplRequirement(String specialRequirement) throws InterruptedException{
		inputSpecialRequirements.click();
		inputSpecialRequirements.clear();
		System.out.println("Entering Special Requirements as " +specialRequirement);
		Thread.sleep(1000);
		inputSpecialRequirements.sendKeys(specialRequirement);
	}

	public void clickSaveSubmitBtn() {
		saveSubmitBtn.click();
	}

	public void clickBackToBOMBtn() {
		backToBOMBtn.click();
	}

	public void clickReleaseTypeBtn(String type) {
		System.out.println("Selecting the Release type with "+type);
		if (type.equals("Auto")) {
			radioAutoRelease.click();
		} else if (type.equals("Request")){
			radioRequestRelease.click();
		}
	}
	
	// Selecting From Location from the drop down
	public void selectShipLocationFrom(String fromLocation) {
		selectLocationFromOption.clear();
		System.out.println("Selecting From Location for Shipping : " +fromLocation);
		for (int i = 0; i < selectLocationFromOption.size(); i++) {
			
			if (selectLocationFromOption.get(i).getText().equals(fromLocation)) {
				selectLocationFromOption.get(i).click();
			}
		}
	}

	// Selecting Location to from the drop down
	public void selectShipToLocation(String toLocation) {
		selectToLocationOption.clear();
		System.out.println("Selecting Location To for Shipment : " +toLocation);
		for (int i = 0; i < selectToLocationOption.size(); i++) {
			if (selectToLocationOption.get(i).getText().equals(toLocation)) 
				selectToLocationOption.get(i).click();
		}
	}
	
	// Click on the Check Out Button
	public boolean verifyDialogHeader() {
       	String titleName = shipmentInfoHeader.getText();
		System.out.println("Title name on Shipment Info Dialog is : "+titleName);
    	if(titleName.contains("Shipment Info")){
    		return true;
    	}
    	else {
    		return false;
    	}
	}
	
    public boolean verifySaveSubmitBtn()
    {
       	String textName = saveSubmitBtn.getText();
		System.out.println("Verifying " +textName+ " button on the Shipment Info Dialog ");
    	if(textName.equals("Save and Submit"))
    		return true;
    	else {
    		System.out.println("Button name is : " +textName );
    		return false;
    	}
    }

    public boolean verifyBacktoBOMBtn()
    {
       	String textName = backToBOMBtn.getText();
		System.out.println("Verifying " +textName+ " button on the Shipment Info Dialog ");
    	if(textName.equals("Back to BOM"))
    		return true;
    	else {
    		System.out.println("Button name is : " +textName );
    		return false;
    	}
    }
	
}