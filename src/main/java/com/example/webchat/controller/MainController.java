package com.example.webchat.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.webchat.service.WebchatService;

@Controller
public class MainController {
	@Autowired
	private WebchatService webchatService;

	@RequestMapping(value={"/","/main"}, method=RequestMethod.GET)
	public String index(HttpServletResponse response,HttpServletRequest request,Model model) {
		Cookie[] cookies = request.getCookies();
		if(cookies == null) {
			String name = webchatService.getNickName();
			Cookie cookie = new Cookie("name",name);
			response.addCookie(cookie);
		}
		String name = cookies[0].getValue();
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
