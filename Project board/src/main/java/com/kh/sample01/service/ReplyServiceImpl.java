package com.kh.sample01.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.kh.sample01.domain.ReplyVo;
import com.kh.sample01.persistence.BoardDao;
import com.kh.sample01.persistence.ReplyDao;
@Service
public class ReplyServiceImpl implements ReplyService {

	@Inject
	public ReplyDao replyDao;
	
	@Inject
	public BoardDao boardDao;
	
	@Override
	public void create(ReplyVo replyVo) throws Exception {
		replyDao.create(replyVo);
		boardDao.updateReplyCnt(1, replyVo.getBno());

	}

	@Override
	public List<ReplyVo> list(int bno) throws Exception {
		return replyDao.list(bno);
	}

	@Override
	public void update(ReplyVo replyVo) throws Exception {
		replyDao.update(replyVo);

	}

	@Override
	public void delete(int rno, int bno) throws Exception {
		replyDao.delete(rno);
		boardDao.updateReplyCnt(-1, bno);
		
	}

}
