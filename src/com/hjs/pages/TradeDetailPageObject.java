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
* @version 创建时间：2018年1月24日 下午3:06:48
* 类说明
*/
public class TradeDetailPageObject extends CommonAppiumPage{
	@AndroidFindBy(id="textView_product")
	private AndroidElement tradeName;		//交易名称
	@AndroidFindBy(id="textView_tradeMoney")
	private AndroidElement tradeMoney;		//交易金额
	@AndroidFindBy(id="textView_tradeTime")
	private AndroidElement tradeState;		//交易时间
	@AndroidFindBy(id="textView_tradeNumber")
	private AndroidElement tradeNumber;		//交易号
	
	private By tradeNameLocator=By.id("textView_product");
	
	public boolean assertTradeDetails(String tradeType,String tradeValue){
		Assert.assertEquals(super.getEleText(tradeName, "交易名称"), tradeType);
		String tradeMoneyText=super.getEleText(tradeMoney, "交易金额");
		Assert.assertEquals(Util.get2DecimalPointsNumInString(tradeMoneyText), tradeValue);
		String time=super.getEleText(tradeState,"交易时间");
		Assert.assertTrue(Util.isValidDate(time),"交易时间不合法");
		String tradeNum=super.getEleText(tradeNumber, "交易号");
		String patt = "\\d{20}";	//20位数字
		Assert.assertTrue(tradeNum.matches(patt),"交易号不为20位数字");
		return true;
	}
	
	public TradeDetailPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public boolean verifyInthisPage(){
		return isElementExsit(tradeNameLocator);
	}
}
