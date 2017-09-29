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

import com.hjs.config.CommonAppiumPage;
import com.hjs.db.SmsVerifyCode;
import com.hjs.mybatis.inter.SmsVerifyCodeOperation;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
* @author huangxiao
* @version 创建时间：2017年9月27日 下午5:47:16
* 类说明
*/
public class FundAccountCreatVerifySmsCodePageObject extends CommonAppiumPage{
	@AndroidFindBy(id="account_et_sms_code")
	private AndroidElement smsCodeInput;		//验证码输入框
	@AndroidFindBy(id="btn_confirm_create")
	private AndroidElement confirmBtn;		//确认按钮
	
	private By smsCodeInputLocator=By.id("account_et_sms_code");
	public FundAccountCreatVerifySmsCodePageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public FundPurchasePageObject inputSmsCode(String phoneNum) throws Exception{
		clickEle(smsCodeInput,"验证码输入框");
		List<SmsVerifyCode> smsVerifyCodeList=new ArrayList<SmsVerifyCode>();
		smsVerifyCodeList=getMsgVerifyCode(phoneNum);
		if(smsVerifyCodeList.isEmpty()){
			throw new Exception("数据库未找到"+phoneNum+"的验证码");
		}
		String msgVerifyCode=smsVerifyCodeList.get(smsVerifyCodeList.size()-1).getVerify_code(); //最近的一个验证码
		SafeKeyBoard safeKeyBoard=new SafeKeyBoard(driver);
		safeKeyBoard.sendNum(msgVerifyCode);
		safeKeyBoard.pressFinishBtn();
		clickEle(confirmBtn,"确认按钮");
		return new FundPurchasePageObject(driver);
	}
	public boolean verifyInthisPage(){
		return isElementExsit(smsCodeInputLocator);
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
