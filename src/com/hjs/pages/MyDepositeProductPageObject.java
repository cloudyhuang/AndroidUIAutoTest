package com.hjs.pages;


import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.testng.Assert;

import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.jobclient.JobClient;
import com.github.ltsopensource.jobclient.RetryJobClient;
import com.github.ltsopensource.jobclient.domain.Response;
import com.hjs.Interface.GetOrderPushStatus;
import com.hjs.config.CommonAppiumPage;
import com.hjs.db.DBOperation;
import com.hjs.db.FisHonorOrder;
import com.hjs.db.FisProdInfo;
import com.hjs.db.FisSecMarketProd;
import com.hjs.mybatis.inter.EifFisOperation;
import com.hjs.publics.Util;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class MyDepositeProductPageObject extends CommonAppiumPage{
	//@AndroidFindBy(id="iv_filter")
	@AndroidFindBy(id="tv_holding")
	private AndroidElement filterBtn;		//筛选按钮
	@AndroidFindBy(id="refresh_animationView")
	private AndroidElement refreshView;		//刷新图标
	@AndroidFindBy(id="tv_filter_all")
	private AndroidElement allFilter;		//全部持有筛选项
	@AndroidFindBy(id="tv_filter_transfer")
	private AndroidElement transferFilter;		//可转让筛选项
	@AndroidFindBy(id="tv_redeem")
	private AndroidElement redeemFilter;		//已回款筛选项
	
	
	private By productNameLocator=By.id("textView_product");
	private By refreshViewLocator=By.id("refresh_animationView");
	public static GetOrderPushStatus orderpushstatus = new GetOrderPushStatus();
	public MyDepositeProductPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public boolean assertRedeemProduct(String productName) throws Exception{
		this.filterRedeemProduct();		//筛选已回款产品
		waitEleUnVisible(refreshViewLocator, 60);
		String productXpath="//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_product' and @text='"+productName+"']";
		super.scrollTo(productXpath);
		AndroidElement productNameEle=driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_product' and @text='"+productName+"']"));
		AndroidElement productRedeemDay=driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_product' and @text='"+productName+"']//ancestor::android.widget.LinearLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/layout_product_title']/parent::android.widget.LinearLayout//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/tv_item_title' and contains(@text,'回款')]"));
		AndroidElement productRedeemTo=driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_product' and @text='"+productName+"']//ancestor::android.widget.LinearLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/layout_product_title']/parent::android.widget.LinearLayout//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/tv_item_title' and contains(@text,'至')]"));
		AndroidElement productInvestMoney=driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_product' and @text='"+productName+"']//ancestor::android.widget.LinearLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/layout_product_title']/parent::android.widget.LinearLayout//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/tv_item_remark_des'][1]"));
		AndroidElement productInvestProfit=driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_product' and @text='"+productName+"']//ancestor::android.widget.LinearLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/layout_product_title']/parent::android.widget.LinearLayout//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/tv_item_remark_des' and contains(@text,'实际收益')]"));
		AndroidElement productInvestStatus=driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_product' and @text='"+productName+"']/parent::android.widget.LinearLayout/parent::android.widget.LinearLayout/parent::android.widget.LinearLayout/android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_invest_state']"));
		String appInvestMoney=Util.get2DecimalPointsNumInString(super.getEleText(productInvestMoney, "投资金额"));
		String appInvestProfit=Util.get2DecimalPointsNumInString(super.getEleText(productInvestProfit, "实际收益"));
		String appProductInvestStatus=super.getEleText(productInvestStatus, "投资状态");
		String appProductRedeemTo=super.getEleText(productRedeemTo, "回款去向");
		Assert.assertEquals(appProductInvestStatus, "转让成功");
		Assert.assertEquals(appInvestMoney, "100.00");
		Assert.assertEquals(appInvestProfit, "0.87");
		Assert.assertEquals(appProductRedeemTo, "至账户余额");
		
		return true;
	}
	public MyRedeemProductDetailPageObject enterRedeemProductDetail(String productName) throws Exception{
		this.filterRedeemProduct();		//筛选已回款产品
		waitEleUnVisible(refreshViewLocator, 60);
		String productXpath="//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_product' and @text='"+productName+"']";
		super.scrollTo(productXpath);
		try{
			AndroidElement productNameEle=driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_product' and @text='"+productName+"']"));
			productNameEle.click();
		}
		catch(Exception e){
			Assert.assertTrue(false,"未找到\""+productName+"\"理财产品");
		}
		return new MyRedeemProductDetailPageObject(driver);
	}
	public boolean assertMyDepositeProduct(String productName) throws IOException, ParseException{
		DBOperation db=new DBOperation();
		String productId=db.getProductId(productName);
		String url="http://172.16.57.47:48080//eif-fis-web/rpc/call/com.eif.fis.facade.biz.omc.OmcFacade/queryProductDetail";
		String params="[{ \"productId\":46381 }]";		
		JSONArray postJsonArray=new JSONArray(params);
		postJsonArray.getJSONObject(0).put("productId", productId);
		String httpResult = orderpushstatus.sendJsonPost(url, postJsonArray.toString());
		int httpStatusCode = orderpushstatus.getStatusCode(); // 响应码
		System.out.println(httpResult);
		JSONObject resJsonobj = new JSONObject(httpResult);
		String msg=String.valueOf(resJsonobj.get("msg"));
		boolean success=resJsonobj.getBoolean("success");
		Assert.assertTrue(success,"接口返回错误，错误信息:"+msg);
		long longInceptionDate=resJsonobj.getLong("inceptionDate");
		String inceptionDate=Util.longToDate(longInceptionDate, "yyyy-MM-dd");		//成立日
		int normalRedeemMode=resJsonobj.getInt("normalRedeemMode");	//兑付日T+N
		long longDueDate=resJsonobj.getLong("dueDate");
		longDueDate=Util.addDateByWorkDay(longDueDate, normalRedeemMode);	//结束日加上兑付工作日
		String dueDate=Util.longToDate(longDueDate, "yyyy-MM-dd");
		waitEleUnVisible(refreshViewLocator, 60);
		try{
			String productXpath="//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_product' and @text='"+productName+"']";
			super.scrollTo(productXpath);
			AndroidElement productNameEle=driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_product' and @text='"+productName+"']"));
			AndroidElement productInvestMoney=driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_product' and @text='"+productName+"']//ancestor::android.widget.LinearLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/layout_product_title']/parent::android.widget.LinearLayout//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/tv_item_remark_des'][1]"));
			AndroidElement productInvestProfit=driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_product' and @text='"+productName+"']//ancestor::android.widget.LinearLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/layout_product_title']/parent::android.widget.LinearLayout//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/tv_item_remark_des' and contains(@text,'预期收益')]"));
			AndroidElement productInvestDay=driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_product' and @text='"+productName+"']//ancestor::android.widget.LinearLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/layout_product_title']/parent::android.widget.LinearLayout//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/tv_item_title' and contains(@text,'投资')]"));
			AndroidElement productReturnMoneyDay=driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_product' and @text='"+productName+"']//ancestor::android.widget.LinearLayout[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/layout_product_title']/parent::android.widget.LinearLayout//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/tv_item_title' and contains(@text,'回款')]"));
			
			String appInvestDay=super.getEleText(productInvestDay, "投资日期");
			String appReturnMoneyDay=super.getEleText(productReturnMoneyDay, "回款日期");
			Pattern p=Pattern.compile("[^0-9-]");	//保留日期
			Matcher m = p.matcher(appInvestDay);
			appInvestDay=m.replaceAll("").trim();
			m = p.matcher(appReturnMoneyDay);
			appReturnMoneyDay=m.replaceAll("").trim();
			Assert.assertEquals(appInvestDay, inceptionDate,"app显示投资日期与成立日期不同");
			Assert.assertEquals(appReturnMoneyDay, dueDate,"app显示回款日期与‘结束日+兑付日’不相等");
			String appInvestMoney=Util.getDecimalNumInString(super.getEleText(productInvestMoney, "产品投资金额"));
			String appInvestProfit=Util.getDecimalNumInString(super.getEleText(productInvestProfit, "产品投资收益"));
			Assert.assertEquals(appInvestMoney, "900.00");
			Assert.assertEquals(appInvestProfit, "51.00");
		}
		catch(Exception e){
			Assert.assertTrue(false,"未找到\""+productName+"\"理财产品");
		}
		return true;
	}
	public MyDepositeProductDetailPageObject enterDepositeProductDetail(String productName){
		waitEleUnVisible(refreshViewLocator, 60);
		try{
			String productXpath="//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_product' and @text='"+productName+"']";
			super.scrollTo(productXpath);
			AndroidElement productNameEle=driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_product' and @text='"+productName+"']"));
			clickEle(productNameEle,"我的理财产品名称");
		}
		catch(Exception e){
			Assert.assertTrue(false,"未找到\""+productName+"\"理财产品");
		}
		return new MyDepositeProductDetailPageObject(driver);
	}
	public MyDepositeProductDetailPageObject enterTransProductDetail(String productName){
		this.filterTransProduct();
		try{
			String productXpath="//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_product' and @text='"+productName+"']";
			super.scrollTo(productXpath);
			AndroidElement productNameEle=driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_product' and @text='"+productName+"']"));
			clickEle(productNameEle,"我的转让产品名称");
		}
		catch(Exception e){
			Assert.assertTrue(false,"未找到\""+productName+"\"转让产品");
		}
		return new MyDepositeProductDetailPageObject(driver);
	}
	public MyDepositeProductDetailPageObject enterAcceptTransProductDetail(String productName){
		this.filterTransProduct();
		try{
			String productXpath="//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_product' and @text='"+"[受让]"+productName+"']";
			super.scrollTo(productXpath);
			AndroidElement productNameEle=driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_product' and @text='"+"[受让]"+productName+"']"));
			clickEle(productNameEle,"我的受让产品名称");
		}
		catch(Exception e){
			Assert.assertTrue(false,"未找到\""+productName+"\"受让产品");
		}
		return new MyDepositeProductDetailPageObject(driver);
	}
	public TransDetailPageObject transApply(String prodName) throws ParseException, IOException{
		String userDate=Util.getUserDate("yyyy-MM-dd HH:mm:ss");
		long inceptionLongDate=Util.minusDate(Util.dateToLong(userDate,"yyyy-MM-dd HH:mm:ss"),10);	//成立日往前推10天
		String inceptionDate=Util.longToDate(inceptionLongDate, "yyyy-MM-dd");
		this.setTransProdInceptionDate(prodName, inceptionDate);
		String jobDate=Util.longToDate(inceptionLongDate, "yyyyMMdd");
		this.runInceptionDateJob(jobDate); //跑成立job
		super.threadsleep(15000);	//等待Job生效
		this.filterTransProduct();
		try{
			String productXpath="//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_product' and @text='"+prodName+"']";
			super.scrollTo(productXpath);
			AndroidElement applyTransBtn=driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_product' and @text='"+prodName+"']/parent::android.widget.LinearLayout/parent::android.widget.LinearLayout/parent::android.widget.LinearLayout/parent::android.widget.LinearLayout//*[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/tv_apply_transfer']"));
			clickEle(applyTransBtn,"申请转让按钮");
		}
		catch(Exception e){
			Assert.assertTrue(false,"未找到\""+prodName+"\"理财产品");
		}
		return new TransDetailPageObject(driver);
		
	}
	public TransDetailPageObject acceptTransProdTransApply(String prodName) throws ParseException, IOException{
		String userDate=Util.getUserDate("yyyy-MM-dd HH:mm:ss");
		long inceptionLongDate=Util.minusDate(Util.dateToLong(userDate,"yyyy-MM-dd HH:mm:ss"),10);	//成立日往前推10天
		String inceptionDate=Util.longToDate(inceptionLongDate, "yyyy-MM-dd");
		this.setSecMarketInceptionDate(prodName, inceptionDate);
		this.filterTransProduct();
		try{
			String productXpath="//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_product' and @text='"+"[受让]"+prodName+"']";
			super.scrollTo(productXpath);
			AndroidElement applyTransBtn=driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_product' and @text='"+"[受让]"+prodName+"']/parent::android.widget.LinearLayout/parent::android.widget.LinearLayout/parent::android.widget.LinearLayout/parent::android.widget.LinearLayout//*[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/tv_apply_transfer']"));
			clickEle(applyTransBtn,"申请转让按钮");
		}
		catch(Exception e){
			Assert.assertTrue(false,"未找到\""+"[受让]"+prodName+"\"受让产品");
		}
		return new TransDetailPageObject(driver);
		
	}
	public void filterTransProduct(){
		clickEle(filterBtn,"筛选按钮");
		threadsleep(1000);
		//clickPoint(54,283);// 点击可转让
		clickEle(transferFilter,"可转让筛选项");
		waitEleUnVisible(refreshViewLocator, 120);
		swipeToDown(1000,1);	//下滑刷新
		waitEleUnVisible(refreshViewLocator, 120);
	}
	public void filterRedeemProduct(){
		clickEle(redeemFilter,"已回款筛选按钮");
		waitEleUnVisible(refreshViewLocator, 60);
	}
	public String getTransProductStatus(String productName){
		this.filterTransProduct();
		try{
			String productXpath="//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_product' and @text='"+productName+"']";
			super.scrollTo(productXpath);
		}
		catch(Exception e){
			Assert.assertTrue(false,"未找到\""+productName+"\"理财产品");
		}
		AndroidElement prodStatus=driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_product' and @text='"+productName+"']/parent::android.widget.LinearLayout/parent::android.widget.LinearLayout/parent::android.widget.LinearLayout/android.widget.TextView[@resource-id='com.evergrande.eif.android.hengjiaosuo:id/textView_invest_state']"));
		return prodStatus.getText();
	}
	public void runInceptionDateJob(String jobDate){
		JobClient jobClient = new RetryJobClient();
		jobClient.setNodeGroup("AndroidAutoTest");
		jobClient.setClusterName("hdfax_cluster");
		jobClient.setRegistryAddress("zookeeper://172.16.57.14:42181");
		jobClient.start();
		String userDate=Util.getUserDate("yyyyMMddHHmmss");
		// 提交任务
		Job job = new Job();
		job.setTaskId("AndroidAutoTest"+userDate);
		job.setParam("jobDate", jobDate);
		job.setParam("type", "PRODUCT_ESTABLISH");
		job.setParam("prodType", "1");
		job.setTaskTrackerNodeGroup("ftc_task_tracker");
		// job.setCronExpression("0 0/1 * * * ?");  // 支持 cronExpression表达式
		// job.setTriggerTime(new Date()); // 支持指定时间执行
		Response response = jobClient.submitJob(job);
		jobClient.stop();
	}
	public void setTransProdInceptionDate(String prodName,String inceptionDate) throws IOException{
		String resource = "eifFisConfig.xml";
	    Reader reader = Resources.getResourceAsReader(resource);  
	    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
	    reader.close();  
	    SqlSession session = sqlSessionFactory.openSession();
	    FisHonorOrder fisHonorOrder=new FisHonorOrder();
	    fisHonorOrder.setProd_inception_date(inceptionDate);
	    fisHonorOrder.setProduct_name(prodName);
	    FisProdInfo fisProdInfo=new FisProdInfo();
	    fisProdInfo.setProduct_name(prodName);
	    fisProdInfo.setInception_date(inceptionDate);
	    try {
	    	EifFisOperation eifFisOperation=session.getMapper(EifFisOperation.class);
			eifFisOperation.setProdInfoInceptionDate(fisProdInfo);
			eifFisOperation.setHonorOrderInceptionDate(fisHonorOrder);
		    session.commit();
	        
	    } finally {
	        session.close();
	    }
	}
	public void setSecMarketInceptionDate(String prodName,String inceptionDate) throws IOException{
		String resource = "eifFisConfig.xml";
	    Reader reader = Resources.getResourceAsReader(resource);  
	    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
	    reader.close();  
	    SqlSession session = sqlSessionFactory.openSession();
	    FisSecMarketProd fisSecMarketProd=new FisSecMarketProd();
	    fisSecMarketProd.setInception_date(inceptionDate);
	    fisSecMarketProd.setProduct_name(prodName);
	    
	    try {
	    	EifFisOperation eifFisOperation=session.getMapper(EifFisOperation.class);
			eifFisOperation.setSecMarketInceptionDate(fisSecMarketProd);
		    session.commit();
	        
	    } finally {
	        session.close();
	    }
	}
	public boolean verifyInthisPage(){
		waitEleUnVisible(refreshViewLocator, 120);
		return isElementExsit(productNameLocator);
	}
}
