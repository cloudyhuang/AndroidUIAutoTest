package com.hjs.config;

import static org.testng.AssertJUnit.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;


import org.apache.http.util.TextUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.hjs.publics.AppiumBaseMethod;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.TimeOutDuration;

public class CommonAppiumPage {
	public AndroidDriver<AndroidElement> driver; 

    private final int WAIT_TIME = 30;    //默认的等待控件时间
    private TimeOutDuration timeOutDuration;
    public CommonAppiumPage(AndroidDriver<AndroidElement> androidDriver) {
        this.driver = androidDriver;
        timeOutDuration = new TimeOutDuration(WAIT_TIME, TimeUnit.SECONDS);
        PageFactory.initElements(new AppiumFieldDecorator(driver,timeOutDuration), this);//,new TimeOutDuration(15,TimeUnit.SECONDS)
        waitAuto(WAIT_TIME);
    }
    
    public int getWaitTime() {
		return WAIT_TIME;
	}

	/**
     * ，隐式等待，如果在指定时间内还是找不到下个元素则会报错停止脚本
     * 全局设定的，find控件找不到就会按照这个事件来等待
     *
     * @param time 要等待的时间
     */
    public void waitAuto(int time) {
        driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
        timeOutDuration.setTime(time, TimeUnit.SECONDS);
        System.out.println("设置隐式等待时间为"+time+"秒");
    }
    /**
     * Click点击控件
     *
     * @param ae  控件
     * @param str 控件的文字描述，供错误时候输出
     * @return 返回是否存在控件
     */
    public boolean clickEle(AndroidElement ae, String str) {
        if (ae != null) {
            try{
            	this.waitForClickble(ae, WAIT_TIME);
            	ae.click();
            }
        	catch(NoSuchElementException e){
        		assertTrue("没有找到目标元素－－"+str,false);
        	}
            catch(TimeoutException e){
        		assertTrue("超时"+WAIT_TIME+"秒，元素\""+str+"\"为不可点击状态！",false);
        	}
            return true;
        } else {
            print(str + "为空，点击错误");
        }
        return false;
    }
    /**
     * Click点击webview控件
     *
     * @param by  web控件locator
     * @param str 控件的文字描述，供错误时候输出
     * @return 返回是否存在控件
     */
    public boolean clickWebEle(By by, String str) {
        if (by != null) {
            try{
            	this.waitForClickble(by, WAIT_TIME);
            	driver.findElement(by).click();
            }
        	catch(NoSuchElementException e){
        		assertTrue("没有找到目标元素－－"+str,false);
        	}
            catch(TimeoutException e){
        		assertTrue("超时"+WAIT_TIME+"秒，元素\""+str+"\"为不可点击状态！",false);
        	}
            return true;
        } else {
            print(str + "为空，点击错误");
        }
        return false;
    }
    /**
     * Click点击webview控件
     *
     * @param ae  web控件locator
     * @param str 控件的文字描述，供错误时候输出
     * @return 返回是否存在控件
     */
    public boolean clickWebEle(WebElement we, String str) {
        if (we != null) {
            try{
            	this.waitForClickble(we, WAIT_TIME);
            	we.click();
            }
        	catch(NoSuchElementException e){
        		assertTrue("没有找到目标元素－－"+str,false);
        	}
            return true;
        } else {
            print(str + "为空，点击错误");
        }
        return false;
    }
    
    /**
     * 往控件输入字符串
     *
     * @param ae  要输入的控件
     * @param str 要输入的字符串
     */
    public void sendKeys(AndroidElement ae, String str) {
        if (ae == null) {
            print("控件为空,输入内容失败:" + str);
        } else {
        	try{
        		ae.sendKeys(str);
        	}
        	catch(NoSuchElementException e){
        		assertTrue("没有找到目标元素－－"+str,false);
        	}
        }

    }
    /**
     * 打印字符
     *
     * @param str 要打印的字符
     */
    public <T> void print(T str) {
        if (!TextUtils.isEmpty(String.valueOf(str))) {
            System.out.println(str);
        } else {
            System.out.println("输出了空字符");
        }
    }
    /**
     * 滑动到元素,且元素滑动到屏幕中间位置
     *
     * @param xpath 元素xpath
     * @throws Exception 
     */
    public void scrollTo(String xpath) throws Exception{
   		waitAuto(0);
    	while(true){
    		//滑动前获取pagesource
    		String beforeScrollStr=driver.getPageSource();  
    		try{
    		AndroidElement scrollToEle=driver.findElement(By.xpath(xpath));
	    		if(scrollToEle.isDisplayed()){
	    			int width = driver.manage().window().getSize().width;	//屏幕像素宽
	    			int height=driver.manage().window().getSize().height;	//屏幕像素高
	    			int halfHeight=height/2;
	    			Point scrollToEleCenterPoint=getNativeEleCenterPoint(scrollToEle);
	    			if(scrollToEleCenterPoint.getY()>height*7/8){
		    			driver.swipe(width / 2, halfHeight, width / 2, height/4, 2000); //只有元素在比较偏僻的地方，将元素滑动到屏幕中间位置，防止出现只滑动一点点导致直接点击的操作 
		    			}
	    			if(scrollToEleCenterPoint.getY()<height/8){
		    			driver.swipe(width / 2, height/4, width / 2, halfHeight, 2000); //只有元素在比较偏僻的地方，将元素滑动到屏幕中间位置，防止出现只滑动一点点导致直接点击的操作 
		    			}
	    			if(scrollToEleCenterPoint.getY()<height/5&&scrollToEleCenterPoint.getY()>height/8||scrollToEleCenterPoint.getY()>height*4/5&&scrollToEleCenterPoint.getY()<height*7/8){
	    			driver.swipe(width / 2, scrollToEleCenterPoint.getY(), width / 2, halfHeight,2000); //只有元素在比较偏僻的地方，将元素滑动到屏幕中间位置，防止出现只滑动一点点导致直接点击的操作 
	    			}
	    			waitAuto(WAIT_TIME);
	    			break;
	    		}
    		}
    		catch(Exception e){
    			e.getMessage();
    		}
    		this.swipeToUp(1000, 1);
    		String afterScrollStr=driver.getPageSource();
    		if(beforeScrollStr.equals(afterScrollStr)){
    			waitAuto(WAIT_TIME);
    			throw new Exception("滑动到底找不到该元素"+xpath);
    		}
    	}
    	waitAuto(WAIT_TIME);
    	this.threadsleep(2000);
    }
    /**
     * 得到元素中心点坐标
     *
     * @param el 元素
     * @return 元素中心点坐标Point
     */
    public Point getNativeEleCenterPoint(AndroidElement el){
    	int startX = el.getLocation().getX();
        int startY = el.getLocation().getY();
        int height = el.getSize().getHeight();
        int width = el.getSize().getWidth();
        int endX =width+startX;
    	int endY =height+startY;
        int centerX=(startX+endX)/2;
        int centerY=(startY+endY)/2;
        Point elCenterPoint=new Point(centerX,centerY);
		return elCenterPoint;
    	
    }
	public  Point getWebElementRealPoint(AndroidDriver<AndroidElement> driver,WebElement el){
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
    public void clickWebviewEle(AndroidDriver<AndroidElement> driver,WebElement el){
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
    public void clickPoint(int x,int y){
    	int screen_width_execute_phone = driver.manage().window().getSize().width;//screen width
    	int screen_height_execute_phone = driver.manage().window().getSize().height; //screen height
    	int x_click = x * screen_width_execute_phone / 720; //x coordinates on execute phone
    	int y_click = y * screen_height_execute_phone / 1280; //y coordinates on execute phone
    	driver.tap(1, x_click, y_click, 0);
    }
    public void clickNativeEle(AndroidElement el,int clickcount){
		Point elpoint = el.getLocation();
    	Dimension elSize = el.getSize();
    	int startX = elpoint.getX();
    	int startY = elpoint.getY();
    	int endX =elSize.getWidth()+startX;
    	int endY =elSize.getHeight()+startY;
    	int centerX = (startX+endX)/2;
    	int centerY = (startY+endY)/2;
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        HashMap<String, Double> tapObject = new HashMap<String, Double>();
//        tapObject.put("x", centerX);
//        tapObject.put("y", centerY);
//        tapObject.put("duration", 0.0);
//        tapObject.put("touchCount", 1.0);
//        tapObject.put("tapCount", 3.0);
        for(int i=0;i<clickcount;i++)
        {
//        js.executeScript("mobile: tap", tapObject);
        	new TouchAction((MobileDriver) driver).tap(centerX, centerY).perform();
        }
    	
    }
    public void  multiPointClick(By[] clickLocator,AndroidDriver<AndroidElement> driver){
    	for(int i=0;i<=clickLocator.length-1;i++)
    	{
    		driver.findElement(clickLocator[i]).click();
    	}
    }
    
    public void scrollelment(By scrollLocator,String scrollToText,AndroidDriver<AndroidElement> driver){	
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
    public void swipeToUp(int during,int times) {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        for(int i=1;i<=times;i++){
        driver.swipe(width / 2, height * 3 / 4, width / 2, height / 4, during);
        threadsleep(1000);
        }
        // wait for page loading
    }

    /**
     * This Method for swipe down
     */
    public void swipeToDown(int during,int times) {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
    	for(int i=1;i<=times;i++)
    	{
        driver.swipe(width / 2, height / 4, width / 2, height * 3 / 4, during);
        threadsleep(1000);
    	}
        // wait for page loading
    }

    /**
     * This Method for swipe Left
     */
    public void swipeToLeft(AndroidDriver<AndroidElement> driver, int during,int times) {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        for(int i=1;i<=times;i++){
        driver.swipe(width * 9 / 10, height / 2, width / 10, height / 2, during);
        threadsleep(1000);
        }
        // wait for page loading
    }

    /**
     * This Method for swipe Right
     */
    public void swipeToRight(AndroidDriver<AndroidElement> driver, int during,int times) {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        for(int i=1;i<=times;i++){
        driver.swipe(width / 4, height / 2, width * 3 / 4, height / 2, during);
        threadsleep(1000);
        }
        // wait for page loading
    }
    
    public void contextNativeApp(AndroidDriver<AndroidElement> driver){
    	Set<String> context = driver.getContextHandles();
        for(String contextname : context){
        System.out.println(contextname);//打印
	       if(contextname.contains("NATIVE"))
	       driver.context(contextname);
        }
    }
    public void contextWebview(AndroidDriver<AndroidElement> driver){
    	//遍历context，切换到webview，注意 有些app可能有多个webview
    	Set<String> context = driver.getContextHandles();
        for(String contextname : context){
        System.out.println(contextname);//打印
	       if(contextname.contains("WEBVIEW_com.evergrande"))
	       driver.context(contextname);
        }
        if(!driver.getContext().contains("WEBVIEW_com.evergrande")){
        	Assert.assertTrue(false,"当前页面未切换到WEBVIEW_com.evergrande，请确认是否打开webview的Debug模式");
        }
    }

	public String getcurrentDate() { 
		SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");//自定义日期格式
		return df.format(new Date());
	}
	public void threadsleep(int timeout){
		try {
			Thread.sleep(timeout);
    	   } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
    	   }
	}
	   /**
     * 智能等待查找元素，每秒查找一次，总时间之后查找不到为空
     *
     * @param locator 元素locator
     */
	public AndroidElement findBy(By locator){
		final long endTime=System.currentTimeMillis()+WAIT_TIME*1000;//System.currentTimeMillis()为自1970年1月1日0时起的毫秒数
		AndroidElement ele=null;
		waitAuto(0); //清除隐式等待
		while(true){
			try{
				ele=driver.findElement(locator);
				if(ele.isDisplayed()){
					waitAuto(WAIT_TIME);
					break;
				}
			}
			catch(Exception e){
				if(System.currentTimeMillis()<endTime){
					Reporter.log("找不到元素，重试，locator:"+locator);
					threadsleep(1000);//等待1s
					continue;
				}
			}
			if(System.currentTimeMillis()>=endTime){
				Reporter.log(WAIT_TIME+"秒找不到元素,locator:"+locator);
				waitAuto(WAIT_TIME);
				return null;
			}
			
		}
		waitAuto(WAIT_TIME);
		return ele;
	}
	public WebElement webFindBy(By locator){
		final long endTime=System.currentTimeMillis()+WAIT_TIME*1000;//System.currentTimeMillis()为自1970年1月1日0时起的毫秒数
		WebElement ele=null;
		waitAuto(0); //清除隐式等待
		while(true){
			try{
				ele=driver.findElement(locator);
				if(ele.isDisplayed()){
					waitAuto(WAIT_TIME);
					break;
				}
			}
			catch(Exception e){
				if(System.currentTimeMillis()<endTime){
					Reporter.log("找不到元素，重试，locator:"+locator);
					threadsleep(1000);//等待1s
					continue;
				}
			}
			if(System.currentTimeMillis()>=endTime){
				Reporter.log(WAIT_TIME+"秒找不到元素,locator:"+locator);
				waitAuto(WAIT_TIME);
				return null;
			}
			
		}
		waitAuto(WAIT_TIME);
		return ele;
	}
	public boolean isElementExsit(AndroidDriver<AndroidElement> driver, By locator) {  
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
/**
  * times秒内判断元素是否存在
  *
  * @param times 时间s
  * @param locator 元素locator
  */
	public boolean isElementExsit(int times,By locator) {  
        boolean flag = false;  
        try {  
        	waitAuto(times);
        	AndroidElement element=driver.findElement(locator);  
            flag=null!=element;  
        } catch (NoSuchElementException e) {  
        	waitAuto(WAIT_TIME);
            System.out.println("Element:" + locator.toString()  
                    + " is not exsit!"); 
        }  
        waitAuto(WAIT_TIME);
        return flag;  
    } 
	/**
	  * times秒内判断元素是否存在
	  *
	  * @param times 时间s
	  * @param ele AndroidElement元素
	  */
	public boolean isElementExsit(int times,AndroidElement ele){ 
		waitAuto(0);
        for (int attempt = 0; attempt < times; attempt++) {  
            try {  
                if(!ele.isDisplayed()){
                	AppiumBaseMethod.threadsleep(1000);
                	continue;
                }
                else {
                	waitAuto(WAIT_TIME);
                	return true;
                }
            } catch (Exception e) {  
            	AppiumBaseMethod.threadsleep(1000);
            	continue;
            }  
        }  
        waitAuto(WAIT_TIME);
		return false;
	}
	
	 /**
     * 判断元素是否存在，时间为隐式等待时间
     *
     * @param locator 元素locator
     */
	public boolean isElementExsit(By locator) {  
        boolean flag = false;  
        try {  
//        	AndroidElement element=driver.findElement(locator); 
        	AndroidElement element=findBy(locator);
            flag=null!=element;  
        } catch (NoSuchElementException e) {  
            System.out.println("Element:" + locator.toString()  
                    + " is not exsit!");  
        }  
        return flag;  

    } 
	 /**
     * 判断web元素是否存在，时间为隐式等待时间
     *
     * @param locator 元素locator
     */
	public boolean isWebElementExsit(By locator) {  
        boolean flag = false;  
        try {  
        	WebElement element=webFindBy(locator);
            flag=null!=element;  
        } catch (NoSuchElementException e) {  
            System.out.println("Element:" + locator.toString()  
                    + " is not exsit!");  
        }  
        return flag;  

    } 
	 /**
     * 判断元素组是否存在，只要有一个存在返回true
     *
     * @param locator 元素locator组
     */
	public boolean isElementsExsit(By...locator){
		waitAuto(0);
		boolean flag = false;
		for (int attempt = 0; attempt < WAIT_TIME; attempt++) {
			for (int i = 0; i < locator.length; i++) {
				try {
					AndroidElement ele = driver.findElement(locator[i]);
					if (ele.isDisplayed()) {
						flag = true;
						break;
					}
				} catch (Exception e) {
				}
			}
			if (flag) {
				break;
			}
			AppiumBaseMethod.threadsleep(1000);

		}
		waitAuto(WAIT_TIME);
		return flag;
        

	}
	
	public void waitEleUnVisible(By by,int waittime){ 
		waitAuto(2);
        for (int attempt = 0; attempt < waittime; attempt++) {  
            try {  
                driver.findElement(by);  
                AppiumBaseMethod.threadsleep(1000);
            } catch (Exception e) {  
            	waitAuto(WAIT_TIME);
                break; 
            }  
        }  
        waitAuto(WAIT_TIME);
	}
	public void waitEleUnVisible(AndroidElement ele,int waittime){ 
		waitAuto(2);
        for (int attempt = 0; attempt < waittime; attempt++) {  
            try {  
                if(ele.isDisplayed()){
                	AppiumBaseMethod.threadsleep(1000);
                	continue;
                }
                else break;
            } catch (Exception e) {  
            	waitAuto(WAIT_TIME);
                break; 
            }  
        }  
        waitAuto(WAIT_TIME);
	}
	
    public void waitForVisible(By by, int waitTime){
        WebDriverWait wait = new WebDriverWait(driver,waitTime);
   	 	wait.until(ExpectedConditions.visibilityOfElementLocated(by)); 
    }
    public void waitForClickble(By by, int waitTime){
        WebDriverWait wait = new WebDriverWait(driver,waitTime);
   	 	wait.until(ExpectedConditions.elementToBeClickable(by)); 
    }
    public void waitForClickble(WebElement we, int waitTime){
        WebDriverWait wait = new WebDriverWait(driver,waitTime);
   	 	wait.until(ExpectedConditions.elementToBeClickable(we)); 
    }
    public void waitForClickble(AndroidElement ae, int waitTime){
        WebDriverWait wait = new WebDriverWait(driver,waitTime);
   	 	wait.until(ExpectedConditions.elementToBeClickable(ae)); 
    }
}
