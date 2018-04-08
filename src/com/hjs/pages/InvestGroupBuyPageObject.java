package com.hjs.pages;

import org.openqa.selenium.By;

import com.hjs.config.CommonAppiumPage;
import com.hjs.publics.Util;
import com.hjs.testDate.GroupBuyInfo;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2017年9月7日 上午11:46:39
* 类说明
*/
public class InvestGroupBuyPageObject extends CommonAppiumPage{
	@AndroidFindBy(id="input")
	private AndroidElement amountInput;		//金额输入框
	@AndroidFindBy(id="subscribe_submit")
	private AndroidElement submitBtn;		//提交按钮
	@AndroidFindBy(id="group_reward")
	private AndroidElement groupReward;		//当前奖励
	@AndroidFindBy(id="group_rate")
	private AndroidElement lvupReward;		//升级奖励
	
	@AndroidFindBy(xpath="//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/agreement']/preceding-sibling::android.widget.FrameLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/subscribe_protocol_checkbox']/android.widget.ImageView[1]")
	private AndroidElement agreementCheckBox;		//协议确认框
	private By amountInputLocator=By.id("input");
	public InvestGroupBuyPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public PayPageObject InvestGroupBuy(String amount) throws Exception{
		String currentReward=super.getEleText(groupReward, "当前奖励");
		currentReward=Util.get2DecimalPointsNumInString(currentReward);
		String lvlupReward=super.getEleText(lvupReward, "升级奖励");
		lvlupReward=Util.get2DecimalPointsNumInString(lvlupReward);
		GroupBuyInfo.setCurrentReward(currentReward);
    	GroupBuyInfo.setLvupReward(lvlupReward);
		clickEle(amountInput,"金额输入框");
		SafeKeyBoard safeKeyBoard=new SafeKeyBoard(driver);
		if(!safeKeyBoard.verifySafeKeyBoardLocated()){
			throw new Exception("安全键盘未出现");
		}
		safeKeyBoard.sendNum(amount);
		safeKeyBoard.pressFinishBtn();
		clickEle(agreementCheckBox,"协议确认框");
		clickEle(submitBtn,"提交按钮");
		return new PayPageObject(driver);
	}
	public boolean verifyInthisPage(){
		return isElementExsit(amountInputLocator);
	}
}
