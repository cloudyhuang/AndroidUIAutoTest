package com.hjs.pages;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.hjs.config.CommonAppiumPage;
import com.hjs.publics.Util;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class InvestPageObject extends CommonAppiumPage{
	@AndroidFindBy(id="input")
	private AndroidElement amountInput;		//金额输入框
	@AndroidFindBy(id="product_name")
	private AndroidElement productName;		//产品名称
	@AndroidFindBy(id="investment_amount")
	private AndroidElement maxInvestAmountEle;		//可投金额
	@AndroidFindBy(id="subscribe_submit")
	private AndroidElement submitBtn;		//提交按钮
	@AndroidFindBy(xpath="//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/agreement']/preceding-sibling::android.widget.FrameLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/subscribe_protocol_checkbox']/android.widget.ImageView[1]")
	private AndroidElement agreementCheckBox;		//协议确认框
	@AndroidFindBy(id="dlg_msg_msg")
	private AndroidElement dlgMsg;		//对话框消息内容
	@AndroidFindBy(id="dlg_msg_singlebtn")
	private AndroidElement dlgSingleBtn;		//对话框按钮
	
	private By amountInputLocator=By.id("input");
	public InvestPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public PayPageObject invest(String amount) throws Exception{
		clickEle(amountInput,"金额输入框");
		SafeKeyBoard safeKeyBoard=new SafeKeyBoard(driver);
		if(!safeKeyBoard.verifySafeKeyBoardLocated()){
			throw new Exception("安全键盘未出现");
		}
		safeKeyBoard.sendNum(amount);
		safeKeyBoard.pressFinishBtn();
//		if(safeKeyBoard.verifySafeKeyBoardLocated()){
//			throw new Exception("点击完成后，安全键盘未消失");
//		}
		boolean isCheckBoxSelected=Boolean.parseBoolean(agreementCheckBox.getAttribute("selected"));
		if(!isCheckBoxSelected){
			clickEle(agreementCheckBox,"协议确认框");
		}
		clickEle(submitBtn,"提交按钮");
		return new PayPageObject(driver);
	}
	public void testNotClickAgreementCheckBox(){
		boolean isCheckBoxSelected=Boolean.parseBoolean(agreementCheckBox.getAttribute("selected"));
		if(isCheckBoxSelected){
			super.clickEle(agreementCheckBox, "协议同意框");
			isCheckBoxSelected=Boolean.parseBoolean(agreementCheckBox.getAttribute("selected"));
			Assert.assertFalse(isCheckBoxSelected,"协议同意框当前被勾选，预期应为不勾选");
			boolean isSubmitBtnEnabled=Boolean.parseBoolean(submitBtn.getAttribute("enabled"));
			Assert.assertFalse(isSubmitBtnEnabled);
		}
		else{
			boolean isSubmitBtnEnabled=Boolean.parseBoolean(submitBtn.getAttribute("enabled"));
			Assert.assertFalse(isSubmitBtnEnabled);
			super.clickEle(agreementCheckBox, "协议同意框");
			isCheckBoxSelected=Boolean.parseBoolean(agreementCheckBox.getAttribute("selected"));
			Assert.assertTrue(isCheckBoxSelected,"协议同意框当前未被勾选，预期应为勾选");
			isSubmitBtnEnabled=Boolean.parseBoolean(submitBtn.getAttribute("enabled"));
			Assert.assertTrue(isSubmitBtnEnabled);
		}
	}
	public void testInvestMoney() throws Exception{
		String inputText=super.getEleText(amountInput, "金额输入框");
		String beginAmount=null;
		String increaseAmount=null;
		String regEx1="(\\d*)元起投";
		String regEx2=", (\\d*)元递增";
		Pattern p1=Pattern.compile(regEx1);
		Pattern p2=Pattern.compile(regEx2);
		Matcher m1=p1.matcher(inputText);
		Matcher m2=p2.matcher(inputText);
		if(m1.find()){
			beginAmount=m1.group(1);		//起投金额
		}
		if(m2.find()){
			increaseAmount=m2.group(1);		//递增金额
		}
		double doubleBeginAmount=Util.stringToDouble(beginAmount);
		double doubleIncreaseAmount=Util.stringToDouble(increaseAmount);
		Random ran=new Random();  
		String testBeginAmount=String.valueOf(ran.nextInt((int)doubleBeginAmount));		//起投金额测试值=随机(0-起投金额)
		String testIncreaseAmount=Util.doubleTransToString(Util.stringToDouble(String.valueOf(ran.nextInt((int)doubleIncreaseAmount)))+doubleBeginAmount);
		//递增金额测试值=起投金额+随机(0-递增金额)
		String maxInvestAmount=Util.get2DecimalPointsNumInString(super.getEleText(maxInvestAmountEle, "可投金额"));
		String testMaxInvestAmount=Util.doubleTransToString(Util.stringToDouble(maxInvestAmount)+doubleIncreaseAmount);
		invest(testBeginAmount);
		if(super.isElementExsit(super.getWaitTime(), dlgMsg)){
			String msg=super.getEleText(dlgMsg, "对话框文字");
			Assert.assertEquals(msg, "不可小于起投金额"+beginAmount+"元");
			clickEle(dlgSingleBtn,"对话框按钮");
		}
		else{
			Assert.assertTrue(false,"输入金额:"+testBeginAmount+"起投金额:"+beginAmount+",未弹出投资金额非法提示框");
		}
		//测试递增金额
		clickEle(amountInput,"金额输入框");
		SafeKeyBoard safeKeyBoard=new SafeKeyBoard(driver);
		if(!safeKeyBoard.verifySafeKeyBoardLocated()){
			throw new Exception("安全键盘未出现");
		}
		safeKeyBoard.pressBackSpace(8);		//清除上个输入数据
		safeKeyBoard.pressFinishBtn();
		invest(testIncreaseAmount);		//测试递增金额
		if(super.isElementExsit(super.getWaitTime(), dlgMsg)){
			String msg=super.getEleText(dlgMsg, "对话框文字");
			Assert.assertEquals(msg, "1.不可小于起投金额"+beginAmount+"元"+"\n"+"2.递增金额需为"+increaseAmount+"元及其倍数");
			clickEle(dlgSingleBtn,"对话框按钮");
		}
		else{
			Assert.assertTrue(false,"输入金额:"+testBeginAmount+"起投金额:"+beginAmount+",未弹出投资金额非法提示框");
		}
		//测试最大可投金额
		clickEle(amountInput,"金额输入框");
		safeKeyBoard=new SafeKeyBoard(driver);
		if(!safeKeyBoard.verifySafeKeyBoardLocated()){
			throw new Exception("安全键盘未出现");
		}
		safeKeyBoard.pressBackSpace(8);		//清除上个输入数据
		safeKeyBoard.pressFinishBtn();
		invest(testMaxInvestAmount);		//测试可投金额
		if(super.isElementExsit(super.getWaitTime(), dlgMsg)){
			String msg=super.getEleText(dlgMsg, "对话框文字");
			Assert.assertEquals(msg, "填写金额已超出本项目可投金额,请修改");
			clickEle(dlgSingleBtn,"对话框按钮");
		}
		else{
			Assert.assertTrue(false,"输入金额:"+testBeginAmount+"起投金额:"+beginAmount+",未弹出投资金额非法提示框");
		}
	}
	public boolean verifyInthisPage(){
		return isElementExsit(amountInputLocator);
	}
}
