package com.hjs.testDate;
/**
* @author huangxiao
* @version 创建时间：2018年2月5日 下午4:30:26
* 类说明
*/
public class TransProductInfo {
	private static String productName;		//产品名称
	private static String investMoney;		//投资金额
	private static String yearProfit;		//年化利率
	private static String redeemTo;		//回款去向
	private static String serviceCharge;	//服务费
	private static String amountToAccount;		//实际到账金额
	public static String getProductName() {
		return productName;
	}
	public static void setProductName(String productName) {
		TransProductInfo.productName = productName;
	}
	public static String getInvestMoney() {
		return investMoney;
	}
	public static void setInvestMoney(String investMoney) {
		TransProductInfo.investMoney = investMoney;
	}
	public static String getYearProfit() {
		return yearProfit;
	}
	public static void setYearProfit(String yearProfit) {
		TransProductInfo.yearProfit = yearProfit;
	}
	public static String getRedeemTo() {
		return redeemTo;
	}
	public static void setRedeemTo(String redeemTo) {
		TransProductInfo.redeemTo = redeemTo;
	}
	public static String getServiceCharge() {
		return serviceCharge;
	}
	public static void setServiceCharge(String serviceCharge) {
		TransProductInfo.serviceCharge = serviceCharge;
	}
	public static String getAmountToAccount() {
		return amountToAccount;
	}
	public static void setAmountToAccount(String amountToAccount) {
		TransProductInfo.amountToAccount = amountToAccount;
	}
}
