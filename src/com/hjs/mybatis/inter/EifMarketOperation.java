package com.hjs.mybatis.inter;

import java.util.List;

import com.hjs.db.ActivityCoupon;
import com.hjs.db.MarketGrouponTask;


public interface EifMarketOperation {
	public ActivityCoupon getActivityCoupon(String id);
	public List<MarketGrouponTask> getGrouponTask(String base_prod_id);
	public void deleteUserCoupon(String user_id);
}
