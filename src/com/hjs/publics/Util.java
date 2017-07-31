package com.hjs.publics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
	public static String getcurrentDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");// 自定义日期格式
		return df.format(new Date());
	}
	/**
	   * 传入时间戳和需要增加的天数，返回n天后的时间戳。
	   *
	   * @param time 时间戳
	   * @param addDays 增加天数
	   * @return time+addDays
	   */
	public  static long addDate(long time,int addDays){
		Date date = new Date(time);
	     Calendar   calendar   =   new   GregorianCalendar(); 
	     calendar.setTime(date); 
	     calendar.add(calendar.DATE,addDays);//把日期往后增加一天.整数往后推,负数往前移动 
	     date=calendar.getTime();   //这个时间就是日期往后推一天的结果 
	     return date.getTime();
	}
	/**
	   * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
	   *
	   * @param sformat yyyyMMddhhmmss
	   * @return
	   */
	  public static String getUserDate(String sformat) {
	    Date currentTime = new Date();
	    SimpleDateFormat formatter = new SimpleDateFormat(sformat);
	    String dateString = formatter.format(currentTime);
	    return dateString;
	  }
	  /**
	   * 根据用户传入的时间,和对应时间格式，转换为相应时间戳
	   *
	   * @param time 时间
	   * @param sformat yyyyMMddhhmmss
	   * @return
	   */
	public static long dateToLong(String time,String sformat) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(sformat);
		// String time="1970-01-06 11:45:55";
		Date date = format.parse(time);
		return date.getTime();
	}

	/*
	 * System.out.println(dateToLong("2017-06-18 20:50:35"));
	 * System.out.println(longToDate(1496904635362L));
	 */
	public static String longToDate(long times) {
		Date date = new Date(times);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(date);
		return dateString;
	}
	 /**
	   * 从字符串中取出数字
	   *
	   * @param string 字符串
	   * @return 字符串中的数字
	   */
	public static String getNumInString(String string) {
		//Pattern p = Pattern.compile("\\d+(\\.\\d+)?");
		Pattern p=Pattern.compile("[^0-9]");
		Matcher m = p.matcher(string);
//		if (m.find()) {
//			return m.group();
//		} else
			return m.replaceAll("").trim();
	}

	public static double stringToDouble(String string) {
		return Double.parseDouble(string);
	}

	public static <T extends Comparable<T>> boolean compare(List<T> a, List<T> b) {
		if (a.size() != b.size())
			return false;
		Collections.sort(a);
		Collections.sort(b);
		for (int i = 0; i < a.size(); i++) {
			if (!a.get(i).equals(b.get(i)))
				return false;
		}
		return true;
	}
}
