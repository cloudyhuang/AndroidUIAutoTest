package com.hjs.pages;


import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.openqa.selenium.By;
import org.testng.Assert;

import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.jobclient.JobClient;
import com.github.ltsopensource.jobclient.RetryJobClient;
import com.github.ltsopensource.jobclient.domain.Response;
import com.hjs.config.CommonAppiumPage;
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
	
	
	private By productNameLocator=By.id("textView_product");
	private By refreshViewLocator=By.id("refresh_animationView");
	public MyDepositeProductPageObject(AndroidDriver<AndroidElement> driver) {
		super(driver);
	}
	public MyDepositeProductDetailPageObject enterDepositeProductDetail(String productName){
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
		waitEleUnVisible(refreshViewLocator, 30);
		swipeToDown(1000,1);	//下滑刷新
		waitEleUnVisible(refreshViewLocator, 30);
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
		return isElementExsit(productNameLocator);
	}
}
