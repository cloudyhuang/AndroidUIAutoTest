package com.hjs.pages;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import com.hjs.config.CommonAppiumPage;

public class CurrentDepositProductDetailPageObject extends CommonAppiumPage{
	@AndroidFindBy(id="none_purchase")
	private AndroidElement payBtn;		//点击投资按钮
	@AndroidFindBy(id="purchase")
	private AndroidElement payAgainBtn;		//再次投资按钮
	@AndroidFindBy(id="ransom")
	private AndroidElement ransomBtn;		//赎回按钮
	
	
	private By payBtnLocator=By.id("button_pay");
	private By payAgainBtnLocator=By.id("purchase");
	public CurrentDepositProductDetailPageObject(
			AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public InvestPageObject clickPayBtn(){
		if(isElementExsit(payBtnLocator)){
			clickEle(payBtn,"点击投资按钮");
		}
		else{
			clickEle(payAgainBtn,"再次投资按钮");
		}
		return new InvestPageObject(driver);
	}
	public RedeemPageObject clickRansomBtn(){
		clickEle(ransomBtn,"赎回按钮");
		return new RedeemPageObject(driver);
	}
	public boolean verifyInthisPage(){
		return (isElementExsit(payBtnLocator)||isElementExsit(payAgainBtnLocator));
	}
}
