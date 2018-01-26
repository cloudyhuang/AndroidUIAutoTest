package com.hjs.db;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.hjs.Interface.Common;
import com.hjs.Interface.GetOrderPushStatus;
import com.hjs.mybatis.inter.EifMarketOperation;
import com.hjs.mybatis.inter.EifMemberOperation;
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
}
