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
}
