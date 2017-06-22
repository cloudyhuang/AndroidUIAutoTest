package com.hjs.publics;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class WebBaseMethod {
    public static void waitForWebElementVisible(WebDriver driver,WebElement ele,int waitTime){
    	WebDriverWait wait = new WebDriverWait(driver,waitTime);
	 	wait.until(ExpectedConditions.visibilityOf(ele));   
    }
    public static void waitForWebElementToBeClickable(WebDriver driver,WebElement ele,int waitTime){
    	WebDriverWait wait = new WebDriverWait(driver,waitTime);
    	wait.until(ExpectedConditions.visibilityOf(ele));  
	 	wait.until(ExpectedConditions.elementToBeClickable(ele));  
	 	
    }
	/**
	*切换到指定名称的窗口

	*@param 窗口名称

	*@return 

	*@author cloudy_huang
	 */
    public static void switchToWindowHandle(WebDriver driver,String windowTitleName){
    	Set<String> handles = driver.getWindowHandles();
    	List<String> it = new ArrayList<String>(handles);
    	Iterator<String> iterator = handles.iterator();
    	String currentHandle=null;
    	try{
    		currentHandle=driver.getWindowHandle();
    	}
    	catch(NoSuchWindowException e){
    		driver.switchTo().window(it.get(0));
    	}
    	while (iterator.hasNext()) {
    		String h = (String) iterator.next();
    		String currentTitle=driver.getTitle();
    		if (currentTitle.contains(windowTitleName)){
    			break;
    		}
	    		if (h != currentHandle) {
	    		 if (driver.switchTo().window(h).getTitle().contains(windowTitleName)) {
	    			driver.switchTo().window(h);
		    		System.out.println("switch to "+driver.getTitle()+" news page successfully");
		    		break;
	    		}
	    	}
    	}
    }
    /**
	*鼠标悬停

	*@param

	*@return 

	*@author cloudy_huang
	 */
    public static void mouthHover(WebDriver driver,WebElement ele,int time){
		Actions builder = new Actions(driver);
		Actions hoverOverRgeistrar = builder.moveToElement(ele);
		hoverOverRgeistrar.perform();
		WebBaseMethod.threadsleep(time);
    }
	public static void threadsleep(int timeout){
		try {
			Thread.sleep(timeout);
    	   } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
    	   }
	}
	public static boolean isElementExsit(WebDriver driver, WebElement ele) {  
        boolean flag = false;  
        try {  
        	WebDriverWait wait = new WebDriverWait(driver,10);
        	wait.until(ExpectedConditions.visibilityOf(ele)); 
        	flag = true;
        } catch (Exception e) {  
        	flag = false;
            System.out.println("Element:" + ele.toString()  
                    + " is not exsit!");  
        }  
        return flag;  
    } 
	public static boolean isElementExsit(WebDriver driver, By locator) {  
        boolean flag = false;  
        try {  
            WebElement element=driver.findElement(locator);  
            flag=element.isDisplayed();  
            System.out.println(flag+""+element);
        } catch (NoSuchElementException e) {  
            System.out.println("Element:" + locator.toString()  
                    + " is not exsit!");  
        }  
        return flag;  
    }  
	
	public static By getWebElementBy(WebElement ele){
		String eleString=ele.toString();
		By by = null;
		if(eleString != null && !"".equals(eleString)){
		String[] a=eleString.split("-> ");
		String[] b=a[1].split(": ");
		switch(b[0]){
		case "xpath":
			by=By.xpath(b[1]);
			break;
		case "className":
			by=By.className(b[1]);
			break;
		case "id":
			by=By.id(b[1]);
			break;
		case "linkText":
			by=By.linkText(b[1]);
			break;
		case "name":
			by=By.name(b[1]);
			break;
		case "partialLinkText":
			by=By.partialLinkText(b[1]);
			break;
		case "tagName":
			by=By.tagName(b[1]);
			break;
		}
		}
		return by;
	}
	
	public static String getcurrentDate() { 
		SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");//自定义日期格式
		return df.format(new Date());
	}
	
	public static <T extends Comparable<T>> boolean listCompare(List<T> a, List<T> b) {
		  if(a.size() != b.size())
		    return false;
		  Collections.sort(a);
		  Collections.sort(b);
		  for(int i=0;i<a.size();i++){
		    if(!a.get(i).equals(b.get(i)))
		      return false;
		  }
		  return true;
		}
	public static boolean mapContains(HashMap smallerMap,HashMap biggerMap){
		boolean flag=true;
		Iterator<String> iter1 = smallerMap.keySet().iterator();
	    while (iter1.hasNext()) {
            String m1Key = (String) iter1.next();
            if (!smallerMap.get(m1Key).equals(biggerMap.get(m1Key))) {//
            	flag=false;
            	break;
            }
        }
	    return flag;
	}

}
