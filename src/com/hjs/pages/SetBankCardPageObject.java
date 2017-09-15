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
	@AndroidFindBy(id="dlg_msg_singlebtn")
	private AndroidElement setBankSucConfirmBtn;		//绑卡成功确认按钮
	@AndroidFindBy(id="dlg_msg_title")
	private AndroidElement setBankResultTitle;		//绑卡结果
	@AndroidFindBy(id="dlg_msg_msg")
	private AndroidElement setBankResultMsg;		//绑卡提示
	
	
	
	
	private By bankCardIdInputLocator=By.xpath("//android.widget.TextView[contains(@text,'填写银行储蓄卡卡号')]");
	public SetBankCardPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public PersonInfoPageObject setBankCard(String bankCardId,String phoneNum) throws Exception{
		clickEle(bankCardIdInput,"银行卡号输入框");
		SafeKeyBoard safeKeyBoard=new SafeKeyBoard(driver);
		if(!safeKeyBoard.verifySafeKeyBoardLocated()){
			throw new Exception("安全键盘未出现");
		}
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
		String setBankResult=setBankResultTitle.getText();
		Assert.assertEquals("绑定成功", setBankResult,"绑卡失败:"+setBankResult);
		clickEle(setBankSucConfirmBtn,"绑卡成功确认按钮");
		return new PersonInfoPageObject(driver);
	}
	public MyBankCardPageObject myBankCardPageGotoSetNormalBankCard(String bankCardId,String phoneNum) throws Exception{
		clickEle(bankCardIdInput,"银行卡号输入框");
		SafeKeyBoard safeKeyBoard=new SafeKeyBoard(driver);
		if(!safeKeyBoard.verifySafeKeyBoardLocated()){
			throw new Exception("安全键盘未出现");
		}
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
		String setBankResult=setBankResultTitle.getText();
		Assert.assertEquals("绑定成功", setBankResult,"绑卡失败:"+setBankResult);
		Assert.assertTrue(setBankResultMsg.getText().contains("本卡仅限于支付，无法接受投资回款。只有您的安全卡具备接受回款的特权，请知悉"),"绑定普通卡失败："+setBankResultMsg.getText());
		clickEle(setBankSucConfirmBtn,"绑卡成功确认按钮");
		return new MyBankCardPageObject(driver);
	}
	public RechargePageObject rechargePageGotoSetSafeBankCard(String bankCardId,String phoneNum) throws Exception{
		clickEle(bankCardIdInput,"银行卡号输入框");
		SafeKeyBoard safeKeyBoard=new SafeKeyBoard(driver);
		if(!safeKeyBoard.verifySafeKeyBoardLocated()){
			throw new Exception("安全键盘未出现");
		}
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
		String setBankResult=setBankResultTitle.getText();
		Assert.assertEquals("绑定成功", setBankResult,"绑卡失败:"+setBankResult);
		Assert.assertTrue(setBankResultMsg.getText().contains("本卡已设为您的安全卡。可用于支付，也将是唯一用于接收投资回款的银行卡。如需了解更多，可查：我的-设置-银行卡"),"绑定安全卡失败："+setBankResultMsg.getText());
		clickEle(setBankSucConfirmBtn,"绑卡成功确认按钮");
		return new RechargePageObject(driver);
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
