package com.hjs.publics;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
public class test {
	//测试方法如下：
	public static void main(String[] args)  {

		System.out.println(getDeviceUdid());
		System.out.println(getDeviceName());
	}
	
	/**
     *  get Device UDID
     */
    public static String getDeviceUdid() {
        List<String> list = getDeviceInfo();
        String[] split = {"", "", ""};
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).contains("device")&&(!list.get(i).equals("List of devices attached"))) {
                split = list.get(i).split(" ");       
            }       
        }

        return split[0];
    }

    /**
     *  get Device Name
     */
    public static String getDeviceName() {
        List<String> list = getDeviceInfo();
        int index = 0;
        String[] split = {"", "", ""};
        
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).contains("model:")) {
                index = list.get(i).indexOf("model:");
                split = list.get(i).substring(index + 6).split(" ");        
            }       
        }

        return split[0];
    }    

    public static List<String> getDeviceInfo() {
        List<String> list = new LinkedList<String>();
        String line = "";  
        String command = "cmd /c adb devices -l";
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));                
            while ((line = reader.readLine()) != null) {
                list.add(line.toString());
            }
            process.waitFor();
         
        } catch (IOException | InterruptedException e) {
            e.getMessage();
        }
        
        return list;
    }
    public static String getImageString(String s)
    {
        String regex = "(<img(.*?)/>)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            String group = matcher.group(1);
            //可根据实际情况多个图片 全部一起return
            return group;
        }
        return "";
    }
	public static void printCurrentTime(){
		while(true){
			System.out.println(System.currentTimeMillis());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public  static long addDate(long time,int addDays){
		Date date = new Date(time);
	     Calendar   calendar   =   new   GregorianCalendar(); 
	     calendar.setTime(date); 
	     calendar.add(calendar.DATE,addDays);//把日期往后增加一天.整数往后推,负数往前移动 
	     date=calendar.getTime();   //这个时间就是日期往后推一天的结果 
	     return date.getTime();
	}
	public static long dateToLong(String time) throws ParseException{
		 SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		    //String time="1970-01-06 11:45:55";  
		    Date date = format.parse(time);  
		    return date.getTime();
	}
	public static String longToDate(long times){
	    Date date = new Date(times);
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String dateString = formatter.format(date);
	    return dateString;
	}
}
