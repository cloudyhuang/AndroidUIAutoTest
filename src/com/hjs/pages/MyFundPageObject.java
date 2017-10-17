package com.hjs.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.hjs.config.CommonAppiumPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2017年10月16日 下午4:40:51
* 类说明
*/
public class MyFundPageObject extends CommonAppiumPage{
	@AndroidFindBy(id="tv_fund_value")
	private AndroidElement fundValue;		//基金总市值
	
	By fundValueLocator=By.id("tv_fund_value");
	public MyFundPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
		Assert.assertTrue(this.verifyInthisPage(), "未进入我持有的基金页面");
	}
	public void enterFund(String fundName){
		AndroidElement myFundName=driver.findElement(By.xpath(""));
	}
	public boolean verifyInthisPage(){
		return isElementExsit(fundValueLocator);
	}
}
