package com.hjs.pages;

import java.util.Set;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.hjs.config.CommonAppiumPage;
import com.hjs.publics.Util;
import com.hjs.testDate.TransRecordInfo;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2018年2月8日 下午2:19:22
* 类说明
*/
public class TransRecordPageObject extends CommonAppiumPage{
	@AndroidFindBy(id="tv_trade_records_menu_title")
	private AndroidElement tradeRecordsMenuFilter;		//交易明细筛选
	
	private By tradeRecordsMenuFilterLocator=By.id("tv_trade_records_menu_title");
	public TransRecordPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	} 
	public boolean assertTransRecord() throws Exception{
		Set<String> set = TransRecordInfo.getRecordInfo().keySet(); // 取出所有的key值TranceNo
		for (String TransNo : set) {
			String transNoXpath = "//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textVie_tradeNo' and @text='"
					+ TransNo + "']";
			String transTimeXpath = transNoXpath
					+ "/preceding-sibling::android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_time']";
			String transPayModeXpath = transNoXpath
					+ "/parent::android.widget.LinearLayout/parent::android.widget.LinearLayout//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_payway']";
			String transResultXpath = transNoXpath
					+ "/parent::android.widget.LinearLayout/parent::android.widget.LinearLayout//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_tradeResult']";
			String transNameXpath = transNoXpath
					+ "/parent::android.widget.LinearLayout/parent::android.widget.LinearLayout//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_product']";
			String transMoneyXpath = transNoXpath
					+ "/parent::android.widget.LinearLayout/parent::android.widget.LinearLayout//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_money']";
			super.scrollTo(transNoXpath);
			AndroidElement transNoEle = driver.findElement(By.xpath(transNoXpath));
			String appTransNo = super.getEleText(transNoEle, "app交易号");
			AndroidElement transTimeEle = driver.findElement(By.xpath(transTimeXpath));
			String appTransTime = super.getEleText(transTimeEle, "app交易时间");
			String expectTransTime = TransRecordInfo.getRecordInfo().get(TransNo).getPayTime();
			long sec = Util.getDistanceTime(appTransTime, expectTransTime);
			Assert.assertTrue(sec < 120, "实际交易时间与预期交易时间误差超过120s");
			AndroidElement transPayModeEle = driver.findElement(By.xpath(transPayModeXpath));
			String appPayMode = super.getEleText(transPayModeEle, "app交易方式");
			String expectPayMode = TransRecordInfo.getRecordInfo().get(TransNo).getPayMode();
			Assert.assertEquals(appPayMode, expectPayMode, "交易记录实际支付方式与预期不一致");
			AndroidElement transResultEle = driver.findElement(By.xpath(transResultXpath));
			String appTransResult = super.getEleText(transResultEle, "app交易结果");
			AndroidElement transNameEle = driver.findElement(By.xpath(transNameXpath));
			String appTransName = super.getEleText(transNameEle, "app交易名称");
			String expectTransName = TransRecordInfo.getRecordInfo().get(TransNo).getTransName();
			Assert.assertEquals(appTransName, expectTransName, "交易记录实际交易名称与预期不符");
			AndroidElement transMoneyEle = driver.findElement(By.xpath(transMoneyXpath));
			String appTransMoney = super.getEleText(transMoneyEle, "app交易金额");
			appTransMoney = Util.get2DecimalPointsNumInString(appTransMoney);
			String expectTransMoney = TransRecordInfo.getRecordInfo().get(TransNo).getPayMoney();
			Assert.assertEquals(appTransMoney, expectTransMoney, "交易记录实际交易金额与预期不符");
		}
		return true;
	}
	public boolean verifyInthisPage(){
		return isElementExsit(tradeRecordsMenuFilterLocator);
	}
}
