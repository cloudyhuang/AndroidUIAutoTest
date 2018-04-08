package com.hjs.mybatis.inter;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.hjs.db.ActivityCoupon;
import com.hjs.db.MarketCouponRule;
import com.hjs.db.MarketGrouponTask;


public interface EifMarketOperation {
	public ActivityCoupon getActivityCoupon(String id);
	public List<MarketGrouponTask> getGrouponTask(String base_prod_id);
	public void deleteUserCoupon(String user_id);
	@Select("select a.name,a.min_amount,a.max_amount,b.raise_interest_rates,b.discount_amount,b.satisfied_amount,b.rule_type from t_market_activity_coupon as a, t_market_rule as b where a.id=#{couponId} and a.rule_id=b.id")
	public MarketCouponRule getMarketCouponRule(String couponId);
}
