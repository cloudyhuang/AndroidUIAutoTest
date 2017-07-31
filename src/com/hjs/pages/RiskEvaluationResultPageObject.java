package com.hjs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import com.hjs.config.CommonAppiumPage;

public class RiskEvaluationResultPageObject extends CommonAppiumPage{
	
	
	private By reEvaluationBtnLocator=By.xpath("//button[@class='re-poll-btn']");
	private By riskResultLocator=By.xpath("//div[@class='title']");
	public RiskEvaluationResultPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
		threadsleep(5000);
		contextWebview(driver);
	}
	public RiskEvaluationPageObject reEvaluation(){
		//clickEle(reEvaluationBtn,"重新评测按钮");
		AndroidElement reEvaluationBtn=driver.findElement(reEvaluationBtnLocator);
		reEvaluationBtn.click();
		this.backNativeApp();
		return new RiskEvaluationPageObject(driver);
	}
	public String getRiskResult(){
		AndroidElement riskResult=driver.findElement(riskResultLocator);
		return riskResult.getText();
	}
	public void backNativeApp(){
		contextNativeApp(driver);
	}
	public boolean verifyInthisPage(){
		return isElementExsit(reEvaluationBtnLocator);
	}

}
