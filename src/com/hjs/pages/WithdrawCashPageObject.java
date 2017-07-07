package com.hjs.pages;

import java.io.IOException;
import java.io.Reader;
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
import com.hjs.mybatis.inter.EifPayCoreOperation;

public class WithdrawCashPageObject extends CommonAppiumPage{

	
	@AndroidFindBy(id="edit_withdraw_money")
	private AndroidElement withdrawMoneyInput;		//提现输入框
	@AndroidFindBy(id="tv_withdraw_max")
	private AndroidElement withdrawMaxTV;		//最大提现额
	@AndroidFindBy(id="btn_confirm_withdraw")
	private AndroidElement confirmWithdrawBtn;		//确认提现按钮
	
	private By withdrawMoneyInputLocator=By.id("edit_withdraw_money");
	public WithdrawCashPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public WithdrawCashResultPageObject withdrawCash(String amount,String tradePwd) throws Exception{
		this.onlyOpenSHENGFUTONGProvider();	//只打开盛付通渠道
		clickEle(withdrawMoneyInput,"提现输入框");
		SafeKeyBoard safeKeyBoard=new SafeKeyBoard(driver);
		if(!safeKeyBoard.verifySafeKeyBoardLocated()){
			throw new Exception("安全键盘未出现");
		}
		safeKeyBoard.sendNum(amount);
		safeKeyBoard.pressFinishBtn();
		if(safeKeyBoard.verifySafeKeyBoardLocated()){
			throw new Exception("点击完成后，安全键盘未消失");
		}
		clickEle(confirmWithdrawBtn,"确认提现按钮");
		if(!safeKeyBoard.verifySafeKeyBoardLocated()){
			throw new Exception("输入交易密码安全键盘未出现");
		}
		safeKeyBoard.sendNum(tradePwd);
		return new WithdrawCashResultPageObject(driver);
	}
	public void onlyOpenSHENGFUTONGProvider() throws IOException{
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
		return isElementExsit(withdrawMoneyInputLocator);
	}

}
