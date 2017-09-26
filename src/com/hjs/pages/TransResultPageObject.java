package com.hjs.pages;

import org.openqa.selenium.By;

import com.hjs.config.CommonAppiumPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2017年9月22日 下午5:54:06
* 类说明
*/
public class TransResultPageObject extends CommonAppiumPage{
	
	@AndroidFindBy(id="status_dec")
	private AndroidElement resultTV;		//转让结果
	
	private By resultTVLocator=By.id("status_dec");
	public TransResultPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public String getTransResult(){
		return resultTV.getText();
	}
	public boolean verifyInthisPage(){
		return isElementExsit(resultTVLocator);
	}

}
