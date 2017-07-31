package com.hjs.pages;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import com.hjs.config.CommonAppiumPage;

public class WithdrawCashResultPageObject extends CommonAppiumPage{

	
	@AndroidFindBy(id="status_dec")
	private AndroidElement statusDec;		//提现结果描述
	@AndroidFindBy(id="filled_view")
	private AndroidElement seeBalanceEntrance;		//查看余额入口
	
	private By statusDecLocator=By.id("status_dec");
	public WithdrawCashResultPageObject(
			AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public String getWithdrawCashResult(){
		return statusDec.getText();
	}
	
	public AccountBanlancePageObject gotoBalancePage(){
		clickEle(seeBalanceEntrance,"查看余额入口");
		return new AccountBanlancePageObject(driver);
	}
	public boolean verifyInthisPage(){
		return isElementExsit(statusDecLocator);
	}

}
