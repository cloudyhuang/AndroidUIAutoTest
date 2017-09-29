package com.hjs.pages;

import org.openqa.selenium.By;

import com.hjs.config.CommonAppiumPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2017年9月28日 上午11:34:47
* 类说明
*/
public class FundPurchasePageObject extends CommonAppiumPage{
	
	@AndroidFindBy(id="et_purchase_sum")
	private AndroidElement purchaseAmountInput;		//申购金额输入框
	@AndroidFindBy(id="btn_fund_confirm_purchase")
	private AndroidElement confirmPurchaseBtn;		//申购确认按钮
	
	private By purchaseAmountInputLocator=By.id("et_purchase_sum");
	public FundPurchasePageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public void purchaseFund(String amount,String pwd) throws Exception{
		clickEle(purchaseAmountInput,"申购金额输入框");
		SafeKeyBoard safeKeyBoard=new SafeKeyBoard(driver);
		if(!safeKeyBoard.verifySafeKeyBoardLocated()){
			throw new Exception("输入金额安全键盘未出现");
		}
		safeKeyBoard.sendNum(amount);
		safeKeyBoard.pressFinishBtn();
		clickEle(confirmPurchaseBtn,"申购确认按钮");
		if(!safeKeyBoard.verifySafeKeyBoardLocated()){
			throw new Exception("输入密码安全键盘未出现");
		}
		safeKeyBoard.sendNum(pwd);
		safeKeyBoard.pressFinishBtn();
	}
	public boolean verifyInthisPage(){
		return isElementExsit(purchaseAmountInputLocator);
	}

}
