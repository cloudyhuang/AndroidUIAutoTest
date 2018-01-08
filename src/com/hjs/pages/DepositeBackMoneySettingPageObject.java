package com.hjs.pages;

import com.hjs.config.CommonAppiumPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2018年1月5日 下午4:15:08
* 类说明
*/
public class DepositeBackMoneySettingPageObject extends CommonAppiumPage{
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@text,'回款至账户余额')]")
	private AndroidElement backMoneyToBalance;		//回款至余额
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@text,'回款至银行卡')]")
	private AndroidElement backMoneyToBankCard;		//回款至银行卡
	
	public DepositeBackMoneySettingPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public void setToBalance(){
		clickEle(backMoneyToBalance,"回款至余额");
	}
	public void setToBankCard(){
		clickEle(backMoneyToBankCard,"回款至银行卡");
	}

}
