package com.hjs.config;

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

public class CommonAppiumTest {
	public static AndroidDriver<AndroidElement> driver;
	public static DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
	public static String runtime=dateFormat.format(new Date());
    private WebDriverWait wait;
    AppiumServer appiumServer;
    @BeforeSuite(alwaysRun = true)
    @Parameters({"udid"})
    public void setUp(String udid) throws Exception {
    	deletePng("surefire-reports"+File.separator+"html");	//删除历史截图文件
    	appiumServer=new AppiumServer();
    	appiumServer.stopServer();	//先结束残留进程
    	try{Thread.sleep(5000);}catch(Exception e){}
    	System.out.println("---- Starting appium server ----");
    	appiumServer.startServer(udid);
		System.out.println("---- Appium server started Successfully ! ----");
		try{Thread.sleep(15000);}catch(Exception e){}
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "app");
        File app = new File(appDir, "app2.4.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, udid); 
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "4.4");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        capabilities.setCapability("appPackage", "com.evergrande.eif.android.hengjiaosuo");
        capabilities.setCapability("unicodeKeyboard", true);	//支持中文
        capabilities.setCapability("resetKeyboard", true);	//运行完毕之后，变回系统的输入法
        capabilities.setCapability("noReset", false);	//是否不重新安装 true不安装，false重新安装
        //关键是加上这段
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("androidProcess", "com.evergrande.eif.android.hengjiaosuo:web");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver,30);
    }
    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) throws Exception {
        if (!result.isSuccess())
            catchExceptions(result);
    }

    public void catchExceptions(ITestResult result) {
        System.out.println("result" + result);
        String methodName = result.getName();
        System.out.println(methodName);
        if (!result.isSuccess()) {
            File file = new File("surefire-reports"+File.separator+"html");
            Reporter.setCurrentTestResult(result);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            Reporter.log("用例失败时间："+df.format(new Date()));
            String filePath = file.getAbsolutePath();
            String dest = result.getMethod().getRealClass().getSimpleName()+"."+result.getMethod().getMethodName();
            String picName=dest+runtime;
            String escapePicName=escapeString(picName);
            System.out.println(escapePicName);
            String html="<img src='"+picName+".png' onclick='window.open(\""+escapePicName+".png\")'' hight='100' width='100'/>";
            Reporter.log(html);

        }
    }
	public void deletePng(String abpath) {
        String[] ss = abpath.split("/");
        String name = ss[ss.length - 1];
        String path = abpath.replace("/" + name, "");

        File file = new File(path);// 里面输入特定目录
        File temp = null;
        File[] filelist = file.listFiles();
        if(filelist!=null){
        for (int i = 0; i < filelist.length; i++) {
            temp = filelist[i];
            if (temp.getName().endsWith("png") && !temp.getName().endsWith(name))// 获得文件名，如果后缀为“png”就删除文件
            {
                temp.delete();// 删除文件
                System.out.println("已删除历史截图文件："+temp.getName());
            }
        }
        }
    }
    /**
     * 替换字符串
     * @param 待替换string
     * @return 替换之后的string
     */
    public String escapeString(String s)
    {
        if (s == null)
        {
            return null;
        }
        
        StringBuilder buffer = new StringBuilder();
        for(int i = 0; i < s.length(); i++)
        {
            buffer.append(escapeChar(s.charAt(i)));
        }
        return buffer.toString();
    }


    /**
     * 将\字符替换为\\
     * @param 待替换char
     * @return 替换之后的char
     */
    private String escapeChar(char character)
    {
        switch (character)
        {
            case '\\': return "\\\\";
            default: return String.valueOf(character);
        }
    }
    @AfterSuite(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
        System.out.println("---- Stoping appium server ----");
        appiumServer.stopServer();
		System.out.println("---- Appium server stopped Successfully ! ----");
    }
   
}
