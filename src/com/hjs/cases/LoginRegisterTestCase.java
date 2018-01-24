package com.hjs.cases;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.hjs.config.CommonAppiumPage;
import com.hjs.config.CommonAppiumTest;
import com.hjs.pages.AboutPageObject;
import com.hjs.pages.AccountBanlancePageObject;
import com.hjs.pages.DebugPageObject;
import com.hjs.pages.DebugSettingPageObject;
import com.hjs.pages.DiscoverPageObject;
import com.hjs.pages.FindPwdResetLoginPwdPageObject;
import com.hjs.pages.FindPwdVerifyPhonePageObject;
import com.hjs.pages.FindPwdVerifyRealNamePageObject;
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
import com.hjs.pages.RechargePageObject;
import com.hjs.pages.RiskEvaluationPageObject;
import com.hjs.pages.RiskEvaluationResultPageObject;
import com.hjs.pages.SetBankCardPageObject;
import com.hjs.pages.SetTradePwdPageObject;
import com.hjs.pages.SignUpPageObject;
import com.hjs.pages.TradePwdResetPageObject;
import com.hjs.pages.WelcomePageObject;
import com.hjs.publics.Util;
import com.hjs.testDate.Account;

/**
* @author huangxiao
* @version 创建时间：2017年12月14日 上午9:31:49
* 类说明
*/
public class LoginRegisterTestCase extends CommonAppiumTest{
	private static String FUND_PRODUCT_NAME="国寿安保增金宝货币";
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
	@Test(description = "登录入口",priority = 2)
	public void testLoginEntrance() throws Exception{
    	HomePageObject homepage=new HomePageObject(driver); 
    	LoginPageObject loginPage=homepage.clickLoginEntrance();
    	Assert.assertTrue(loginPage.verifyInFirstLoginPage(), "点击首页-登录入口未跳转到登录页");
    	loginPage.backKeyEvent();
    	Assert.assertTrue(homepage.verifyIsInHomePage(),"点击返回键未返回主页");
//    	FinancialPageObject financialPageObject=homepage.enterFinancialPage();
//    	Assert.assertTrue(financialPageObject.verifyInthisPage(),"点击理财未进入理财页");
//    	DepositProductDetailPageObject depositProductDetailPage=financialPageObject.clickFirstDepositProduct();
//    	Assert.assertTrue(depositProductDetailPage.verifyInthisPage(),"点击产品未进入产品详情页");
//    	depositProductDetailPage.clickPayBtn();
//    	Assert.assertTrue(loginPage.verifyInFirstLoginPage(),"点击投资未进入登录页面");
//    	loginPage.backKeyEvent();
//    	loginPage.threadsleep(2000);
//    	loginPage.backKeyEvent();
//    	Assert.assertTrue(homepage.verifyIsInHomePage(),"点击返回键未返回主页");
//    	financialPageObject=homepage.reloadFinancialPage();
//    	Assert.assertTrue(financialPageObject.verifyInthisPage(),"点击理财未进入理财页");
//    	financialPageObject.clickBannerImage();
//    	Assert.assertTrue(loginPage.verifyInFirstLoginPage(),"点击banner未进入登录页面");
//    	loginPage.backKeyEvent();
//    	FinancialPageObject financialPage=homepage.enterFinancialPage();
//    	Assert.assertTrue(financialPage.verifyInthisPage(), "点击首页理财入口，未出现理财页面");
//    	FundDetailPageObject fundDetailPage=financialPage.clickFundProduct(FUND_PRODUCT_NAME);
//    	Assert.assertTrue(fundDetailPage.verifyInthisPage(), "未进入基金详情页");
//    	CommonAppiumPage page=fundDetailPage.purchaseFund();
//    	Assert.assertTrue(loginPage.verifyInFirstLoginPage(),"点击banner未进入登录页面");
//    	loginPage.backKeyEvent();
//    	loginPage.threadsleep(2000);
//    	loginPage.backKeyEvent();
//    	Assert.assertTrue(homepage.verifyIsInHomePage(),"点击返回键未返回主页");
    	DiscoverPageObject discoverPage=homepage.clickdiscoverEntrance();
    	Assert.assertTrue(discoverPage.verifyInthisPage(),"点击发现按钮未进入发现页面");
    	loginPage=discoverPage.clickLoginEntrance();
    	Assert.assertTrue(loginPage.verifyInFirstLoginPage(),"点击发现页-登录按钮未进入登录页面");
	}
	@Test(description = "登录输入错误密码多次切换账号",priority = 3)
	public void testWrongPwdLoginAndSwitchAccount() throws InterruptedException{
		new HomePageObject(driver).backToHomePage();
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("LoginPageObject"), "点击首页-我的入口未跳转到登录页");
    	String phoneNum="17052411184";
    	String wrongPwd="888888";	//错误密码
    	LoginPageObject loginPage=(LoginPageObject)page;
    	String verifyPhoneNumResultPageName=loginPage.verifyPhoneNum(phoneNum).getClass().getName(); //com.hjs.pages.LoginPageObject
    	Assert.assertTrue(verifyPhoneNumResultPageName.contains("LoginPageObject"), "登录账户手机号验证后未进入输入密码页面！");
    	loginPage=loginPage.wrongPwdLoginAndSwitchAccount(wrongPwd);
    	Assert.assertTrue(loginPage.verifyInFirstLoginPage(),"连续密码输错后切换账号未跳转到登录页");
	}
	@Test(description = "登录输入错误密码多次找回密码",priority = 4)
	public void testWrongPwdLoginAndFindPwd() throws Exception{
		new HomePageObject(driver).backToHomePage();
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("LoginPageObject"), "点击首页-我的入口未跳转到登录页");
    	String phoneNum="17052411184";
    	String wrongPwd="888888";	//错误密码
    	String truePwd="hxnearc228";
    	LoginPageObject loginPage=(LoginPageObject)page;
    	String verifyPhoneNumResultPageName=loginPage.verifyPhoneNum(phoneNum).getClass().getName(); //com.hjs.pages.LoginPageObject
    	Assert.assertTrue(verifyPhoneNumResultPageName.contains("LoginPageObject"), "登录账户手机号验证后未进入输入密码页面！");
    	FindPwdVerifyPhonePageObject findPwdVerifyPhonePage=loginPage.wrongPwdLoginAndFindPwd(wrongPwd);
    	Assert.assertTrue(findPwdVerifyPhonePage.verifyInthisPage(),"连续密码输错后找回密码未跳转到找回密码页");
    	FindPwdVerifyRealNamePageObject findPwdVerifyRealNamePage=findPwdVerifyPhonePage.verifyPhone(phoneNum);
    	Assert.assertTrue(findPwdVerifyRealNamePage.verifyInthisPage(),"验证手机号后未跳转到验证实名信息页面");
    	FindPwdResetLoginPwdPageObject findPwdResetLoginPwdPage=findPwdVerifyRealNamePage.verifyRealName();
    	Assert.assertTrue(findPwdResetLoginPwdPage.verifyInthisPage(),"实名后未进入新密码设置页面");
    	loginPage=findPwdResetLoginPwdPage.resetNewLoginPwd(truePwd);
    	
	}
	@Test(description = "登录",priority = 5)
	public void testSignedPhoneLogin() throws InterruptedException{
		new HomePageObject(driver).backToHomePage();
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Reporter.log("进入首页-我的入口");
    	Assert.assertTrue(pageName.contains("LoginPageObject"), "点击首页-我的入口未跳转到登录页");
    	Reporter.log("未登录-跳转到登录页");
    	String phoneNum="17052411184";
    	String password="hxnearc228";
    	LoginPageObject loginPageObject=(LoginPageObject)page;
    	String verifyPhoneNumResultPageName=loginPageObject.verifyPhoneNum(phoneNum).getClass().getName(); //com.hjs.pages.LoginPageObject
    	Assert.assertTrue(verifyPhoneNumResultPageName.contains("LoginPageObject"), "登录账户手机号验证后未进入输入密码页面！");
    	GesturePwd gesturePwd=loginPageObject.login(password);
    	Assert.assertTrue(gesturePwd.verifyInthisPage(),"登录后未跳转到手势密码页");
    	String loginResult=gesturePwd.verifyGestureTips();
    	Assert.assertEquals("请绘制您的手势密码", loginResult);
	}
    @Test(priority = 6,description="测试手势密码")
    public void testGesturePwd()throws InterruptedException{
    	GesturePwd gesturePwd=new GesturePwd(driver);
    	String result=gesturePwd.setFirstGesturePwd(1,4,7,8);
    	Assert.assertEquals("请再画一次手势密码", result);
    	boolean setNextGestureResult=gesturePwd.setNextGesturePwd(1,4,7,8).verifyIsInHomePage();
    	Assert.assertTrue(setNextGestureResult, "第二次设置手势失败，未跳转到主页，主页我的入口未显示");
    	
    }
    @Test(priority = 7,description="手势密码切换其他账号登录")
    public void testSwitchUserAtGesturePwd()throws InterruptedException{
    	new HomePageObject(driver).backToHomePage();
    	GesturePwd gesturePwd=new GesturePwd(driver);
    	Assert.assertTrue(gesturePwd.verifyInthisPage(), "当前未在手势密码页面");
    	LoginPageObject loginPage=gesturePwd.switchUser();
    	Assert.assertTrue(loginPage.verifyInFirstLoginPage(), "手势密码点击其他账号登录未进入登录页面");
    	loginPage.closePage();
    	Assert.assertTrue(gesturePwd.verifyInthisPage(), "关闭其他账号登录后未返回手势密码登录页");
    	gesturePwd.inputGesturePwd(1,4,7,8);
    	HomePageObject homePage=new HomePageObject(driver);
    	Assert.assertTrue(homePage.verifyIsInHomePage(), "输入手密后未进入首页");
    	new HomePageObject(driver).backToHomePage();
    	Assert.assertTrue(gesturePwd.verifyInthisPage(), "当前未在手势密码页面");
    	loginPage=gesturePwd.switchUser();
    	Assert.assertTrue(loginPage.verifyInFirstLoginPage(), "手势密码点击其他账号登录未进入登录页面");
    	String phoneNum="17052411184";
    	String password="hxnearc228";
    	String verifyPhoneNumResultPageName=loginPage.verifyPhoneNum(phoneNum).getClass().getName(); //com.hjs.pages.LoginPageObject
    	Assert.assertTrue(verifyPhoneNumResultPageName.contains("LoginPageObject"), "登录账户手机号验证后未进入输入密码页面！");
    	loginPage.login(password);
    	Assert.assertTrue(homePage.verifyIsInHomePage(), "登录后未进入主页");
    }
    @Test(priority = 8,description="手势密码页忘记密码重设手密")
    public void testForgotPwdAtGesturePwd()throws InterruptedException{
    	new HomePageObject(driver).backToHomePage();
    	GesturePwd gesturePwd=new GesturePwd(driver);
    	Assert.assertTrue(gesturePwd.verifyInthisPage(), "当前未在手势密码页面");
    	LoginPageObject loginPage=gesturePwd.forgotPwd();
    	Assert.assertTrue(loginPage.verifyInthisPage(), "手势密码点击忘记密码未进入登录页面");
    	String password="hxnearc228";
    	gesturePwd=loginPage.login(password);
    	Assert.assertTrue(gesturePwd.verifyInthisPage(),"登录后未跳转到手势密码页");
    	String loginResult=gesturePwd.verifyGestureTips();
    	Assert.assertEquals("请绘制您的手势密码", loginResult);
    	String result=gesturePwd.setFirstGesturePwd(1,4,7,8);
    	Assert.assertEquals("请再画一次手势密码", result);
    	boolean setNextGestureResult=gesturePwd.setNextGesturePwd(1,4,7,8).verifyIsInHomePage();
    	Assert.assertTrue(setNextGestureResult, "第二次设置手势失败，未跳转到主页，主页我的入口未显示");
    	
    }
    @Test(priority = 9,description="重设登录密码验证登录密码错误")
    public void testResetLoginPwdWrongLoginPwd()throws Exception{
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
    	String phoneNum="17052411184";
    	String wrongLoginPwd="123456";
    	String truePwd="hxnearc228";
    	FindPwdVerifyPhonePageObject findPwdVerifyPhonePage=loginPwdResetPage.resetLoginPwdByWrongLoginPwd(wrongLoginPwd);
    	Assert.assertTrue(findPwdVerifyPhonePage.verifyInthisPage(),"连续密码输错后找回密码未跳转到找回密码页");
    	FindPwdVerifyRealNamePageObject findPwdVerifyRealNamePage=findPwdVerifyPhonePage.verifyPhone(phoneNum);
    	Assert.assertTrue(findPwdVerifyRealNamePage.verifyInthisPage(),"验证手机号后未跳转到验证实名信息页面");
    	FindPwdResetLoginPwdPageObject findPwdResetLoginPwdPage=findPwdVerifyRealNamePage.verifyRealName();
    	Assert.assertTrue(findPwdResetLoginPwdPage.verifyInthisPage(),"实名后未进入新密码设置页面");
    	LoginPageObject loginPage=findPwdResetLoginPwdPage.resetNewLoginPwd(truePwd);
    	Assert.assertTrue(loginPage.verifyInthisPage(),"重设新密码后未跳转到登录页面");
    	loginPage.login(truePwd);
    	Assert.assertTrue(homepage.verifyIsInHomePage(),"登录后未跳转到首页");
    }
    @Test(priority = 10,description="关闭打开手密开关")
    public void testCloseAndOpenGestureSwitch()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	PersonSettingPageObject personSettingPage=((MinePageObject)page).enterPersonSetting();
    	Assert.assertTrue(personSettingPage.verifyInthisPage(), "进入我的个人头像，未进入设置页");
    	PwdSettingPageObject pwdSettingPage=personSettingPage.gotoPwdResetPage();
    	Assert.assertTrue(pwdSettingPage.verifyInthisPage(), "点击密码管理未进入密码设置页");
    	String loginPwd="hxnearc228";
    	pwdSettingPage.closeGestureSwitch(loginPwd);
    	GesturePwd gesturePwd=pwdSettingPage.openGestureSwitch(loginPwd);
    	String result=gesturePwd.setFirstGesturePwd(1,4,7,8);
    	Assert.assertEquals("请再画一次手势密码", result);
    	gesturePwd.setNextGesturePwd(1,4,7,8);
    	Assert.assertTrue(pwdSettingPage.verifyInthisPage(), "第二次设置手密后未返回密码设置页面");
    }
    @Test(priority = 11,description="多次输入手密错误")
    public void testMultiWrongGesturePwd()throws Exception{
    	new HomePageObject(driver).backToHomePage();
    	GesturePwd gesturePwd=new GesturePwd(driver);
    	Assert.assertTrue(gesturePwd.verifyInthisPage(), "当前未在手势密码页面");
    	LoginPageObject loginPage=gesturePwd.inputWrongGesturePwd(1,2,3,4);
    	Assert.assertTrue(loginPage.verifyInthisPage(), "多次手密输入错误点击重新登录未进入登录页面");
    	String loginPwd="hxnearc228";
    	gesturePwd=loginPage.login(loginPwd);
    	Assert.assertTrue(gesturePwd.verifyInthisPage(), "重新登录后未进入重设手密页面");
    	String result=gesturePwd.setFirstGesturePwd(1,4,7,8);
    	Assert.assertEquals("请再画一次手势密码", result);
    	boolean setNextGestureResult=gesturePwd.setNextGesturePwd(1,4,7,8).verifyIsInHomePage();
    	Assert.assertTrue(setNextGestureResult, "第二次设置手势失败，未跳转到主页，主页我的入口未显示");
    }
    @Test(description = "登录验证码输入错误",priority = 12,enabled=false)
	public void testWrongCaptcha() throws InterruptedException{
    	new HomePageObject(driver).backToHomePage();
    	GesturePwd gesturePwd=new GesturePwd(driver);
    	Assert.assertTrue(gesturePwd.verifyInthisPage(), "当前未在手势密码页面");
    	LoginPageObject loginPage=gesturePwd.switchUser();
    	Assert.assertTrue(loginPage.verifyInFirstLoginPage(), "手势密码点击其他账号登录未进入登录页面");
    	String phoneNum="17052411184";
    	loginPage.verifyPhoneNumByWrongCaptcha(phoneNum); //验证toast需要uiautomator2支持
	}
    @Test(description = "登录页忘记密码",priority = 13)
	public void testLoginPageForgotPwd() throws Exception{
    	new HomePageObject(driver).backToHomePage();
    	GesturePwd gesturePwd=new GesturePwd(driver);
    	Assert.assertTrue(gesturePwd.verifyInthisPage(), "当前未在手势密码页面");
    	LoginPageObject loginPage=gesturePwd.switchUser();
    	Assert.assertTrue(loginPage.verifyInFirstLoginPage(), "手势密码页-点击其他账号登录未进入->登录页面");
    	String phoneNum="17052411184";
    	String truePwd="hxnearc228";
    	CommonAppiumPage page=loginPage.verifyPhoneNum(phoneNum);
    	String verifyPhoneNumResultPageName=page.getClass().getName(); 
    	Assert.assertTrue(verifyPhoneNumResultPageName.contains("LoginPageObject"), "登录页-验证手机号后未进入->输入密码页面！");
    	FindPwdVerifyPhonePageObject findPwdVerifyPhonePage=loginPage.forgotPwd();
    	Assert.assertTrue(findPwdVerifyPhonePage.verifyInthisPage(),"登录页-点击忘记密码未进入->重设登录密码页");
    	FindPwdVerifyRealNamePageObject findPwdVerifyRealNamePage=findPwdVerifyPhonePage.verifyPhone(phoneNum);
    	Assert.assertTrue(findPwdVerifyRealNamePage.verifyInthisPage(),"重设密码-验证手机号后未跳转到->重设密码-验证实名信息页面");
    	FindPwdResetLoginPwdPageObject findPwdResetLoginPwdPage=findPwdVerifyRealNamePage.verifyRealName();
    	Assert.assertTrue(findPwdResetLoginPwdPage.verifyInthisPage(),"实名后未进入新密码设置页面");
    	findPwdResetLoginPwdPage.resetNewLoginPwd(truePwd);
    	Assert.assertTrue(gesturePwd.verifyInthisPage(),"重设新密码后未跳转到手势密码页");
    	gesturePwd.inputGesturePwd(1,4,7,8);
    	Assert.assertTrue(new HomePageObject(driver).verifyIsInHomePage(),"输入手密后未跳转到首页");
	}
    @Test(description = "有手密放置30分钟",priority = 14)
	public void testTouchEvtTimeoutManager() throws Exception{
    	new HomePageObject(driver).backToHomePage();
    	GesturePwd gesturePwd=new GesturePwd(driver);
    	Assert.assertTrue(gesturePwd.verifyInthisPage(), "当前未在手势密码页面");
    	DebugPageObject debugPage=gesturePwd.gotoDebugPage();
    	Assert.assertTrue(debugPage.verifyInthisPage(),"长按音量键并点击元素未进入debug页面");
    	DebugSettingPageObject debugSettingPage=debugPage.gotoDebugSettingPage();
    	Assert.assertTrue(debugSettingPage.verifyInthisPage(),"点击setting后未进入debug setting页面");
    	int timeOutSeconds=30;		//设置成30秒超时
    	debugSettingPage.changeTouchEvtTimeout(Duration.ofSeconds(timeOutSeconds));
    	Assert.assertTrue(gesturePwd.verifyInthisPage(),"设置debug setting后未回到手势密码页");
    	gesturePwd.inputGesturePwd(1,4,7,8);
//    	HomePageObject homePage=new HomePageObject(driver);
//    	Assert.assertTrue(homePage.verifyIsInHomePage(),"输入手密后未跳转到首页");
    	gesturePwd.threadsleep(timeOutSeconds*1000);
    	Assert.assertTrue(gesturePwd.verifyInthisPage(),timeOutSeconds+"秒后未回到手密页面");
    	debugPage=gesturePwd.gotoDebugPage();
    	Assert.assertTrue(debugPage.verifyInthisPage(),"长按音量键并点击元素未进入debug页面");
    	debugSettingPage=debugPage.gotoDebugSettingPage();
    	Assert.assertTrue(debugSettingPage.verifyInthisPage(),"点击setting后未进入debug setting页面");
    	int deafultTimeOutSeconds=1800;		//设置回默认
    	debugSettingPage.changeTouchEvtTimeout(Duration.ofSeconds(deafultTimeOutSeconds));
    	Assert.assertTrue(gesturePwd.verifyInthisPage(),"设置debug setting后未回到手势密码页");
	}
    @Test(description="无手密放置30分钟",priority = 15)
    public void testTouchEvtTimeoutManagerWithNoGesturePwd()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	GesturePwd gesturePwd=new GesturePwd(driver);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	PersonSettingPageObject personSettingPage=((MinePageObject)page).enterPersonSetting();
    	Assert.assertTrue(personSettingPage.verifyInthisPage(), "进入我的个人头像，未进入设置页");
    	PwdSettingPageObject pwdSettingPage=personSettingPage.gotoPwdResetPage();
    	Assert.assertTrue(pwdSettingPage.verifyInthisPage(), "点击密码管理未进入密码设置页");
    	String loginPwd="hxnearc228";
    	pwdSettingPage.closeGestureSwitch(loginPwd);
    	DebugPageObject debugPage=pwdSettingPage.gotoDebugPage();
    	Assert.assertTrue(debugPage.verifyInthisPage(),"长按音量键并点击元素未进入debug页面");
    	DebugSettingPageObject debugSettingPage=debugPage.gotoDebugSettingPage();
    	Assert.assertTrue(debugSettingPage.verifyInthisPage(),"点击setting后未进入debug setting页面");
    	int timeOutSeconds=30;		//设置成30秒超时
    	debugSettingPage.changeTouchEvtTimeout(Duration.ofSeconds(timeOutSeconds));
    	Assert.assertTrue(pwdSettingPage.verifyInthisPage(),"设置debug setting后未回到密码设置页面");
    	gesturePwd.threadsleep(timeOutSeconds*1000);
    	Assert.assertTrue(homepage.verifyIsInHomePage(),timeOutSeconds+"秒后未回到主页");
    	LoginPageObject loginPage=homepage.clickLoginEntrance();
    	Assert.assertTrue(loginPage.verifyInthisPage(), "点击首页-登录入口未跳转到登录密码输入页");
    	gesturePwd=loginPage.login(loginPwd);
    	Assert.assertTrue(gesturePwd.verifyInthisPage(), "登录后未出现手密设置页");
    	String result=gesturePwd.setFirstGesturePwd(1,4,7,8);
    	Assert.assertEquals("请再画一次手势密码", result);
    	gesturePwd.setNextGesturePwd(1,4,7,8);
    	Assert.assertTrue(homepage.verifyIsInHomePage(), "第二次设置手密后未返回主页");
    	//设置回默认手密待机时间
    	debugPage=homepage.gotoDebugPage();
    	Assert.assertTrue(debugPage.verifyInthisPage(),"长按音量键并点击元素未进入debug页面");
    	debugSettingPage=debugPage.gotoDebugSettingPage();
    	Assert.assertTrue(debugSettingPage.verifyInthisPage(),"点击setting后未进入debug setting页面");
    	int deafultTimeOutSeconds=1800;		//设置回默认
    	debugSettingPage.changeTouchEvtTimeout(Duration.ofSeconds(deafultTimeOutSeconds));
    	Assert.assertTrue(homepage.verifyIsInHomePage(),"设置debug setting后未回到首页");
    }
    @Test(description = "手密失效时间",priority = 16)
   	public void testGstExpriedTimeOut() throws Exception{
       	new HomePageObject(driver).backToHomePage();
       	GesturePwd gesturePwd=new GesturePwd(driver);
       	Assert.assertTrue(gesturePwd.verifyInthisPage(), "当前未在手势密码页面");
       	DebugPageObject debugPage=gesturePwd.gotoDebugPage();
       	Assert.assertTrue(debugPage.verifyInthisPage(),"长按音量键并点击元素未进入debug页面");
       	DebugSettingPageObject debugSettingPage=debugPage.gotoDebugSettingPage();
       	Assert.assertTrue(debugSettingPage.verifyInthisPage(),"点击setting后未进入debug setting页面");
       	int timeOutMinute=2;		//设置成1分钟超时
       	Duration duration=Duration.ofMinutes(timeOutMinute);
       	debugSettingPage.changeGstExpiredTimeOut(duration);
       	Assert.assertTrue(gesturePwd.verifyInthisPage(),"设置debug setting后未回到手势密码页");
       	LoginPageObject loginPage=gesturePwd.waitforGstExperied(duration, 1,4,7,8);
       	Assert.assertTrue(loginPage.verifyInthisPage(),"失效手密重登后未进入登录页面");
       	String pwd="hxnearc228";
       	gesturePwd=loginPage.login(pwd);
       	Assert.assertTrue(gesturePwd.verifyInthisPage(),"手密失效重新登录后未进入手势密码输入页面");
       	String result=gesturePwd.setFirstGesturePwd(1,4,7,8);
    	Assert.assertEquals("请再画一次手势密码", result);
    	HomePageObject homepage=gesturePwd.setNextGesturePwd(1,4,7,8);
    	Assert.assertTrue(homepage.verifyIsInHomePage(), "第二次设置手密后未返回主页");
       	debugPage=homepage.gotoDebugPage();
       	Assert.assertTrue(debugPage.verifyInthisPage(),"长按音量键并点击元素未进入debug页面");
       	debugSettingPage=debugPage.gotoDebugSettingPage();
       	Assert.assertTrue(debugSettingPage.verifyInthisPage(),"点击setting后未进入debug setting页面");
       	int deafultTimeOutMinute=129600;		//设置回默认90天
       	duration=Duration.ofMinutes(deafultTimeOutMinute);
       	debugSettingPage.changeGstExpiredTimeOut(duration);
       	Assert.assertTrue(homepage.verifyIsInHomePage(),"设置debug setting后未回到主页");
   	}
    @Test(priority = 17,description="登出账户")
    public void testLogOut()throws InterruptedException{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Reporter.log("进入首页-我的入口");
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	PersonSettingPageObject personSettingPage=((MinePageObject)page).enterPersonSetting();
    	Assert.assertTrue(personSettingPage.verifyInthisPage(), "进入我的个人头像，未出现安全退出按钮");
    	personSettingPage=personSettingPage.noLogOut();
    	Assert.assertTrue(personSettingPage.verifyInthisPage(), "安全退出弹框提示点击否为回到个人设置页");
    	boolean isLogout=personSettingPage.logOut().verifyInthisPage();
    	Assert.assertTrue(isLogout, "退出后未跳转到登录页");
    }
    @Test(priority = 18,description="注册账号")
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
		String loginPwd="Hd888888";
		Account.setLoginPwd(loginPwd);
		Assert.assertTrue(((SignUpPageObject)page).verifyInthisPage(),"未进入注册页面");
		gesturePwd=((SignUpPageObject)page).registe(phoneNum, loginPwd);
		Assert.assertTrue(gesturePwd.verifyInthisPage(), "设置密码后未跳转到手势密码界面");
		String result=gesturePwd.setFirstGesturePwd(1,4,7,8);
    	Assert.assertEquals("请再画一次手势密码", result);
    	boolean setNextGestureResult=gesturePwd.setNextGesturePwd(1,4,7,8).verifyIsInHomePage();
    	Assert.assertTrue(setNextGestureResult, "第二次设置手势失败，未跳转到主页，主页我的入口未显示");
		Reporter.log("注册成功，注册账号为："+phoneNum+"密码:"+loginPwd);
    }
    @Test(priority = 19,description="实名并设置安全卡")
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
		Account.setSafeBankCardID(bankCardId);
		Account.setSafeBankPhoneNum(phoneNum);
		Account.setTradePwd(tradePwd);
		Assert.assertTrue(setBankCardPageObject.verifyInthisPage(), "未出现银行卡绑卡界面，银行卡号输入框未出现");
		personInfoPage=setBankCardPageObject.setBankCard(bankCardId, phoneNum);
		Assert.assertTrue(personInfoPage.verifyInthisPage(),"绑卡后未跳转到我的个人信息界面");
    }
    @Test(priority = 20,description="绑定普通卡")
    public void testBundNormalBankCard()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	PersonSettingPageObject personSettingPage=((MinePageObject)page).enterPersonSetting();//进入个人设置页
    	Assert.assertTrue(personSettingPage.verifyInthisPage(), "进入我的个人头像，未进入设置页");
    	MyBankCardPageObject myBankCardPage=personSettingPage.gotoMyBankCard();//进入我的银行卡页面
    	Assert.assertTrue(myBankCardPage.verifyInthisPage(), "进入银行卡页面未出现银行卡");
    	List<String> bankCardName=myBankCardPage.getMyBankCardNameList();	//绑定前银行卡列表
    	SetBankCardPageObject setBankCardPageObject=myBankCardPage.addNewBankCard();
    	Assert.assertTrue(setBankCardPageObject.verifyInthisPage(), "未出现银行卡绑卡界面，银行卡号输入框未出现");
    	SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmm");//自定义日期格式
    	String bankCardId="621483"+df.format(new Date());
    	String last4BankCardId=bankCardId.substring(bankCardId.length()-4,bankCardId.length());
    	int randNum = new Random().nextInt(10);//0~9随机数
		String phoneNum=df.format(new Date())+String.valueOf(randNum);
		myBankCardPage=setBankCardPageObject.myBankCardPageGotoSetNormalBankCard(bankCardId, phoneNum);
    	List<String> AftUnBundBankCardName=myBankCardPage.getMyBankCardNameList();	//绑定后银行卡列表
    	boolean flag=false;
    	for(int i=0;i<AftUnBundBankCardName.size();i++){
    		if(Util.getNumInString(AftUnBundBankCardName.get(i)).equals(last4BankCardId)){
    			flag=true;
    		}
    	}
    	Assert.assertTrue(flag,"绑定普通卡后四位为："+last4BankCardId+"当前卡页面未包含此卡");
    	Assert.assertFalse(bankCardName.equals(AftUnBundBankCardName),"绑定前后银行卡列表名称相同");
    	Account.setNormalBankCardID(bankCardId);
    	Account.setNormalBankPhoneNum(phoneNum);
    }
    @Test(priority = 21,description="有普通卡解绑安全卡")
    public void testUnBundBankCard()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	PersonSettingPageObject personSettingPage=((MinePageObject)page).enterPersonSetting();//进入个人设置页
    	Assert.assertTrue(personSettingPage.verifyInthisPage(), "进入我的个人头像，未进入设置页");
    	MyBankCardPageObject myBankCardPage=personSettingPage.gotoMyBankCard();//进入我的银行卡页面
    	Assert.assertTrue(myBankCardPage.verifyInthisPage(), "进入银行卡页面未出现银行卡");
    	List<String> bankCardName=myBankCardPage.getMyBankCardNameList();	//解绑前银行卡列表
		myBankCardPage = myBankCardPage.unbundSafeBankCard(Account.getTradePwd(), Account.getSafeBankPhoneNum());
		Assert.assertTrue(myBankCardPage.verifyInthisPage(), "解绑银行卡后未跳转到我的银行卡页面");
		List<String> AftUnBundBankCardName = myBankCardPage.getMyBankCardNameList(); // 解绑后银行卡列表
		Assert.assertFalse(bankCardName.equals(AftUnBundBankCardName), "解绑前后银行卡列表名称相同");
//    	Assert.assertTrue(personSettingPageObject.verifyInthisPage(), "解绑银行卡后未跳转到个人设置页面");
//    	Thread.sleep(2000); //返回个人设置时银行卡名称会闪现，因此等待一下
//    	String bankCardName=personSettingPageObject.getBankCardName();
//    	Assert.assertEquals(bankCardName, "未设置", "解绑失败，当前银行卡为："+bankCardName);
    	
    }
    @Test(priority = 22,description="解绑普通卡")
    public void testUnBundNormalBankCard()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	PersonSettingPageObject personSettingPage=((MinePageObject)page).enterPersonSetting();//进入个人设置页
    	Assert.assertTrue(personSettingPage.verifyInthisPage(), "进入我的个人头像，未进入设置页");
    	MyBankCardPageObject myBankCardPage=personSettingPage.gotoMyBankCard();//进入我的银行卡页面
    	Assert.assertTrue(myBankCardPage.verifyInthisPage(), "进入银行卡页面未出现银行卡");
    	List<String> bankCardName=myBankCardPage.getMyBankCardNameList();	//解绑前银行卡列表
    	if(bankCardName.size()==1){//若只有1张安全卡，加一张普通卡
    		Reporter.log("当前只有一张安全卡，添加一张普通卡");
    		SetBankCardPageObject setBankCardPageObject=myBankCardPage.addNewBankCard();
        	Assert.assertTrue(setBankCardPageObject.verifyInthisPage(), "未出现银行卡绑卡界面，银行卡号输入框未出现");
        	SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmm");//自定义日期格式
        	String bankCardId="621483"+df.format(new Date());
        	String last4BankCardId=bankCardId.substring(bankCardId.length()-4,bankCardId.length());
        	int randNum = new Random().nextInt(10);//0~9随机数
    		String phoneNum=df.format(new Date())+String.valueOf(randNum);
    		myBankCardPage=setBankCardPageObject.myBankCardPageGotoSetNormalBankCard(bankCardId, phoneNum);
        	List<String> AftUnBundBankCardName=myBankCardPage.getMyBankCardNameList();	//绑定后银行卡列表
        	boolean flag=false;
        	for(int i=0;i<AftUnBundBankCardName.size();i++){
        		if(Util.getNumInString(AftUnBundBankCardName.get(i)).equals(last4BankCardId)){
        			flag=true;
        		}
        	}
        	Assert.assertTrue(flag,"绑定普通卡后四位为："+last4BankCardId+"当前卡页面未包含此卡");
        	Assert.assertFalse(bankCardName.equals(AftUnBundBankCardName),"解绑前后银行卡列表名称相同");
        	Reporter.log("添加普通卡成功");
        	Account.setNormalBankCardID(bankCardId);
        	Account.setNormalBankPhoneNum(phoneNum);
    	}
    	else if(bankCardName.size()<1){
    		throw new Exception("当前无卡！无法解绑");
    	}
    	String last4BankCardId=null;
    	if(!(Account.getNormalBankCardID()==null)){
    		last4BankCardId=Account.getNormalBankCardID().substring(Account.getNormalBankCardID().length()-4,Account.getNormalBankCardID().length());
    	}
    	bankCardName=myBankCardPage.getMyBankCardNameList();	//解绑前银行卡列表
    	myBankCardPage=myBankCardPage.unbundNormalBankCard(Account.getTradePwd(),last4BankCardId);
    	Reporter.log("解绑普通卡。。");
    	Assert.assertTrue(myBankCardPage.verifyInthisPage(), "解绑银行卡后未跳转到我的银行卡页面");
    	List<String> AftUnBundBankCardName=myBankCardPage.getMyBankCardNameList();	//解绑后银行卡列表
    	Assert.assertFalse(bankCardName.equals(AftUnBundBankCardName),"解绑前后银行卡列表名称相同");
    	
    }
    @Test(priority = 23,description="无普通卡解绑安全卡")
    public void testNoNormalCardUnBundSafeBankCard()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	PersonSettingPageObject personSettingPage=((MinePageObject)page).enterPersonSetting();//进入个人设置页
    	Assert.assertTrue(personSettingPage.verifyInthisPage(), "进入我的个人头像，未进入设置页");
    	MyBankCardPageObject myBankCardPage=personSettingPage.gotoMyBankCard();//进入我的银行卡页面
    	Assert.assertTrue(myBankCardPage.verifyInthisPage(), "进入银行卡页面未出现银行卡");
    	List<String> bankCardName=myBankCardPage.getMyBankCardNameList();	//解绑前银行卡列表
		myBankCardPage = myBankCardPage.unbundSafeBankCard(Account.getTradePwd(), Account.getSafeBankPhoneNum());
		Assert.assertTrue(myBankCardPage.verifyInthisPage(), "解绑银行卡后未跳转到我的银行卡页面");
		List<String> AftUnBundBankCardName = myBankCardPage.getMyBankCardNameList(); // 解绑后银行卡列表
		Assert.assertFalse(bankCardName.equals(AftUnBundBankCardName), "解绑前后银行卡列表名称相同");
//    	Assert.assertTrue(personSettingPageObject.verifyInthisPage(), "解绑银行卡后未跳转到个人设置页面");
//    	Thread.sleep(2000); //返回个人设置时银行卡名称会闪现，因此等待一下
//    	String bankCardName=personSettingPageObject.getBankCardName();
//    	Assert.assertEquals(bankCardName, "未设置", "解绑失败，当前银行卡为："+bankCardName);
    	
    }
	@Test(priority = 24, description = "充值页绑定安全卡")
	public void testRechargePageBundSafeBankCard() throws Exception {
		new HomePageObject(driver).backToHomePage(1, 4, 7, 8);
		HomePageObject homepage = new HomePageObject(driver);
		CommonAppiumPage page = homepage.enterPersonEntrace();
		String pageName = page.getClass().getName();
		Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
		AccountBanlancePageObject accountBanlancePage = ((MinePageObject) page).enterAccountBanlancePage();
		Assert.assertTrue(accountBanlancePage.verifyInthisPage(), "点击账户余额未进入余额页");
		if(Account.getSafeBankCardID()==null){
			SetBankCardPageObject setBankCardPageObject=accountBanlancePage.rechargeToSetBankCardPage();
			Assert.assertTrue(setBankCardPageObject.verifyInthisPage(), "未出现银行卡绑卡界面，银行卡号输入框未出现");
	    	SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmm");//自定义日期格式
	    	String bankCardId="621483"+df.format(new Date());
	    	int randNum = new Random().nextInt(10);//0~9随机数
			String phoneNum=df.format(new Date())+String.valueOf(randNum);
			RechargePageObject rechargePage=setBankCardPageObject.rechargePageGotoSetSafeBankCard(bankCardId, phoneNum);
			Assert.assertTrue(rechargePage.verifyInthisPage(), "绑定安全卡后未跳转到余额充值页面");
	    	Account.setSafeBankCardID(bankCardId);
	    	Account.setSafeBankPhoneNum(phoneNum);
		}
		else{
			RechargePageObject rechargePage=accountBanlancePage.gotoRechargePage();
	    	Assert.assertTrue(rechargePage.verifyInthisPage(), "点击充值未进入到充值页面");
			Assert.assertTrue(false,"当前存在安全卡，无法绑定");
		}

	}
  
    @Test(priority = 25,description="重设交易密码")
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
    	tradePwdResetPage.resetTradePwd(Account.getRealName(), Account.getIdno(), Account.getSafeBankCardID(), newTradePwd);
    	Account.setTradePwd(newTradePwd);
    	
    }
    @Test(priority = 26,description="重设登录密码")
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
    	loginPwdResetPage.resetLoginPwd(Account.getLoginPwd(), "Hd666666");
    	Account.setLoginPwd("Hd666666");
    }
    @Test(priority = 27,description="用新密码重新登录")
    public void testReLoginWithNewPwd()throws Exception{
    	new HomePageObject(driver).backToHomePage();
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("LoginPageObject"), "点击首页-我的入口未跳转到登录页");
    	LoginPageObject loginPageObject=(LoginPageObject)page;
    	GesturePwd gesturePwd=loginPageObject.login(Account.getLoginPwd());
    	Assert.assertTrue(gesturePwd.verifyInthisPage(),"登录后未跳转到手势密码页");
    	Assert.assertEquals("请绘制您的手势密码", gesturePwd.verifyGestureTips(),"登录后未进入设置手密页");
    	String result=gesturePwd.setFirstGesturePwd(1,4,7,8);
    	Assert.assertEquals("请再画一次手势密码", result);
    	boolean setNextGestureResult=gesturePwd.setNextGesturePwd(1,4,7,8).verifyIsInHomePage();
    	Assert.assertTrue(setNextGestureResult, "第二次设置手势失败，未跳转到主页，主页我的入口未显示");
    }
    @Test(priority = 28,description="风险评测")
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
    		//RiskEvaluationPageObject RiskEvaluationPage=new RiskEvaluationPageObject(driver);
    		Assert.assertTrue(((RiskEvaluationPageObject)page).verifyInthisPage(), "未进入风险评测页，开始评测按钮未出现");
    		String riskLevel="R4";
    		RiskEvaluationResultPageObject RiskEvaluationResultPage=((RiskEvaluationPageObject)page).startRiskEvaluation(riskLevel);
    		String expectRiskResult=((RiskEvaluationPageObject)page).riskLevelToRiskResult(riskLevel);
    		String riskResult=RiskEvaluationResultPage.getRiskResult();
    		RiskEvaluationResultPage.backNativeApp();
    		Assert.assertEquals(riskResult, expectRiskResult, "实际风评结果为"+riskResult+"然而期待结果为"+expectRiskResult);
    	}
    	else{
    		throw new Exception("该账号已评测过！");
    	}
    }
    @Test(priority = 29,description="重新风险评测")
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
    		//RiskEvaluationResultPageObject RiskEvaluationResultPage=new RiskEvaluationResultPageObject(driver);
    		Assert.assertTrue(((RiskEvaluationResultPageObject)page).verifyInthisPage(), "未进入重新风险评测页，重新评测按钮未出现");//需要再判断按钮的文字是否为重新评测，并且按钮可点击
    		RiskEvaluationPageObject RiskEvaluationPage=((RiskEvaluationResultPageObject)page).reEvaluation();
    		String riskLevel="R3";
    		RiskEvaluationPage.startRiskEvaluation(riskLevel);
    		String expectRiskResult=RiskEvaluationPage.riskLevelToRiskResult(riskLevel);
    		String riskResult=((RiskEvaluationResultPageObject)page).getRiskResult();
    		((RiskEvaluationResultPageObject)page).backNativeApp();
    		Assert.assertEquals(riskResult, expectRiskResult, "实际风评结果为"+riskResult+"然而期待结果为"+expectRiskResult);
    	}
    	else{
    		throw new Exception("该账号未评测过，不能重新评测");
    	}
    }
    @Test(priority = 30,description="跳转关于页")
    public void testGotoAboutPage()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	PersonSettingPageObject personSettingPage=((MinePageObject)page).enterPersonSetting();
    	Assert.assertTrue(personSettingPage.verifyInthisPage(), "进入我的个人头像，未进入设置页"); 
    	AboutPageObject aboutPage=personSettingPage.gotoAboutPage();
    	Assert.assertTrue(aboutPage.verifyInthisPage(), "点击关于未进入关于页面"); 
    	aboutPage.isHaveNewAppVersion();
    	
    }
}
