package com.hjs.pages;

import org.openqa.selenium.By;

import com.hjs.config.CommonAppiumPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2017年9月11日 下午2:22:03
* 类说明
*/
public class GroupedBuyDetailPageObject extends CommonAppiumPage{
	@AndroidFindBy(id="share_code")
	private AndroidElement shareCodeTV;		//邀请码
	@AndroidFindBy(id="btn_reInvest")
	private AndroidElement reInvestBtn;		//点击投资按钮
	@AndroidFindBy(id="btn_invite")
	private AndroidElement inviteBtn;		//邀请好友按钮
	@AndroidFindBy(id="member_more")
	private AndroidElement memberMoreBtn;		//更多团员按钮
	@AndroidFindBy(id="member_count")
	private AndroidElement memberCount;		//团员数量
	@AndroidFindBy(xpath="//android.widget.TextView[@text='已团']/following-sibling::android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/tv_left_bottom']")
	private AndroidElement buyedAmount;		//已购买金额
	@AndroidFindBy(xpath="//android.widget.TextView[@text='当前奖励']/following-sibling::android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/tv_right_bottom']")
	private AndroidElement currentReward;		//当前奖励
	@AndroidFindBy(xpath="//android.widget.TextView[@text='升级差额']/following-sibling::android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/tv_left_bottom']")
	private AndroidElement lvupBalance;		//升级差额
	@AndroidFindBy(xpath="//android.widget.TextView[@text='升级奖励']/following-sibling::android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/tv_right_bottom']")
	private AndroidElement lvupReward;		//升级奖励

	private By shareCodeTVLocator=By.id("share_code");
	public GroupedBuyDetailPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public InvestGroupBuyPageObject clickInvestGroupBuyBtn(){
		clickEle(reInvestBtn,"点击投资按钮");
		return new InvestGroupBuyPageObject(driver);
	}
	public boolean verifyInthisPage(){
		return isElementExsit(shareCodeTVLocator);
	}
}
