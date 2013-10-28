/**
 * 
 */
package com.wwt.sda;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrdersPage {

	protected final WebDriver driver;

	@FindBy(xpath="//*[@data-placeholder='Select Market(s)']//*[@class='ng-scope ng-binding']")
	private List<WebElement> selectMarketOption;

	@FindBy(xpath="//*[@data-placeholder='Select status(es)']//*[@class='ng-scope ng-binding']")
	private List<WebElement> selectStatusOption;

	@FindBy(xpath="//*[@data-placeholder='Select comment type(s)']//*[@class='ng-scope ng-binding']")
	private List<WebElement> selectCommentOption;

	@FindBy(xpath="//*[@id='orderTable']//*[@class='input-small ng-pristine ng-valid']")
	private WebElement inputSite;

	@FindBy(xpath="//input[@ng-model='query.salesOrder']")
	private WebElement inputSalesOrder;

	@FindBy(xpath="//button[contains(text(),'Create BOM')]")
	private WebElement createBOMBtn;

	@FindBy(xpath="//a[contains(text(),'View BOM')]")
	private WebElement viewBOMBtn;

	@FindBy(xpath="//button[contains(text(),'Apply Filters')]")
	private WebElement applyFilterBtn;  

	@FindBy(xpath="//*[@class='center']//i[@class='icon-search']")
	private WebElement iconMagnifierBtn;

//	@FindBy(xpath="//*[@class='center']//i[@class='icon-barcode']")
//	private WebElement iconBarCodeBtn;
//
//	@FindBy(xpath="//*[@class='center']//i[@class='icon-truck']")
//	private WebElement iconShipmentBtn;

	@FindBy(xpath="//h3[@class='page-title ng-scope ng-binding']")
	private WebElement titleText;

	@FindBy(xpath="//*[@ng-model='query.lastTransaction[0]']")  
	private WebElement fromDateLastTran;

	@FindBy(xpath="//*[@ng-model='query.lastTransaction[1]']")  
	private WebElement toDateLastTran;

	@FindBy(xpath="//*[@ng-model='query.conStartCur[0]']")  
	private WebElement fromDateConStartCur;

	@FindBy(xpath="//*[@ng-model='query.conStartCur[1]']")  
	private WebElement toDateConStartCur;

	@FindBy(xpath="//*[@ng-model='query.siteAcComplete[0]']")  
	private WebElement fromDateSiteAcComp;

	@FindBy(xpath="//*[@ng-model='query.siteAcComplete[1]']")  
	private WebElement toDateSiteAcComp;

	@FindBy(xpath="//*[@ng-model='query.aAndEComplete[0]']")  
	private WebElement fromDateAandComp;

	@FindBy(xpath="//*[@ng-model='query.aAndEComplete[1]']")  
	private WebElement toDateAandComp;

	@FindBy(xpath="//*[@ng-model='query.bpAppSubmitted[0]']")  
	private WebElement fromDateBPAppSubmit;

	@FindBy(xpath="//*[@ng-model='query.bpAppSubmitted[1]']")  
	private WebElement toDateBPAppSubmit;

	@FindBy(xpath="//*[@ng-model='query.leasingComplete[0]']")  
	private WebElement fromDateLeaseComp;

	@FindBy(xpath="//*[@ng-model='query.leasingComplete[1]']")  
	private WebElement toDateLeaseComp;

	@FindBy(xpath="//*[@ng-model='query.zoningApproved[0]']")  
	private WebElement fromDateZoneAppr;

	@FindBy(xpath="//*[@ng-model='query.zoningApproved[1]']")  
	private WebElement toDateZoneAppr;

	@FindBy(xpath="//*[@class=' table-condensed']//td[@class='day' or @class='day active']")  
	private List<WebElement> dateSelect;

	@FindBy(xpath="//*[@id='orderTable']//tbody//tr//td[2]")  
	private WebElement siteName;

	@FindBy(xpath="//*[@id='orderTable']//td[contains(@ng-show,'MARKET')]")  
	private WebElement marketName;

	@FindBy(xpath="//*[@id='orderTable']//td[contains(@ng-show,'SALES_ORDER')]")  
	private WebElement salesOrderNo;

	public OrdersPage(WebDriver driver) {
		this.driver = driver;
	}

/*    public boolean verifyTextOnHome(String orders) throws InterruptedException
    {
    	Thread.sleep(2000);
		if (orders.isEmpty()) {
			return false;
		} else {
			String textName = titleText.getText();
			System.out.println("No. of orders found for this search are : " + textName);
			return true;
		}
	}
*/
    public boolean verifyTextOnHome() throws InterruptedException
    {
			String textName = titleText.getText();
			if (textName.contains("Sites")) {
				System.out.println("No. of orders found for this search are : " + textName);
				return true;
			} else 
				return false;
	}
    // Entering the Sales Orders in the Search box 
	public String searchSalesOrder(String salesOrder) throws InterruptedException{
		inputSalesOrder.sendKeys(salesOrder);
		String ordersFound = clickApplyFilterBtn();
		return ordersFound;
	}

	public String getSiteName() throws InterruptedException{
		Thread.sleep(1000);
		String site = siteName.getText();
		return site;
	}
	
	
	// Entering the Site Number in the Search box 
	public String searchSiteOrder(String siteNumber) throws InterruptedException{
		inputSite.sendKeys(siteNumber);
		String ordersFound = clickApplyFilterBtn();
		return ordersFound;
	}

	// Click on the Apply Filter Button
	public String clickApplyFilterBtn() throws InterruptedException{
			applyFilterBtn.click();
		Thread.sleep(3000);
		String noOfOrders[] = titleText.getText().split(" ");
		String valueFull = noOfOrders[1];
		String value1 = valueFull.replace('(',' ');
		String value = value1.replace(')',' ');
		return value;
	}
	
	public void clickCreateBOMBtn(){ 
		createBOMBtn.click();	
	}

	public void clickViewBOMBtn(){
		viewBOMBtn.click();
	}
	
	// Verify no. of Orders found for search
	public boolean verifyOrdersFound(String orders) throws Exception{
		if (orders.isEmpty()){
			return false;
		} else if (orders.equals(" 0 ")){
			System.out.println("No orders found for this Search ");
			return true;
		} else {
			String siteDetails = siteName.getText();
			System.out.println("Found orders and First Site# in the table is : " +siteDetails);
			return true;	
		} 
	}

	// Selecting the Orders from the drop down box "
	public String searchStatusOption(String statusOption) throws InterruptedException{
		// Checking the list of all options in the Status drop down
		for (int i = 0; i < selectStatusOption.size(); i++) {
			if (selectStatusOption.get(i).getText().equals(statusOption)) {
				selectStatusOption.clear();
				selectStatusOption.get(i).click();
			}
		}
		String ordersFound = clickApplyFilterBtn();
		return ordersFound;
	}
	// Selecting the Market Orders from the drop down box for "Charlotte"
	public String searchMarketOption(String marketOption) throws InterruptedException {
		// Checking the list of all options in the Status drop down
		for (int i = 0; i < selectMarketOption.size(); i++) {
			if (selectMarketOption.get(i).getText().equals(marketOption)) {
				selectMarketOption.get(i).click();
			}
		}
		String ordersFound = clickApplyFilterBtn();
		return ordersFound;
	}

	// Selecting the Orders from the drop down box "
	public String searchCommentsOption(String commentOption) throws InterruptedException{
		// Checking the list of all options in the Status drop down
		for (int i = 0; i < selectCommentOption.size(); i++) {
			if (selectCommentOption.get(i).getText().equals(commentOption)) {
				selectCommentOption.get(i).click();
			}
		}
		String ordersFound = clickApplyFilterBtn();
		return ordersFound;
	}
	
	// Selecting the From Date value "
	public void selectFromDate(String fromDateSearch, String fieldType) throws InterruptedException{
		int dateValue = Integer.valueOf(fromDateSearch);
		Thread.sleep(1000);
		if (fieldType.equals("Last Transaction")) {
			System.out.println("From Date Selected for Last Transaction is : September "+fromDateSearch);
			fromDateLastTran.click();
		} else if (fieldType.equals("Con Start Cur")){
			System.out.println("From Date Selected for Con Start Cur is : September "+fromDateSearch);
			fromDateConStartCur.click();
		} else if (fieldType.equals("Site Acq Complete")){
			System.out.println("From Date Selected for Site Acq Complete is : September "+fromDateSearch);
			fromDateSiteAcComp.click();
		} else if (fieldType.equals("A&E Complete")){
			System.out.println("From Date Selected for A&E Complete is : September "+fromDateSearch);
			fromDateAandComp.click();
		} else if (fieldType.equals("BP App Submitted")){
			System.out.println("From Date Selected for BP App Submitted is : September "+fromDateSearch);
			fromDateBPAppSubmit.click();
		} else if (fieldType.equals("Leasing Complete")){
			Thread.sleep(1000);
			System.out.println("From Date Selected for Leasing Complete is : September "+fromDateSearch);
			fromDateLeaseComp.click();
		} else if (fieldType.equals("Zoning Approved")){
			Thread.sleep(1000);
			System.out.println("From Date Selected for Zoning Approved is : September "+fromDateSearch);
			fromDateZoneAppr.click();
		}	
		dateSelect.get(dateValue-1).click();
		Thread.sleep(1000);
	}

	// Selecting the To Date value "
	public void selectToDate(String toDateSearch, String toDate) throws InterruptedException{
		int dateValue = Integer.valueOf(toDateSearch);
		Thread.sleep(1000);;
		if (toDate.equals("Last Transaction")) {
			System.out.println("Last Transaction To Date selected is : September "+toDateSearch);
			toDateLastTran.click();
		} else if (toDate.equals("Con Start Cur")){
			System.out.println("Con Start Cur To Date selected is : September "+toDateSearch);
			toDateConStartCur.click();
		} else if (toDate.equals("Site Acq Complete")){
			System.out.println("Site Acq Complete To Date selected is : September "+toDateSearch);
			toDateSiteAcComp.click();
		} else if (toDate.equals("A&E Complete")){
			System.out.println("A&E Complete To Date selected is : September "+toDateSearch);
			toDateAandComp.click();
		} else if (toDate.equals("BP App Submitted")){
			System.out.println("BP App Submitted To Date selected is : September "+toDateSearch);
			toDateBPAppSubmit.click();
		} else if (toDate.equals("Leasing Complete")){
			Thread.sleep(1000);
			System.out.println("Leasing Complete To Date selected is : September "+toDateSearch);
			toDateLeaseComp.click();
		} else if (toDate.equals("Zoning Approved")){
			Thread.sleep(1000);
			System.out.println("Zoning Approved To Date selected is : September "+toDateSearch);
			toDateZoneAppr.click();
		}	
		dateSelect.get(dateValue-1).click();
		Thread.sleep(1000);
	}
	
	public void clickMagnifierBtn(){
		iconMagnifierBtn.click();
	}

	public String getMarketDetails() {
		String marketDetails = marketName.getText();
		return marketDetails;
	}
	
	public String getSalesOrderDetails() {
		String salesOrdDetails = salesOrderNo.getText();
		return salesOrdDetails;
	}
	
	public String getSiteDetails() throws InterruptedException {
		Thread.sleep(1000);
		String siteNameDetails = siteName.getText();
		return siteNameDetails;
	}
}

