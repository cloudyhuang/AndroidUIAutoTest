package com.hjs.cases;


import java.io.File;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.hjs.Interface.InitProduct;
import com.hjs.config.CommonAppiumPage;
import com.hjs.config.CommonAppiumTest;
import com.hjs.pages.AccountBanlancePageObject;
import com.hjs.pages.CurrentDepositProductDetailPageObject;
import com.hjs.pages.DepositProductDetailPageObject;
import com.hjs.pages.FinancialPageObject;
import com.hjs.pages.GesturePwd;
import com.hjs.pages.HomePageObject;
import com.hjs.pages.InvestPageObject;
import com.hjs.pages.InvestResultPageObject;
import com.hjs.pages.LoginPageObject;
import com.hjs.pages.MinePageObject;
import com.hjs.pages.MyDepositeProductDetailPageObject;
import com.hjs.pages.MyDepositeProductPageObject;
import com.hjs.pages.PayPageObject;
import com.hjs.pages.PersonSettingPageObject;
import com.hjs.pages.RechargePageObject;
import com.hjs.pages.RechargeResultPageObject;
import com.hjs.pages.RedeemPageObject;
import com.hjs.pages.RedeemResultPageObject;
import com.hjs.pages.WelcomePageObject;
import com.hjs.pages.WithdrawCashPageObject;
import com.hjs.pages.WithdrawCashResultPageObject;
import com.hjs.publics.Util;


public class TradeTestCase extends CommonAppiumTest{
	private static String DEPOSITE_PRODUCT_NAME="黄XAutoTest产品170727002201";
	private static String CURRENTDEPOSITE_PRODUCT_NAME="恒存金-灵活理财";
    @Test(priority = 1,description="理财页产品信息")
	public void test1() throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	PersonSettingPageObject personSettingPage=((MinePageObject)page).enterPersonSetting();
    	Assert.assertTrue(personSettingPage.verifyInthisPage(), "进入我的个人头像，未出现安全退出按钮");
    	LoginPageObject loginPageObject=personSettingPage.logOut();
    	Assert.assertTrue(loginPageObject.verifyInthisPage(), "退出后未跳转到登录页面");
    	String phoneNum="17052411184";
    	page=loginPageObject.switchAccount(phoneNum);
		pageName=page.getClass().getName();
		Assert.assertTrue(pageName.contains("LoginPageObject"), "验证手机号后未进入登录页面");
		GesturePwd gesturePwd=((LoginPageObject)page).login("hxnearc228");
		String result=gesturePwd.setFirstGesturePwd(1,4,7,8);
    	Assert.assertEquals("请再画一次手势密码", result);
    	boolean setNextGestureResult=gesturePwd.setNextGesturePwd(1,4,7,8).verifyIsInHomePage();
    	Assert.assertTrue(setNextGestureResult, "第二次设置手势失败，未跳转到主页，主页我的入口未显示");
    	FinancialPageObject FinancialPage=homepage.enterFinancialPage();
    	Assert.assertTrue(FinancialPage.verifyInthisPage(), "点击首页理财入口，未出现理财页面");
    	DEPOSITE_PRODUCT_NAME=FinancialPage.testProductInfo();
	}
    @Test(priority = 2,description="银行卡不带券购买定期产品")
    public void test2() throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	FinancialPageObject FinancialPage=homepage.enterFinancialPage();
    	Assert.assertTrue(FinancialPage.verifyInthisPage(), "点击首页理财入口，未出现理财页面");
    	DepositProductDetailPageObject depositProductDetailPage=FinancialPage.clickDepositProduct(DEPOSITE_PRODUCT_NAME);
    	Assert.assertTrue(depositProductDetailPage.verifyInthisPage(), "理财页点击定期产品后未出现定期理财产品详情页");
    	String startMonney=depositProductDetailPage.getStartMoney();
    	InvestPageObject investPage=depositProductDetailPage.clickPayBtn();
    	Assert.assertTrue(investPage.verifyInthisPage(), "点击立即投资后未进入到投资页");
    	PayPageObject payPage=investPage.invest(startMonney);
    	Assert.assertTrue(payPage.verifyInthisPage(), "点击确认投资未跳转到支付页面");
    	InvestResultPageObject investResultPage=payPage.payByBankCardWithoutCoupon("123456", "17052411227");
    	Assert.assertTrue(investResultPage.verifyInthisPage(), "支付后未跳转到支付结果页面");
    	String investResult=investResultPage.getInvestResult();
    	Assert.assertTrue(investResult.equals("投资申请已受理")||investResult.contains("已提交"), "投资失败，投资结果为:"+investResult);
    }
    @Test(priority = 3,description="银行卡现金券购买定期产品")
    public void test3() throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	FinancialPageObject FinancialPage=homepage.enterFinancialPage();
    	Assert.assertTrue(FinancialPage.verifyInthisPage(), "点击首页理财入口，未出现理财页面");
    	DepositProductDetailPageObject depositProductDetailPage=FinancialPage.clickDepositProduct(DEPOSITE_PRODUCT_NAME);
    	Assert.assertTrue(depositProductDetailPage.verifyInthisPage(), "理财页点击定期产品后未出现定期理财产品详情页");
    	String startMonney=depositProductDetailPage.getStartMoney();
    	InvestPageObject investPage=depositProductDetailPage.clickPayBtn();
    	Assert.assertTrue(investPage.verifyInthisPage(), "点击立即投资后未进入到投资页");
    	PayPageObject payPage=new PayPageObject(driver);
    	String couponId="2463";
    	Assert.assertTrue(payPage.addCouponToUser("17052411184", couponId), "加券失败");
    	investPage.invest(startMonney);
    	Assert.assertTrue(payPage.verifyInthisPage(), "点击确认投资未跳转到支付页面");
    	InvestResultPageObject investResultPage=payPage.payByBankCardWithCoupon("123456", "17052411227",couponId);
    	Assert.assertTrue(investResultPage.verifyInthisPage(), "支付后未跳转到支付结果页面");
    	String investResult=investResultPage.getInvestResult();
    	Assert.assertTrue(investResult.equals("投资申请已受理")||investResult.contains("已提交"), "投资失败，投资结果为:"+investResult);
    }
    @Test(priority = 4,description="银行卡加息券购买定期理财产品")
    public void test4() throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	FinancialPageObject FinancialPage=homepage.enterFinancialPage();
    	Assert.assertTrue(FinancialPage.verifyInthisPage(), "点击首页理财入口，未出现理财页面");
    	DepositProductDetailPageObject depositProductDetailPage=FinancialPage.clickDepositProduct(DEPOSITE_PRODUCT_NAME);
    	Assert.assertTrue(depositProductDetailPage.verifyInthisPage(), "理财页点击定期产品后未出现定期理财产品详情页");
    	String startMonney=depositProductDetailPage.getStartMoney();
    	InvestPageObject investPage=depositProductDetailPage.clickPayBtn();
    	Assert.assertTrue(investPage.verifyInthisPage(), "点击立即投资后未进入到投资页");
    	PayPageObject payPage=new PayPageObject(driver);
    	String couponId="2465";
    	Assert.assertTrue(payPage.addCouponToUser("17052411184", couponId), "加券失败");
    	investPage.invest(startMonney);
    	Assert.assertTrue(payPage.verifyInthisPage(), "点击确认投资未跳转到支付页面");
    	InvestResultPageObject investResultPage=payPage.payByBankCardWithCoupon("123456", "17052411227",couponId);
    	Assert.assertTrue(investResultPage.verifyInthisPage(), "支付后未跳转到支付结果页面");
    	String investResult=investResultPage.getInvestResult();
    	Assert.assertTrue(investResult.equals("投资申请已受理")||investResult.contains("已提交"), "投资失败，投资结果为:"+investResult);
    }
    @Test(priority = 5,description="银行卡不带券无短信验证码购买定期产品")
    public void test5() throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	FinancialPageObject FinancialPage=homepage.enterFinancialPage();
    	Assert.assertTrue(FinancialPage.verifyInthisPage(), "点击首页理财入口，未出现理财页面");
    	DepositProductDetailPageObject depositProductDetailPage=FinancialPage.clickDepositProduct(DEPOSITE_PRODUCT_NAME);
    	Assert.assertTrue(depositProductDetailPage.verifyInthisPage(), "理财页点击定期产品后未出现定期理财产品详情页");
    	String startMonney=depositProductDetailPage.getStartMoney();
    	InvestPageObject investPage=depositProductDetailPage.clickPayBtn();
    	Assert.assertTrue(investPage.verifyInthisPage(), "点击立即投资后未进入到投资页");
    	PayPageObject payPage=investPage.invest(startMonney);
    	Assert.assertTrue(payPage.verifyInthisPage(), "点击确认投资未跳转到支付页面");
    	InvestResultPageObject investResultPage=payPage.payByBankCardWithoutCouponWithoutSmsCode("123456", "17052411227");
    	Assert.assertTrue(investResultPage.verifyInthisPage(), "支付后未跳转到支付结果页面");
    	String investResult=investResultPage.getInvestResult();
    	Assert.assertTrue(investResult.equals("投资申请已受理")||investResult.contains("已提交"), "投资失败，投资结果为:"+investResult);
    }
    @Test(priority = 6,description="银行卡现金券无验证码购买定期产品")
    public void test6() throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	FinancialPageObject FinancialPage=homepage.enterFinancialPage();
    	Assert.assertTrue(FinancialPage.verifyInthisPage(), "点击首页理财入口，未出现理财页面");
    	DepositProductDetailPageObject depositProductDetailPage=FinancialPage.clickDepositProduct(DEPOSITE_PRODUCT_NAME);
    	Assert.assertTrue(depositProductDetailPage.verifyInthisPage(), "理财页点击定期产品后未出现定期理财产品详情页");
    	String startMonney=depositProductDetailPage.getStartMoney();
    	InvestPageObject investPage=depositProductDetailPage.clickPayBtn();
    	Assert.assertTrue(investPage.verifyInthisPage(), "点击立即投资后未进入到投资页");
    	PayPageObject payPage=new PayPageObject(driver);
    	String couponId="2463";
    	Assert.assertTrue(payPage.addCouponToUser("17052411184", couponId), "加券失败");
    	investPage.invest(startMonney);
    	Assert.assertTrue(payPage.verifyInthisPage(), "点击确认投资未跳转到支付页面");
    	InvestResultPageObject investResultPage=payPage.payByBankCardWithCouponWithoutSmsCode("123456", "17052411227",couponId);
    	Assert.assertTrue(investResultPage.verifyInthisPage(), "支付后未跳转到支付结果页面");
    	String investResult=investResultPage.getInvestResult();
    	Assert.assertTrue(investResult.equals("投资申请已受理")||investResult.contains("已提交"), "投资失败，投资结果为:"+investResult);
    }
    @Test(priority = 7,description="银行卡加息券无验证码购买定期产品")
    public void test7() throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	FinancialPageObject FinancialPage=homepage.enterFinancialPage();
    	Assert.assertTrue(FinancialPage.verifyInthisPage(), "点击首页理财入口，未出现理财页面");
    	DepositProductDetailPageObject depositProductDetailPage=FinancialPage.clickDepositProduct(DEPOSITE_PRODUCT_NAME);
    	Assert.assertTrue(depositProductDetailPage.verifyInthisPage(), "理财页点击定期产品后未出现定期理财产品详情页");
    	String startMonney=depositProductDetailPage.getStartMoney();
    	InvestPageObject investPage=depositProductDetailPage.clickPayBtn();
    	Assert.assertTrue(investPage.verifyInthisPage(), "点击立即投资后未进入到投资页");
    	PayPageObject payPage=new PayPageObject(driver);
    	String couponId="2465";
    	Assert.assertTrue(payPage.addCouponToUser("17052411184", couponId), "加券失败");
    	investPage.invest(startMonney);
    	Assert.assertTrue(payPage.verifyInthisPage(), "点击确认投资未跳转到支付页面");
    	InvestResultPageObject investResultPage=payPage.payByBankCardWithCouponWithoutSmsCode("123456", "17052411227",couponId);
    	Assert.assertTrue(investResultPage.verifyInthisPage(), "支付后未跳转到支付结果页面");
    	String investResult=investResultPage.getInvestResult();
    	Assert.assertTrue(investResult.equals("投资申请已受理")||investResult.contains("已提交"), "投资失败，投资结果为:"+investResult);
    }
    @Test(priority = 8,description="充值")
    public void test8()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	AccountBanlancePageObject accountBanlancePage=((MinePageObject)page).enterAccountBanlancePage();
    	Assert.assertTrue(accountBanlancePage.verifyInthisPage(), "点击账户余额未进入余额页");
    	int currentCash=accountBanlancePage.getAvailableBalance();
    	int rechargeAmount=10000;
    	int expectCash=currentCash+rechargeAmount;
    	String stringRechargeAmount=String.valueOf(rechargeAmount);
    	RechargePageObject rechargePage=accountBanlancePage.gotoRechargePage();
    	Assert.assertTrue(rechargePage.verifyInthisPage(), "点击充值未进入到充值页面");
    	RechargeResultPageObject rechargeResultPage=rechargePage.recharge(stringRechargeAmount, "123456", "17052411227");
    	Assert.assertTrue(rechargeResultPage.verifyInthisPage(), "充值输入验证码后未跳转到充值结果页");
    	Assert.assertTrue(rechargeResultPage.getRechargeResult().contains("成功充值"), "充值申请失败，失败信息为："+rechargeResultPage.getRechargeResult());
    	accountBanlancePage=rechargeResultPage.gotoBalancePage();
    	int actualCash=accountBanlancePage.getAvailableBalance();
    	Assert.assertEquals(actualCash, expectCash,"实际剩余余额为"+actualCash+"预期剩余余额为"+expectCash);

    }
    @Test(priority = 9,description="余额不带券购买定期产品")
    public void test9() throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	FinancialPageObject FinancialPage=homepage.enterFinancialPage();
    	Assert.assertTrue(FinancialPage.verifyInthisPage(), "点击首页理财入口，未出现理财页面");
    	DepositProductDetailPageObject depositProductDetailPage=FinancialPage.clickDepositProduct(DEPOSITE_PRODUCT_NAME);
    	Assert.assertTrue(depositProductDetailPage.verifyInthisPage(), "理财页点击定期产品后未出现定期理财产品详情页");
    	String startMonney=depositProductDetailPage.getStartMoney();
    	InvestPageObject investPage=depositProductDetailPage.clickPayBtn();
    	Assert.assertTrue(investPage.verifyInthisPage(), "点击立即投资后未进入到投资页");
    	PayPageObject payPage=new PayPageObject(driver);
    	investPage.invest(startMonney);
    	Assert.assertTrue(payPage.verifyInthisPage(), "点击确认投资未跳转到支付页面");
    	InvestResultPageObject investResultPage=payPage.payByBanlanceWithoutCoupon("123456", "17052411227");
    	Assert.assertTrue(investResultPage.verifyInthisPage(), "支付后未跳转到支付结果页面");
    	String investResult=investResultPage.getInvestResult();
    	Assert.assertTrue(investResult.equals("投资申请已受理")||investResult.contains("已提交"), "投资失败，投资结果为:"+investResult);
 
    }
    @Test(priority = 10,description="余额现金券购买定期产品")
    public void test10() throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	FinancialPageObject FinancialPage=homepage.enterFinancialPage();
    	Assert.assertTrue(FinancialPage.verifyInthisPage(), "点击首页理财入口，未出现理财页面");
    	DepositProductDetailPageObject depositProductDetailPage=FinancialPage.clickDepositProduct(DEPOSITE_PRODUCT_NAME);
    	Assert.assertTrue(depositProductDetailPage.verifyInthisPage(), "理财页点击定期产品后未出现定期理财产品详情页");
    	String startMonney=depositProductDetailPage.getStartMoney();
    	InvestPageObject investPage=depositProductDetailPage.clickPayBtn();
    	Assert.assertTrue(investPage.verifyInthisPage(), "点击立即投资后未进入到投资页");
    	PayPageObject payPage=new PayPageObject(driver);
    	String couponId="2463";
    	Assert.assertTrue(payPage.addCouponToUser("17052411184", couponId), "加券失败");
    	investPage.invest(startMonney);
    	Assert.assertTrue(payPage.verifyInthisPage(), "点击确认投资未跳转到支付页面");
    	InvestResultPageObject investResultPage=payPage.payByBanlanceWithCoupon("123456", "17052411227", couponId);
    	Assert.assertTrue(investResultPage.verifyInthisPage(), "支付后未跳转到支付结果页面");
    	String investResult=investResultPage.getInvestResult();
    	Assert.assertTrue(investResult.equals("投资申请已受理")||investResult.contains("已提交"), "投资失败，投资结果为:"+investResult);

    }
    @Test(priority = 11,description="余额加息券购买定期产品")
    public void test11() throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	FinancialPageObject FinancialPage=homepage.enterFinancialPage();
    	Assert.assertTrue(FinancialPage.verifyInthisPage(), "点击首页理财入口，未出现理财页面");
    	DepositProductDetailPageObject depositProductDetailPage=FinancialPage.clickDepositProduct(DEPOSITE_PRODUCT_NAME);
    	Assert.assertTrue(depositProductDetailPage.verifyInthisPage(), "理财页点击定期产品后未出现定期理财产品详情页");
    	String startMonney=depositProductDetailPage.getStartMoney();
    	InvestPageObject investPage=depositProductDetailPage.clickPayBtn();
    	Assert.assertTrue(investPage.verifyInthisPage(), "点击立即投资后未进入到投资页");
    	PayPageObject payPage=new PayPageObject(driver);
    	String couponId="2465";
    	Assert.assertTrue(payPage.addCouponToUser("17052411184", couponId), "加券失败");
    	investPage.invest(startMonney);
    	Assert.assertTrue(payPage.verifyInthisPage(), "点击确认投资未跳转到支付页面");
    	InvestResultPageObject investResultPage=payPage.payByBanlanceWithCoupon("123456", "17052411227", couponId);
    	Assert.assertTrue(investResultPage.verifyInthisPage(), "支付后未跳转到支付结果页面");
    	String investResult=investResultPage.getInvestResult();
    	Assert.assertTrue(investResult.equals("投资申请已受理")||investResult.contains("已提交"), "投资失败，投资结果为:"+investResult);

    }
    @Test(priority = 12,description="余额不带券购买活期产品")
    public void test12() throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	FinancialPageObject FinancialPage=homepage.enterFinancialPage();
    	Assert.assertTrue(FinancialPage.verifyInthisPage(), "点击首页理财入口，未出现理财页面");
    	CurrentDepositProductDetailPageObject currentDepositProductDetailPage=FinancialPage.clickCurrentDepositProduct(CURRENTDEPOSITE_PRODUCT_NAME);
    	Assert.assertTrue(currentDepositProductDetailPage.verifyInthisPage(), "理财页点击活期产品后未出现活期理财产品详情页");
    	String startMonney="100";
    	InvestPageObject investPage=currentDepositProductDetailPage.clickPayBtn();
    	Assert.assertTrue(investPage.verifyInthisPage(), "点击立即投资后未进入到投资页");
    	PayPageObject payPage=new PayPageObject(driver);
    	investPage.invest(startMonney);
    	Assert.assertTrue(payPage.verifyInthisPage(), "点击确认投资未跳转到支付页面");
    	InvestResultPageObject investResultPage=payPage.payByBanlanceWithoutCoupon("123456", "17052411227");
    	Assert.assertTrue(investResultPage.verifyInthisPage(), "支付后未跳转到支付结果页面");
    	String investResult=investResultPage.getInvestResult();
    	Assert.assertTrue(investResult.equals("投资已成功")||investResult.contains("已提交"), "投资失败，投资结果为:"+investResult);
    }
    @Test(priority = 13,description="银行卡不带券购买活期产品")
    public void test13() throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	FinancialPageObject FinancialPage=homepage.enterFinancialPage();
    	Assert.assertTrue(FinancialPage.verifyInthisPage(), "点击首页理财入口，未出现理财页面");
    	CurrentDepositProductDetailPageObject currentDepositProductDetailPage=FinancialPage.clickCurrentDepositProduct(CURRENTDEPOSITE_PRODUCT_NAME);
    	Assert.assertTrue(currentDepositProductDetailPage.verifyInthisPage(), "理财页点击活期产品后未出现活期理财产品详情页");
    	String startMonney="100";
    	InvestPageObject investPage=currentDepositProductDetailPage.clickPayBtn();
    	Assert.assertTrue(investPage.verifyInthisPage(), "点击立即投资后未进入到投资页");
    	PayPageObject payPage=new PayPageObject(driver);
    	investPage.invest(startMonney);
    	Assert.assertTrue(payPage.verifyInthisPage(), "点击确认投资未跳转到支付页面");
    	InvestResultPageObject investResultPage=payPage.payByBankCardWithoutCoupon("123456", "17052411227");
    	Assert.assertTrue(investResultPage.verifyInthisPage(), "支付后未跳转到支付结果页面");
    	String investResult=investResultPage.getInvestResult();
    	Assert.assertTrue(investResult.equals("投资已成功")||investResult.contains("已提交"), "投资失败，投资结果为:"+investResult);
    }
    @Test(priority = 14,description="银行卡不带券购买定期产品失败场景")
    public void test14() throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	FinancialPageObject FinancialPage=homepage.enterFinancialPage();
    	Assert.assertTrue(FinancialPage.verifyInthisPage(), "点击首页理财入口，未出现理财页面");
    	DepositProductDetailPageObject depositProductDetailPage=FinancialPage.clickDepositProduct(DEPOSITE_PRODUCT_NAME);
    	Assert.assertTrue(depositProductDetailPage.verifyInthisPage(), "理财页点击定期产品后未出现定期理财产品详情页");
    	String startMonney=depositProductDetailPage.getStartMoney();
    	InvestPageObject investPage=depositProductDetailPage.clickPayBtn();
    	Assert.assertTrue(investPage.verifyInthisPage(), "点击立即投资后未进入到投资页");
    	PayPageObject payPage=investPage.invest(startMonney);
    	Assert.assertTrue(payPage.verifyInthisPage(), "点击确认投资未跳转到支付页面");
    	InvestResultPageObject investResultPage=payPage.payByBankCardFailWithoutCoupon("123456", "17052411227");
    	Assert.assertTrue(investResultPage.verifyInthisPage(), "支付后未跳转到支付结果页面");
    	String investResult=investResultPage.getInvestResult();
    	Assert.assertTrue(investResult.equals("交易失败"),"未跳转到投资失败页面，页面信息为:"+investResult);

    }
    @Test(priority = 15,expectedExceptions = Exception.class, expectedExceptionsMessageRegExp="找不到下架产品",description="下架产品")
    public void test15() throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	FinancialPageObject financialPage=homepage.enterFinancialPage();
    	Assert.assertTrue(financialPage.verifyInthisPage(), "点击首页理财入口，未出现理财页面");
    	financialPage.productPullOffAndFindProduct(DEPOSITE_PRODUCT_NAME);


    }
    @Test(priority = 16,description="提现")
    public void test16()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	AccountBanlancePageObject accountBanlancePage=((MinePageObject)page).enterAccountBanlancePage();
    	Assert.assertTrue(accountBanlancePage.verifyInthisPage(), "点击账户余额未进入余额页");
    	int maxWithdrawCash=accountBanlancePage.getAvailableBalance();
    	int halfWithdrawCash=maxWithdrawCash/2;
    	int expectCash=maxWithdrawCash-halfWithdrawCash;
    	String stringHalfWithdrawCash=String.valueOf(halfWithdrawCash);
    	WithdrawCashPageObject withdrawCashPage=accountBanlancePage.gotoWithdrawCashPage();
    	Assert.assertTrue(withdrawCashPage.verifyInthisPage(), "点击提现未进入到提现页面");
    	if(withdrawCashPage.getWithdrawCount()>0){
	    	WithdrawCashResultPageObject withdrawCashResultPage=withdrawCashPage.withdrawCash(stringHalfWithdrawCash, "123456");
	    	Assert.assertTrue(withdrawCashResultPage.verifyInthisPage(), "提现输入密码后未跳转到提现结果页");
	    	Assert.assertTrue(withdrawCashResultPage.getWithdrawCashResult().contains("申请成功"), "提现申请失败，失败信息为："+withdrawCashResultPage.getWithdrawCashResult());
	    	accountBanlancePage=withdrawCashResultPage.gotoBalancePage();
	    	int actualCash=accountBanlancePage.getAvailableBalance();
	    	Assert.assertEquals(actualCash, expectCash,"实际剩余余额为"+actualCash+"预期剩余余额为"+expectCash);
    	}
    	else{
    		Reporter.log("注意：当前提现次数为0！");
    	}
    }
    @Test(priority = 17,description="我的定期产品信息")
    public void test17()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	MyDepositeProductPageObject myDepositeProductPage=((MinePageObject)page).enterMyDepositeProductPage();
    	Assert.assertTrue(myDepositeProductPage.verifyInthisPage(), "点击定期理财未进入我的理财产品页");
    	MyDepositeProductDetailPageObject myDepositeProductDetailPage=myDepositeProductPage.enterDepositeProductDetail(DEPOSITE_PRODUCT_NAME);
    	Assert.assertTrue(myDepositeProductDetailPage.verifyInthisPage(), "点击"+DEPOSITE_PRODUCT_NAME+"产品未进入我的理财产品详情页");
    	myDepositeProductDetailPage.assertMtpAndAppDetail("17052411184");
    }

    /*
     * 请把赎回放最后一个用例*/
    @Test(priority = 40,description="赎回活期产品到余额")
    public void test40() throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	PersonSettingPageObject personSettingPage=((MinePageObject)page).enterPersonSetting();
    	Assert.assertTrue(personSettingPage.verifyInthisPage(), "进入我的个人头像，未出现安全退出按钮");
    	LoginPageObject loginPageObject=personSettingPage.logOut();
    	Assert.assertTrue(loginPageObject.verifyInthisPage(), "退出后未跳转到登录页面");
    	String phoneNum="15021808695";
    	page=loginPageObject.switchAccount(phoneNum);
		pageName=page.getClass().getName();
		Assert.assertTrue(pageName.contains("LoginPageObject"), "验证手机号后未进入登录页面");
		GesturePwd gesturePwd=((LoginPageObject)page).login("a123456");
		homepage=gesturePwd.skipGesturePwd();	//跳过手势密码，无需再次测试
		Assert.assertTrue(homepage.verifyIsInHomePage(), "跳过手势密码设置后未进入主页");
    	FinancialPageObject FinancialPage=homepage.enterFinancialPage();
    	Assert.assertTrue(FinancialPage.verifyInthisPage(), "点击首页理财入口，未出现理财页面");
    	CurrentDepositProductDetailPageObject currentDepositProductDetailPage=FinancialPage.clickCurrentDepositProduct(CURRENTDEPOSITE_PRODUCT_NAME);
    	Assert.assertTrue(currentDepositProductDetailPage.verifyInthisPage(), "理财页点击活期产品后未出现活期理财产品详情页");
    	String redeemAmount="1";
    	RedeemPageObject redeemPage=currentDepositProductDetailPage.clickRansomBtn();
    	Assert.assertTrue(redeemPage.verifyInthisPage(), "点击赎回后未进入到赎回页");
    	RedeemResultPageObject redeemResultPage=redeemPage.redeemToBalance(redeemAmount, "111111");
    	Assert.assertTrue(redeemResultPage.verifyInthisPage(), "赎回后未跳转到赎回结果页面");
    	String redeemResult=redeemResultPage.getRedeemResult();
    	Assert.assertTrue(redeemResult.equals("提交成功"), "投资失败，投资结果为:"+redeemResult);
    }


}
