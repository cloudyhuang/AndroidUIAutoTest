package com.hjs.pages;

import java.io.IOException;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.hjs.db.ClientCertification;
import com.hjs.mybatis.inter.EifMemberOperation;

public class RealNamePageObject extends CommonAppiumPage{
	@AndroidFindBy(id="editText_name")
	private AndroidElement nameInput;		//实名姓名输入框
	@AndroidFindBy(id="idCard_view")
	private AndroidElement idCardInput;		//实名身份证输入框
	@AndroidFindBy(id="button_commit")
	private AndroidElement commitBtn;		//实名确认提交按钮
	@AndroidFindBy(id="dlg_msg_msg")
	private AndroidElement dlgMsg;		//对话框信息
	@AndroidFindBy(id="dlg_msg_rightbtn")
	private AndroidElement msgRightBtn;		//对话框右侧按钮（确认）
	
	
	private By idCardInputLocator=By.id("button_commit");	//实名身份证输入框Locator
	public RealNamePageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public String realName(String userName,String idCard) throws Exception{
		beforeRealName(idCard);
		clickEle(nameInput,"实名姓名输入框");
		sendKeys(nameInput,userName);
		SafeKeyBoard safeKeyBoard=openSafeKeyBoard();
		safeKeyBoard.sendNum(idCard);
		safeKeyBoard.pressFinishBtn();
		clickEle(commitBtn,"实名确认提交按钮");
		return dlgMsg.getText();
	}
	public SafeKeyBoard openSafeKeyBoard(){
		clickEle(idCardInput,"实名身份证输入框");
		clickEle(idCardInput,"实名身份证输入框");
		return new SafeKeyBoard(driver);
	}
	public SetTradePwdPageObject confirmRealName(){
		clickEle(msgRightBtn,"对话框右侧按钮（确认）");
		return new SetTradePwdPageObject(driver);
	}
	public boolean verifyInthisPage(){
		return isElementExsit(idCardInputLocator);
	}
	public void beforeRealName(String idCard) throws Exception{
		String realIdno=idCard;
	    Reader reader = Resources.getResourceAsReader("eifMemberConfig.xml");  
	    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
	    reader.close();  
	    SqlSession session = sqlSessionFactory.openSession();
	    try {
	    	EifMemberOperation eifMemberOperation=session.getMapper(EifMemberOperation.class);
	    	List<ClientCertification> clientCertification = eifMemberOperation.getClientCertification(realIdno);
	    	SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");//自定义日期格式
	    	String fakeIdno="511027"+df.format(new Date());
	    	if(clientCertification.size()>0){
		    	for(int i=0;i<clientCertification.size();i++){
		    		clientCertification.get(i).setFakeIdno(fakeIdno);
		    		int result=eifMemberOperation.updateIdno(clientCertification.get(i));
		    		System.out.println(result);
		    	}    	
		        session.commit();
	    	}
	        
	    } finally {
	        session.close();
	    }
	}

}
