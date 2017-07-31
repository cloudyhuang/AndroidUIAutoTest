package com.hjs.mybatis.inter;

import java.util.List;

import com.hjs.db.SmsVerifyCode;

public interface SmsVerifyCodeOperation {
	public List<SmsVerifyCode> getSmsVerifyCode(String phone_num);
}
