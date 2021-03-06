package com.hjs.pages;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import com.hjs.config.CommonAppiumPage;

public class PersonSettingPageObject extends CommonAppiumPage{
	
	@AndroidFindBy(id="view_user_photo")
	private AndroidElement userPhoto;		//用户头像
	@AndroidFindBy(id="button_exit")
	private AndroidElement logOutBtn;		//退出按钮
	//@AndroidFindBy(id="dlg_msg_rightbtn")
	@AndroidFindBy(id="dlg_msg_3pbtn_type2_1")
	private AndroidElement dlgMsgRightBtn;		//退出确认按钮
	@AndroidFindBy(id="dlg_msg_3pbtn_type2_2")
	private AndroidElement dlgMsgLeftBtn;		//退出否认按钮	
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@text,'银行卡')]")
	private AndroidElement gotoBankCardBtn;		//跳转我的银行卡按钮
	@AndroidFindBy(xpath="(//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/tv_item_remark_des'])[1]")
	private AndroidElement bankCardName;		//银行卡名称
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@text,'定期回款')]")
	private AndroidElement depositeBackMoney;		//定期回款按钮
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@text,'密码管理')]")
	private AndroidElement gotoPwdResetBtn;		//跳转密码管理按钮
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@text,'风险评测')]")
	private AndroidElement gotoRiskEvaluationBtn;		//跳转风险评测按钮
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@text,'风险评测')]/following-sibling::android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/tv_item_remark_des']")
	private AndroidElement riskResult;		//风险评测结果，xx型或尚未评测
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@text,'关于')]")
	private AndroidElement gotoAboutBtn;		//跳转关于按钮
	
	private By logOutBtnLocator=By.id("button_exit");	//退出按钮Locator
	//private By dlgMsgRightBtnLocator=By.id("dlgMsgRightBtn");	//退出确认按钮Locator
	public PersonSettingPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public LoginPageObject logOut(){
		clickEle(logOutBtn,"退出按钮");
		clickEle(dlgMsgRightBtn,"退出确认按钮");
		return new LoginPageObject(driver);
	}
	public PersonSettingPageObject noLogOut(){
		clickEle(logOutBtn,"退出按钮");
		clickEle(dlgMsgLeftBtn,"退出否认按钮");
		return new PersonSettingPageObject(driver);
	}
	public PersonInfoPageObject gotoPersonInfo(){
		clickEle(userPhoto,"设置-用户头像");
		return new PersonInfoPageObject(driver);
	}
	public MyBankCardPageObject gotoMyBankCard(){
		clickEle(gotoBankCardBtn,"跳转我的银行卡按钮");
		return new MyBankCardPageObject(driver);
	}
	public String getBankCardName(){
		return bankCardName.getText();
	}
	public PwdSettingPageObject gotoPwdResetPage(){
		clickEle(gotoPwdResetBtn,"跳转密码管理页面按钮");
		return new PwdSettingPageObject(driver);
	}
	public DepositeBackMoneySettingPageObject gotoBackMoneySettingPage(){
		clickEle(depositeBackMoney,"定期回款按钮");
		return new DepositeBackMoneySettingPageObject(driver);
	}
	public CommonAppiumPage gotoRiskEvaluation(){
		
		if(riskResult.getText().contains("立即评测")){
			clickEle(gotoRiskEvaluationBtn,"跳转风险评测按钮");
			return new RiskEvaluationPageObject(driver);
		}
		else {
			clickEle(gotoRiskEvaluationBtn,"跳转风险评测按钮");
			return new RiskEvaluationResultPageObject(driver);
		}
	}
	public boolean verifyInthisPage(){
		return isElementExsit(logOutBtnLocator);
	}
	public AboutPageObject gotoAboutPage(){
		clickEle(gotoAboutBtn,"跳转关于按钮");
		return new AboutPageObject(driver);
	}

}
