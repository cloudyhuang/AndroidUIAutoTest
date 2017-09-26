package com.hjs.pages;

import org.openqa.selenium.By;

import com.hjs.config.CommonAppiumPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2017年9月21日 上午11:52:28
* 转让详情页面
*/
public class TransDetailPageObject extends CommonAppiumPage{
	@AndroidFindBy(id="edit_rate")
	private AndroidElement rateEdit;		//转让收益编辑框
	@AndroidFindBy(id="btn_confirm")
	private AndroidElement applyTransBtn;		//申请转让按钮
	@AndroidFindBy(id="dlg_msg_3pbtn_type2_1")
	private AndroidElement mgsConfirmBtn;		//对话框确认按钮
	
	private By rateEditLocator=By.id("edit_rate");
	public TransDetailPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public TransConfirmPageObject applyTrans(){
		clickEle(applyTransBtn,"申请转让按钮");
		clickEle(mgsConfirmBtn,"对话框确认按钮");
		return new TransConfirmPageObject(driver);
	}
	
	public boolean verifyInthisPage(){
		return isElementExsit(rateEditLocator);
	}

}
