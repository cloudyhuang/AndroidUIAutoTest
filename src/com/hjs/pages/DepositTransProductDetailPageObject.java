package com.hjs.pages;

import org.openqa.selenium.By;

import com.hjs.config.CommonAppiumPage;
import com.hjs.pages.InvestPageObject;
import com.hjs.pages.InvestTransPageObject;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2017年9月25日 下午3:24:29
* 类说明
*/
public class DepositTransProductDetailPageObject extends CommonAppiumPage{
	@AndroidFindBy(id="second_market_subscribe_btn")
	private AndroidElement investBtn;		//投资按钮
	
	private By transferDayLocator=By.id("transfer_day");
	public DepositTransProductDetailPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public InvestTransPageObject clickPayBtn(){
		clickEle(investBtn,"投资按钮");
		return new InvestTransPageObject(driver);
	}
	public boolean verifyInthisPage(){
		return isElementExsit(transferDayLocator);
	}

}
