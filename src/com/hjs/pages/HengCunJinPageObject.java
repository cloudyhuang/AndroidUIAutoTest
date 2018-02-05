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
	
	@AndroidFindBy(xpath="//android.widget.TextView[@text='昨日收益(元)']/ancestor::android.widget.LinearLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/layout_top_bottom_text']//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textview_bottom']")
	private AndroidElement lastDayProfitValue;		//昨日收益数值
	@AndroidFindBy(xpath="//android.widget.TextView[@text='累计收益(元)']/ancestor::android.widget.LinearLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/layout_top_bottom_text']//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textview_bottom']")
	private AndroidElement totalProfitValue;		//累计收益数值
	@AndroidFindBy(id="tv_mix_product_type")
	private AndroidElement hengCunJinProductType;		//持有产品恒存金-灵活理财
	
	private By totalAssetLocator=By.id("tv_mix_total_asset");
	public HengCunJinPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public CurrentDepositProductDetailPageObject gotoCurrentDepositProductDetailPage(){
		clickEle(hengCunJinProductType,"持有产品恒存金-灵活理财");
		return new CurrentDepositProductDetailPageObject(driver);
	}
	public String getTotalAsset(){
		String totalAssetValue=Util.get2DecimalPointsNumInString(super.getEleText(totalAsset, "总资产"));
		return totalAssetValue;
	}
	public boolean verifyInthisPage(){
		return isElementExsit(totalAssetLocator);
	}
	

}
