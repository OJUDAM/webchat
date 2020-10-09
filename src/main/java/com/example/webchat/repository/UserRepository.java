package com.example.webchat.repository;

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

	public void insert(UserVo userVo) {
		sqlSession.insert("user.insert",userVo);
	}
	
}
