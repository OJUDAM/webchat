package com.example.webchat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.webchat.repository.ReplyRepository;
import com.example.webchat.vo.BoardVo;
import com.example.webchat.vo.ReplyVo;

@Service
public class ReplyService {

	@Autowired
	private ReplyRepository replyRepository;
	
	public List<ReplyVo> getReplyList(Long startNo, Long boardNo) {
		
		return replyRepository.getList(startNo, boardNo);
	}

	public boolean increaseGroupOrderNo(ReplyVo vo) {
		return replyRepository.updateOrderNo( vo.getGroupNo(), vo.getOrderNo()) == 1;
	}

	public Long addReply(ReplyVo vo) {
		return replyRepository.addReply(vo);	
	}

	public String getName(Long replyNo) {
		return replyRepository.getName(replyNo);
	}

}
