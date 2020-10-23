package com.example.webchat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.webchat.service.GuestBookService;
import com.example.webchat.vo.GuestBookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestBookController {
	@Autowired
	private GuestBookService guestbookService;
	
	@RequestMapping("")
	public String index(Model model) {
		List<GuestBookVo> list = guestbookService.getMessageList();
		model.addAttribute("list",list);
		
		return "/WEB-INF/views/index.jsp";
	}
	
	@RequestMapping(value ="/add", method=RequestMethod.POST)
	public String add(GuestBookVo vo) {
		guestbookService.writeMessage(vo);
		return "redirect:/guestbook";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String delete( Long no) {
		guestbookService.deleteMessage(no);
		return "redirect:/guestbook";
	}
	@RequestMapping("/ajax")
	public String indexAjax() {
		return "/WEB-INF/views/guestbook/index-ajax.jsp";
	}
}
