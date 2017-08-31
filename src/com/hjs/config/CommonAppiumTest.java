package com.hjs.config;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
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
    @Parameters({"udid","isDebug"})
    public void setUp(String udid,boolean isDebug) throws Exception {
    	if(isDebug){}
    	else {
    		JenkinsJob.buildAndroidApp();	//触发构建安卓app，上个包为Debug，TEST环境版本，未超过一天，不触发
    		downLoadFromUrl("http://172.16.59.251:8088/app-default_channel.apk", "app2.4.apk"); //拉取最新包
    	}
    	System.out.println(System.getenv());
    	System.out.println(System.getenv("ANDROID_HOME"));
    	deletePng("surefire-reports"+File.separator+"html");	//删除历史截图文件
    	if(System.getProperty("os.name").contains("Mac")){
        	appiumServer=new MacAppiumServer();
    	}
    	else appiumServer=new WinAppiumServer();
    	appiumServer.stopServer();	//先结束残留进程
    	System.out.println("---- Starting appium server ----");
    	appiumServer.startServer(udid,isDebug);
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
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT,"600");
        if(isDebug){capabilities.setCapability("noReset", true);}	//是否不重新安装 true不安装，false重新安装
        else {capabilities.setCapability("noReset", false);	}//是否不重新安装 true不安装，false重新安装
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
	/** 
     * 从网络Url中下载文件 
     * @param urlStr 
     * @param fileName 
     * @throws IOException 
     */  
    public void downLoadFromUrl(String urlStr,String fileName) throws IOException{  
        URL url = new URL(urlStr);    
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
                //设置超时间为3秒  
        conn.setConnectTimeout(3*1000);  
        //防止屏蔽程序抓取而返回403错误  
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
  
        //得到输入流  
        InputStream inputStream = conn.getInputStream();    
        //获取自己数组  
        byte[] getData = readInputStream(inputStream);      
  
        //文件保存位置  
        //File saveDir = new File(savePath);  
        File classpathRoot = new File(System.getProperty("user.dir"));
        File saveDir = new File(classpathRoot, "app");
        if(!saveDir.exists()){  
            saveDir.mkdir();  
        }  
        File file = new File(saveDir+File.separator+fileName);      
        FileOutputStream fos = new FileOutputStream(file);       
        fos.write(getData);   
        if(fos!=null){  
            fos.close();    
        }  
        if(inputStream!=null){  
            inputStream.close();  
        }  
  
  
        System.out.println("info:"+url+" download success");   
  
    }
    /** 
     * 从输入流中获取字节数组 
     * @param inputStream 
     * @return 
     * @throws IOException 
     */  
    public byte[] readInputStream(InputStream inputStream) throws IOException {    
        byte[] buffer = new byte[1024];    
        int len = 0;    
        ByteArrayOutputStream bos = new ByteArrayOutputStream();    
        while((len = inputStream.read(buffer)) != -1) {    
            bos.write(buffer, 0, len);    
        }    
        bos.close();    
        return bos.toByteArray();    
    }    
    
    @AfterSuite(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
        System.out.println("---- Stoping appium server ----");
        appiumServer.stopServer();
		System.out.println("---- Appium server stopped Successfully ! ----");
    }
   
}
