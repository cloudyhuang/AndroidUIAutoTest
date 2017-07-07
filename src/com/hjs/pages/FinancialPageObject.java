package com.hjs.pages;

import java.io.IOException;
import java.text.ParseException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import com.hjs.Interface.InitProduct;
import com.hjs.config.CommonAppiumPage;
import com.hjs.publics.Util;

public class FinancialPageObject extends CommonAppiumPage{

	private By titleSwitchLocator=By.id("title_switch");
	public FinancialPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public DepositProductDetailPageObject clickDepositProduct(String DepositProductName) throws Exception{
		//driver.scrollTo(DepositProductName);
		String productXpath="//android.widget.TextView[@text='"+DepositProductName+"']";
		super.scrollTo(productXpath);
		AndroidElement DepositProduct=driver.findElement(By.xpath("//android.widget.TextView[@text='"+DepositProductName+"']"));
		clickEle(DepositProduct,"定期产品名称："+DepositProductName);
		return new DepositProductDetailPageObject(driver);
	}
	public CurrentDepositProductDetailPageObject clickCurrentDepositProduct(String DepositProductName) throws Exception{
		//driver.scrollTo(DepositProductName);
		String productXpath="//android.widget.TextView[@text='"+DepositProductName+"']";
		super.scrollTo(productXpath);
		AndroidElement DepositProduct=driver.findElement(By.xpath("//android.widget.TextView[@text='"+DepositProductName+"']"));
		clickEle(DepositProduct,"活期产品名称："+DepositProductName);
		return new CurrentDepositProductDetailPageObject(driver);
	}
	public String testProductInfo() throws Exception{
		String currentDate=Util.getcurrentDate();
		String productName="黄XAutoTest产品"+ currentDate;
		String minBuyAmt="100";
		String baseProductName="黄XAutoTest基础产品" + currentDate;
		String productLimit="365";
		String displayRate="6+0.3";
		String mrktPlusRate="0.01";
		InitProduct product = new InitProduct.Builder(productName).setMinBuyAmt(minBuyAmt)
				.setBaseProductName(baseProductName)
				.setProductLimit(productLimit).setDisplayRate(displayRate)
				.setMrktPlusRate(mrktPlusRate).build();
		product.creatProduct();
		swipeToDown(1000,1);	//下滑刷新
		String productXpath="//android.widget.TextView[@text='"+productName+"']";
		super.scrollTo(productXpath);
		//driver.scrollTo(productName);
		AndroidElement baseProfitEle=driver.findElement(By.xpath("//android.widget.TextView[@text='"+productName+"']/ancestor::android.widget.LinearLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/item_financial_home_privilege']//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/base_profit']"));
		AndroidElement extraProfitEle=driver.findElement(By.xpath("//android.widget.TextView[@text='"+productName+"']/ancestor::android.widget.LinearLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/item_financial_home_privilege']//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/extra_profit']"));
		AndroidElement productLimitAndMinBuyAmtEle=driver.findElement(By.xpath("//android.widget.TextView[@text='"+productName+"']/ancestor::android.widget.LinearLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/item_financial_home_privilege']//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/home_page_item_time']"));
		String expectBaseProfit=displayRate.split("\\+")[0];
		String appBaseProfit=Util.getNumInString(baseProfitEle.getText());//前端显示x%,取出x
		Assert.assertEquals(expectBaseProfit, appBaseProfit,"前端显示收益率与OMC配置不同，OMC："+expectBaseProfit+"前端显示："+appBaseProfit);
		
		String expectExtraProfit=mrktPlusRate;
		String appExtraProfit=Util.getNumInString(extraProfitEle.getText());//前端显示x%,取出x
		double doubleAppExtraProfit=Util.stringToDouble(appExtraProfit);
		doubleAppExtraProfit=doubleAppExtraProfit/100;
		appExtraProfit=String.valueOf(doubleAppExtraProfit);
		Assert.assertEquals(expectExtraProfit, appExtraProfit,"前端显示额外收益率与OMC配置不同，OMC："+expectExtraProfit+"前端显示："+appExtraProfit);
		String appProductLimit=Util.getNumInString(productLimitAndMinBuyAmtEle.getText().split("\\|")[0]); //前端显示x 天  |  y元起 ,取出x
		Assert.assertEquals(productLimit,appProductLimit,"前端显示投资期限与OMC配置不同，OMC："+productLimit+"前端显示："+appProductLimit);	//验证投资期限
		String appMinBuyAmt=Util.getNumInString(productLimitAndMinBuyAmtEle.getText().split("\\|")[1]);	//前端显示x 天  |  y元起 ,取出y
		Assert.assertEquals(minBuyAmt,appMinBuyAmt,"前端显示起投金额与OMC配置不同，OMC："+minBuyAmt+"前端显示："+appMinBuyAmt);	//验证起投金额
		return productName;
	}
	public boolean verifyInthisPage(){
		return isElementExsit(titleSwitchLocator);
	}
}
