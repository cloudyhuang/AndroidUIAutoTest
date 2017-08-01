package com.hjs.mybatis.inter;

import com.hjs.db.ActivityCoupon;


public interface EifMarketOperation {
	public ActivityCoupon getActivityCoupon(String id);
	public void deleteUserCoupon(String user_id);
}
