package com.hjs.pages;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import com.hjs.config.CommonAppiumPage;

public class WithdrawCashPageObject extends CommonAppiumPage{

	
	@AndroidFindBy(id="edit_withdraw_money")
	private AndroidElement withdrawMoneyInput;		//提现输入框
	@AndroidFindBy(id="tv_withdraw_max")
	private AndroidElement withdrawMaxTV;		//最大提现额
	@AndroidFindBy(id="btn_confirm_withdraw")
	private AndroidElement confirmWithdrawBtn;		//确认提现按钮
	
	private By withdrawMoneyInputLocator=By.id("edit_withdraw_money");
	public WithdrawCashPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public WithdrawCashResultPageObject withdrawCash(String amount,String tradePwd) throws Exception{
		clickEle(withdrawMoneyInput,"提现输入框");
		SafeKeyBoard safeKeyBoard=new SafeKeyBoard(driver);
		if(!safeKeyBoard.verifySafeKeyBoardLocated()){
			throw new Exception("安全键盘未出现");
		}
		safeKeyBoard.sendNum(amount);
		safeKeyBoard.pressFinishBtn();
		if(safeKeyBoard.verifySafeKeyBoardLocated()){
			throw new Exception("点击完成后，安全键盘未消失");
		}
		clickEle(confirmWithdrawBtn,"确认提现按钮");
		if(!safeKeyBoard.verifySafeKeyBoardLocated()){
			throw new Exception("输入交易密码安全键盘未出现");
		}
		safeKeyBoard.sendNum(tradePwd);
		return new WithdrawCashResultPageObject(driver);
	}
	public boolean verifyInthisPage(){
		return isElementExsit(withdrawMoneyInputLocator);
	}

}
