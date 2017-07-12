package com.hjs.cases;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.StringEscapeUtils;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.hjs.config.CommonAppiumPage;
import com.hjs.config.CommonAppiumTest;
import com.hjs.pages.GesturePwd;
import com.hjs.pages.HomePageObject;
import com.hjs.pages.LoginPageObject;
import com.hjs.pages.LoginPwdResetPageObject;
import com.hjs.pages.MinePageObject;
import com.hjs.pages.MyBankCardPageObject;
import com.hjs.pages.PersonInfoPageObject;
import com.hjs.pages.PersonSettingPageObject;
import com.hjs.pages.PwdSettingPageObject;
import com.hjs.pages.RealNamePageObject;
import com.hjs.pages.RiskEvaluationPageObject;
import com.hjs.pages.RiskEvaluationResultPageObject;
import com.hjs.pages.SetBankCardPageObject;
import com.hjs.pages.SetTradePwdPageObject;
import com.hjs.pages.SignUpPageObject;
import com.hjs.pages.TradePwdResetPageObject;
import com.hjs.pages.WelcomePageObject;
import com.hjs.publics.AppiumBaseMethod;
import com.hjs.testDate.Account;

public class AccountManageTestCase extends CommonAppiumTest {
	@Test(priority = 1,description="跳过欢迎页到主页")
	public void testWelcomePage() throws InterruptedException{
		WelcomePageObject welcomePageObject=new WelcomePageObject(driver); 
		boolean isSwipeSuc=false;
		try {
			isSwipeSuc = welcomePageObject.swipeWelcomeImage().verifyIntoHomepageBtnExist(); 
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Reporter.log(e.getMessage());
		}
    	Assert.assertTrue(isSwipeSuc, "滑动后未出现进入主页按钮");
    	boolean isIntoHomepage=welcomePageObject.intoHomepage().verifyIsInHomePage();
    	Assert.assertTrue(isIntoHomepage, "按下进入主页按钮后没有进入主页");
	}
    @Test(description = "登录",priority = 2)
	public void testSignedPhoneLogin() throws InterruptedException{
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Reporter.log("进入首页-我的入口");
    	Assert.assertTrue(pageName.contains("LoginPageObject"), "点击首页-我的入口未跳转到登录页");
    	Reporter.log("未登录-跳转到登录页");
    	String phoneNum="17052411184";
    	String password="hxnearc228";
    	Account.setLoginPwd(password);
    	LoginPageObject loginPageObject=(LoginPageObject)page;
    	String verifyPhoneNumResultPageName=loginPageObject.verifyPhoneNum(phoneNum).getClass().getName(); //com.hjs.pages.LoginPageObject
    	Assert.assertTrue(verifyPhoneNumResultPageName.contains("LoginPageObject"), "登录账户手机号验证后未进入输入密码页面！");
    	String loginResult=loginPageObject.login(password).verifyGestureTips();
    	Assert.assertEquals("请绘制您的手势密码", loginResult);
	}
    @Test(priority = 3,description="测试手势密码")
    public void testGesturePwd()throws InterruptedException{
    	GesturePwd gesturePwd=new GesturePwd(driver);
    	String result=gesturePwd.setFirstGesturePwd(1,4,7,8);
    	Assert.assertEquals("请再画一次手势密码", result);
    	boolean setNextGestureResult=gesturePwd.setNextGesturePwd(1,4,7,8).verifyIsInHomePage();
    	Assert.assertTrue(setNextGestureResult, "第二次设置手势失败，未跳转到主页，主页我的入口未显示");
    	
    }
    @Test(priority = 4,description="登出账户")
    public void testLogOut()throws InterruptedException{
//    	new HomePageObject(driver).backToHomePage(1,4,7,8);//集成请清除
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Reporter.log("进入首页-我的入口");
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	PersonSettingPageObject personSettingPage=((MinePageObject)page).enterPersonSetting();
    	Assert.assertTrue(personSettingPage.verifyInthisPage(), "进入我的个人头像，未出现安全退出按钮");
    	boolean isLogout=personSettingPage.logOut().verifyInthisPage();
    	Assert.assertTrue(isLogout, "退出后未跳转到首页");
    }
    @Test(priority = 5,description="注册账号")
    public void testSignAccount()throws Exception{
//    	HomePageObject homepage=new HomePageObject(driver); //集成请注释
//    	homepage.enterPersonEntrace();//集成请注释
    	LoginPageObject loginPageObject=new LoginPageObject(driver);
    	Assert.assertTrue(loginPageObject.verifyInthisPage(), "未在登录页面");
    	SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmm");//自定义日期格式
		int randNum = new Random().nextInt(10);//0~9随机数
		String phoneNum=df.format(new Date())+String.valueOf(randNum);
		CommonAppiumPage page=loginPageObject.switchAccount(phoneNum);
		String pageName=page.getClass().getName();
		Assert.assertTrue(pageName.contains("SignUpPageObject"), "未进入注册页面");
		GesturePwd gesturePwd = null;
		String loginPwd="hxnearc228";
		gesturePwd=((SignUpPageObject)page).registe(phoneNum, loginPwd);
		Assert.assertTrue(gesturePwd.verifyInthisPage(), "设置密码后未跳转到手势密码界面");
		String result=gesturePwd.setFirstGesturePwd(1,4,7,8);
    	Assert.assertEquals("请再画一次手势密码", result);
    	boolean setNextGestureResult=gesturePwd.setNextGesturePwd(1,4,7,8).verifyIsInHomePage();
    	Assert.assertTrue(setNextGestureResult, "第二次设置手势失败，未跳转到主页，主页我的入口未显示");
		
    }
    @Test(priority = 6,description="实名并设置银行卡")
    public void testRealNameAndSetBankCard()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	PersonSettingPageObject personSettingPage=((MinePageObject)page).enterPersonSetting();
    	Assert.assertTrue(personSettingPage.verifyInthisPage(), "进入我的个人头像，未出现安全退出按钮");
    	PersonInfoPageObject personInfoPage=personSettingPage.gotoPersonInfo();
    	Assert.assertTrue(personInfoPage.verifyInthisPage(), "进入设置用户头像未跳转到个人信息页面");
    	RealNamePageObject realNamePageObject=personInfoPage.gotoRealNamePage();
    	if(realNamePageObject==null){
    		Assert.assertTrue(false,"该用户已实名，姓名为"+personInfoPage.getUserName());
    	}
    	else{
    		Assert.assertTrue(realNamePageObject.verifyInthisPage(), "个人信息点击未实名未跳转到实名界面");
    	}
    	String realName="黄霄自动化";
    	String idno="511027194810164955";
    	Account.setRealName(realName);
    	Account.setIdno(idno);
    	String realNameResult=realNamePageObject.realName(realName, idno);
    	Assert.assertTrue(realNameResult.contains("成功"), realNameResult);
    	SetTradePwdPageObject setTradePwdPageObject=realNamePageObject.confirmRealName();
    	String tradePwd="123456";
    	setTradePwdPageObject.setPwd(tradePwd);
    	SetBankCardPageObject setBankCardPageObject=new SetBankCardPageObject(driver);
    	SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmm");//自定义日期格式
    	String bankCardId="621483"+df.format(new Date());
    	int randNum = new Random().nextInt(10);//0~9随机数
		String phoneNum=df.format(new Date())+String.valueOf(randNum);
		Account.setBankCardID(bankCardId);
		Account.setBankPhoneNum(phoneNum);
		Account.setTradePwd(tradePwd);
		Assert.assertTrue(setBankCardPageObject.verifyInthisPage(), "未出现银行卡绑卡界面，银行卡号输入框未出现");
    	setBankCardPageObject.setBankCard(bankCardId, phoneNum);
    }
    @Test(priority = 7,description="解绑银行卡")
    public void testUnBundBankCard()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	PersonSettingPageObject personSettingPage=((MinePageObject)page).enterPersonSetting();//进入个人设置页
    	Assert.assertTrue(personSettingPage.verifyInthisPage(), "进入我的个人头像，未进入设置页");
    	MyBankCardPageObject myBankCardPageObject=personSettingPage.gotoMyBankCard();//进入我的银行卡页面
    	Assert.assertTrue(myBankCardPageObject.verifyInthisPage(), "进入银行卡页面未出现银行卡");
    	PersonSettingPageObject personSettingPageObject=myBankCardPageObject.unbundBankCard(Account.getTradePwd(),Account.getBankPhoneNum());
    	Assert.assertTrue(personSettingPageObject.verifyInthisPage(), "解绑银行卡后未跳转到个人设置页面");
    	Thread.sleep(2000); //返回个人设置时银行卡名称会闪现，因此等待一下
    	String bankCardName=personSettingPageObject.getBankCardName();
    	Assert.assertEquals(bankCardName, "未设置", "解绑失败，当前银行卡为："+bankCardName);
    	
    }
    @Test(priority = 8,description="重设交易密码")
    public void testResetTradePwd()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	PersonSettingPageObject personSettingPage=((MinePageObject)page).enterPersonSetting();
    	Assert.assertTrue(personSettingPage.verifyInthisPage(), "进入我的个人头像，未进入设置页");
    	PwdSettingPageObject pwdSettingPage=personSettingPage.gotoPwdResetPage();
    	Assert.assertTrue(pwdSettingPage.verifyInthisPage(), "点击密码管理未进入密码设置页");
    	TradePwdResetPageObject tradePwdResetPage=pwdSettingPage.gotoTradePwdPage();
    	Assert.assertTrue(tradePwdResetPage.verifyInthisPage(), "点击重设交易密码未进入设置交易密码页");
    	String newTradePwd="456123";
    	tradePwdResetPage.resetTradePwd(Account.getRealName(), Account.getIdno(), "", newTradePwd);
    	Account.setTradePwd(newTradePwd);
    	
    }
    @Test(priority = 9,description="重设登录密码")
    public void testResetLoginPwd()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	PersonSettingPageObject personSettingPage=((MinePageObject)page).enterPersonSetting();
    	Assert.assertTrue(personSettingPage.verifyInthisPage(), "进入我的个人头像，未进入设置页");
    	PwdSettingPageObject pwdSettingPage=personSettingPage.gotoPwdResetPage();
    	Assert.assertTrue(pwdSettingPage.verifyInthisPage(), "点击密码管理未进入密码设置页");
    	LoginPwdResetPageObject loginPwdResetPage=pwdSettingPage.gotoLoginPwdPage();
    	Assert.assertTrue(loginPwdResetPage.verifyInthisPage(), "点击重设登录密码未进入设置登录密码页");
    	loginPwdResetPage.resetLoginPwd(Account.getLoginPwd(), "hxnearcj228");
    	Account.setLoginPwd("hxnearcj228");
    }
    @Test(priority = 10,description="风险评测")
    public void testRiskEvaluation()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	PersonSettingPageObject personSettingPage=((MinePageObject)page).enterPersonSetting();
    	Assert.assertTrue(personSettingPage.verifyInthisPage(), "进入我的个人头像，未进入设置页");
    	page=personSettingPage.gotoRiskEvaluation();
    	if(page.getClass().getName().contains("RiskEvaluationPageObject")){
    		RiskEvaluationPageObject RiskEvaluationPage=new RiskEvaluationPageObject(driver);
    		Assert.assertTrue(RiskEvaluationPage.verifyInthisPage(), "未进入风险评测页，开始评测按钮未出现");
    		String riskLevel="R4";
    		RiskEvaluationResultPageObject RiskEvaluationResultPage=RiskEvaluationPage.startRiskEvaluation(riskLevel);
    		String expectRiskResult=RiskEvaluationPage.riskLevelToRiskResult(riskLevel);
    		String riskResult=RiskEvaluationResultPage.getRiskResult();
    		RiskEvaluationResultPage.backNativeApp();
    		Assert.assertEquals(riskResult, expectRiskResult, "实际风评结果为"+riskResult+"然而期待结果为"+expectRiskResult);
    	}
    	else{
    		throw new Exception("该账号已评测过！");
    	}
    }
    @Test(priority = 11,description="重新风险评测")
    public void testReRiskEvaluation()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	PersonSettingPageObject personSettingPage=((MinePageObject)page).enterPersonSetting();
    	Assert.assertTrue(personSettingPage.verifyInthisPage(), "进入我的个人头像，未进入设置页"); 
    	page=personSettingPage.gotoRiskEvaluation();
    	if(page.getClass().getName().contains("RiskEvaluationResultPageObject")){
    		RiskEvaluationResultPageObject RiskEvaluationResultPage=new RiskEvaluationResultPageObject(driver);
    		Assert.assertTrue(RiskEvaluationResultPage.verifyInthisPage(), "未进入重新风险评测页，重新评测按钮未出现");//需要再判断按钮的文字是否为重新评测，并且按钮可点击
    		RiskEvaluationPageObject RiskEvaluationPage=RiskEvaluationResultPage.reEvaluation();
    		String riskLevel="R3";
    		RiskEvaluationPage.startRiskEvaluation(riskLevel);
    		String expectRiskResult=RiskEvaluationPage.riskLevelToRiskResult(riskLevel);
    		String riskResult=RiskEvaluationResultPage.getRiskResult();
    		RiskEvaluationResultPage.backNativeApp();
    		Assert.assertEquals(riskResult, expectRiskResult, "实际风评结果为"+riskResult+"然而期待结果为"+expectRiskResult);
    	}
    	else{
    		throw new Exception("该账号未评测过，不能重新评测");
    	}
    }
    
    

}
