package com.hjs.pages;


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

import com.hjs.config.CommonAppiumPage;
import com.hjs.publics.AppiumBaseMethod;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.TimeOutDuration;

public class HomePageObject extends CommonAppiumPage{
	@AndroidFindBy(id="btn_new_user_guide")
	private AndroidElement loginEntrance;		//首页-登录入口
	@AndroidFindBy(id="button_person")
	private AndroidElement personEntrance;		//首页-我的入口
	@AndroidFindBy(id="button_buy")
	private AndroidElement financialEntrance;		//首页-理财入口
	@AndroidFindBy(id="button_discover")
	private AndroidElement discoverEntrance;		//首页-发现入口
	@AndroidFindBy(id="button_home")
	private AndroidElement homeEntrance;		//首页-首页入口
	
	
	
	
	//private By noRemindUpdateLocator=By.id("btn_no_remind");		//下次更新locator
	private By noRemindUpdateLocator=By.id("btn_remind_next");		//下次更新locator
	private By personEnraceLocator=By.id("button_person");		//首页-我的入口locator
	private By adCloseBtnLocator=By.id("icv_advertisement_close");		//我的入口广告关闭按钮locator
	public HomePageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
		this.noRemindUpdate();
		this.closeAD();
	}
	public LoginPageObject clickLoginEntrance(){
		clickEle(loginEntrance,"首页-登录入口");
		return new LoginPageObject(driver);
	}
	public DiscoverPageObject clickdiscoverEntrance(){
		clickEle(discoverEntrance,"首页-发现入口");
		return new DiscoverPageObject(driver);
	}
	public CommonAppiumPage enterPersonEntrace(){
		this.noRemindUpdate();
		clickEle(personEntrance,"首页-我的入口");
		this.closeAD();
		if(this.verifyIsInHomePage()){
			return new MinePageObject(driver);
		}
		else return new LoginPageObject(driver);
	}
	public void noRemindUpdate(){
		if (isElementExsit(2,noRemindUpdateLocator)){
			driver.findElement(noRemindUpdateLocator).click();
		}
	}
	public void closeAD(){
		if (isElementExsit(2,adCloseBtnLocator)){
			driver.findElement(adCloseBtnLocator).click();
		}
	}
	public FinancialPageObject enterFinancialPage(){
		this.noRemindUpdate();
		clickEle(financialEntrance,"首页-理财入口");
		this.closeAD();
		return new FinancialPageObject(driver);
	}
	public FinancialPageObject reloadFinancialPage(){
		clickEle(homeEntrance,"首页-首页入口");
		clickEle(financialEntrance,"首页-理财入口");
		return new FinancialPageObject(driver);
	}
	public boolean verifyIsInHomePage(){
		this.closeAD();
		return isElementExsit(5,personEnraceLocator);
	}
	 public void backToHomePage(int...gesturePwds){
		//driver.startActivity("com.evergrande.eif.android.hengjiaosuo", ".HDSplashActivity");
		 //driver.startActivity("com.evergrande.eif.android.hengjiaosuo", "com.evergrande.eif.userInterface.activity.modules.homePage.HDHomePageShowActivity");
		//try {driver.runAppInBackground(1);}catch(Exception e){}
		driver.closeApp();
		//super.threadsleep(2000);
		driver.launchApp();
	    new WelcomePageObject(driver).skipBackgroundAD();
	    GesturePwd gesturePwd=new GesturePwd(driver);
	    if(gesturePwds.length>0&&gesturePwd.verifyInthisPage()){
	    gesturePwd.inputGesturePwd(gesturePwds);
	    }
	    }
}
