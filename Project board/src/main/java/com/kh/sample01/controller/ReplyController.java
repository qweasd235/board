package com.kh.sample01.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kh.sample01.domain.ReplyVo;
import com.kh.sample01.service.ReplyService;

@RestController
@RequestMapping("/replies")
public class ReplyController {

	@Inject
	private ReplyService replyService;
	
		
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@RequestBody ReplyVo replyVo) throws Exception{
		replyService.create(replyVo);
		return "success";
	}
	
	@RequestMapping(value = "/all/{bno}", method = RequestMethod.GET)
	public List<ReplyVo> list(@PathVariable("bno") int bno) throws Exception{
		return replyService.list(bno);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public String update(@RequestBody ReplyVo replyVo) throws Exception{
		replyService.update(replyVo);
		return "success";
	}
	
	@RequestMapping(value = "/delete/{rno}")
	public String delete(@PathVariable("rno") int rno,
						 @PathVariable("bno") int bno) throws Exception{
		replyService.delete(rno, bno);
		return "success";
	}
}
