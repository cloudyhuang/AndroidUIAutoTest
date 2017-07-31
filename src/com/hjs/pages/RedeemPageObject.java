package com.hjs.pages;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import com.hjs.config.CommonAppiumPage;

public class RedeemPageObject extends CommonAppiumPage{
	@AndroidFindBy(id="ransom_know")
	private AndroidElement guideTips;		//引导知道了按钮
	@AndroidFindBy(id="ransom_return_to_name")
	private AndroidElement ransomReturnToName;		//赎回到哪名称
	@AndroidFindBy(id="ransom_input")
	private AndroidElement ransomInput;		//赎回金额输入框
	@AndroidFindBy(id="ransom")
	private AndroidElement ransomConfirmBtn;		//确认赎回按钮
	
	private By ransomInputLocator=By.id("ransom_input");
	private By guideTipsLocator=By.id("ransom_know");
	
	public RedeemPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public RedeemResultPageObject redeemToBalance(String amount,String tradePwd) throws Exception{
		if(!ransomReturnToName.getText().equals("账户余额")){
			clickEle(ransomReturnToName,"赎回到哪名称");	
			super.clickNativeEle(ransomReturnToName, 1);
		}
		clickEle(ransomInput,"赎回金额输入框");
		SafeKeyBoard safeKeyBoard=new SafeKeyBoard(driver);
		if(!safeKeyBoard.verifySafeKeyBoardLocated()){
			throw new Exception("安全键盘未出现");
		}
		safeKeyBoard.sendNum(amount);
		safeKeyBoard.pressFinishBtn();
		if(safeKeyBoard.verifySafeKeyBoardLocated()){
			throw new Exception("点击完成后，安全键盘未消失");
		}
		clickEle(ransomConfirmBtn,"确认赎回按钮");
		if(!safeKeyBoard.verifySafeKeyBoardLocated()){
			throw new Exception("安全键盘未出现");
		}
		safeKeyBoard.sendNum(tradePwd);
		return new RedeemResultPageObject(driver);
		
	}
	public void skipGuide(){
		if(super.isElementExsit(3,guideTipsLocator)){
			clickEle(guideTips,"引导知道了按钮");
		}
	}
	public boolean verifyInthisPage(){
		this.skipGuide();
		return isElementExsit(ransomInputLocator);
	}
}
