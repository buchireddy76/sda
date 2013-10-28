package com.wwt.sda;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CommentsDialog {

	protected final WebDriver driver;

	@FindBy(xpath = "//*[@class='modal hide ng-scope in']//h3[contains(text(),'Comments')]")
	private WebElement commentsHeader;

	@FindBy(xpath = "//*[@class='modal hide ng-scope in']//*[@id='selectCommentType']//option")
	private List<WebElement> commentsList;

	@FindBy(xpath = "//*[@class='modal hide ng-scope in']//*[@class='control-group']//*[@id='textCommentMessage']")
	private WebElement commentsText;

	@FindBy(xpath = "//*[@class='modal hide ng-scope in']//*[@ng-model='subscriberSelected']//input[@type='text']")
	private WebElement subscriberSelect;

	@FindBy(xpath = "//*[@class='modal hide ng-scope in']//*[@ng-model='subscriberSelected']//input[@type='text']")
	private WebElement subscriberHiddenSelect;

//	@FindBy(xpath = "//*[@class='modal hide ng-scope in']//*[@ng-model='subscriberSelected']//input[@type='text']")
//	private WebElement iconSubscribeBulb;

	@FindBy(xpath = "//*[@class='modal hide ng-scope in']//*[@class='btn btn-primary']")
	private WebElement btnAdd;

	@FindBy(xpath = "//*[@class='modal hide ng-scope in']//*[@class='modal-footer']//button[contains(text(),'Save Comment')]")
	private WebElement btnSaveComment;

	@FindBy(xpath = "//*[@class='modal hide ng-scope in']//*[@class='modal-body']//*[@class='ng-scope']//*[@class='btn btn-danger btn-mini']")
	private WebElement btnRemove;

	@FindBy(xpath = "//*[@class='modal hide ng-scope in']//*[@class='modal-footer']//button[contains(text(),'Close')]")
	private WebElement btnClose;

	@FindBy(xpath = "//*[@class='modal hide ng-scope in']//*[@class='modal-body']")
	private WebElement commentDialogBody;

	public CommentsDialog(WebDriver driver) {
		this.driver = driver;
	}

	public boolean verifyDialogHeader() throws InterruptedException {
		Thread.sleep(1000);
       	String titleName = commentsHeader.getText();
		System.out.println("Title name on Comments Dialog is : "+titleName);
    	if(titleName.contains("Comments")){
    		return true;
    	}
    	else {
    		return false;
    	}
	}

	public void selectCommentType(String commentOption)  {
		System.out.println("No. of comments itesm in drop down are : "+commentsList.size());
		for (int i = 0; i < commentsList.size(); i++) {
			if (commentsList.get(i).getText().equals(commentOption)) {
				commentsList.get(i).click();
				break;
			} 
		}
	}

	public void inputComments(String newComments) {
		commentsText.clear();
		System.out.println("Enter new Comments to this order : " +newComments);
		commentsText.sendKeys(newComments);
		commentDialogBody.click();
	}

	public void selectSubscriber(String newSubscriber) throws InterruptedException {
		subscriberSelect.clear();
		subscriberSelect.sendKeys(newSubscriber);
		subscriberHiddenSelect.click();
	}

	public void clickAddButton() throws InterruptedException {
		btnAdd.click();
		Thread.sleep(1000);
	}

	public void clickSaveCommentBtn() {
		btnSaveComment.click();
	}

	public void clickCloseBtn() {
		btnClose.click();
	}
	
	public boolean verifyRemoveBtn() {
       	String btnName = btnRemove.getText();
		System.out.println("Verifying " +btnName + " Button is displayed or not");
    	if(btnName.contains("Remove")){
    		return true;
    	}
    	else {
    		return false;
    	}
	}
}
