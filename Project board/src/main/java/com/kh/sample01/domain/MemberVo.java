package com.kh.sample01.domain;

import java.sql.Timestamp;

public class MemberVo {
	
	private String userid;
	private String userpw;
	private String username;
	private String email;
	private Timestamp regdate;
	private Timestamp updatedate;
	private int member_point;
	
	public MemberVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MemberVo(String userid, String userpw, String username, String email, Timestamp regdate,
			Timestamp updatedate) {
		super();
		this.userid = userid;
		this.userpw = userpw;
		this.username = username;
		this.email = email;
		this.regdate = regdate;
		this.updatedate = updatedate;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUserpw() {
		return userpw;
	}
	public void setUserpw(String userpw) {
		this.userpw = userpw;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Timestamp getRegdate() {
		return regdate;
	}
	public void setRegdate(Timestamp regdate) {
		this.regdate = regdate;
	}
	public Timestamp getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(Timestamp updatedate) {
		this.updatedate = updatedate;
	}
	public int getMember_point() {
		return member_point;
	}
	public void setMember_point(int member_point) {
		this.member_point = member_point;
	}
	@Override
	public String toString() {
		return "MemberVo [userid=" + userid + ", userpw=" + userpw + ", username=" + username + ", email=" + email
				+ ", regdate=" + regdate + ", updatedate=" + updatedate + ", member_point=" + member_point + "]";
	}
	
	
	
}
