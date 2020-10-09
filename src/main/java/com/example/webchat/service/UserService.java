package com.example.webchat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.webchat.repository.UserRepository;
import com.example.webchat.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public boolean existEmail(String email) {
		UserVo userVo = userRepository.get(email);
		return userVo != null;
	}

	public void join(UserVo userVo) {
		userRepository.insert(userVo);
	}

	public UserVo getUser(String email, String password) {
		return userRepository.get(email, password);
	}

}
