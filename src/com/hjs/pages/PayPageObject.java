package com.hjs.pages;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.openqa.selenium.By;
import org.testng.Assert;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import com.hjs.Interface.Common;
import com.hjs.Interface.GetOrderPushStatus;
import com.hjs.config.CommonAppiumPage;
import com.hjs.db.ActivityCoupon;
import com.hjs.db.BankProvider;
import com.hjs.db.Member;
import com.hjs.db.SmsVerifyCode;
import com.hjs.mybatis.inter.EifMarketOperation;
import com.hjs.mybatis.inter.EifMemberOperation;
import com.hjs.mybatis.inter.EifPayCoreOperation;
import com.hjs.mybatis.inter.SmsVerifyCodeOperation;

public class PayPageObject extends CommonAppiumPage{
	
	@AndroidFindBy(id="button_subscribe")
	private AndroidElement confirmPayBtn;		//确认支付按钮
	@AndroidFindBy(id="show_choose_pay_way")
	private AndroidElement choosePayWayBtn;		//更换支付方式按钮
	
	@AndroidFindBy(xpath="//android.widget.ListView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/listView_payway']//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_bankCard' and contains(@text,'银行')][1]")
	private AndroidElement bankCardPayOptions;		//银行卡付款方式
	@AndroidFindBy(xpath="//android.widget.ListView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/listView_payway']//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_bankCard' and contains(@text,'余额支付')]")
	private AndroidElement balancePayOptions;		//余额付款方式
	@AndroidFindBy(id="dlg_sms_input_singlebtn")
	private AndroidElement smsVerifyCodeCommitBtn;		//短信验证码确认按钮
	@AndroidFindBy(id="coupon_name")
	private AndroidElement chooseCouponBtn;		//优惠券选择入口按钮
	@AndroidFindBy(id="message")
	private AndroidElement waitMessage;		//等待信息
	//@AndroidFindBy(id="dlg_msg_rightbtn")
	@AndroidFindBy(id="dlg_msg_3pbtn_type2_1")
	private AndroidElement dlgMsgRightbtn;		//弹出框右侧确认按钮
	@AndroidFindBy(id="dlg_msg_msg")
	private AndroidElement dlgMsgMsg;		//弹出框信息
	
	private By waitMessageLocator=By.id("message");
	private By choosePayWayBtnLocator=By.id("show_choose_pay_way");
	private By balancePayOptionsLocator=By.xpath("//android.widget.ListView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/listView_payway']//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_bankCard' and contains(@text,'余额支付')]");	//余额支付方式选项
	private static final String SHENGFUTONG_FailCode="555555";
	public PayPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public InvestResultPageObject payByBankCardWithoutCoupon(String tradePwd,String bankPhoneNum) throws Exception{
		this.onlyOpenSHENGFUTONGProvider();	//只打开盛付通渠道
		clickEle(chooseCouponBtn,"优惠券选择入口按钮");
		ChooseCouponPageObject chooseCouponPage=new ChooseCouponPageObject(driver);
		chooseCouponPage.chooseCoupon("");
		super.threadsleep(1000);
		//clickEle(bankCardPayOptions,"银行卡付款方式");
		this.chooseBankCardPayWays();
		clickEle(confirmPayBtn,"确认支付按钮");
		if(super.isElementExsit(5, dlgMsgRightbtn)){
			String Msg=dlgMsgMsg.getText();
			if(Msg.contains("本订单未使用优惠券，是否继续？")){
				clickEle(dlgMsgRightbtn,"继续不使用优惠券确认按钮");
			}
		}
		SafeKeyBoard safeKeyBoard=new SafeKeyBoard(driver);
		if(!safeKeyBoard.verifySafeKeyBoardLocated()){
			throw new Exception("安全键盘未出现");
		}
		safeKeyBoard.sendNum(tradePwd);
		threadsleep(2000);	//等待发送验证码
		List<SmsVerifyCode> smsVerifyCodeList=new ArrayList<SmsVerifyCode>();
		smsVerifyCodeList=getMsgVerifyCode(bankPhoneNum);
		if(smsVerifyCodeList.isEmpty()){
			safeKeyBoard.sendNum("222222");
			safeKeyBoard.pressFinishBtn();
			clickEle(smsVerifyCodeCommitBtn,"短信验证码确认按钮");
			waitEleUnVisible(waitMessageLocator, 10);
		}
		else{
			String msgVerifyCode=smsVerifyCodeList.get(smsVerifyCodeList.size()-1).getVerify_code(); //最近的一个验证码
			safeKeyBoard.sendNum(msgVerifyCode);
			safeKeyBoard.pressFinishBtn();
			clickEle(smsVerifyCodeCommitBtn,"短信验证码确认按钮");
			waitEleUnVisible(waitMessageLocator, 10);
		}
		return new InvestResultPageObject(driver);
	}
	public InvestResultPageObject payByBankCardWithoutCouponWithoutSmsCode(String tradePwd,String bankPhoneNum) throws Exception{
		this.onlyOpenSHENGFUTONGProvider();	//只打开盛付通渠道
		this.openNoProvider();		//什么渠道都不打开
		this.openProviderStatus("0007");	//打开宝付支付渠道
		clickEle(chooseCouponBtn,"优惠券选择入口按钮");
		ChooseCouponPageObject chooseCouponPage=new ChooseCouponPageObject(driver);
		chooseCouponPage.chooseCoupon("");
		super.threadsleep(1000);
		//clickEle(bankCardPayOptions,"银行卡付款方式");
		this.chooseBankCardPayWays();
		clickEle(confirmPayBtn,"确认支付按钮");
		SafeKeyBoard safeKeyBoard=new SafeKeyBoard(driver);
		if(!safeKeyBoard.verifySafeKeyBoardLocated()){
			throw new Exception("安全键盘未出现");
		}
		safeKeyBoard.sendNum(tradePwd);
		waitEleUnVisible(waitMessageLocator, 10);
		return new InvestResultPageObject(driver);
	}
	public InvestResultPageObject payByBankCardFailWithoutCoupon(String tradePwd,String bankPhoneNum) throws Exception{
		this.onlyOpenSHENGFUTONGProvider();	//只打开盛付通渠道
		clickEle(chooseCouponBtn,"优惠券选择入口按钮");
		ChooseCouponPageObject chooseCouponPage=new ChooseCouponPageObject(driver);
		chooseCouponPage.chooseCoupon("");
		super.threadsleep(1000);
		//clickEle(bankCardPayOptions,"银行卡付款方式");
		this.chooseBankCardPayWays();
		clickEle(confirmPayBtn,"确认支付按钮");
		SafeKeyBoard safeKeyBoard=new SafeKeyBoard(driver);
		if(!safeKeyBoard.verifySafeKeyBoardLocated()){
			throw new Exception("安全键盘未出现");
		}
		safeKeyBoard.sendNum(tradePwd);
		threadsleep(2000);	//等待发送验证码
		safeKeyBoard.sendNum(SHENGFUTONG_FailCode);//输入失败验证码
		safeKeyBoard.pressFinishBtn();
		clickEle(smsVerifyCodeCommitBtn,"短信验证码确认按钮");
		waitEleUnVisible(waitMessageLocator, 10);
		return new InvestResultPageObject(driver);
	}
	public InvestResultPageObject payByBankCardWithCoupon(String tradePwd,String bankPhoneNum,String couponId) throws Exception{
		this.onlyOpenSHENGFUTONGProvider();	//只打开盛付通渠道
		clickEle(chooseCouponBtn,"优惠券选择入口按钮");
		ChooseCouponPageObject chooseCouponPage=new ChooseCouponPageObject(driver);
		String couponName=this.getCouponName(couponId);//从数据库找优惠券名称
		if(couponName==null||couponName.equals("")||couponName.equals("null")){
			throw new Exception("查询不到券id为"+couponId+"对应的券名称");
		}
		chooseCouponPage.chooseCoupon(couponName);
		super.threadsleep(1000);//跳转等待
		//clickEle(bankCardPayOptions,"银行卡付款方式");
		this.chooseBankCardPayWays();
		clickEle(confirmPayBtn,"确认支付按钮");
		SafeKeyBoard safeKeyBoard=new SafeKeyBoard(driver);
		if(!safeKeyBoard.verifySafeKeyBoardLocated()){
			throw new Exception("安全键盘未出现");
		}
		safeKeyBoard.sendNum(tradePwd);
		threadsleep(2000);	//等待发送验证码
		List<SmsVerifyCode> smsVerifyCodeList=new ArrayList<SmsVerifyCode>();
		smsVerifyCodeList=getMsgVerifyCode(bankPhoneNum);
		if(smsVerifyCodeList.isEmpty()){
			safeKeyBoard.sendNum("222222");
			safeKeyBoard.pressFinishBtn();
			clickEle(smsVerifyCodeCommitBtn,"短信验证码确认按钮");
			waitEleUnVisible(waitMessageLocator, 10);
		}
		else{
			String msgVerifyCode=smsVerifyCodeList.get(smsVerifyCodeList.size()-1).getVerify_code(); //最近的一个验证码
			safeKeyBoard.sendNum(msgVerifyCode);
			safeKeyBoard.pressFinishBtn();
			clickEle(smsVerifyCodeCommitBtn,"短信验证码确认按钮");
			waitEleUnVisible(waitMessageLocator, 10);
		}
		return new InvestResultPageObject(driver);
	}
	public InvestResultPageObject payByBankCardWithCouponWithoutSmsCode(String tradePwd,String bankPhoneNum,String couponId) throws Exception{
		this.onlyOpenSHENGFUTONGProvider();	//只打开盛付通渠道
		this.openNoProvider();		//什么渠道都不打开
		this.openProviderStatus("0007");	//打开宝付支付渠道
		clickEle(chooseCouponBtn,"优惠券选择入口按钮");
		ChooseCouponPageObject chooseCouponPage=new ChooseCouponPageObject(driver);
		String couponName=this.getCouponName(couponId);//从数据库找优惠券名称
		if(couponName==null||couponName.equals("")||couponName.equals("null")){
			throw new Exception("查询不到券id为"+couponId+"对应的券名称");
		}
		chooseCouponPage.chooseCoupon(couponName);
		super.threadsleep(1000);
		//clickEle(bankCardPayOptions,"银行卡付款方式");
		this.chooseBankCardPayWays();
		clickEle(confirmPayBtn,"确认支付按钮");
		SafeKeyBoard safeKeyBoard=new SafeKeyBoard(driver);
		if(!safeKeyBoard.verifySafeKeyBoardLocated()){
			throw new Exception("安全键盘未出现");
		}
		safeKeyBoard.sendNum(tradePwd);
		waitEleUnVisible(waitMessageLocator, 5);
		return new InvestResultPageObject(driver);
	}
	public InvestResultPageObject payByBanlanceWithoutCoupon(String tradePwd,String bankPhoneNum) throws Exception{
		clickEle(chooseCouponBtn,"优惠券选择入口按钮");
		ChooseCouponPageObject chooseCouponPage=new ChooseCouponPageObject(driver);
		chooseCouponPage.chooseCoupon("");
		super.threadsleep(1000);
		//clickEle(balancePayOptions,"余额付款方式");
		this.chooseBanlancePayWays();
		clickEle(confirmPayBtn,"确认支付按钮");
		if(super.isElementExsit(5, dlgMsgRightbtn)){
			String Msg=dlgMsgMsg.getText();
			if(Msg.contains("本订单未使用优惠券，是否继续？")){
				clickEle(dlgMsgRightbtn,"继续不使用优惠券确认按钮");
			}
		}
		SafeKeyBoard safeKeyBoard=new SafeKeyBoard(driver);
		if(!safeKeyBoard.verifySafeKeyBoardLocated()){
			throw new Exception("安全键盘未出现");
		}
		safeKeyBoard.sendNum(tradePwd);
		waitEleUnVisible(waitMessageLocator, 5);
		return new InvestResultPageObject(driver);
	}
	public InvestResultPageObject payByBanlanceWithCoupon(String tradePwd,String bankPhoneNum,String couponId) throws Exception{
		clickEle(chooseCouponBtn,"优惠券选择入口按钮");
		ChooseCouponPageObject chooseCouponPage=new ChooseCouponPageObject(driver);
		String couponName=this.getCouponName(couponId);//从数据库找优惠券名称
		if(couponName==null||couponName.equals("")||couponName.equals("null")){
			throw new Exception("查询不到券id为"+couponId+"对应的券名称");
		}
		chooseCouponPage.chooseCoupon(couponName);
		super.threadsleep(1000);
		//clickEle(balancePayOptions,"余额付款方式");
		this.chooseBanlancePayWays();
		clickEle(confirmPayBtn,"确认支付按钮");
		SafeKeyBoard safeKeyBoard=new SafeKeyBoard(driver);
		if(!safeKeyBoard.verifySafeKeyBoardLocated()){
			throw new Exception("安全键盘未出现");
		}
		safeKeyBoard.sendNum(tradePwd);
		waitEleUnVisible(waitMessageLocator,10);
		return new InvestResultPageObject(driver);
	}
	public void chooseBankCardPayWays(){
		clickEle(choosePayWayBtn,"更换支付方式按钮");
		if(!isElementExsit(balancePayOptionsLocator)){
			Assert.assertTrue(false, "点击更换支付方式后未弹出余额支付方式");
		}
		clickEle(bankCardPayOptions,"银行卡付款方式");
	}
	public void chooseBanlancePayWays(){
		clickEle(choosePayWayBtn,"更换支付方式按钮");
		if(!isElementExsit(balancePayOptionsLocator)){
			Assert.assertTrue(false, "点击更换支付方式后未弹出余额支付方式");
		}
		clickEle(balancePayOptions,"余额付款方式");
	}
	public List<SmsVerifyCode> getMsgVerifyCode(String phoneNum) throws IOException{
        Reader reader = Resources.getResourceAsReader("eifGoutongMybatis.xml");  
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
        reader.close();  
        SqlSession session = sqlSessionFactory.openSession();
        try {
        	SmsVerifyCodeOperation smsVerifyCodeOperation=session.getMapper(SmsVerifyCodeOperation.class);
        	List<SmsVerifyCode> smsVerifyCodes = smsVerifyCodeOperation.getSmsVerifyCode(phoneNum);
        	return smsVerifyCodes;
        } finally {
            session.close();
        }
	}
	public boolean addCouponToUser(String phoneNum,String couponId) throws Exception{
		String memberNo=this.getMemberNo(phoneNum);
		if(memberNo==null||memberNo.equals("")||memberNo.equals("null")){
			throw new Exception("查询不到"+phoneNum+"对应memberNo");
		}
		GetOrderPushStatus orderpushstatus = new GetOrderPushStatus();
		String url = ("http://172.16.57.55:48080//eif-market-web/rpc/call/com.eif.market.facade.biz.MKTCouponUseFacade/issueCouponToUser");
		String params = "[{ \"activityCouponId\":"+couponId+", \"issueDpt\":\"string\", \"memberNoList\":[\""+memberNo+"\"] }]\n";
		String httpResult =orderpushstatus.sendJsonPost(url,params);
		//int httpStatusCode=orderpushstatus.getStatusCode();  //响应码
		boolean flag=Boolean.parseBoolean(Common.getJsonValue(httpResult, "success")); ;	//响应结果
		return flag;
	}
	public void deleteUserCoupon(String phoneNum) throws Exception{
		String memberNo=this.getMemberNo(phoneNum);
		if(memberNo==null||memberNo.equals("")||memberNo.equals("null")){
			throw new Exception("查询不到"+phoneNum+"对应memberNo");
		}
		String resource = "eifMarketConfig.xml";
        Reader reader = Resources.getResourceAsReader(resource);  
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
        reader.close();  
        SqlSession session = sqlSessionFactory.openSession();
        try {
        	EifMarketOperation eifMarketOperation=session.getMapper(EifMarketOperation.class);
        	eifMarketOperation.deleteUserCoupon(memberNo);
        	session.commit();  
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
        	return member.getMember_no();
        } finally {
            session.close();
        }
        
	}
	public String getCouponName(String couponId) throws IOException{
      String resource = "eifMarketConfig.xml";
      Reader reader = Resources.getResourceAsReader(resource);  
      SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
      reader.close();  
      SqlSession session = sqlSessionFactory.openSession();
      try {
      	EifMarketOperation eifMarketOperation=session.getMapper(EifMarketOperation.class);
      	ActivityCoupon activityCoupon=eifMarketOperation.getActivityCoupon(couponId);
      	return activityCoupon.getName();
      } finally {
          session.close();
      }
	}
	public void onlyOpenSHENGFUTONGProvider() throws IOException{
		String resource = "eifPayCoreConfig.xml";
	    Reader reader = Resources.getResourceAsReader(resource);  
	    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
	    reader.close();  
	    SqlSession session = sqlSessionFactory.openSession();
	    try {
	    	EifPayCoreOperation eifPayCoreOperation=session.getMapper(EifPayCoreOperation.class);
	    	List<BankProvider> bankProviderList = eifPayCoreOperation.getBankProvider("03080000");
	    	if(bankProviderList.size()>0){
		    	for(int i=0;i<bankProviderList.size();i++){
		    		if(!bankProviderList.get(i).getProvider_no().equals("0002")){
		    			bankProviderList.get(i).setStatus(1);
			    		int result=eifPayCoreOperation.updateBankProviderStatus(bankProviderList.get(i));
		    		}
		    		else if(bankProviderList.get(i).getProvider_no().equals("0002")){
		    			bankProviderList.get(i).setStatus(0);
			    		int result=eifPayCoreOperation.updateBankProviderStatus(bankProviderList.get(i));
		    		}
		    	}    	
		        session.commit();
	    	}
	        
	    } finally {
	        session.close();
	    }
	}
	
	public void openNoProvider() throws IOException{
		String resource = "eifPayCoreConfig.xml";
	    Reader reader = Resources.getResourceAsReader(resource);  
	    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
	    reader.close();  
		SqlSession session = sqlSessionFactory.openSession();
		try {
			EifPayCoreOperation eifPayCoreOperation = session.getMapper(EifPayCoreOperation.class);
			List<BankProvider> bankProviderList = eifPayCoreOperation.getBankProvider("03080000");
			if (bankProviderList.size() > 0) {
				for (int i = 0; i < bankProviderList.size(); i++) {
					bankProviderList.get(i).setStatus(1);
					int result = eifPayCoreOperation.updateBankProviderStatus(bankProviderList.get(i));
				}
				session.commit();
			}
	        
	    } finally {
	        session.close();
	    }
	}
	public void openProviderStatus(String providerNo) throws IOException{
		String resource = "eifPayCoreConfig.xml";
	    Reader reader = Resources.getResourceAsReader(resource);  
	    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
	    reader.close();  
	    SqlSession session = sqlSessionFactory.openSession();
	    try {
	    	EifPayCoreOperation eifPayCoreOperation=session.getMapper(EifPayCoreOperation.class);
	    	List<BankProvider> bankProviderList = eifPayCoreOperation.getBankProvider("03080000");
	    	if(bankProviderList.size()>0){
		    	for(int i=0;i<bankProviderList.size();i++){
		    		if(bankProviderList.get(i).getProvider_no().equals(providerNo)){
		    			bankProviderList.get(i).setStatus(0);
			    		int result=eifPayCoreOperation.updateBankProviderStatus(bankProviderList.get(i));
		    		}
		    	}    	
		        session.commit();
	    	}
	        
	    } finally {
	        session.close();
	    }
	}
	public boolean verifyInthisPage(){
		return isElementExsit(choosePayWayBtnLocator);
	}
}
