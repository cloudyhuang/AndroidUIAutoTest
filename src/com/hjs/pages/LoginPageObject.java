package com.hjs.pages;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import com.hjs.config.CommonAppiumPage;
import com.hjs.config.DisConfConfig;

public class LoginPageObject extends CommonAppiumPage{
	
	@AndroidFindBy(id="textView_close")
	private AndroidElement closePageBtn;		//关闭按钮
	@AndroidFindBy(id="phone_view")
	private AndroidElement loginPhoneInput;		//登录手机号输入框
	@AndroidFindBy(id="editText_captcha")
	private AndroidElement captchaInput;		//验证码输入框
	@AndroidFindBy(id="button_nextStep")
	private AndroidElement assertPhoneNumBtn;		//验证手机号
	@AndroidFindBy(id="editText_pwd")
	private AndroidElement pwdInput;		//密码输入框
	@AndroidFindBy(id="button_login")
	private AndroidElement loginBtn;		//登录按钮
	@AndroidFindBy(id="button_switch_account")
	private AndroidElement switchAccountBtn;		//更换账号或注册
	@AndroidFindBy(xpath="//android.widget.Button[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/dlg_msg_3pbtn_type2_2' and @text='切换账号']")
	private AndroidElement swichAccountBtnOnTips;		//提示弹框中切换账号按钮
	@AndroidFindBy(xpath="//android.widget.Button[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/dlg_msg_3pbtn_type2_1' and @text='找回密码']")
	private AndroidElement findPwdBtnOnTips;		//提示弹框中找回密码按钮
	
	
	private By loginedAccountLocator=By.id("textView_account");	//已登录账号Locator
	public LoginPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	
	public GesturePwd login(String password){
		sendKeys(pwdInput, password);
		clickEle(loginBtn,"登录按钮");
		super.threadsleep(3000);
		DisConfConfig disConfConfig=new DisConfConfig();
    	disConfConfig.openVerifyCode();	//打开验证码验证
		return new GesturePwd(driver);
		
	}
	public LoginPageObject wrongPwdLoginAndSwitchAccount(String pwd){
		while(true){
			sendKeys(pwdInput, pwd);
			clickEle(loginBtn,"登录按钮");
			if(super.isElementExsit(2, swichAccountBtnOnTips)){
				break;
			}
			clearKeys(pwdInput, "密码输入框");
		}
		clickEle(swichAccountBtnOnTips,"提示弹框中切换账号按钮");
		DisConfConfig disConfConfig=new DisConfConfig();
    	disConfConfig.openVerifyCode();	//打开验证码验证
		return new LoginPageObject(driver);
	}
	public FindPwdVerifyPhonePageObject wrongPwdLoginAndFindPwd(String wrongPwd){
		while(true){
			sendKeys(pwdInput, wrongPwd);
			clickEle(loginBtn,"登录按钮");
			if(super.isElementExsit(2, swichAccountBtnOnTips)){
				break;
			}
			clearKeys(pwdInput, "密码输入框");
		}
		clickEle(findPwdBtnOnTips,"提示弹框中找回密码按钮");
		DisConfConfig disConfConfig=new DisConfConfig();
    	disConfConfig.openVerifyCode();	//打开验证码验证
		return new FindPwdVerifyPhonePageObject(driver);
	}
	public CommonAppiumPage verifyPhoneNum(String phoneNumber){
		clickEle(loginPhoneInput,"登录手机号输入框");
		SafeKeyBoard safeKeyBoard=new SafeKeyBoard(driver);
		safeKeyBoard.sendNum(phoneNumber);
		safeKeyBoard.pressFinishBtn();
		if(super.isElementExsit(5,captchaInput)){
			DisConfConfig disConfConfig=new DisConfConfig();
	    	disConfConfig.closeVerifyCode();	//关闭验证码验证
			clickEle(captchaInput,"验证码输入框");
			super.sendKeys(captchaInput, "1234");
		}
		super.threadsleep(2000);//使之disconf关闭验证码生效等待
		clickEle(assertPhoneNumBtn,"验证手机号按钮");
		if(this.verifyInthisPage()){
			return new LoginPageObject(driver);
		}
		else {
			return new SignUpPageObject(driver);
		}
	}
	public CommonAppiumPage switchAccount(String phoneNumber){
		clickEle(switchAccountBtn,"切换账号或注册按钮");
		clickEle(loginPhoneInput,"登录手机号输入框");
		SafeKeyBoard safeKeyBoard=new SafeKeyBoard(driver);
		safeKeyBoard.sendNum(phoneNumber);
		safeKeyBoard.pressFinishBtn();
		if(super.isElementExsit(5,captchaInput)){
			DisConfConfig disConfConfig=new DisConfConfig();
	    	disConfConfig.closeVerifyCode();	//关闭验证码验证
			clickEle(captchaInput,"验证码输入框");
			super.sendKeys(captchaInput, "1234");
		}
		super.threadsleep(2000);//使之disconf关闭验证码生效等待
		clickEle(assertPhoneNumBtn,"验证手机号按钮");
		//driver.findElement(By.id("phone_view")).click();
		if(this.verifyInthisPage()){
			return new LoginPageObject(driver);
		}
		else {
			return new SignUpPageObject(driver);
		}
	}
	public void closePage(){
		clickEle(closePageBtn,"关闭按钮");
	}
	public boolean verifyInFirstLoginPage(){
		return isElementExsit(super.getWaitTime(),loginPhoneInput);
	}
	public boolean verifyInthisPage(){
		return isElementExsit(loginedAccountLocator);
	}
	
}
