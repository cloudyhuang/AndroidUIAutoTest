package com.hjs.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.output.ByteArrayOutputStream;

public class AppiumServer {
	public void startServer(String udid){
		String cmd="cmd /c adb kill-server && adb start-server && adb connect "+udid;
		runCommand(cmd);
		CommandLine command = new CommandLine("cmd");
		command.addArgument("/c");
		command.addArgument("appium");
		command.addArgument("--address");
		command.addArgument("127.0.0.1");
		command.addArgument("--port");
		command.addArgument("4723");
		command.addArgument("--device-name");
		command.addArgument("127.0.0.1:52001");//新加
		command.addArgument("--no-reset");
		command.addArgument("--command-timeout");
		command.addArgument("600");
		command.addArgument("--log");
		command.addArgument("D:/appiumLogs.txt");
		DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
		DefaultExecutor executor = new DefaultExecutor();
		executor.setExitValue(1); 
		try {
		executor.execute(command, resultHandler);
		} catch (IOException e) {
		e.printStackTrace();
		}
		}

		public void stopServer(){

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
		private static void runCommand(String cmd){
	        try {
	        	Process p =Runtime.getRuntime().exec(cmd);
	        	//取得命令结果的输出流    
	            InputStream fis=p.getInputStream();    
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
	    }
}
