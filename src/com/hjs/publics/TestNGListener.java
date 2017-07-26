package com.hjs.publics;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.hjs.cases.TradeTestCase;
import com.hjs.config.CommonAppiumTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;

/** 
 * @author 作者:黄霄
 * @version 创建时间：2017-6-28 下午4:05:29 
 * 类说明 
 */
public class TestNGListener implements ITestListener {
	AppiumDriver<AndroidElement> driver;

    public void onTestStart(ITestResult result) {}

    public void onTestSuccess(ITestResult result) {}

    public void onTestFailure(ITestResult result) {
        System.out.println("========================failed, create snapshot here==============");
        captureScreenShot(result);      
    }

    public void onTestSkipped(ITestResult result) {}

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}

    public void onStart(ITestContext context) {}

    public void onFinish(ITestContext context) {}

    public void captureScreenShot(ITestResult result){      
    	String contextName = TradeTestCase.driver.getContext();
    	TradeTestCase.driver.context("NATIVE_APP");
        File srcFile = TradeTestCase.driver.getScreenshotAs(OutputType.FILE);
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        File location = new File("surefire-reports"+File.separator+"html");
        String dest = result.getMethod().getRealClass().getSimpleName()+"."+result.getMethod().getMethodName();
        //File targetFile = new File(location.getAbsolutePath()+File.separator+dest+dateFormat.format(new Date())+".png");
        File targetFile = new File(location.getAbsolutePath()+File.separator+dest+CommonAppiumTest.runtime+".png");
        System.out.println("----------------- file is " + targetFile.getPath());
        
        try {
            FileUtils.copyFile(srcFile, targetFile);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


}
