package com.kh.sample01.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.sample01.common.MyConstants;
import com.kh.sample01.domain.MessageVo;
import com.kh.sample01.persistence.MemberDao;
import com.kh.sample01.persistence.MessageDao;

@Service
public class MessageServiceImpl implements MessageService {

	@Inject
	public MessageDao messageDao;

	@Inject
	public MemberDao memberDao;
	
	@Transactional
	@Override
	public void sendMessage(MessageVo messageVo) throws Exception {
		messageDao.insert(messageVo);
		memberDao.updatePoint(MyConstants.MESSAGE_SEND_POINT, messageVo.getSender());

	}
	
	@Transactional
	@Override
	public MessageVo readMessage(int message_id, String userid) throws Exception {
		//읽으면 포인트 + 5
		memberDao.updatePoint(MyConstants.MESSAGE_READ_POINT, userid);
		//읽으면 읽은 시간 갱신
		messageDao.timeUpdate(message_id);
		return messageDao.select(message_id);
	}

}
