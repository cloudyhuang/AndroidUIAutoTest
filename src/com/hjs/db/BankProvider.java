package com.hjs.db;

public class BankProvider {
	private String id;
    private String provider_no;
    private String bank_no;
    private int status;
    
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
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
}
