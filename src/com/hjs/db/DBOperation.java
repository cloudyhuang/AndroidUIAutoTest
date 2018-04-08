package com.hjs.db;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.json.JSONObject;

import com.hjs.Interface.Common;
import com.hjs.Interface.GetOrderPushStatus;
import com.hjs.mybatis.inter.EifBalanceOperation;
import com.hjs.mybatis.inter.EifFisOperation;
import com.hjs.mybatis.inter.EifMarketOperation;
import com.hjs.mybatis.inter.EifMemberOperation;
import com.hjs.mybatis.inter.EifOfcOperation;
import com.hjs.mybatis.inter.EifPayCoreOperation;
import com.hjs.mybatis.inter.SmsVerifyCodeOperation;

/**
* @author huangxiao
* @version 创建时间：2017年12月20日 下午6:30:56
* 类说明
*/
public class DBOperation {
	public String getRealNameByPhone(String phoneNum) throws Exception{
		String resource = "eifMemberConfig.xml";
	    Reader reader = Resources.getResourceAsReader(resource);  
	    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
	    reader.close();  
	    SqlSession session = sqlSessionFactory.openSession();
	    
	    try {
	    	EifMemberOperation eifMemberOperation=session.getMapper(EifMemberOperation.class);
	    	MemberClientCertification memberClientCertification=eifMemberOperation.getMemberClientCertification(phoneNum);
	    	if(memberClientCertification==null){
	    		throw new Exception("数据库找不到"+phoneNum+"实名信息");
	    	}
	    	else{
		        return memberClientCertification.getName();
	    	}
	    } finally {
	        session.close();
	    }
	}
	public String getIdNoByPhone(String phoneNum) throws Exception{
		String resource = "eifMemberConfig.xml";
	    Reader reader = Resources.getResourceAsReader(resource);  
	    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
	    reader.close();  
	    SqlSession session = sqlSessionFactory.openSession();
	    
	    try {
	    	EifMemberOperation eifMemberOperation=session.getMapper(EifMemberOperation.class);
	    	MemberClientCertification memberClientCertification=eifMemberOperation.getMemberClientCertification(phoneNum);
	    	if(memberClientCertification==null){
	    		throw new Exception("数据库找不到"+phoneNum+"实名信息");
	    	}
	    	else{
		        return memberClientCertification.getIdno();
	    	}
	    } finally {
	        session.close();
	    }
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
		
		this.closeAllProvider_payment_limitationStatus();	//关闭Provider_payment_limitation所有渠道状态status
		this.openProvider_payment_limitationStatus("0002");	//打开Provider_payment_limitation中0002 盛付通的状态status
		this.updateNoProviderTransLimit("0002");	//将0002 盛付通的限额全部放开
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
		this.closeAllProvider_payment_limitationStatus();//关闭Provider_payment_limitation所有渠道状态status
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
		this.openProvider_payment_limitationStatus(providerNo);	//打开Provider_payment_limitation中指定providerNo的状态status
		this.updateNoProviderTransLimit(providerNo);	//将providerNo的限额全部放开
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
	public void openProvider_payment_limitationStatus(String providerNo) throws IOException{
		String resource = "eifPayCoreConfig.xml";
	    Reader reader = Resources.getResourceAsReader(resource);  
	    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
	    reader.close();  
	    SqlSession session = sqlSessionFactory.openSession();
	    PayCore_provider_payment_limitation payCore_provider=new PayCore_provider_payment_limitation();
	    payCore_provider.setBank_no("03080000");
	    payCore_provider.setProvider_no(providerNo);
	    payCore_provider.setStatus(0);
	    try {
	    	EifPayCoreOperation eifPayCoreOperation=session.getMapper(EifPayCoreOperation.class);
	    	eifPayCoreOperation.updateProvider_payment_limitationStatus(payCore_provider);
		    session.commit();
	        
	    } finally {
	        session.close();
	    }
	}
	
	public void closeAllProvider_payment_limitationStatus() throws IOException{
		String resource = "eifPayCoreConfig.xml";
	    Reader reader = Resources.getResourceAsReader(resource);  
	    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
	    reader.close();  
	    SqlSession session = sqlSessionFactory.openSession();
	    PayCore_provider_payment_limitation payCore_provider=new PayCore_provider_payment_limitation();
	    payCore_provider.setBank_no("03080000");
	    payCore_provider.setStatus(1);
	    try {
	    	EifPayCoreOperation eifPayCoreOperation=session.getMapper(EifPayCoreOperation.class);
	    	eifPayCoreOperation.updateProvider_payment_limitationStatusByBankNo(payCore_provider);
		    session.commit();
	        
	    } finally {
	        session.close();
	    }
	}
	public void updateNoProviderTransLimit(String providerNo) throws IOException{
		String resource = "eifPayCoreConfig.xml";
	    Reader reader = Resources.getResourceAsReader(resource);  
	    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
	    reader.close();  
	    SqlSession session = sqlSessionFactory.openSession();
	    PayCore_provider_payment_limitation payCore_provider=new PayCore_provider_payment_limitation();
	    payCore_provider.setBank_no("03080000");
	    payCore_provider.setProvider_no(providerNo);
	    payCore_provider.setTrans_limit("10000000.000000");
	    payCore_provider.setTrans_min("0.000000");
	    payCore_provider.setDay_limit("99999999999.000000");
	    payCore_provider.setMonth_limit("99999999999.000000");
	    try {
	    	EifPayCoreOperation eifPayCoreOperation=session.getMapper(EifPayCoreOperation.class);
	    	eifPayCoreOperation.updateProviderTransLimits(payCore_provider);
	    	eifPayCoreOperation.updateProviderTransMin(payCore_provider);
	    	eifPayCoreOperation.updateProviderDayLimits(payCore_provider);
	    	eifPayCoreOperation.updateProviderMonthLimit(payCore_provider);
		    session.commit();
	        
	    } finally {
	        session.close();
	    }
	}
    /**
     * 通过产品名称获取产品ID
     *
     * @param productName  产品名称
     * @return 产品ID
     */
	public String getProductId(String productName) throws IOException{
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
	 /**
     * 通过产品名称获取活期产品ID
     *
     * @param productName  活期产品名称
     * @return 活期产品ID
     */
	public String getCurrentProductId(String productName) throws IOException{
		String resource = "eifFisConfig.xml";
        Reader reader = Resources.getResourceAsReader(resource);  
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
        reader.close();  
        SqlSession session = sqlSessionFactory.openSession();
        try {
        	EifFisOperation eifFisOperation=session.getMapper(EifFisOperation.class);
        	FisCurrentProdInfo fisCurrentProdInfo=eifFisOperation.getFisCurrentProdInfo(productName);
        	if(fisCurrentProdInfo==null){
        		return null;
        	}
        	return fisCurrentProdInfo.getId();
        }
        finally {
            session.close();
        }
	}
	 /**
     * 通过手机号获取最近交易号
     *
     * @param phoneNum  手机号
     * @return 最近交易号
	 * @throws Exception 
     */
	public String getTransNoByPhoneNum(String phoneNum) throws Exception{
		String member_no=this.getMemberNo(phoneNum);
		String resource = "eifOfcConfig.xml";
        Reader reader = Resources.getResourceAsReader(resource);  
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
        reader.close();  
        SqlSession session = sqlSessionFactory.openSession();
        try {
        	EifOfcOperation eifOfcOperation=session.getMapper(EifOfcOperation.class);
        	OfcBusinessOrderItem ofcBusinessOrderItem=eifOfcOperation.getOfcBusinessOrderItem(member_no);
        	if(ofcBusinessOrderItem==null){
        		return null;
        	}
        	return ofcBusinessOrderItem.getBusiness_order_item_no();
        }
        finally {
            session.close();
        }
	}
	 /**
     * 更新存款配置信息
     *
     * @param configValue  配置json
	 * @throws IOException 
     */
	public void updateWithdrawBalanceConfig(String configValue) throws IOException{
		String resource = "eifBalanceConfig.xml";
	    Reader reader = Resources.getResourceAsReader(resource);  
	    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
	    reader.close();  
	    SqlSession session = sqlSessionFactory.openSession();
	    BalanceConfig balanceConfig=new BalanceConfig();
	    balanceConfig.setConfig_key("withdraw");
	    balanceConfig.setConfig_value(configValue);
	    try {
	    	EifBalanceOperation eifBalanceOperation=session.getMapper(EifBalanceOperation.class);
	    	eifBalanceOperation.updateBalanceConfig(balanceConfig);
		    session.commit();
	        
	    } finally {
	        session.close();
	    }
	}
	 /**
     * 得到存款balance配置信息
     *
     * @return 存款配置信息
	 * @throws Exception 
     */
	public String getWithdrawBalanceConfigValue() throws Exception{
		String resource = "eifBalanceConfig.xml";
        Reader reader = Resources.getResourceAsReader(resource);  
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
        reader.close();  
        SqlSession session = sqlSessionFactory.openSession();
        try {
        	EifBalanceOperation eifBalanceOperation=session.getMapper(EifBalanceOperation.class);
        	String configValue=eifBalanceOperation.getBalanceConfigValue("withdraw");
        	return configValue;
        }
        finally {
            session.close();
        }
	}
	/**
     * 得到优惠券规则
     *
     * @return 优惠券规则
	 * @throws Exception 
     */
	public MarketCouponRule getCouponRule(String couponId) throws Exception{
		String resource = "eifMarketConfig.xml";
        Reader reader = Resources.getResourceAsReader(resource);  
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
        reader.close();  
        SqlSession session = sqlSessionFactory.openSession();
        try {
        	EifMarketOperation eifMarketOperation=session.getMapper(EifMarketOperation.class);
        	MarketCouponRule couponRule=eifMarketOperation.getMarketCouponRule(couponId);
        	return couponRule;
        }
        finally {
            session.close();
        }
	}
	 /**
     * 更新存款最低限额
     * @param lowerLimitAmount 最低限额
	 * @throws Exception 
     */
	public void updateWithdrawLowerLimitAmount(double lowerLimitAmount) throws Exception{
		String configValue=this.getWithdrawBalanceConfigValue();
		JSONObject configValueJson=new JSONObject(configValue);
		configValueJson.getJSONObject("amount").put("lowerLimitAmount", lowerLimitAmount);
		//this.updateWithdrawBalanceConfig(configValueJson.toString());
	}
}
