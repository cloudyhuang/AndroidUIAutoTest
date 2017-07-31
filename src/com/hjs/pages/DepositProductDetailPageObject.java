package com.hjs.pages;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import com.hjs.config.CommonAppiumPage;

public class DepositProductDetailPageObject extends CommonAppiumPage{
	
	@AndroidFindBy(id="button_pay")
	private AndroidElement payBtn;		//点击投资按钮
	@AndroidFindBy(id="textView_startMoney")
	private AndroidElement startMoney;		//起投金额
	
	private By payBtnLocator=By.id("button_pay");
	public DepositProductDetailPageObject(
			AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public InvestPageObject clickPayBtn(){
		clickEle(payBtn,"点击投资按钮");
		return new InvestPageObject(driver);
	}
	public String getStartMoney(){
		return startMoney.getText();
	}
	public boolean verifyInthisPage(){
		return isElementExsit(payBtnLocator);
	}
}
