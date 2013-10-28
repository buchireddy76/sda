package com.wwt.sda;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrderConfirmPage<verifyAttribute> {

	protected final WebDriver driver;

	@FindBy(xpath = "//div[@id='order-detail-header']//h3[@class='page-title ng-binding']")
	private WebElement confirmHeaderText;

	@FindBy(xpath="//button[contains(text(),'Back to BOM')]")
	private WebElement backToBOMBtn;

	@FindBy(xpath="//button[contains(text(),'Continue')]")
	private WebElement continueBtn;

	@FindBy(xpath="//*[@id='order-detail-header']//h4//*[@class='ng-binding']")
	private WebElement orderStatus;

	@FindBy(xpath = "//*[@class='center']//*[@class='input-medium ng-pristine ng-valid']//option")
	private List<WebElement> selectBOMSubmitStats;

	@FindBy(xpath = "//*[@id='order-detail-header']//*[@class='input-medium ng-pristine ng-valid']/option")
	private List<WebElement> selectOrderSubmitStatsOption;

//	@FindBy(xpath="//div[@id='site-details']//*[@class='well']//p[contains(@ng-show,'columns')]")
//	private List<WebElement> listSiteDetails;
//
//	@FindBy(xpath="//div[@id='site-details']//*[@class='well']")
//	private List<WebElement> siteDetailsList;

	@FindBy(xpath="//p[contains(@ng-show, 'MARKET')]")
	private WebElement labelMarket;

	@FindBy(xpath="//p[contains(@ng-show, 'SALES_ORDER')]")
	private WebElement labelSalesOrder;

	@FindBy(xpath="//div[@id='order-detail-header']//button[contains(text(),'Create Supplemental BOM')]")
	private WebElement createSuppBOMBtn;

	@FindBy(xpath="//div[@id='order-detail-header']//*[@class='btn btn-info line-link ng-scope ng-binding']")
	private WebElement barCodePDFBtn;

	@FindBy(xpath="//div[@id='order-detail-header']//button[contains(text(),'Labels')]")
	private WebElement labelsBtn;

	@FindBy(xpath="//*[@class='btn-group open']//*[@class='dropdown-menu']//*[@class='ng-binding']")
	private List<WebElement> labelsList;

	@FindBy(xpath="//*[@class='table table-hover table-bordered table-striped']//*[@class='btn btn-small pull-right']")
	private WebElement iconComments;

	@FindBy(xpath="//*[@class='latest-comment']//*[@class='ng-binding']")
	private WebElement commentText;

	@FindBy(xpath="//*[@class='latest-comment']//*[@class='muted ng-binding']")
	private WebElement commentUserDetails;

	@FindBy(xpath="//*[@class='table table-hover table-bordered table-striped']//i[@class='icon-barcode']")
	private WebElement iconBarCode;

	@FindBy(xpath="//*[@class='table table-hover table-bordered table-striped']//i[@class='icon-truck']")
	private WebElement iconShipmentInfo;

//	@FindBy(xpath="//body[@onload='getrep()']")
//	private WebElement dwnloadPDFPage;
//
	public OrderConfirmPage(WebDriver driver) {
		this.driver = driver;
	}

	public void clickContinueBtn() {
		continueBtn.click();
	}

	public void clickBackToBOMBtn() {
		backToBOMBtn.click();
	}

	public boolean verifyHeader(String orderSiteName) throws InterruptedException {
		Thread.sleep(1000);
		selectBOMSubmitStats.size();
       	String titleName = confirmHeaderText.getText();
       	String titleSplitNames [] = titleName.split(" ");
       	String title = titleSplitNames[2];
       	System.out.println("Order details are : "+orderSiteName);
		System.out.println("Verifying title name of the Order Confirmation Page " +title); 
    	if (titleSplitNames[2].equals(orderSiteName)) {
    		return true;
    	}
    	else {
    		return false;
    	}
	}
	
	public boolean verifySiteOrder(String orderSiteName) throws InterruptedException {
	//	selectBOMSubmitStats.size();
		Thread.sleep(2000);
       	String titleName = confirmHeaderText.getText();
       	String titleSplitNames [] = titleName.split(" ");
 		System.out.println("Verifying Site Order in the Order Confirmation Page "+titleSplitNames[2] ); 
    	if (titleSplitNames[2].equals(orderSiteName)) {
    		return true;
    	}
    	else {
    		return false;
    	}
	}

	public boolean verifyOrderConfirmHeader() {
       	String titleName = confirmHeaderText.getText();
       	System.out.println("Verifying if the title has Orders for in the text or not");
    	if (titleName.contains("Orders for")) {
    		return true;
    	}
    	else {
    		System.out.println("Title name on Order Confirmation page is : "+titleName);
    		return false;
    	}
	}

	// Selecting BOM options from the BOM drop down
	public void selectBOMStatusUpdate(String bomStatus) throws InterruptedException {
		for (int i = 0; i < selectBOMSubmitStats.size(); i++) {
			if (selectBOMSubmitStats.get(i).getText().equals(bomStatus)) 
				selectBOMSubmitStats.get(i).click();
		}
		Thread.sleep(1000);
	}

	// Selecting Order status from the Order Status BOM dropdown
	public void selectOrdertatusUpdate(String orderStatus) throws InterruptedException {
		for (int i = 0; i < selectOrderSubmitStatsOption.size(); i++) {
			if (selectOrderSubmitStatsOption.get(i).getText().equals(orderStatus)) { 
				selectOrderSubmitStatsOption.get(i).click();
				break;
			}				
		}
		Thread.sleep(1000);
	}
		

	public void refreshPage() throws InterruptedException {
		System.out.println("Refreshing Order Confirmation Page after 5 minutes");
		driver.navigate().refresh();
		Thread.sleep(5000);
	}
	
	public boolean verifyOrderSubmitted() {
       	String orderStatusMsg = orderStatus.getText();
       	System.out.println("Current Status of the BOM Order is : "+orderStatusMsg);
    	if (orderStatusMsg.equals("Order Submitted")) {
    		return true;
    	}
    	else 
    	{
    		System.out.println("Current status of the order is : "+orderStatusMsg);
    		return false;
    	}
}
	
	public boolean verifySalesOrder(String salesOrder) {
		String salesOrdDetails [] = labelSalesOrder.getText().split(":");
		String salesOrders = salesOrdDetails[1].trim();
		System.out.println("Verifying Sales Order Name : " + salesOrder); 
		
    	if (salesOrders.equals(salesOrder)) {
    		System.out.println("Sales order is verified with the Order confirmation Page");
    		return true;
    	}
    	else {
    		return false;
    	}
	}
	
	public boolean verifyMarketDetails(String marketDetails) {
		System.out.println("Market Name to be verified is : " + marketDetails); 
		String salesOrdDetails = labelMarket.getText();
		System.out.println("Verifying Market Name : " + marketDetails); 
    	if (salesOrdDetails.equals(marketDetails)) {
    		return true;
    	}
    	else {
    		return false;
    	}
	}
	
	public void clickBarCodePDFBtn() throws InterruptedException{
		barCodePDFBtn.click();
		Thread.sleep(2000);
	}
	
	public void clickSupplBOMBtn() throws InterruptedException{
		createSuppBOMBtn.click();
		Thread.sleep(2000);
	}
	
	public String getMainWindowHandle(){
		return  driver.getWindowHandle();
	}

	// Verifying if the PDF is downloading or not
	public boolean verifyPDFDownloadPage(String mainWindow) {
		boolean pdfDownload = false;
		Set set = driver.getWindowHandles();
		Iterator it = set.iterator();
		System.out.println("Checking for PDF download page");
		while (it.hasNext()) {
			String str = it.next().toString();

			if (!mainWindow.equals(str)) {
				driver.switchTo().window(str);
				String pdf = driver.findElement(By.xpath("//embed[contains(@type,'pdf')]")).getAttribute("type");

				if (pdf.equals("application/pdf"))
					pdfDownload = true;
			}
		}
		return pdfDownload;
	}
	
	public boolean verifySiteOrderDetails(String siteDetails, String details) throws InterruptedException {
		Thread.sleep(2000);
		String getElement = driver.findElement(By.xpath("//div[@id='site-details']//*[@class='well']//p[contains(@ng-show, '"+ siteDetails + "')]")).getText();
		String elementName1[] = getElement.split(":");
		String elementName = elementName1[1].trim();
		System.out.println("Verifying Site details for " +getElement+ " in the Order Confirmation page ");
	
		if (elementName.equals(details)) {
			return true;
		}
		else{
			System.out.println("The site details in Confirm page are not matching with the Orders page");
			return false;
		}
	}
	
	// Selecting the Label option from the Drop down
	public void clickLabelsBtn(String labelName) throws InterruptedException{
		labelsBtn.click();
		Thread.sleep(1000);
		for (int i = 0; i < labelsList.size(); i++) {
			if (labelsList.get(i).getText().equals(labelName)) { 
				labelsList.get(i).click();
				break;
			}				
		}
		Thread.sleep(2000);
	}

	public void clickBarCodeIcon() {
		iconBarCode.click();
	}

	public void clickShipmentInfoIcon() {
		iconShipmentInfo.click();
	}
	
	public void clickCommentsIcon() throws InterruptedException{
		Thread.sleep(2000);
		iconComments.click();
	}
	
	public String getSiteOrder() throws InterruptedException {
		Thread.sleep(2000);
       	String titleName = confirmHeaderText.getText();
       	String titleSplitNames [] = titleName.split(" ");
       	String orderNumber = titleSplitNames[2];
 		System.out.println("Site order on Order Confirm Page is  "+orderNumber ); 
		return orderNumber;
	}

	public boolean verifyUserDetails(String userDetails) throws InterruptedException {
		Thread.sleep(2000);
		String usrtDetails = commentUserDetails.getText();
		System.out.println("Comment User Details on the page : " + usrtDetails);
    	if (usrtDetails.contains(userDetails)) {
    		return true;
    	}
    	else {
    		return false;
    	}
	}
	
	public boolean verifyCommentDetails(String commentDetails) throws InterruptedException {
		Thread.sleep(2000);
		String cmntDetails = commentText.getText();
		System.out.println("Comment text Details on the page : " + cmntDetails);
    	if (cmntDetails.contains(commentDetails)) {
    		return true;
    	}
    	else {
    		return false;
    	}
	}

}