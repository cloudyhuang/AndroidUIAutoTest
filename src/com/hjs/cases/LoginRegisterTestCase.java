package com.hjs.cases;

import java.time.Duration;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.hjs.config.CommonAppiumPage;
import com.hjs.config.CommonAppiumTest;
import com.hjs.pages.DebugPageObject;
import com.hjs.pages.DebugSettingPageObject;
import com.hjs.pages.DepositProductDetailPageObject;
import com.hjs.pages.DiscoverPageObject;
import com.hjs.pages.FinancialPageObject;
import com.hjs.pages.FindPwdResetLoginPwdPageObject;
import com.hjs.pages.FindPwdVerifyPhonePageObject;
import com.hjs.pages.FindPwdVerifyRealNamePageObject;
import com.hjs.pages.FundDetailPageObject;
import com.hjs.pages.GesturePwd;
import com.hjs.pages.HomePageObject;
import com.hjs.pages.LoginPageObject;
import com.hjs.pages.LoginPwdResetPageObject;
import com.hjs.pages.MinePageObject;
import com.hjs.pages.PersonSettingPageObject;
import com.hjs.pages.PwdSettingPageObject;
import com.hjs.pages.WelcomePageObject;

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
    	FinancialPageObject financialPageObject=homepage.enterFinancialPage();
    	Assert.assertTrue(financialPageObject.verifyInthisPage(),"点击理财未进入理财页");
    	DepositProductDetailPageObject depositProductDetailPage=financialPageObject.clickFirstDepositProduct();
    	Assert.assertTrue(depositProductDetailPage.verifyInthisPage(),"点击产品未进入产品详情页");
    	depositProductDetailPage.clickPayBtn();
    	Assert.assertTrue(loginPage.verifyInFirstLoginPage(),"点击投资未进入登录页面");
    	loginPage.backKeyEvent();
    	loginPage.threadsleep(2000);
    	loginPage.backKeyEvent();
    	Assert.assertTrue(homepage.verifyIsInHomePage(),"点击返回键未返回主页");
    	financialPageObject=homepage.reloadFinancialPage();
    	Assert.assertTrue(financialPageObject.verifyInthisPage(),"点击理财未进入理财页");
    	financialPageObject.clickBannerImage();
    	Assert.assertTrue(loginPage.verifyInFirstLoginPage(),"点击banner未进入登录页面");
    	loginPage.backKeyEvent();
    	FinancialPageObject financialPage=homepage.enterFinancialPage();
    	Assert.assertTrue(financialPage.verifyInthisPage(), "点击首页理财入口，未出现理财页面");
    	FundDetailPageObject fundDetailPage=financialPage.clickFundProduct(FUND_PRODUCT_NAME);
    	Assert.assertTrue(fundDetailPage.verifyInthisPage(), "未进入基金详情页");
    	CommonAppiumPage page=fundDetailPage.purchaseFund();
    	Assert.assertTrue(loginPage.verifyInFirstLoginPage(),"点击banner未进入登录页面");
    	loginPage.backKeyEvent();
    	loginPage.threadsleep(2000);
    	loginPage.backKeyEvent();
    	Assert.assertTrue(homepage.verifyIsInHomePage(),"点击返回键未返回主页");
    	DiscoverPageObject discoverPage=homepage.clickdiscoverEntrance();
    	Assert.assertTrue(discoverPage.verifyInthisPage(),"点击发现按钮未进入发现页面");
    	loginPage=discoverPage.clickLoginEntrance();
    	Assert.assertTrue(loginPage.verifyInFirstLoginPage(),"点击发现页-登录按钮未进入登录页面");
	}
	@Test(description = "登录输入错误密码多次切换账号",priority = 3)
	public void testWrongPwdLoginAndSwitchAccount() throws InterruptedException{
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
       	int timeOutMinute=1;		//设置成1分钟超时
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
}
