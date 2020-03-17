package com.kh.sample01.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kh.sample01.domain.MemberVo;

public class AuthInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		MemberVo memberVo = (MemberVo)session.getAttribute("memberVo");
		if(memberVo == null) {
			String uri = request.getRequestURI();
			String query = request.getQueryString(); // ? 뒤로 전달되는 내용
			String targetLocation = uri + "?" + query;
			session.setAttribute("targetLocation", targetLocation);
			response.sendRedirect("/member/loginGet");
			return false;
		}
		return true;
	}
}
