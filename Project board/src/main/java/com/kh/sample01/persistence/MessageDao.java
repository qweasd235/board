package com.kh.sample01.persistence;

import com.kh.sample01.domain.MessageVo;

public interface MessageDao {

	//메시지 추가
	public void insert(MessageVo messageVo) throws Exception;
	//메시지 읽기
	public MessageVo select(int message_id) throws Exception;
	//메시지 열람시간 갱신
	public void timeUpdate(int message_id) throws Exception;
	//메시지 삭제
	public void delete(int message_id) throws Exception;
}
