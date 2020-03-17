package com.kh.sample01;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kh.sample01.domain.BoardVo;
import com.kh.sample01.domain.PagingDto;
import com.kh.sample01.persistence.BoardDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations= {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class BoardDaoTest {
	
	@Inject
	private BoardDao dao;
	
	@Test
	public void testcreate() throws Exception{
		BoardVo vo = new BoardVo();
		vo.setTitle("게시글3");
		vo.setContent("내용3");
		vo.setWriter("걸음마코드3");
		dao.create(vo);
	}
	
	@Test
	public void testRead() throws Exception{
		Integer bno = 1;
		dao.read(bno);
	}
	
	@Test
	public void testUpdate() throws Exception{
		BoardVo vo = new BoardVo();
		vo.setBno(1);
		vo.setTitle("수정게시글1");
		vo.setContent("수정내용1");
		vo.setWriter("수정코드");
		dao.update(vo);
	}
	
	@Test
	public void testDelete() throws Exception{
		Integer bno = 1;
		dao.delete(bno);
	}
	
	@Test
	public void testListAll() throws Exception{
		PagingDto pagingDto = new PagingDto();
		pagingDto.setPage(1);
		pagingDto.setPerPage(20);
		pagingDto.setSearchType("content");
		pagingDto.setKeyword("15");
		dao.listAll(pagingDto);
	}
	
	@Test
	public void testListCount() throws Exception{
		PagingDto dto = new PagingDto();
		dto.setSearchType("title");
		dto.setKeyword("15");
		dao.listCount(dto);
	}
	
}
