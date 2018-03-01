package com.hjs.pages;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import com.hjs.config.CommonAppiumPage;
import com.hjs.testDate.TransRecordInfo.TransInfo;

public class InvestPageObject extends CommonAppiumPage{
	@AndroidFindBy(id="input")
	private AndroidElement amountInput;		//金额输入框
	@AndroidFindBy(id="product_name")
	private AndroidElement productName;		//产品名称
	@AndroidFindBy(id="subscribe_submit")
	private AndroidElement submitBtn;		//提交按钮
	@AndroidFindBy(xpath="//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/agreement']/preceding-sibling::android.widget.FrameLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/subscribe_protocol_checkbox']/android.widget.ImageView[1]")
	private AndroidElement agreementCheckBox;		//协议确认框
	
	private By amountInputLocator=By.id("input");
	public InvestPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public PayPageObject invest(String amount) throws Exception{
		clickEle(amountInput,"金额输入框");
		SafeKeyBoard safeKeyBoard=new SafeKeyBoard(driver);
		if(!safeKeyBoard.verifySafeKeyBoardLocated()){
			throw new Exception("安全键盘未出现");
		}
		safeKeyBoard.sendNum(amount);
		safeKeyBoard.pressFinishBtn();
		if(safeKeyBoard.verifySafeKeyBoardLocated()){
			throw new Exception("点击完成后，安全键盘未消失");
		}
		clickEle(agreementCheckBox,"协议确认框");
		clickEle(submitBtn,"提交按钮");
		return new PayPageObject(driver);
	}
	
	public boolean verifyInthisPage(){
		return isElementExsit(amountInputLocator);
	}
}
