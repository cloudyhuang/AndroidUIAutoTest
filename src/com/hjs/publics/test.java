package com.hjs.publics;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.lang3.StringEscapeUtils;
import org.eclipse.jetty.websocket.api.Session;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.hjs.config.AppiumServer;

public class test {
	public static void main(String[] args) {
//	String to[] = { "xxxx.com" };
//	// 配置发送邮件的环境属性
//	final Properties props = new Properties();
//	/*
//	* 可用的属性： mail.store.protocol / mail.transport.protocol / mail.host /
//	* mail.user / mail.from
//	*/
//	// 表示SMTP发送邮件，需要进行身份验证
//	props.put("mail.smtp.auth", "true");
//	props.put("mail.smtp.host", "smtp.cvte.com");
//	// 发件人的账号
//	props.put("mail.user", "xxx@cvte.com");
//	// 访问SMTP服务时需要提供的密码
//	props.put("mail.password", "xxxxx");
//
//	// 构建授权信息，用于进行SMTP进行身份验证
//	Authenticator authenticator = new Authenticator() {
//	    @Override
//	    protected PasswordAuthentication getPasswordAuthentication() {
//	        // 用户名、密码
//	        String userName = props.getProperty("mail.user");
//	        String password = props.getProperty("mail.password");
//	        return new PasswordAuthentication(userName, password);
//	    }
//	};
//	// 使用环境属性和授权信息，创建邮件会话
//	Session mailSession = Session.getInstance(props, authenticator);
//	// 创建邮件消息
//	MimeMessage message = new MimeMessage(mailSession);
//	// 设置发件人
//	InternetAddress form = new InternetAddress(props.getProperty("mail.user"));
//	message.setFrom(form);
//
//	// 设置收件人
//	String toList = "xxxx";
//	InternetAddress[] iaToList = new InternetAddress().parse(toList); // 设置多个收件人
//	message.setRecipients(RecipientType.TO, iaToList);
//
//	// 设置抄送
////	        InternetAddress cc = new InternetAddress("wuguohui@cvte.com");
////	        message.setRecipient(RecipientType.CC, cc);
//
//	// 设置密送，其他的收件人不能看到密送的邮件地址
//	// InternetAddress bcc = new InternetAddress("aaaaa@163.com");
//	// message.setRecipient(RecipientType.CC, bcc);
//
//	// 设置邮件标题
//	message.setSubject("Web Auto Test Mail");
//
//	message.setContent(GenerateHtml.readSuitsXml(), "text/html;charset=UTF-8");
//
//	// 发送邮件
//	Transport.send(message);
//	System.out.println("成功发送邮件");
		AppiumServer appiumServer=new AppiumServer();
		appiumServer.stopServer();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		appiumServer.startServer();
	}

}