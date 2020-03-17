package com.kh.sample01.service;

import com.kh.sample01.domain.LoginDto;
import com.kh.sample01.domain.MemberVo;

public interface MemberService {

	public MemberVo login(LoginDto loginDto) throws Exception;
}
