package com.wwt.sda;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.wwt.sda.LoginPage;
import com.wwt.sda.GetWebDriverType;

public class TestNG_SDA {

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
	EmailPage emailPage = null;

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
		emailPage = PageFactory.initElements(driver, EmailPage.class);

		String userId = scriptproperties.getProperty("CONFIG_LOGIN_USERNAME");
		String passWord = scriptproperties.getProperty("CONFIG_LOGIN_PASSWORD");

		logIn.ClickOnlogIn(userId, passWord);
	}

	@Test(priority = 1, dataProvider="getData", dataProviderClass=GetInputData.class)
	public void SDA_VerifyHome_04() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS); 
		System.out.println("SDA_VerifyHome_04");
		
 		assertTrue(homePage.verifyActiveTab(), "My Orders tab is should be the Active tab");
 		assertTrue(homePage.verifyHeaderAction(), "Action Header should be present on the Table ");
 		assertTrue(homePage.verifyAppFltrBtnName(), "Apply Filters Button in the Actions column should be present");
 		assertTrue(homePage.verifyHeaderComments(), "Comments Header should be present in the table");
  		assertTrue(homePage.verifyHeaderColumn("Site") && homePage.verifyHeaderColumn("Sales Order") && homePage.verifyHeaderColumn("Market") 
  				&& homePage.verifyHeaderColumn("Status") && homePage.verifyHeaderColumn("Last Transaction") && homePage.verifyHeaderColumn("Con Start Cur") 
  				&& homePage.verifyHeaderColumn("Site Acq Complete") && homePage.verifyHeaderColumn("A&E Complete") && homePage.verifyHeaderColumn("BP App Submitted") 
 				&& homePage.verifyHeaderColumn("Leasing Complete") && homePage.verifyHeaderColumn("Zoning Approved"),"Headers - Site, Sales Order, Market, Status, "
 						+ "Last Transaction, Con Start Cur, Site Acq Complete, A&E Complete, BP App Submitted, Leasing Complete and Zoning Approved should be visible. ");
	}
	
	@Test(priority = 5, dataProvider="getData", dataProviderClass=GetInputData.class)
	public void SDA_VerifyAllOrders_05() throws Exception {
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS); 
		System.out.println("SDA_VerifyAllOrders_05");
		
		homePage.clickOnMyOrdersBtn();
		homePage.clickOnAllOrdersBtn();
		assertTrue(orderPage.verifyTextOnHome(), "Page Header, Sites XXXXX should reflect orders searched");
	}

	@Test(priority = 10, dataProvider="getData", dataProviderClass=GetInputData.class)
	public void SDA_AllOrdersSearchSite_06(String siteName) throws Exception {
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS); 
		System.out.println("SDA_AllOrdersSearchSite_06");

		homePage.clickOnMyOrdersBtn();
		homePage.clickOnAllOrdersBtn();
		String noOfOrders = orderPage.searchSiteOrder(siteName);
		assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites xxxx' ");
		assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should display All orders search details in the table");
	}

	@Test(priority = 15, dataProvider="getData", dataProviderClass=GetInputData.class)
	public void SDA_AllOrders_SearchSalesOrder_07(String salesNumber) throws Exception {
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS); 
		System.out.println("SDA_AllOrders_SearchSalesOrder_07");
		
		homePage.clickOnMyOrdersBtn();
		homePage.clickOnAllOrdersBtn();
		String noOfOrders = orderPage.searchSalesOrder(salesNumber);
		assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites xxxx' ");
		assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should displays Sales order search details in the table");
	}

	@Test(priority = 20, dataProvider="getData", dataProviderClass=GetInputData.class)
	public void SDA_AllOrders_SeachMarketOrder_08(String marketName) throws Exception {
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS); 
		System.out.println("SDA_AllOrders_SeachMarketOrder_08");
		
		homePage.clickOnMyOrdersBtn();
		homePage.clickOnAllOrdersBtn();
		String noOfOrders = orderPage.searchMarketOption(marketName);
		assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites xxxx' ");
		assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should displays Market search details in the table");
	}

	@Test(priority = 25, dataProvider="getData", dataProviderClass=GetInputData.class)
	public void SDA_AllOrders_SearchStautsOrder_09(String statusOrder) throws Exception {
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS); 
		System.out.println("SDA_AllOrders_SearchStautsOrder_09");
		
		homePage.clickOnMyOrdersBtn();
		homePage.clickOnAllOrdersBtn();
		String noOfOrders = orderPage.searchStatusOption(statusOrder);
		assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites xxxx' ");
		assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should displays Status search details in the table");
	}

	@Test(priority = 30, dataProvider="getData", dataProviderClass=GetInputData.class)
	public void SDA_AllOrders_SearchCommentsOrder_12(String commentName) throws Exception {
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS); 
		System.out.println("SDA_AllOrders_SearchCommentsOrder_12");
		
		homePage.clickOnMyOrdersBtn();
		homePage.clickOnAllOrdersBtn();
		String noOfOrders = orderPage.searchCommentsOption(commentName);
		assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites xxxx' ");
		assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should displays Comments search details in the table");
	}	
	
	@Test(priority = 35, dataProvider="getData", dataProviderClass=GetInputData.class)
	public void SDA_AllOrders_SearchLastTransaction_10(String fromStart, String toEnd, String lastTrans) throws Exception {
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS); 
		System.out.println("SDA_AllOrders_SearchLastTransaction_10");
		
		homePage.clickOnMyOrdersBtn();
		homePage.clickOnAllOrdersBtn();
		orderPage.selectFromDate(fromStart,lastTrans);
		String noOfOrders = orderPage.clickApplyFilterBtn();
		assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites xxxx' ");
		assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should displays all orders in the table from the date mentioned");

		driver.navigate().refresh();
		orderPage.selectToDate(toEnd,lastTrans);
		noOfOrders = orderPage.clickApplyFilterBtn();
		assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites xxxx' ");
		assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should display all orders in the table from starting till the Date searched for");

		driver.navigate().refresh();
		orderPage.selectFromDate(fromStart,lastTrans);
		orderPage.selectToDate(toEnd,lastTrans);
		noOfOrders = orderPage.clickApplyFilterBtn();
		assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites xxxx' ");
		assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should display all orders in the table with the date range searched for");
		
	}	
	
	@Test(priority = 40, dataProvider="getData", dataProviderClass=GetInputData.class)
	public void SDA_AllOrders_SearchConStartCur_11(String fromStart, String toEnd, String conStartCur) throws Exception {
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS); 
		System.out.println("SDA_AllOrders_SearchConStartCur_11");
		
		homePage.clickOnMyOrdersBtn();
		homePage.clickOnAllOrdersBtn();
		orderPage.selectFromDate(fromStart,conStartCur);
		String noOfOrders = orderPage.clickApplyFilterBtn();
		assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites xxxx' ");
		assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should displays all orders in the table from the date mentioned");

		driver.navigate().refresh();
		orderPage.selectToDate(toEnd, conStartCur);
		noOfOrders = orderPage.clickApplyFilterBtn();
		assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites xxxx' ");
		assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should display all orders in the table from starting till the Date searched for");

		driver.navigate().refresh();
		orderPage.selectFromDate(fromStart,conStartCur);
		orderPage.selectToDate(toEnd,conStartCur);
		noOfOrders = orderPage.clickApplyFilterBtn();
		assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites xxxx' ");
		assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should display all orders in the table with the date range searched for");
	}	
	
	@Test(priority = 45, dataProvider="getData", dataProviderClass=GetInputData.class)
	public void SDA_AllOrders_SearchSiteAcqCompl_13(String fromStart, String toEnd, String siteAcqComp) throws Exception {
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS); 
		System.out.println("SDA_SearchSiteAcqCompl_AllOrders_16");
		
		homePage.clickOnMyOrdersBtn();
		homePage.clickOnAllOrdersBtn();
		orderPage.selectFromDate(fromStart,siteAcqComp);
		String noOfOrders = orderPage.clickApplyFilterBtn();
		assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites xxxx' ");
		assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should displays all orders in the table from the date mentioned");
		
		driver.navigate().refresh();
		orderPage.selectToDate(toEnd,siteAcqComp);
		noOfOrders = orderPage.clickApplyFilterBtn();
		assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites xxxx' ");
		assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should display all orders in the table from starting till the Date searched for");

		driver.navigate().refresh();
		orderPage.selectFromDate(fromStart,siteAcqComp);
		orderPage.selectToDate(toEnd,siteAcqComp);
		noOfOrders = orderPage.clickApplyFilterBtn();
		assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites xxxx' ");
		assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should display all orders in the table with the date range searched for");
	}	
	
	@Test(priority = 50, dataProvider="getData", dataProviderClass=GetInputData.class)
	public void SDA_AllOrders_Search_Aecomp_14(String fromStart, String toEnd, String andEComp) throws Exception {
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS); 
		System.out.println("SDA_AllOrders_Search_Aecomp_14");
		
		homePage.clickOnMyOrdersBtn();
		homePage.clickOnAllOrdersBtn();
		orderPage.selectFromDate(fromStart,andEComp);
		String noOfOrders = orderPage.clickApplyFilterBtn();
		assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites xxxx' ");
		assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should displays all orders in the table from the date mentioned");
		
		driver.navigate().refresh();
		orderPage.selectToDate(toEnd,andEComp);
		noOfOrders = orderPage.clickApplyFilterBtn();
		assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites xxxx' ");
		assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should display all orders in the table from starting till the Date searched for");

		driver.navigate().refresh();
		orderPage.selectFromDate(fromStart,andEComp);
		orderPage.selectToDate(toEnd,andEComp);
		noOfOrders = orderPage.clickApplyFilterBtn();
		assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites xxxx' ");
		assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should display all orders in the table with the date range searched for");
	}	
	
	@Test(priority = 55, dataProvider="getData", dataProviderClass=GetInputData.class)
	public void SDA_AllOrders_Search_BPAppSubmit_15(String fromStart, String toEnd, String bpAppSub) throws Exception {
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS); 
		System.out.println("SDA_AllOrders_Search_BPAppSubmit_15");
		
		homePage.clickOnMyOrdersBtn();
		homePage.clickOnAllOrdersBtn();
		orderPage.selectFromDate(fromStart,bpAppSub);
		String noOfOrders = orderPage.clickApplyFilterBtn();
		assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites xxxx' ");
		assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should displays all orders in the table from the date mentioned");
		
		driver.navigate().refresh();
		orderPage.selectToDate(toEnd,bpAppSub);
		noOfOrders = orderPage.clickApplyFilterBtn();
		assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites xxxx' ");
		assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should display all orders in the table from starting till the Date searched for");

		driver.navigate().refresh();
		orderPage.selectFromDate(fromStart,bpAppSub);
		orderPage.selectToDate(toEnd,bpAppSub);
		noOfOrders = orderPage.clickApplyFilterBtn();
		assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites xxxx' ");
		assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should display all orders in the table with the date range searched for");
	}	
	
	@Test(priority = 60, dataProvider="getData", dataProviderClass=GetInputData.class)
	public void SDA_AllOrders_SearchSite_LeasingComp_16(String fromStart, String toEnd, String leaseComp) throws Exception {
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS); 
		System.out.println("SDA_AllOrders_SearchSite_LeasingComp_16");
		
		homePage.clickOnMyOrdersBtn();
		homePage.clickOnAllOrdersBtn();
		orderPage.selectFromDate(fromStart,leaseComp);
		String noOfOrders = orderPage.clickApplyFilterBtn();
		assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites xxxx' ");
		assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should displays all orders in the table from the date mentioned");
		
		driver.navigate().refresh();
		orderPage.selectToDate(toEnd,leaseComp);
		noOfOrders = orderPage.clickApplyFilterBtn();
		assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites xxxx' ");
		assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should display all orders in the table from starting till the Date searched for");

		driver.navigate().refresh();
		orderPage.selectFromDate(fromStart,leaseComp);
		orderPage.selectToDate(toEnd,leaseComp);
		noOfOrders = orderPage.clickApplyFilterBtn();
		assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites xxxx' ");
		assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should display all orders in the table with the date range searched for");
	}	
	
	@Test(priority = 65, dataProvider="getData", dataProviderClass=GetInputData.class)
	public void SDA_AllOrders_Search_ZoningApp_17(String fromStart, String toEnd, String zoneAppr) throws Exception {
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS); 
		System.out.println("SDA_AllOrders_Search_ZoningApp_17");
		
		homePage.clickOnMyOrdersBtn();
		homePage.clickOnAllOrdersBtn();
		orderPage.selectFromDate(fromStart,zoneAppr);
		String noOfOrders = orderPage.clickApplyFilterBtn();
		assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites xxxx' ");
		assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should displays all orders in the table from the date mentioned");
		
		driver.navigate().refresh();
		orderPage.selectToDate(toEnd,zoneAppr);
		noOfOrders = orderPage.clickApplyFilterBtn();
		assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites xxxx' ");
		assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should display all orders in the table from starting till the Date searched for");

		driver.navigate().refresh();
		orderPage.selectFromDate(fromStart,zoneAppr);
		orderPage.selectToDate(toEnd,zoneAppr);
		noOfOrders = orderPage.clickApplyFilterBtn();
		assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites xxxx' ");
		assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should display all orders in the table with the date range searched for");
	}	
	
	@Test(priority = 70, dataProvider="getData", dataProviderClass=GetInputData.class)
	public void SDA_CreateBOM_ReadyToOrder_OrderSubmitted_18(String statusOrder, String salesNumber, String shipFrom, String shipTo, 
		String splRequirement, String releaseType, String bomStatus, String orderStatus) throws Exception {
		String noOfOrders;
		driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS); 
		System.out.println("SDA_CreateBOM_ReadyToOrder_OrderSubmitted_18");
		
		homePage.clickOnMyOrdersBtn();
		homePage.clickOnAllOrdersBtn();
		noOfOrders = orderPage.searchStatusOption(statusOrder);
		assertTrue(orderPage.verifyTextOnHome(), "Page Header, Sites XXXXX should reflect orders searched");
		assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should display All orders search details in the table");
		noOfOrders = orderPage.searchSalesOrder(salesNumber);
		String siteName = orderPage.getSiteName();
		assertTrue(orderPage.verifyTextOnHome(), "Heading of the page would be 'Sites (1)' ");
		assertTrue(orderPage.verifyOrdersFound(noOfOrders),"Application should displays Sales order search details in the table");
	//	orderPage.clickCreateBOMBtn();
		orderPage.clickViewBOMBtn();

		//Verifying Order Form Dialog box and Selecting values in the table and Click on Check Out button
		assertTrue(orderFormPage.verifyTextOnOrderForm(), "Location Order Form page should be poped up ");
		assertTrue(orderFormPage.verifyTableHeaders("RFDS MATERIALS") && orderFormPage.verifyTableHeaders("HYBRIFLEX") 
				&& orderFormPage.verifyTableHeaders("CONSTRUCTION MATERIALS"),"Application should find 3 tables with details in the Location Order Form Page");
		orderFormPage.selectRFDSDetails();
		orderFormPage.selectHFlexDetails();
		orderFormPage.selectCMDetails();
		List appRFDSList = orderFormPage.readTable("RFDS");
		List appHFlexList = orderFormPage.readTable("HFlex");
		List appCMList = orderFormPage.readTable("CM");
		// reading table values
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
		shipinfoDlg.clickReleaseTypeBtn(releaseType);
		shipinfoDlg.clickSaveSubmitBtn();
		
		//Order Confirm Page
		assertTrue(orderConfirmPage.verifyHeader(siteName), "Order Confirmation page with Header appended with Site # to be displayed");
		Thread.sleep(350000);	// wait for 350 seconds for the email alert and status update on Order Confirmation page.
		
		orderConfirmPage.refreshPage();
		assertTrue(orderConfirmPage.verifyOrderSubmitted(), "Current status of the order should be Order Submitted");
		
		//open the outlook page*/
		emailPage.LoginOutlook();
		emailPage.clickInbox();
		emailPage.clickFirstEmail();
		assertTrue(emailPage.verifyFirstMailSubject(siteName), "Application should display the subject as Order Submitted");	
		assertTrue(emailPage.verifySalesOrder(salesNumber), "Application should display the Sales Order in the first line of Email Body");	
		assertTrue(emailPage.verifyReleaseType(releaseType), "Application should display the Sales Order in the first line of Email Body");	
		assertTrue(emailPage.verifyShipFrom(shipFrom), "Application should display the Location From details to be dispatched");	
		assertTrue(emailPage.verifyShipTo(shipTo), "Application should display Location To details to be shipped");	

		List emlRFDSList = emailPage.emlReadTable("RFDS");
		List emlHFlexList = emailPage.emlReadTable("HFlex");
		List emlCMList = emailPage.emlReadTable("CM");
		assertTrue(emailPage.verifyLists(appRFDSList, emlRFDSList, "RFDS"), "RFDS values in application should match with the RFDS values in the email");
		assertTrue(emailPage.verifyLists(appHFlexList, emlHFlexList, "HFlex"), "Hybriflex values in application should match with the Hybriflex values in the email");
		assertTrue(emailPage.verifyLists(appCMList, emlCMList, "CM"), "CM values in application should match with the CM values in the email");
	}	
}



