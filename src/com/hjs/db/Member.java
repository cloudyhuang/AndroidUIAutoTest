package com.hjs.db;

public class Member {
	private String id;
    private String verified_mobile;
    private String member_no;

	public String getMember_no() {
		return member_no;
	}
	public void setMember_no(String member_no) {
		this.member_no = member_no;
	}
	public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getVerified_mobile() {
        return verified_mobile;
    }
    public void setVerifyCode(String verified_mobile) {
        this.verified_mobile = verified_mobile;
    }
	@Override
    public String toString() {
        return "User [id=" + id + ", verified_mobile=" + verified_mobile + "]";
    }
}
