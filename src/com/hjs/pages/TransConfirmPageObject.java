package com.hjs.pages;

import org.openqa.selenium.By;

import com.hjs.config.CommonAppiumPage;
import com.hjs.testDate.TransProductInfo;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2017年9月22日 下午5:39:47
* 类说明
*/
public class TransConfirmPageObject extends CommonAppiumPage{
	@AndroidFindBy(id="tv_productName")
	private AndroidElement productName;		//产品名称
	@AndroidFindBy(id="tv_AnnualYieldRate")
	private AndroidElement yieldRate;		//年化收益
	@AndroidFindBy(id="tv_return")
	private AndroidElement redeemTo;		//回款去向
	@AndroidFindBy(id="tv_fwValue")
	private AndroidElement serviceCharge;		//服务费
	@AndroidFindBy(id="tv_result")
	private AndroidElement amountToAccount;		//实际到账金额
	
	
	@AndroidFindBy(id="btn_confirm")
	private AndroidElement confirmBtn;		//确认转让按钮
	
	
	private By confirmBtnLocator=By.id("btn_confirm");
	public TransConfirmPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public void setTransProductInfo(){
		TransProductInfo.setProductName(super.getEleText(productName, "产品名称"));
		TransProductInfo.setYearProfit(super.getEleText(yieldRate, "年化收益"));
		TransProductInfo.setRedeemTo(super.getEleText(redeemTo, "回款去向"));
		TransProductInfo.setServiceCharge(super.getEleText(serviceCharge, "服务费"));
		TransProductInfo.setAmountToAccount(super.getEleText(amountToAccount, "实际到账金额")+"元");
	}
	public TransResultPageObject confirmTrans(String pwd){
		clickEle(confirmBtn,"确认转让按钮");
		SafeKeyBoard safeKeyBoard=new SafeKeyBoard(driver);
		safeKeyBoard.sendNum(pwd);
		return new TransResultPageObject(driver);
	}
	public boolean verifyInthisPage(){
		return isElementExsit(confirmBtnLocator);
	}
}
