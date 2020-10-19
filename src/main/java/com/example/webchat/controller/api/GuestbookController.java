package com.example.webchat.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.webchat.dto.JSONResult;
import com.example.webchat.service.GuestBookService;
import com.example.webchat.vo.GuestBookVo;

@Controller("guestbookAPIController")
@RequestMapping("/guestbook/api")
public class GuestbookController {
	
	@Autowired
	private GuestBookService guestbookService;
	
	@ResponseBody
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public JSONResult delete(@ModelAttribute GuestBookVo vo) {
		//ajax 삭제 작업
		return JSONResult.success(vo);
	}
	
	@ResponseBody
	@RequestMapping( "/list" )
	public JSONResult list(
			@RequestParam( value="sno", required=true, defaultValue="0") Long startNo) {
		List<GuestBookVo> list = guestbookService.getMessageList(startNo);
		return JSONResult.success( list );
		
	}
}
