package com.wwt.sda;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class EmailPage {

	protected final WebDriver driver;

	@FindBy(xpath = "//*[@class='sub']")
	private WebElement subjectHeader;

	@FindBy(xpath = "//*[@class='bdy']//h1")
	private WebElement bodyFirstLine;

	@FindBy(xpath = "//*[@class='bdy']//div//table[2]//tbody//tr//td[2]")
	private WebElement releaseType;

	@FindBy(xpath = "//*[@class='bdy']//div//table[2]//tbody//tr[5]//td[2]")
	private WebElement ShpFrom;

	@FindBy(xpath = "//*[@class='bdy']//div//table[2]//tbody//tr[9]//td[2]")
	private WebElement ShpTo;

	@FindBy(xpath = "//*[@class='bdy']//div//table[3]//tbody//tr[4]//table//tbody//tr")
	private List<WebElement> tableRowsHFlex;

	@FindBy(xpath = "//*[@class='bdy']//div//table[3]//tbody//tr[2]//table//tbody//tr")
	private List<WebElement> tableRowsCM;

	@FindBy(xpath = "//*[@class='bdy']//div//table[3]//tbody//tr[6]//table//tbody//tr")
	private List<WebElement> tableRowsRFDS;

	public EmailPage(WebDriver driver) {
		this.driver = driver;
	}

	//	For Outlook Interaction 
	public void LoginOutlook() throws InterruptedException{
		System.out.println("Logging to the Webmail account");
		driver.navigate().to("https://webmail.wwt.com/");
		driver.findElement(By.id("username")).sendKeys("ramidib");
		driver.findElement(By.id("password")).sendKeys("Hello123");
		driver.findElement(By.className("btn")).click();
		Thread.sleep(10000);
	}

	public void clickInbox() throws InterruptedException{
		driver.findElement(By.linkText("Inbox")).click();
		Thread.sleep(3000);
	}

	public boolean verifyFirstMailSubject(String siteDetails){
		
		boolean email=false;
		String subject = subjectHeader.getText();
		System.out.println("Subject of First email in the Inbox is : "+subject);
		if (subject.contains("Order Submitted") && subject.contains(siteDetails)) {
			email = true;
		} else {
			email = false;
		}
		return email;
	}
	
	public void clickFirstEmail() throws InterruptedException {
		System.out.println("Click on the First email in the Inbox");
		driver.findElement(By.xpath("//tr[3]/td[@class='frst'][6]/h1/a")).click();;
		Thread.sleep(1000);
	}
	
	public boolean verifySalesOrder(String sOrder) {
		String firstLineInBody[] = bodyFirstLine.getText().split(":");
		String salesOrderInMailBdy = firstLineInBody[1]; 
		String salesOrderInMail = salesOrderInMailBdy.trim();
		System.out.println("The first line in Email body is : "+salesOrderInMail);
		if (salesOrderInMail.equals(sOrder)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean verifyReleaseType(String typeRelease) {
		String releaseTypeInMail = releaseType.getText();
		System.out.println("Release Type in email is : "+releaseTypeInMail);
		
		if (releaseTypeInMail.equals(typeRelease)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean verifyShipFrom(String frmLocation) {
		String shpFromInMail = ShpFrom.getText();
		System.out.println("Shipping location from in Email is : "+shpFromInMail);
		
		if (shpFromInMail.equals(frmLocation)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean verifyShipTo(String toLocation) {
		String shpToInMail = ShpTo.getText();
		System.out.println("Location to ship in Email is : "+shpToInMail);
		
		if (shpToInMail.equals(toLocation)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean verifyTables(){
		boolean flag=false;
		String consMtrlTable = driver.findElement(By.xpath("//h3[contains(text(),'CONSTRUCTION MATERIALS')]")).getText();
		String hybriFlexTable = driver.findElement(By.xpath("//h3[contains(text(),'HYBRIFLEX')]")).getText();
		String rfdsTable = driver.findElement(By.xpath("//h3[contains(text(),'RFDS MATERIALS')]")).getText();

		if(consMtrlTable.contains("CONSTRUCTION") && hybriFlexTable.equals("HYBRIFLEX") && rfdsTable.contains("RFDS")){
			System.out.println("RFDS, Hybriflex and Construction Material Tables are verified");
			flag=true;
		}
		return flag;
	}

	public List emlReadTable(String tableName)
	{
		List emlCateg=new ArrayList();
		List emlPart=new ArrayList();
		List emlDesc=new ArrayList();
		List emlCurDesg=new ArrayList();
		List emlMainList=new ArrayList();

    	int m=0; 
    	if (tableName.equals("RFDS")) 
    		m =6;
    	else if (tableName.equals("HFlex")) 
    		m =4;
    	else if (tableName.equals("CM")) 
    		m =2;

    	List<WebElement> list=	 driver.findElements(By.xpath("//table[@cellspacing='0'][3]//tr["+m+"]//table//tr"));
    	System.out.println(" ");
		System.out.println("************** Printing values for " +tableName+ " Table for "+list.size()+" rows from the Email ***************");
		for(int rows =2; rows <=list.size(); rows++){

			String emlCategory= driver.findElement(By.xpath("//table[@cellspacing='0'][3]//tr["+m+"]//table//tr["+rows+"]/td[5]")).getText();
			emlCateg.add(emlCategory);
			System.out.print(emlCategory+"     ");

			String emlPartNo= driver.findElement(By.xpath("//table[@cellspacing='0'][3]//tr["+m+"]//table//tr["+rows+"]/td[4]")).getText();
			emlPart.add(emlPartNo);
			System.out.print(emlPartNo+"     ");

			String emlDescription= driver.findElement(By.xpath("//table[@cellspacing='0'][3]//tr["+m+"]//table//tr["+rows+"]/td[6]")).getText();
			emlDesc.add(emlDescription);
			System.out.print(emlDescription+"     ");

			String appCrDsgn= driver.findElement(By.xpath("//table[@cellspacing='0'][3]//tr["+m+"]//table//tr["+rows+"]/td[1]")).getText();
			emlCurDesg.add(appCrDsgn);
			System.out.print(appCrDsgn);
			System.out.println(" ");
		}
		System.out.println(" ");
		emlMainList.add(emlCateg);
		emlMainList.add(emlPart);
		emlMainList.add(emlDesc);
		emlMainList.add(emlCurDesg);

		return emlMainList;	
	}
	
	public  boolean  verifyLists(List<List> appList, List<List> emlList, String table) 
	{
		boolean b=true;
		if (table.equals("RFDS")) {
			System.out.println("Verifying Email values with the Application Values for RFDS Table");
		} else if (table.equals("HFlex")) {
	 		System.out.println("Verifying Email values with the Application Values for HybriFlex Table");
		} else if (table.equals("CM")) {
	 		System.out.println("Verifying Email values with the Application Values for Construction Material Table");
		}
		
	 	for(int i=0;i<appList.size();i++)
	 	{
	 		if(!appList.get(i).containsAll(emlList.get(i)))
	 		{
		 		System.out.println("We have a miss match in comparing both the list values");
		 		b=false;
		 		break;
	 		}
	 	}
	 	return b;
	}
}
