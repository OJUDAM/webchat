package com.example.webchat.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.webchat.dto.JSONResult;
import com.example.webchat.service.BoardService;
import com.example.webchat.vo.BoardVo;

@Controller("BoardControllerApi")
@RequestMapping("/board/api")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@ResponseBody
	@RequestMapping("/list")
	public JSONResult list( 
			@RequestParam(value="sno", required=true, defaultValue="0") Long startNo,
			@PathVariable(" no ") Long no, Model model){
				
			List<BoardVo> list = boardService.getReplyList(startNo, no);
			return JSONResult.success(list);
	}
		

}
