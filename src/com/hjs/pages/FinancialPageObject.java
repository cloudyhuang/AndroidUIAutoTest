package com.hjs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.testng.Assert;

import com.hjs.Interface.InitProduct;
import com.hjs.config.CommonAppiumPage;
import com.hjs.publics.Util;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class FinancialPageObject extends CommonAppiumPage{
	@AndroidFindBy(id="image")
	private AndroidElement bannerImage;		//banner图
	@AndroidFindBy(id="top_seperate_block")
	private AndroidElement seperateBlock;		//空隙条
	
	
	private By titleSwitchLocator=By.id("title_switch");
	private By refreshViewLocator=By.id("refresh_animationView");
	public FinancialPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public void clickBannerImage(){
		clickEle(bannerImage,"banner图");
	}
	public DepositProductDetailPageObject clickDepositProduct(String DepositProductName) throws Exception{
		//driver.scrollTo(DepositProductName);
		this.clickWenJian();
		waitEleUnVisible(refreshViewLocator, 30);
		String productXpath="//android.widget.TextView[@text='"+DepositProductName+"']";
		super.scrollTo(productXpath);
		AndroidElement DepositProduct=driver.findElement(By.xpath("//android.widget.TextView[@text='"+DepositProductName+"']"));
		clickEle(DepositProduct,"定期产品名称："+DepositProductName);
		return new DepositProductDetailPageObject(driver);
	}
	public DepositProductDetailPageObject clickFirstDepositProduct() throws Exception{
		this.clickWenJian();
		waitEleUnVisible(refreshViewLocator, 30);
		String depositeTitle="//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/group_title' and @text='定期']";
		super.scrollTo(depositeTitle);
		String firstProductXpath="//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/group_title' and @text='定期']/ancestor::android.widget.LinearLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/item_financial_home_privilege']//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_title']";
		AndroidElement DepositProduct=driver.findElement(By.xpath(firstProductXpath));
		clickEle(DepositProduct,"第一个定期产品");
		return new DepositProductDetailPageObject(driver);
	}
	public DepositGroupBuyProductDetailPageObject clickDepositGroupBuyProduct(String DepositGroupBuyProductName) throws Exception{
		//driver.scrollTo(DepositProductName);
		this.clickWenJian();
		waitEleUnVisible(refreshViewLocator, 30);
		String productXpath="//android.widget.TextView[@text='"+DepositGroupBuyProductName+"']";
		super.scrollTo(productXpath);
		AndroidElement DepositProduct=driver.findElement(By.xpath("//android.widget.TextView[@text='"+DepositGroupBuyProductName+"']"));
		clickEle(DepositProduct,"定期团购产品名称："+DepositGroupBuyProductName);
		return new DepositGroupBuyProductDetailPageObject(driver);
	}
	public DepositTransProductDetailPageObject clickDepositTransProduct(String DepositTransProductName) throws Exception{
		//driver.scrollTo(DepositProductName);
		this.clickZhuanRang();
		waitEleUnVisible(refreshViewLocator, 30);
		String productXpath="//android.widget.TextView[@text='"+DepositTransProductName+"']";
		super.scrollTo(productXpath);
		AndroidElement DepositProduct=driver.findElement(By.xpath("//android.widget.TextView[@text='"+DepositTransProductName+"']"));
		clickEle(DepositProduct,"转让产品名称："+DepositTransProductName);
		return new DepositTransProductDetailPageObject(driver);
	}
	public CurrentDepositProductDetailPageObject clickCurrentDepositProduct(String DepositProductName) throws Exception{
		//driver.scrollTo(DepositProductName);
		this.clickWenJian();
		waitEleUnVisible(refreshViewLocator, 30);
		String productXpath="//android.widget.TextView[@text='"+DepositProductName+"']";
		super.scrollTo(productXpath);
		AndroidElement DepositProduct=driver.findElement(By.xpath("//android.widget.TextView[@text='"+DepositProductName+"']"));
		clickEle(DepositProduct,"活期产品名称："+DepositProductName);
		return new CurrentDepositProductDetailPageObject(driver);
	}
	public FundDetailPageObject clickFundProduct(String fundProdName) throws Exception{
		//driver.scrollTo(DepositProductName);
		this.clickJiJin();
		waitEleUnVisible(refreshViewLocator, 30);
		String productXpath="//android.widget.TextView[@text='"+fundProdName+"']";
		super.scrollTo(productXpath);
		AndroidElement fundProduct=driver.findElement(By.xpath("//android.widget.TextView[@text='"+fundProdName+"']"));
		clickEle(fundProduct,"基金产品名称："+fundProdName);
		return new FundDetailPageObject(driver);
	}
	public String testProductInfo() throws Exception{
		String currentDate=Util.getcurrentDate();
		String productName="黄XAutoTest产品"+ currentDate;
		String minBuyAmt="100";
		String baseProductName="黄XAutoTest基础产品" + currentDate;
		String productLimit="365";
		String displayRate="6";
		String mrktPlusRate="0.01";
		InitProduct product = new InitProduct.Builder(productName).setMinBuyAmt(minBuyAmt)
				.setBaseProductName(baseProductName)
				.setProductLimit(productLimit).setDisplayRate(displayRate)
				.setMrktPlusRate(mrktPlusRate).build();
		product.creatProduct();
		this.clickWenJian();	//点击稳健标签
		waitEleUnVisible(refreshViewLocator, 30);
		super.threadsleep(60000);  //等待job刷新app前端产品
		swipeToDown(seperateBlock,1000,1);	//从空隙条开始下滑刷新
		waitEleUnVisible(refreshViewLocator, 30);
		swipeToDown(seperateBlock,1000,1);	//从空隙条开始下滑刷新
		waitEleUnVisible(refreshViewLocator, 30);
		threadsleep(5000);
		String productXpath="//android.widget.TextView[@text='"+productName+"']";
		super.scrollTo(productXpath);
		AndroidElement baseProfitEle=driver.findElement(By.xpath("//android.widget.TextView[@text='"+productName+"']/ancestor::android.widget.LinearLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/item_financial_home_privilege']//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/base_profit']"));
		AndroidElement extraProfitEle=driver.findElement(By.xpath("//android.widget.TextView[@text='"+productName+"']/ancestor::android.widget.LinearLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/item_financial_home_privilege']//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/extra_profit']"));
		AndroidElement productLimitAndMinBuyAmtEle=driver.findElement(By.xpath("//android.widget.TextView[@text='"+productName+"']/ancestor::android.widget.LinearLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/item_financial_home_privilege']//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/home_page_item_time']"));
		String expectBaseProfit=displayRate.split("\\+")[0];
		String appBaseProfit=Util.getNumInString(baseProfitEle.getText());//前端显示x%,取出x
		Assert.assertEquals(appBaseProfit,expectBaseProfit,"前端显示收益率与OMC配置不同，OMC："+expectBaseProfit+"前端显示："+appBaseProfit);
		
		String expectExtraProfit=mrktPlusRate;
		String appExtraProfit=Util.getNumInString(extraProfitEle.getText());//前端显示x%,取出x
		double doubleAppExtraProfit=Util.stringToDouble(appExtraProfit);
		doubleAppExtraProfit=doubleAppExtraProfit/100;
		appExtraProfit=String.valueOf(doubleAppExtraProfit);
		Assert.assertEquals(appExtraProfit, expectExtraProfit,"前端显示额外收益率与OMC配置不同，OMC："+expectExtraProfit+"前端显示："+appExtraProfit);
		String appProductLimit=Util.getNumInString(productLimitAndMinBuyAmtEle.getText().split("\\|")[0]); //前端显示x 天  |  y元起 ,取出x
		Assert.assertEquals(appProductLimit,productLimit,"前端显示投资期限与OMC配置不同，OMC："+productLimit+"前端显示："+appProductLimit);	//验证投资期限
		String appMinBuyAmt=Util.getNumInString(productLimitAndMinBuyAmtEle.getText().split("\\|")[1]);	//前端显示x 天  |  y元起 ,取出y
		Assert.assertEquals(appMinBuyAmt,minBuyAmt,"前端显示起投金额与OMC配置不同，OMC："+minBuyAmt+"前端显示："+appMinBuyAmt);	//验证起投金额
		return productName;
	}
	public String testGroupBuyProductInfo() throws Exception{
		String currentDate=Util.getcurrentDate();
		String productName="黄XAutoTest团购产品"+ currentDate;
		String minBuyAmt="100";
		String baseProductName="黄XAutoTest团购基础产品" + currentDate;
		String productLimit="365";
		String displayRate="6";
		String mrktPlusRate="0.01";
		String groupBuyId="159";
		InitProduct product = new InitProduct.Builder(productName).setMinBuyAmt(minBuyAmt)
				.setBaseProductName(baseProductName)
				.setProductLimit(productLimit).setDisplayRate(displayRate)
				.setMrktPlusRate(mrktPlusRate).setGroupBuyId(groupBuyId).build();
		product.creatProduct();
		this.clickWenJian();	//点击稳健标签
		waitEleUnVisible(refreshViewLocator, 30);
		super.threadsleep(5000);  //等待job刷新app前端产品
		swipeToDown(seperateBlock,1000,1);	//从空隙条开始下滑刷新
		waitEleUnVisible(refreshViewLocator, 30);
		swipeToDown(seperateBlock,1000,1);	//从空隙条开始下滑刷新
		waitEleUnVisible(refreshViewLocator, 30);
		String productXpath="//android.widget.TextView[@text='"+productName+"']";
		super.scrollTo(productXpath);
		AndroidElement baseProfitEle=driver.findElement(By.xpath("//android.widget.TextView[@text='"+productName+"']/ancestor::android.widget.LinearLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/item_financial_home_privilege']//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/base_profit']"));
		AndroidElement extraProfitEle=driver.findElement(By.xpath("//android.widget.TextView[@text='"+productName+"']/ancestor::android.widget.LinearLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/item_financial_home_privilege']//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/extra_profit']"));
		AndroidElement productLimitAndMinBuyAmtEle=driver.findElement(By.xpath("//android.widget.TextView[@text='"+productName+"']/ancestor::android.widget.LinearLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/item_financial_home_privilege']//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/home_page_item_time']"));
		String expectBaseProfit=displayRate.split("\\+")[0];
		String appBaseProfit=Util.getNumInString(baseProfitEle.getText());//前端显示x%,取出x
		Assert.assertEquals(appBaseProfit,expectBaseProfit,"前端显示收益率与OMC配置不同，OMC："+expectBaseProfit+"前端显示："+appBaseProfit);
		
		String expectExtraProfit=mrktPlusRate;
		String appExtraProfit=Util.getNumInString(extraProfitEle.getText());//前端显示x%,取出x
		double doubleAppExtraProfit=Util.stringToDouble(appExtraProfit);
		doubleAppExtraProfit=doubleAppExtraProfit/100;
		appExtraProfit=String.valueOf(doubleAppExtraProfit);
		Assert.assertEquals(appExtraProfit, expectExtraProfit,"前端显示额外收益率与OMC配置不同，OMC："+expectExtraProfit+"前端显示："+appExtraProfit);
		String appProductLimit=Util.getNumInString(productLimitAndMinBuyAmtEle.getText().split("\\|")[0]); //前端显示x 天  |  y元起 ,取出x
		Assert.assertEquals(appProductLimit,productLimit,"前端显示投资期限与OMC配置不同，OMC："+productLimit+"前端显示："+appProductLimit);	//验证投资期限
		String appMinBuyAmt=Util.getNumInString(productLimitAndMinBuyAmtEle.getText().split("\\|")[1]);	//前端显示x 天  |  y元起 ,取出y
		Assert.assertEquals(appMinBuyAmt,minBuyAmt,"前端显示起投金额与OMC配置不同，OMC："+minBuyAmt+"前端显示："+appMinBuyAmt);	//验证起投金额
		return productName;
	}
	public String testTransSPVProductInfo() throws Exception{
		String currentDate=Util.getcurrentDate();
		String productName="黄XAutoTest可转让产品"+ currentDate;
		String minBuyAmt="100";
		String baseProductName="黄XAutoTest可转让基础产品" + currentDate;
		String productLimit="365";
		String displayRate="6";
		String mrktPlusRate="0.01";
		boolean isSupportTrans=true;
		String transRuleTmplId="133";
		InitProduct product = new InitProduct.Builder(productName).setMinBuyAmt(minBuyAmt)
				.setBaseProductName(baseProductName).setProductLimit(productLimit).setDisplayRate(displayRate)
				.setMrktPlusRate(mrktPlusRate).setIsSupportTrans(isSupportTrans).setTransRuleTmplId(transRuleTmplId).build();
		product.creatProduct();
		this.clickWenJian();	//点击稳健标签
		waitEleUnVisible(refreshViewLocator, 30);
		super.threadsleep(5000);  //等待job刷新app前端产品
		swipeToDown(seperateBlock,1000,1);	//从空隙条开始下滑刷新
		waitEleUnVisible(refreshViewLocator, 30);
		swipeToDown(seperateBlock,1000,1);	//从空隙条开始下滑刷新
		waitEleUnVisible(refreshViewLocator, 30);
		threadsleep(5000);
		String productXpath="//android.widget.TextView[@text='"+productName+"']";
		super.scrollTo(productXpath);
		AndroidElement baseProfitEle=driver.findElement(By.xpath("//android.widget.TextView[@text='"+productName+"']/ancestor::android.widget.LinearLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/item_financial_home_privilege']//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/base_profit']"));
		AndroidElement extraProfitEle=driver.findElement(By.xpath("//android.widget.TextView[@text='"+productName+"']/ancestor::android.widget.LinearLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/item_financial_home_privilege']//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/extra_profit']"));
		AndroidElement productLimitAndMinBuyAmtEle=driver.findElement(By.xpath("//android.widget.TextView[@text='"+productName+"']/ancestor::android.widget.LinearLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/item_financial_home_privilege']//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/home_page_item_time']"));
		String expectBaseProfit=displayRate.split("\\+")[0];
		String appBaseProfit=Util.getNumInString(baseProfitEle.getText());//前端显示x%,取出x
		Assert.assertEquals(appBaseProfit,expectBaseProfit,"前端显示收益率与OMC配置不同，OMC："+expectBaseProfit+"前端显示："+appBaseProfit);
		
		String expectExtraProfit=mrktPlusRate;
		String appExtraProfit=Util.getNumInString(extraProfitEle.getText());//前端显示x%,取出x
		double doubleAppExtraProfit=Util.stringToDouble(appExtraProfit);
		doubleAppExtraProfit=doubleAppExtraProfit/100;
		appExtraProfit=String.valueOf(doubleAppExtraProfit);
		Assert.assertEquals(appExtraProfit, expectExtraProfit,"前端显示额外收益率与OMC配置不同，OMC："+expectExtraProfit+"前端显示："+appExtraProfit);
		String appProductLimit=Util.getNumInString(productLimitAndMinBuyAmtEle.getText().split("\\|")[0]); //前端显示x 天  |  y元起 ,取出x
		Assert.assertEquals(appProductLimit,productLimit,"前端显示投资期限与OMC配置不同，OMC："+productLimit+"前端显示："+appProductLimit);	//验证投资期限
		String appMinBuyAmt=Util.getNumInString(productLimitAndMinBuyAmtEle.getText().split("\\|")[1]);	//前端显示x 天  |  y元起 ,取出y
		Assert.assertEquals(appMinBuyAmt,minBuyAmt,"前端显示起投金额与OMC配置不同，OMC："+minBuyAmt+"前端显示："+appMinBuyAmt);	//验证起投金额
		return productName;
	}
	public void productPullOffAndFindProduct(String productName) throws Exception{
		InitProduct product = new InitProduct.Builder("").build();
		product.productPullOffShelves(productName);
		this.clickWenJian();	//点击稳健标签
		waitEleUnVisible(refreshViewLocator, 30);
		super.threadsleep(5000);  //等待job刷新app前端产品
		swipeToDown(seperateBlock,1000,1);	//从空隙条开始下滑刷新
		waitEleUnVisible(refreshViewLocator, 30);
		swipeToDown(seperateBlock,1000,1);	//从空隙条开始下滑刷新
		waitEleUnVisible(refreshViewLocator, 30);
		threadsleep(5000);
		String productXpath="//android.widget.TextView[@text='"+productName+"']";
		try {
			super.scrollTo(productXpath);
		} catch (Exception e) {
			throw new Exception("找不到下架产品");
		}
	}
	public void clickWenJian(){
		AndroidElement titleSwitchEle=driver.findElement(titleSwitchLocator);
		Point elpoint = titleSwitchEle.getLocation();
    	Dimension elSize = titleSwitchEle.getSize();
    	int startX = elpoint.getX();
    	int startY = elpoint.getY();
    	int endX =elSize.getWidth()+startX;
    	int endY =elSize.getHeight()+startY;
    	int centerX = (startX+endX)/4;
    	int centerY = (startY+endY)/2;
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        HashMap<String, Double> tapObject = new HashMap<String, Double>();
//        tapObject.put("x", centerX);
//        tapObject.put("y", centerY);
//        tapObject.put("duration", 0.0);
//        tapObject.put("touchCount", 1.0);
//        tapObject.put("tapCount", 3.0);
//        js.executeScript("mobile: tap", tapObject);
    	new TouchAction((MobileDriver) driver).tap(centerX, centerY).perform();
	}
	public void clickZhuanRang(){
		AndroidElement titleSwitchEle=driver.findElement(titleSwitchLocator);
		Point elpoint = titleSwitchEle.getLocation();
    	Dimension elSize = titleSwitchEle.getSize();
    	int startX = elpoint.getX();
    	int startY = elpoint.getY();
    	int endX =elSize.getWidth()+startX;
    	int endY =elSize.getHeight()+startY;
    	int centerX = (int) ((startX+endX)*0.38);
    	int centerY = (startY+endY)/2;
    	new TouchAction((MobileDriver) driver).tap(centerX, centerY).perform();
	}
	public void clickJiJin(){
		AndroidElement titleSwitchEle=driver.findElement(titleSwitchLocator);
		Point elpoint = titleSwitchEle.getLocation();
    	Dimension elSize = titleSwitchEle.getSize();
    	int startX = elpoint.getX();
    	int startY = elpoint.getY();
    	int endX =elSize.getWidth()+startX;
    	int endY =elSize.getHeight()+startY;
    	int centerX = (int) ((startX+endX)*0.61);
    	int centerY = (startY+endY)/2;
    	new TouchAction((MobileDriver) driver).tap(centerX, centerY).perform();
	}
	public boolean verifyInthisPage(){
		return isElementExsit(titleSwitchLocator);
	}
}
