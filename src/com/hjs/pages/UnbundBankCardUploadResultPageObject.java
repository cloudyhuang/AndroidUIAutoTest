package com.hjs.pages;

import org.openqa.selenium.By;

import com.hjs.config.CommonAppiumPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2018年4月11日 下午4:53:09
* 类说明
*/
public class UnbundBankCardUploadResultPageObject extends CommonAppiumPage{
	
	@AndroidFindBy(id="tv_success_title")
	private AndroidElement successTitle;		//提交结果
	
	private By successTitleLocator=By.id("tv_success_title");
	public String getUploadResult(){
		String result=super.getEleText(successTitle, "提交结果");
		return result;
		
	}
	public UnbundBankCardUploadResultPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public boolean verifyInthisPage(){
		return isElementExsit(successTitleLocator);
	}
}
