package com.hjs.mybatis.inter;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hjs.db.FisCollectPlan;
import com.hjs.db.FisCurrentProdInfo;
import com.hjs.db.FisHonorOrder;
import com.hjs.db.FisProdInfo;
import com.hjs.db.FisSecMarketProd;


public interface EifFisOperation {
	public FisProdInfo getFisProdInfo(String product_name);
	@Select("SELECT * from t_fis_current_prod_info where product_name=#{product_name}")
	public FisCurrentProdInfo getFisCurrentProdInfo(String product_name);
	
	public FisCollectPlan getFisCollectPlan(String assets_id);
	@Update("UPDATE t_fis_honor_order set prod_inception_date=#{prod_inception_date} where product_name=#{product_name}")
	public int setHonorOrderInceptionDate(FisHonorOrder fisHonorOrder);
	@Update("UPDATE t_fis_prod_info set inception_date=#{inception_date} where product_name=#{product_name}")
	public int setProdInfoInceptionDate(FisProdInfo fisProdInfo);
	@Update("update t_fis_sec_market_prod set inception_date=#{inception_date} where product_name=#{product_name}")
	public int setSecMarketInceptionDate(FisSecMarketProd fisSecMarketPro);
}
