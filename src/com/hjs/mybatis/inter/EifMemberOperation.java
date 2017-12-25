package com.hjs.mybatis.inter;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.hjs.db.ClientCertification;
import com.hjs.db.Member;
import com.hjs.db.MemberClientCertification;
import com.hjs.db.MemberFundAccount;

public interface EifMemberOperation {
	public int updateIdno(ClientCertification clientCertification);
	public List<ClientCertification> getClientCertification(String idno);
	public Member getMember(String verifyMobile);
	@Select("SELECT * from t_member as a,t_member_fund_account as b where a.verified_mobile=#{phoneNum} and a.member_no=b.member_no")
	public MemberFundAccount getFundAccount(String phoneNum);
	@Select("SELECT b.name,b.idno from t_member as a,t_client_certification as b where a.verified_mobile=#{phoneNum} and a.member_no=b.member_no")
	public MemberClientCertification getMemberClientCertification(String phoneNum);
}
