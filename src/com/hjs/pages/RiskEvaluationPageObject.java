package com.hjs.pages;

import java.util.ArrayList;
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
		threadsleep(15000);
		contextWebview(driver);
	}
	public RiskEvaluationResultPageObject startRiskEvaluation(String riskLevle) throws Exception{
		if(super.isElementExsit(5,startRiskEvaluationBtnLocator)){
			WebElement startEvaluationBtn=driver.findElement(startRiskEvaluationBtnLocator);
			startEvaluationBtn.click();
		}
		waitForVisible(submitBtnLocator, 15);
		String[] riskOptionXpath=getRiskOptionXpath(riskLevle);
		List<AndroidElement> bestRiskOption=new ArrayList<AndroidElement>();
		for(int i=0;i<riskOptionXpath.length;i++){
			bestRiskOption.add(driver.findElement(By.xpath(riskOptionXpath[i])));
		}
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
	public String[] getRiskOptionXpath(String riskLevle) throws Exception{
		String xpath[]=new String[20];
		int optionArr[]=this.getRiskOptionArray(riskLevle);
		for(int i=0;i<optionArr.length;i++){
			xpath[i]="//div[@class='questions']"+"["+(i+1)+"]"+"/div[@class='answer-area']["+(optionArr[i]+1)+"]/div";
		}
//		switch(riskLevle){
//		case "R1":
//			xpath="//div[@class='questions']/div[@class='answer-area'][1]/div";
//			break;
//		case "R2":
//			xpath="//div[@class='questions']/div[@class='answer-area'][2]/div";
//			break;
//		case "R3":
//			xpath="//div[@class='questions']/div[@class='answer-area'][3]/div";
//			break;
//		case "R4":
//			xpath="//div[@class='questions']/div[@class='answer-area'][last()]/div";
//			break;
//		default:
//			throw new Exception("风险等级参数有误，应为R1、R2、R3、R4一种");
//		}
		return xpath;
	}
	public int[] getRiskOptionArray(String riskLevle) throws Exception{
		int arr[][] = new int[20][];	//20道题目
		arr[0] = new int[] { 3, 2, 1, 1, 0 };	//每题对应分值
		arr[1] = new int[] { 5, 7, 3, 2, 0 };
		arr[2] = new int[] { 1, 2, 4, 5 };
		arr[3] = new int[] { 4, 3, 2, 1, 0 };
		arr[4] = new int[] { 1, 3, 4, 5, 6 };
		arr[5] = new int[] { 3, 2, 1, 0 };
		arr[6] = new int[] { 0, 1, 2, 3 };
		arr[7] = new int[] { 5, 5, 5, 0 };
		arr[8] = new int[] { 1, 3, 5 };
		arr[9] = new int[] { 1, 2, 3, 5 };
		arr[10] = new int[] { 1, 2, 3, 4 };
		arr[11] = new int[] { 1, 3, 4, 6 };
		arr[12] = new int[] { 0, 1, 2, 4, 6 };
		arr[13] = new int[] { 1, 2, 3, 4, 0 };
		arr[14] = new int[] { 1, 3, 5 };
		arr[15] = new int[] { 2, 4, 5, 6, 6 };
		arr[16] = new int[] { 0, 1, 3, 5, 6 };
		arr[17] = new int[] { 0, 2, 3, 4 };
		arr[18] = new int[] { 0, 1, 2, 4, 5 };
		arr[19] = new int[] { 7, 5, 4, 3, 1 };
		int b[]=new int[20];
		switch(riskLevle){
		case "R1":	//选分数最少的选项
			for (int i = 0; i < arr.length; i++) {
				int min=100;
				int mini=0;
				for(int j=0;j<arr[i].length;j++){
					if(arr[i][j]<min) {
						min=arr[i][j];
						mini=j;
					}
				}
				b[i]=mini;
			}
			break;
		case "R2":	//选分数第二少的选项
			for (int i = 0; i < arr.length; i++) {
				int min=arr[i][0];
				int min2=min;
				int mini=0;
				int min2i=mini;
				for(int j=0;j<arr[i].length;j++){
					if((min==min2)&&(arr[i][j]>min2)){
						min2=arr[i][j];
						min2i=j;
					}
					else if(arr[i][j]<min) {
						min2=min;
						min2i=mini;
						min=arr[i][j];
						mini=j;
					}
				}
				b[i]=min2i;
			}
			break;
		case "R3":	//选分数第二大的选项
			for (int i = 0; i < arr.length; i++) {
				int max=arr[i][0];
				int max2=max;
				int maxi=0;
				int max2i=maxi;
				for(int j=0;j<arr[i].length;j++){
					if((max==max2)&&(arr[i][j]<max2)){
						max2=arr[i][j];
						max2i=j;
					}
					else if(arr[i][j]>max) {
						max2=max;
						max2i=maxi;
						max=arr[i][j];
						maxi=j;
					}
				}
				b[i]=max2i;
			}
			break;
		case "R4":	//选分数最大的选项
			for (int i = 0; i < arr.length; i++) {
				int max=0;
				int maxi=0;
				for(int j=0;j<arr[i].length;j++){
					if(arr[i][j]>max) {
						max=arr[i][j];
						maxi=j;
					}
				}
				b[i]=maxi;
			}
			break;
		default:
			throw new Exception("风险等级参数有误，应为R1、R2、R3、R4一种");
		}
		return b;
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
