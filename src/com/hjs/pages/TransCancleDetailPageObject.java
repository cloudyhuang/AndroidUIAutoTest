package com.hjs.pages;

import org.openqa.selenium.By;

import com.hjs.config.CommonAppiumPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2017年9月25日 上午11:36:48
* 类说明
*/
public class TransCancleDetailPageObject extends CommonAppiumPage{
	@AndroidFindBy(id="btn_confirm")
	private AndroidElement cancleTransBtn;		//申请撤销转让按钮
	
	private By cancleTransBtnLocator=By.id("btn_confirm");
	public TransCancleDetailPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public TransCancelResultPageObject cancelTrans(String pwd){
		clickEle(cancleTransBtn,"撤销转让按钮");
		SafeKeyBoard safeKeyBoard=new SafeKeyBoard(driver);
		safeKeyBoard.sendNum(pwd);
		return new TransCancelResultPageObject(driver);
	}
	public boolean verifyInthisPage(){
		return isElementExsit(cancleTransBtnLocator);
	}
}
