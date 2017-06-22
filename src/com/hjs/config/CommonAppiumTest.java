package com.hjs.config;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

public class CommonAppiumTest {
	public AndroidDriver<AndroidElement> driver;
    private WebDriverWait wait;
    AppiumServer appiumServer;
    @BeforeSuite(alwaysRun = true)
    public void setUp() throws Exception {
    	appiumServer=new AppiumServer();
    	//appiumServer.stopServer();
    	System.out.println("---- Starting appium server ----");
    	appiumServer.startServer();
		System.out.println("---- Appium server started Successfully ! ----");
		try{Thread.sleep(15000);}catch(Exception e){}
		
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "app");
        File app = new File(appDir, "测试2.1.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "127.0.0.1:52001"); 
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "4.4");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        capabilities.setCapability("appPackage", "com.evergrande.eif.android.hengjiaosuo");
        capabilities.setCapability("unicodeKeyboard", true);	//支持中文
        capabilities.setCapability("resetKeyboard", true);	//运行完毕之后，变回系统的输入法
        capabilities.setCapability("noReset", true);
        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver,30);
    }
    @AfterSuite(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
        System.out.println("---- Stoping appium server ----");
        appiumServer.stopServer();
		System.out.println("---- Appium server stopped Successfully ! ----");
    }
   
}
