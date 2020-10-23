package com.example.webchat.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.webchat.dto.JSONResult;
import com.example.webchat.service.ReplyService;
import com.example.webchat.vo.ReplyVo;

@Controller("BoardControllerApi")
@RequestMapping("/board/api")
public class BoardController {
	
	@Autowired
	private ReplyService replyService;
	/*
	@ResponseBody
	@RequestMapping("/list")
	public JSONResult list( 
			@RequestParam(value="sno", required=true, defaultValue="0") Long startNo,
			@RequestParam(value="gno", required=true, defaultValue="0") Integer groupNo,
			Model model){
				
			List<BoardVo> list = boardService.getReplyList(startNo, groupNo);
			return JSONResult.success(list);
	}
	*/	
	@ResponseBody
	@RequestMapping("/add/{no}")
	public JSONResult add(@RequestBody ReplyVo vo) {
		
	/*	replyService.wirteReply( vo );*/
		return JSONResult.success(vo);
	}
	
}
