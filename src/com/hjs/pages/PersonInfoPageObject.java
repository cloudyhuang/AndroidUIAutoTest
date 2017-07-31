package com.hjs.pages;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import com.hjs.config.CommonAppiumPage;

public class PersonInfoPageObject extends CommonAppiumPage{
	@AndroidFindBy(id="textView_userName")
	private AndroidElement userName;		//用户姓名
	private By userNameLocator=By.id("textView_userName");	////用户姓名Locator
	public PersonInfoPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public String getUserName(){
		return userName.getText();
	}
	public RealNamePageObject gotoRealNamePage(){
		if(this.getUserName().equals("未实名")){
		clickEle(userName,"用户姓名");
		return new RealNamePageObject(driver);
		}
		else return null;
	}
	public boolean verifyInthisPage(){
		return isElementExsit(userNameLocator);
	}

}
