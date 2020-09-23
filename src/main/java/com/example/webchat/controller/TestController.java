package com.example.webchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
	
	@RequestMapping("/index1")
	public String index() {
		return "/WEB-INF/views/main/index.jsp";
	}
}
