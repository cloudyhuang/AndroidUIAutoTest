package com.hjs.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import com.hjs.config.CommonAppiumPage;
import com.hjs.publics.Util;

public class AccountBanlancePageObject extends CommonAppiumPage{

	@AndroidFindBy(id="tv_available_balance")
	private AndroidElement availableBalance;		//可用余额
	@AndroidFindBy(id="imgv_balance_eye")
	private AndroidElement balanceEye;		//余额掩码
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
	public String getStringBanlance(){
		super.waitEleUnVisible(By.id("refresh_animationView"), super.getWaitTime());//等待刷新图标消失
		String balance=super.getEleText(availableBalance, "可用余额数值");
		return Util.get2DecimalPointsNumInString(balance);
		
	}
	public void openBanlanceEye() throws Exception{
		String balance=super.getEleText(availableBalance, "可用余额数值");
		String patt = "((-)?\\d{1,3})(,\\d{3})*(\\.\\d+)?$";	//含千分位分隔符的小数(包含正/负数)
		if(balance.matches(patt)){
			clickEle(balanceEye,"余额掩码");
			String transitBalanceValue=super.getEleText(transitBalance, "在途数值");
			Assert.assertEquals(transitBalanceValue, "****");
		}
		else if(balance.equals("****")){
			throw new Exception("当前已经是掩码");
		}
		else throw new Exception("余额显示格式有误，非千分位小数或****");
	}
	public void closeBanlanceEye() throws Exception{
		String balance=super.getEleText(availableBalance, "可用余额数值");
		String patt = "((-)?\\d{1,3})(,\\d{3})*(\\.\\d+)?$";	//含千分位小数
		if(balance.matches(patt)){
			throw new Exception("当前是千分位显示，无需关闭掩码");
		}
		else if(balance.equals("****")){
			clickEle(balanceEye,"余额掩码");
			String transitBalanceValue=super.getEleText(transitBalance, "在途数值");
			Assert.assertTrue(transitBalanceValue.matches(patt), "可用余额打开掩码后未显示为千分位小数");
		}
		else throw new Exception("余额显示格式有误，非千分位小数或****");
	}
	/**
     * 余额掩码是否关闭
     * @return true 掩码关闭，false 掩码打开
     */
	public boolean isBalanceMaskClose(){
		super.waitEleUnVisible(By.id("refresh_animationView"), super.getWaitTime());//等待刷新图标消失
		String stringAvailableBalance=super.getEleText(availableBalance, "余额数值");
		String stringTransitBalance=super.getEleText(transitBalance, "在途数值");
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
