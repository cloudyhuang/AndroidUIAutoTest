package com.hjs.cases;


import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.hjs.config.CommonAppiumPage;
import com.hjs.config.CommonAppiumTest;
import com.hjs.db.DBOperation;
import com.hjs.pages.AccountBanlancePageObject;
import com.hjs.pages.CurrentDepositProductDetailPageObject;
import com.hjs.pages.DepositGroupBuyProductDetailPageObject;
import com.hjs.pages.DepositProductDetailPageObject;
import com.hjs.pages.DepositTransProductDetailPageObject;
import com.hjs.pages.DepositeBackMoneySettingPageObject;
import com.hjs.pages.FinancialPageObject;
import com.hjs.pages.FundAccountCreatPageObject;
import com.hjs.pages.FundAccountCreatVerifySmsCodePageObject;
import com.hjs.pages.FundDetailPageObject;
import com.hjs.pages.FundPurchasePageObject;
import com.hjs.pages.FundPurchaseResultPageObject;
import com.hjs.pages.GesturePwd;
import com.hjs.pages.GroupedBuyDetailPageObject;
import com.hjs.pages.HengCunJinPageObject;
import com.hjs.pages.HomePageObject;
import com.hjs.pages.IncomeAndExpensesDetailPageObject;
import com.hjs.pages.IncomeAndExpensesDetailPageObject.BalanceExchange;
import com.hjs.pages.InvestGroupBuyPageObject;
import com.hjs.pages.InvestPageObject;
import com.hjs.pages.InvestResultPageObject;
import com.hjs.pages.InvestTransPageObject;
import com.hjs.pages.LoginPageObject;
import com.hjs.pages.MassegePageObject;
import com.hjs.pages.MineCouponPageObject;
import com.hjs.pages.MinePageObject;
import com.hjs.pages.MyDepositeProductDetailPageObject;
import com.hjs.pages.MyDepositeProductPageObject;
import com.hjs.pages.MyRedeemProductDetailPageObject;
import com.hjs.pages.PayPageObject;
import com.hjs.pages.PersonSettingPageObject;
import com.hjs.pages.RechargePageObject;
import com.hjs.pages.RechargeResultPageObject;
import com.hjs.pages.RedeemPageObject;
import com.hjs.pages.RedeemResultPageObject;
import com.hjs.pages.TradeDetailPageObject;
import com.hjs.pages.TransCancelResultPageObject;
import com.hjs.pages.TransCancleDetailPageObject;
import com.hjs.pages.TransConfirmPageObject;
import com.hjs.pages.TransDetailPageObject;
import com.hjs.pages.TransRecordPageObject;
import com.hjs.pages.TransResultPageObject;
import com.hjs.pages.WithdrawCashPageObject;
import com.hjs.pages.WithdrawCashResultPageObject;
import com.hjs.publics.Util;
import com.hjs.testDate.Account;
import com.hjs.testDate.GroupBuyInfo;
import com.hjs.testDate.TransRecordInfo;
import com.hjs.testDate.TransRecordInfo.TransInfo;


public class TradeTestCase extends CommonAppiumTest{
	private static String DEPOSITE_PRODUCT_NAME="黄XAutoTest产品180226205723";
	private static String DEPOSITE_GROUPBUYPRODUCT_NAME="黄XAutoTest团购产品170919042116";
	private static String DEPOSITE_TRANSPRODUCT_NAME="黄XAutoTest可转让产品180207172258";
	private static String FUND_PRODUCT_NAME="国寿安保增金宝货币";
	private static String CURRENTDEPOSITE_PRODUCT_NAME="恒存金-灵活理财";
	private static String phoneNum="17052411184";
	private static String withdrawValue="4594";
    @Test(priority = 1,description="理财页产品信息")
	public void testFinancialProductInfo() throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	PersonSettingPageObject personSettingPage=((MinePageObject)page).enterPersonSetting();
    	Assert.assertTrue(personSettingPage.verifyInthisPage(), "进入我的个人头像，未出现安全退出按钮");
    	LoginPageObject loginPageObject=personSettingPage.logOut();
    	Assert.assertTrue(loginPageObject.verifyInthisPage(), "退出后未跳转到登录页面");
    	page=loginPageObject.switchAccount(phoneNum);
		pageName=page.getClass().getName();
		Assert.assertTrue(pageName.contains("LoginPageObject"), "验证手机号后未进入登录页面");
		((LoginPageObject)page).login("hxnearc228");
		Assert.assertTrue(homepage.verifyIsInHomePage(),"登录后未跳转到主页");
    	FinancialPageObject FinancialPage=homepage.enterFinancialPage();
    	Assert.assertTrue(FinancialPage.verifyInthisPage(), "点击首页理财入口，未出现理财页面");
    	DEPOSITE_PRODUCT_NAME=FinancialPage.testProductInfo();
	}
    @Test(priority = 2,description="银行卡不带券购买定期产品")
    public void testPayRegularProductsByBankCardWithoutCoupon() throws Exception{
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
    	TransRecordInfo transRecordInfo=new TransRecordInfo();
    	TransInfo transInfo=transRecordInfo.new TransInfo();
    	transInfo.setPayMode("卡支付");		//记录交易记录
    	transInfo.setPayMoney(Util.get2DecimalPointsNumInString(startMonney));
    	transInfo.setTransName("投资-"+DEPOSITE_PRODUCT_NAME);
    	Assert.assertTrue(payPage.verifyInthisPage(), "点击确认投资未跳转到支付页面");
    	InvestResultPageObject investResultPage=payPage.payByBankCardWithoutCoupon("123456", "17052411227");
    	Assert.assertTrue(investResultPage.verifyInthisPage(), "支付后未跳转到支付结果页面");
    	transInfo.setPayTime(Util.getUserDate("yyyy-MM-dd HH:mm:ss"));
    	String investResult=investResultPage.getInvestResult();
    	if(investResult.equals("投资申请已受理")||investResult.contains("已提交")){
    		transInfo.setPayIsSuccesse(true);
    	}
    	else{
    		transInfo.setPayIsSuccesse(false);
    	}
    	DBOperation dbOperation=new DBOperation();
    	String transNo=dbOperation.getTransNoByPhoneNum(phoneNum);
    	transInfo.setTransNo(transNo);
    	TransRecordInfo.addRecordInfo(transNo,transInfo);
    	Assert.assertTrue(investResult.equals("投资申请已受理")||investResult.contains("已提交"), "投资失败，投资结果为:"+investResult);
    }
    @Test(priority = 3,description="银行卡现金券购买定期产品")
    public void testPayRegularProductsByBankCardWithCashCoupon() throws Exception{
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
    	payPage.deleteUserCoupon(phoneNum);
    	String couponId="2463";
    	Assert.assertTrue(payPage.addCouponToUser(phoneNum, couponId), "加券失败");
    	investPage.invest(startMonney);
    	Assert.assertTrue(payPage.verifyInthisPage(), "点击确认投资未跳转到支付页面");
    	InvestResultPageObject investResultPage=payPage.payByBankCardWithCoupon("123456", "17052411227",couponId);
    	Assert.assertTrue(investResultPage.verifyInthisPage(), "支付后未跳转到支付结果页面");
    	String investResult=investResultPage.getInvestResult();
    	Assert.assertTrue(investResult.equals("投资申请已受理")||investResult.contains("已提交"), "投资失败，投资结果为:"+investResult);
    }
    @Test(priority = 4,description="银行卡加息券购买定期理财产品")
    public void testPayRegularProductsByBankCardWithBonusCoupon() throws Exception{
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
    	payPage.deleteUserCoupon(phoneNum);	//清除用户所有优惠券
    	String couponId="2465";
    	Assert.assertTrue(payPage.addCouponToUser(phoneNum, couponId), "加券失败");
    	investPage.invest(startMonney);
    	Assert.assertTrue(payPage.verifyInthisPage(), "点击确认投资未跳转到支付页面");
    	InvestResultPageObject investResultPage=payPage.payByBankCardWithCoupon("123456", "17052411227",couponId);
    	Assert.assertTrue(investResultPage.verifyInthisPage(), "支付后未跳转到支付结果页面");
    	String investResult=investResultPage.getInvestResult();
    	Assert.assertTrue(investResult.equals("投资申请已受理")||investResult.contains("已提交"), "投资失败，投资结果为:"+investResult);
    }
    @Test(priority = 5,description="银行卡不带券无短信验证码购买定期产品")
    public void testPayRegularProductsByBankCardWithoutCouponWithoutSmsCode() throws Exception{
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
    public void testtestPayRegularProductsByBankCardWithCashCouponWithoutSmsCode() throws Exception{
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
    	payPage.deleteUserCoupon(phoneNum);
    	String couponId="2463";
    	Assert.assertTrue(payPage.addCouponToUser(phoneNum, couponId), "加券失败");
    	investPage.invest(startMonney);
    	Assert.assertTrue(payPage.verifyInthisPage(), "点击确认投资未跳转到支付页面");
    	InvestResultPageObject investResultPage=payPage.payByBankCardWithCouponWithoutSmsCode("123456", "17052411227",couponId);
    	Assert.assertTrue(investResultPage.verifyInthisPage(), "支付后未跳转到支付结果页面");
    	String investResult=investResultPage.getInvestResult();
    	Assert.assertTrue(investResult.equals("投资申请已受理")||investResult.contains("已提交"), "投资失败，投资结果为:"+investResult);
    }
    @Test(priority = 7,description="银行卡加息券无验证码购买定期产品")
    public void testtestPayRegularProductsByBankCardWithBonusCouponWithoutSmsCode() throws Exception{
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
    	payPage.deleteUserCoupon(phoneNum);
    	String couponId="2465";
    	Assert.assertTrue(payPage.addCouponToUser(phoneNum, couponId), "加券失败");
    	investPage.invest(startMonney);
    	Assert.assertTrue(payPage.verifyInthisPage(), "点击确认投资未跳转到支付页面");
    	InvestResultPageObject investResultPage=payPage.payByBankCardWithCouponWithoutSmsCode("123456", "17052411227",couponId);
    	Assert.assertTrue(investResultPage.verifyInthisPage(), "支付后未跳转到支付结果页面");
    	String investResult=investResultPage.getInvestResult();
    	Assert.assertTrue(investResult.equals("投资申请已受理")||investResult.contains("已提交"), "投资失败，投资结果为:"+investResult);
    }
    @Test(priority = 8,description="充值")
    public void testRecharge()throws Exception{
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
    	Assert.assertTrue(accountBanlancePage.verifyInthisPage(), "充值成功后点击查看余额未进入余额页");
    	int actualCash=accountBanlancePage.getAvailableBalance();
    	Assert.assertEquals(actualCash, expectCash,"实际剩余余额为"+actualCash+"预期剩余余额为"+expectCash);

    }
    @Test(priority = 9,description="余额不带券购买定期产品")
    public void testPayRegularProductsByBanlanceWithoutCoupon() throws Exception{
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
    public void testPayRegularProductsByBanlanceWithCashCoupon() throws Exception{
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
    	payPage.deleteUserCoupon(phoneNum);
    	String couponId="2463";
    	Assert.assertTrue(payPage.addCouponToUser(phoneNum, couponId), "加券失败");
    	investPage.invest(startMonney);
    	Assert.assertTrue(payPage.verifyInthisPage(), "点击确认投资未跳转到支付页面");
    	InvestResultPageObject investResultPage=payPage.payByBanlanceWithCoupon("123456", "17052411227", couponId);
    	Assert.assertTrue(investResultPage.verifyInthisPage(), "支付后未跳转到支付结果页面");
    	String investResult=investResultPage.getInvestResult();
    	Assert.assertTrue(investResult.equals("投资申请已受理")||investResult.contains("已提交"), "投资失败，投资结果为:"+investResult);

    }
    @Test(priority = 11,description="余额加息券购买定期产品")
    public void testPayRegularProductsByBanlanceWithBonusCoupon() throws Exception{
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
    	payPage.deleteUserCoupon(phoneNum);
    	String couponId="2465";
    	Assert.assertTrue(payPage.addCouponToUser(phoneNum, couponId), "加券失败");
    	investPage.invest(startMonney);
    	Assert.assertTrue(payPage.verifyInthisPage(), "点击确认投资未跳转到支付页面");
    	InvestResultPageObject investResultPage=payPage.payByBanlanceWithCoupon("123456", "17052411227", couponId);
    	Assert.assertTrue(investResultPage.verifyInthisPage(), "支付后未跳转到支付结果页面");
    	String investResult=investResultPage.getInvestResult();
    	Assert.assertTrue(investResult.equals("投资申请已受理")||investResult.contains("已提交"), "投资失败，投资结果为:"+investResult);

    }
    @Test(priority = 12,description="余额不带券购买活期产品")
    public void testPayCurrentProductsByBanlanceWithoutCoupon() throws Exception{
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
    	Assert.assertTrue(investResult.equals("投资申请已受理")||investResult.equals("投资已成功")||investResult.contains("已提交"), "投资失败，投资结果为:"+investResult);
    }
    @Test(priority = 13,description="银行卡不带券购买活期产品")
    public void testPayCurrentProductsByBankCardWithoutCoupon() throws Exception{
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
    	Assert.assertTrue(investResult.equals("投资申请已受理")||investResult.equals("投资已成功")||investResult.contains("已提交"), "投资失败，投资结果为:"+investResult);
    }
    @Test(priority = 14,description="银行卡不带券购买定期产品失败场景")
    public void testPayByBankCardFailWithoutCoupon() throws Exception{
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
    	Assert.assertTrue(investResult.contains("失败"),"未跳转到投资失败页面，页面信息为:"+investResult);

    }
    @Test(priority = 15,expectedExceptions = Exception.class, expectedExceptionsMessageRegExp="找不到下架产品",description="下架产品")
    public void testOffProducts() throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	FinancialPageObject financialPage=homepage.enterFinancialPage();
    	Assert.assertTrue(financialPage.verifyInthisPage(), "点击首页理财入口，未出现理财页面");
    	financialPage.productPullOffAndFindProduct(DEPOSITE_PRODUCT_NAME);


    }
    @Test(priority = 16,description="提现")
    public void testWithdraw()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	AccountBanlancePageObject accountBanlancePage=((MinePageObject)page).enterAccountBanlancePage();
    	Assert.assertTrue(accountBanlancePage.verifyInthisPage(), "点击账户余额未进入余额页");
    	DBOperation db=new DBOperation();
    	db.updateWithdrawLowerLimitAmount(10.00);		//调整存款最低限额为10元
    	int maxWithdrawCash=accountBanlancePage.getAvailableBalance();
    	int halfWithdrawCash=maxWithdrawCash/2;
    	int expectCash=maxWithdrawCash-halfWithdrawCash;
    	String stringHalfWithdrawCash=String.valueOf(halfWithdrawCash);
    	withdrawValue=stringHalfWithdrawCash;	//赋值给全局变量读取提现金额
    	WithdrawCashPageObject withdrawCashPage=accountBanlancePage.gotoWithdrawCashPage();
    	Assert.assertTrue(withdrawCashPage.verifyInthisPage(), "点击提现未进入到提现页面");
    	if(withdrawCashPage.getWithdrawCount()>0){
	    	WithdrawCashResultPageObject withdrawCashResultPage=withdrawCashPage.withdrawCash(stringHalfWithdrawCash, "123456");
	    	Assert.assertTrue(withdrawCashResultPage.verifyInthisPage(), "提现输入密码后未跳转到提现结果页");
	    	Assert.assertTrue(withdrawCashResultPage.getWithdrawCashResult().contains("申请成功"), "提现申请失败，失败信息为："+withdrawCashResultPage.getWithdrawCashResult());
	    	accountBanlancePage=withdrawCashResultPage.gotoBalancePage();
	    	Assert.assertTrue(accountBanlancePage.verifyInthisPage(), "提现成功后点击查看余额未进入余额页");
	    	int actualCash=accountBanlancePage.getAvailableBalance();
	    	Assert.assertEquals(actualCash, expectCash,"实际剩余余额为"+actualCash+"预期剩余余额为"+expectCash);
    	}
    	else{
    		Reporter.log("注意：当前提现次数为0！");
    	}
    }
    @Test(priority = 17,description="账户余额展示及掩码")
    public void testAccountBanlance()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	String minePageBanlance=((MinePageObject)page).getBanlance();
    	AccountBanlancePageObject accountBanlancePage=((MinePageObject)page).enterAccountBanlancePage();
    	Assert.assertTrue(accountBanlancePage.verifyInthisPage(), "点击账户余额未进入余额页");
    	String accountPageBanlance=accountBanlancePage.getStringBanlance();
    	Assert.assertEquals(accountPageBanlance, minePageBanlance,"我的页余额和账户余额页余额不一致");
    	if(accountBanlancePage.isBalanceMaskClose()){
    		accountBanlancePage.openBanlanceEye();
        	accountBanlancePage.closeBanlanceEye();
    	}
    	else{
    		accountBanlancePage.closeBanlanceEye();
    		accountBanlancePage.openBanlanceEye();
        	
    	}
    	
    }
    @Test(priority = 18,description="余额交易明细")
    public void testBalanceTradeDetail()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	AccountBanlancePageObject accountBanlancePage=((MinePageObject)page).enterAccountBanlancePage();
    	Assert.assertTrue(accountBanlancePage.verifyInthisPage(), "点击账户余额未进入余额页");
    	String accountPageBanlance=accountBanlancePage.getStringBanlance();	//余额页的余额
    	IncomeAndExpensesDetailPageObject incomeAndExpensesDetailPage=accountBanlancePage.gotoIncomeAndExpensesDetailPage();
    	Assert.assertTrue(incomeAndExpensesDetailPage.verifyInthisPage(), "点击余额页明细未进入收支明细页面");
    	BalanceExchange balanceExchange=incomeAndExpensesDetailPage.getLastBalanceExchange();
    	String lastTradeType=balanceExchange.getType();		//收支明细页-最近一笔交易类型
    	String balanceSum=balanceExchange.getBalanceSum();		//收支明细页-最近一笔余额
    	String tradeSum=balanceExchange.getValue();		//收支明细页-最近一笔交易金额
    	Assert.assertEquals(lastTradeType, "提现","最近一笔交易类型不是提现，而是："+lastTradeType);
    	Assert.assertEquals(balanceSum,accountPageBanlance,"余额页余额与最后一笔交易余额不相等");
    	Assert.assertEquals(tradeSum,withdrawValue+".00","最近一笔交易金额与最近提现金额不相等");
    	TradeDetailPageObject tradeDetailPage=incomeAndExpensesDetailPage.gotoLastTradeDetailPage();
    	Assert.assertTrue(tradeDetailPage.verifyInthisPage(), "点击明细页面最近一笔交易后未跳转到交易详情页");
    	tradeDetailPage.assertTradeDetails("余额提现",withdrawValue+".00");
    	
    }
    @Test(priority = 19,description="恒存金明细")
    public void testHengCunJinDetail()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	String hengCunJinValue=((MinePageObject)page).getHengCunJinValue();
    	HengCunJinPageObject hengCunJinPage=((MinePageObject)page).gotoHengCunJinPage();
    	Assert.assertTrue(hengCunJinPage.verifyInthisPage(), "点击账户余额未进入余额页");
    	String hengCunJinTotalAsset=hengCunJinPage.getTotalAsset();
    	Assert.assertEquals(hengCunJinTotalAsset, hengCunJinValue,"我的页恒存金数值和恒存金页数值不同");
    	CurrentDepositProductDetailPageObject currentDepositProductDetailPage=hengCunJinPage.gotoCurrentDepositProductDetailPage();
    	Assert.assertTrue(currentDepositProductDetailPage.verifyInthisPage(), "恒存金页点击持有恒存金未跳转到恒存金投资页面");
    	
    }
    @Test(priority = 20,description="我的定期产品资产、预期收益确认")
    public void testMyRegularProductAssetAndProfitInfo()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	MyDepositeProductPageObject myDepositeProductPage=((MinePageObject)page).enterMyDepositeProductPage();
    	Assert.assertTrue(myDepositeProductPage.verifyInthisPage(), "点击定期理财未进入我的理财产品页");
    	myDepositeProductPage.assertMyDepositeProduct(DEPOSITE_PRODUCT_NAME);
    	
    }
    @Test(priority = 24,description="交易记录")
    public void testTransRecorder()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	TransRecordPageObject transRecordPage=((MinePageObject)page).enterTransRecorder();
    	Assert.assertTrue(transRecordPage.verifyInthisPage(), "点击交易记录未进入交易记录页面");
    	transRecordPage.assertTransRecord();
    }
    @Test(priority = 25,description="我的定期产品信息")
    public void testMyRegularProductInfo()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	MyDepositeProductPageObject myDepositeProductPage=((MinePageObject)page).enterMyDepositeProductPage();
    	Assert.assertTrue(myDepositeProductPage.verifyInthisPage(), "点击定期理财未进入我的理财产品页");
    	MyDepositeProductDetailPageObject myDepositeProductDetailPage=myDepositeProductPage.enterDepositeProductDetail(DEPOSITE_PRODUCT_NAME);
    	Assert.assertTrue(myDepositeProductDetailPage.verifyInthisPage(), "点击"+DEPOSITE_PRODUCT_NAME+"产品未进入我的理财产品详情页");
    	myDepositeProductDetailPage.assertMtpAndAppDetail(phoneNum);
    }
    @Test(priority = 26,description="定期回款设置到银行卡")
    public void testBackMoneySettingToBankCard()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	PersonSettingPageObject personSettingPage=((MinePageObject)page).enterPersonSetting();
    	Assert.assertTrue(personSettingPage.verifyInthisPage(), "进入我的个人头像，未进入设置页");
    	DepositeBackMoneySettingPageObject depositeBackMoneySettingPage=personSettingPage.gotoBackMoneySettingPage();
    	Assert.assertTrue(depositeBackMoneySettingPage.verifyInthisPage(), "点击定期回款按钮未进入定期回款设置页面");
    	depositeBackMoneySettingPage.setToBankCard();
    	depositeBackMoneySettingPage.backKeyEvent();
    	Assert.assertTrue(personSettingPage.verifyInthisPage(), "从定期回款设置页返回后未进入设置页");
    	personSettingPage.backKeyEvent();
    	MinePageObject minePage=new MinePageObject(driver);
    	Assert.assertTrue(minePage.verifyInthisPage(), "从设置页返回后未进入我的页");
    	MyDepositeProductPageObject myDepositeProductPage=minePage.enterMyDepositeProductPage();
    	Assert.assertTrue(myDepositeProductPage.verifyInthisPage(), "点击定期理财未进入我的理财产品页");
    	MyDepositeProductDetailPageObject myDepositeProductDetailPage=myDepositeProductPage.enterDepositeProductDetail(DEPOSITE_PRODUCT_NAME);
    	Assert.assertTrue(myDepositeProductDetailPage.verifyInthisPage(), "点击"+DEPOSITE_PRODUCT_NAME+"产品未进入我的理财产品详情页");
    	myDepositeProductDetailPage.backKeyEvent();
    	myDepositeProductPage.enterDepositeProductDetail(DEPOSITE_PRODUCT_NAME);	//防止缓存再进入一次
    	Assert.assertTrue(myDepositeProductDetailPage.verifyInthisPage(), "点击"+DEPOSITE_PRODUCT_NAME+"产品未进入我的理财产品详情页");
    	myDepositeProductDetailPage.assertBackMoneyTo("至银行卡");
    }
    @Test(priority = 27,description="定期回款设置到余额")
    public void testBackMoneySettingToBanlance()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	PersonSettingPageObject personSettingPage=((MinePageObject)page).enterPersonSetting();
    	Assert.assertTrue(personSettingPage.verifyInthisPage(), "进入我的个人头像，未进入设置页");
    	DepositeBackMoneySettingPageObject depositeBackMoneySettingPage=personSettingPage.gotoBackMoneySettingPage();
    	Assert.assertTrue(depositeBackMoneySettingPage.verifyInthisPage(), "点击定期回款按钮未进入定期回款设置页面");
    	depositeBackMoneySettingPage.setToBalance();
    	depositeBackMoneySettingPage.backKeyEvent();
    	Assert.assertTrue(personSettingPage.verifyInthisPage(), "从定期回款设置页返回后未进入设置页");
    	personSettingPage.backKeyEvent();
    	MinePageObject minePage=new MinePageObject(driver);
    	Assert.assertTrue(minePage.verifyInthisPage(), "从设置页返回后未进入我的页");
    	MyDepositeProductPageObject myDepositeProductPage=minePage.enterMyDepositeProductPage();
    	Assert.assertTrue(myDepositeProductPage.verifyInthisPage(), "点击定期理财未进入我的理财产品页");
    	MyDepositeProductDetailPageObject myDepositeProductDetailPage=myDepositeProductPage.enterDepositeProductDetail(DEPOSITE_PRODUCT_NAME);
    	Assert.assertTrue(myDepositeProductDetailPage.verifyInthisPage(), "点击"+DEPOSITE_PRODUCT_NAME+"产品未进入我的理财产品详情页");
    	myDepositeProductDetailPage.assertBackMoneyTo("至账户余额");
    }
    @Test(priority = 28,description="发起团")
    public void testBuyGroupBuyProduct()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	FinancialPageObject financialPage=homepage.enterFinancialPage();
    	Assert.assertTrue(financialPage.verifyInthisPage(), "点击首页理财入口，未出现理财页面");
    	DEPOSITE_GROUPBUYPRODUCT_NAME=financialPage.testGroupBuyProductInfo();
    	DepositGroupBuyProductDetailPageObject depositGroupBuyProductDetailPage=financialPage.clickDepositGroupBuyProduct(DEPOSITE_GROUPBUYPRODUCT_NAME);
    	String startMonney=depositGroupBuyProductDetailPage.getStartMoney();
    	Assert.assertTrue(depositGroupBuyProductDetailPage.verifyInthisPage(), "点击团购产品，未出现团购产品页面");
    	depositGroupBuyProductDetailPage.checkGroupBuyInfo();
    	InvestGroupBuyPageObject investGroupBuyPage=depositGroupBuyProductDetailPage.startGroupBuy();
    	Assert.assertTrue(investGroupBuyPage.verifyInthisPage(), "发起团后未跳转到团购投资页");
    	PayPageObject payPage=investGroupBuyPage.InvestGroupBuy(startMonney);
    	Assert.assertTrue(payPage.verifyInthisPage(), "提交团购投资申请后未跳转到支付页面");
    	InvestResultPageObject investResultPage=payPage.payByBankCardWithoutCoupon("123456", "17052411227");
    	Assert.assertTrue(investResultPage.verifyInthisPage(), "支付后未跳转到支付结果页面");
    	String investResult=investResultPage.getInvestResult();
    	Assert.assertTrue(investResult.equals("投资申请已受理")||investResult.equals("投资已成功")||investResult.contains("已提交"), "投资失败，投资结果为:"+investResult);
    	int groupBuyPeopleNum=GroupBuyInfo.getGroupBuyPeopleNum();
    	groupBuyPeopleNum++;
    	GroupBuyInfo.setGroupBuyPeopleNum(groupBuyPeopleNum);
    	double groupBuyedAmountm=GroupBuyInfo.getGroupBuyedAmountm();
    	groupBuyedAmountm=groupBuyedAmountm+Util.stringToDouble(startMonney);
    	GroupBuyInfo.setGroupBuyedAmountm(groupBuyedAmountm);
    }
    @Test(priority = 29,description="检查已买团购信息")
    public void testGroupBuyedProduct()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	FinancialPageObject financialPage=homepage.enterFinancialPage();
    	Assert.assertTrue(financialPage.verifyInthisPage(), "点击首页理财入口，未出现理财页面");
    	DepositGroupBuyProductDetailPageObject depositGroupBuyProductDetailPage=financialPage.clickDepositGroupBuyProduct(DEPOSITE_GROUPBUYPRODUCT_NAME);
    	Assert.assertTrue(depositGroupBuyProductDetailPage.verifyInthisPage(), "点击团购产品，未出现团购产品页面");
    	depositGroupBuyProductDetailPage.checkGroupedInfo(Util.doubleTransToString(GroupBuyInfo.getGroupBuyedAmountm()), String.valueOf(GroupBuyInfo.getGroupBuyPeopleNum()));
    	GroupedBuyDetailPageObject groupedBuyDetailPage=depositGroupBuyProductDetailPage.gotoGroupedBuyDetailPage();
    	Assert.assertTrue(groupedBuyDetailPage.verifyInthisPage(), "点击成团详情未出现已团购产品详情页面");
    	GroupBuyInfo.setGroupBuyShareCode(groupedBuyDetailPage.getShareCode());
    }
    @Test(priority = 30,description="现金券购买团购产品",enabled = false)
    public void testBuyGroupBuyProductWithCashCoupon()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	FinancialPageObject financialPage=homepage.enterFinancialPage();
    	Assert.assertTrue(financialPage.verifyInthisPage(), "点击首页理财入口，未出现理财页面");
    	DepositGroupBuyProductDetailPageObject depositGroupBuyProductDetailPage=financialPage.clickDepositGroupBuyProduct(DEPOSITE_GROUPBUYPRODUCT_NAME);
    	String startMonney=depositGroupBuyProductDetailPage.getStartMoney();
    	Assert.assertTrue(depositGroupBuyProductDetailPage.verifyInthisPage(), "点击团购产品，未出现团购产品页面");
    	InvestGroupBuyPageObject investGroupBuyPage=depositGroupBuyProductDetailPage.startGroupBuy();
    	Assert.assertTrue(investGroupBuyPage.verifyInthisPage(), "发起团后未跳转到团购投资页");
    	PayPageObject payPage=new PayPageObject(driver);
    	payPage.deleteUserCoupon(phoneNum);	//清除用户所有优惠券
    	String couponId="2463";
    	Assert.assertTrue(payPage.addCouponToUser(phoneNum, couponId), "加券失败");
    	payPage=investGroupBuyPage.InvestGroupBuy(startMonney);
    	Assert.assertTrue(payPage.verifyInthisPage(), "提交团购投资申请后未跳转到支付页面");
    	InvestResultPageObject investResultPage=payPage.payByBankCardWithCoupon("123456", "17052411227",couponId);
    	Assert.assertTrue(investResultPage.verifyInthisPage(), "支付后未跳转到支付结果页面");
    	String investResult=investResultPage.getInvestResult();
    	Assert.assertTrue(investResult.equals("投资申请已受理")||investResult.equals("投资已成功")||investResult.contains("已提交"), "投资失败，投资结果为:"+investResult);
    	double groupBuyedAmountm=GroupBuyInfo.getGroupBuyedAmountm();
    	groupBuyedAmountm=groupBuyedAmountm+Util.stringToDouble(startMonney);
    }
    @Test(priority = 31,description="参与团")
    public void testJoinGroupBuyProduct()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	PersonSettingPageObject personSettingPage=((MinePageObject)page).enterPersonSetting();
    	Assert.assertTrue(personSettingPage.verifyInthisPage(), "进入我的个人头像，未出现安全退出按钮");
    	LoginPageObject loginPageObject=personSettingPage.logOut();
    	Assert.assertTrue(loginPageObject.verifyInthisPage(), "退出后未跳转到登录页面");
    	page=loginPageObject.switchAccount("17090720054");
		pageName=page.getClass().getName();
		Assert.assertTrue(pageName.contains("LoginPageObject"), "验证手机号后未进入登录页面");
		GesturePwd gesturePwd=((LoginPageObject)page).login("Hd666666");
		if(gesturePwd.verifyInthisPage()){		//若首次登录设置手密，否则进入主页
			Assert.assertEquals("请绘制您的手势密码", gesturePwd.verifyGestureTips(),"登录后未进入设置手密页");
	    	String result=gesturePwd.setFirstGesturePwd(1,4,7,8);
	    	Assert.assertEquals("请再画一次手势密码", result);
	    	boolean setNextGestureResult=gesturePwd.setNextGesturePwd(1,4,7,8).verifyIsInHomePage();
	    	Assert.assertTrue(setNextGestureResult, "第二次设置手势失败，未跳转到主页，主页我的入口未显示");
		}
		else{
			Assert.assertTrue(homepage.verifyIsInHomePage(),"登录后未进入主页");
		}
    	FinancialPageObject financialPage=homepage.enterFinancialPage();
    	Assert.assertTrue(financialPage.verifyInthisPage(), "点击首页理财入口，未出现理财页面");
    	DepositGroupBuyProductDetailPageObject depositGroupBuyProductDetailPage=financialPage.clickDepositGroupBuyProduct(DEPOSITE_GROUPBUYPRODUCT_NAME);
    	String startMonney=depositGroupBuyProductDetailPage.getStartMoney();
    	Assert.assertTrue(depositGroupBuyProductDetailPage.verifyInthisPage(), "点击团购产品，未出现团购产品页面");
    	page=depositGroupBuyProductDetailPage.joinGroupBuy(GroupBuyInfo.getGroupBuyShareCode());
    	InvestGroupBuyPageObject investGroupBuyPage = null;
    	if(page.getClass().getName().contains("GroupedBuyDetailPageObject")){
    		GroupedBuyDetailPageObject groupedBuyDetail=(GroupedBuyDetailPageObject)page;
    		Assert.assertTrue(groupedBuyDetail.verifyInthisPage(), "加入团后未跳转到团购详情页");
    		investGroupBuyPage=groupedBuyDetail.clickInvestGroupBuyBtn();
    	}
    	else if(page.getClass().getName().contains("InvestGroupBuyPageObject")){
    		investGroupBuyPage=(InvestGroupBuyPageObject)page;
    	}

    	Assert.assertTrue(investGroupBuyPage.verifyInthisPage(), "成团详情点击投资未跳转到团购投资页");
    	PayPageObject payPage=investGroupBuyPage.InvestGroupBuy(startMonney);
    	Assert.assertTrue(payPage.verifyInthisPage(), "提交团购投资申请后未跳转到支付页面");
    	InvestResultPageObject investResultPage=payPage.payByBankCardWithoutCoupon("123456", "17052411227");
    	Assert.assertTrue(investResultPage.verifyInthisPage(), "支付后未跳转到支付结果页面");
    	String investResult=investResultPage.getInvestResult();
    	Assert.assertTrue(investResult.equals("投资申请已受理")||investResult.equals("投资已成功")||investResult.contains("已提交"), "投资失败，投资结果为:"+investResult);
    	GroupedBuyDetailPageObject groupedBuyDetail=investResultPage.gotoGroupedBuyDetailPage();
    	Assert.assertTrue(groupedBuyDetail.verifyInthisPage(), "投资成功查看详情未跳转到团购详情页");
    	String currentReward=groupedBuyDetail.getCurrentReward();
    	String lvupReward=groupedBuyDetail.getLvupReward();
    	GroupBuyInfo.setCurrentReward(currentReward);
    	GroupBuyInfo.setLvupReward(lvupReward);
    }
    @Test(priority = 32,description="购买金额、人数达到团购加息阶梯")
    public void testJoinGroupBuyProductToPlusProfit()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	FinancialPageObject financialPage=homepage.enterFinancialPage();
    	Assert.assertTrue(financialPage.verifyInthisPage(), "点击首页理财入口，未出现理财页面");
    	DepositGroupBuyProductDetailPageObject depositGroupBuyProductDetailPage=financialPage.clickDepositGroupBuyProduct(DEPOSITE_GROUPBUYPRODUCT_NAME);
    	Assert.assertTrue(depositGroupBuyProductDetailPage.verifyInthisPage(), "点击团购产品，未出现团购产品页面");
    	GroupedBuyDetailPageObject groupedBuyDetail=depositGroupBuyProductDetailPage.gotoGroupedBuyDetailPage();
    	String getLvupBalance=groupedBuyDetail.getLvupBalance();
    	InvestGroupBuyPageObject investGroupBuyPage=groupedBuyDetail.clickInvestGroupBuyBtn();
    	Assert.assertTrue(investGroupBuyPage.verifyInthisPage(), "成团详情点击投资未跳转到团购投资页");
    	PayPageObject payPage=investGroupBuyPage.InvestGroupBuy(getLvupBalance);
    	Assert.assertTrue(payPage.verifyInthisPage(), "提交团购投资申请后未跳转到支付页面");
    	InvestResultPageObject investResultPage=payPage.payByBankCardWithoutCoupon("123456", "17052411227");
    	Assert.assertTrue(investResultPage.verifyInthisPage(), "支付后未跳转到支付结果页面");
    	String investResult=investResultPage.getInvestResult();
    	Assert.assertTrue(investResult.equals("投资申请已受理")||investResult.equals("投资已成功")||investResult.contains("已提交"), "投资失败，投资结果为:"+investResult);
    	groupedBuyDetail=investResultPage.gotoGroupedBuyDetailPage();
    	Assert.assertTrue(groupedBuyDetail.verifyInthisPage(), "投资成功查看详情未跳转到团购详情页");
    	String currentReward=groupedBuyDetail.getCurrentReward();
    	String lvupReward=groupedBuyDetail.getLvupReward();
    	Assert.assertEquals(currentReward, GroupBuyInfo.getLvupReward(),"当前额外收益为："+currentReward+"预期额外收益为："+GroupBuyInfo.getLvupReward());
    	GroupBuyInfo.setCurrentReward(currentReward);
    	GroupBuyInfo.setLvupReward(lvupReward);
    }
    @Test(priority = 33,expectedExceptions = Exception.class, expectedExceptionsMessageRegExp="找不到下架产品",description="下架团购产品")
    public void testOffGroupBuyProducts() throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	FinancialPageObject financialPage=homepage.enterFinancialPage();
    	Assert.assertTrue(financialPage.verifyInthisPage(), "点击首页理财入口，未出现理财页面");
    	financialPage.productPullOffAndFindProduct(DEPOSITE_GROUPBUYPRODUCT_NAME);

    }
    @Test(priority = 34,description="购买可转让产品")
    public void testBuyTransProduct()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	PersonSettingPageObject personSettingPage=((MinePageObject)page).enterPersonSetting();
    	Assert.assertTrue(personSettingPage.verifyInthisPage(), "进入我的个人头像，未出现安全退出按钮");
    	LoginPageObject loginPageObject=personSettingPage.logOut();
    	Assert.assertTrue(loginPageObject.verifyInthisPage(), "退出后未跳转到登录页面");
    	page=loginPageObject.switchAccount(phoneNum);
		pageName=page.getClass().getName();
		Assert.assertTrue(pageName.contains("LoginPageObject"), "验证手机号后未进入登录页面");
		((LoginPageObject)page).login("hxnearc228");
		Assert.assertTrue(homepage.verifyIsInHomePage(),"登录后未跳转到主页"); 
    	FinancialPageObject financialPage=homepage.enterFinancialPage();
    	Assert.assertTrue(financialPage.verifyInthisPage(), "点击首页理财入口，未出现理财页面");
    	DEPOSITE_TRANSPRODUCT_NAME=financialPage.testTransSPVProductInfo();
    	DepositProductDetailPageObject depositProductDetailPage=financialPage.clickDepositProduct(DEPOSITE_TRANSPRODUCT_NAME);
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
    @Test(priority = 35,description="转让产品")
    public void testTransTransProduct()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	MyDepositeProductPageObject myDepositeProductPage=((MinePageObject)page).enterMyDepositeProductPage();
    	Assert.assertTrue(myDepositeProductPage.verifyInthisPage(), "点击定期理财未进入我的理财产品页");
    	TransDetailPageObject transDetailPage=myDepositeProductPage.transApply(DEPOSITE_TRANSPRODUCT_NAME);
    	Assert.assertTrue(transDetailPage.verifyInthisPage(), "申请转让后未跳转到转让详情页面");
    	TransConfirmPageObject transConfirmPage=transDetailPage.applyTrans();
    	Assert.assertTrue(transConfirmPage.verifyInthisPage(), "申请转让后未跳转到转让确认页面");
    	transConfirmPage.setTransProductInfo();
    	TransResultPageObject transResultPage=transConfirmPage.confirmTrans("123456");
    	Assert.assertTrue(transResultPage.verifyInthisPage(), "申请转让确认后未跳转到转让结果页面");
    	String result=transResultPage.getTransResult();
    	Assert.assertTrue(result.equals("转让申请已提交")||result.contains("已受理"), "转让失败，转让结果为:"+result);
    }
    @Test(priority = 36,description="撤销转让产品")
    public void testCancleTransProduct()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	MyDepositeProductPageObject myDepositeProductPage=((MinePageObject)page).enterMyDepositeProductPage();
    	Assert.assertTrue(myDepositeProductPage.verifyInthisPage(), "点击定期理财未进入我的理财产品页");
    	MyDepositeProductDetailPageObject myDepositeProductDetailPage=myDepositeProductPage.enterTransProductDetail(DEPOSITE_TRANSPRODUCT_NAME);
    	Assert.assertTrue(myDepositeProductDetailPage.verifyInthisPage(), "点击产品后未跳转到我的产品详情页面");
    	TransCancleDetailPageObject transCancleDetailPage=myDepositeProductDetailPage.cancelTrans();
    	Assert.assertTrue(transCancleDetailPage.verifyInthisPage(), "撤销转让后未跳转到撤销转让详情页面");
    	TransCancelResultPageObject transCancelResultPage=transCancleDetailPage.cancelTrans("123456");
    	Assert.assertTrue(transCancelResultPage.verifyInthisPage(), "撤销转让输入密码后未跳转到撤销转让结果页面");
    	String result=transCancelResultPage.getTransCancelResult();
    	Assert.assertTrue(result.equals("撤销成功")||result.contains("已受理"), "撤销转让失败，撤销转让结果为:"+result);
    }
    @Test(priority = 37,description="受让产品")
    public void testAcceptTransProduct()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	MyDepositeProductPageObject myDepositeProductPage=((MinePageObject)page).enterMyDepositeProductPage();
    	Assert.assertTrue(myDepositeProductPage.verifyInthisPage(), "点击定期理财未进入我的理财产品页");
    	String transProdStatus=myDepositeProductPage.getTransProductStatus(DEPOSITE_TRANSPRODUCT_NAME);
    	if(transProdStatus.equals("投资中")){//如果产品在投资中则再转让
	    	TransDetailPageObject transDetailPage=myDepositeProductPage.transApply(DEPOSITE_TRANSPRODUCT_NAME);
	    	Assert.assertTrue(transDetailPage.verifyInthisPage(), "申请转让后未跳转到转让详情页面");
	    	TransConfirmPageObject transConfirmPage=transDetailPage.applyTrans();
	    	Assert.assertTrue(transConfirmPage.verifyInthisPage(), "申请转让后未跳转到转让确认页面");
	    	TransResultPageObject transResultPage=transConfirmPage.confirmTrans("123456");
	    	Assert.assertTrue(transResultPage.verifyInthisPage(), "申请转让确认后未跳转到转让结果页面");
	    	String result=transResultPage.getTransResult();
	    	Assert.assertTrue(result.equals("转让申请已提交")||result.contains("已受理"), "转让失败，转让结果为:"+result);
    	}
    	//-----登录另一个账号受让产品-----
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	homepage=new HomePageObject(driver); 
    	page=homepage.enterPersonEntrace();
    	pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	PersonSettingPageObject personSettingPage=((MinePageObject)page).enterPersonSetting();
    	Assert.assertTrue(personSettingPage.verifyInthisPage(), "进入我的个人头像，未出现安全退出按钮");
    	LoginPageObject loginPageObject=personSettingPage.logOut();
    	Assert.assertTrue(loginPageObject.verifyInthisPage(), "退出后未跳转到登录页面");
    	page=loginPageObject.switchAccount("17090720054");
		pageName=page.getClass().getName();
		Assert.assertTrue(pageName.contains("LoginPageObject"), "验证手机号后未进入登录页面");
		GesturePwd gesturePwd=((LoginPageObject)page).login("Hd666666");
		if(gesturePwd.verifyInthisPage()){
			Assert.assertTrue(gesturePwd.verifyInthisPage(),"登录后未跳转到手势密码页");
	    	Assert.assertEquals("请绘制您的手势密码", gesturePwd.verifyGestureTips(),"登录后未进入设置手密页");
	    	String result=gesturePwd.setFirstGesturePwd(1,4,7,8);
	    	Assert.assertEquals("请再画一次手势密码", result);
	    	boolean setNextGestureResult=gesturePwd.setNextGesturePwd(1,4,7,8).verifyIsInHomePage();
	    	Assert.assertTrue(setNextGestureResult, "第二次设置手势失败，未跳转到主页，主页我的入口未显示"); 
		}
    	//-----登录另一个账号受让产品-----
    	FinancialPageObject financialPage=homepage.enterFinancialPage();
    	Assert.assertTrue(financialPage.verifyInthisPage(), "点击首页理财入口，未出现理财页面");
    	DepositTransProductDetailPageObject depositTransProductDetailPage=financialPage.clickDepositTransProduct(DEPOSITE_TRANSPRODUCT_NAME);
    	Assert.assertTrue(depositTransProductDetailPage.verifyInthisPage(), "理财页点击定期产品后未出现定期理财产品详情页");
    	InvestTransPageObject investTransPage=depositTransProductDetailPage.clickPayBtn();
    	Assert.assertTrue(investTransPage.verifyInthisPage(), "点击立即投资后未进入到投资转让产品页");
    	PayPageObject payPage=investTransPage.investTransPro();
    	Assert.assertTrue(payPage.verifyInthisPage(), "点击确认投资未跳转到支付页面");
    	InvestResultPageObject investResultPage=payPage.payByBankCardWithoutCoupon("123456", "17052411227");
    	Assert.assertTrue(investResultPage.verifyInthisPage(), "支付后未跳转到支付结果页面");
    	String investResult=investResultPage.getInvestResult();
    	Assert.assertTrue(investResult.equals("投资申请已受理")||investResult.contains("已提交"), "投资失败，投资结果为:"+investResult);
    }
    @Test(priority = 38,description="转让受让产品")
    public void testTransAcceptTransProduct()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	MyDepositeProductPageObject myDepositeProductPage=((MinePageObject)page).enterMyDepositeProductPage();
    	Assert.assertTrue(myDepositeProductPage.verifyInthisPage(), "点击定期理财未进入我的理财产品页");
    	TransDetailPageObject transDetailPage=myDepositeProductPage.acceptTransProdTransApply(DEPOSITE_TRANSPRODUCT_NAME);
    	Assert.assertTrue(transDetailPage.verifyInthisPage(), "申请转让后未跳转到转让详情页面");
    	TransConfirmPageObject transConfirmPage=transDetailPage.applyTrans();
    	Assert.assertTrue(transConfirmPage.verifyInthisPage(), "申请转让后未跳转到转让确认页面");
    	TransResultPageObject transResultPage=transConfirmPage.confirmTrans("123456");
    	Assert.assertTrue(transResultPage.verifyInthisPage(), "申请转让确认后未跳转到转让结果页面");
    	String result=transResultPage.getTransResult();
    	Assert.assertTrue(result.equals("转让申请已提交")||result.contains("已受理"), "转让失败，转让结果为:"+result);
    }
    @Test(priority = 39,description="撤销转让受让产品")
    public void testCancleTransAcceptTransProduct()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	MyDepositeProductPageObject myDepositeProductPage=((MinePageObject)page).enterMyDepositeProductPage();
    	Assert.assertTrue(myDepositeProductPage.verifyInthisPage(), "点击定期理财未进入我的理财产品页");
    	MyDepositeProductDetailPageObject myDepositeProductDetailPage=myDepositeProductPage.enterAcceptTransProductDetail(DEPOSITE_TRANSPRODUCT_NAME);
    	Assert.assertTrue(myDepositeProductDetailPage.verifyInthisPage(), "点击产品后未跳转到我的产品详情页面");
    	TransCancleDetailPageObject transCancleDetailPage=myDepositeProductDetailPage.cancelTrans();
    	Assert.assertTrue(transCancleDetailPage.verifyInthisPage(), "撤销转让后未跳转到撤销转让详情页面");
    	TransCancelResultPageObject transCancelResultPage=transCancleDetailPage.cancelTrans("123456");
    	Assert.assertTrue(transCancelResultPage.verifyInthisPage(), "撤销转让输入密码后未跳转到撤销转让结果页面");
    	String result=transCancelResultPage.getTransCancelResult();
    	Assert.assertTrue(result.equals("撤销成功")||result.contains("已受理"), "撤销转让失败，撤销转让结果为:"+result);
    }
    @Test(priority = 40,expectedExceptions = Exception.class, expectedExceptionsMessageRegExp="找不到下架产品",description="下架可转让产品")
    public void testOffTransProduct() throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	FinancialPageObject financialPage=homepage.enterFinancialPage();
    	Assert.assertTrue(financialPage.verifyInthisPage(), "点击首页理财入口，未出现理财页面");
    	financialPage.productPullOffAndFindProduct(DEPOSITE_TRANSPRODUCT_NAME);

    }
    @Test(priority = 41,description="我的定期产品已回款确认")
    public void testMyRegularProductRedeemInfo()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	PersonSettingPageObject personSettingPage=((MinePageObject)page).enterPersonSetting();
    	Assert.assertTrue(personSettingPage.verifyInthisPage(), "进入我的个人头像，未出现安全退出按钮");
    	LoginPageObject loginPageObject=personSettingPage.logOut();
    	Assert.assertTrue(loginPageObject.verifyInthisPage(), "退出后未跳转到登录页面");
    	page=loginPageObject.switchAccount("17052411184");
		pageName=page.getClass().getName();
		Assert.assertTrue(pageName.contains("LoginPageObject"), "验证手机号后未进入登录页面");
		GesturePwd gesturePwd=((LoginPageObject)page).login("hxnearc228");
		if(gesturePwd.verifyInthisPage()){
			Assert.assertTrue(gesturePwd.verifyInthisPage(),"登录后未跳转到手势密码页");
	    	Assert.assertEquals("请绘制您的手势密码", gesturePwd.verifyGestureTips(),"登录后未进入设置手密页");
	    	String result=gesturePwd.setFirstGesturePwd(1,4,7,8);
	    	Assert.assertEquals("请再画一次手势密码", result);
	    	boolean setNextGestureResult=gesturePwd.setNextGesturePwd(1,4,7,8).verifyIsInHomePage();
	    	Assert.assertTrue(setNextGestureResult, "第二次设置手势失败，未跳转到主页，主页我的入口未显示"); 
		}
    	page=homepage.enterPersonEntrace();
    	pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	MyDepositeProductPageObject myDepositeProductPage=((MinePageObject)page).enterMyDepositeProductPage();
    	Assert.assertTrue(myDepositeProductPage.verifyInthisPage(), "点击定期理财未进入我的理财产品页");
    	myDepositeProductPage.assertRedeemProduct(DEPOSITE_TRANSPRODUCT_NAME);
    	MyRedeemProductDetailPageObject myRedeemProductDetailPage=myDepositeProductPage.enterRedeemProductDetail(DEPOSITE_TRANSPRODUCT_NAME);
    	myRedeemProductDetailPage.assertProductDetail();
    }
    @Test(priority = 42,description="基金开户",enabled=false)
    public void testCreatFundAccount()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	PersonSettingPageObject personSettingPage=((MinePageObject)page).enterPersonSetting();
    	Assert.assertTrue(personSettingPage.verifyInthisPage(), "进入我的个人头像，未出现安全退出按钮");
    	LoginPageObject loginPageObject=personSettingPage.logOut();
    	Assert.assertTrue(loginPageObject.verifyInthisPage(), "退出后未跳转到登录页面");
    	String switchPhoneNum=Account.getLoginAccount();
    	page=loginPageObject.switchAccount(switchPhoneNum);
		pageName=page.getClass().getName();
		Assert.assertTrue(pageName.contains("LoginPageObject"), "验证手机号后未进入登录页面");
		((LoginPageObject)page).login(Account.getLoginPwd());	
		Assert.assertTrue(homepage.verifyIsInHomePage(),"登录后未跳转到主页"); 
		Account.setCurrentAccount(switchPhoneNum);
    	FinancialPageObject financialPage=homepage.enterFinancialPage();
    	Assert.assertTrue(financialPage.verifyInthisPage(), "点击首页理财入口，未出现理财页面");
    	FundDetailPageObject fundDetailPage=financialPage.clickFundProduct(FUND_PRODUCT_NAME);
    	Assert.assertTrue(fundDetailPage.verifyInthisPage(), "未进入基金详情页");
    	page=fundDetailPage.purchaseFund();
    	pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("FundAccountCreatPageObject"),switchPhoneNum+"该账户已开通基金账户");
    	FundAccountCreatVerifySmsCodePageObject fundAccountCreatVerifySmsCodePage=((FundAccountCreatPageObject)page).creatFundAccount();
    	Assert.assertTrue(fundAccountCreatVerifySmsCodePage.verifyInthisPage(), "确认开户后未跳转到短信验证码输入页面");
    	FundPurchasePageObject fundPurchasePage=fundAccountCreatVerifySmsCodePage.inputSmsCode(switchPhoneNum);
    	Assert.assertTrue(fundPurchasePage.verifyInthisPage(), "输入完毕验证码后未回到基金申购页面");
    }
    @Test(priority = 43,description="基金申购",enabled=false)
    public void testPurchaseFund()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	FinancialPageObject financialPage=homepage.enterFinancialPage();
    	Assert.assertTrue(financialPage.verifyInthisPage(), "点击首页理财入口，未出现理财页面");
    	FundDetailPageObject fundDetailPage=financialPage.clickFundProduct(FUND_PRODUCT_NAME);
    	Assert.assertTrue(fundDetailPage.verifyInthisPage(), "未进入基金详情页");
    	CommonAppiumPage page=fundDetailPage.purchaseFund();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("FundPurchasePageObject"),"当前账户未开通基金账户");
    	FundPurchaseResultPageObject fundPurchaseResultPage=((FundPurchasePageObject)page).purchaseFund("100", "456123");
    	Assert.assertTrue(fundPurchaseResultPage.verifyInthisPage(), "申购基金后未跳转到申购结果页面");
    	String purchaseResult=fundPurchaseResultPage.getPurchaseResult();
    	Assert.assertTrue(purchaseResult.contains("成功"), "申购失败，"+purchaseResult);
    }
    
    @Test(priority = 44,description="总资产展示")
    public void testTotalAssetsView()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	((MinePageObject)page).assertTotalAssetValue();
    	
    }
    @Test(priority = 45,description="掩码测试")
    public void testAssetsEye()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	((MinePageObject)page).openAssetEye();
    	AccountBanlancePageObject accountBanlancePage=((MinePageObject)page).enterAccountBanlancePage();
    	Assert.assertTrue(accountBanlancePage.verifyInthisPage(),"我的页面点击账户余额未进入账户余额页面");
    	Assert.assertFalse(accountBanlancePage.isBalanceMaskClose(),"打开掩码后账户余额中的掩码未打开");
    	((MinePageObject)page).backKeyEvent();
    	Assert.assertTrue(((MinePageObject)page).verifyInthisPage(),"账户余额返回后未回到我的页面");
    	((MinePageObject)page).closeAssetEye();
    	accountBanlancePage=((MinePageObject)page).enterAccountBanlancePage();
    	Assert.assertTrue(accountBanlancePage.verifyInthisPage(),"我的页面点击账户余额未进入账户余额页面");
    	Assert.assertTrue(accountBanlancePage.isBalanceMaskClose(),"关闭掩码后账户余额中的掩码未关闭");
    	
    }
    @Test(priority = 46,description="站内信测试")
    public void testMsg()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	String msgPrompt=((MinePageObject)page).getMsgPrompt();
    	MassegePageObject massegePage=((MinePageObject)page).gotoMsgPage();
    	Assert.assertTrue(massegePage.verifyInthisPage(), "我的页点击站内信图标未跳转到站内信页面");
    	massegePage.backKeyEvent();
    	Assert.assertTrue(((MinePageObject)page).verifyInthisPage(), "站内信页面返回未回到我的页");
    	String msgPrompt2=((MinePageObject)page).getMsgPrompt();
    	if(msgPrompt.equals("0")){
    		Assert.assertEquals(msgPrompt, msgPrompt2,"进入站内信前未读数量为0，进入站内信后返回数量不等于0，当前站内信数量:"+msgPrompt2);
    	}
    	else{
    		Assert.assertEquals(msgPrompt2, "0","站内信进入前消息数量为"+msgPrompt+",进入后返回站内信数量未清零，当前站内信数量："+msgPrompt2);
    	}
    }
    @Test(priority = 47,description="优惠券展示")
    public void testCouponView()throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	DBOperation dbOperation=new DBOperation();
    	dbOperation.deleteUserCoupon("17052411184");	//清除用户优惠券
    	CommonAppiumPage page=homepage.enterPersonEntrace();	
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	String couponCount=((MinePageObject)page).getCouponCount();
    	Assert.assertEquals(couponCount, "可用0张","清除优惠券后，我的页面优惠券展示应该为0");
    	//验证增加一张券前端展示
    	String couponId="2465";
    	Assert.assertTrue(dbOperation.addCouponToUser("17052411184", couponId), "加券失败");
    	((MinePageObject)page).refresh();
    	couponCount=((MinePageObject)page).getCouponCount();
    	Assert.assertEquals(couponCount, "新增1张","新增一张优惠券后，我的页面优惠券展示应该为1");
    	//验证查看新增券后的前端展示
    	MineCouponPageObject mineCouponPage=((MinePageObject)page).gotoCouponPage();
    	Assert.assertTrue(mineCouponPage.verifyInthisPage(),"我的页面点击优惠券未跳转到我的优惠券页面");
    	mineCouponPage.backKeyEvent();
    	Assert.assertTrue(((MinePageObject)page).verifyInthisPage(),"我的优惠券页面返回后未跳转到我的页面");
    	couponCount=((MinePageObject)page).getCouponCount();
    	Assert.assertEquals(couponCount, "可用1张","新增一张优惠券后，进入优惠券页面再返回前端应显示可用1张，当前显示:"+couponCount);
    	//验证优惠券投资跳转
    	mineCouponPage=((MinePageObject)page).gotoCouponPage();
    	Assert.assertTrue(mineCouponPage.verifyInthisPage(),"我的页面点击优惠券未跳转到我的优惠券页面");
    	FinancialPageObject financialPage=mineCouponPage.investByCoupon();
    	Assert.assertTrue(financialPage.verifyInthisPage(),"优惠券页面投资按钮点击后未跳转到理财页面");
    }
   
    /*
     * 请把赎回放最后一个用例*/
    @Test(priority = 55,description="赎回活期产品到余额")
    public void testRedeemToBalance() throws Exception{
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
		if(gesturePwd.verifyInthisPage()){
			Assert.assertTrue(gesturePwd.verifyInthisPage(),"登录后未跳转到手势密码页");
	    	Assert.assertEquals("请绘制您的手势密码", gesturePwd.verifyGestureTips(),"登录后未进入设置手密页");
	    	String result=gesturePwd.setFirstGesturePwd(1,4,7,8);
	    	Assert.assertEquals("请再画一次手势密码", result);
	    	boolean setNextGestureResult=gesturePwd.setNextGesturePwd(1,4,7,8).verifyIsInHomePage();
	    	Assert.assertTrue(setNextGestureResult, "第二次设置手势失败，未跳转到主页，主页我的入口未显示"); 
		}
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
    /*
     * 请把赎回放最后一个用例*/
    @Test(priority = 56,description="赎回活期产品到银行卡")
    public void testRedeemToBankCard() throws Exception{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	HomePageObject homepage=new HomePageObject(driver); 
    	FinancialPageObject FinancialPage=homepage.enterFinancialPage();
    	Assert.assertTrue(FinancialPage.verifyInthisPage(), "点击首页理财入口，未出现理财页面");
    	CurrentDepositProductDetailPageObject currentDepositProductDetailPage=FinancialPage.clickCurrentDepositProduct(CURRENTDEPOSITE_PRODUCT_NAME);
    	Assert.assertTrue(currentDepositProductDetailPage.verifyInthisPage(), "理财页点击活期产品后未出现活期理财产品详情页");
    	String redeemAmount="1";
    	RedeemPageObject redeemPage=currentDepositProductDetailPage.clickRansomBtn();
    	Assert.assertTrue(redeemPage.verifyInthisPage(), "点击赎回后未进入到赎回页");
    	RedeemResultPageObject redeemResultPage=redeemPage.redeemToBankCard(redeemAmount, "111111");
    	Assert.assertTrue(redeemResultPage.verifyInthisPage(), "赎回后未跳转到赎回结果页面");
    	String redeemResult=redeemResultPage.getRedeemResult();
    	Assert.assertTrue(redeemResult.equals("提交成功"), "投资失败，投资结果为:"+redeemResult);
    }

}
