package com.wwt.sda;

import java.io.FileInputStream;
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

public class TestNG_PDFDownloads {

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
		
		String getPageTitle = driver.findElement(By.xpath("//div[@id='order-detail-header']//h3[@class='page-title ng-binding']")).getText();
		if (getPageTitle.contains("Orders for")) {
			
		} else {
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
	
	@Test(priority = 5, dataProvider="getData", dataProviderClass=GetInputData.class)
	public void SDA_ViewBOM_Verify_Labels_Download_22(String statusOrder, String salesNumber, String salesOrder, String marketDetails, 
			String siteSector) throws Exception {
		String noOfOrders;
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS); 
		System.out.println("SDA_ViewBOM_Verify_Labels_Download_22");
		
		String getPageTitle = driver.findElement(By.xpath("//div[@id='order-detail-header']//h3[@class='page-title ng-binding']")).getText();
		if (getPageTitle.contains("Orders for")) {
			
		} else {
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
	}
}