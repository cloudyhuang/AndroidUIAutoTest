package com.hjs.pages;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import com.hjs.config.CommonAppiumPage;

public class LoginPwdResetPageObject extends CommonAppiumPage{
	@AndroidFindBy(id="editText_oldPwd")
	private AndroidElement oldLoginPwdInput;		//旧登录密码输入框
	@AndroidFindBy(id="button_change_pwd")
	private AndroidElement changePwdBtn;		//验证密码按钮
	@AndroidFindBy(id="editText_newPwd")
	private AndroidElement newPwdInput;		//新密码输入框
	@AndroidFindBy(id="editText_newPwdAgain")
	private AndroidElement newPwdAgainInput;		//新密码再次输入框
	@AndroidFindBy(id="button_change_pwd")
	private AndroidElement commitChangePwdBtn;		//提交新密码按钮
	
	@AndroidFindBy(xpath="//android.widget.Button[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/dlg_msg_singlebtn' and @text='找回密码']")
	private AndroidElement findPwdAtTips;		//找回密码
	
	private By oldLoginPwdInputLoactor=By.id("editText_oldPwd");
	private By newPwdInputLocator=By.id("editText_newPwd");
	public LoginPwdResetPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public PwdSettingPageObject resetLoginPwd(String oldLoginPwd,String newLoginPwd) throws Exception{
		clickEle(oldLoginPwdInput,"旧登录密码输入框");
		sendKeys(oldLoginPwdInput,oldLoginPwd);
		clickEle(changePwdBtn,"验证密码按钮");
		if(!isElementExsit(newPwdInputLocator)){
			throw new Exception("验证密码后未跳转到输入新密码页面");
		}
		clickEle(newPwdInput,"新密码输入框");
		sendKeys(newPwdInput,newLoginPwd);
		clickEle(newPwdAgainInput,"新密码再次输入框");
		sendKeys(newPwdAgainInput,newLoginPwd);
		clickEle(commitChangePwdBtn,"提交新密码按钮");
		return new PwdSettingPageObject(driver);
	}
	public FindPwdVerifyPhonePageObject resetLoginPwdByWrongLoginPwd(String wrongLoginPwd) throws Exception{
		clickEle(oldLoginPwdInput,"旧登录密码输入框");
		sendKeys(oldLoginPwdInput,wrongLoginPwd);
		while(true){
			clickEle(changePwdBtn,"验证密码按钮");
			if(super.isElementExsit(3, findPwdAtTips)){
				clickEle(findPwdAtTips,"找回密码");
				break;
			}
		
		}
		
		
		return new FindPwdVerifyPhonePageObject(driver);
	}
	public boolean verifyInthisPage(){
		return isElementExsit(oldLoginPwdInputLoactor);
	}

}
