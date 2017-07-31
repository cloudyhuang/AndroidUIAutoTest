package com.hjs.pages;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import com.hjs.config.CommonAppiumPage;

public class InvestPageObject extends CommonAppiumPage{
	@AndroidFindBy(id="input")
	private AndroidElement amountInput;		//金额输入框
	@AndroidFindBy(id="subscribe_submit")
	private AndroidElement submitBtn;		//提交按钮
	
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
		clickEle(submitBtn,"提交按钮");
		return new PayPageObject(driver);
	}
	public boolean verifyInthisPage(){
		return isElementExsit(amountInputLocator);
	}
}
