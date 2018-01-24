package com.hjs.db;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.hjs.mybatis.inter.EifFisOperation;
import com.hjs.mybatis.inter.EifMarketOperation;
import com.hjs.mybatis.inter.EifMemberOperation;
import com.hjs.mybatis.inter.EifPayCoreOperation;
import com.hjs.mybatis.inter.SmsVerifyCodeOperation;

public class Test2 {

//	public static void main(String[] args) throws Exception {
////		//动态配置数据库参数  
////		Properties properties = new Properties();  
////		properties.setProperty("jdbc.driver", "com.mysql.jdbc.Driver");  
////		properties.setProperty("jdbc.url", "jdbc:mysql://172.16.57.131:43306/eif_market");  
////		properties.setProperty("jdbc.username", "em");  
////		properties.setProperty("jdbc.password", "emadmin123"); 
//		
//        String resource = "eifMarketConfig.xml";
//        Reader reader = Resources.getResourceAsReader(resource);  
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
//        reader.close();  
//        SqlSession session = sqlSessionFactory.openSession();
//        try {
//        	EifMarketOperation eifMarketOperation=session.getMapper(EifMarketOperation.class);
//        	eifMarketOperation.deleteUserCoupon("fb3e8117ac474936822f1dd8588c7b40");
//        	session.commit();  
//        }
//        finally {
//            session.close();
//        }
//    }
	public void openBaofuProvider_payment_limitationStatus() throws IOException{
		String resource = "eifPayCoreConfig.xml";
	    Reader reader = Resources.getResourceAsReader(resource);  
	    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
	    reader.close();  
	    SqlSession session = sqlSessionFactory.openSession();
	    PayCore_provider_payment_limitation payCore_provider=new PayCore_provider_payment_limitation();
	    payCore_provider.setBank_no("03080000");
	    payCore_provider.setProvider_no("0007");
	    payCore_provider.setStatus(0);
	    try {
	    	EifPayCoreOperation eifPayCoreOperation=session.getMapper(EifPayCoreOperation.class);
	    	eifPayCoreOperation.updateProvider_payment_limitationStatus(payCore_provider);
		    session.commit();
	        
	    } finally {
	        session.close();
	    }
	}
	public void openShengFuTongProvider_payment_limitationStatus() throws IOException{
		String resource = "eifPayCoreConfig.xml";
	    Reader reader = Resources.getResourceAsReader(resource);  
	    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
	    reader.close();  
	    SqlSession session = sqlSessionFactory.openSession();
	    PayCore_provider_payment_limitation payCore_provider=new PayCore_provider_payment_limitation();
	    payCore_provider.setBank_no("03080000");
	    payCore_provider.setProvider_no("0002");
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
	public static void main(String[] args) throws Exception {
		Test2 test=new Test2();
		test.openShengFuTongProvider_payment_limitationStatus();
	}
}
