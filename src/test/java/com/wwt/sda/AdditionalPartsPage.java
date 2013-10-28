package com.wwt.sda;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdditionalPartsPage {
	
	protected final WebDriver driver;

	@FindBy(xpath = "//div[contains(@id,'partials-additionalItems')]//h3[contains(text(),'RFDS MATERIALS Additional Parts')]")
	private WebElement additonalRFDSHeader;

	@FindBy(xpath = "//div[contains(@id,'partials-additionalItems')]//h3[contains(text(),'HYBRIFLEX Additional Parts')]")
	private WebElement additonalHybriflexHeader;

	@FindBy(xpath = "//div[contains(@id,'partials-additionalItems')]//h3[contains(text(),'CONSTRUCTION MATERIALS Additional Parts')]")
	private WebElement additonalCMHeader;

	@FindBy(xpath = "//div[contains(@id,'partials-additionalItems')]//tr[@class='ng-scope']")
	private List<WebElement> noOfRowsInTable;

	@FindBy(xpath = "//*[@class='table table-striped']//*[@class='input-mini ng-pristine ng-valid']")
	private WebElement inputQtyFirstRow;

	@FindBy(xpath = "//*[@class='modal hide ng-scope in']//*[@class='btn btn-primary']")
	private WebElement btnAddItems;

	@FindBy(xpath = "//*[@class='modal hide ng-scope in']//*[@class='btn']")
	private WebElement btnCancel;

	public AdditionalPartsPage(WebDriver driver) {
		this.driver = driver;
	}

	public boolean verifyDialogHeader(String tableName) throws InterruptedException {
		Thread.sleep(1000);
		boolean headerText = false;
		if (tableName.equals("RFDS Materials")) {
	       	String titleName = additonalRFDSHeader.getText();
			System.out.println("Header name on " +tableName+ "Dialog is : "+titleName);
	    	if(titleName.contains("RFDS MATERIALS Additional Parts")){
	    		headerText= true;
	    	}
	    	else {
	    		headerText = false;
	    	}
		}
		if (tableName.equals("Hybriflex")) {
	       	String titleName = additonalHybriflexHeader.getText();
			System.out.println("Header name on " +tableName+ "Dialog is : "+titleName);
	    	if(titleName.contains("HYBRIFLEX Additional Parts")){
	    		headerText= true;
	    	}
	    	else {
	    		headerText = false;
	    	}
		}
		if (tableName.equals("Construction Materials")) {
	       	String titleName = additonalCMHeader.getText();
			System.out.println("Header name on " +tableName+ "Dialog is : "+titleName);
	    	if(titleName.contains("CONSTRUCTION MATERIALS Additional Parts")){
	    		headerText= true;
	    	}
	    	else {
	    		headerText = false;
	    	}
		}
		return headerText;
	}

	public void inputOrderQty(String value) {
		inputQtyFirstRow.clear();
		inputQtyFirstRow.sendKeys(value);
	}
	
	public void clickAddItemsBtn() throws InterruptedException {
		btnAddItems.click();
		Thread.sleep(1000);
	}

	public void clickCancelBtn() {
		btnCancel.click();
	}

	public void selectTableDetails(String partNo, String value) {
		int rowsInTable = noOfRowsInTable.size();

		List<WebElement> partNoInTable = noOfRowsInTable.get(1).findElements(By.xpath("//td[2]"));
		List<WebElement> valueToSet = noOfRowsInTable.get(1).findElements(By.xpath("//td/input"));

		System.out.println("value of Part No to update is : " + partNo+ " with value : "+ value);
		for (int i = 0; i < rowsInTable; i++) {
			if (partNoInTable.get(i).getText().contains(partNo)){
				System.out.println("Updating Row value in RFDS Table with : "+ value);
				valueToSet.get(i).sendKeys(value);
				break;
			}
		}
	}

	public boolean findTheElement(WebDriver driverTemp,By by) {
		boolean elementFound = false;
		for(int seconds = 0; seconds<600; seconds++) {
			try{
				if(driver.findElements(by).size()>0){
					elementFound = true;
					break;
				}
				Thread.sleep(100);
			}catch(Exception ex){
				System.out.println("Element not found" + by);
				return elementFound;
			}
		}
		return elementFound;
	}
}


