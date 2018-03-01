package com.hjs.pages;

import java.io.IOException;
import java.text.ParseException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import com.hjs.Interface.GetOrderPushStatus;
import com.hjs.config.CommonAppiumPage;
import com.hjs.db.DBOperation;
import com.hjs.publics.Util;
import com.hjs.testDate.TransProductInfo;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2018年2月5日 下午4:08:42
* 类说明
*/
public class MyRedeemProductDetailPageObject extends CommonAppiumPage{
	@AndroidFindBy(id="asset_name")
	private AndroidElement productName;		//产品名称
	@AndroidFindBy(xpath="//android.widget.TextView[@text='回款金额']/following-sibling::android.widget.TextView")
	private AndroidElement redeemAmount;		//回款金额
	@AndroidFindBy(xpath="//android.widget.TextView[@text='实际收益']/following-sibling::android.widget.TextView")
	private AndroidElement actualProfit;		//实际收益
	@AndroidFindBy(xpath="//android.widget.TextView[@text='回款去向']/following-sibling::android.widget.TextView")
	private AndroidElement redeemTo;		//回款去向
	@AndroidFindBy(xpath="//android.widget.TextView[@text='转让价格']/following-sibling::android.widget.TextView")
	private AndroidElement transPrice;		//转让价格
	@AndroidFindBy(xpath="//android.widget.TextView[@text='服务费']/following-sibling::android.widget.TextView")
	private AndroidElement serviceCharge;		//服务费
	@AndroidFindBy(xpath="//android.widget.TextView[@text='年化收益率']/following-sibling::android.widget.TextView")
	private AndroidElement yieldProfitRate;		//年化收益
	@AndroidFindBy(xpath="//android.widget.TextView[@text='项目期限']/following-sibling::android.widget.TextView")
	private AndroidElement projectDuration;		//项目期限
	@AndroidFindBy(xpath="//android.widget.TextView[@text='投资金额']/following-sibling::android.widget.TextView")
	private AndroidElement investMoney;		//投资金额
	@AndroidFindBy(xpath="//android.widget.TextView[@text='收益起始']/following-sibling::android.widget.TextView")
	private AndroidElement profitStartDay;		//收益开始日
	@AndroidFindBy(xpath="//android.widget.TextView[@text='收益到期']/following-sibling::android.widget.TextView")
	private AndroidElement profitEndDay;		//收益到期日
	
	public static GetOrderPushStatus orderpushstatus = new GetOrderPushStatus();
	public MyRedeemProductDetailPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public boolean assertProductDetail() throws IOException, ParseException{
		String expectProductName=TransProductInfo.getProductName();		//预期产品名称
		String actualProductName=super.getEleText(productName, "产品名称");
		Assert.assertEquals(actualProductName, expectProductName);
		double expectRedeemAmount=Util.stringToDouble(Util.get2DecimalPointsNumInString(TransProductInfo.getAmountToAccount()));		//预期回款金额
		double actualRedeemAmount=Util.stringToDouble(Util.get2DecimalPointsNumInString(super.getEleText(redeemAmount, "回款金额")));
		Assert.assertEquals(actualRedeemAmount, expectRedeemAmount);
		String expectRedeemTo=TransProductInfo.getRedeemTo();		//预期回款去向	
		String actualRedeemTo=super.getEleText(redeemTo, "回款去向");
		Assert.assertEquals(actualRedeemTo, expectRedeemTo);
		double expectServiceCharge=Util.stringToDouble(Util.get2DecimalPointsNumInString(TransProductInfo.getServiceCharge()));		//预期服务费
		double actualServiceCharge=Util.stringToDouble(Util.get2DecimalPointsNumInString(super.getEleText(serviceCharge, "服务费")));
		Assert.assertEquals(actualServiceCharge, expectServiceCharge);
		String expectYeildProfit=TransProductInfo.getYearProfit();		//预期年化收益
		String actualYeildProfit=super.getEleText(yieldProfitRate, "年化收益");
		Assert.assertEquals(actualYeildProfit, expectYeildProfit);
		double expectTransPrice=expectRedeemAmount+expectServiceCharge;		//预期转让价格
		double actualTransPrice=Util.stringToDouble(Util.get2DecimalPointsNumInString(super.getEleText(transPrice, "转让价格")));
		Assert.assertEquals(actualTransPrice, expectTransPrice);
		DBOperation db=new DBOperation();
		String productId=db.getProductId(expectProductName);
		String url="http://172.16.57.47:48080//eif-fis-web/rpc/call/com.eif.fis.facade.biz.omc.OmcFacade/queryProductDetail";
		String params="[{ \"productId\":46381 }]";		
		JSONArray postJsonArray=new JSONArray(params);
		postJsonArray.getJSONObject(0).put("productId", productId);
		String httpResult = orderpushstatus.sendJsonPost(url, postJsonArray.toString());
		int httpStatusCode = orderpushstatus.getStatusCode(); // 响应码
		System.out.println(httpResult);
		JSONObject resJsonobj = new JSONObject(httpResult);
		String msg=String.valueOf(resJsonobj.get("msg"));
		boolean success=resJsonobj.getBoolean("success");
		Assert.assertTrue(success,"接口返回错误，错误信息:"+msg);
		long longInceptionDate=resJsonobj.getLong("inceptionDate");
		String inceptionDate=Util.longToDate(longInceptionDate, "yyyy-MM-dd");		//成立日
		String actualProfitStartDay=super.getEleText(profitStartDay, "收益起始日");
		Assert.assertEquals(actualProfitStartDay, inceptionDate);
		long longDueDate=resJsonobj.getLong("dueDate");
		String dueDate=Util.longToDate(longDueDate, "yyyy-MM-dd");
		String actualProfitEndDay=super.getEleText(profitEndDay, "收益到期日");
		Assert.assertEquals(actualProfitEndDay, dueDate);
		String expectProductLimit=resJsonobj.get("productLimit").toString();		//产品期限
		String actualProductLimit=Util.getNumInString(super.getEleText(projectDuration, "项目期限"));
		Assert.assertEquals(actualProductLimit, expectProductLimit);
		

		return true;
	}

}
