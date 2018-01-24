package com.hjs.pages;

import java.util.List;

import org.openqa.selenium.By;

import com.hjs.config.CommonAppiumPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2018年1月22日 上午11:49:59
* 类说明
*/
public class MineCouponPageObject extends CommonAppiumPage{
	@AndroidFindBy(id="btn_coupon_invest")
	private AndroidElement couponInvestBtn;		//使用优惠券投资按钮
	@AndroidFindBy(id="tv_coupon_name")
	private List<AndroidElement> couponName;		//优惠券名称
	@AndroidFindBy(id="switch_coupon")
	private AndroidElement switchCouponBtn;		//切换优惠券“可用”/“历史”
	
	private By switchCouponBtnLocator=By.id("switch_coupon");
	public MineCouponPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public FinancialPageObject investByCoupon(){
		clickEle(couponInvestBtn,"使用优惠券投资按钮");
		return new FinancialPageObject(driver);
	}
	public boolean verifyInthisPage(){
		return isElementExsit(switchCouponBtnLocator);
	}
}
