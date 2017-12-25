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
	public static void main(String[] args) throws Exception {
		
		String resource = "eifMemberConfig.xml";
	    Reader reader = Resources.getResourceAsReader(resource);  
	    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
	    reader.close();  
	    SqlSession session = sqlSessionFactory.openSession();
	    
	    try {
	    	EifMemberOperation eifMemberOperation=session.getMapper(EifMemberOperation.class);
	    	MemberClientCertification memberClientCertification=eifMemberOperation.getMemberClientCertification("17411184");
	    	if(memberClientCertification==null){
	    		throw new Exception("数据库找不到");
	    	}
	    	else{
	    		System.out.println(memberClientCertification.getIdno());
		        System.out.println(memberClientCertification.getName());
	    	}
	        
	    } finally {
	        session.close();
	    }
	}
}
