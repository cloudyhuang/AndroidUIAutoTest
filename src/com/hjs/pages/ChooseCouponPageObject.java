package com.hjs.pages;

import java.util.List;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import com.hjs.config.CommonAppiumPage;

public class ChooseCouponPageObject extends CommonAppiumPage{
	
	@AndroidFindBy(id="icon_isChoosed")
	private AndroidElement isChoosedIcon;		//已选择图标
	@AndroidFindBy(id="imageView_back")
	//@AndroidFindBy(id="layout_back")
	private AndroidElement backBtn;		//返回按钮
	
	private By isChoosedIconLocator=By.id("icon_isChoosed");
	public ChooseCouponPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public PayPageObject chooseCoupon(String couponName) throws Exception{
		if(couponName.equals("")){
			if(isElementExsit(3,isChoosedIconLocator)){
				clickEle(isChoosedIcon,"已选择图标");
				//clickEle(backBtn,"返回按钮");
				super.backKeyEvent();  //返回
			}
			else {
				super.backKeyEvent();  //返回
				//clickEle(backBtn,"返回按钮");
			}
		}
		else{
			if(isElementExsit(3,isChoosedIconLocator)){
				clickEle(isChoosedIcon,"已选择图标");
			}
			List<AndroidElement> couponEle=driver.findElements(By.xpath("//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/tv_coupon_name' and @text='"+couponName+"']"));
			if(couponEle.size()<=0){
				throw new Exception("未找到名称为"+couponName+"的优惠券");
			}
			clickEle(couponEle.get(0),"优惠券");
		}
		return new PayPageObject(driver);
	}
}
