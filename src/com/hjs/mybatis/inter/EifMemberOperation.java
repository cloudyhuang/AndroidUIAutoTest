package com.hjs.mybatis.inter;

import java.util.List;

import com.hjs.db.ClientCertification;
import com.hjs.db.Member;

public interface EifMemberOperation {
	public int updateIdno(ClientCertification clientCertification);
	public List<ClientCertification> getClientCertification(String idno);
	public Member getMember(String verifyMobile);
}
