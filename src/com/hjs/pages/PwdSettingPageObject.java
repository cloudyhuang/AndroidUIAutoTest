package com.hjs.pages;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import com.hjs.config.CommonAppiumPage;

public class PwdSettingPageObject extends CommonAppiumPage{
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@text,'重设登录密码')]")
	private AndroidElement resetLoginPwd;		//重设登录密码跳转按钮
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@text,'重设交易密码')]")
	private AndroidElement resetTradePwd;		//重设交易密码跳转按钮
	
	private By resetTradePwdLocator=By.xpath("//android.widget.TextView[contains(@text,'重设交易密码')]");
	public PwdSettingPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public TradePwdResetPageObject gotoTradePwdPage(){
		clickEle(resetTradePwd,"重设登录密码跳转按钮");
		return new TradePwdResetPageObject(driver);
	}
	public LoginPwdResetPageObject gotoLoginPwdPage(){
		clickEle(resetLoginPwd,"重设登录密码跳转按钮");
		return new LoginPwdResetPageObject(driver);
	}
	public boolean verifyInthisPage(){
		return isElementExsit(resetTradePwdLocator);
	}

}
