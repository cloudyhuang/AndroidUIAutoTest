package com.hjs.pages;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import com.hjs.config.CommonAppiumPage;

public class AccountBanlancePageObject extends CommonAppiumPage{

	@AndroidFindBy(id="tv_available_balance")
	private AndroidElement availableBalance;		//可用余额
	@AndroidFindBy(id="btn_balance_withdrawCash")
	private AndroidElement withdrawCashEntrance;		//提现入口
	@AndroidFindBy(id="btn_balance_recharge")
	private AndroidElement rechargeEntrance;		//充值入口
	
	private By availableBalanceLocator=By.id("tv_available_balance");
	public AccountBanlancePageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public int getAvailableBalance(){
		String stringAvailableBalance=availableBalance.getText();
		double doubleAvailableBalance=Double.parseDouble(stringAvailableBalance);
		int intAvailableBalance=(int)doubleAvailableBalance;
		return intAvailableBalance;
	}
	public WithdrawCashPageObject gotoWithdrawCashPage(){
		clickEle(withdrawCashEntrance,"提现入口");
		return new WithdrawCashPageObject(driver);
	}
	public RechargePageObject gotoRechargePage(){
		clickEle(rechargeEntrance,"充值入口");
		return new RechargePageObject(driver);
	}
	public boolean verifyInthisPage(){
		return isElementExsit(availableBalanceLocator);
	}
	

}
