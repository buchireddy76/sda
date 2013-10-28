/**
 * 
 */
package com.wwt.sda;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrderFormPage {

	protected final WebDriver driver;

	@FindBy(xpath = "//*[@class='accordion-heading']//*[@href='#category0']")
	private WebElement tableRFDSHeaders;

	@FindBy(xpath = "//*[@class='accordion']//*[@href='#category1']")
	private WebElement tableHFlexHeaders;

	@FindBy(xpath = "//*[@class='accordion-heading']//*[@href='#category2']")
	private WebElement tableCMHeaders;

	@FindBy(xpath = "//*[@id='category0']/div/table/tbody/tr/td[1]/span")
	private List<WebElement> tableRFDSRows;
	
	@FindBy(xpath = "//*[@id='category1']/div/table/tbody/tr/td[1]/span")
	private List<WebElement> tableHFlexRows;
	
	@FindBy(xpath = "//*[@id='category2']/div/table/tbody/tr/td[1]/span")
	private List<WebElement> tableCMRows;
	
	@FindBy(xpath = "//*[@class='row-fluid bom-header ng-scope']")
	private List<WebElement> ordersFormPageTitle;

	@FindBy(xpath="//*[@class='well']//*[@class='btn btn-large btn-primary']")
	private WebElement checkOutBtn;

	@FindBy(xpath="//*[@id='category0']//*[@class='btn']")
	private WebElement AddPartsRFDSBtn;

	@FindBy(xpath="//*[@id='category1']//*[@class='btn']")
	private WebElement AddPartsHFlexBtn;

	@FindBy(xpath="//*[@id='category2']//*[@class='btn']")
	private WebElement AddPartsCMBtn;

	@FindBy(xpath="//*[@class='well']//*[@class='btn btn-large btn-warning']")
	private WebElement cancelBtn;

	@FindBy(xpath = "//*[@id='categoryCollapse']/div")
	private List<WebElement> ordersFormTable;

	public OrderFormPage(WebDriver driver) {
		this.driver = driver;
	}

	public void clickCheckOutBtn() {
		checkOutBtn.click();
	}

	public void clickCancelBtn() {
		cancelBtn.click();
	}

	public void clickRFDSPartsBtn() {
		AddPartsRFDSBtn.click();
	}

	public void clickHFlexPartsBtn() {
		AddPartsHFlexBtn.click();
	}

	public void clickCMPartsBtn() {
		AddPartsCMBtn.click();
	}

	public void selectRFDSDetails() {
		int rowsInRFDS = tableRFDSRows.size();
		List<WebElement> rowsInTable = ordersFormTable.get(1).findElements(By.xpath("//div/div/table/tbody/tr/td[1]/span"));
		List<WebElement> valueToSet = ordersFormTable.get(1).findElements(By.xpath("//div/div/table/tbody/tr/td[5]/input"));

		for (int i = 0; i < rowsInRFDS; i++) {
			if (rowsInTable.get(i).getAttribute("class").equals("ng-binding badge badge-important")) {
				String value = rowsInTable.get(i).getText().replace("-", "");
				System.out.println("Updating Row value in RFDS with : "+ value);
				valueToSet.get(i).clear();
				valueToSet.get(i).sendKeys(value);
			}
		}
	}

	public void selectHFlexDetails() throws InterruptedException {
		int rowsInRFDS = tableRFDSRows.size();
		JavascriptExecutor js = (JavascriptExecutor) this.driver;
		js.executeScript("window.scrollTo(0,500); return true");

		tableHFlexHeaders.click();
		int rowsInHFlex = tableHFlexRows.size();
		int totalRowsHFlex = rowsInRFDS + rowsInHFlex;
		
		List<WebElement> rowsInTable = ordersFormTable.get(1).findElements(By.xpath("//div/div/table/tbody/tr/td[1]/span"));
		List<WebElement> valueToSet = ordersFormTable.get(1).findElements(By.xpath("//div/div/table/tbody/tr/td[5]/input"));

		for (int i = rowsInRFDS; i < totalRowsHFlex; i++) {
			if (rowsInTable.get(i).getAttribute("class").equals("ng-binding badge badge-important")) {
				String value = rowsInTable.get(i).getText().replace("-", "");
				System.out.println("Updating Row value in Hybriflex with : "+ value);
				valueToSet.get(i).clear();
				valueToSet.get(i).sendKeys(value);
			}
		}
	}

	public void selectCMDetails() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) this.driver;
		js.executeScript("window.scroll(document.body.scrollHeight,0); return true");
		Thread.sleep(1000);
		tableRFDSHeaders.click();
		int rowsInRFDS = tableRFDSRows.size();
		js = (JavascriptExecutor) this.driver;
		js.executeScript("window.scrollTo(0,500); return true");
		Thread.sleep(1000);

		tableHFlexHeaders.click();
		int rowsInHFlex = tableHFlexRows.size();
		js = (JavascriptExecutor) this.driver;
		js.executeScript("window.scrollTo(0,1000); return true");
		Thread.sleep(1000);
		tableCMHeaders.click();
		int totalRowsHFlex = rowsInRFDS + rowsInHFlex;
		int rowsInCM = tableCMRows.size();
		int totalTableRows = totalRowsHFlex + rowsInCM;
		
		List<WebElement> rowsInTable = ordersFormTable.get(1).findElements(By.xpath("//div/div/table/tbody/tr/td[1]/span"));
		List<WebElement> valueToSet = ordersFormTable.get(1).findElements(By.xpath("//div/div/table/tbody/tr/td[5]/input"));

		for (int i = totalRowsHFlex; i < totalTableRows; i++) {
			if (rowsInTable.get(i).getAttribute("class").equals("ng-binding badge badge-important")) {
				String value = rowsInTable.get(i).getText().replace("-", "");
				System.out.println("Updating Row value in Construction Material with : "+ value);
				valueToSet.get(i).clear();
				valueToSet.get(i).sendKeys(value);
			}
		}
	}

	// Verifying the Title name on Order Form Page
    public boolean verifyTextOnOrderForm()
    {
       	String textName = ordersFormPageTitle.get(0).getText();
    	if(textName.contains("Location Order Form")){
    		return true;
    	}
    	else
    		return false;
    }

    // Verifying the Table Header names in the Order Form page
    public boolean verifyTableHeaders(String headerName)
    {
   		System.out.println("Checking the " +headerName+" Table Headers ");
    	if (headerName.equals(tableRFDSHeaders.getText())){
    		return true;
    	} else if (headerName.equals(tableHFlexHeaders.getText())){
    		return true;
    	} else if (headerName.equals(tableCMHeaders.getText())){
    		return true;
    	} else 
    	return false;
    }
    
    public void clickHFlexHeader() throws InterruptedException {
    	tableHFlexHeaders.click();
    	Thread.sleep(1000);
    }
    
    public void clickCMHeader() throws InterruptedException {
    	tableCMHeaders.click();
    	Thread.sleep(1000);
    }
    
    public List readTable(String tableName)
    {
    	List appCateg = new ArrayList();
    	List appPart = new ArrayList();
    	List appDesc = new ArrayList();
    	List appCurDesg = new ArrayList();
    	List appMainList=new ArrayList();

    	int m=0; 
    	if (tableName.equals("RFDS")) 
    		m =0;
    	else if (tableName.equals("HFlex")) 
    		m =1;
    	else if (tableName.equals("CM")) 
    		m =2;
    	
    	List<WebElement> noRows=driver.findElements(By.xpath("//div[@id='category"+m+"']//table[@class='bom-table table']/tbody"));
    	System.out.println(" ");
		System.out.println("************** Printing values for " +tableName+ " Table for "+noRows.size()+" rows from the Application ***************");
    	for(int rows = 1; rows <= noRows.size(); rows++)
    	{
    		String appCategory = driver.findElement(By.xpath("//div[@id='category"+m+"']//table[@class='bom-table table']/tbody["+rows+"]//td[2]")).getText();
    		System.out.print(appCategory+"     ");
    		appCateg.add(appCategory);

    		String appPartNo = driver.findElement(By.xpath("//div[@id='category"+m+"']//table[@class='bom-table table']/tbody["+rows+"]//td[3]")).getText();
    		System.out.print(appPartNo+"     ");
    		appPart.add(appPartNo);
    		
    		String appDescription = driver.findElement(By.xpath("//div[@id='category"+m+"']//table[@class='bom-table table']/tbody["+rows+"]//td[4]")).getText();
    		System.out.print(appDescription+"     ");
    		appDesc.add(appDescription);
    		
    		String appCrDsgn = driver.findElement(By.xpath("//div[@id='category"+m+"']//table[@class='bom-table table']/tbody["+rows+"]//td[6]")).getText();
    		System.out.print(appCrDsgn);
    		appCurDesg.add(appCrDsgn);
    		System.out.println(" ");
    	}
		System.out.println(" ");
    	appMainList.add(appCateg);
    	appMainList.add(appPart);
    	appMainList.add(appDesc);
    	appMainList.add(appCurDesg);
    	return appMainList;    
    }
}
