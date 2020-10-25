package com.example.webchat.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.webchat.dto.JSONResult;
import com.example.webchat.service.ReplyService;
import com.example.webchat.vo.BoardVo;
import com.example.webchat.vo.ReplyVo;

@Controller("BoardControllerApi")
@RequestMapping("/board/api")
public class BoardController {
	
	@Autowired
	private ReplyService replyService;
	
	@ResponseBody
	@RequestMapping("/list")
	public JSONResult list( 
			@RequestParam(value="sno", required=true, defaultValue="0") Long startNo,
			@RequestParam(value="bno", required=true, defaultValue="0") Long boardNo){
		List<BoardVo> list = replyService.getReplyList(startNo, boardNo);
		System.out.println(startNo+" : "+boardNo);
		return JSONResult.success(list);
	}
		
	@ResponseBody
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public JSONResult add(@RequestBody ReplyVo vo) {
		System.out.println(vo.toString());
		
		if(vo.getGroupNo() != null) {
			replyService.increaseGroupOrderNo( vo);
		}
		
		replyService.addReply( vo );
		
		return JSONResult.success(vo);
	}
	
}
