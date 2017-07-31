package com.hjs.mybatis.inter;

import java.util.List;

import com.hjs.db.BankProvider;
import com.hjs.db.ClientCertification;

public interface EifPayCoreOperation {
	public List<BankProvider> getBankProvider(String bank_no);
	public int updateBankProviderStatus(BankProvider bankProvider);
}
