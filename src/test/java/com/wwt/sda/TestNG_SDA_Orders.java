package com.wwt.sda;

import java.io.FileInputStream;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.wwt.sda.LoginPage;
import com.wwt.sda.GetWebDriverType;


public class TestNG_SDA_Orders {

	FileInputStream ConfigFIS = null, HomeFIS = null;
	protected WebDriver driver = null;

	Properties scriptproperties = null;
	public Options options = null;
	String BROWSER = null;

	DriverConfiguration driverPropertiesConfiguration = null;
	LoginPage logIn = null;
	HomePage homePage = null;
	WaitSleep waitDriver = null;
	OrdersPage orderPage = null;
	OrderFormPage orderFormPage = null;
	ConfirmDialog confirmDlg = null;
	OrderConfirmPage orderConfirmPage = null;
	ReviewOrderDialog reviewOrderDlg = null;
	ShipmentInfoDialog shipinfoDlg = null;
	SerialNumbersPage serialNosPage = null;
	CommentsDialog commentDlg = null;
	AdditionalPartsPage additionalParts = null;

	@BeforeTest
	public void pageObjectsCreation() throws Exception {
		ConfigFIS = new FileInputStream("Configuration.properties");
		scriptproperties = new Properties();
		scriptproperties.load(ConfigFIS);
		BROWSER = scriptproperties.getProperty("CONFIG_BROWSERTYPE");

		GetWebDriverType driver1 = PageFactory.initElements(driver, GetWebDriverType.class);
		driver = driver1.getDriver(BROWSER);

		driverPropertiesConfiguration = PageFactory.initElements(driver, DriverConfiguration.class);
		driverPropertiesConfiguration.driverConfiguration(scriptproperties);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS); 

		logIn = PageFactory.initElements(driver, LoginPage.class);
		homePage = PageFactory.initElements(driver, HomePage.class);
		waitDriver = PageFactory.initElements(driver, WaitSleep.class);
		orderPage = PageFactory.initElements(driver, OrdersPage.class);
		orderFormPage = PageFactory.initElements(driver, OrderFormPage.class);
		shipinfoDlg = PageFactory.initElements(driver, ShipmentInfoDialog.class);
		reviewOrderDlg = PageFactory.initElements(driver, ReviewOrderDialog.class);
		orderConfirmPage = PageFactory.initElements(driver, OrderConfirmPage.class);
		confirmDlg = PageFactory.initElements(driver, ConfirmDialog.class);
		serialNosPage = PageFactory.initElements(driver, SerialNumbersPage.class);
		commentDlg = PageFactory.initElements(driver, CommentsDialog.class);
		additionalParts = PageFactory.initElements(driver, AdditionalPartsPage.class);

		String userId = scriptproperties.getProperty("CONFIG_LOGIN_USERNAME");
		String passWord = scriptproperties.getProperty("CONFIG_LOGIN_PASSWORD");

		logIn.ClickOnlogIn(userId, passWord);
	}
	
	@Test(priority = 1, dataProvider="getData", dataProviderClass=GetInputData.class)
	public void SDA_ViewBOM_Verify_SiteDetails_OrdConfirm_20(String statusOrder, String salesNumber, String salesOrder, String marketDetails) throws Exception {
		String noOfOrders;
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS); 
		System.out.println("SDA_ViewBOM_Verify_SiteDetails_OrdConfirm_20");
		
		homePage.clickOnMyOrdersBtn();
		homePage.clickOnAllOrdersBtn();
		noOfOrders = orderPage.searchStatusOption(statusOrder);		// search for "Out of LSC"
		assertTrue(orderPage.verifyTextOnHome(), "Page Header, Sites XXXXX should reflect orders searched");
		assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should display results for All Orders Searched ");
		
		noOfOrders = orderPage.searchSalesOrder(salesNumber);
		String ordersSO = orderPage.getSalesOrderDetails();
		String ordersMarket = orderPage.getMarketDetails();
		String siteDetails = orderPage.getSiteDetails();

		assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites (1)' ");
		assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should display results for the Sales Order searched for.");
		orderPage.clickMagnifierBtn();

		//Order Confirm Page
		assertTrue(orderConfirmPage.verifySiteOrder(siteDetails), "Applicataion should display the Site order details along with the Page Header");
		assertTrue(orderConfirmPage.verifySiteOrderDetails(salesOrder, ordersSO), "Sales Order should match with the details we used from the Data sheet");
		assertTrue(orderConfirmPage.verifySiteOrderDetails(marketDetails, ordersMarket), "Market Name should match with the details used from the Data sheet");
		
	}	

	@Test(priority = 5, dataProvider="getData", dataProviderClass=GetInputData.class)
	public void SDA_ViewBOM_Verify_BarCodePDF_Download_21(String statusOrder, String salesNumber, String salesOrder, String marketDetails) throws Exception {
		String noOfOrders;
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS); 
		System.out.println("SDA_ViewBOM_Verify_BarCodePDF_Download_21");
		
		try {
			driver.findElement(By.xpath("//div[@id='order-detail-header']//h3[@class='page-title ng-binding']")).getText();
		} catch (Exception e) {
			homePage.clickOnMyOrdersBtn();
			homePage.clickOnAllOrdersBtn();
			noOfOrders = orderPage.searchStatusOption(statusOrder);		// search for "Out of LSC"
			assertTrue(orderPage.verifyTextOnHome(), "Page Header, Sites XXXXX should reflect orders searched");
			assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should display results for All Orders Searched ");
			
			noOfOrders = orderPage.searchSalesOrder(salesNumber);
			String ordersSO = orderPage.getSalesOrderDetails();
			String ordersMarket = orderPage.getMarketDetails();
			String siteDetails = orderPage.getSiteDetails();
	
			assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites (1)' ");
			assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should display results for the Sales Order searched for.");
			orderPage.clickMagnifierBtn();
	
			//Order Confirm Page
			assertTrue(orderConfirmPage.verifySiteOrder(siteDetails), "Applicataion should display the Site order details along with the Page Header");
			assertTrue(orderConfirmPage.verifySiteOrderDetails(salesOrder, ordersSO), "Sales Order should match with the details we used from the Data sheet");
			assertTrue(orderConfirmPage.verifySiteOrderDetails(marketDetails, ordersMarket), "Market Name should match with the details used from the Data sheet");
		}
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS); 
		String mainwindow = orderConfirmPage.getMainWindowHandle();
		orderConfirmPage.clickBarCodePDFBtn();
		assertTrue(orderConfirmPage.verifyPDFDownloadPage(mainwindow), "Application should download the Orders into a PDF file");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS); 
		driver.switchTo().window(mainwindow);
	}	
	
	@Test(priority = 10, dataProvider="getData", dataProviderClass=GetInputData.class)
	public void SDA_ViewBOM_Verify_Labels_Download_22(String statusOrder, String salesNumber, String salesOrder, String marketDetails, 
			String siteSector) throws Exception {
		String noOfOrders;
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS); 
		System.out.println("SDA_ViewBOM_Verify_Labels_Download_22");
		
		try {
			driver.findElement(By.xpath("//div[@id='order-detail-header']//h3[@class='page-title ng-binding']")).getText();
		} catch (Exception e) {
			
			homePage.clickOnMyOrdersBtn();
			homePage.clickOnAllOrdersBtn();
			noOfOrders = orderPage.searchStatusOption(statusOrder);		// search for "Out of LSC"
			assertTrue(orderPage.verifyTextOnHome(), "Page Header, Sites XXXXX should reflect orders searched");
			assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should display results for All Orders Searched ");
			
			noOfOrders = orderPage.searchSalesOrder(salesNumber);
			String ordersSO = orderPage.getSalesOrderDetails();
			String ordersMarket = orderPage.getMarketDetails();
			String siteDetails = orderPage.getSiteDetails();
			assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites (1)' ");
			assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should display results for the Sales Order searched for.");
			orderPage.clickMagnifierBtn();

			//Order Confirm Page
			assertTrue(orderConfirmPage.verifySiteOrder(siteDetails), "Applicataion should display the Site order details along with the Page Header");
			assertTrue(orderConfirmPage.verifySiteOrderDetails(salesOrder, ordersSO), "Sales Order should match with the details we used from the Data sheet");
			assertTrue(orderConfirmPage.verifySiteOrderDetails(marketDetails, ordersMarket), "Market Name should match with the details used from the Data sheet");
		}

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS); 
		String mainwindow = orderConfirmPage.getMainWindowHandle();
		orderConfirmPage.clickLabelsBtn(siteSector);
		assertTrue(orderConfirmPage.verifyPDFDownloadPage(mainwindow), "Application should download the Orders into a PDF file");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS); 
		driver.switchTo().window(mainwindow);
	}
	
	@Test(priority = 20, dataProvider="getData", dataProviderClass=GetInputData.class)
	public void SDA_ViewBOM_Verify_SerialNumbers_23(String statusOrder, String salesNumber, String salesOrder, String marketDetails, String NewSerialNo, 
			String userName) throws Exception {
		String noOfOrders;
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS); 
		System.out.println("SDA_ViewBOM_Verify_SerialNumbers_23");
		
		try {
			driver.findElement(By.xpath("//div[@id='order-detail-header']//h3[@class='page-title ng-binding']")).getText();
		} catch (Exception e) {
			homePage.clickOnMyOrdersBtn();
			homePage.clickOnAllOrdersBtn();
			noOfOrders = orderPage.searchStatusOption(statusOrder);		// search for "Out of LSC"
			assertTrue(orderPage.verifyTextOnHome(), "Page Header, Sites XXXXX should reflect orders searched");
			assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application displays the first row's Site # details for this Status search");
			
			noOfOrders = orderPage.searchSalesOrder(salesNumber);
			String ordersSO = orderPage.getSalesOrderDetails();
			String ordersMarket = orderPage.getMarketDetails();
			String siteDetails = orderPage.getSiteDetails();
			assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites (1)' ");
			assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should display Site # for the Sales Order searched for");
			orderPage.clickMagnifierBtn();
	
			//Order Confirm Page
			assertTrue(orderConfirmPage.verifySiteOrder(siteDetails), "Applicataion should display the Site order details along with the Page Header");
			assertTrue(orderConfirmPage.verifySiteOrderDetails(salesOrder, ordersSO), "Sales Order should match with the details we used from the Data sheet");
			assertTrue(orderConfirmPage.verifySiteOrderDetails(marketDetails, ordersMarket), "Market Name should match with the details used from the Data sheet");
		}
		orderConfirmPage.clickBarCodeIcon();
		
		//Serial Number Page
		assertTrue(serialNosPage.verifyPageHeader(), "Heading of the Page should be Serial Numbers ");
		serialNosPage.inputSerialNumber(NewSerialNo);
		serialNosPage.clickSectorBtn();
		serialNosPage.clickSaveBtn();
		assertTrue(serialNosPage.verifyTextOnSave(), "Serial No. should be successfully saved.");
		serialNosPage.clickReplaceBtn();
		assertTrue(serialNosPage.verifyReplacedUser(userName), "User details should be displayed in the Replaced column");
		assertTrue(serialNosPage.verifyReplacedDate(), "The date of replaced should be displayed in the Replaced Date column");
		serialNosPage.clickBackToOrderBtn();
		
	}

	@Test(priority = 25, dataProvider="getData", dataProviderClass=GetInputData.class)
	public void SDA_ViewBOM_Verify_ShipmentInformation_24(String statusOrder, String salesNumber, String salesOrder, String marketDetails, String shipFrom, 
			String shipTo, String splRequirement) throws Exception {
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS); 
		System.out.println("SDA_ViewBOM_Verify_ShipmentInformation_24");
		try {
			driver.findElement(By.xpath("//div[@id='order-detail-header']//h3[@class='page-title ng-binding']")).getText();
		} catch(Exception e) {
			String noOfOrders;
			
			homePage.clickOnMyOrdersBtn();
			homePage.clickOnAllOrdersBtn();
			noOfOrders = orderPage.searchStatusOption(statusOrder);		// search for "Out of LSC"
			assertTrue(orderPage.verifyTextOnHome(), "Page Header, Sites XXXXX should reflect orders searched");
			assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should display results for All Orders Search ");
			
			noOfOrders = orderPage.searchSalesOrder(salesNumber);
			String ordersSO = orderPage.getSalesOrderDetails();
			String ordersMarket = orderPage.getMarketDetails();
			String siteDetails = orderPage.getSiteDetails();
			assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites (1)' ");
			assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should display Site # for the Sales Order searched for");
			orderPage.clickMagnifierBtn();

			//Order Confirm Page
			assertTrue(orderConfirmPage.verifySiteOrder(siteDetails), "Applicataion should display the Site order details along with the Page Header");
			assertTrue(orderConfirmPage.verifySiteOrderDetails(salesOrder, ordersSO), "Sales Order should match with the details we used from the Data sheet");
			assertTrue(orderConfirmPage.verifySiteOrderDetails(marketDetails, ordersMarket), "Market Name should match with the details used from the Data sheet");
		}

		String siteDetails = orderConfirmPage.getSiteOrder();
		orderConfirmPage.clickShipmentInfoIcon();
		//Shipment Information Page
		assertTrue(shipinfoDlg.verifyDialogHeader(), "Application should display a Dialog box with heading as Shipment Info");
		shipinfoDlg.selectShipLocationFrom(shipFrom);
		shipinfoDlg.selectShipToLocation(shipTo);
		shipinfoDlg.enterSplRequirement(splRequirement);
		shipinfoDlg.clickSaveSubmitBtn();
		assertTrue(orderConfirmPage.verifyHeader(siteDetails), "Application should navigate back to Order confirmation page after saving Shipment details");
		
	}
	
	@Test(priority = 30, dataProvider="getData", dataProviderClass=GetInputData.class)
	public void SDA_ViewBOM_Verify_Comments_25(String cmntType, String comments, String subscribeUserName, String statusOrder, String salesNumber, 
			String salesOrder, String marketDetails) throws Exception {
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS); 
		System.out.println("SDA_ViewBOM_Verify_Comments_25");

		try { 
			driver.findElement(By.xpath("//div[@id='order-detail-header']//h3[@class='page-title ng-binding']")).getText();
		} catch (Exception e) {
			String noOfOrders;
			homePage.clickOnMyOrdersBtn();
			homePage.clickOnAllOrdersBtn();
			noOfOrders = orderPage.searchStatusOption(statusOrder);		// search for "Out of LSC"
			assertTrue(orderPage.verifyTextOnHome(), "Page Header, Sites XXXXX should reflect orders searched");
			assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should display results for All Orders Search ");
			
			noOfOrders = orderPage.searchSalesOrder(salesNumber);
			String ordersSO = orderPage.getSalesOrderDetails();
			String ordersMarket = orderPage.getMarketDetails();
			String siteDetails = orderPage.getSiteDetails();
			assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites (1)' ");
			assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should display Site # for the Sales Order searched for");
			orderPage.clickMagnifierBtn();
	
			//Order Confirm Page
			assertTrue(orderConfirmPage.verifySiteOrder(siteDetails), "Applicataion should display the Site order details along with the Page Header");
			assertTrue(orderConfirmPage.verifySiteOrderDetails(salesOrder, ordersSO), "Sales Order should match with the details we used from the Data sheet");
			assertTrue(orderConfirmPage.verifySiteOrderDetails(marketDetails, ordersMarket), "Market Name should match with the details used from the Data sheet");
		}
		orderConfirmPage.clickCommentsIcon();
		
		//Comments Dialog Page
		assertTrue(commentDlg.verifyDialogHeader(), "Application should display a Dialog with heading as Comments");
		commentDlg.selectCommentType(cmntType);
		commentDlg.selectSubscriber(subscribeUserName);
		commentDlg.inputComments(comments);
		commentDlg.clickAddButton();
		assertTrue(commentDlg.verifyRemoveBtn(), "After adding the Comments, Remove button should be enabled");
		commentDlg.clickSaveCommentBtn();
		assertTrue(orderConfirmPage.verifyUserDetails(subscribeUserName), "The user details who has modified should be displayed");
		assertTrue(orderConfirmPage.verifyCommentDetails(comments), "The entered or modified comments should be displayed");
	}

	@Test(priority = 35, dataProvider="getData", dataProviderClass=GetInputData.class)
	public void SDA_CreateSupplementalBOM_26(String statusOrder, String salesNumber, String salesOrder, String marketDetails, String partNo1, String partValue1, 
			String partNo2, String partValue2, String partNo3,String partValue3, String shipFrom, String shipTo, String splRequirement) throws Exception {
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS); 
		System.out.println("SDA_CreateSupplementalBOM_26");

		try {
			driver.findElement(By.xpath("//div[@id='order-detail-header']//h3[@class='page-title ng-binding']")).getText();
		} catch (Exception e) {
			String noOfOrders;
			homePage.clickOnMyOrdersBtn();
			homePage.clickOnAllOrdersBtn();
			noOfOrders = orderPage.searchStatusOption(statusOrder);		// search for "Out of LSC"
			assertTrue(orderPage.verifyTextOnHome(), "Page Header, Sites XXXXX should reflect orders searched");
			assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should display results for All Orders Search ");
			
			noOfOrders = orderPage.searchSalesOrder(salesNumber);
			String ordersSO = orderPage.getSalesOrderDetails();
			String ordersMarket = orderPage.getMarketDetails();
			String siteDetails = orderPage.getSiteDetails();
			assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites (1)' ");
			assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should display Site # for the Sales Order searched for");
			orderPage.clickMagnifierBtn();
	
			//Order Confirm Page
			assertTrue(orderConfirmPage.verifySiteOrder(siteDetails), "Applicataion should display the Site order details along with the Page Header");
			assertTrue(orderConfirmPage.verifySiteOrderDetails(salesOrder, ordersSO), "Sales Order should match with the details we used from the Data sheet");
			assertTrue(orderConfirmPage.verifySiteOrderDetails(marketDetails, ordersMarket), "Market Name should match with the details used from the Data sheet");
		}
		String siteName = orderConfirmPage.getSiteOrder();
		orderConfirmPage.clickSupplBOMBtn();
		
		assertTrue(orderFormPage.verifyTextOnOrderForm(), "Location Order Form page should be poped up ");
		assertTrue(orderFormPage.verifyTableHeaders("RFDS MATERIALS") && orderFormPage.verifyTableHeaders("HYBRIFLEX") 
				&& orderFormPage.verifyTableHeaders("CONSTRUCTION MATERIALS"),"Application should find 3 tables with details in the Location Order Form Page");
	
		orderFormPage.clickRFDSPartsBtn();
		additionalParts.selectTableDetails(partValue1, partValue1); // passing the Part No and the Value as parameters
		additionalParts.clickAddItemsBtn();
		
		orderFormPage.clickHFlexHeader();
		orderFormPage.clickHFlexPartsBtn();
		additionalParts.selectTableDetails(partValue2, partValue2);		// passing the Part No and the Value as parameters
		additionalParts.clickAddItemsBtn();
		
		orderFormPage.clickCMHeader();
		orderFormPage.clickCMPartsBtn();
		additionalParts.selectTableDetails(partValue3, partValue3);			// passing the Part No and the Value as parameters
		additionalParts.clickAddItemsBtn();
		
		orderFormPage.clickCheckOutBtn();		
		
		//Verifying Review Order Dialog box and click on the Shipping Info Button 
		assertTrue(reviewOrderDlg.verifyDialogHeader(), "Review Order page should be poped up");
 		assertTrue(reviewOrderDlg.verifyEnterShipInfoBtn(), "Enter Shipping Info should be displayed");
 		assertTrue(reviewOrderDlg.verifyBacktoBOMBtn(), "Back to BOM Button should be displayed");
		reviewOrderDlg.clickEnterShipInfoBtn();
		
		//Verifying Shipment info Dialog box and click on Save and Submit
		assertTrue(shipinfoDlg.verifyDialogHeader(), "Shipment Info page should be popped up");
 		assertTrue(shipinfoDlg.verifySaveSubmitBtn(), "Save & Submit button should be displayed");
 		assertTrue(shipinfoDlg.verifyBacktoBOMBtn(), "Back to BOM Button should be displayed");
		shipinfoDlg.selectShipLocationFrom(shipFrom);
		shipinfoDlg.selectShipToLocation(shipTo);
		shipinfoDlg.enterSplRequirement(splRequirement);
		shipinfoDlg.clickSaveSubmitBtn();
		
		//Order Confirm Page
		assertTrue(orderConfirmPage.verifyHeader(siteName), "Order Confirmation page with Header appended with Site # to be displayed");
	}
}



