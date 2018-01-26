package com.hjs.pages;

import java.util.List;

import com.hjs.config.CommonAppiumPage;
import com.hjs.publics.Util;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2018年1月24日 上午11:05:58
* 类说明
*/
public class IncomeAndExpensesDetailPageObject extends CommonAppiumPage{
	@AndroidFindBy(id="tv_balance_exchange_type")
	private List<AndroidElement> balanceExchangeType;		//收支费用类型
	@AndroidFindBy(id="tv_balance_exchange_sum")
	private List<AndroidElement> balanceExchangeSum;		//费用数值
	@AndroidFindBy(id="tv_balance_sum")
	private List<AndroidElement> balanceSum;		//当前余额
	
	
	public IncomeAndExpensesDetailPageObject(AndroidDriver<AndroidElement> driver) { 
		super(driver);
	}
	public BalanceExchange getLastBalanceExchange(){
		BalanceExchange balanceExchange=new BalanceExchange();
		balanceExchange.setType(super.getEleText(balanceExchangeType.get(0), "最近一笔收支费用类型"));
		balanceExchange.setValue(Util.get2DecimalPointsNumInString(super.getEleText(balanceExchangeSum.get(0), "最近一笔费用金额")));
		balanceExchange.setBalanceSum(Util.get2DecimalPointsNumInString(super.getEleText(balanceSum.get(0), "当前余额")));
		return balanceExchange;
	}
	public TradeDetailPageObject gotoLastTradeDetailPage(){
		clickEle(balanceExchangeType.get(0),"最近一笔收支费用类型");
		return new TradeDetailPageObject(driver);
	}
	public class BalanceExchange{
		String type;
		String value;
		String balanceSum;
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getBalanceSum() {
			return balanceSum;
		}
		public void setBalanceSum(String balanceSum) {
			this.balanceSum = balanceSum;
		}
	}
	public boolean verifyInthisPage(){
		return isElementExsit(super.getWaitTime(),balanceExchangeType.get(0));
	}
}
