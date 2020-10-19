package com.example.webchat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.webchat.repository.GuestBookRepository;
import com.example.webchat.vo.GuestBookVo;

@Service
public class GuestBookService {
	@Autowired
	private GuestBookRepository guestbookRepository;
	
	public List<GuestBookVo> getMessageList() {
		return guestbookRepository.getList();
	}
	public boolean writeMessage(GuestBookVo vo) {
		//성공시 1 반환
		return 1==guestbookRepository.insert(vo);
	}
	public boolean deleteMessage(Long no) {
		//성공시 행의 갯수(1) 없으면 0
		int count = guestbookRepository.delete(no);
		return count==1;
	}
	public List<GuestBookVo> getMessageList(Long startNo){
		
		return guestbookRepository.getList(startNo);
	}
	
}
