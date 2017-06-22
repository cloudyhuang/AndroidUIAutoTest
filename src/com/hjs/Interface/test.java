package com.hjs.Interface;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class test {
	public String httpResult = null;
	public String outParams = null;
	public String returnCode = null;
	public String bodyinfo = null;
	public String msg = null;
	public int httpStatusCode;
	public static GetOrderPushStatus orderpushstatus = new GetOrderPushStatus();
	
	@Test
	public void getOrder() throws IOException{
		String url = ("http://172.16.57.62:48080/eif-omc-web/auth/login");
		String params = "account=huangxiao&password=123456";
		//httpResult = orderpushstatus.sendJsonPost(url,params);
		httpResult = orderpushstatus.sendx_www_form_urlencodedPost(url, params);//响应结果
		httpStatusCode=orderpushstatus.getStatusCode();  //响应码
		System.out.println(httpResult+"status:"+httpStatusCode);
		Reporter.log("请求地址："+orderpushstatus.geturl());
		Reporter.log("返回响应码: "+httpStatusCode);
		Reporter.log("返回结果: "+httpResult);
		Assert.assertEquals(200, httpStatusCode);

	}
}
