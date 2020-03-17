package com.kh.sample01;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kh.sample01.domain.MessageVo;
import com.kh.sample01.persistence.MessageDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations= {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class MessageDaoTest {

	@Inject
	private MessageDao messageDao;
	
	@Test
	public void testInsert() throws Exception{
		MessageVo messageVo = new MessageVo();
		messageVo.setSender("user03");
		messageVo.setTarget_id("user01");
		messageVo.setMessage_text("쪽지테스트4");
		messageDao.insert(messageVo);
	}
	
	@Test
	public void testSelect() throws Exception{
		messageDao.select(2);
	}
	
	@Test
	public void testTimeUpdate() throws Exception{
		messageDao.timeUpdate(4);
	}
	
	@Test
	public void testDelete() throws Exception{
		messageDao.delete(2);
	}
	
}
