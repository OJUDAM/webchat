package com.example.webchat.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.security.Auth;
import com.example.security.AuthUser;
import com.example.web.util.WebUtil;
import com.example.webchat.service.BoardService;
import com.example.webchat.vo.BoardVo;
import com.example.webchat.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@RequestMapping("")
	public String index(
			@RequestParam(value="p",required=true, defaultValue="1") Integer page,
			@RequestParam(value="kwd", required=true, defaultValue="")String keyword,
			Model model) {
		
		Map<String, Object> map = boardService.getMessageList(page, keyword);
		model.addAttribute("map",map);
		
		return "/WEB-INF/views/board/index.jsp";
	}
	
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write() {
		return "/WEB-INF/views/board/write.jsp";
	}
	
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(
			@AuthUser UserVo authUser,
			@ModelAttribute BoardVo boardVo,
			@RequestParam(value="p", required=true, defaultValue="1") Integer page,
			@RequestParam(value="kwd", required=true, defaultValue="") String keyword) {
		
		boardVo.setUserNo(authUser.getNo());
		if( boardVo.getGroupNo() != null) {
			boardService.increaseGroupOrderNo( boardVo );
		}
		boardService.addMessage(boardVo);
		
		return (boardVo.getGroupNo() != null) ? 
				"redirect:/board?p="+page+"&kwd="+WebUtil.encodeURL( keyword, "UTF-8"):
				"redirect:/board";
	}

	@RequestMapping("/view/{no}")
	public String view(@PathVariable("no") Long no, Model model) {
		BoardVo boardVo = boardService.getMessage(no);
		model.addAttribute("boardVo",boardVo);
		return "/WEB-INF/views/board/view.jsp";
	}
	
	@Auth
	@RequestMapping("/reply/{no}")
	public String reply(@PathVariable("no") Long no, Model model) {
		
		BoardVo boardVo = boardService.getMessage(no);
		boardVo.setOrderNo(boardVo.getOrderNo()+1);
		boardVo.setDepth(boardVo.getDepth()+1);
		
		model.addAttribute("boardVo", boardVo);
		
		return "/WEB-INF/views/board/reply.jsp";
	}
	
	@Auth
	@RequestMapping("/modify/{no}")
	public String modify(@AuthUser UserVo authUser,
			@PathVariable("no") Long no, Model model) {
		BoardVo boardVo = boardService.getMessage(no, authUser.getNo());
		model.addAttribute("boardVo",boardVo);
		
		return "/WEB-INF/views/board/modify.jsp";
	}
	
	@Auth
	@RequestMapping(value="/modify" ,method=RequestMethod.POST)
	public String modify(
			@AuthUser UserVo authUser,
			@ModelAttribute BoardVo boardVo,
			@RequestParam( value="p", required=true, defaultValue="1") Integer page,
			@RequestParam( value="kwd", required=true, defaultValue="") String keyword) {
			
		boardVo.setUserNo(authUser.getNo());
		boardService.modifyMessage(boardVo);
		return "redirect:/board/view/"+boardVo.getNo()+
				"?p="+page+"&kwd="+WebUtil.encodeURL(keyword, "UTF-8");
	}
	
	@RequestMapping("/delete/{no}")
	public String delete(
			@AuthUser UserVo authUser,
			@RequestParam(value="p", required=true, defaultValue="1") Integer page,
			@RequestParam(value="kwd", required=true, defaultValue="") String keyword,
			@PathVariable("no") Long no) {
		boardService.deleteMessage(no, authUser.getNo());
		
		return "redirect:/board?p="+page+
				"&kwd="+WebUtil.encodeURL(keyword, "UTF-8");
	}
	
	@RequestMapping("/ajax")
	public String indexAjax(
			@RequestParam(value="p",required=true, defaultValue="1") Integer page,
			@RequestParam(value="kwd", required=true, defaultValue="")String keyword,
			Model model) {
		
		Map<String, Object> map = boardService.getMessageList(page, keyword);
		model.addAttribute("map",map);
		
		return "/WEB-INF/views/board/index-ajax.jsp";
	}
	@RequestMapping("/view/ajax/{no}")
	public String viewAjax(@PathVariable("no") Long no, Model model) {
		BoardVo boardVo = boardService.getMessage(no);
		model.addAttribute("boardVo",boardVo);
		return "/WEB-INF/views/board/view-ajax.jsp";
	}
	
	
}
