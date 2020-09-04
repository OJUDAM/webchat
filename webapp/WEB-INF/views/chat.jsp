<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Chatting</title>
</head>
<body>
	<div>
		<input type="text" id="sender" value="${sessionScope.id }">
		<input type="text" id="messageinput">
	</div>
	<div>
		<button type="button" onclick="openSocket();">Open</button>
		<button type="button" onclick="send();">Send</button>
		<button type="button" onclick="closeSocket();">Close</button>
	</div>
	<div id="messages"></div>
	
	<script>
	var ws;
    var messages=document.getElementById("messages");
   
    function openSocket(){
        if(ws!==undefined && ws.readyState!==WebSocket.CLOSED){
            writeResponse("WebSocket is already opened.");
            return;
        }
        //������ ��ü ����� �ڵ�
        ws=new WebSocket("ws://localhost:8080/webchat/echo.do");
        
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
        messages.innerHTML+="<br/>"+text;
    }
	</script>
</body>
</html>