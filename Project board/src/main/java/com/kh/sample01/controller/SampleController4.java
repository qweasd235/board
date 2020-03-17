package com.kh.sample01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SampleController4 {
	@RequestMapping("/doG")
	public String doG(RedirectAttributes rttr) {
		System.out.println("doG 실행됨");
		rttr.addFlashAttribute("msg", "success");
		return "redirect:/doH";
	}
	
	@RequestMapping("/doH")
	public String doH(@ModelAttribute("msg") String msg) {
		System.out.println("doH 실행됨");
		return "doH";
	}
}
