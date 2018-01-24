package com.hjs.db;
/**
* @author huangxiao
* @version 创建时间：2018年1月22日 下午5:13:44
* 类说明
*/
public class PayCore_provider_payment_limitation {
	private String id;
    private String provider_no;
    private String bank_no;
    private int status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProvider_no() {
		return provider_no;
	}
	public void setProvider_no(String provider_no) {
		this.provider_no = provider_no;
	}
	public String getBank_no() {
		return bank_no;
	}
	public void setBank_no(String bank_no) {
		this.bank_no = bank_no;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
    
}
