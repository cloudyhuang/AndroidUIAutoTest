package com.hjs.publics;

import java.io.BufferedReader;
import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.io.IOUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.hjs.db.FisProdInfo;
import com.hjs.db.MarketGrouponTask;
import com.hjs.mybatis.inter.EifFisOperation;
import com.hjs.mybatis.inter.EifMarketOperation;


public class test {

	public static void main(String[] args) {
		String a="8890000";
		a=a.substring(a.length()-4, a.length());
		System.out.println(a);

	}

	// 方法一
	public static String doubleTrans1(double num) {
		if (num % 1.0 == 0) {
			return String.valueOf((long) num);
		}
		return String.valueOf(num);
	}

    public static void downloadAPKWithBuildNo(String buildNo){
    	String cmd="curl -O target/app/app2.4.apk http://172.16.59.251:8080/job/eif-android-app/job/release/"+buildNo+"/artifact/app/build/outputs/apk/app-default_channel-debug.apk --user huangxiao:Hxnearcj228";
    	String result=runCommand(cmd);
    	System.out.println(result);
    }
    public static String getGrouponTask() throws IOException{
		String resource = "eifMarketConfig.xml";
		List<Integer>rate=new ArrayList<Integer>();
	    Reader reader = Resources.getResourceAsReader(resource);  
	    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
	    reader.close();  
	    SqlSession session = sqlSessionFactory.openSession();
	    try {
	    	EifMarketOperation eifMarketOperation=session.getMapper(EifMarketOperation.class);
	    	List<MarketGrouponTask> getGrouponTaskList = eifMarketOperation.getGrouponTask("4519");
	    	if(getGrouponTaskList.size()>0){
		    	for(int i=0;i<getGrouponTaskList.size();i++){
		    		rate.add(Integer.parseInt(getGrouponTaskList.get(i).getAward_rates()));
		    	}    	
	    	}
	    	Collections.sort(rate);
	    	String maxRate= rate.get(rate.size()-1).toString();
	    	return maxRate;
	        
	    } finally {
	        session.close();
	    }
	}
	public static String getBaseProductId(String productName) throws Exception{
		String resource = "eifFisConfig.xml";
        Reader reader = Resources.getResourceAsReader(resource);  
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
        reader.close();  
        SqlSession session = sqlSessionFactory.openSession();
        try {
        	EifFisOperation eifFisOperation=session.getMapper(EifFisOperation.class);
        	FisProdInfo fisProdInfo=eifFisOperation.getFisProdInfo(productName);
        	if(fisProdInfo==null){
        		return null;
        	}
        	return fisProdInfo.getBase_product_id();
        }
        finally {
            session.close();
        }
	}
	public static void startServer() {
		CommandLine command = new CommandLine("appium");
		command.addArgument("--address");
		command.addArgument("127.0.0.1");
		command.addArgument("--port");
		command.addArgument("4723");
		command.addArgument("--device-name");
		command.addArgument("127.0.0.1:52001");// 新加
		command.addArgument("--no-reset");
		command.addArgument("--command-timeout");
		command.addArgument("600");
		command.addArgument("--log");
		command.addArgument(System.getProperty("user.dir") + File.separator + "appiumLogs.txt");
		DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
		DefaultExecutor executor = new DefaultExecutor();
		executor.setExitValue(1);
		try {
			executor.execute(command, resultHandler);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stopServer() {

		CommandLine command = new CommandLine("cmd");
		command.addArgument("/c");
		command.addArgument("taskkill");
		command.addArgument("/F");
		command.addArgument("/IM");
		command.addArgument("node.exe");

		DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
		DefaultExecutor executor = new DefaultExecutor();
		executor.setExitValue(1);

		try {
			executor.execute(command, resultHandler);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String runCommand(String cmd){
		String result=null;
        try {
        	Process p =Runtime.getRuntime().exec(cmd);
        	//取得命令结果的输出流    
            InputStream fis=p.getInputStream();    
            result = IOUtils.toString(fis, "UTF-8");
           //用一个读输出流类去读    
            InputStreamReader isr=new InputStreamReader(fis);    
           //用缓冲器读行    
            BufferedReader br=new BufferedReader(isr);    
            String line=null;    
           //直到读完为止    
           while((line=br.readLine())!=null)    
            {    
                System.out.println(line);    
            }   
           p.waitFor();//导致当前线程等待，如果必要，一直要等到由该 Process 对象表示的进程已经终止。如果已终止该子进程，此方法立即返回。如果没有终止该子进程，调用的线程将被阻塞，直到退出子进程
           p.exitValue() ;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return result;
    }
}