package com.kh.sample01.service;

import java.util.List;

import com.kh.sample01.domain.BoardVo;
import com.kh.sample01.domain.PagingDto;

public interface BoardService {

	//글쓰기
	public void regist(BoardVo vo) throws Exception;
	//글읽기
	public BoardVo read(Integer bno) throws Exception;
	//글수정
	public void modify(BoardVo vo) throws Exception;
	//글삭제
	public void delete(Integer bno) throws Exception;
	//글목록
	public List<BoardVo> listAll(PagingDto pagingDto) throws Exception; 
	//글갯수
	public int listCount(PagingDto pagingDto) throws Exception;
	//첨부파일명 목록
	public List<String> getAttach(int bno) throws Exception;
	//첨부파일 데이터 삭제
	public void deleteAttach(String full_name) throws Exception;
	
}
