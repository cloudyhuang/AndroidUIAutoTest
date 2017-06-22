package com.hjs.publics;

import static org.testng.AssertJUnit.assertTrue;

import java.util.List;
//import static org.junit.Assert.assertTrue;

//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.InputStreamReader;
//import java.text.SimpleDateFormat;
//import java.util.List;
//
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
/*
 *包名:com.kdzwy.cases
 *作者:Adien_cui
 *时间:2017-2-10  下午5:45:15
 *描述:元素操作
 **/


public class OperateElement {
	
	public static void main(String[] args){
	
	}
	
	/**
	 * 通过元素的Xpath，等待元素的出现,返回此元素
	 * @param driver
	 * @param locator 元素的Xpath
	 * @return 返回等待的元素
	 */
	public static WebElement waitForElementByXpath(String elementName,WebDriver driver,String locator){
		WebDriverWait wait = new WebDriverWait(driver, 60);
		WebElement targetElement = null;
		try{
			targetElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
		}catch(Exception e){
			assertTrue("没有找到目标元素--"+elementName,false);
		};
		return targetElement;
	}
	
	/**
	 * 通过元素的name，等待元素的出现,返回此元素
	 * @param driver
	 * @param name 元素的name
	 * @return 返回等待的元素
	 */
	public static WebElement waitForElementByName(String elementName,WebDriver driver,String name){
		WebDriverWait wait = new WebDriverWait(driver, 60);
		WebElement targetElement = null;
		try{
			targetElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.name(name)));
		}catch(Exception e){
			assertTrue("没有找到目标元素--"+elementName,false);
		}
		return targetElement;
	}
	
	/**
	 * 通过元素的id，等待元素的出现,返回此元素
	 * @param driver
	 * @param id 元素的id
	 * @return 返回等待的元素
	 */
	public static WebElement waitForElementById(String elementName,WebDriver driver,String id){
		WebDriverWait wait = new WebDriverWait(driver, 60);
		WebElement targetElement = null;
		try{
			targetElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
		}catch(Exception e){
			assertTrue("没有找到目标元素--"+elementName,false);
		}
		return targetElement;
	}
	
	/**
	 * 通过元素的linkText，等待元素的出现,返回此元素
	 * @param driver
	 * @param linkText 元素的linkText
	 * @return 返回等待的元素
	 */
	public static WebElement waitForElementByLinkText(String elementName,WebDriver driver,String linkText){
		WebDriverWait wait = new WebDriverWait(driver, 60);
		WebElement targetElement = null;
		try{
			targetElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(linkText)));
		}catch(Exception e){
			assertTrue("没有找到目标元素--"+elementName,false);
		}
		return targetElement;
	}
	
	/**
	 * 通过元素的className，等待元素的出现,返回此元素
	 * @param driver
	 * @param className 元素的className
	 * @return 返回等待的元素
	 */
	public static WebElement waitForElementByClassName(String elementName,WebDriver driver,String className){
		WebDriverWait wait = new WebDriverWait(driver, 60);
		WebElement targetElement = null;
		try{
			targetElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.className(className)));
		}catch(Exception e){
			assertTrue("没有找到目标元素--"+elementName,false);
		}
		return targetElement;
	}

	/**
	 * 通过元素的className，等待元素列表的出现,返回此元素列表
	 * @param driver
	 * @param className 元素的className
	 * @return 返回等待的元素
	 */
	public static List<WebElement> waitForElementListByClassName(String elementName,WebDriver driver,String className){
		WebDriverWait wait = new WebDriverWait(driver, 60);
		List<WebElement> targetElementList = null;
		try{
			targetElementList  = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy((By.className(className))));
		}catch(Exception e){
			assertTrue("没有找到目标元素--"+elementName,false);
		}
		return targetElementList ;
	}
	
	/**
	 * 通过元素的linktext，等待元素列表的出现,返回此元素列表
	 * @param driver
	 * @param linkText 元素的linktext
	 * @return 返回等待的元素
	 */
	public static List<WebElement> waitForElementListByLinkText(String elementName,WebDriver driver,String linkText){
		WebDriverWait wait = new WebDriverWait(driver, 60);
		List<WebElement> targetElementList = null;
		try{
			targetElementList  = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy((By.linkText(linkText))));
		}catch(Exception e){
			assertTrue("没有找到目标元素--"+elementName,false);
		}
		return targetElementList ;
	}
	
	/**
	 * 通过元素的linkText，等待元素的出现,并点击它
	 * @param driver
	 * @param linkText
	 * @throws InterruptedException 
	 */
	public static void waitAndClickByLinkText(String elementName,WebDriver driver,String linkText) throws InterruptedException{
		WebElement targetElement = waitForElementByLinkText(elementName,driver,linkText);
		Thread.sleep(1000);
		targetElement.click();
	}
	
	/**
	 * 通过元素的Xpath，等待元素的出现,并点击它
	 * @param driver
	 * @param Xpath
	 * @throws InterruptedException 
	 */
	public static void waitAndClickByXpath(String elementName,WebDriver driver,String Xpath) throws InterruptedException{
		WebElement targetElement = waitForElementByXpath(elementName,driver,Xpath);
		Thread.sleep(1000);
		targetElement.click();
	}
	
	/**
	 * 通过元素的ID，等待元素的出现,并点击它
	 * @param driver
	 * @param Id
	 * @throws InterruptedException 
	 */
	public static void waitAndClickById(String elementName,WebDriver driver,String Id) throws InterruptedException{
		WebElement targetElement = waitForElementById(elementName,driver,Id);
		Thread.sleep(1000);
		targetElement.click();
	}
	
	/**
	 * 通过元素的ClassName，等待元素的出现,并点击它
	 * @param driver
	 * @param className
	 * @throws InterruptedException 
	 */
	public static void waitAndClickByClassName(String elementName,WebDriver driver,String className) throws InterruptedException{
		WebElement targetElement = waitForElementByClassName(elementName,driver,className);
		Thread.sleep(1000);
		targetElement.click();
	}
	
	/**
	 * 通过元素的Name，等待元素的出现,并点击它
	 * @param driver
	 * @param name
	 * @throws InterruptedException 
	 */
	public static void waitAndClickByName(String elementName,WebDriver driver,String name) throws InterruptedException{
		WebElement targetElement = waitForElementByClassName(elementName,driver,name);
		Thread.sleep(1000);
		targetElement.click();
	}

	/**
	 * 通过元素的Xpath，等待文本框的出现,并输入
	 * @throws InterruptedException
	 */
	public static void waitAndSendKeysByXpath(String elementName,WebDriver driver,String Xpath,String keys) throws InterruptedException{
		WebElement targetElement = waitForElementByXpath(elementName,driver,Xpath);
		Thread.sleep(1000);
		targetElement.sendKeys(keys);
	}

	/**
	 * 通过元素的ID，等待文本框的出现,并输入
	 * @throws InterruptedException
	 */
	public static void waitAndSendKeysById(String elementName,WebDriver driver,String Id,String keys) throws InterruptedException{
		WebElement targetElement = waitForElementById(elementName,driver,Id);
		Thread.sleep(1000);
		targetElement.sendKeys(keys);
	}

	/**
	 * 通过元素的ClassName，等待文本框的出现,并输入
	 * @throws InterruptedException
	 */
	public static void waitAndSendKeysByClassName(String elementName,WebDriver driver,String className,String keys) throws InterruptedException{
		WebElement targetElement = waitForElementByClassName(elementName,driver,className);
		Thread.sleep(1000);
		targetElement.sendKeys(keys);
	}

	/**
	 * 通过元素的Name，等待文本框的出现,并输入
	 * @throws InterruptedException
	 */
	public static void waitAndSendKeysyName(String elementName,WebDriver driver,String name,String keys) throws InterruptedException {
		WebElement targetElement = waitForElementByClassName(elementName, driver, name);
		Thread.sleep(1000);
		targetElement.sendKeys(keys);
	}
	
	
	/**
	 * 已知frame的classname，切换到frame
	 * @param FrameName 窗口名称
	 * @param driver
	 * @param frameClassName 窗口的ClassName
	 */
	public static void swtichToFrameByClassName(String FrameName,WebDriver driver,String frameClassName){
		WebElement iFrame = OperateElement.waitForElementByClassName(FrameName, driver, frameClassName);
		driver.switchTo().frame(iFrame);
	}
	
	/**
	 * 已知frame的xpath，切换到frame
	 * @param FrameName 窗口名称
	 * @param driver
	 * @param locator 窗口的xpath
	 */
	public static void swtichToFrameByXpath(String FrameName,WebDriver driver,String locator){
		WebElement iFrame = OperateElement.waitForElementByXpath(FrameName, driver, locator);
		driver.switchTo().frame(iFrame);
	}
	
	
	public static Alert waitForAlert(String alertName,WebDriver driver){
		WebDriverWait wait = new WebDriverWait(driver, 60);
		Alert targetAlert = null;
		try{
			targetAlert = wait.until(ExpectedConditions.alertIsPresent());
		}catch(Exception e){
			assertTrue("没有找到目标元素--"+alertName,false);
		}
		return targetAlert;
	}
}

