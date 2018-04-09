package com.hjs.pages;

import java.text.ParseException;

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
* @version 创建时间：2018年3月1日 下午5:17:42
* 类说明
*/
public class TransRecordDetailPageObject extends CommonAppiumPage{
	@AndroidFindBy(id="textView_product")
	private AndroidElement productNameTV;		//产品名称
	@AndroidFindBy(id="textView_remark")
	private AndroidElement remarkTV;		//备注
	@AndroidFindBy(id="textView_tradeMoney")
	private AndroidElement trandeMoneyTV;		//交易金额
	@AndroidFindBy(id="textView_tradeState")
	private AndroidElement tradeStateTV;		//交易状态
	@AndroidFindBy(id="textView_tradeTime")
	private AndroidElement tradeTimeTV;		//交易时间
	@AndroidFindBy(id="textView_tradeNumber")
	private AndroidElement tradeNumberTV;		//交易号
	
	
	private By productNameTVLocator=By.id("textView_product");
	public TransRecordDetailPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver); 
	}
	
	public boolean assertRecordDetail(String tranceNo) throws ParseException{
		String appTransTime=super.getEleText(tradeTimeTV, "交易时间");
		String expectTransTime = TransRecordInfo.getRecordInfo().get(tranceNo).getPayTime();
		long sec = Util.getDistanceTime(appTransTime, expectTransTime);
		Assert.assertTrue(sec < 120, "实际交易时间与预期交易时间误差超过120s");
		String appTransName=super.getEleText(productNameTV,"产品名称");
		String expectTransName = TransRecordInfo.getRecordInfo().get(tranceNo).getTransName();
		Assert.assertEquals(appTransName, expectTransName, "交易记录实际交易名称与预期不符");
		String appTransMoney=super.getEleText(trandeMoneyTV, "交易金额");
		appTransMoney = Util.get2DecimalPointsNumInString(appTransMoney);
		String expectTransMoney = TransRecordInfo.getRecordInfo().get(tranceNo).getPayMoney();
		Assert.assertEquals(appTransMoney, expectTransMoney, "交易记录实际交易金额与预期不符");
		return true;
	}
	public boolean verifyInthisPage(){
		return isElementExsit(productNameTVLocator);
	}

}
