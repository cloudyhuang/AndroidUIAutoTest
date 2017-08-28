package com.hjs.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;

/**
 * @author huangxiao
 * @version 创建时间：2017年8月17日 下午2:24:12 mac os启动appium服务
 */
public class MacAppiumServer extends AppiumServer {
	@Override
	public void startServer(String udid,boolean isDebug) {
    	String cmd1="adb kill-server";
    	String cmd2="adb start-server";
    	String cmd3="adb connect 127.0.0.1:62001";
		runCommand(cmd1);
		runCommand(cmd2);
		runCommand(cmd3);
		if(isDebug){}
		else {
			String cmd4 = "adb -s " + udid + " uninstall com.evergrande.eif.android.hengjiaosuo"; // 卸载app
			System.out.print("卸载app:");
			runCommand(cmd4);
		}
		CommandLine command = new CommandLine("appium");
		command.addArgument("--address");
		command.addArgument("127.0.0.1");
		command.addArgument("--port");
		command.addArgument("4723");
		command.addArgument("--device-name");
		command.addArgument(udid);// 新加
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

	@Override
	public void stopServer() {

		CommandLine command = new CommandLine("killall");
		command.addArgument("node");

		DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
		DefaultExecutor executor = new DefaultExecutor();
		executor.setExitValue(1);

		try {
			executor.execute(command, resultHandler);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void startIOSServer() {
		// TODO Auto-generated method stub
		
	}
}
