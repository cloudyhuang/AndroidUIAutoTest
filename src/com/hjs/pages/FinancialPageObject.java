package com.hjs.pages;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.testng.Assert;
import org.testng.Reporter;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import com.hjs.Interface.InitProduct;
import com.hjs.config.CommonAppiumPage;
import com.hjs.publics.Util;

public class FinancialPageObject extends CommonAppiumPage{

	private By titleSwitchLocator=By.id("title_switch");
	private By refreshViewLocator=By.id("refresh_animationView");
	public FinancialPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public DepositProductDetailPageObject clickDepositProduct(String DepositProductName) throws Exception{
		//driver.scrollTo(DepositProductName);
		this.clickWenJian();
		waitEleUnVisible(refreshViewLocator, 30);
		String productXpath="//android.widget.TextView[@text='"+DepositProductName+"']";
		super.scrollTo(productXpath);
		AndroidElement DepositProduct=driver.findElement(By.xpath("//android.widget.TextView[@text='"+DepositProductName+"']"));
		clickEle(DepositProduct,"定期产品名称："+DepositProductName);
		return new DepositProductDetailPageObject(driver);
	}
	public CurrentDepositProductDetailPageObject clickCurrentDepositProduct(String DepositProductName) throws Exception{
		//driver.scrollTo(DepositProductName);
		this.clickWenJian();
		waitEleUnVisible(refreshViewLocator, 30);
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
		String displayRate="6";
		String mrktPlusRate="0.01";
		InitProduct product = new InitProduct.Builder(productName).setMinBuyAmt(minBuyAmt)
				.setBaseProductName(baseProductName)
				.setProductLimit(productLimit).setDisplayRate(displayRate)
				.setMrktPlusRate(mrktPlusRate).build();
		product.creatProduct();
		this.clickWenJian();	//点击稳健标签
		waitEleUnVisible(refreshViewLocator, 30);
		swipeToDown(1000,1);	//下滑刷新
		waitEleUnVisible(refreshViewLocator, 30);
		swipeToDown(1000,1);	//下滑刷新
		waitEleUnVisible(refreshViewLocator, 30);
		threadsleep(5000);
		String productXpath="//android.widget.TextView[@text='"+productName+"']";
		super.scrollTo(productXpath);
		AndroidElement baseProfitEle=driver.findElement(By.xpath("//android.widget.TextView[@text='"+productName+"']/ancestor::android.widget.LinearLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/item_financial_home_privilege']//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/base_profit']"));
		AndroidElement extraProfitEle=driver.findElement(By.xpath("//android.widget.TextView[@text='"+productName+"']/ancestor::android.widget.LinearLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/item_financial_home_privilege']//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/extra_profit']"));
		AndroidElement productLimitAndMinBuyAmtEle=driver.findElement(By.xpath("//android.widget.TextView[@text='"+productName+"']/ancestor::android.widget.LinearLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/item_financial_home_privilege']//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/home_page_item_time']"));
		String expectBaseProfit=displayRate.split("\\+")[0];
		String appBaseProfit=Util.getNumInString(baseProfitEle.getText());//前端显示x%,取出x
		Assert.assertEquals(appBaseProfit,expectBaseProfit,"前端显示收益率与OMC配置不同，OMC："+expectBaseProfit+"前端显示："+appBaseProfit);
		
		String expectExtraProfit=mrktPlusRate;
		String appExtraProfit=Util.getNumInString(extraProfitEle.getText());//前端显示x%,取出x
		double doubleAppExtraProfit=Util.stringToDouble(appExtraProfit);
		doubleAppExtraProfit=doubleAppExtraProfit/100;
		appExtraProfit=String.valueOf(doubleAppExtraProfit);
		Assert.assertEquals(appExtraProfit, expectExtraProfit,"前端显示额外收益率与OMC配置不同，OMC："+expectExtraProfit+"前端显示："+appExtraProfit);
		String appProductLimit=Util.getNumInString(productLimitAndMinBuyAmtEle.getText().split("\\|")[0]); //前端显示x 天  |  y元起 ,取出x
		Assert.assertEquals(appProductLimit,productLimit,"前端显示投资期限与OMC配置不同，OMC："+productLimit+"前端显示："+appProductLimit);	//验证投资期限
		String appMinBuyAmt=Util.getNumInString(productLimitAndMinBuyAmtEle.getText().split("\\|")[1]);	//前端显示x 天  |  y元起 ,取出y
		Assert.assertEquals(appMinBuyAmt,minBuyAmt,"前端显示起投金额与OMC配置不同，OMC："+minBuyAmt+"前端显示："+appMinBuyAmt);	//验证起投金额
		return productName;
	}
	public void productPullOffAndFindProduct(String productName) throws Exception{
		InitProduct product = new InitProduct.Builder("").build();
		product.productPullOffShelves(productName);
		swipeToDown(1000,1);	//下滑刷新
		waitEleUnVisible(refreshViewLocator, 30);
		swipeToDown(1000,1);	//下滑刷新
		waitEleUnVisible(refreshViewLocator, 30);
		threadsleep(5000);
		String productXpath="//android.widget.TextView[@text='"+productName+"']";
		try {
			super.scrollTo(productXpath);
		} catch (Exception e) {
			throw new Exception("找不到下架产品");
		}
	}
	public void clickWenJian(){
		AndroidElement titleSwitchEle=driver.findElement(titleSwitchLocator);
		Point elpoint = titleSwitchEle.getLocation();
    	Dimension elSize = titleSwitchEle.getSize();
    	double startX = elpoint.getX();
    	double startY = elpoint.getY();
    	double endX =elSize.getWidth()+startX;
    	double endY =elSize.getHeight()+startY;
    	double centerX = (startX+endX)/4;
    	double centerY = (startY+endY)/2;
        JavascriptExecutor js = (JavascriptExecutor) driver;
        HashMap<String, Double> tapObject = new HashMap<String, Double>();
        tapObject.put("x", centerX);
        tapObject.put("y", centerY);
        tapObject.put("duration", 0.0);
        tapObject.put("touchCount", 1.0);
        tapObject.put("tapCount", 3.0);
        js.executeScript("mobile: tap", tapObject);
	}
	public boolean verifyInthisPage(){
		return isElementExsit(titleSwitchLocator);
	}
}
