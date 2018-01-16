package com.hjs.pages;

import java.util.List;

import com.hjs.config.CommonAppiumPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2018年1月16日 下午4:11:16
* 类说明
*/
public class MassegePageObject extends CommonAppiumPage{
	@AndroidFindBy(id="tv_msg_content")
	private List<AndroidElement> msgContent; 	//消息内容
	
	
	public MassegePageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public boolean verifyInthisPage(){
		return super.isElementExsit(getWaitTime(), msgContent.get(0));
	}
}
