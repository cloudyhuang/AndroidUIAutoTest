package com.hjs.mybatis.inter;

import org.apache.ibatis.annotations.Select;

import com.hjs.db.OfcBusinessOrderItem;

public interface EifOfcOperation {
	@Select("SELECT * from t_ofc_business_order_item where member_no=#{member_no} ORDER BY create_time DESC limit 1")
	public OfcBusinessOrderItem getOfcBusinessOrderItem(String member_no);
}
