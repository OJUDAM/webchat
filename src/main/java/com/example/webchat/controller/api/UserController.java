package com.example.webchat.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.webchat.dto.JSONResult;
import com.example.webchat.service.UserService;

@Controller("userApiController")
@RequestMapping("/user/api")
public class UserController {
	@Autowired
	private UserService userService;
	
	@ResponseBody
	@RequestMapping("/checkemail")
	public JSONResult checkEmail(@RequestParam(value="email",required=true, defaultValue="")String email) {
		System.out.println(email);
		boolean exist = userService.existEmail(email);
		return JSONResult.success(exist);
	}
}
