package com.hjs.Interface;

import java.io.IOException;
import java.text.ParseException;

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

import com.hjs.publics.Util;

public class test {
	public String httpResult = null;
	public String outParams = null;
	public String returnCode = null;
	public String bodyinfo = null;
	public String msg = null;
	public int httpStatusCode;
	public static GetOrderPushStatus orderpushstatus = new GetOrderPushStatus();
	
	@Test
	public void getOrder() throws IOException, ParseException{
		String url="http://172.16.57.47:48080//eif-fis-web/rpc/call/com.eif.fis.facade.biz.omc.OmcFacade/createAssetsSchedule";
		String params="[{\"AssetSchedule\":{\"createTime\":1515273008604,\"assetsName\":\"黄XAutoTest资管份额180108154042\",\"clearAccountSignMonth\":\"12\",\"status\":\"1\",\"valueDays\":\"1\",\"bgAssetId\":4049,\"isRevenuesOnRedemptionDay\":\"false\",\"inceptionDate\":1516261243000,\"assetsNo\":\"资管项目编号180108154042\",\"batchNo\":\"1323\",\"collectEndTime\":1517989243000,\"comment\":\"1\",\"modifyTime\":1515273008604,\"clearAccountSign\":\"5004\",\"dueDate\":1515359408604,\"productLimit\":\"360\"}}]";
		String userDate=Util.getUserDate("yyyy-MM-dd HH:mm:ss");
		long modifyTime = Util.dateToLong(userDate,"yyyy-MM-dd HH:mm:ss");
		long dueDate=Util.addDate(Util.dateToLong(userDate,"yyyy-MM-dd HH:mm:ss"), 1);
		long inceptionDate=Util.addDate(Util.dateToLong(userDate,"yyyy-MM-dd HH:mm:ss"), 30);
		long collectEndTime=Util.addDate(Util.dateToLong(userDate,"yyyy-MM-dd HH:mm:ss"), 10);
		
		JSONArray postJsonArray=new JSONArray(params);
		postJsonArray.getJSONObject(0).put("assetsName", "黄XAutoTest资管份额180108154042");
		postJsonArray.getJSONObject(0).put("assetsNo", "资管项目编号180108154042");
		postJsonArray.getJSONObject(0).put("bgAssetId", "4049");
		postJsonArray.getJSONObject(0).put("inceptionDate", inceptionDate);
		postJsonArray.getJSONObject(0).put("collectEndTime", collectEndTime);
		postJsonArray.getJSONObject(0).put("modifyTime", modifyTime);
		postJsonArray.getJSONObject(0).put("dueDate", dueDate);
		
		httpResult = orderpushstatus.sendJsonPost(url, postJsonArray.toString());
		httpStatusCode = orderpushstatus.getStatusCode(); // 响应码
		JSONObject postJsonobj = new JSONObject(httpResult);
		String msg=String.valueOf(postJsonobj.get("msg"));
		boolean success=postJsonobj.getBoolean("success");
		Assert.assertTrue(success,"接口返回错误，错误信息:"+msg);
		String assetProjectId=String.valueOf(postJsonobj.get("id"));
		

	}
}
