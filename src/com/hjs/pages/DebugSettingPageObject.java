package com.hjs.pages;

import java.time.Duration;

import org.openqa.selenium.By;

import com.hjs.config.CommonAppiumPage;
import com.hjs.publics.Util;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2018年1月2日 下午5:45:16
* 类说明
*/
public class DebugSettingPageObject extends CommonAppiumPage{
	@AndroidFindBy(id="editText_lockscreen_time")
	private AndroidElement touchEvtTimeoutInput;		//锁屏安全时间输入框
	@AndroidFindBy(id="editText_gst_validtime")
	private AndroidElement gstExpiredTimeOutInput;		//手密失效时间输入框
	
	@AndroidFindBy(id="tv_right")
	private AndroidElement saveBtn;		//保存按钮
	
	private By touchEvtTimeoutInputLocator=By.id("editText_lockscreen_time");
	public DebugSettingPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public void changeTouchEvtTimeout(Duration timeOut){
		String time=String.valueOf(timeOut.getSeconds());
		super.clearKeys(touchEvtTimeoutInput, "锁屏安全时间输入框");
		super.sendKeys(touchEvtTimeoutInput, time);
		clickEle(saveBtn,"保存按钮");
	}
	public void changeGstExpiredTimeOut(Duration timeOut){
		String time=String.valueOf(timeOut.toMinutes());
		super.clearKeys(gstExpiredTimeOutInput, "手密失效时间输入框");
		super.sendKeys(gstExpiredTimeOutInput, time);
		clickEle(saveBtn,"保存按钮");
	}
	public boolean verifyInthisPage(){
		return isElementExsit(touchEvtTimeoutInputLocator);
	}
	
}
