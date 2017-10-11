package com.hjs.pages;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.openqa.selenium.By;

import com.hjs.config.CommonAppiumPage;
import com.hjs.db.MemberFundAccount;
import com.hjs.mybatis.inter.EifMemberOperation;
import com.hjs.testDate.Account;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2017年9月29日 上午10:46:06
* 类说明
*/
public class FundDetailPageObject extends CommonAppiumPage{

	@AndroidFindBy(id="btn_enter_ym")
	private AndroidElement enterYMBtn;		//进入盈米按钮
	@AndroidFindBy(id="fund_yesterdayChg")
	private AndroidElement yesterdayChg;		//7日年化
	@AndroidFindBy(id="fund_nav")
	private AndroidElement fundNav;		//万份收益
	@AndroidFindBy(id="fund_typeDesc")
	private AndroidElement fundTypeDesc;		//基金类型
	@AndroidFindBy(id="fund_riskLevelDesc")
	private AndroidElement fundRiskLevelDesc;		//基金风险等级描述
	@AndroidFindBy(id="btn_fund_purchase")
	private AndroidElement fundPurchaseBtn;		//基金申购按钮
	
	private By fundPurchaseBtnLocator=By.id("btn_fund_purchase");
	
	
	public FundDetailPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
		this.skipEnterYMTips();
	}
	public CommonAppiumPage purchaseFund() throws IOException{
		clickEle(fundPurchaseBtn,"基金申购按钮");
		if(this.isExitFundAccount("17092807345")){//Account.getCurrentAccount()
			return new FundPurchasePageObject(driver);
		}
		else return new FundAccountCreatPageObject(driver);
		
	}
	public void skipEnterYMTips(){
		if(super.isElementExsit(5, enterYMBtn)){
			clickEle(enterYMBtn,"进入盈米按钮");
		}
	}
	public boolean isExitFundAccount(String phoneNum) throws IOException{
		String resource = "eifMemberConfig.xml";
	    Reader reader = Resources.getResourceAsReader(resource);  
	    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
	    reader.close();  
	    SqlSession session = sqlSessionFactory.openSession();
	    
	    try {
	    	EifMemberOperation eifMemberOperation=session.getMapper(EifMemberOperation.class);
	    	MemberFundAccount memberFundAccount=eifMemberOperation.getFundAccount(phoneNum);
	    	if(memberFundAccount==null){
	    		return false;
	    	}
	    	else return true;
	    } finally {
	        session.close();
	    }
	}
	public boolean verifyInthisPage(){
		return isElementExsit(fundPurchaseBtnLocator);
	}

}
