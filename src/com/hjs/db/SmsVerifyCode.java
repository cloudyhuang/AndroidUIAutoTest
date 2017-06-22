package com.hjs.db;

public class SmsVerifyCode {
	private String id;
    private String phone_num;
    private String verify_code;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPhone_num() {
        return phone_num;
    }
    public void setPhoneNum(String phone_num) {
        this.phone_num = phone_num;
    }
    public String getVerify_code() {
        return verify_code;
    }
    public void setVerifyCode(String verify_code) {
        this.verify_code = verify_code;
    }
    @Override
    public String toString() {
        return "User [id=" + id + ", phone_num=" + phone_num + ", verify_code=" + verify_code + "]";
    }
}
