package com.wwt.sda;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage {
	
	protected final WebDriver driver;
	
	@FindBy(xpath="//*[@class='nav']//*[@id='lineupNav']//a")
	private WebElement allOrdersBtn;	

	@FindBy(xpath="//*[@class='nav']//*[@id='myOrdersNav']//a")
	private WebElement myOrdersBtn;	

	@FindBy(xpath="//*[@class='nav']//*[@class='ng-binding']")
	private WebElement sprintALUBtn;

	@FindBy(xpath="//ul[@class='dropdown-menu']/li/a")
	private List <WebElement> sprintALUOptions;

//	@FindBy(id="//*[@class='nav']//*[@id='reportNav']")
//	private WebElement reportsBtn;	
	
	@FindBy(xpath="//input[@placeholder='Enter Site/SO/Serial' and @type='text']")
	private WebElement inputSiteSOSerialText;
	
//	@FindBy(xpath="//input[@class='select2-input' and @type='text']")
//	private WebElement inputStatusText;

	@FindBy(xpath="//h3[@class='page-title ng-scope ng-binding']")
	private WebElement titleText;

	@FindBy(xpath="//th[contains(text(),'Actions')]")
	private WebElement headerAction;
	
	@FindBy(xpath="//span[contains(text(),'Site')]")
	private WebElement headerSite;
	
	@FindBy(xpath="//span[contains(text(),'Sales Order')]")
	private WebElement headerSalesOrder;
	
	@FindBy(xpath="//span[contains(text(),'Market')]")
	private WebElement headerMarket;
	
	@FindBy(xpath="//span[contains(text(),'Status')]")
	private WebElement headerStatus;
	
	@FindBy(xpath="//span[contains(text(),'Last Transaction')]")
	private WebElement headerLastTransaction;
	
	@FindBy(xpath="//span[contains(text(),'Con Start Cur')]")
	private WebElement headerConStartCur;
	
	@FindBy(xpath="//th[contains(text(),'Comments')]")
	private WebElement headerComments;
	
	@FindBy(xpath="//span[contains(text(),'Site Acq Complete')]")
	private WebElement headerSiteAcqComp;
	
	@FindBy(xpath="//span[contains(text(),'A&E Complete')]")
	private WebElement headerAEComplete;
	
	@FindBy(xpath="//span[contains(text(),'BP App Submitted')]")
	private WebElement headerBPAppSubmitted;
	
	@FindBy(xpath="//span[contains(text(),'Leasing Complete')]")
	private WebElement headerLeasingComp;
	
	@FindBy(xpath="//span[contains(text(),'Zoning Approved')]")
	private WebElement headerZoningAppr;
	
//	@FindBy(xpath="//input[@ng-model='query.site']")
//	private WebElement inputSite;
//	
//	@FindBy(xpath="//input[@ng-model='query.salesOrder']")
//	private WebElement inputSalesOrder;
	
	@FindBy(xpath="//button[contains(text(),'Apply Filters')]")
	private WebElement applyFilterBtn;

	@FindBy(xpath="//li[@id='myOrdersNav']")
	private WebElement activeOrderTab;

	@FindBy(xpath = "//*[@id='user-menu']//*[@class='dropdown-toggle']")
	private WebElement userName;

	public HomePage(WebDriver driver) {
        this.driver = driver;
    }
    
    public boolean verifyDisplyUserName(String expectedUserName)
    {
    	boolean verifyUserName = false;
       	String usrName = userName.getText();
	
    	if(usrName.equals(expectedUserName))
    	{
    		System.out.println("Applcation logged in with Valid user : " + usrName);
    		verifyUserName = true;
    	}
    	return verifyUserName;
    }

    public void	enterSearchSiteSOSerial(String enterSiteSOSerialNumber){
    	inputSiteSOSerialText.sendKeys(enterSiteSOSerialNumber);
    }
    
    public void	clickOnMyOrdersBtn() throws InterruptedException{
		myOrdersBtn.click();
    }  
    
    public void clickOnAllOrdersBtn() throws InterruptedException{
		allOrdersBtn.click();
    	Thread.sleep(1000);
	}
    
    public void sprintALUBtnClick() throws InterruptedException
    {
		sprintALUBtn.click();
    	Thread.sleep(1000);
	}
    
   //Selecting the Required Filter criteria for Sprint ALU options.   
    public void selectSprintOption(String sprintOption) throws InterruptedException{
       	for(int i = 0; i<sprintALUOptions.size(); i++){
       		if(sprintALUOptions.get(i).getText().equals(sprintOption)){
       			sprintALUOptions.get(i).click();
       			break;
       		}
       	}  
     }

    public boolean validatehomepagedispay(){
    	  System.out.println(titleText.getText());  	
    	return (titleText.isDisplayed());
    }
    
    public boolean verifyHeaderAction()
    {
       	String textName = headerAction.getText();
		System.out.println("Verifying Table Header " +headerAction.getText());
    	if(textName.equals("Actions")){
    		return true;
    	}
    	else 
    		return false;
    }

    public boolean verifyHeaderColumn(String columnName)
    {
    	try{
    		driver.findElement(By.xpath("//span[contains(text(),'"+columnName+"')]"));
    		System.out.println("Table Header " +driver.findElement(By.xpath("//span[contains(text(),'"+columnName+"')]")).getText()+ " is verified");
    		return true;
    	}
    	catch(NoSuchElementException e){
    		System.out.println("Column: " + columnName + " not found.");
    		return false;    		
    	}
    }

    public boolean verifyHeaderComments()
    {
       	String textName = headerComments.getText();
		System.out.println("Verifying Table Header " +headerComments.getText());
    	if(textName.equals("Comments")){
    		return true;
    	}
    	else 
    		return false;
    }

   public boolean verifyHeaderSite(String expectedMsg)
    {
    	boolean verifyTextMsg = false;
       	String textName = headerSite.getText();
    	if(textName.equals(expectedMsg))
    	{
    		verifyTextMsg = true;
    	}
    	return verifyTextMsg;
    }

    public boolean verifyHeaderSalesOrder(String expectedMsg)
    {
    	boolean verifyTextMsg = false;
       	String textName = headerSalesOrder.getText();
    	if(textName.equals(expectedMsg))
    	{
    		verifyTextMsg = true;
    	}
    	return verifyTextMsg;
    }

    public boolean verifyHeaderMarket(String expectedMsg)
    {
    	boolean verifyTextMsg = false;
       	String textName = headerMarket.getText();
    	if(textName.equals(expectedMsg))
    	{
    		verifyTextMsg = true;
    	}
    	return verifyTextMsg;
    }

    public boolean verifyHeaderStatus(String expectedMsg)
    {
    	boolean verifyTextMsg = false;
       	String textName = headerStatus.getText();
    	if(textName.equals(expectedMsg))
    	{
    		verifyTextMsg = true;
    	}
    	return verifyTextMsg;
    }

    public boolean verifyHeaderLastTransaction(String expectedMsg)
    {
    	boolean verifyTextMsg = false;
       	String textName = headerLastTransaction.getText();
    	if(textName.equals(expectedMsg))
    	{
    		verifyTextMsg = true;
    	}
    	return verifyTextMsg;
    }

    public boolean verifyHeaderConStartCur(String expectedMsg)
    {
    	boolean verifyTextMsg = false;
       	String textName = headerConStartCur.getText();
    	if(textName.equals(expectedMsg))
    	{
    		verifyTextMsg = true;
    	}
    	return verifyTextMsg;
    }

    public boolean verifyHeaderSiteAcqComp(String expectedMsg)
    {
    	boolean verifyTextMsg = false;
       	String textName = headerSiteAcqComp.getText();
    	if(textName.equals(expectedMsg))
    	{
    		verifyTextMsg = true;
    	}
    	return verifyTextMsg;
    }

    public boolean verifyHeaderAEComp(String expectedMsg)
    {
    	boolean verifyTextMsg = false;
       	String textName = headerAEComplete.getText();
    	if(textName.equals(expectedMsg))
    	{
    		verifyTextMsg = true;
    	}
    	return verifyTextMsg;
    }

    public boolean verifyHeaderBPAppSubmit(String expectedMsg)
    {
    	boolean verifyTextMsg = false;
       	String textName = headerBPAppSubmitted.getText();
    	if(textName.equals(expectedMsg))
    	{
    		verifyTextMsg = true;
    	}
    	return verifyTextMsg;
    }

    public boolean verifyHeaderLeasingComp(String expectedMsg)
    {
    	boolean verifyTextMsg = false;
       	String textName = headerLeasingComp.getText();
    	if(textName.equals(expectedMsg))
    	{
    		verifyTextMsg = true;
    	}
    	return verifyTextMsg;
    }

    public boolean verifyHeaderZoneApproved(String expectedMsg)
    {
    	boolean verifyTextMsg = false;
       	String textName = headerZoningAppr.getText();
    	if(textName.equals(expectedMsg))
    	{
    		verifyTextMsg = true;
    	}
    	return verifyTextMsg;
    }

    public boolean verifyAppFltrBtnName()
    {
       	String textName = applyFilterBtn.getText();
    	if(textName.equals("Apply Filters")){
    		System.out.println("Button Name  : "+applyFilterBtn.getText()+" is verified");
    		return true;
    	}
    	else 
    	return false;
    }

    public boolean verifyActiveTab() throws InterruptedException
    {
    	Thread.sleep(1000);
    	String tabName = activeOrderTab.getText();
    	String activeName = activeOrderTab.getAttribute("class");
    	if(tabName.contains("My Orders") && (activeName.contains("navLink active"))){
    		System.out.println("Active Tab on the Home page is : " +activeOrderTab.getText());
    		return true;
    	}
    	else
    		return false;
    }
}