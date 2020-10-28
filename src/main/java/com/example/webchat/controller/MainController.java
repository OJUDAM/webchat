package com.example.webchat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.webchat.service.WebchatService;

@Controller
public class MainController {
	@Autowired
	private WebchatService webchatService;

	
	@RequestMapping({"/","/main"})
	public String index(Model model) {
		String name = webchatService.getNickName();
		model.addAttribute("nickName",name);
		
		return "/WEB-INF/views/main/index.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		
		String name = webchatService.getNickName();
		return "<h1>"+name+"</h1>";
	}
}
