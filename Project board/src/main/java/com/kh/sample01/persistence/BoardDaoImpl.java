package com.kh.sample01.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kh.sample01.domain.BoardVo;
import com.kh.sample01.domain.PagingDto;

@Repository
public class BoardDaoImpl implements BoardDao {

	private static final String NAMESPACE = "com.kh.mappers.boardMapper";
	
	@Inject
	private SqlSession sqlSession;
	
	@Override
	public void create(BoardVo vo) throws Exception {
		sqlSession.insert(NAMESPACE + ".create", vo);

	}

	@Override
	public BoardVo read(Integer bno) throws Exception {
		BoardVo vo = sqlSession.selectOne(NAMESPACE + ".read", bno);
		return vo;
	}

	@Override
	public void update(BoardVo vo) throws Exception {
		sqlSession.update(NAMESPACE + ".update", vo);

	}

	@Override
	public void delete(Integer bno) throws Exception {
		sqlSession.delete(NAMESPACE + ".delete", bno);

	}

	@Override
	public List<BoardVo> listAll(PagingDto pagingDto) throws Exception {
		List<BoardVo> list = sqlSession.selectList(NAMESPACE + ".listAll" , pagingDto);
		return list;
	}

	@Override
	public int listCount(PagingDto pagingDto) throws Exception {
		int count = sqlSession.selectOne(NAMESPACE + ".listCount", pagingDto);
		return count;
	}

	@Override
	public void updateReplyCnt(int count, int bno) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("count", count);
		paramMap.put("bno", bno);
		sqlSession.update(NAMESPACE + ".replyCnt", paramMap);
		
	}

	@Override
	public void updateViewCnt(int bno) throws Exception {
		sqlSession.update(NAMESPACE + ".viewCnt", bno);
		
	}

	@Override
	public void attach(String full_name, int bno) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("full_name", full_name);
		paramMap.put("bno", bno);
		sqlSession.insert(NAMESPACE + ".attach", paramMap);
		
	}

	@Override
	public int getNextVal() throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".getNextVal");
		
	}

	@Override
	public List<String> getAttach(int bno) throws Exception {
		return sqlSession.selectList(NAMESPACE + ".getAttach", bno);
	}

	@Override
	public void deleteAttach(String full_name) throws Exception {
		sqlSession.delete(NAMESPACE + ".deleteAttach", full_name);
		
	}

	@Override
	public void deleteAttachByBno(int bno) throws Exception {
		sqlSession.delete(NAMESPACE + ".deleteAttachByBno", bno);
		
	}

}
