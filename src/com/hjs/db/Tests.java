package com.hjs.db;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

public class Tests {

	 public static void main(String[] args) {
        String resource = "eifGoutongMybatis.xml";
        //使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
        InputStream is = Tests.class.getClassLoader().getResourceAsStream(resource);
        //构建sqlSession的工厂
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
        SqlSession session = sessionFactory.openSession();
        String statement = "com.hjs.db.UserMapping.getSmsVerifyCode";//映射sql的标识字符串
        //执行查询返回一个唯一user对象的sql
        SmsVerifyCode smsVerifyCode = session.selectOne(statement, "17051720280");
        System.out.println(smsVerifyCode.getVerify_code());
    }

}
