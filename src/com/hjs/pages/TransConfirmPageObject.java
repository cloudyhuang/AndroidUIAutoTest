package com.hjs.pages;

import org.openqa.selenium.By;

import com.hjs.config.CommonAppiumPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2017年9月22日 下午5:39:47
* 类说明
*/
public class TransConfirmPageObject extends CommonAppiumPage{
	
	@AndroidFindBy(id="btn_confirm")
	private AndroidElement confirmBtn;		//确认转让按钮
	
	private By confirmBtnLocator=By.id("btn_confirm");
	public TransConfirmPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public TransResultPageObject confirmTrans(String pwd){
		clickEle(confirmBtn,"确认转让按钮");
		SafeKeyBoard safeKeyBoard=new SafeKeyBoard(driver);
		safeKeyBoard.sendNum(pwd);
		return new TransResultPageObject(driver);
	}
	public boolean verifyInthisPage(){
		return isElementExsit(confirmBtnLocator);
	}
}
