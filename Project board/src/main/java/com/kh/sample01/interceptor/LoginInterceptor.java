package com.kh.sample01.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kh.sample01.domain.MemberVo;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("로그인 전처리");
		HttpSession session = request.getSession();
		MemberVo memberVo = (MemberVo)session.getAttribute("memberVo");
		if(memberVo != null) {
			session.removeAttribute("memberVo");
		}
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("로그인 후처리");
		HttpSession session = request.getSession();
		ModelMap modelMap = modelAndView.getModelMap();
		MemberVo memberVo = (MemberVo)modelMap.get("memberVo");
		if(memberVo != null) {
			session.setAttribute("memberVo", memberVo);
		
		}
		
		super.postHandle(request, response, handler, modelAndView);
	}
}
