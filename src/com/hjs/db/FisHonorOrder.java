package com.hjs.db;
/**
* @author huangxiao
* @version 创建时间：2017年9月20日 上午11:20:28
* 类说明
*/
public class FisHonorOrder {
	private String id;
	private String product_name;
	private String prod_inception_date;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProd_inception_date() {
		return prod_inception_date;
	}
	public void setProd_inception_date(String prod_inception_date) {
		this.prod_inception_date = prod_inception_date;
	}
}
