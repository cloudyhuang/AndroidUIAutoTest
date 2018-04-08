package com.hjs.db;
/**
* @author huangxiao
* @version 创建时间：2018年3月27日 下午3:57:26
* 类说明
*/
public class MarketCouponRule {
	private String name;
	private String min_amount;
	private String max_amount;
	private String raise_interest_rates;
	private String discount_amount;
	private String satisfied_amount;
	private String rule_type;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMin_amount() {
		return min_amount;
	}
	public void setMin_amount(String min_amount) {
		this.min_amount = min_amount;
	}
	public String getMax_amount() {
		return max_amount;
	}
	public void setMax_amount(String max_amount) {
		this.max_amount = max_amount;
	}
	public String getRaise_interest_rates() {
		return raise_interest_rates;
	}
	public void setRaise_interest_rates(String raise_interest_rates) {
		this.raise_interest_rates = raise_interest_rates;
	}
	public String getDiscount_amount() {
		return discount_amount;
	}
	public void setDiscount_amount(String discount_amount) {
		this.discount_amount = discount_amount;
	}
	public String getSatisfied_amount() {
		return satisfied_amount;
	}
	public void setSatisfied_amount(String satisfied_amount) {
		this.satisfied_amount = satisfied_amount;
	}
	public String getRule_type() {
		return rule_type;
	}
	public void setRule_type(String rule_type) {
		this.rule_type = rule_type;
	}
}
