package com.hjs.publics;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.io.IOUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.hjs.db.BankProvider;
import com.hjs.db.DBOperation;
import com.hjs.db.FisProdInfo;
import com.hjs.db.MarketGrouponTask;
import com.hjs.mybatis.inter.EifFisOperation;
import com.hjs.mybatis.inter.EifMarketOperation;
import com.hjs.mybatis.inter.EifPayCoreOperation;

public class test {

	public static void main(String[] args) throws Exception {
		DBOperation db=new DBOperation();
		db.updateNoProviderTransLimit("0007");
	}

	public static List<FindString> findString(String pattern,String str){
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		List<FindString> findString=new ArrayList<FindString>();
		while (m.find()) {
			FindString commentedOut=new FindString();
			commentedOut.setStartIndex(m.start());
			commentedOut.setEndIndex(m.end());
			commentedOut.setText(m.group(1));
			System.out.println("start:" + m.start() + " end:" + m.end());
			findString.add(commentedOut);
		}
		return findString;
	}
	 static  class FindString{
		private int startIndex;
		private int endIndex;
		private String text;
		public int getStartIndex() {
			return startIndex;
		}
		public void setStartIndex(int startIndex) {
			this.startIndex = startIndex;
		}
		public int getEndIndex() {
			return endIndex;
		}
		public void setEndIndex(int endIndex) {
			this.endIndex = endIndex;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
	}
	public static void onlyOpenSHENGFUTONGProvider() throws IOException{
		String resource = "eifPayCoreConfig.xml";
	    Reader reader = Resources.getResourceAsReader(resource);  
	    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
	    reader.close();  
	    SqlSession session = sqlSessionFactory.openSession();
	    try {
	    	EifPayCoreOperation eifPayCoreOperation=session.getMapper(EifPayCoreOperation.class);
	    	List<BankProvider> bankProviderList = eifPayCoreOperation.getBankProvider("03080000");
	    	if(bankProviderList.size()>0){
		    	for(int i=0;i<bankProviderList.size();i++){
		    		if(!bankProviderList.get(i).getProvider_no().equals("0002")){
		    			bankProviderList.get(i).setStatus(1);
			    		int result=eifPayCoreOperation.updateBankProviderStatus(bankProviderList.get(i));
		    		}
		    		else if(bankProviderList.get(i).getProvider_no().equals("0002")){
		    			bankProviderList.get(i).setStatus(0);
			    		int result=eifPayCoreOperation.updateBankProviderStatus(bankProviderList.get(i));
		    		}
		    	}    	
		        session.commit();
	    	}
	        
	    } finally {
	        session.close();
	    }
	}
	public static boolean matchApiLogs(String s){
		String regex="(<self>([\\d\\D]*)</self>)";
		Pattern pattern=Pattern.compile(regex);
		Matcher matcher=pattern.matcher(s);
		return matcher.matches();
	}
	public static String removeApiLog(String s){
		s=s.replaceAll("<self>([\\d\\D]*)</self><br />", "");
		s=s.replaceAll("<img(.*?)/>", "");
		return s;
	}
	public static String getImageString(String s)
	{
	    String regex = "(<self>([\\d\\D]*)</self>)";
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(s);
	    while (matcher.find()) {
	        String group = matcher.group(1);
	        //可根据实际情况多个图片 全部一起return
	        return group;
	    }
	    return "";
	}
	public static void clearInfoForFile(String fileName) {
        File file =new File(fileName);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	// 方法一
	public static String doubleTrans1(double num) {
		if (num % 1.0 == 0) {
			return String.valueOf((long) num);
		}
		return String.valueOf(num);
	}

	public static void downloadAPKWithBuildNo(String buildNo) {
		String cmd = "curl -O target/app/app2.4.apk http://172.16.59.251:8080/job/eif-android-app/job/release/"
				+ buildNo
				+ "/artifact/app/build/outputs/apk/app-default_channel-debug.apk --user huangxiao:Hxnearcj228";
		String result = runCommand(cmd);
		System.out.println(result);
	}

	public static String getGrouponTask() throws IOException {
		String resource = "eifMarketConfig.xml";
		List<Integer> rate = new ArrayList<Integer>();
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		reader.close();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			EifMarketOperation eifMarketOperation = session.getMapper(EifMarketOperation.class);
			List<MarketGrouponTask> getGrouponTaskList = eifMarketOperation.getGrouponTask("4519");
			if (getGrouponTaskList.size() > 0) {
				for (int i = 0; i < getGrouponTaskList.size(); i++) {
					rate.add(Integer.parseInt(getGrouponTaskList.get(i).getAward_rates()));
				}
			}
			Collections.sort(rate);
			String maxRate = rate.get(rate.size() - 1).toString();
			return maxRate;

		} finally {
			session.close();
		}
	}

	public static String getBaseProductId(String productName) throws Exception {
		String resource = "eifFisConfig.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		reader.close();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			EifFisOperation eifFisOperation = session.getMapper(EifFisOperation.class);
			FisProdInfo fisProdInfo = eifFisOperation.getFisProdInfo(productName);
			if (fisProdInfo == null) {
				return null;
			}
			return fisProdInfo.getBase_product_id();
		} finally {
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

	public static void runAdbCommand() {
		try {
			Process process = Runtime.getRuntime().exec("anyproxy --intercept --rule /usr/local/lib/node_modules/anyproxy/rule_sample/rule_log.js"); // adb
																		// shell
			final BufferedWriter outputStream = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
			final BufferedReader inputStream = new BufferedReader(new InputStreamReader(process.getInputStream()));
			// 这里一定要注意错误流的读取，不然很容易阻塞，得不到你想要的结果，
			final BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			new Thread(new Runnable() {
				String line;

				public void run() {
					System.out.println("listener started");
					try {
						while ((line = inputStream.readLine()) != null) {
							System.out.println(line);
						}
					} catch (IOException e) {
						// e.printStackTrace();
					}
				}
			}).start();
			new Thread(new Runnable() {
				final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

				public void run() {
					System.out.println("writer started");
					String line;
					try {
						while ((line = br.readLine()) != null) {
							outputStream.write(line + "\r\n");
							outputStream.flush();
						}
					} catch (IOException e) {
						// e.printStackTrace();
					}
				}
			}).start();
			int i = process.waitFor();
			System.out.println("i=" + i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String runCommand(String cmd) {
		String result = null;
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			// 取得命令结果的输出流
			InputStream fis = p.getInputStream();
			result = IOUtils.toString(fis, "UTF-8");
			// 用一个读输出流类去读
			InputStreamReader isr = new InputStreamReader(fis);
			// 用缓冲器读行
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			// 直到读完为止
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			p.waitFor();// 导致当前线程等待，如果必要，一直要等到由该 Process
						// 对象表示的进程已经终止。如果已终止该子进程，此方法立即返回。如果没有终止该子进程，调用的线程将被阻塞，直到退出子进程
			p.exitValue();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}