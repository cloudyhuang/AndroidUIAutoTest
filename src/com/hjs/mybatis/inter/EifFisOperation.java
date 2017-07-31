package com.hjs.mybatis.inter;

import com.hjs.db.FisCollectPlan;
import com.hjs.db.FisProdInfo;


public interface EifFisOperation {
	public FisProdInfo getFisProdInfo(String product_name);
	public FisCollectPlan getFisCollectPlan(String assets_id);
}
