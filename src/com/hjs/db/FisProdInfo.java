package com.hjs.db;

public class FisProdInfo {
	private String id;
	private String base_product_id;
	private String product_name;
	private String inception_date;
	public String getBase_product_id() {
		return base_product_id;
	}

	public void setBase_product_id(String base_product_id) {
		this.base_product_id = base_product_id;
	}

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

	public String getInception_date() {
		return inception_date;
	}

	public void setInception_date(String inception_date) {
		this.inception_date = inception_date;
	}

}
