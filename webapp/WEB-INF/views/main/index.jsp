<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/main.css"
	rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
	<script defer>
	'use strict';
	var ws;
	var nickName = decodeURIComponent(escape("${cookie.name.value}"));
	console.log(nickName);
	$("#sender").val(nickName);
	
    function openSocket(){
        if(ws!==undefined && ws.readyState!==WebSocket.CLOSED){
            writeResponse("WebSocket is already opened.");
            return;
        }
        //웹소켓 객체 만드는 코드
        ws=new WebSocket("ws://djam1020.gq/webchat/echo.do");
        
        ws.onopen=function(event){
            if(event.data===undefined) return;
            
            writeResponse(event.data);
        };
        ws.onmessage=function(event){
            writeResponse(event.data);
        };
        ws.onclose=function(event){
            writeResponse("Connection closed");
        }
    }
    
    function send(){
        var text=document.getElementById("messageinput").value+","+document.getElementById("sender").value;
        ws.send(text);
        text="";
    }
    
    function closeSocket(){
        ws.close();
    }
    function writeResponse(text){
        var txt = document.getElementById('messages');
        txt.value+= '\r'+text;
    }
    openSocket();
	</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
		<div id="wrapper">
			<div id="content">
				<div id="site-introduction">
					<div>
						<input  type="text" id="sender" style="display:none">
						<input type="text" id="messageinput">
					</div>
					<div>
						<button type="button" onclick="openSocket();" style="display:none">Open</button>
						<button type="button" onclick="send();">Send</button>
						<button type="button" onclick="closeSocket();">Close</button>
					</div>
					<div>
						<textarea id="messages" style="overflow-y:scroll" rows="20" cols="30"></textarea> 
					</div>
					<h2>안녕하세요. 오주담의 mysite에 오신 것을 환영합니다.</h2>
					<p>
						이 사이트는 웹 프로그램밍 실습과제 예제 사이트입니다.<br> <br> <a
							href="${pageContext.request.contextPath }/guestbook">방명록</a>에 글
						남기기<br>
					</p>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp">
			<c:param name="menu" value="main"/>
		</c:import>
		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>

	</div>
</body>
</html>