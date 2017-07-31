package com.hjs.Interface;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.json.JSONArray;
import org.json.JSONObject;
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
		String url="http://172.16.57.47:48080//eif-fis-web/rpc/call/com.eif.fis.facade.biz.omc.OmcFacade/productPullOffShelves";
		String params="[{\"ids\":[]}]";		
		JSONArray postJsonArray=new JSONArray(params);
		postJsonArray.getJSONObject(0).getJSONArray("ids").put(41327);
		httpResult = orderpushstatus.sendJsonPost(url, postJsonArray.toString());
		httpStatusCode = orderpushstatus.getStatusCode(); // 响应码
		System.out.println(httpResult);
		JSONObject postJsonobj = new JSONObject(httpResult);
		String msg=String.valueOf(postJsonobj.get("msg"));
		boolean success=postJsonobj.getBoolean("success");
		Assert.assertTrue(success,"接口返回错误，错误信息:"+msg);

	}
}
