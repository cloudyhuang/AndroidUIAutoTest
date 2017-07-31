package com.hjs.db;

public class ClientCertification {
	private String id;
    private String member_id;
    private String name;
    private String idno;
    private Member member;
    private String fakeIdno;
	public String getFakeIdno() {
		return fakeIdno;
	}
	public void setFakeIdno(String fakeIdno) {
		this.fakeIdno = fakeIdno;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdno() {
		return idno;
	}
	public void setIdno(String idno) {
		this.idno = idno;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
    
}
