package com.hjs.mybatis.inter;

import java.util.List;

import org.apache.ibatis.annotations.Update;

import com.hjs.db.BankProvider;
import com.hjs.db.PayCore_provider_payment_limitation;

public interface EifPayCoreOperation {
	public List<BankProvider> getBankProvider(String bank_no);
	public int updateBankProviderStatus(BankProvider bankProvider);
	@Update("update t_paycore_provider_payment_limitation set `status`=#{status} where bank_no=#{bank_no} and provider_no=#{provider_no}")
	public int updateProvider_payment_limitationStatus(PayCore_provider_payment_limitation provider_payment_limitation);
	@Update("update t_paycore_provider_payment_limitation set `status`=#{status} where bank_no=#{bank_no}")
	public int updateProvider_payment_limitationStatusByBankNo(PayCore_provider_payment_limitation provider_payment_limitation);
	
	@Update("update t_paycore_provider_payment_limitation set trans_limit=#{trans_limit} where bank_no=#{bank_no} and provider_no=#{provider_no}")
	public int updateProviderTransLimits(PayCore_provider_payment_limitation provider_payment_limitation);
	@Update("update t_paycore_provider_payment_limitation set trans_min=#{trans_min} where bank_no=#{bank_no} and provider_no=#{provider_no}")
	public int updateProviderTransMin(PayCore_provider_payment_limitation provider_payment_limitation);
	@Update("update t_paycore_provider_payment_limitation set day_limit=#{day_limit} where bank_no=#{bank_no} and provider_no=#{provider_no}")
	public int updateProviderDayLimits(PayCore_provider_payment_limitation provider_payment_limitation);
	@Update("update t_paycore_provider_payment_limitation set month_limit=#{month_limit} where bank_no=#{bank_no} and provider_no=#{provider_no}")
	public int updateProviderMonthLimit(PayCore_provider_payment_limitation provider_payment_limitation);
}
