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

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import com.hjs.config.CommonAppiumPage;
import com.hjs.db.SmsVerifyCode;
import com.hjs.mybatis.inter.SmsVerifyCodeOperation;

public class TradePwdResetPageObject extends CommonAppiumPage{
	
	@AndroidFindBy(id="tv_phone_num")
	private AndroidElement phoneNumText;		//手机号
	@AndroidFindBy(id="button_getSmsCode")
	private AndroidElement getSmsCodeBtn;		//获取短信验证码按钮
	@AndroidFindBy(id="editText_code")
	private AndroidElement smsCodeInput;		//手机验证码输入框
	@AndroidFindBy(id="button_verify_phone")
	private AndroidElement verifyPhoneBtn;		//验证手机号按钮
	@AndroidFindBy(id="editText_realName")
	private AndroidElement realNameInput;		//实名输入框
	@AndroidFindBy(id="editText_id")
	private AndroidElement idNoInput;		//身份证输入框
	@AndroidFindBy(id="editText_cardNo")
	private AndroidElement bankCardInput;		//银行卡输入框
	@AndroidFindBy(id="button_verify_id")
	private AndroidElement verifyIdBtn;		//验证身份按钮
	
	private By phoneNumTextLocator=By.id("tv_phone_num");	//手机号Locator
	private By realNameInputLocator=By.id("editText_realName");	//实名输入框Locator
	public TradePwdResetPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public PwdSettingPageObject resetTradePwd(String realName,String idNo,String bankCardNo,String newTradePwd) throws Exception{
		String phoneNum=phoneNumText.getText();
		clickEle(getSmsCodeBtn,"获取短信验证码按钮");
		threadsleep(2000);
		clickEle(smsCodeInput,"手机验证码输入框");
		SafeKeyBoard safeKeyBoard=new SafeKeyBoard(driver);
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
		clickEle(verifyPhoneBtn,"验证手机号按钮");
		if(!isElementExsit(realNameInputLocator)){
			throw new Exception("验证手机号后未出现验证身份界面，实名输入框未出现");
		}
		clickEle(realNameInput,"实名输入框");
		sendKeys(realNameInput,realName);
		clickEle(idNoInput,"身份证输入框");
		sendKeys(idNoInput,idNo);
		if(!(bankCardNo.isEmpty()||bankCardNo.equals(""))){
			clickEle(bankCardInput,"银行卡输入框");
			if(!safeKeyBoard.verifySafeKeyBoardLocated()){
				throw new Exception("安全键盘未出现");
			}
			safeKeyBoard.sendNum(bankCardNo);
			safeKeyBoard.pressFinishBtn();
			if(safeKeyBoard.verifySafeKeyBoardLocated()){
				throw new Exception("点击完成后，安全键盘未消失");
			}
		}
		clickEle(verifyIdBtn,"验证身份按钮");
		if(!safeKeyBoard.verifySafeKeyBoardLocated()){
			throw new Exception("点击验证身份按钮,安全键盘未出现");
		}
		safeKeyBoard.sendNum(newTradePwd);
		threadsleep(2000);
		safeKeyBoard.sendNum(newTradePwd);
		return new PwdSettingPageObject(driver);
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
	public boolean verifyInthisPage(){
		return isElementExsit(phoneNumTextLocator);
	}

}
