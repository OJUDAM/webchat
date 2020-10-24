package com.example.webchat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.webchat.repository.ReplyRepository;
import com.example.webchat.vo.BoardVo;

@Service
public class ReplyService {

	@Autowired
	private ReplyRepository replyRepository;
	
	public List<BoardVo> getReplyList(Long startNo, Long boardNo) {
		
		return replyRepository.getList(startNo, boardNo);
	}

}
