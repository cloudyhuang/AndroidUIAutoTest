package com.hjs.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import com.hjs.config.CommonAppiumPage;

public class SetTradePwdPageObject extends CommonAppiumPage{

	public SetTradePwdPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public void setPwd(String pwd){
		SafeKeyBoard safeKeyBoard=new SafeKeyBoard(driver);
		safeKeyBoard.sendNum(pwd);
		threadsleep(2000);
		safeKeyBoard.sendNum(pwd);
		threadsleep(2000);
	}
}
