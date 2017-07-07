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

public class SetBankCardPageObject extends CommonAppiumPage{
	
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@text,'填写银行储蓄卡卡号')]")
	private AndroidElement bankCardIdInput;		//银行卡号输入框
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@text,'填写银行预留手机号')]")
	private AndroidElement phoneNumInput;		//手机号输入框
	@AndroidFindBy(id="button_commit")
	private AndroidElement commitBtn;		//设置银行卡提交按钮
	@AndroidFindBy(id="dlg_sms_input_singlebtn")
	private AndroidElement smsVerifyCodeCommitBtn;		//短信验证码确认按钮
	
	private By bankCardIdInputLocator=By.xpath("//android.widget.TextView[contains(@text,'填写银行储蓄卡卡号')]");
	public SetBankCardPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public void setBankCard(String bankCardId,String phoneNum) throws Exception{
		clickEle(bankCardIdInput,"银行卡号输入框");
		SafeKeyBoard safeKeyBoard=new SafeKeyBoard(driver);
		safeKeyBoard.sendNum(bankCardId);
		safeKeyBoard.pressFinishBtn();
		clickEle(phoneNumInput,"手机号输入框");
		safeKeyBoard.sendNum(phoneNum);
		safeKeyBoard.pressFinishBtn();
		clickEle(commitBtn,"设置银行卡提交按钮");
		threadsleep(2000);	//等待发送验证码
		List<SmsVerifyCode> smsVerifyCodeList=new ArrayList<SmsVerifyCode>();
		smsVerifyCodeList=getMsgVerifyCode(phoneNum);
		if(smsVerifyCodeList.isEmpty()){
			throw new Exception("数据库未找到"+phoneNum+"的验证码");
		}
		String msgVerifyCode=smsVerifyCodeList.get(smsVerifyCodeList.size()-1).getVerify_code(); //最近的一个验证码
		safeKeyBoard.sendNum(msgVerifyCode);
		safeKeyBoard.pressFinishBtn();
		clickEle(smsVerifyCodeCommitBtn,"短信验证码确认按钮");
	}
	public boolean verifyInthisPage(){
		return isElementExsit(bankCardIdInputLocator);
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
