package com.example.webchat.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.webchat.repository.WebChatRepository;

@Service
public class WebchatService {
	@Autowired
	private WebChatRepository webchatRepository;
	
	public String getNickName() {
		System.out.println("service");
		String name = "";
		Map<String, String> map = webchatRepository.getNickName();
		name = map.get("firstName") + "의"+map.get("secondName");
		return name;
	}
}
