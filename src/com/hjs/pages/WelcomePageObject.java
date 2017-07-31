package com.hjs.pages;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import com.hjs.config.CommonAppiumPage;

public class WelcomePageObject extends CommonAppiumPage{
	
	@AndroidFindBy(id="perform_pager")
	private AndroidElement welcomeImage;		//欢迎页图
	@AndroidFindBy(id="perform_btn")
	private AndroidElement intoHomepageBtn;		//进入主页按钮
	@AndroidFindBy(id="ccv_skip")
	private AndroidElement skipBackgroundAD;		//跳过背景广告按钮
	
	private By welcomeImageLocator=By.id("perform_pager");	//欢迎页图Locator
	private By intoHomepageBtnLocator=By.id("perform_btn");	//进入主页按钮Locator
	private By skipBackgroundADLocator=By.id("ccv_skip");	//跳过背景广告按钮Locator
	public WelcomePageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public WelcomePageObject swipeWelcomeImage() throws Exception{
		if(isElementExsit(driver, welcomeImageLocator)){
			swipeToLeft(driver,600,3);
		}
		else throw new Exception("未出现欢迎页的滑动页");
		return new WelcomePageObject(driver);
	}
	public HomePageObject intoHomepage(){
		clickEle(intoHomepageBtn,"欢迎页进入主页按钮");
		return new HomePageObject(driver);
	}
	public void skipBackgroundAD(){
		if(isElementExsit(5,skipBackgroundADLocator)){
			clickEle(skipBackgroundAD,"跳过背景广告按钮");
		}
		
	}
	public boolean verifyIntoHomepageBtnExist(){
		return isElementExsit(intoHomepageBtnLocator);
	}
}
