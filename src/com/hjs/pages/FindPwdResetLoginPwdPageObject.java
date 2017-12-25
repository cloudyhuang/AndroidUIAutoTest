package com.hjs.pages;

import org.openqa.selenium.By;

import com.hjs.config.CommonAppiumPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2017年12月20日 下午6:50:36
* 类说明
*/
public class FindPwdResetLoginPwdPageObject extends CommonAppiumPage{
	
	@AndroidFindBy(id = "EditText_newPwd")
	private AndroidElement newLoginPwd1Input; // 新密码输入框1
	@AndroidFindBy(id = "editText_PwdAgin")
	private AndroidElement newLoginPwd2Input; // 新密码输入框2
	@AndroidFindBy(id = "button_confirm")
	private AndroidElement commitPwdBtn; // 提交密码按钮
	
	private By newLoginPwd1InputLocator=By.id("EditText_newPwd");
	
	public FindPwdResetLoginPwdPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public LoginPageObject resetNewLoginPwd(String newPwd){
		sendKeys(newLoginPwd1Input,newPwd);
		sendKeys(newLoginPwd2Input,newPwd);
		clickEle(commitPwdBtn,"提交密码按钮");
		return new LoginPageObject(driver);
	}
	public boolean verifyInthisPage() {
		return isElementExsit(newLoginPwd1InputLocator);
	}

}
