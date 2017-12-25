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
import org.testng.Reporter;

import com.hjs.config.CommonAppiumPage;
import com.hjs.config.DisConfConfig;
import com.hjs.db.SmsVerifyCode;
import com.hjs.mybatis.inter.SmsVerifyCodeOperation;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2017年12月19日 下午4:27:38
* 类说明
*/
public class FindPwdVerifyPhonePageObject extends CommonAppiumPage{
	
	@AndroidFindBy(id="phone_view")
	private AndroidElement phoneInput;		//手机号输入框
	@AndroidFindBy(id="editText_captcha")
	private AndroidElement captchaInput;		//图形验证码输入框
	@AndroidFindBy(id="editText_code")
	private AndroidElement smsCodeInput;		//短信验证码输入框
	@AndroidFindBy(id="button_getSmsCode")
	private AndroidElement getSmsCodeBtn;		//获取短信验证码按钮
	@AndroidFindBy(id="button_nextStep")
	private AndroidElement verifyPhoneBtn;		//验证手机号码按钮
	
	private By getSmsCodeBtnLocator=By.id("button_getSmsCode");
	public FindPwdVerifyPhonePageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	
	public FindPwdVerifyRealNamePageObject verifyPhone(String phoneNum) throws IOException{
		if(super.isElementExsit(5,captchaInput)){
			DisConfConfig disConfConfig=new DisConfConfig();
	    	disConfConfig.closeVerifyCode();	//关闭验证码验证
			clickEle(captchaInput,"验证码输入框");
			super.sendKeys(captchaInput, "1234");
		}	
		clickEle(getSmsCodeBtn,"获取短信验证码按钮");
		super.threadsleep(5000); 	//等待发送验证码
		SafeKeyBoard safeKeyBoard=new SafeKeyBoard(driver);
		List<SmsVerifyCode> smsVerifyCodeList=new ArrayList<SmsVerifyCode>();
		smsVerifyCodeList=getMsgVerifyCode(phoneNum);
		if(smsVerifyCodeList.isEmpty()){
			Reporter.log("数据库未找到"+phoneNum+"的验证码");
			safeKeyBoard.sendNum("111111");
			safeKeyBoard.pressFinishBtn();
		}
		else{
			String msgVerifyCode=smsVerifyCodeList.get(smsVerifyCodeList.size()-1).getVerify_code(); //最近的一个验证码
			clickEle(smsCodeInput,"短信验证码输入框");
			safeKeyBoard.sendNum(msgVerifyCode);
			safeKeyBoard.pressFinishBtn();
		}
		clickEle(verifyPhoneBtn,"验证手机号码按钮");
		super.threadsleep(5000); 	//等待验证通过
		DisConfConfig disConfConfig=new DisConfConfig();
    	disConfConfig.openVerifyCode();	//打开图像验证码验证
		return new FindPwdVerifyRealNamePageObject(driver);
	}
	public boolean verifyInthisPage(){
		return isElementExsit(getSmsCodeBtnLocator);
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


}
