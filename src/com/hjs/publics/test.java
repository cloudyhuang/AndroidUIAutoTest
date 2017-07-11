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
import org.testng.Assert;
import org.testng.annotations.Test;

public class test {
	String message = "Manisha";

	@Test
	public void testPrintMessage() {
		System.out.println("Inside testPrintMessage()");
		message = "Manisha";
		Assert.assertEquals(message, "Manisha");
	}

	@Test(dependsOnMethods = { "initEnvironmentTest" })
	public void testSalutationMessage() {
		System.out.println("Inside testSalutationMessage()");
		message = "Hi!" + "Manisha";
		Assert.assertEquals(message, "Manisha");
	}

	@Test
	public void initEnvironmentTest() {
		System.out.println("This is initEnvironmentTest");
		message = "manisha";
		Assert.assertEquals(message, "Manisha");
	}
}
