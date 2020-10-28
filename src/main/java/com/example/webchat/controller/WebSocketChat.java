package com.example.webchat.controller;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.webchat.service.WebchatService;

@Controller
@ServerEndpoint(value="/echo.do")
public class WebSocketChat {
	@Autowired
	private WebchatService webchatService;
	
	private static final List<Session> sessionList=new ArrayList<>();
	
	public WebSocketChat() {
		System.out.println("웹소켓(서버) 객체 생성");
		System.out.println(webchatService.getNickName());
	}
	
	@OnOpen
	public void onOpen(Session session) {
		
		System.out.println("Open session id: "+session.getId());
		
		try {
			final Basic basic=session.getBasicRemote();
			basic.sendText("Connection Extablished");
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		sessionList.add(session);
	}
	
	/*
	  Send All
	 */
	private void sendAllSessionToMessage(Session self, String message) {
		try {
			for(Session session : WebSocketChat.sessionList) {
				if(!self.getId().equals(session.getId())) {
					session.getBasicRemote().sendText(message.split(",")[1]+" : "+message.split(",")[0]);
				}
			}
			}catch(Exception e) {
				System.out.println(e.getMessage());
		}
	}
	
	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("Message From "+message.split(",")[1]+" : "+message.split(",")[0]);
		try {
			final Basic basic = session.getBasicRemote();
			basic.sendText(message.split(",")[1]+" : "+message.split(",")[0]);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		sendAllSessionToMessage(session, message);
	}
	
	@OnError
	public void onError(Throwable e, Session session) {
		System.out.println("OnError");
	}
	
	@OnClose
	public void onClose(Session session) {
		System.out.println("Session "+session.getId()+" has ended");
		sessionList.remove(session);
	}
}
