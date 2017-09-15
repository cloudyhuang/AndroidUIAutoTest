package com.hjs.pages;

import org.openqa.selenium.By;

import com.hjs.config.CommonAppiumPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2017年9月15日 上午10:13:24
* 类说明
*/
public class SetSafeBankCardPageObject extends CommonAppiumPage{
	@AndroidFindBy(id="btn_confirm_select")
	private AndroidElement nextBtn;		//下一步按钮
	
	private By nextBtnLocator=By.id("btn_confirm_select");
	public SetSafeBankCardPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	public MyBankCardPageObject unbundSafeCardSetOtherSafeCard(String pwd){
		clickEle(nextBtn,"下一步按钮");
		SafeKeyBoard safeKeyBoard=new SafeKeyBoard(driver);
		safeKeyBoard.sendNum(pwd);
		return new MyBankCardPageObject(driver);
	}
	public boolean verifyInthisPage(){
		return isElementExsit(nextBtnLocator);
	}
}
