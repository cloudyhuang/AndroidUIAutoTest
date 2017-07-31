package com.hjs.pages;

import java.io.IOException;
import java.io.Reader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import com.hjs.Interface.GetOrderPushStatus;
import com.hjs.config.CommonAppiumPage;
import com.hjs.db.FisProdInfo;
import com.hjs.db.Member;
import com.hjs.mybatis.inter.EifFisOperation;
import com.hjs.mybatis.inter.EifMemberOperation;
import com.hjs.publics.Util;

public class MyDepositeProductDetailPageObject extends CommonAppiumPage{
	
	@AndroidFindBy(id="textView_expectAnnual")
	private AndroidElement expectAnnualTv;		//预期年化
	@AndroidFindBy(xpath="//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/tv_item_title' and @text='项目期限']/following-sibling::android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/tv_item_remark_des']")
	private AndroidElement productLimitTv;		//项目期限
	@AndroidFindBy(xpath="//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/tv_item_title' and @text='投资起始日']/following-sibling::android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/tv_item_remark_des']")
	private AndroidElement valueDateTv;		//投资起始日
	@AndroidFindBy(xpath="//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/tv_item_title' and @text='投资到期日']/following-sibling::android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/tv_item_remark_des']")
	private AndroidElement dueDateTv;		//投资到期日
	@AndroidFindBy(xpath="//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/tv_item_title' and @text='协议']/following-sibling::android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/tv_item_remark_des']")
	private AndroidElement agreementBtn;		//协议查看按钮
	@AndroidFindBy(id="textView_redeemWay")
	private AndroidElement redeemWayTv;		//回款方式
	@AndroidFindBy(id="textView_redeemGoal")
	private AndroidElement redeemGoalTv;		//回款去向
	@AndroidFindBy(id="textView_refundDate")
	private AndroidElement refundDateTv;		//回款日期
	@AndroidFindBy(id="tv_title")
	private AndroidElement productNameTv;		//产品名称
	@AndroidFindBy(id="layout_back")
	private AndroidElement backBtn;		//返回按钮
	
	private By expectAnnualTvLocator=By.id("textView_expectAnnual");
	public MyDepositeProductDetailPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public Map<String,String>getDetailMap(){
		Map<String,String> productDetailMap=new HashMap<String,String>();
		productDetailMap.put("expectAnnual", expectAnnualTv.getText());	//预期年化
		productDetailMap.put("productLimit", productLimitTv.getText());	//项目期限
		productDetailMap.put("valueDate", valueDateTv.getText());	//投资起始日
		productDetailMap.put("dueDate", dueDateTv.getText());	//投资到期日
		productDetailMap.put("redeemWay", redeemWayTv.getText());	//回款方式
		productDetailMap.put("redeemGoal", redeemGoalTv.getText());	//回款去向
		productDetailMap.put("refundDate", refundDateTv.getText());	//回款日期
		return productDetailMap;
	}
	public List<String>getProductAgreementInfoList(){
		clickEle(agreementBtn,"协议查看按钮");
		List<AndroidElement> agreementListEle=driver.findElements(By.id("tv_item_title"));
		List<String> agreementList=new ArrayList<String>();
		for(int i=0;i<agreementListEle.size();i++){
			agreementList.add(agreementListEle.get(i).getText());
		}
		clickEle(backBtn,"返回按钮");
		return agreementList;
	}

	public void assertMtpAndAppDetail(String phoneNum) throws Exception{
		String memberNo=this.getMemberNo(phoneNum);
		if(memberNo==null||memberNo.equals("")||memberNo.equals("null")){
			throw new Exception("查询不到"+phoneNum+"对应memberNo");
		}
		List<String> appAgreementInfoList=this.getProductAgreementInfoList();
		String productName=productNameTv.getText();
		String ProductId=this.getProductId(productName);
		if(ProductId==null||ProductId.equals("")||ProductId.equals("null")){
			throw new Exception("查询不到"+productName+"对应的ProductId");
		}
		JSONObject mtpProductDetailJson=this.getMtpProductDetailJson(ProductId, memberNo);
		JSONArray jsonarray=mtpProductDetailJson.getJSONObject("fixedProductDetail").getJSONArray("agreementURLInfoList");
		List<String> mtpAgreementInfoList=new ArrayList<String>();
		for(int i=0;i<jsonarray.length();i++){
			mtpAgreementInfoList.add(jsonarray.getJSONObject(i).getString("title"));//mtp产品协议
		}
		Assert.assertTrue(Util.compare(mtpAgreementInfoList, appAgreementInfoList), "app和mtp返回产品协议名称不同，app:"+appAgreementInfoList.toString()+"mtp:"+mtpAgreementInfoList.toString());
		Map<String,String> appProductDetailMap=this.getDetailMap();
		String mtpYieldRate=mtpProductDetailJson.getJSONObject("fixedProductDetail").getString("expectedAnnualYieldRate");	//预期年化
		DecimalFormat df= new DecimalFormat("######0.00");	//mtp返回值四位小数，保留2位
		double mtpYieldRateDouble=Double.valueOf(mtpYieldRate);
		mtpYieldRate=df.format(mtpYieldRateDouble); 
		mtpYieldRate=mtpYieldRate+"%";	//mtp不显示%，app显示%
		String appYieldRate=appProductDetailMap.get("expectAnnual"); //app预期年化
		Assert.assertEquals(mtpYieldRate, appYieldRate,"预期年化app显示错误,mtp:"+mtpYieldRate+"app实际："+appYieldRate);
		
		String mtpProductLimit=String.valueOf(mtpProductDetailJson.getJSONObject("fixedProductDetail").get("productLimit"));	//项目期限
		mtpProductLimit=mtpProductLimit+" 天";	//app显示“xxx 天”
		String appProductLimit=appProductDetailMap.get("productLimit"); //app项目期限
		appProductLimit=appProductLimit.trim();
		Assert.assertEquals(mtpProductLimit, appProductLimit,"项目期限pp显示错误,mtp:"+mtpProductLimit+"app实际："+appProductLimit);
		
		String mtpValueDate=mtpProductDetailJson.getJSONObject("fixedProductDetail").getString("valueDate");	//投资起始日
		String appValueDate=appProductDetailMap.get("valueDate");	//app投资起始日
		Assert.assertEquals(mtpValueDate, appValueDate,"投资起始日pp显示错误,mtp:"+mtpValueDate+"app实际："+appValueDate);
		
		String mtpDueDate=mtpProductDetailJson.getJSONObject("fixedProductDetail").getString("dueDate");	//投资到期日
		String appDueDate=appProductDetailMap.get("dueDate");	//app投资到期日
		Assert.assertEquals(mtpDueDate, appDueDate,"投资到期日pp显示错误,mtp:"+mtpDueDate+"app实际："+appDueDate);
		
		String mtpProfitModeDesc=mtpProductDetailJson.getJSONObject("fixedProductDetail").getString("profitModeDesc");	//回款方式
		String appProfitModeDesc=appProductDetailMap.get("redeemWay");	//app回款方式
		Assert.assertEquals(mtpProfitModeDesc, appProfitModeDesc,"回款方式app显示错误,mtp:"+mtpProfitModeDesc+"app实际："+appProfitModeDesc);
		
		String mtpReturnTo=mtpProductDetailJson.getJSONObject("fixedProductDetail").getString("returnTo");	//回款去向
		String appReturnTo=appProductDetailMap.get("redeemGoal");	//app回款去向
		Assert.assertEquals(mtpReturnTo, appReturnTo,"回款去向app显示错误,mtp:"+mtpReturnTo+"app实际："+appReturnTo);
		
		String mtpAccountingDate=mtpProductDetailJson.getJSONObject("fixedProductDetail").getString("accountingDate");	//回款日期
		mtpAccountingDate="预计"+mtpAccountingDate; //app显示预计xxxx-xx-xx
		String appAccountingDate=appProductDetailMap.get("refundDate");	//app回款日期
		Assert.assertEquals(mtpAccountingDate, appAccountingDate,"回款日期app显示错误,mtp:"+mtpAccountingDate+"app实际："+appAccountingDate);
		
	}
	public JSONObject getMtpProductDetailJson(String ProductId,String memberNo){
		GetOrderPushStatus orderpushstatus = new GetOrderPushStatus();
		String url = ("http://172.16.57.29:8080/eif-mtp-web/mobile/v1/fixedproduct/op_fixed_product_detail.json");
		String params = "{\"fixedProductId\": "+ProductId+",\"memberNo\": \""+memberNo+"\",\"clientId\":\"test\",\"appId\":\"001\"}";
		String httpResult = orderpushstatus.sendJsonPost(url,params);
		int httpStatusCode=orderpushstatus.getStatusCode();  //响应码
		JSONObject jsonobj = new JSONObject(httpResult);
		return jsonobj;
	}
	public String getProductId(String productName) throws Exception{
		String resource = "eifFisConfig.xml";
        Reader reader = Resources.getResourceAsReader(resource);  
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
        reader.close();  
        SqlSession session = sqlSessionFactory.openSession();
        try {
        	EifFisOperation eifFisOperation=session.getMapper(EifFisOperation.class);
        	FisProdInfo fisProdInfo=eifFisOperation.getFisProdInfo(productName);
        	if(fisProdInfo==null){
        		return null;
        	}
        	return fisProdInfo.getId();
        }
        finally {
            session.close();
        }
	}
	public String getMemberNo(String phoneNum) throws Exception{
	    String resource = "eifMemberConfig.xml";
        Reader reader = Resources.getResourceAsReader(resource);  
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
        reader.close();  
        SqlSession session = sqlSessionFactory.openSession();
        try {
        	EifMemberOperation eifMemberOperation=session.getMapper(EifMemberOperation.class);
        	Member member = eifMemberOperation.getMember(phoneNum);
        	if(member==null){
        		return null;
        	}
        	return member.getMember_no();
        } finally {
            session.close();
        }
        
	}
	public boolean verifyInthisPage(){
		return isElementExsit(expectAnnualTvLocator);
	}
}
