package com.hjs.db;

/**
 * @author huangxiao
 * @version 创建时间：2017年9月6日 下午3:49:14 类说明
 */
public class MarketGrouponTask {
	private String id;
	private String actvty_no;
	private String award_rates;
	private String min_user_count;
	
	public String getMin_user_count() {
		return min_user_count;
	}

	public void setMin_user_count(String min_user_count) {
		this.min_user_count = min_user_count;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getActvty_no() {
		return actvty_no;
	}

	public void setActvty_no(String actvty_no) {
		this.actvty_no = actvty_no;
	}

	public String getAward_rates() {
		return award_rates;
	}

	public void setAward_rates(String award_rates) {
		this.award_rates = award_rates;
	}
}
