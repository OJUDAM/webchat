package com.example.webchat.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.webchat.vo.BoardVo;



@Repository
public class BoardRepository {

	@Autowired
	private SqlSession sqlSession;
	
	public int getTotalCount(String keyword) {
		return sqlSession.selectOne("board.getTotalCount",keyword);
	}

	public List<BoardVo> getList(String keyword, int currentPage, int size) {
		Map<String, Object> map = new HashMap<>();
		map.put("keyword", keyword);
		map.put("startIndex", (currentPage-1)*size);
		map.put("size", size);
		return sqlSession.selectList("board.getList", map);
	}

	public int updateOrderNo(Integer groupNo, Integer orderNo) {
		Map<String, Integer> map = new HashMap<>();
		map.put("groupNo", groupNo);
		map.put("orderNo", orderNo);
		return sqlSession.update("board.updateOrderNo", map);
	}

	public int insert(BoardVo boardVo) {
		return sqlSession.insert("board.insert", boardVo);
	}

}
