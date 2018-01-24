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
import com.hjs.db.BankProvider;
import com.hjs.db.DBOperation;
import com.hjs.db.SmsVerifyCode;
import com.hjs.mybatis.inter.EifPayCoreOperation;
import com.hjs.mybatis.inter.SmsVerifyCodeOperation;
import com.hjs.publics.AppiumBaseMethod;
import com.hjs.publics.Util;

public class RechargePageObject extends CommonAppiumPage{
	
	@AndroidFindBy(id="edit_recharge_money")
	private AndroidElement rechargeMoneyInput;		//提现输入框
	@AndroidFindBy(id="btn_confirm_recharge")
	private AndroidElement confirmRechargeBtn;		//确认充值按钮
	@AndroidFindBy(id="dlg_sms_input_singlebtn")
	private AndroidElement smsVerifyCodeCommitBtn;		//短信验证码确认按钮
	
	private By waitMessageLocator=By.id("message");		//等待加载信息
	private By rechargeMoneyInputLocator=By.id("edit_recharge_money");
	public RechargePageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public RechargeResultPageObject recharge(String amount,String tradePwd,String bankPhoneNum) throws Exception{
		this.onlyOpenSHENGFUTONGProvider();	//只打开盛付通渠道
		String rechargeMoneyInputText=rechargeMoneyInput.getText();
		String minAmount=Util.getNumInString(rechargeMoneyInputText);
		double doubleMinAmount=Util.stringToDouble(minAmount);
		double doubleAmount=Util.stringToDouble(amount);
		if(doubleAmount<doubleMinAmount){
			throw new Exception("充值金额不能小于最小金额"+doubleMinAmount);
		}
		clickEle(rechargeMoneyInput,"提现输入框");
		SafeKeyBoard safeKeyBoard=new SafeKeyBoard(driver);
		if(!safeKeyBoard.verifySafeKeyBoardLocated()){
			throw new Exception("安全键盘未出现");
		}
		safeKeyBoard.sendNum(amount);
		safeKeyBoard.pressFinishBtn();
		if(safeKeyBoard.verifySafeKeyBoardLocated()){
			throw new Exception("点击完成后，安全键盘未消失");
		}
		clickEle(confirmRechargeBtn,"确认充值按钮");
		if(!safeKeyBoard.verifySafeKeyBoardLocated()){
			throw new Exception("输入密码安全键盘未出现");
		}
		safeKeyBoard.sendNum(tradePwd);
		threadsleep(2000);	//等待发送验证码
		List<SmsVerifyCode> smsVerifyCodeList=new ArrayList<SmsVerifyCode>();
		smsVerifyCodeList=getMsgVerifyCode(bankPhoneNum);
		if(smsVerifyCodeList.isEmpty()){
			safeKeyBoard.sendNum("222222");
			safeKeyBoard.pressFinishBtn();
			clickEle(smsVerifyCodeCommitBtn,"短信验证码确认按钮");
			waitEleUnVisible(waitMessageLocator, 10);
		}
		else{
			String msgVerifyCode=smsVerifyCodeList.get(smsVerifyCodeList.size()-1).getVerify_code(); //最近的一个验证码
			safeKeyBoard.sendNum(msgVerifyCode);
			safeKeyBoard.pressFinishBtn();
			clickEle(smsVerifyCodeCommitBtn,"短信验证码确认按钮");
			waitEleUnVisible(waitMessageLocator, 10);
		}
		return new RechargeResultPageObject(driver);
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
	public void onlyOpenSHENGFUTONGProvider() throws IOException{
		DBOperation dboperation=new DBOperation();
		dboperation.closeAllProvider_payment_limitationStatus();
		dboperation.openProvider_payment_limitationStatus("0002");
		String resource = "eifPayCoreConfig.xml";
	    Reader reader = Resources.getResourceAsReader(resource);  
	    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
	    reader.close();  
	    SqlSession session = sqlSessionFactory.openSession();
	    try {
	    	EifPayCoreOperation eifPayCoreOperation=session.getMapper(EifPayCoreOperation.class);
	    	List<BankProvider> bankProviderList = eifPayCoreOperation.getBankProvider("03080000");
	    	if(bankProviderList.size()>0){
		    	for(int i=0;i<bankProviderList.size();i++){
		    		if(!bankProviderList.get(i).getProvider_no().equals("0002")){
		    			bankProviderList.get(i).setStatus(1);
			    		int result=eifPayCoreOperation.updateBankProviderStatus(bankProviderList.get(i));
		    		}
		    		else if(bankProviderList.get(i).getProvider_no().equals("0002")){
		    			bankProviderList.get(i).setStatus(0);
			    		int result=eifPayCoreOperation.updateBankProviderStatus(bankProviderList.get(i));
		    		}
		    	}    	
		        session.commit();
	    	}
	        
	    } finally {
	        session.close();
	    }
	}
	public boolean verifyInthisPage(){
		return isElementExsit(rechargeMoneyInputLocator);
	}
}
