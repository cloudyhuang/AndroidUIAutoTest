package com.hjs.publics;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
//import org.apache.poi.ss.usermodel.DataFormat;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestngListsener extends TestListenerAdapter  {
	
	public static WebDriver driver;
	
	public void onTestFailure(ITestResult tr) {
		
		super.onTestFailure(tr);
		try {
			//System.out.println("截图");
			takeScreenShot(tr);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void takeScreenShot(ITestResult tr) throws IOException{
			
//		SimpleDateFormat smf = new SimpleDateFormat("MMddHHmmss") ;
//		String curTime = smf.format(new java.util.Date());
//		String fileName = tr.getName()+"_"+curTime+".png";
		String fileName = tr.getName()+".png";
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcFile, new File(Constants.FAILURE_SCREENSHOT_URL+fileName));
	}

}
