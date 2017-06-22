package com.hjs.cases;


import org.testng.Assert;
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
	private static final String DEPOSITE_PRODUCT_NAME="wj-SPV资管-转让测试1号";
	private static final String CURRENTDEPOSITE_PRODUCT_NAME="恒存金(10000038)";
    @Test(priority = 1,enabled=false)
    public void testBuyDepositProductByBankCardWithoutCoupon() throws Exception{
    	try{
    	new WelcomePageObject(driver).skipBackgroundAD();
	    GesturePwd gesturePwd=new GesturePwd(driver);
	    gesturePwd.inputGesturePwd(1,4,7,8);
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
    	finally{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	}
    }
    @Test(priority = 2,enabled=false)
    public void testBuyDepositProductByBankCardWithCashCoupon() throws Exception{
//    	new WelcomePageObject(driver).skipBackgroundAD();
//	    GesturePwd gesturePwd=new GesturePwd(driver);
//	    gesturePwd.inputGesturePwd(1,4,7,8);
    	try{
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
    	finally{
        	new HomePageObject(driver).backToHomePage(1,4,7,8);
        }
    }
    @Test(priority = 3,enabled=false)
    public void testBuyDepositProductByBankCardWithRateIncreasesCoupon() throws Exception{
//    	new WelcomePageObject(driver).skipBackgroundAD();
//	    GesturePwd gesturePwd=new GesturePwd(driver);
//	    gesturePwd.inputGesturePwd(1,4,7,8);
    	try{
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
    	finally{
        	new HomePageObject(driver).backToHomePage(1,4,7,8);
        }
    }
    @Test(priority = 4,enabled=false)
    public void testBuyDepositProductByBankCardWithoutCouponWithoutSmsCode() throws Exception{
    	try{
//    	new WelcomePageObject(driver).skipBackgroundAD();
//	    GesturePwd gesturePwd=new GesturePwd(driver);
//	    gesturePwd.inputGesturePwd(1,4,7,8);
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
    	finally{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	}
    }
    @Test(priority = 5,enabled=false)
    public void testBuyDepositProductByBankCardWithCashCouponWithoutSmsCode() throws Exception{
//    	new WelcomePageObject(driver).skipBackgroundAD();
//	    GesturePwd gesturePwd=new GesturePwd(driver);
//	    gesturePwd.inputGesturePwd(1,4,7,8);
    	try{
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
    	finally{
        	new HomePageObject(driver).backToHomePage(1,4,7,8);
        }
    }
    @Test(priority = 6,enabled=false)
    public void testBuyDepositProductByBankCardWithRateIncreasesCouponWithoutSmsCode() throws Exception{
//    	new WelcomePageObject(driver).skipBackgroundAD();
//	    GesturePwd gesturePwd=new GesturePwd(driver);
//	    gesturePwd.inputGesturePwd(1,4,7,8);
    	try{
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
    	finally{
        	new HomePageObject(driver).backToHomePage(1,4,7,8);
        }
    }
    @Test(priority = 7,enabled=false)
    public void testRecharge()throws Exception{
//    	new WelcomePageObject(driver).skipBackgroundAD();
//	    GesturePwd gesturePwd=new GesturePwd(driver);
//	    gesturePwd.inputGesturePwd(1,4,7,8);
    	try{
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
    	finally{
        	new HomePageObject(driver).backToHomePage(1,4,7,8);
        }
    }
    @Test(priority = 8,enabled=false)
    public void testBuyDepositProductByBanlanceWithoutCoupon() throws Exception{
//    	new WelcomePageObject(driver).skipBackgroundAD();
//	    GesturePwd gesturePwd=new GesturePwd(driver);
//	    gesturePwd.inputGesturePwd(1,4,7,8);
    	try{
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
    	finally{
        	new HomePageObject(driver).backToHomePage(1,4,7,8);
        }
    }
    @Test(priority = 9,enabled=false)
    public void testBuyDepositProductByBanlanceWithCashCoupon() throws Exception{
//    	new WelcomePageObject(driver).skipBackgroundAD();
//	    GesturePwd gesturePwd=new GesturePwd(driver);
//	    gesturePwd.inputGesturePwd(1,4,7,8);
    	try{
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
    	finally{
        	new HomePageObject(driver).backToHomePage(1,4,7,8);
        }
    }
    @Test(priority = 10,enabled=false)
    public void testBuyDepositProductByBanlanceWithRateIncreasesCoupon() throws Exception{
//    	new WelcomePageObject(driver).skipBackgroundAD();
//	    GesturePwd gesturePwd=new GesturePwd(driver);
//	    gesturePwd.inputGesturePwd(1,4,7,8);
    	try{
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
    	finally{
        	new HomePageObject(driver).backToHomePage(1,4,7,8);
        }
    }
    @Test(priority = 11,enabled=false)
    public void testBuyCurrentDepositProductByBanlanceWithoutCoupon() throws Exception{
//    	new WelcomePageObject(driver).skipBackgroundAD();
//	    GesturePwd gesturePwd=new GesturePwd(driver);
//	    gesturePwd.inputGesturePwd(1,4,7,8);
    	try{
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
    	finally{
        	new HomePageObject(driver).backToHomePage(1,4,7,8);
        }
    }
    @Test(priority = 12,enabled=false)
    public void testBuyCurrentDepositProductByBankCardWithoutCoupon() throws Exception{
//    	new WelcomePageObject(driver).skipBackgroundAD();
//	    GesturePwd gesturePwd=new GesturePwd(driver);
//	    gesturePwd.inputGesturePwd(1,4,7,8);
    	try{
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
    	finally{
        	new HomePageObject(driver).backToHomePage(1,4,7,8);
        }
    }
    @Test(priority = 13,enabled=false)
    public void testBuyDepositProductFailByBankCardWithoutCoupon() throws Exception{
    	try{
    	new WelcomePageObject(driver).skipBackgroundAD();
	    GesturePwd gesturePwd=new GesturePwd(driver);
	    gesturePwd.inputGesturePwd(1,4,7,8);
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
    	finally{
    	new HomePageObject(driver).backToHomePage(1,4,7,8);
    	}
    }
    @Test(priority = 14,enabled=false)
    public void testWithdrawCash()throws Exception{
//    	new WelcomePageObject(driver).skipBackgroundAD();
//	    GesturePwd gesturePwd=new GesturePwd(driver);
//	    gesturePwd.inputGesturePwd(1,4,7,8);
    	try{
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
    	WithdrawCashResultPageObject withdrawCashResultPage=withdrawCashPage.withdrawCash(stringHalfWithdrawCash, "123456");
    	Assert.assertTrue(withdrawCashResultPage.verifyInthisPage(), "提现输入密码后未跳转到提现结果页");
    	Assert.assertTrue(withdrawCashResultPage.getWithdrawCashResult().contains("申请成功"), "提现申请失败，失败信息为："+withdrawCashResultPage.getWithdrawCashResult());
    	accountBanlancePage=withdrawCashResultPage.gotoBalancePage();
    	int actualCash=accountBanlancePage.getAvailableBalance();
    	Assert.assertEquals(actualCash, expectCash,"实际剩余余额为"+actualCash+"预期剩余余额为"+expectCash);
    	}
    	finally{
        	new HomePageObject(driver).backToHomePage(1,4,7,8);
        }
    }
    @Test(priority = 15,enabled=false)
    public void testMyDepositeProductInfo()throws Exception{
    	new WelcomePageObject(driver).skipBackgroundAD();
	    GesturePwd gesturePwd=new GesturePwd(driver);
	    gesturePwd.inputGesturePwd(1,4,7,8);
    	try{
    	HomePageObject homepage=new HomePageObject(driver); 
    	CommonAppiumPage page=homepage.enterPersonEntrace();
    	String pageName=page.getClass().getName();
    	Assert.assertTrue(pageName.contains("MinePageObject"), "点击首页-我的入口未进入我的页面");
    	MyDepositeProductPageObject myDepositeProductPage=((MinePageObject)page).enterMyDepositeProductPage();
    	Assert.assertTrue(myDepositeProductPage.verifyInthisPage(), "点击定期理财未进入我的理财产品页");
    	String productName="定期产品1231号";
    	MyDepositeProductDetailPageObject myDepositeProductDetailPage=myDepositeProductPage.enterDepositeProductDetail(productName);
    	Assert.assertTrue(myDepositeProductDetailPage.verifyInthisPage(), "点击"+productName+"产品未进入我的理财产品详情页");
    	myDepositeProductDetailPage.assertMtpAndAppDetail("17052411184");
    	}
    	finally{
        	new HomePageObject(driver).backToHomePage(1,4,7,8);
        }
    }
    @Test(priority = 16)
	public void testFinancialPageProductInfo() throws Exception{
    	new WelcomePageObject(driver).skipBackgroundAD();
	    GesturePwd gesturePwd=new GesturePwd(driver);
	    gesturePwd.inputGesturePwd(1,4,7,8);
	    try{
	    HomePageObject homepage=new HomePageObject(driver); 
    	FinancialPageObject FinancialPage=homepage.enterFinancialPage();
    	Assert.assertTrue(FinancialPage.verifyInthisPage(), "点击首页理财入口，未出现理财页面");
    	FinancialPage.testProductInfo();
	    }
	    finally{
        	new HomePageObject(driver).backToHomePage(1,4,7,8);
        }
	}
    /*
     * 请把赎回放最后一个用例*/
    @Test(priority = 40,enabled=false)
    public void testRedeemCurrentDepositToBanlance() throws Exception{
    	new WelcomePageObject(driver).skipBackgroundAD();
	    GesturePwd gesturePwd=new GesturePwd(driver);
	    gesturePwd.inputGesturePwd(1,4,7,8);
    	try{
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
		gesturePwd=((LoginPageObject)page).login("a123456");
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
    	finally{
        	new HomePageObject(driver).backToHomePage();
        }
    }

}
