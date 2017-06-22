package com.hjs.testDate;

public class Account {
	private static String bankCardID;
	private static String bankPhoneNum;
	private static String tradePwd;
	private static String realName;
	private static String idno;
	private static String loginPwd;
	public static String getLoginPwd() {
		return loginPwd;
	}
	public static void setLoginPwd(String loginPwd) {
		Account.loginPwd = loginPwd;
	}
	public static String getRealName() {
		return realName;
	}
	public static void setRealName(String realName) {
		Account.realName = realName;
	}
	public static String getIdno() {
		return idno;
	}
	public static void setIdno(String idno) {
		Account.idno = idno;
	}
	public static String getTradePwd() {
		return tradePwd;
	}
	public static void setTradePwd(String tradePwd) {
		Account.tradePwd = tradePwd;
	}
	public static String getBankCardID() {
		return bankCardID;
	}
	public static void setBankCardID(String bankCardID) {
		Account.bankCardID = bankCardID;
	}
	public static String getBankPhoneNum() {
		return bankPhoneNum;
	}
	public static void setBankPhoneNum(String phoneNum) {
		Account.bankPhoneNum = phoneNum;
	}
	
}
