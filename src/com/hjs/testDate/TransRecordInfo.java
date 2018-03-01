package com.hjs.testDate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author huangxiao
* @version 创建时间：2018年2月8日 下午2:36:24
* 交易记录信息
*/
public  class TransRecordInfo {
	private static Map<String,TransInfo> recordInfo=new HashMap<String,TransInfo>();
	
	public static Map<String,TransInfo> getRecordInfo() {
		return recordInfo;
	}

	public static void addRecordInfo(String transNo,TransInfo info) {
		recordInfo.put(transNo, info);
	}

	public class TransInfo{
		private  String transName;		//交易名称
		private String transNo;		//交易号
		private  String payMoney;		//支付金额
		private  String payMode;		//支付方式
		private  boolean payIsSuccesse;		//支付是否成功
		private  String payTime;		//支付时间
		public String getTransNo() {
			return transNo;
		}
		public void setTransNo(String transNo) {
			this.transNo = transNo;
		}
		public  String getTransName() {
			return transName;
		}
		public  void setTransName(String transName) {
			this.transName = transName;
		}
		public  String getPayMoney() {
			return payMoney;
		}
		public  void setPayMoney(String payMoney) {
			this.payMoney = payMoney;
		}
		public  String getPayMode() {
			return payMode;
		}
		public  void setPayMode(String payMode) {
			this.payMode = payMode;
		}
		public  boolean getPayIsSuccesse() {
			return payIsSuccesse;
		}
		public  void setPayIsSuccesse(boolean payIsSuccesse) {
			this.payIsSuccesse = payIsSuccesse;
		}
		public  String getPayTime() {
			return payTime;
		}
		public  void setPayTime(String payTime) {
			this.payTime = payTime;
		}
	}
	
}
