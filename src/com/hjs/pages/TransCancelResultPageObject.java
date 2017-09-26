package com.hjs.pages;

import org.openqa.selenium.By;

import com.hjs.config.CommonAppiumPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2017年9月25日 上午10:14:48
* 类说明
*/
public class TransCancelResultPageObject extends CommonAppiumPage{
	
	@AndroidFindBy(id="status_dec")
	private AndroidElement cancelResult;		//取消转让结果
	
	private By cancelResultLocator=By.id("status_dec");
	public TransCancelResultPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public String getTransCancelResult(){
		return cancelResult.getText();
	}
	public boolean verifyInthisPage(){
		return isElementExsit(cancelResultLocator);
	}
}
