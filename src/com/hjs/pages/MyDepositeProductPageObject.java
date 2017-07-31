package com.hjs.pages;


import org.openqa.selenium.By;
import org.testng.Assert;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import com.hjs.config.CommonAppiumPage;

public class MyDepositeProductPageObject extends CommonAppiumPage{
	private By productNameLocator=By.id("textView_product");
	public MyDepositeProductPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public MyDepositeProductDetailPageObject enterDepositeProductDetail(String productName){
		try{
			String productXpath="//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_product' and @text='"+productName+"']";
			super.scrollTo(productXpath);
			AndroidElement productNameEle=driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_product' and @text='"+productName+"']"));
			clickEle(productNameEle,"我的理财产品名称");
		}
		catch(Exception e){
			Assert.assertTrue(false,"未找到\""+productName+"\"理财产品");
		}
		return new MyDepositeProductDetailPageObject(driver);
	}
	public boolean verifyInthisPage(){
		return isElementExsit(productNameLocator);
	}
}
