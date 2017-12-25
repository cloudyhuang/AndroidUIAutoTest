package com.hjs.db;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.hjs.mybatis.inter.EifMemberOperation;

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
}
