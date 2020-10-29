package com.example.security;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.example.webchat.service.WebchatService;

public class CookieInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private WebchatService webchatService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Cookie[] cookies = request.getCookies();
		boolean check =false;
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals("name")) {
					check = true;
				}
			}
		}
		
		//쿠키가 없는경우
		if(!check) {
			String nickName="";
			try {
				nickName = URLEncoder.encode(webchatService.getNickName(),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Cookie cookie = new Cookie("name",nickName);
			response.addCookie(cookie);
		}
		
		
		
		return true;
	}

}
