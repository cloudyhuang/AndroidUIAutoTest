package com.hjs.pages;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.openqa.selenium.NoSuchElementException;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import com.hjs.config.CommonAppiumPage;
import com.hjs.db.SmsVerifyCode;
import com.hjs.db.Tests;
import com.hjs.mybatis.inter.SmsVerifyCodeOperation;

public class SignUpPageObject extends CommonAppiumPage{
	
	@AndroidFindBy(id="phone_view")
	private AndroidElement verifyCodeInput;		//验证码输入框
	@AndroidFindBy(id="editText_pwd")
	private AndroidElement pwdInput;		//密码输入框
	@AndroidFindBy(id="button_register")
	private AndroidElement registerBtn;		//确认注册按钮
	
	public SignUpPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public GesturePwd registe(String phoneNum,String pwd) throws Exception{
		sendMsgVerifyCode(phoneNum);
		clickEle(pwdInput,"密码输入框");
		sendKeys(pwdInput,pwd);
		clickEle(registerBtn,"同意协议并注册按钮");
		return new GesturePwd(driver);
	}
	public void sendMsgVerifyCode(String phoneNum) throws Exception{
		
		SafeKeyBoard safeKeyBoard=openSafeKeyBoard();
		if(!safeKeyBoard.verifySafeKeyBoardLocated()){
			throw new Exception("安全键盘未出现");
		}
		List<SmsVerifyCode> smsVerifyCodeList=new ArrayList<SmsVerifyCode>();
		smsVerifyCodeList=getMsgVerifyCode(phoneNum);
		if(smsVerifyCodeList.isEmpty()){
			throw new Exception("数据库未找到"+phoneNum+"的验证码");
		}
		String msgVerifyCode=smsVerifyCodeList.get(smsVerifyCodeList.size()-1).getVerify_code(); //最近的一个验证码
		safeKeyBoard.sendNum(msgVerifyCode);
		safeKeyBoard.pressFinishBtn();
		if(safeKeyBoard.verifySafeKeyBoardLocated()){
			throw new Exception("点击完成后，安全键盘未消失");
		}
	}
	public SafeKeyBoard openSafeKeyBoard(){
		clickEle(verifyCodeInput,"验证码输入框");
		return new SafeKeyBoard(driver);
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
