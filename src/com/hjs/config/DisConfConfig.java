package com.hjs.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;
import org.testng.Assert;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.hjs.Interface.GetOrderPushStatus;

/**
* @author huangxiao
* @version 创建时间：2017年9月21日 下午7:02:48
* 类说明
*/
public class DisConfConfig {
	public void updateDisConfConfig(String configId,String value){
		int httpStatusCode;
		String httpResult = null;
		GetOrderPushStatus orderpushstatus = new GetOrderPushStatus();
		String url = ("http://172.16.59.243:8081/api/account/signin");
		String params = "name=admin&password=admin&remember=1";
		httpResult = orderpushstatus.sendx_www_form_urlencodedPost(url, params);//响应结果
		httpStatusCode=orderpushstatus.getStatusCode();  //响应码
		System.out.println(httpResult+"status:"+httpStatusCode);
		JSONObject postJsonobj = new JSONObject(httpResult);
		String msg=postJsonobj.get("message").toString();
		boolean success=postJsonobj.getBoolean("success");
		Assert.assertTrue(success,"接口返回错误，错误信息:"+msg);
		url = ("http://172.16.59.243:8081/api/web/config/item/"+configId);
		params = "configId="+configId+"&value="+value;
		httpResult = orderpushstatus.sendx_www_form_urlencodedPut(url, params);//响应结果
		httpStatusCode=orderpushstatus.getStatusCode();  //响应码
		System.out.println(httpResult+"status:"+httpStatusCode);
		postJsonobj = new JSONObject(httpResult);
		msg=postJsonobj.get("message").toString();
		success=postJsonobj.getBoolean("success");
		Assert.assertTrue(success,"接口返回错误，错误信息:"+msg);
	}
	public JSONObject getDisConfConfig(String configId){
		int httpStatusCode;
		String httpResult = null;
		GetOrderPushStatus orderpushstatus = new GetOrderPushStatus();
		String url = ("http://172.16.59.243:8081/api/account/signin");
		String params = "name=admin&password=admin&remember=1";
		httpResult = orderpushstatus.sendx_www_form_urlencodedPost(url, params);//响应结果
		httpStatusCode=orderpushstatus.getStatusCode();  //响应码
		System.out.println(httpResult+"status:"+httpStatusCode);
		JSONObject postJsonobj = new JSONObject(httpResult);
		String msg=postJsonobj.get("message").toString();
		boolean success=postJsonobj.getBoolean("success");
		Assert.assertTrue(success,"登录接口返回错误，错误信息:"+msg);
		url = ("http://172.16.59.243:8081/api/web/config/1855");
		httpResult=orderpushstatus.sendGet(url);//响应结果
		httpStatusCode=orderpushstatus.getStatusCode();  //响应码
		System.out.println(httpResult+"status:"+httpStatusCode);
		postJsonobj = new JSONObject(httpResult);
		return postJsonobj;
	}
	public String getVerifyCodeConfigValue(){
		JSONObject json=this.getDisConfConfig("1855");
		return json.getJSONObject("result").getString("value");
	}

	public void closeVerifyCode(){
		//captcha.captchaStatus=false\ncaptcha.captchaCode=23456789abcdefghjkmnpqrstuvwxyz\ncaptcha.captchaType=randomLineDecorator\ncaptcha.digitChars=23456789\ncaptcha.letterChars=abcdefghjkmnpqrstuvwxyz
		String value=this.getDisConfConfig("1855").getJSONObject("result").getString("value");
		Map<String, String> valueMap = Splitter.on("\n").withKeyValueSeparator("=").split(value);
		Map<String, String> cv = new LinkedHashMap<>();
		cv.putAll(valueMap);
		cv.put("captcha.captchaStatus", "false");
		String closeVerifyCodeValue=Joiner.on("\n").withKeyValueSeparator("=").join(cv);
		this.updateDisConfConfig("1855", closeVerifyCodeValue);
	}
	public void openVerifyCode(){
		String value=this.getDisConfConfig("1855").getJSONObject("result").getString("value");
		Map<String, String> valueMap = Splitter.on("\n").withKeyValueSeparator("=").split(value);
		Map<String, String> cv = new LinkedHashMap<>();
		cv.putAll(valueMap);
		cv.put("captcha.captchaStatus", "true");
		String closeVerifyCodeValue=Joiner.on("\n").withKeyValueSeparator("=").join(cv);
		this.updateDisConfConfig("1855", closeVerifyCodeValue);
	}
	

}
