package com.hjs.pages;

import org.openqa.selenium.By;

import com.hjs.config.CommonAppiumPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2017年12月14日 上午11:11:23
* 类说明
*/
public class DiscoverPageObject extends CommonAppiumPage{
	@AndroidFindBy(id="btnLogin")
	private AndroidElement loginEntrance;		//发现页-登录入口
	private By adCloseBtnLocator=By.id("icv_advertisement_close");		//广告关闭按钮locator
	public DiscoverPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
		this.closeAD();
	}
	public LoginPageObject clickLoginEntrance(){
		this.closeAD();
		clickEle(loginEntrance,"发现页-登录入口");
		return new LoginPageObject(driver);
	}
	public void closeAD(){
		if (isElementExsit(2,adCloseBtnLocator)){
			driver.findElement(adCloseBtnLocator).click();
		}
	}
	public boolean verifyInthisPage(){
		return isElementExsit(super.getWaitTime(),loginEntrance);
	}
}
