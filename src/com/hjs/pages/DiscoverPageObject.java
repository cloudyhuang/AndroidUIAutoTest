package com.hjs.pages;

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
	public DiscoverPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public LoginPageObject clickLoginEntrance(){
		clickEle(loginEntrance,"发现页-登录入口");
		return new LoginPageObject(driver);
	}
	public boolean verifyInthisPage(){
		return isElementExsit(super.getWaitTime(),loginEntrance);
	}
}
