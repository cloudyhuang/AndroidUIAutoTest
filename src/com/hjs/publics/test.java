package com.hjs.publics;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

public class test {

	//测试方法如下：
	public static void main(String[] args) throws ParseException {
		
		
		System.out.print(Util.addDate(dateToLong(Util.getUserDate("yyyy-MM-dd HH:mm:ss")), 10));
		;
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
