package com.example.webchat.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.webchat.vo.GuestBookVo;

@Repository
public class GuestBookRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public List<GuestBookVo> getList() {
		List<GuestBookVo> list = sqlSession.selectList("guestbook.getList");
		return list;
	}

	public int insert(GuestBookVo vo) {
		return sqlSession.insert("guestbook.insert",vo);
	}

	public int delete(Long no) {
		int count = sqlSession.delete("guestbook.delete",no);
		return count;
	}

	public List<GuestBookVo> getList(Long startNo) {
		return sqlSession.selectList("guestbook.getList2",startNo);
	}

	public int delete(GuestBookVo vo) {
		return sqlSession.delete("guestbook.delete2",vo);
	}

}
