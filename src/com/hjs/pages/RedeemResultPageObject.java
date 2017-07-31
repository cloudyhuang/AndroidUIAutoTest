package com.hjs.pages;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import com.hjs.config.CommonAppiumPage;

public class RedeemResultPageObject extends CommonAppiumPage{
	
	@AndroidFindBy(id="order_result_dec")
	private AndroidElement orderResultDec;		//赎回结果描述
	
	private By orderResultDecLocator=By.id("order_result_dec");
	
	public RedeemResultPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public String getRedeemResult(){
		return orderResultDec.getText();
	}
	public boolean verifyInthisPage(){
		return isElementExsit(orderResultDecLocator);
	}
}
