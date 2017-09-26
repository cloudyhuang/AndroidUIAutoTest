package com.hjs.pages;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.openqa.selenium.By;
import org.testng.Assert;

import com.hjs.config.CommonAppiumPage;
import com.hjs.db.FisProdInfo;
import com.hjs.db.MarketGrouponTask;
import com.hjs.mybatis.inter.EifFisOperation;
import com.hjs.mybatis.inter.EifMarketOperation;
import com.hjs.publics.Util;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2017年9月6日 上午9:54:22
* 类说明
*/
public class DepositGroupBuyProductDetailPageObject extends CommonAppiumPage{
	@AndroidFindBy(id="activity_title")
	private AndroidElement prodName;	//产品名称
	@AndroidFindBy(id="button_pay")
	private AndroidElement payBtn;		//点击参团按钮
	@AndroidFindBy(id="textView_startMoney")
	private AndroidElement startMoney;		//起投金额
	@AndroidFindBy(id="highest_profit")
	private AndroidElement highestExtraProfit;		//团购最高加息
	@AndroidFindBy(id="btn_create")
	private AndroidElement createGroupBuyBtn;		//发起团按钮
	@AndroidFindBy(id="btn_join")
	private AndroidElement joinGroupBuyBtn;		//加入团按钮
	@AndroidFindBy(id="code1")
	private AndroidElement joinGroupBuyCode1;		//加入团邀请码1
	@AndroidFindBy(id="code2")
	private AndroidElement joinGroupBuyCode2;		//加入团邀请码1
	@AndroidFindBy(id="code3")
	private AndroidElement joinGroupBuyCode3;		//加入团邀请码1
	@AndroidFindBy(id="code4")
	private AndroidElement joinGroupBuyCode4;		//加入团邀请码1
	
	@AndroidFindBy(id="btn_submit")
	private AndroidElement submitBtn;		//确认按钮
	//@AndroidFindBy(id="dlg_msg_rightbtn")
	@AndroidFindBy(id="dlg_msg_3pbtn_type2_1")
	private AndroidElement msgSubmitBtn;		//弹框确认按钮
	@AndroidFindBy(xpath="//android.widget.LinearLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/group_progress']/android.widget.TextView[1]")
	private AndroidElement groupedAmount;		//已团金额
	@AndroidFindBy(id="tv_group_num")
	private AndroidElement groupedPeopleNum;		//已购团购人数
	@AndroidFindBy(id="tv_group_next_num")
	private AndroidElement groupedNextNum;		//团购还差人数
	
	
	private By payBtnLocator=By.id("button_pay");
	private By highestExtraProfitLocator=By.id("highest_profit");
	public DepositGroupBuyProductDetailPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	public void checkGroupBuyInfo() throws IOException{
		String productName=prodName.getText();
		String appMaxRate=highestExtraProfit.getText();
		appMaxRate=Util.getNumInString(appMaxRate);
		String expectMaxRate=getMaxRate(getBaseProductId(productName));
		int expectIntMaxRate=Integer.parseInt(expectMaxRate);
		expectIntMaxRate=expectIntMaxRate/100;
		expectMaxRate=String.valueOf(expectIntMaxRate);
		Assert.assertEquals(appMaxRate, expectMaxRate,"预期最大收益："+expectMaxRate+"app显示最大收益:"+appMaxRate);
	}
	public void checkGroupedInfo(String buyedAmount,String buyedPeopleNum) throws IOException{
		String productName=prodName.getText();
		String appGroupedAmount=groupedAmount.getText();	
		appGroupedAmount=Util.getNumInString(appGroupedAmount);		//app已购金额
		Assert.assertEquals(appGroupedAmount, buyedAmount,"app显示已购金额："+appGroupedAmount+"期望已购金额："+buyedAmount);
		String appGroupedPeopleNum=groupedPeopleNum.getText();		//app已购人数
		Assert.assertEquals(appGroupedPeopleNum, buyedPeopleNum,"app显示已购人数："+appGroupedPeopleNum+"期望已购人数："+buyedPeopleNum);
		String appGroupedNextNum=groupedNextNum.getText();
		String minPeopleNum=getMinUserCount(getBaseProductId(productName));
		int expectNextNum=Integer.parseInt(minPeopleNum)-Integer.parseInt(buyedPeopleNum);
		Assert.assertEquals(appGroupedNextNum, Integer.toString(expectNextNum),"app显示还差人数："+appGroupedNextNum+"期望还差人数："+Integer.toString(expectNextNum));
		
	}
	public GroupedBuyDetailPageObject gotoGroupedBuyDetailPage(){
		clickEle(groupedPeopleNum,"已团购人数");
		return new GroupedBuyDetailPageObject(driver);
	}
	public InvestGroupBuyPageObject startGroupBuy(){
		if(isElementExsit(5, highestExtraProfitLocator)){
			clickEle(payBtn,"点击参团按钮");
			clickEle(createGroupBuyBtn,"发起团按钮");
			clickEle(submitBtn,"确认按钮");
			clickEle(msgSubmitBtn,"弹框确认按钮");
		}
		else{
			clickEle(payBtn,"点击参团按钮");
		}
		return new InvestGroupBuyPageObject(driver);
	}

	public CommonAppiumPage joinGroupBuy(String joinCode) throws Exception {
		if(joinCode.length()>4){
			throw new Exception("加入邀请码超过4位");
		}
		if (isElementExsit(5, highestExtraProfitLocator)) {
			clickEle(payBtn, "点击参团按钮");
			sendKeys(joinGroupBuyCode1, String.valueOf(joinCode.charAt(0)));
			joinGroupBuyCode2.clear();
			sendKeys(joinGroupBuyCode2, String.valueOf(joinCode.charAt(1)));
			joinGroupBuyCode3.clear();
			sendKeys(joinGroupBuyCode3, String.valueOf(joinCode.charAt(2)));
			joinGroupBuyCode4.clear();
			sendKeys(joinGroupBuyCode4, String.valueOf(joinCode.charAt(3)));
			clickEle(submitBtn, "确认按钮");
			clickEle(msgSubmitBtn, "弹框确认按钮");
		}
		else {
			clickEle(payBtn, "点击参团按钮");
			return new InvestGroupBuyPageObject(driver);
		}
		return new GroupedBuyDetailPageObject(driver);
	}
	public String getStartMoney(){
		return startMoney.getText();
	}
	public String getBaseProductId(String productName) throws IOException{
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
        	return fisProdInfo.getBase_product_id();
        }
        finally {
            session.close();
        }
	}
	 public String getMinUserCount(String baseProdId) throws IOException{
			String resource = "eifMarketConfig.xml";
			String minUserCount=null;
		    Reader reader = Resources.getResourceAsReader(resource);  
		    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
		    reader.close();  
		    SqlSession session = sqlSessionFactory.openSession();
		    try {
		    	EifMarketOperation eifMarketOperation=session.getMapper(EifMarketOperation.class);
		    	List<MarketGrouponTask> getGrouponTaskList = eifMarketOperation.getGrouponTask(baseProdId);
		    	if(getGrouponTaskList.size()>0){
		    		minUserCount=getGrouponTaskList.get(0).getMin_user_count();
		    	}

		    	return minUserCount;
		        
		    } finally {
		        session.close();
		    }
		}
	 public String getMaxRate(String baseProdId) throws IOException{
			String resource = "eifMarketConfig.xml";
			List<Integer>rate=new ArrayList<Integer>();
		    Reader reader = Resources.getResourceAsReader(resource);  
		    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
		    reader.close();  
		    SqlSession session = sqlSessionFactory.openSession();
		    try {
		    	EifMarketOperation eifMarketOperation=session.getMapper(EifMarketOperation.class);
		    	List<MarketGrouponTask> getGrouponTaskList = eifMarketOperation.getGrouponTask(baseProdId);
		    	if(getGrouponTaskList.size()>0){
			    	for(int i=0;i<getGrouponTaskList.size();i++){
			    		rate.add(Integer.parseInt(getGrouponTaskList.get(i).getAward_rates()));
			    	}    	
		    	}
		    	Collections.sort(rate);
		    	String maxRate= rate.get(rate.size()-1).toString();
		    	return maxRate;
		        
		    } finally {
		        session.close();
		    }
		}
	public boolean verifyInthisPage(){
		return isElementExsit(payBtnLocator);
	}

}
