package com.hjs.testDate;
/**
* @author huangxiao
* @version 创建时间：2017年9月12日 上午11:32:22
* 类说明
*/
public class GroupBuyInfo {
	private static String groupBuyName;
	private static String groupBuyShareCode;
	private static int groupBuyPeopleNum=0;
	private static double groupBuyedAmountm=0;
	public static String getGroupBuyName() {
		return groupBuyName;
	}
	public static void setGroupBuyName(String groupBuyName) {
		GroupBuyInfo.groupBuyName = groupBuyName;
	}
	public static String getGroupBuyShareCode() {
		return groupBuyShareCode;
	}
	public static void setGroupBuyShareCode(String groupBuyShareCode) {
		GroupBuyInfo.groupBuyShareCode = groupBuyShareCode;
	}
	public static int getGroupBuyPeopleNum() {
		return groupBuyPeopleNum;
	}
	public static void setGroupBuyPeopleNum(int groupBuyPeopleNum) {
		GroupBuyInfo.groupBuyPeopleNum = groupBuyPeopleNum;
	}
	public static double getGroupBuyedAmountm() {
		return groupBuyedAmountm;
	}
	public static void setGroupBuyedAmountm(double groupBuyedAmountm) {
		GroupBuyInfo.groupBuyedAmountm = groupBuyedAmountm;
	}
	
}
