package com.hjs.pages;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import com.hjs.config.CommonAppiumPage;
import com.hjs.publics.Util;

public class AccountBanlancePageObject extends CommonAppiumPage{

	@AndroidFindBy(id="tv_available_balance")
	private AndroidElement availableBalance;		//可用余额
	@AndroidFindBy(id="tv_transit_balance")
	private AndroidElement transitBalance;		//在途(元)
	@AndroidFindBy(id="btn_balance_withdrawCash")
	private AndroidElement withdrawCashEntrance;		//提现入口
	@AndroidFindBy(id="btn_balance_recharge")
	private AndroidElement rechargeEntrance;		//充值入口
	//@AndroidFindBy(id="imgv_balance_tips")
	@AndroidFindBy(id="imgv_balance_tips_btn")
	private AndroidElement guideTips;		//引导按钮
	
	
	private By availableBalanceLocator=By.id("tv_available_balance");
	public AccountBanlancePageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
		this.skipGuideTips();
	}
	public int getAvailableBalance(){
		super.waitEleUnVisible(By.id("refresh_animationView"), super.getWaitTime());//等待刷新图标消失
		String stringAvailableBalance=availableBalance.getText();//取出余额，格式为x,xxx.xx
		stringAvailableBalance=Util.getNumInString(stringAvailableBalance); //去掉逗号和小数点，仅保留数字 xxxxxx
		double doubleAvailableBalance=Double.parseDouble(stringAvailableBalance); //转换为double
		doubleAvailableBalance=doubleAvailableBalance/100; //由于去掉小数点，所以除以100还原数值
		int intAvailableBalance=(int)doubleAvailableBalance; //取整
		return intAvailableBalance;
	}
	public boolean isBalanceMaskClose(){
		super.waitEleUnVisible(By.id("refresh_animationView"), super.getWaitTime());//等待刷新图标消失
		String stringAvailableBalance=super.getEleText(availableBalance, "余额数值");
		String stringTransitBalance=super.getEleText(transitBalance, "在途");
		String patt = "((-)?\\d{1,3})(,\\d{3})*(\\.\\d+)?$";	//含千分位分隔符的小数(包含正/负数)
		return stringAvailableBalance.matches(patt)&&stringTransitBalance.matches(patt);
	}
	public void skipGuideTips(){
		if(super.isElementExsit(5, guideTips)){
			clickEle(guideTips,"引导按钮");
		}
	}
	public WithdrawCashPageObject gotoWithdrawCashPage(){
		clickEle(withdrawCashEntrance,"提现入口");
		return new WithdrawCashPageObject(driver);
	}
	public RechargePageObject gotoRechargePage(){
		clickEle(rechargeEntrance,"充值入口");
		return new RechargePageObject(driver);
	}
	public SetBankCardPageObject rechargeToSetBankCardPage(){
		clickEle(rechargeEntrance,"充值入口");
		return new SetBankCardPageObject(driver);
	}
	public boolean verifyInthisPage(){
		return isElementExsit(availableBalanceLocator);
	}
	

}
