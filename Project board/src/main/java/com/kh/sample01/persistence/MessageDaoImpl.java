package com.kh.sample01.persistence;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kh.sample01.domain.MessageVo;

@Repository
public class MessageDaoImpl implements MessageDao {

	private static final String NAMESPACE = "com.kh.mappers.messageMapper";
	
	@Inject
	private SqlSession sqlSession;
	
	@Override
	public void insert(MessageVo messageVo) throws Exception {
		sqlSession.insert(NAMESPACE + ".insert", messageVo);

	}

	@Override
	public MessageVo select(int message_id) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".select", message_id);
	}

	@Override
	public void timeUpdate(int message_id) throws Exception {
		sqlSession.update(NAMESPACE + ".timeUpdate", message_id);

	}

	@Override
	public void delete(int message_id) throws Exception {
		sqlSession.delete(NAMESPACE + ".delete", message_id);

	}

}
