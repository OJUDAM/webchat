package com.example.webchat.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.webchat.vo.BoardVo;

@Repository
public class ReplyRepository {

	@Autowired
	private SqlSession sqlSession;
	public List<BoardVo> getList(Long startNo, Long boardNo) {
		Map<String, Long> map = new HashMap<>();
		map.put("startNo", startNo);
		map.put("boardNo", boardNo);
		return sqlSession.selectList("reply.getList",map);
	}

}
