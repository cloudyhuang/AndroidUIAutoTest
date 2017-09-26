package com.hjs.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import com.hjs.config.CommonAppiumPage;

public class SetTradePwdPageObject extends CommonAppiumPage{
	//@AndroidFindBy(id="dlg_msg_rightbtn")
	@AndroidFindBy(id="dlg_msg_3pbtn_type2_1")
	private AndroidElement confirmBtn;		//设置密码确认按钮
	public SetTradePwdPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public void setPwd(String pwd){
		SafeKeyBoard safeKeyBoard=new SafeKeyBoard(driver);
		safeKeyBoard.sendNum(pwd);
		threadsleep(2000);
		safeKeyBoard.sendNum(pwd);
		threadsleep(2000);
		clickEle(confirmBtn,"设置密码确认按钮");
		
	}
}
