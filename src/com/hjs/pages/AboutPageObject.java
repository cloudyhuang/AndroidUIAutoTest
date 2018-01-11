package com.hjs.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.hjs.config.CommonAppiumPage;
import com.hjs.publics.Util;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;


/**
 * @author huangxiao
 * @version 创建时间：2018年1月11日 下午4:22:41 类说明
 */
public class AboutPageObject extends CommonAppiumPage {
	@AndroidFindBy(id="textView_appVersion")
	private AndroidElement appVersioTV;		//app版本文字
	@AndroidFindBy(id="textView_new_appVersion")
	private AndroidElement newAppVersioTV;		//app新版本文字
	@AndroidFindBy(id="btn_updateNow")
	private AndroidElement updateNowBtn;		//立即更新按钮
	
	
	private By appVersioTVLocator=By.id("textView_appVersion");
	public AboutPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public boolean isHaveNewAppVersion(){
		String appVersion=Util.getNumInString(super.getEleText(appVersioTV, "app版本文字"));
		int intAppVersion=Integer.parseInt(appVersion);
		if(super.isElementExsit(getWaitTime(), updateNowBtn)){
			String appNewVersion=Util.getNumInString(super.getEleText(newAppVersioTV, "app新版本文字"));
			int intAppNewVersion=Integer.parseInt(appNewVersion);
			Assert.assertTrue(intAppNewVersion>intAppVersion,"出现更新按钮，但当前版本未小于最新版本");
			return true;
		}
		else
			return false;
		
	}
	public boolean verifyInthisPage(){
		return isElementExsit(appVersioTVLocator);
	}

}
