package com.hjs.pages;

import org.openqa.selenium.By;

import com.hjs.config.CommonAppiumPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2017年9月28日 下午3:34:09
* 类说明
*/
public class FundPurchaseResultPageObject extends CommonAppiumPage{
	
	@AndroidFindBy(id="ok")
	private AndroidElement finishBtn;		//完成按钮
	@AndroidFindBy(xpath="//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/title'][1]")
	private AndroidElement resultTV;		//申购结果
	
	private By resultTVLocator=By.xpath("//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/title'][1]");
	public FundPurchaseResultPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}

	public String getPurchaseResult(){
		return resultTV.getText();
	}
	public boolean verifyInthisPage(){
		return isElementExsit(resultTVLocator);
	}
}
