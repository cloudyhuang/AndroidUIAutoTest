package com.hjs.mybatis.inter;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hjs.db.BalanceConfig;

public interface EifBalanceOperation {
	@Update("update t_balance_front_config set `config_value`=#{config_value} where config_key=#{config_key}")
	public int updateBalanceConfig(BalanceConfig balanceConfig);
	@Select("select config_value from t_balance_front_config where config_key=#{config_key}")
	public String getBalanceConfigValue(String config_key);
	
}
