package com.hjs.pages;

import java.util.List;

import org.openqa.selenium.By;

import com.hjs.config.CommonAppiumPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2018年4月9日 下午3:12:10
* 解绑银行卡操作指南
*/
public class UnbundBankCardInstructionsPageObject extends CommonAppiumPage{
	
	@AndroidFindBy(id="btn_confirm_select")
	private AndroidElement nextStepBtn;		//下一步按钮
	@AndroidFindBy(id="tv_guide_title")
	private List<AndroidElement> guideTitle;		//操作指南提示
	
	public UnbundBankCardInstructionsPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public SetSafeBankCardPageObject unbundSafeCardGotoNextPage(){
		clickEle(nextStepBtn,"下一步按钮");
		return new SetSafeBankCardPageObject(driver);
		
	}
	public UnbundBankCardUploadIDCardPageObject unbundNormalCardGotoNextPage(){
		clickEle(nextStepBtn,"下一步按钮");
		return new UnbundBankCardUploadIDCardPageObject(driver);
		
	}
	public boolean verifyInthisPage(){
		return isElementExsit(super.getWaitTime(),guideTitle.get(0));
	}
}
