package com.hjs.pages;

import org.openqa.selenium.By;

import com.hjs.config.CommonAppiumPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2017年9月27日 下午4:07:03
* 类说明
*/
public class FundAccountCreatPageObject extends CommonAppiumPage{
	
	@AndroidFindBy(id="account_tv_user_name")
	private AndroidElement accountUserName;		//开户人姓名
	@AndroidFindBy(id="account_tv_user_card")
	private AndroidElement accountUserID;		//开户人身份证
	@AndroidFindBy(id="account_tv_bank_name")
	private AndroidElement accountBankName;		//开户人银行卡名称
	@AndroidFindBy(id="account_tv_bank_no")
	private AndroidElement accountBankNo;		//开户人银行卡卡号
	@AndroidFindBy(id="btn_prepare_create")
	private AndroidElement confirmBtn;		//确认开户按钮
	
	private By accountUserNameLocator=By.id("account_tv_user_name");
	public FundAccountCreatPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public void creatFundAccount(){
		clickEle(confirmBtn,"确认开户按钮");
	}
	public boolean verifyInthisPage(){
		return isElementExsit(accountUserNameLocator);
	}
}
