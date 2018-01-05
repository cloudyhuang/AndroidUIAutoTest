package com.hjs.pages;

import org.openqa.selenium.By;

import com.hjs.config.CommonAppiumPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2018年1月2日 下午5:32:54
* 类说明
*/
public class DebugPageObject extends CommonAppiumPage{
	@AndroidFindBy(id="btn_debug_setting")
	private AndroidElement debugSettingBtn;		//debug setting 按钮
	
	private By debugSettingBtnLocator=By.id("btn_debug_setting");
	public DebugPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public DebugSettingPageObject gotoDebugSettingPage(){
		clickEle(debugSettingBtn,"debug setting 按钮");
		return new DebugSettingPageObject(driver);
	}
	public boolean verifyInthisPage(){
		return isElementExsit(debugSettingBtnLocator);
	}
}
