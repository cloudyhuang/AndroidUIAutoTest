package com.hjs.pages;

import org.openqa.selenium.By;

import com.hjs.config.CommonAppiumPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2017年9月29日 上午10:46:06
* 类说明
*/
public class FundDetailPageObject extends CommonAppiumPage{

	@AndroidFindBy(id="btn_enter_ym")
	private AndroidElement enterYMBtn;		//进入盈米按钮
	@AndroidFindBy(id="fund_yesterdayChg")
	private AndroidElement yesterdayChg;		//7日年化
	@AndroidFindBy(id="fund_nav")
	private AndroidElement fundNav;		//万份收益
	@AndroidFindBy(id="fund_typeDesc")
	private AndroidElement fundTypeDesc;		//基金类型
	@AndroidFindBy(id="fund_riskLevelDesc")
	private AndroidElement fundRiskLevelDesc;		//基金风险等级描述
	@AndroidFindBy(id="btn_fund_purchase")
	private AndroidElement fundPurchaseBtn;		//基金申购按钮
	
	private By fundPurchaseBtnLocator=By.id("btn_fund_purchase");
	
	
	public FundDetailPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
		this.skipEnterYMTips();
	}
	public FundPurchasePageObject purchaseFund(){
		clickEle(fundPurchaseBtn,"基金申购按钮");
		return new FundPurchasePageObject(driver);
	}
	public void skipEnterYMTips(){
		if(super.isElementExsit(5, enterYMBtn)){
			clickEle(enterYMBtn,"进入盈米按钮");
		}
		
	}
	public boolean verifyInthisPage(){
		return isElementExsit(fundPurchaseBtnLocator);
	}

}
