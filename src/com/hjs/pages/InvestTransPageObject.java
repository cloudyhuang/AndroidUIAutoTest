package com.hjs.pages;

import org.openqa.selenium.By;

import com.hjs.config.CommonAppiumPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2017年9月25日 下午5:56:11
* 类说明
*/
public class InvestTransPageObject extends CommonAppiumPage{
	@AndroidFindBy(id="second_market_invest_amount")
	private AndroidElement amountTV;		//转让投资金额
	@AndroidFindBy(id="subscribe_submit")
	private AndroidElement submitBtn;		//提交按钮
	@AndroidFindBy(xpath="//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/agreement']/preceding-sibling::android.widget.FrameLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/subscribe_protocol_checkbox']/android.widget.ImageView[1]")
	private AndroidElement agreementCheckBox;		//协议确认框
	
	private By amountTVLocator=By.id("second_market_invest_amount");
	public InvestTransPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public PayPageObject investTransPro() throws Exception{
		clickEle(agreementCheckBox,"协议确认框");
		clickEle(submitBtn,"提交按钮");
		return new PayPageObject(driver);
	}
	public boolean verifyInthisPage(){
		return isElementExsit(amountTVLocator);
	}

}
