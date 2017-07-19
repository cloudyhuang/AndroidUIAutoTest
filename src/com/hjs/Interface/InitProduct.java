package com.hjs.Interface;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import com.hjs.db.FisCollectPlan;
import com.hjs.db.FisProdInfo;
import com.hjs.mybatis.inter.EifFisOperation;
import com.hjs.publics.Util;


public class InitProduct {
	public String httpResult = null;
	public int httpStatusCode;
	public static GetOrderPushStatus orderpushstatus = new GetOrderPushStatus();
	private String productName;
	private String baseProductName;
	private String minBuyAmt;
	private String productLimit;
	private String displayRate;
	private String mrktPlusRate;
	
	public static class Builder{    
		private String productName=null;
		private String baseProductName="黄XAutoTest基础产品";
		private String minBuyAmt="100";
		private String productLimit="365";
		private String displayRate="6+0.3";
		private String mrktPlusRate="0.01";
            
        public Builder(String productName){    
            this.productName = productName;    
        }    
        public Builder setBaseProductName(String baseProductName){    
            this.baseProductName = baseProductName;    
            return this;    
        }  
        public Builder setMinBuyAmt(String minBuyAmt){    
            this.minBuyAmt = minBuyAmt;    
            return this;    
        }    
            
        public Builder setProductLimit(String productLimit){    
            this.productLimit = productLimit;    
            return this;    
        }    
            
        public Builder setDisplayRate(String displayRate){    
            this.displayRate = displayRate;    
            return this;    
        }    
        public Builder setMrktPlusRate(String mrktPlusRate){    
            this.mrktPlusRate = mrktPlusRate;    
            return this;    
        }       
        public InitProduct build(){    
            return new InitProduct(this);    
        }    
    }    
        
    private InitProduct(Builder builder){     
        this.productName = builder.productName;    
        this.baseProductName=builder.baseProductName;
        this.minBuyAmt = builder.minBuyAmt;    
        this.productLimit = builder.productLimit;    
        this.displayRate = builder.displayRate;  
        this.mrktPlusRate=builder.mrktPlusRate;
    }  

	public void creatProduct() throws IOException, ParseException {
		String currentDate=Util.getcurrentDate();
		String userDate=Util.getUserDate("yyyy-MM-dd HH:mm:ss");
		//创建通道
		String channelName="黄XAutoTest通道"+currentDate;
		String channelId=this.createAssetChannel(channelName);
		//创建路径
		String pathName="黄XAutoTest路径"+currentDate;
		String assetPathId=this.createAssetPath(pathName,channelId, channelName);
		//创建项目
		String borrowerSubject="黄XAutoTest借款主体"+currentDate;
		String projectName="黄XAutoTest资管项目"+currentDate;
		String projectAssetsNo="AutoTest"+currentDate;
		String assetProjectId=this.createAssetProject(borrowerSubject, projectName,projectAssetsNo);
		//创建基础资产管理
		String bgAssetsName="黄XAutoTest资管项目"+currentDate;
		String bgAssetsNo="黄XAutoTest资管项目编号"+currentDate;
		String bgAssetId=this.createBackgroundAssets(bgAssetsName, bgAssetsNo, assetPathId, assetProjectId);
		//创建资管份额
		String assetsName="黄XAutoTest资管份额"+currentDate;	
		String assetsNo="资管项目编号"+currentDate;
		long collectEndTime = Util.addDate(Util.dateToLong(userDate,"yyyy-MM-dd HH:mm:ss"),10);
		String assetsId=this.createAsset(assetsName, assetsNo,bgAssetId,collectEndTime);
		//创建募集计划
		long startTime=Util.dateToLong(userDate,"yyyy-MM-dd HH:mm:ss");
		long endTime=Util.addDate(Util.dateToLong(userDate,"yyyy-MM-dd HH:mm:ss"),10);
		String planId=this.createCollectPlan(assetsId, startTime,endTime);
		//创建产品
		this.creatProduct(assetsId, planId);
		//产品上架
		String productId=this.getProductId(productName); 
		this.productIssued(productId);
	}
	public String getProductLimit() {
		return productLimit;
	}

	public String getProductName() {
		return productName;
	}
	public String getMinBuyAmt() {
		return minBuyAmt;
	}
	public String getDisplayRate() {
		return displayRate;
	}
	public String getMrktPlusRate() {
		return mrktPlusRate;
	}
	public String createAssetChannel(String channelName){
		String url="http://172.16.57.47:48080//eif-fis-web/rpc/call/com.eif.fis.facade.biz.omc.OmcFacade/createAssetChannel";
		String params="[{\"assetChannelDetail\":{\"chanStatus\":\"1\",\"channelName\":\"黄XAutoTest通道201706141027\",\"channelType\":\"0\",\"comment\":\"通道\",\"hdfax\":{\"bankInfos\":[{\"bankAccHolder\":\"屋里\",\"bankAccount\":\"622578676566787862\",\"bankName\":\"上海银行\",\"status\":\"1\"}],\"orgName\":\"hdfax\"},\"isEditable\":\"true\"}}]";
		JSONArray postJsonArray=new JSONArray(params);
		postJsonArray.getJSONObject(0).getJSONObject("assetChannelDetail").put("channelName", channelName);
		httpResult = orderpushstatus.sendJsonPost(url, postJsonArray.toString());
		httpStatusCode = orderpushstatus.getStatusCode(); // 响应码
		JSONObject postJsonobj = new JSONObject(httpResult);
		String msg=String.valueOf(postJsonobj.get("msg"));
		boolean success=postJsonobj.getBoolean("success");
		Assert.assertTrue(success,"接口返回错误，错误信息:"+msg);
		String channelId=String.valueOf(postJsonobj.get("id"));
		return channelId;
	}
	public String createAssetPath(String pathName,String channelId,String channelName){
		String url="http://172.16.57.47:48080//eif-fis-web/rpc/call/com.eif.fis.facade.biz.omc.OmcFacade/createAssetPath";
		String params="[{\"assetPath\":{\"cashInChannel\":\"117\",\"cashOutChannel\":\"117\",\"chanIdJson\":\"[117]\",\"comment\":\"路径\",\"detailJson\":[{\"chanStatus\":\"1\",\"channelName\":\"黄XAutoTest通道170616141337\",\"channelType\":\"0\",\"comment\":\"通道\",\"id\":\"117\",\"isEditable\":\"true\"}],\"isEditable\":\"true\",\"pathName\":\"黄XAutoTest路径201706161709\",\"pathStatus\":\"1\",\"transStructure\":\"0\"}}]";
		JSONArray postJsonArray=new JSONArray(params);
		postJsonArray.getJSONObject(0).getJSONObject("assetPath").put("cashInChannel", channelId);
		postJsonArray.getJSONObject(0).getJSONObject("assetPath").put("cashOutChannel", channelId);
		postJsonArray.getJSONObject(0).getJSONObject("assetPath").put("chanIdJson", "["+channelId+"]");
		postJsonArray.getJSONObject(0).getJSONObject("assetPath").getJSONArray("detailJson").getJSONObject(0).put("channelName", channelName);
		postJsonArray.getJSONObject(0).getJSONObject("assetPath").getJSONArray("detailJson").getJSONObject(0).put("id", channelId);
		postJsonArray.getJSONObject(0).getJSONObject("assetPath").put("pathName", pathName);
		httpResult = orderpushstatus.sendJsonPost(url, postJsonArray.toString());
		httpStatusCode = orderpushstatus.getStatusCode(); // 响应码
		JSONObject postJsonobj = new JSONObject(httpResult);
		String msg=String.valueOf(postJsonobj.get("msg"));
		boolean success=postJsonobj.getBoolean("success");
		Assert.assertTrue(success,"接口返回错误，错误信息:"+msg);
		String assetPathId=String.valueOf(postJsonobj.get("id"));
		return assetPathId;
	}
	public String createAssetProject(String borrowerSubject,String projectName,String assetsNo) throws ParseException{
		String url="http://172.16.57.47:48080//eif-fis-web/rpc/call/com.eif.fis.facade.biz.omc.OmcFacade/createAssetProject";
		String params="[{\"assetProject\":{\"actualDays\":\"31\",\"advisorFeeRate\":\"0.3\",\"areaCorpRate\":\"0.3\",\"assetsCost\":\"0.3\",\"assetsNo\":\"AutoTest201706141139\",\"borrowerRate\":\"0.3\",\"borrowerSubject\":\"黄Xtest借款主体201706141139\",\"currency\":\"anc\",\"cutIntRate\":\"0.3\",\"entrustLoanRate\":\"0.3\",\"estateGroupRate\":\"0.3\",\"expireDate\":1497768634961,\"intPayMode\":\"计息方式\",\"materialCorpRate\":\"0.3\",\"monthPeriod\":\"31\",\"projectName\":\"黄Xtest资管项目201706141139\",\"projectType\":\"土地款\",\"regionCorp\":\"上海\",\"status\":\"1\",\"totalScale\":\"99999999\",\"transName\":\"交易方名称\",\"valueDate\":1496904634961}}]";
		String currentDate=Util.getUserDate("yyyy-MM-dd HH:mm:ss");
		long expireDate=Util.addDate(Util.dateToLong(currentDate,"yyyy-MM-dd HH:mm:ss"),10);
		long valueDate=Util.dateToLong(currentDate,"yyyy-MM-dd HH:mm:ss");
		JSONArray postJsonArray=new JSONArray(params);
		postJsonArray.getJSONObject(0).getJSONObject("assetProject").put("borrowerSubject", borrowerSubject);
		postJsonArray.getJSONObject(0).getJSONObject("assetProject").put("projectName", projectName);
		postJsonArray.getJSONObject(0).getJSONObject("assetProject").put("assetsNo", assetsNo);
		postJsonArray.getJSONObject(0).getJSONObject("assetProject").put("expireDate", expireDate);
		postJsonArray.getJSONObject(0).getJSONObject("assetProject").put("valueDate", valueDate);
		
		httpResult = orderpushstatus.sendJsonPost(url, postJsonArray.toString());
		httpStatusCode = orderpushstatus.getStatusCode(); // 响应码
		JSONObject postJsonobj = new JSONObject(httpResult);
		String msg=String.valueOf(postJsonobj.get("msg"));
		boolean success=postJsonobj.getBoolean("success");
		Assert.assertTrue(success,"接口返回错误，错误信息:"+msg);
		String assetProjectId=String.valueOf(postJsonobj.get("id"));
		return assetProjectId;
	}
	public String createBackgroundAssets(String assetsName,String assetsNo,String pathId,String projectId){
		String url="http://172.16.57.47:48080//eif-fis-web/rpc/call/com.eif.fis.facade.biz.omc.OmcFacade/createBackgroundAssets";
		String params="[{\"backgroundAssets\":{\"assetCategory\":\"定期\",\"assetPlanManager\":\"11\",\"assetsCost\":\"100\",\"assetsName\":\"黄Xtest资管项目201706141418\",\"assetsNo\":\"资管项目编号201706141417\",\"avaliableAmount\":\"100000000\",\"companyProfile\":\"111\",\"creditCtrlShareholder\":\"增信方控股股东\",\"creditMainBusiness\":\"增信方主营业务\",\"creditMeasure\":\"增信措施\",\"creditName\":\"增信方名称\",\"creditRegCapital\":\"100\",\"custodianBank\":\"323\",\"directedFinancingUnderwriter\":\"0\",\"disclosureName\":\"4322\",\"editable\":\"true\",\"entrustLoanBank\":\"323\",\"entrustLoanBeginTime\":1496904635054,\"entrustLoanEndTime\":1497768635054,\"entrustLoanIntDays\":\"10\",\"filingIssue\":\"0\",\"issueAgency\":\"发行机构\",\"issueMethodAndObject\":\"0\",\"pathId\":103,\"projectId\":83,\"projectType\":\"项目类型\",\"protocols\":[{\"BgAssetsProtocol\":{\"filePath\":\"\\\"group1/M00/02/46/rBA5Flk487WAfeEaAAAAjNZUajA77.html\\\"\"}}],\"repaymentSource\":\"还款来源\",\"specialExplanation\":\"0\",\"totalAmount\":\"1000000\",\"transFundPurpose\":\"交易方资金用途\",\"transMainBusiness\":\"交易方主营业务\",\"transRegCapital\":\"100\",\"transStruct\":\"0\",\"trusteeManager\":\"0\"}}]";
		
		JSONArray postJsonArray=new JSONArray(params);
		postJsonArray.getJSONObject(0).getJSONObject("backgroundAssets").put("projectId", projectId);
		postJsonArray.getJSONObject(0).getJSONObject("backgroundAssets").put("pathId", pathId);
		postJsonArray.getJSONObject(0).getJSONObject("backgroundAssets").put("assetsName", assetsName);
		postJsonArray.getJSONObject(0).getJSONObject("backgroundAssets").put("assetsNo", assetsNo);
		
		httpResult = orderpushstatus.sendJsonPost(url, postJsonArray.toString());
		httpStatusCode = orderpushstatus.getStatusCode(); // 响应码
		JSONObject postJsonobj = new JSONObject(httpResult);
		String msg=String.valueOf(postJsonobj.get("msg"));
		boolean success=postJsonobj.getBoolean("success");
		Assert.assertTrue(success,"接口返回错误，错误信息:"+msg);
		String assetProjectId=String.valueOf(postJsonobj.get("id"));
		return assetProjectId;
	}
	public String createAsset(String assetsName,String assetsNo,String bgAssetId,long collectEndTime){
		String url="http://172.16.57.47:48080//eif-fis-web/rpc/call/com.eif.fis.facade.biz.omc.OmcFacade/createAsset";
		String params="[{\"assertIssuerBankAccount\":\"上海银行\",\"assertIssuerBankHolder\":\"支行\",\"assertIssuerName\":\"内部账户开立(zhou)\",\"assertIssuerPaymentId\":\"QAI00000000000007166\",\"assertManagerBankAccount\":\"广发银行\",\"assertManagerBankHolder\":\"张华有\",\"assertManagerName\":\"张华有测试\",\"assertManagerPaymentId\":\"QAI00000000000007588\",\"assetManager\":\"2c90b9b856535f0e015654888246002b\",\"assetManagerBankAcct\":\"1234567890087654321\",\"assetManagerCard\":\"\",\"assetsAvaliableAmount\":\"50000000\",\"assetsCategory\":\"房地产\",\"assetsName\":\"黄Xtest资管份额201706141437\",\"assetsNo\":\"黄Xtest资管项目编号201706141437\",\"assetsScale\":\"50000000\",\"bgAssetId\":759,\"clearAccountSign\":\"5004\",\"collectEndTime\":1499496635126,\"plans\":[{\"endTime\":1497768635126,\"minScale\":\"10000000\",\"startTime\":1496991035126}],\"prodIssuerBankAcct\":\"11\",\"productIssuer\":\"2c90b9b8560776fa0156117cd6a60180\",\"productIssuerCard\":\"\",\"transStructureType\":\"0\"}]";
		
		JSONArray postJsonArray=new JSONArray(params);
		postJsonArray.getJSONObject(0).put("assetsName", assetsName);
		postJsonArray.getJSONObject(0).put("assetsNo", assetsNo);
		postJsonArray.getJSONObject(0).put("bgAssetId", bgAssetId);
		postJsonArray.getJSONObject(0).put("collectEndTime", collectEndTime);
		
		httpResult = orderpushstatus.sendJsonPost(url, postJsonArray.toString());
		httpStatusCode = orderpushstatus.getStatusCode(); // 响应码
		JSONObject postJsonobj = new JSONObject(httpResult);
		String msg=String.valueOf(postJsonobj.get("msg"));
		boolean success=postJsonobj.getBoolean("success");
		Assert.assertTrue(success,"接口返回错误，错误信息:"+msg);
		String assetProjectId=String.valueOf(postJsonobj.get("id"));
		return assetProjectId;
	}
	public String createCollectPlan(String assetsId,long startTime,long endTime) throws IOException{
		String url="http://172.16.57.47:48080//eif-fis-web/rpc/call/com.eif.fis.facade.biz.omc.OmcFacade/createCollectPlan";
		String params="[{\"plans\":[{\"assetsId\":905,\"endTime\":1497768635188,\"minScale\":\"10000000\",\"sequence\":\"0\",\"startTime\":1496904635188}]}]";		
		JSONArray postJsonArray=new JSONArray(params);
		postJsonArray.getJSONObject(0).getJSONArray("plans").getJSONObject(0).put("assetsId", assetsId);
		postJsonArray.getJSONObject(0).getJSONArray("plans").getJSONObject(0).put("startTime", startTime);
		postJsonArray.getJSONObject(0).getJSONArray("plans").getJSONObject(0).put("endTime", endTime);

		httpResult = orderpushstatus.sendJsonPost(url, postJsonArray.toString());
		httpStatusCode = orderpushstatus.getStatusCode(); // 响应码
		System.out.println(httpResult);
		JSONObject postJsonobj = new JSONObject(httpResult);
		String msg=String.valueOf(postJsonobj.get("msg"));
		boolean success=postJsonobj.getBoolean("success");
		Assert.assertTrue(success,"接口返回错误，错误信息:"+msg);
		String collectPlanId=null;
		if(success){
			collectPlanId=this.getCollectPlanId(assetsId);
		}
		return collectPlanId;
	}
	public void creatProduct(String assetsId,String planId) throws ParseException{
		String userDate=Util.getUserDate("yyyy-MM-dd HH:mm:ss");
		long startTime=Util.dateToLong(userDate,"yyyy-MM-dd HH:mm:ss");
		long endTime=Util.addDate(Util.dateToLong(userDate,"yyyy-MM-dd HH:mm:ss"),10);
		String url = ("http://172.16.57.62:48080/eif-omc-web/auth/login");
		String params = "account=huangxiao&password=123456";
		httpResult = orderpushstatus.sendx_www_form_urlencodedPost(url, params);//响应结果
		httpStatusCode=orderpushstatus.getStatusCode();  //响应码
		System.out.println(httpResult+"status:"+httpStatusCode);
		Assert.assertFalse(httpResult.contains("用户名"), "登录失败,出现用户名密码输入框");
		url = ("http://172.16.57.62:48080/eif-omc-web/prod/create");
		params = "{\"baseInfo\":{\"actualAnnualYieldRate\":\"0.05\",\"annualYieldRate\":\"0.05\",\"assetsId\":\"905\",\"baseProdName\":\"黄Xtest基础产品201706151031\",\"baseProdScale\":\"1000000\",\"cashOutType\":\"0\",\"chargeAmt\":\"1\",\"chargeType\":\"1\",\"closeType\":\"1\",\"currency\":\"CNY\",\"dailyMaxBuyAmt\":\"-1\",\"defaultInsType\":\"0\",\"earredeeIntberWay\":\"0\",\"excessRedeemAmt\":\"-1\",\"expectedAnnualYieldRate\":\"0.05\",\"groupBuyId\":\"-1\",\"groupons\":\"\",\"historicalAnnualYieldRate\":\"0.05\",\"holdAmt\":\"0\",\"hugeRedeemAmt\":\"-1\",\"incomePerMillion\":\"0.82\",\"interBear\":\"2\",\"interPeriod\":\"360\",\"invesStrategy\":\"2\",\"investmentUnit\":\"0\",\"isProfitReinvest\":\"false\",\"isRevenuesOnRedemptionDay\":\"true\",\"isSupportTrans\":\"false\",\"isTimeLimited\":\"false\",\"limitBuyAmt\":\"-1\",\"marketExtInfo\":{\"mrktPlusRate\":\"0.05\",\"mrktPlusRateDesc\":\"加息说明\"},\"marketingExtraYieldRate\":\"0.003\",\"maxBuyAmt\":\"-1\",\"maxSellAmt\":\"-1\",\"minBuyAmt\":\"1\",\"minSellAmt\":\"1\",\"normalRedeemAmt\":\"1000000\",\"normalRedeemMode\":\"3\",\"overdueRate\":\"0\",\"payMode\":\"0&1&2\",\"perAmount\":\"1\",\"perIncAmt\":\"100\",\"perUdbLimitAmt\":\"-1\",\"perUdsLimitAmt\":\"-1\",\"perYearDate\":\"365\",\"productGuarantee\":\"SA\",\"productLimit\":\"360\",\"productRank\":\"1\",\"profitMode\":\"0\",\"profitType\":\"0\",\"remainScale\":\"90000000\",\"shareClass\":\"1\",\"subRateCalcuMethod\":\"0\",\"taxRate\":\"0\",\"termType\":\"0\",\"valueDate\":\"2\",\"weekAnnualYieldRate\":\"0.05\"},\"conf\":{\"endTimeBeforeEstablish\":\"0\",\"establishHeadCount\":\"200\",\"establishPercentage\":\"0.5\",\"splitScale\":\"800000\"},\"prodInfo\":{\"attachPath\":\"[{\\\"filePath\\\" : \\\"group1/M00/02/70/rBA5FVk487uAf8peAAAAjNZUajA10.html\\\",\\\"tempName\\\" : \\\"免责说明\\\",\\\"tempType\\\" : \\\"explainFile\\\"}, {\\\"filePath\\\" : \\\"group1/M00/02/70/rBA5FVk487uAf8peAAAAjNZUajA10.html\\\",\\\"tempName\\\" : \\\"产品说明\\\",\\t\\\"tempType\\\" : \\\"rulesFile\\\"}, {\\t\\\"filePath\\\" : \\\"group1/M00/02/70/rBA5FVk487uAf8peAAAAjNZUajA10.html\\\",\\\"tempName\\\" : \\\"风险揭示\\\",\\\"tempType\\\" : \\\"riskFile\\\"}, {\\\"filePath\\\" : \\\"group1/M00/02/70/rBA5FVk487uAf8peAAAAjNZUajA10.html\\\",\\\"tempName\\\" : \\\"认购协议\\\",\\t\\\"tempType\\\" : \\\"targetFile\\\"}]\",\"bizChannel\":\"0\",\"clearAccountSign\":\"5004\",\"collectPeriod\":\"3\",\"contractTempPath\":\"group1/M00/02/70/rBA5FVk487uAf8peAAAAjNZUajA10.html\",\"displayBaseRate\":\"5\",\"displayPlusRate\":\"0.3\",\"displayRate\":\"5+0.3\",\"isComPay\":\"true\",\"isEmployeeExclusive\":\"false\",\"isForceRiskReview\":\"false\",\"isNovicePacks\":\"false\",\"isOwnMaster\":\"true\",\"isRecommend\":\"false\",\"isReservationPurchase\":\"false\",\"isRiskReview\":\"false\",\"isTimeLimited\":\"false\",\"isVisible\":\"true\",\"marketExtInfo\":{\"$ref\":\"$.baseInfo.marketExtInfo\"},\"orderExpireTime\":\"900\",\"planId\":\"1103\",\"planO\":{\"assetsId\":905,\"endTime\":1497768635000,\"minScale\":\"1000000\",\"sequence\":\"0\",\"startTime\":1497433129000},\"prodFirstCategoryFg\":\"2\",\"prodFouthCategoryFg\":\"2\",\"prodFouthCategoryFgO\":\"2\",\"prodSecondCategoryFg\":\"5\",\"prodThirdCategoryFg\":\"10\",\"productDesc\":\"SA\",\"productName\":\"黄Xtest产品06141031\",\"productRemark\":\"SA\",\"productScale\":\"1000000\",\"publishDate\":1497433129000,\"sellFlag\":\"00000000\",\"taxLiability\":\"SA\",\"transBeginTime\":1497433129000,\"transEndTime\":1497768635000,\"whiteListType\":\"0\"}}";
		JSONObject postJsonobj = new JSONObject(params);
		
		postJsonobj.getJSONObject("baseInfo").put("baseProdName", baseProductName);
		postJsonobj.getJSONObject("baseInfo").put("assetsId", assetsId);
		postJsonobj.getJSONObject("baseInfo").getJSONObject("marketExtInfo").put("mrktPlusRate", mrktPlusRate);
		
		postJsonobj.getJSONObject("baseInfo").put("productLimit", productLimit);
		
		
		postJsonobj.getJSONObject("baseInfo").put("minBuyAmt", minBuyAmt);
		postJsonobj.getJSONObject("prodInfo").put("displayRate", displayRate);
		postJsonobj.getJSONObject("prodInfo").put("planId", planId);
		postJsonobj.getJSONObject("prodInfo").put("productName", productName);
		postJsonobj.getJSONObject("prodInfo").put("publishDate", startTime);	//上架时间
		postJsonobj.getJSONObject("prodInfo").put("transBeginTime", startTime);	//发行开始时间
		postJsonobj.getJSONObject("prodInfo").put("transEndTime", endTime);	//发行结束时间
		postJsonobj.getJSONObject("prodInfo").getJSONObject("planO").put("startTime", startTime);
		postJsonobj.getJSONObject("prodInfo").getJSONObject("planO").put("endTime", endTime);
		httpResult = orderpushstatus.sendJsonPost(url, postJsonobj.toString());
		httpStatusCode = orderpushstatus.getStatusCode(); // 响应码
		Assert.assertTrue(httpResult.equals(""),"创建产品出错，错误响应返回："+httpResult);
	}
    /**
     * 产品上架
     * @param productId  产品id
     */
	public void productIssued(String productId){
		String url="http://172.16.57.47:48080//eif-fis-web/rpc/call/com.eif.fis.facade.biz.omc.OmcFacade/productIssued";
		String params="[{\"ids\":[]}]";		
		JSONArray postJsonArray=new JSONArray(params);
		postJsonArray.getJSONObject(0).getJSONArray("ids").put(productId);
		httpResult = orderpushstatus.sendJsonPost(url, postJsonArray.toString());
		httpStatusCode = orderpushstatus.getStatusCode(); // 响应码
		System.out.println(httpResult);
		JSONObject postJsonobj = new JSONObject(httpResult);
		String msg=String.valueOf(postJsonobj.get("msg"));
		boolean success=postJsonobj.getBoolean("success");
		Assert.assertTrue(success,"接口返回错误，错误信息:"+msg);
	}
	  /**
     * 产品下架
     * @param productName  产品名称
	 * @throws IOException 
     */
	public void productPullOffShelves(String productName) throws IOException{
		String productId=this.getProductId(productName); 
		String url="http://172.16.57.47:48080//eif-fis-web/rpc/call/com.eif.fis.facade.biz.omc.OmcFacade/productPullOffShelves";
		String params="[{\"ids\":[]}]";		
		JSONArray postJsonArray=new JSONArray(params);
		postJsonArray.getJSONObject(0).getJSONArray("ids").put(productId);
		httpResult = orderpushstatus.sendJsonPost(url, postJsonArray.toString());
		httpStatusCode = orderpushstatus.getStatusCode(); // 响应码
		System.out.println(httpResult);
		JSONObject postJsonobj = new JSONObject(httpResult);
		String msg=String.valueOf(postJsonobj.get("msg"));
		boolean success=postJsonobj.getBoolean("success");
		Assert.assertTrue(success,"接口返回错误，错误信息:"+msg);
	}
	
	public String getCollectPlanId(String assetsId) throws IOException{
		String resource = "eifFisConfig.xml";
        Reader reader = Resources.getResourceAsReader(resource);  
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
        reader.close();  
        SqlSession session = sqlSessionFactory.openSession();
        try {
        	EifFisOperation eifFisOperation=session.getMapper(EifFisOperation.class);
        	FisCollectPlan fisCollectPlan=eifFisOperation.getFisCollectPlan(assetsId);
        	if (fisCollectPlan==null){
        		return null;
        	}	
        	return fisCollectPlan.getId();
        }    
        finally {
            session.close();
        }
        
	}
    /**
     * 通过产品名称获取产品ID
     *
     * @param productName  产品名称
     * @return 产品ID
     */
	public String getProductId(String productName) throws IOException{
		String resource = "eifFisConfig.xml";
        Reader reader = Resources.getResourceAsReader(resource);  
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
        reader.close();  
        SqlSession session = sqlSessionFactory.openSession();
        try {
        	EifFisOperation eifFisOperation=session.getMapper(EifFisOperation.class);
        	FisProdInfo fisProdInfo=eifFisOperation.getFisProdInfo(productName);
        	if(fisProdInfo==null){
        		return null;
        	}
        	return fisProdInfo.getId();
        }
        finally {
            session.close();
        }
	}
}
