package com.hjs.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import com.hjs.config.CommonAppiumPage;

public class RiskEvaluationPageObject extends CommonAppiumPage{
	@AndroidFindBy(xpath="//button[@class='btn-start']")
	private AndroidElement startRiskEvaluationBtn;		//开始测评按钮
	@AndroidFindBy(xpath="//button[@class='submitBtn']")
	private AndroidElement submitBtn;		//提交按钮
	@AndroidFindBy(id="dlg_msg_rightbtn")
	private AndroidElement dlgConfirmBtn;		//对话框确认按钮
	
	private By startRiskEvaluationBtnLocator=By.xpath("//button[@class='btn-start']");
	private By submitBtnLocator=By.xpath("//button[@class='submitBtn']");
	public RiskEvaluationPageObject(AndroidDriver<AndroidElement> androidDriver) {
		super(androidDriver);
		threadsleep(5000);
		contextWebview(driver);
	}
	public RiskEvaluationResultPageObject startRiskEvaluation(String riskLevle) throws Exception{
		if(super.isElementExsit(startRiskEvaluationBtnLocator)){
			WebElement startEvaluationBtn=driver.findElement(startRiskEvaluationBtnLocator);
			startEvaluationBtn.click();
		}
		String riskOptionXpath=getRiskOptionXpath(riskLevle);
		List<AndroidElement> bestRiskOption=driver.findElements(By.xpath(riskOptionXpath));
		for(int i=0;i<bestRiskOption.size();i++){
			bestRiskOption.get(i).click();
			threadsleep(500);
		}
		WebElement submitBtn=driver.findElement(submitBtnLocator);
		submitBtn.click();
		contextNativeApp(driver);
		clickEle(dlgConfirmBtn,"对话框确认按钮");
		return new RiskEvaluationResultPageObject(driver);
	}
	public String getRiskOptionXpath(String riskLevle) throws Exception{
		String xpath;
		switch(riskLevle){
		case "R1":
			xpath="//div[@class='questions']/div[@class='answer-area'][1]/div";
			break;
		case "R2":
			xpath="//div[@class='questions']/div[@class='answer-area'][2]/div";
			break;
		case "R3":
			xpath="//div[@class='questions']/div[@class='answer-area'][3]/div";
			break;
		case "R4":
			xpath="//div[@class='questions']/div[@class='answer-area'][last()]/div";
			break;
		default:
			throw new Exception("风险等级参数有误，应为R1、R2、R3、R4一种");
		}
		return xpath;
	}
	public String riskLevelToRiskResult(String riskLevel) throws Exception{
		String riskResult;
		switch(riskLevel){
		case "R1":
			riskResult="保守型";
			break;
		case "R2":
			riskResult="平衡型";
			break;
		case "R3":
			riskResult="成长型";
			break;
		case "R4":
			riskResult="进取型";
			break;
		default:
			throw new Exception("风险等级参数有误，应为R1、R2、R3、R4一种");
		}
		return riskResult;
	}
	public boolean verifyInthisPage(){
		return isElementExsit(startRiskEvaluationBtnLocator);
	}
}
