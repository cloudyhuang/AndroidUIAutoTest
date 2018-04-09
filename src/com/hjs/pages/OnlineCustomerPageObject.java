package com.hjs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import com.hjs.config.CommonAppiumPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
* @author huangxiao
* @version 创建时间：2018年3月26日 上午10:57:03
* 类说明
*/
public class OnlineCustomerPageObject extends CommonAppiumPage{
	@FindBy(xpath="//div[@id='floatp']//input[@id='inputwidth']")
	private AndroidElement input;		//输入框
	@FindBy(xpath="//div[@id='floatp']//button[@class='btn']")
	private AndroidElement sendMsgBtn;		//发送消息按钮
	
	private By sendMsgBtnLocator=By.xpath("//div[@id='floatp']//button[@class='btn']");
	public OnlineCustomerPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
		threadsleep(15000);
		contextWebview(driver);
	}
	public boolean testSendMsg(){
		String testMsg="测试消息";
		this.sendMsg(testMsg);
		String lastAuthorSendMsg=this.getLastAuthorInlineText();
		Assert.assertEquals(testMsg, lastAuthorSendMsg);
		String lastInlineText=this.getLastInlineText();
		Assert.assertEquals(lastInlineText, "如需帮助，请点击人工");
		this.backNativeApp();
		return true;
	}
	public String getLastInlineText(){
		WebElement lastLineEle=driver.findElement(By.xpath("//div[@class='messages']/ul[@class='message']/li[last()]"));
		return lastLineEle.getText();
	}
	public String getLastAuthorInlineText(){
		WebElement lastLineEle=driver.findElement(By.xpath("//div[@class='messages']/ul[@class='message']/li[@class='author'][last()]"));
		return lastLineEle.getText();
	}
	public void sendMsg(String msg){
		input.sendKeys(msg);
		sendMsgBtn.click();
	}
	public void backNativeApp(){
		contextNativeApp(driver);
	}
	public boolean verifyInthisPage(){
		return isWebElementExsit(sendMsgBtnLocator);
	}

}
