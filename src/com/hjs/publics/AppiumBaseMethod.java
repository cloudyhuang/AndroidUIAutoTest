package com.hjs.publics;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class AppiumBaseMethod {
	public static Point getWebElementRealPoint(AndroidDriver driver,WebElement el){
		AppiumBaseMethod.contextNativeApp(driver);
	   	int devWidth= driver.manage().window().getSize().width;//[720]
	   	int devHeight=driver.manage().window().getSize().height;//[1280]
	   	Point appWvpoint=driver.findElement(By.id("app_detaill_wv")).getLocation();//webview左上角坐标[0,127]
	   	AppiumBaseMethod.contextWebview(driver);
		Point elpoint = el.getLocation();
    	Dimension elSize = el.getSize();
    	double appWvX=appWvpoint.getX();
    	double appWvY=appWvpoint.getY();
    	WebElement webBodyElement=driver.findElementByTagName("body");
		Dimension webBodyElSize=webBodyElement.getSize();
    	double startX = elpoint.getX();
    	double startY = elpoint.getY();
    	double endX =elSize.getWidth()+startX;
    	double endY =elSize.getHeight()+startY;
    	double centerX = (startX+endX)/2;
    	double centerY = (startY+endY)/2;
    	double elX=((devWidth-appWvX)/webBodyElSize.width)*centerX+appWvX;
    	double elY=((devHeight-appWvY)/webBodyElSize.height)*centerY+appWvY;
    	System.out.println("centerX:"+centerX+",centerY:"+centerY);
        System.out.println("elX:"+elX+",elY:"+elY);
        elpoint.move((int)elX, (int)elY);
        return elpoint;
	}
    public static void clickWebviewEle(AndroidDriver driver,WebElement el){
    	Point elRealPoint=AppiumBaseMethod.getWebElementRealPoint(driver,el);
    	double elX=elRealPoint.getX();
    	double elY=elRealPoint.getY();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        HashMap<String, Double> tapObject = new HashMap<String, Double>();
        tapObject.put("x", elX);
        tapObject.put("y", elY);
        tapObject.put("duration", 0.0);
        tapObject.put("touchCount", 1.0);
        tapObject.put("tapCount", 3.0);
        js.executeScript("mobile: tap", tapObject);
    	
    }
    public static void clickNativeEle(AndroidDriver driver,WebElement el,int clickcount){
		Point elpoint = el.getLocation();
    	Dimension elSize = el.getSize();
    	double startX = elpoint.getX();
    	double startY = elpoint.getY();
    	double endX =elSize.getWidth()+startX;
    	double endY =elSize.getHeight()+startY;
    	double centerX = (startX+endX)/2;
    	double centerY = (startY+endY)/2;
        JavascriptExecutor js = (JavascriptExecutor) driver;
        HashMap<String, Double> tapObject = new HashMap<String, Double>();
        tapObject.put("x", centerX);
        tapObject.put("y", centerY);
        tapObject.put("duration", 0.0);
        tapObject.put("touchCount", 1.0);
        tapObject.put("tapCount", 3.0);
        for(int i=0;i<clickcount;i++)
        {
        js.executeScript("mobile: tap", tapObject);
        }
    	
    }
    public static void  multiPointClick(By[] clickLocator,AndroidDriver driver){
    	for(int i=0;i<=clickLocator.length-1;i++)
    	{
    		driver.findElement(clickLocator[i]).click();
    	}
    }
    
    public static void scrollelment(By scrollLocator,String scrollToText,AndroidDriver driver){	
    	WebElement scrollElement=driver.findElement(scrollLocator);
    	WebElement scroIdex_1Element=scrollElement.findElement(By.xpath("./li[1]/div[@class='dw-i']"));
    	WebElement scroToElement=scrollElement.findElement(By.xpath(".//div[@class='dw-i' and text()='"+scrollToText+"']"));
    	Point scroIdex_1Point=AppiumBaseMethod.getWebElementRealPoint(driver,scroIdex_1Element);
    	Point scroToPoint=AppiumBaseMethod.getWebElementRealPoint(driver,scroToElement);
    	while(!scroIdex_1Point.equals(scroToPoint)){
    	AppiumBaseMethod.contextNativeApp(driver);
    	driver.swipe(scroIdex_1Point.getX(),scroIdex_1Point.getY(),scroIdex_1Point.getX(),scroIdex_1Point.getY()-60, 500);
    	scroToPoint=AppiumBaseMethod.getWebElementRealPoint(driver,scroToElement);
    	}
    	AppiumBaseMethod.contextWebview(driver);
    
    	
    	//((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();" ,el);
    }
    /**
     * This Method for swipe up
     */
    public static void swipeToUp(AndroidDriver driver, int during,int times) {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        for(int i=1;i<=times;i++){
        driver.swipe(width / 2, height * 3 / 4, width / 2, height / 4, during);
        AppiumBaseMethod.threadsleep(500);
        }
        // wait for page loading
    }

    /**
     * This Method for swipe down
     */
    public static void swipeToDown(AndroidDriver driver, int during,int times) {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
    	for(int i=1;i<=times;i++)
    	{
        driver.swipe(width / 2, height / 4, width / 2, height * 3 / 4, during);
        AppiumBaseMethod.threadsleep(500);
    	}
        // wait for page loading
    }

    /**
     * This Method for swipe Left
     */
    public static void swipeToLeft(AndroidDriver driver, int during,int times) {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        for(int i=1;i<=times;i++){
        driver.swipe(width * 3 / 4, height / 2, width / 4, height / 2, during);
        AppiumBaseMethod.threadsleep(500);
        }
        // wait for page loading
    }

    /**
     * This Method for swipe Right
     */
    public static void swipeToRight(AndroidDriver driver, int during,int times) {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        for(int i=1;i<=times;i++){
        driver.swipe(width / 4, height / 2, width * 3 / 4, height / 2, during);
        AppiumBaseMethod.threadsleep(500);
        }
        // wait for page loading
    }
    
    public static void contextNativeApp(AndroidDriver driver){
    	Set<String> context = driver.getContextHandles();
        for(String contextname : context){
        System.out.println(contextname);//打印
	       if(contextname.contains("NATIVE"))
	       driver.context(contextname);
        }
    }
    public static void contextWebview(AndroidDriver driver){
    	//遍历context，切换到webview，注意 有些app可能有多个webview
    	Set<String> context = driver.getContextHandles();
        for(String contextname : context){
        System.out.println(contextname);//打印
	       if(contextname.contains("WEBVIEW"))
	       driver.context(contextname);
        }
    }

	public static String getcurrentDate() { 
		SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");//自定义日期格式
		return df.format(new Date());
	}
	public static void threadsleep(int timeout){
		try {
			Thread.sleep(timeout);
    	   } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
    	   }
	}
	public static boolean isElementExsit(AndroidDriver<AndroidElement> driver, By locator) {  
        boolean flag = false;  
        try {  
        	AndroidElement element=driver.findElement(locator);  
            flag=null!=element;  
        } catch (NoSuchElementException e) {  
            System.out.println("Element:" + locator.toString()  
                    + " is not exsit!");  
        }  
        return flag;  
    }

	
	public static void waitEleUnVisible(AndroidDriver driver,By by,int waittime){ 
        for (int attempt = 0; attempt < waittime; attempt++) {  
            try {  
                driver.findElement(by);  
                AppiumBaseMethod.threadsleep(1000);
            } catch (Exception e) {  
                break; 
            }  
        }  
		
	}
	
    public static void waitForVisible(AndroidDriver driver,By by, int waitTime){
        WebDriverWait wait = new WebDriverWait(driver,waitTime);
   	 	wait.until(ExpectedConditions.visibilityOfElementLocated(by)); 
    }
    

    
}
