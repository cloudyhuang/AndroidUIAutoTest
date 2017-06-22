package com.hjs.pages;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import com.hjs.config.CommonAppiumPage;

public class MinePageObject extends CommonAppiumPage{
	@AndroidFindBy(id="imageView_person")
	private AndroidElement personImage;		//个人头像
	@AndroidFindBy(xpath="//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textview_top' and @text='账户余额']")
	private AndroidElement accountBanlance;		//账户余额
	@AndroidFindBy(xpath="//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textview_top' and @text='定期理财']")
	private AndroidElement myDepositeProduct;		//定期理财
	private By personImageLocator=By.id("imageView_person");	//个人头像Locator
	
	public MinePageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	
	public PersonSettingPageObject enterPersonSetting(){
		clickEle(personImage,"我的-个人头像");
		return new PersonSettingPageObject(driver);
	}
	public MyDepositeProductPageObject enterMyDepositeProductPage(){
		clickEle(myDepositeProduct,"定期理财");
		return new MyDepositeProductPageObject(driver);
	}
	public AccountBanlancePageObject enterAccountBanlancePage(){
		clickEle(accountBanlance,"账户余额入口");
		return new AccountBanlancePageObject(driver);
	}
	public boolean verifyInthisPage(){
		return isElementExsit(personImageLocator);
	}
}
