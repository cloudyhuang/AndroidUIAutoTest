package com.hjs.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.testng.Assert;

import com.hjs.config.CommonAppiumPage;
import com.hjs.publics.Util;
import com.hjs.testDate.GroupBuyInfo;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2018年3月8日 下午4:33:34
* 类说明
*/
public class MyGroupBuyPageObject extends CommonAppiumPage{
	@AndroidFindBy(id="tv_group_launcher")
	private List<AndroidElement> groupLauncher;		//团购发起人
	@AndroidFindBy(id="switch_group_list")
	private AndroidElement switchGroup;		//选择进行中/已结束团购
	@AndroidFindBy(id="tv_group_reward_desc")
	private List<AndroidElement> groupReward;		//利率
	@AndroidFindBy(id="tv_my_invest_sum")
	private List<AndroidElement> investSum;		//投资金额
	@AndroidFindBy(id="tv_user_joined_time")
	private List<AndroidElement> joinedTime;		//参团时间
	
	
	
	private By switchGroupLocator=By.id("switch_group_list");
	public MyGroupBuyPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public boolean assertMyGroupBuyInfo(){
		String rate=super.getEleText(groupReward.get(0), "利率");
		rate=Util.get2DecimalPointsNumInString(rate);
		String expectRate=GroupBuyInfo.getCurrentReward();
		Assert.assertEquals(expectRate, rate,"团购利率与预期不符");
		String investMoney=super.getEleText(investSum.get(0), "我的投资");
		double doubleActualInvestMoney=Util.stringToDouble(investMoney);
		double expectInvestMoney=GroupBuyInfo.getGroupBuyedAmountm();
		Assert.assertEquals(expectInvestMoney, doubleActualInvestMoney,"团购金额与预期不符");
		String expectJoinTime=GroupBuyInfo.getInvestDate();
		String joinTime=super.getEleText(joinedTime.get(0), "参团时间");
		Assert.assertEquals(expectJoinTime, joinTime,"参团时间与预期不符");
		return true;
	}
	public boolean assertEndGroupBuyInfo(){
		Point switchGroupPoint=super.getNativeEleCenterPoint(switchGroup);
		int startX = switchGroup.getLocation().getX();
        int width = switchGroup.getSize().getWidth();
        switchGroupPoint.x=startX+width*3/4;
		super.clickPoint(switchGroupPoint.getX(), switchGroupPoint.getY());
		Assert.assertTrue(isElementExsit(10,groupLauncher.get(0)),"点击已结束团购，未加载出内容");
		return true;
	}
	public boolean verifyInthisPage(){
		return isElementExsit(switchGroupLocator);
	}
}
