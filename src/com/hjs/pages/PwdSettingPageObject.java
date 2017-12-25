package com.hjs.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import com.hjs.config.CommonAppiumPage;

public class PwdSettingPageObject extends CommonAppiumPage{
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@text,'重设登录密码')]")
	private AndroidElement resetLoginPwd;		//重设登录密码跳转按钮
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@text,'重设交易密码')]")
	private AndroidElement resetTradePwd;		//重设交易密码跳转按钮
	@AndroidFindBy(id="switch_button")
	private AndroidElement gesture_switch;		//手势密码开关
	@AndroidFindBy(id="et_login_password")
	private AndroidElement loginPwdInput;		//验证登录密码输入框
	@AndroidFindBy(id="btn_login_password_confirm")
	private AndroidElement loginPwdConfirmBtn;		//登录密码确认按钮
	@AndroidFindBy(id="dlg_msg_msg")
	private AndroidElement dlgMsgMsg;		//弹出框信息内容
	@AndroidFindBy(id="dlg_msg_singlebtn")
	private AndroidElement tipsConfirmBtn;		//弹出框确认按钮
	
	
	
	private By resetTradePwdLocator=By.xpath("//android.widget.TextView[contains(@text,'重设交易密码')]");
	public PwdSettingPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public TradePwdResetPageObject gotoTradePwdPage(){
		clickEle(resetTradePwd,"重设登录密码跳转按钮");
		return new TradePwdResetPageObject(driver);
	}
	public LoginPwdResetPageObject gotoLoginPwdPage(){
		clickEle(resetLoginPwd,"重设登录密码跳转按钮");
		return new LoginPwdResetPageObject(driver);
	}
	public void closeGestureSwitch(String pwd) throws Exception{
		String switchStatus=gesture_switch.getAttribute("checked");
		if(switchStatus.equals("true")){
			clickEle(gesture_switch,"手势密码开关");
			super.sendKeys(loginPwdInput, pwd);
			clickEle(loginPwdConfirmBtn,"登录密码确认按钮");
			Assert.assertEquals(super.getEleText(dlgMsgMsg, "提示框信息内容"), "您的账号已关闭手势密码服务，如需开启可重新设置");
			clickEle(tipsConfirmBtn,"弹出框确认按钮");
		}
		else throw new Exception("当前手密开关为关");
	}
	public boolean verifyInthisPage(){
		return isElementExsit(resetTradePwdLocator);
	}

}
