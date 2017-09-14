package com.hjs.testDate;

public class Account {
	private static String safeBankCardID;
	private static String normalBankCardID;
	private static String safeBankPhoneNum;
	private static String normalBankPhoneNum;
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

	public static String getSafeBankPhoneNum() {
		return safeBankPhoneNum;
	}
	public static void setSafeBankPhoneNum(String safeBankPhoneNum) {
		Account.safeBankPhoneNum = safeBankPhoneNum;
	}
	public static String getNormalBankPhoneNum() {
		return normalBankPhoneNum;
	}
	public static void setNormalBankPhoneNum(String normalBankPhoneNum) {
		Account.normalBankPhoneNum = normalBankPhoneNum;
	}
	public static String getSafeBankCardID() {
		return safeBankCardID;
	}
	public static void setSafeBankCardID(String safeBankCardID) {
		Account.safeBankCardID = safeBankCardID;
	}
	public static String getNormalBankCardID() {
		return normalBankCardID;
	}
	public static void setNormalBankCardID(String normalBankCardID) {
		Account.normalBankCardID = normalBankCardID;
	}
	
}
