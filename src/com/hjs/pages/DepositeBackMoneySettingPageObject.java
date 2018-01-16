package com.hjs.pages;

import org.junit.Assert;
import org.openqa.selenium.By;

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
	@AndroidFindBy(id="dlg_msg_msg")
	private AndroidElement dlgMsg;		//提示
	@AndroidFindBy(id="dlg_msg_3pbtn_type2_1")
	private AndroidElement rightBtn;		//提示右侧按钮
	
	private By backMoneyToBankCardLocator=By.xpath("//android.widget.TextView[contains(@text,'回款至银行卡')]");
	
	public DepositeBackMoneySettingPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public void setToBalance(){
		clickEle(backMoneyToBalance,"回款至余额");
		if(super.isElementExsit(getWaitTime(), dlgMsg)){
			Assert.assertEquals(super.getEleText(dlgMsg, "提示"), "当前正在回款中的项目，回款去向规则将不受此次变更影响");
			clickEle(rightBtn,"提示右侧确认按钮");
		}
		
	}
	public void setToBankCard(){
		clickEle(backMoneyToBankCard,"回款至银行卡");
		if(super.isElementExsit(super.getWaitTime(), dlgMsg)){
			Assert.assertEquals(super.getEleText(dlgMsg, "提示"), "当前正在回款中的项目，回款去向规则将不受此次变更影响");
			clickEle(rightBtn,"提示右侧确认按钮");
		}
	}
	public boolean verifyInthisPage(){
		return isElementExsit(backMoneyToBankCardLocator);
	}

}
