package com.hjs.pages;

import org.openqa.selenium.By;

import com.hjs.config.CommonAppiumPage;
import com.hjs.db.DBOperation;
import com.hjs.publics.Util;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
 * @author huangxiao
 * @version 创建时间：2017年12月19日 下午6:40:13 类说明
 */
public class FindPwdVerifyRealNamePageObject extends CommonAppiumPage {

	@AndroidFindBy(id = "textView_account")
	private AndroidElement accountInfo; // 账号信息
	@AndroidFindBy(id = "editText_realName")
	private AndroidElement realNameInput; // 实名输入框
	@AndroidFindBy(id = "editText_number")
	private AndroidElement idInput; // 身份证输入框
	@AndroidFindBy(id = "button_commit")
	private AndroidElement verifyRealNameBtn; // 身份验证按钮

	private By realNameInputLocator = By.id("editText_realName");

	public FindPwdVerifyRealNamePageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}

	public FindPwdResetLoginPwdPageObject verifyRealName() throws Exception {
		String phoneNum = Util.getNumInString(super.getEleText(accountInfo, "账号信息"));
		DBOperation dBOperation=new DBOperation();
		String realName=dBOperation.getRealNameByPhone(phoneNum);
		String idNo=dBOperation.getIdNoByPhone(phoneNum);
		super.sendKeys(realNameInput, realName);
		super.sendKeys(idInput, idNo);
		clickEle(verifyRealNameBtn,"身份验证按钮");
		return new FindPwdResetLoginPwdPageObject(driver);
	}

	public boolean verifyInthisPage() {
		return isElementExsit(realNameInputLocator);
	}

}
