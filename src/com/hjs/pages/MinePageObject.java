package com.hjs.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import com.hjs.config.CommonAppiumPage;
import com.hjs.publics.Util;

public class MinePageObject extends CommonAppiumPage{
	@AndroidFindBy(id="imageView_person")
	private AndroidElement personImage;		//个人头像
	@AndroidFindBy(id="imgv_msg")
	private AndroidElement msgImg;		//站内信图标
	@AndroidFindBy(id="tv_msg_prompt")
	private AndroidElement msgPrompt;		//站内信条数
	@AndroidFindBy(id="textView_totalAssets")
	private AndroidElement totalAssetsTV;		//总资产
	@AndroidFindBy(id="imageView_asset_eye")
	private AndroidElement assetEye;		//资产掩码
	@AndroidFindBy(xpath="//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textview_top' and @text='昨日收益(元)']/ancestor::android.widget.LinearLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/layout_top_bottom_text']//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textview_bottom']")
	private AndroidElement lastDayEarningsValue;		//昨日收益数值
	@AndroidFindBy(xpath="//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textview_top' and @text='累计收益(元)']/ancestor::android.widget.LinearLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/layout_top_bottom_text']//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textview_bottom']")
	private AndroidElement accumulatedIncomeValue;		//累计收益数值
	@AndroidFindBy(xpath="//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textview_top' and @text='账户余额']")
	private AndroidElement accountBanlance;		//账户余额
	@AndroidFindBy(xpath="//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textview_top' and @text='账户余额']/ancestor::android.widget.LinearLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/layout_top_bottom_text']//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textview_bottom']")
	private AndroidElement accountBanlanceValue;		//账户余额数值
	@AndroidFindBy(xpath="//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textview_top' and @text='恒存金']/ancestor::android.widget.LinearLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/layout_top_bottom_text']//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textview_bottom']")
	private AndroidElement hengCunJinValue;		//恒存金数值
	@AndroidFindBy(xpath="//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textview_top' and @text='定期理财']")
	private AndroidElement myDepositeProduct;		//定期理财
	@AndroidFindBy(xpath="//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textview_top' and @text='定期理财']/ancestor::android.widget.LinearLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/layout_top_bottom_text']//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textview_bottom']")
	private AndroidElement myDepositeProductValue;		//定期理财数值
	@AndroidFindBy(xpath="//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textview_top' and @text='基金']/ancestor::android.widget.LinearLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/layout_top_bottom_text']//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textview_bottom']")
	private AndroidElement fundValue;		//基金数值
	
	private By personImageLocator=By.id("imageView_person");	//个人头像Locator
	private By adCloseBtnLocator=By.id("icv_advertisement_close");		//广告关闭按钮locator
	
	public MinePageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
		this.closeAD();
	}
	public void closeAD(){
		if (isElementExsit(2,adCloseBtnLocator)){
			driver.findElement(adCloseBtnLocator).click();
		}
	}
	public MassegePageObject gotoMsgPage(){
		clickEle(msgImg,"站内信图标");
		return new MassegePageObject(driver);
	}
	public String getMsgPrompt(){
		if(super.isElementExsit(5, msgPrompt)){
			return super.getEleText(msgPrompt, "站内信条数");
		}
		else return "0";
	}
	public boolean assertTotalAssetValue(){
		String accountBanlanceValueStr=super.getEleText(accountBanlanceValue, "账户余额数值");
		String hengCunJinValueStr=super.getEleText(hengCunJinValue, "恒存金数值");
		String myDepositeProductValueStr=super.getEleText(myDepositeProductValue, "定期理财数值");
		String fundValueStr=super.getEleText(fundValue, "基金数值");
		String totalAssetsStr=super.getEleText(totalAssetsTV, "总资产");
		
		double doubleAccountBanlanceValueStr=Util.stringToDouble(Util.get2DecimalPointsNumInString(accountBanlanceValueStr));
		double doubleHengCunJinValueStr=Util.stringToDouble(Util.get2DecimalPointsNumInString(hengCunJinValueStr));
		double doubleMyDepositeProductValueStr=Util.stringToDouble(Util.get2DecimalPointsNumInString(myDepositeProductValueStr));
		double doubleFundValueStr=0;
		if(fundValueStr.equals("申购费率低 全场4折")){
			doubleFundValueStr=0;
		}
		else{
			doubleFundValueStr=Util.stringToDouble(Util.get2DecimalPointsNumInString(hengCunJinValueStr));
		}
		double doubleTotalAssetsStr=Util.stringToDouble(Util.get2DecimalPointsNumInString(totalAssetsStr));
		double expectTotalAssets=doubleAccountBanlanceValueStr+doubleHengCunJinValueStr+doubleMyDepositeProductValueStr+doubleFundValueStr;
		Assert.assertEquals(doubleTotalAssetsStr, expectTotalAssets,"实际app总资产显示："+doubleTotalAssetsStr+"预期总资产："+expectTotalAssets);
		return true;
	}
	public void openAssetEye() throws Exception{
		String totalAssetsStr=super.getEleText(totalAssetsTV, "总资产");
		String patt = "((-)?\\d{1,3})(,\\d{3})*(\\.\\d+)?$";	//含千分位分隔符的小数(包含正/负数)
		if(totalAssetsStr.matches(patt)){
			clickEle(assetEye,"资产掩码");
			String lastDayEarningsValueStr=super.getEleText(lastDayEarningsValue, "昨日收益数值");
			Assert.assertEquals(lastDayEarningsValueStr, "****");
			String accumulatedIncomeValueStr=super.getEleText(accumulatedIncomeValue, "累计收益数值");
			Assert.assertEquals(accumulatedIncomeValueStr, "****");
			String accountBanlanceValueStr=super.getEleText(accountBanlanceValue, "账户余额数值");
			Assert.assertEquals(accountBanlanceValueStr, "****");
			String hengCunJinValueStr=super.getEleText(hengCunJinValue, "恒存金数值");
			if(hengCunJinValueStr.equals("恒存金 灵活理财")){
			}
			else{
				Assert.assertEquals(hengCunJinValueStr, "****");
			}
			String myDepositeProductValueStr=super.getEleText(myDepositeProductValue, "定期理财数值");
			Assert.assertEquals(myDepositeProductValueStr, "****");
			String fundValueStr=super.getEleText(fundValue, "基金数值");
			if(fundValueStr.equals("申购费率低 全场4折")){
			}
			else{
				Assert.assertEquals(fundValueStr, "****");
			}
		}
		else if(totalAssetsStr.equals("****")){
			throw new Exception("当前已经是掩码");
		}
		else throw new Exception("总资产显示格式有误，非千分位小数或****");
	}
	public void closeAssetEye() throws Exception{
		String totalAssetsStr=super.getEleText(totalAssetsTV, "总资产");
		String patt = "((-)?\\d{1,3})(,\\d{3})*(\\.\\d+)?$";	//含千分位小数
		if(totalAssetsStr.matches(patt)){
			throw new Exception("当前是千分位显示，无需关闭掩码");
		}
		else if(totalAssetsStr.equals("****")){
			clickEle(assetEye,"资产掩码");
			String lastDayEarningsValueStr=super.getEleText(lastDayEarningsValue, "昨日收益数值");
			Assert.assertTrue(lastDayEarningsValueStr.matches(patt), "昨日收益打开掩码后未显示为千分位小数");
			String accumulatedIncomeValueStr=super.getEleText(accumulatedIncomeValue, "累计收益数值");
			Assert.assertTrue(accumulatedIncomeValueStr.matches(patt), "累计收益打开掩码后未显示为千分位小数");
			String accountBanlanceValueStr=super.getEleText(accountBanlanceValue, "账户余额数值");
			Assert.assertTrue(accountBanlanceValueStr.matches(patt), "账户余额打开掩码后未显示为千分位小数");
			String hengCunJinValueStr=super.getEleText(hengCunJinValue, "恒存金数值");
			if(hengCunJinValueStr.equals("恒存金 灵活理财")){
			}
			else{
				Assert.assertTrue(hengCunJinValueStr.matches(patt), "恒存金数值打开掩码后未显示为千分位小数");
			}
			String myDepositeProductValueStr=super.getEleText(myDepositeProductValue, "定期理财数值");
			Assert.assertTrue(myDepositeProductValueStr.matches(patt), "定期理财打开掩码后未显示为千分位小数");
			String fundValueStr=super.getEleText(fundValue, "基金数值");
			if(fundValueStr.equals("申购费率低 全场4折")){
			}
			else{
				Assert.assertTrue(fundValueStr.matches(patt), "基金数值打开掩码后未显示为千分位小数");
			}
		}
		else throw new Exception("总资产显示格式有误，非千分位小数或****");
	}
	
	public PersonSettingPageObject enterPersonSetting(){
		clickEle(personImage,"我的-个人头像");
		return new PersonSettingPageObject(driver);
	}
	public MyDepositeProductPageObject enterMyDepositeProductPage(){
		clickEle(myDepositeProduct,"定期理财");
		return new MyDepositeProductPageObject(driver);
	}
	public AccountBanlancePageObject enterAccountBanlancePage(){
		clickEle(accountBanlance,"账户余额入口");
		return new AccountBanlancePageObject(driver);
	}
	public boolean verifyInthisPage(){
		return isElementExsit(personImageLocator);
	}
}
