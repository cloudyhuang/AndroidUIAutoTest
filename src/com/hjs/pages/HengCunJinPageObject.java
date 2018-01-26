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
* @version 创建时间：2018年1月25日 下午5:26:36
* 类说明
*/
public class HengCunJinPageObject extends CommonAppiumPage{
	@AndroidFindBy(id="tv_mix_total_asset")
	private AndroidElement totalAsset;		//总资产
	
	private By totalAssetLocator=By.id("tv_mix_total_asset");
	public HengCunJinPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public String getTotalAsset(String value){
		String totalAssetValue=Util.get2DecimalPointsNumInString(super.getEleText(totalAsset, "总资产"));
		return totalAssetValue;
	}
	public boolean verifyInthisPage(){
		return isElementExsit(totalAssetLocator);
	}
	

}
