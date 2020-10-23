package com.example.webchat.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.webchat.repository.BoardRepository;
import com.example.webchat.vo.BoardVo;

@Service
public class BoardService {
	private static final int LIST_SIZE = 5;
	private static final int PAGE_SIZE = 5;
	
	@Autowired
	private BoardRepository boardRepository;
	
	public Map<String, Object> getMessageList(int currentPage, String keyword){
		// 페이징을 위한 기본 데이터 계산
		int totalCount = boardRepository.getTotalCount(keyword);
		int pageCount = (int)Math.ceil((double)totalCount / LIST_SIZE);
		int blockCount = (int)Math.ceil((double)pageCount / PAGE_SIZE);
		int currentBlock = (int)Math.ceil((double)currentPage / PAGE_SIZE);
		
		/**
		 * 파라미터 page 값 검증(예외처리)
		 */
		 
		//현재 페이지가 총페이지수 넘어갈 경우
		if( currentPage > pageCount) {
			//현재 페이지,블록 마지막으로 설정
			currentPage = pageCount;
			currentBlock = (int)Math.ceil((double)currentPage / PAGE_SIZE);
		}
		
		// 현재 페이지가 1보다 작을경우
		if( currentPage < 1) {
			//현제 페이지, 블록 처음으로 설정
			currentPage = 1;
			currentBlock = 1;
		}
		
		/**
		 * view에서 페이지 리스트를 렌더링 하기위한 데이터 값 계산
		 */
		
		//블록에서 첫 페이지
		int beginPage = (currentBlock == 0)?1:(currentBlock-1)*PAGE_SIZE +1;
		//이전 블록으로가서 마지막 페이지
		int prevPage = (currentBlock > 1)? (currentBlock -1)*PAGE_SIZE : 0;
		//다음 블록으로가서 첫 페이지
		int nextPage = (currentBlock < blockCount )? currentBlock * PAGE_SIZE +1:0;
		//마지막 페이지 숫자 계산
		int endPage = (nextPage > 0)? (beginPage-1)+ LIST_SIZE : pageCount;
	
		//리스트 가져오기
		List<BoardVo> list = boardRepository.getList( keyword, currentPage, LIST_SIZE);
		
		//리스트 정보를 맵에 저장
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("list", list);
		map.put("totalCount",totalCount);
		map.put("listSize", LIST_SIZE);
		map.put("currentPage",currentPage);
		map.put("beginPage", beginPage);
		map.put("prevPage", prevPage);
		map.put("nextPage", nextPage);
		map.put("endPage", endPage);
		map.put("keyword", keyword);
		
		return map;
	}

	public boolean increaseGroupOrderNo(BoardVo boardVo) {
		return boardRepository.updateOrderNo( boardVo.getGroupNo(), boardVo.getOrderNo()) > 0;
	}

	public boolean addMessage(BoardVo boardVo) {
		return boardRepository.insert( boardVo ) == 1;
	}

	public BoardVo getMessage(Long no) {
		BoardVo boardVo = boardRepository.get(no);
		if( boardVo != null) {
			boardRepository.updateHit(no);
		}
		return boardVo;
	}

	public BoardVo getMessage(Long no, Long userNo) {
		BoardVo boardVo = boardRepository.get(no, userNo);
		return boardVo;
	}

	public boolean modifyMessage(BoardVo boardVo) {
		return boardRepository.update(boardVo)==1;
	}

	public boolean deleteMessage(Long no, Long userNo) {
		return boardRepository.delete(no, userNo) ==1;
	}
	
}
