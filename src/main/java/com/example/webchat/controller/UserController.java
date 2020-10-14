package com.example.webchat.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.security.Auth;
import com.example.security.AuthUser;
import com.example.webchat.service.UserService;
import com.example.webchat.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	/*
	@RequestMapping( value="/join", method=RequestMethod.GET)
	public String join(Model model) {
		model.addAttribute("userVo", new UserVo());
		return "/WEB-INF/views/user/join.jsp";
	}
	*/
	@RequestMapping( value="/join", method=RequestMethod.GET)
	public String join(@ModelAttribute UserVo userVo) {
		
		return "/WEB-INF/views/user/join.jsp";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(
			@ModelAttribute @Valid UserVo userVo,
			BindingResult result) {
		if(result.hasErrors()) {
			System.out.println("error");
			return "/WEB-INF/views/user/join.jsp";
			
		}
		userService.join(userVo);
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login() {
		return "/WEB-INF/views/user/login.jsp";
	}
	
	@RequestMapping("/joinsuccess")
	public String joinsuccess() {
		return "/WEB-INF/views/user/joinsuccess.jsp";
	}
	
	@Auth(Auth.Role.USER)
	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public String modify(@ModelAttribute @AuthUser UserVo authUser) {
		UserVo userVo = userService.getUser( authUser.getNo() );
		
		return "/WEB-INF/views/user/modify.jsp";
	}
}
