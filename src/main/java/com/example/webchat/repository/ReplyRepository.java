package com.example.webchat.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.webchat.vo.ReplyVo;

@Repository
public class ReplyRepository {

	@Autowired
	private SqlSession sqlSession;
	public List<ReplyVo> getList(Long startNo, Long boardNo) {
		Map<String, Long> map = new HashMap<>();
		map.put("startNo", startNo);
		map.put("boardNo", boardNo);
		return sqlSession.selectList("reply.getList",map);
	}
	public int updateOrderNo(Integer groupNo, Integer orderNo) {
		Map<String, Integer> map = new HashMap<>();
		map.put("groupNo", groupNo);
		map.put("orderNo", orderNo);
		return sqlSession.update("reply.updateOrderNo", map);
	}
	public int addReply(ReplyVo vo) {
		return sqlSession.insert("reply.insert",vo);
	}
	public String getName(Long replyNo) {
		return sqlSession.selectOne("reply.getName", replyNo);
	}

}
