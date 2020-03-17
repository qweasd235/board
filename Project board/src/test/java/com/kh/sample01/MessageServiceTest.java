package com.kh.sample01;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kh.sample01.domain.MessageVo;
import com.kh.sample01.service.MessageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations= {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class MessageServiceTest {

	@Inject
	private MessageService messageService;
	
	@Test
	public void testSendMessage() throws Exception{
		MessageVo messageVo = new MessageVo();
		messageVo.setSender("user03");
		messageVo.setTarget_id("user01");
		messageVo.setMessage_text("포인트테스트1");
		messageService.sendMessage(messageVo);
	}
	
	@Test
	public void testReadMessage() throws Exception{
		messageService.readMessage(21, "user01");
	}
}
