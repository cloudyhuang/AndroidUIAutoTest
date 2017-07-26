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
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.lang3.StringEscapeUtils;
import org.eclipse.jetty.websocket.api.Session;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.hjs.config.AppiumServer;

public class test {
	public static void main(String[] args) {
		int arr[][] = new int[20][];
		arr[0] = new int[] { 3, 2, 1, 1, 0 };
		arr[1] = new int[] { 5, 7, 3, 2, 0 };
		arr[2] = new int[] { 1, 2, 4, 5 };
		arr[3] = new int[] { 4, 3, 2, 1, 0 };
		arr[4] = new int[] { 1, 3, 4, 5, 6 };
		arr[5] = new int[] { 3, 2, 1, 0 };
		arr[6] = new int[] { 0, 1, 2, 3 };
		arr[7] = new int[] { 5, 5, 5, 0 };
		arr[8] = new int[] { 1, 3, 5 };
		arr[9] = new int[] { 1, 2, 3, 5 };
		arr[10] = new int[] { 1, 2, 3, 4 };
		arr[11] = new int[] { 1, 3, 4, 6 };
		arr[12] = new int[] { 0, 1, 2, 4, 6 };
		arr[13] = new int[] { 1, 2, 3, 4, 0 };
		arr[14] = new int[] { 1, 3, 5 };
		arr[15] = new int[] { 2, 4, 5, 6, 6 };
		arr[16] = new int[] { 0, 1, 3, 5, 6 };
		arr[17] = new int[] { 0, 2, 3, 4 };
		arr[18] = new int[] { 0, 1, 2, 4, 5 };
		arr[19] = new int[] { 7, 5, 4, 3, 1 };
		int b[]=new int[20];
		int score=0;
		for (int i = 0; i < arr.length; i++) {
			int max=arr[i][0];
			int max2=max;
			int maxi=0;
			int max2i=maxi;
			for(int j=0;j<arr[i].length;j++){
				if((max==max2)&&(arr[i][j]<max2)){
					max2=arr[i][j];
					max2i=j;
				}
				else if(arr[i][j]>max) {
					max2=max;
					max2i=maxi;
					max=arr[i][j];
					maxi=j;
				}
			}
			b[i]=max2i;
		}
		for(int i=0;i<b.length;i++){
			System.out.print(b[i]+" ");
		}
		for(int i=0;i<arr.length;i++){
			score=score+arr[i][b[i]];
		}
		System.out.println("分数为"+score);
	}

}