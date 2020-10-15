package com.example.webchat.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.webchat.vo.UserVo;

@Repository
public class UserRepository {

	@Autowired
	private SqlSession sqlSession;

	public UserVo get(String email) {
		return sqlSession.selectOne("user.getByEmail",email);
	}
	
	public UserVo get(String email, String password) {
		Map<String, String> map = new HashMap<>();
		map.put("email", email);
		map.put("password", password);
		return sqlSession.selectOne("user.getByEmailAndPassword",map);
	}

	public void insert(UserVo userVo) {
		sqlSession.insert("user.insert",userVo);
	}

	public UserVo get(Long no) {
		return sqlSession.selectOne("user.getByNo",no);
	}

	public int update(UserVo userVo) {
		return sqlSession.update("user.update",userVo);
	}
}
