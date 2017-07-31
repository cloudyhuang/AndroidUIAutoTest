package com.hjs.pages;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import com.hjs.config.CommonAppiumPage;

public class InvestResultPageObject extends CommonAppiumPage{
	
	@AndroidFindBy(id="order_result_dec")
	private AndroidElement orderResultDesc;		//投资结果描述
	@AndroidFindBy(id="subscribe_dec")
	private AndroidElement failResultDesc;		//失败投资结果描述
	
	private By orderResultDescLocator=By.id("order_result_dec");
	private By failResultDescLocator=By.id("subscribe_dec");
	public InvestResultPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public String getInvestResult(){
		while(true){
			if(isElementExsit(1,orderResultDescLocator)){
				return orderResultDesc.getText();
			}
			else if(isElementExsit(1,failResultDescLocator)){
				return failResultDesc.getText();
			}
			else return null;
		}
		
	}
	public boolean verifyInthisPage(){
//		return (isElementExsit(orderResultDescLocator)||isElementExsit(failResultDescLocator));
		return isElementsExsit(orderResultDescLocator,failResultDescLocator);
	}
}
