package com.kh.sample01.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.sample01.domain.BoardVo;
import com.kh.sample01.domain.MemberVo;
import com.kh.sample01.domain.PagingDto;
import com.kh.sample01.service.BoardService;
import com.kh.sample01.util.FileUploadUtil;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	@Resource
	private String uploadPath;
	
	@Inject
	private BoardService boardService;
	
	//글등록 폼 -> /board/register
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void registerGet(@ModelAttribute PagingDto pagingDto) throws Exception{
		System.out.println("registerGet() 실행됨...");
	}
	
	//글등록 처리 -> /board/register
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerPOST(HttpSession session, BoardVo boardVo, @ModelAttribute PagingDto pagingDto) throws Exception{
		System.out.println("registerPost() 실행됨...");
		System.out.println("boardVo:" + boardVo);
		MemberVo memberVo = (MemberVo)session.getAttribute("memberVo");
		boardVo.setWriter(memberVo.getUserid());
		boardService.regist(boardVo);
//		return "board/success";
		return "redirect:/board/listAll?perPage=" + pagingDto.getPerPage();
	}
	
	// 게시글 목록
	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public String listAll(Model model, PagingDto pagingDto) throws Exception{
		
		List<BoardVo> list = boardService.listAll(pagingDto);
		int totalCount = boardService.listCount(pagingDto);
		pagingDto.setTotalCount(totalCount);
		System.out.println("pagingDto:" + pagingDto);
		model.addAttribute("list", list);
		model.addAttribute("pagingDto", pagingDto);
		return "/board/listAll";
	}
	
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public String read(@RequestParam("bno") int bno, PagingDto pagingDto, Model model) throws Exception{
		//int bno = Integer.parseInt(request.getParameter("bno");
		BoardVo boardVo = boardService.read(bno);
		model.addAttribute("boardVo", boardVo);
		return "board/read";
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(BoardVo boardVo, @ModelAttribute PagingDto pagingDto) throws Exception{
		boardService.modify(boardVo);
		return "redirect:/board/listAll?page=" + pagingDto.getPage() 
								+ "&perPage=" + pagingDto.getPerPage();
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(@RequestParam("bno") int bno, 
						@ModelAttribute PagingDto pagingDto,
						RedirectAttributes rttr) throws Exception{
		//파일 삭제
		List<String> fileNameList = boardService.getAttach(bno); 
		FileUploadUtil.delete(fileNameList, uploadPath);
		//데이터 삭제
		boardService.delete(bno);
		rttr.addFlashAttribute("msg", "delete_success");
		return "redirect:/board/listAll?page=" + pagingDto.getPage()
									+ "&perPage=" + pagingDto.getPerPage();
	}
	
	@RequestMapping(value="/getAttach/{bno}", method = RequestMethod.GET)
	@ResponseBody
	public List<String> getAttach(@PathVariable("bno") int bno) throws Exception{
		return boardService.getAttach(bno);
	}
}
