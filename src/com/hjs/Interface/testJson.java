package com.hjs.Interface;

import java.io.IOException;

import org.apache.http.HttpException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import com.hjs.publics.Util;
public class testJson {
	public String httpResult = null;
	public String outParams = null;
	public String returnCode = null;
	public String bodyinfo = null;
	public String msg = null;
	public int httpStatusCode;
	public static GetOrderPushStatus orderpushstatus = new GetOrderPushStatus();

	@Test
	public void getOrder() {
		String currentDate=Util.getcurrentDate();
		String channelName="黄XAutoTest通道"+currentDate;
		String url="http://172.16.57.47:48080//eif-fis-web/rpc/call/com.eif.fis.facade.biz.omc.OmcFacade/createAssetChannel";
		String params="[{\"assetChannelDetail\":{\"chanStatus\":\"1\",\"channelName\":\"黄XAutoTest通道201706141027\",\"channelType\":\"0\",\"comment\":\"通道\",\"hdfax\":{\"bankInfos\":[{\"bankAccHolder\":\"屋里\",\"bankAccount\":\"622578676566787862\",\"bankName\":\"上海银行\",\"status\":\"1\"}],\"orgName\":\"hdfax\"},\"isEditable\":\"true\"}}]";
		JSONArray postJsonArray=new JSONArray(params);
		postJsonArray.getJSONObject(0).getJSONObject("assetChannelDetail").put("channelName", channelName);
		httpResult = orderpushstatus.sendJsonPost(url, postJsonArray.toString());
		httpStatusCode = orderpushstatus.getStatusCode(); // 响应码
		JSONObject postJsonobj = new JSONObject(httpResult);
		String channelId=String.valueOf(postJsonobj.get("id"));
		System.out.println(httpResult + "status:" + httpStatusCode+"\n"+channelId);
		
//		url = ("http://172.16.57.62:48080/eif-omc-web/auth/login");
//		params = "account=huangxiao&password=123456";
//		//httpResult = orderpushstatus.sendJsonPost(url,params);
//		httpResult = orderpushstatus.sendx_www_form_urlencodedPost(url, params);//响应结果
//		httpStatusCode=orderpushstatus.getStatusCode();  //响应码
//		System.out.println(httpResult+"status:"+httpStatusCode);
//		
//		url = ("http://172.16.57.62:48080/eif-omc-web/prod/create");
//		params = "{\"baseInfo\":{\"actualAnnualYieldRate\":\"0.05\",\"annualYieldRate\":\"0.05\",\"assetsId\":\"905\",\"baseProdName\":\""+baseProductName+"\",\"baseProdScale\":\"1000000\",\"cashOutType\":\"0\",\"chargeAmt\":\"1\",\"chargeType\":\"1\",\"closeType\":\"1\",\"currency\":\"CNY\",\"dailyMaxBuyAmt\":\"-1\",\"defaultInsType\":\"0\",\"earredeeIntberWay\":\"0\",\"excessRedeemAmt\":\"-1\",\"expectedAnnualYieldRate\":\"0.05\",\"groupBuyId\":\"-1\",\"groupons\":\"\",\"historicalAnnualYieldRate\":\"0.05\",\"holdAmt\":\"0\",\"hugeRedeemAmt\":\"-1\",\"incomePerMillion\":\"0.82\",\"interBear\":\"2\",\"interPeriod\":\"360\",\"invesStrategy\":\"2\",\"investmentUnit\":\"0\",\"isProfitReinvest\":\"false\",\"isRevenuesOnRedemptionDay\":\"true\",\"isSupportTrans\":\"false\",\"isTimeLimited\":\"false\",\"limitBuyAmt\":\"-1\",\"marketExtInfo\":{\"mrktPlusRate\":\"0.05\",\"mrktPlusRateDesc\":\"0123\"},\"marketingExtraYieldRate\":\"0.003\",\"maxBuyAmt\":\"-1\",\"maxSellAmt\":\"-1\",\"minBuyAmt\":\"1\",\"minSellAmt\":\"1\",\"normalRedeemAmt\":\"1000000\",\"normalRedeemMode\":\"3\",\"overdueRate\":\"0\",\"payMode\":\"0&1&2\",\"perAmount\":\"1\",\"perIncAmt\":\"100\",\"perUdbLimitAmt\":\"-1\",\"perUdsLimitAmt\":\"-1\",\"perYearDate\":\"365\",\"productGuarantee\":\"SA\",\"productLimit\":\"360\",\"productRank\":\"1\",\"profitMode\":\"0\",\"profitType\":\"0\",\"remainScale\":\"90000000\",\"shareClass\":\"1\",\"subRateCalcuMethod\":\"0\",\"taxRate\":\"0\",\"termType\":\"0\",\"valueDate\":\"2\",\"weekAnnualYieldRate\":\"0.05\"},\"conf\":{\"endTimeBeforeEstablish\":\"0\",\"establishHeadCount\":\"200\",\"establishPercentage\":\"0.5\",\"splitScale\":\"800000\"},\"prodInfo\":{\"attachPath\":\"[{\\\"filePath\\\" : \\\"group1/M00/02/70/rBA5FVk487uAf8peAAAAjNZUajA10.html\\\",\\\"tempName\\\" : \\\"免责说明\\\",\\\"tempType\\\" : \\\"explainFile\\\"}, {\\\"filePath\\\" : \\\"group1/M00/02/70/rBA5FVk487uAf8peAAAAjNZUajA10.html\\\",\\\"tempName\\\" : \\\"产品说明\\\",\\t\\\"tempType\\\" : \\\"rulesFile\\\"}, {\\t\\\"filePath\\\" : \\\"group1/M00/02/70/rBA5FVk487uAf8peAAAAjNZUajA10.html\\\",\\\"tempName\\\" : \\\"风险揭示\\\",\\\"tempType\\\" : \\\"riskFile\\\"}, {\\\"filePath\\\" : \\\"group1/M00/02/70/rBA5FVk487uAf8peAAAAjNZUajA10.html\\\",\\\"tempName\\\" : \\\"认购协议\\\",\\t\\\"tempType\\\" : \\\"targetFile\\\"}]\",\"bizChannel\":\"0\",\"clearAccountSign\":\"5004\",\"collectPeriod\":\"3\",\"contractTempPath\":\"group1/M00/02/70/rBA5FVk487uAf8peAAAAjNZUajA10.html\",\"displayBaseRate\":\"5\",\"displayPlusRate\":\"0.3\",\"displayRate\":\"5+0.3\",\"isComPay\":\"true\",\"isEmployeeExclusive\":\"false\",\"isForceRiskReview\":\"false\",\"isNovicePacks\":\"false\",\"isOwnMaster\":\"true\",\"isRecommend\":\"false\",\"isReservationPurchase\":\"false\",\"isRiskReview\":\"false\",\"isTimeLimited\":\"false\",\"isVisible\":\"true\",\"marketExtInfo\":{\"$ref\":\"$.baseInfo.marketExtInfo\"},\"orderExpireTime\":\"900\",\"planId\":\"1103\",\"planO\":{\"assetsId\":905,\"endTime\":1497768635000,\"minScale\":\"1000000\",\"sequence\":\"0\",\"startTime\":1497433129000},\"prodFirstCategoryFg\":\"2\",\"prodFouthCategoryFg\":\"2\",\"prodFouthCategoryFgO\":\"2\",\"prodSecondCategoryFg\":\"5\",\"prodThirdCategoryFg\":\"10\",\"productDesc\":\"SA\",\"productName\":\""+productName+"\",\"productRemark\":\"SA\",\"productScale\":\"1000000\",\"publishDate\":1497433129000,\"sellFlag\":\"00000000\",\"taxLiability\":\"SA\",\"transBeginTime\":1497433129000,\"transEndTime\":1497768635000,\"whiteListType\":\"0\"}}";
//		postJsonobj = new JSONObject(params);
//		String baseProductName="黄Xtest基础产品"+currentDate;
//		String productName="黄Xtest产品"+currentDate;
//		postJsonobj.getJSONObject("baseInfo").put("baseProdName", baseProductName);
//		postJsonobj.getJSONObject("prodInfo").put("productName", productName);
//		httpResult = orderpushstatus.sendJsonPost(url, postJsonobj.toString());
//		httpStatusCode = orderpushstatus.getStatusCode(); // 响应码
//		System.out.println(httpResult + "status:" + httpStatusCode);
		



	}

}
