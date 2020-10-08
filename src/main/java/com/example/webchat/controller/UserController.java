package com.example.webchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

	@RequestMapping("/join")
	public String join() {
		return "/WEB-INF/views/user/join.jsp";
	}
}
