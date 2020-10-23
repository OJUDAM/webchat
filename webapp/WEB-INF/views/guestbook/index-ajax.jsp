<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>방명록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-ajax.css" type="text/css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<style type="text/css">
.ui-dialog .ui-dialog-buttonpan .ui-dialog-buttonset{
	float: none;
	text-align: center
}

.ui-dialog .ui-dialog-buttonpan button{
	margin-left: 10px;
	margin-right: auto;
}

#dialog-message p{
	padding: 20px 0;
	font-weight: bold;
	font-size: 1.0em;
}

#dialog-delete-form p{
	padding: 10px;
	font-weight:bold;
	font-size:1.0em;
}

#dialog-delete-form p.error{
	color: #f00;
}

#dialog-delete-form input[type="password"]{
	padding: 5px;
	border:1px solid #888;
	outline: none;
	width: 180px;	
}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	var isEnd = false;
	var messageBox = function(title, message, callback){
		$("#dialog-message").attr("title",title);
		$("#dialog-message p").text( message );
		$( "#dialog-message" ).dialog({
			modal: true,
			button: {
				"확인": function(){
					$( this ).dialog( "close" );
				}
			},
			close: callback || function(){}
		});
	}
	
	var render = function( vo, mode ){
		var html=
			"<li data-no'"+vo.no+"'>"+
			" <strong>" + vo.name+"</strong>" +
			" <p>" + vo.message.replace( /\n/gi, "<br>") + "</p>"+
			" <a href='' data-no='" +vo.no+"'>삭제</a>"+
			"</li>";
			
			if( mode === true){
				$("#list-guestbook").pretend(html);
			}else{
				$("#list-guestbook").append(html);
			}
	}
	
	var fetchList = function(){
		if(isEnd === true){
			return;
		}
		var startNo = $("#list-guestbook li").last().data("no") || 0;
		
		$.ajax({
			url: "${pageContext.request.contextPath}/guestbook/api/list?sno="+startNo,
			type: "get",
			dataType: "json",
			data: "",
			
			success: function(response){
				if( response.result === "fail"){
					console.warn(response.message);
					return ;
				}
				
				if(response.data.length < 5){
					isEnd = true;
					$("#btn-next").prop("disabled", true);
				}
				
				$.each(response.data, function(index, vo){
					render(vo, false);
				});
			},
			error: function(jqXHR, status, e){
				console.error(status + " : "+ e);
			}
		});
	}
	$(function(){
		var dialogDelete = $("#dialog-delete-form").dialog({
			autoOpen: false,
			height: 180,
			width: 300,
			modal: true,
			buttons: {
				"삭제": function(){
					var no = $("#delete-no").val();
					var password= $("#delete-password").val();
					
					$.ajax({
						url: "${pageContext.request.contextPath}/guestbook/api/delete",
						type: "post",
						data: "no="+no + "&"+
							  "password=" + password,
						success: function(response){
							if( response.result === "fail"){
								console.error( response.message);
								return ;
							}
							
							if( response.data === -1){
								$( "#dialog-delete-form .validateTips").hide();
								$( "#dialog-delete-form .validateTips.error").show();
								$( "#delete-password").val("");
								return;
							}
							$("#list-guestbook li[data-no='"+response.data+"']").remove();
							dialogDelete.dialog("close");
						},
						error: function(jqXHR, status, e){
							console.error(status+ " : "+e);
						}
					});
				},
				"취소":function(){
					dialogDelete.dialog("close");
				}
			},
			close: function(){
				$("#dialog-delete-form .validateTips").show();
				$("#dialog-delete-form .validateTips.error").hide();
				$("#delete-no").val("");
				$("#delete-password").val("");
			}
		});
		
		//live event ==> deleteform open
		$( document ).on( "click", "#list-guestbook li a", function( event){
			event.preventDefaule();
			window.no = $(this).data("no");
			$("#delete-no").val(no);
			
			dialogDelete.dialog("open");
		});
		
		$("#add-form").submit(function(event){
			// submit event 기본 동작을 막음
			// posting을 막음
			event.preventDefault();
			
			var guestbookVo = {};
			var vo = {};
			
			//validate form data
			vo.name=$("#input-name").val();
			if( vo.name === ""){
				messageBox("방명록에 글 남기기", "이름은 필수 입력 항목 입니다.",function(){
					$("input-name").focus();
				});
				return ;
			}
			vo.password = $("#input-password").val();
			if( vo.password === ""){
				messageBox("방명록에 글 남기기", "비밀번호는 필수 입력 항목 입니다.",function(){
					$("input-password").focus();
				});
				return ;
			}
			vo.message = $("#ta-message").val();
			if( vo.message === ""){
				messageBox("방명록에 글 남기기", "내용은 필수 입력 항목 입니다.",function(){
					$("ta-message").focus();
				});
				return ;
			}
			
			$.ajax({
				url: "${pageContext.request.contextPath}/guestbook/api/add",
				type: "post",
				dataType: "json",
				data: JSON.stringify(vo),
				contentType: 'application/json', //JSON Type으로 데이터를 보낼 떄
				success: function(response){
					if( response.result === "fail"){
						console.error( response.message);
						return;
					}
					
					render( response.data, true);
					
					$("#add-form")[0].reset();
				},
				error: function( jqXHR, status, e){
					console.error( status + " : "+  jqXHR.responseText);
				}
			});
		});
	})
	fetchList();
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp"/>
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름">
					<input type="password" id="input-password" placeholder="비밀번호">
					<textarea id="ta-message" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기">
				</form>
				<ul id="list-guestbook"></ul>
				<div style="margin: 20px 0; text-align: center">
					<button id="btn-next" style="padding: 10px 20px">다음</button>
				</div>
			</div>
			<div id="dialog-message" title="" style="display: none">
				<p></p>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display: none">
				<p class="validateTips nomal">
					작성시 입력했던 비밀번호를 입력하세요.
				</p>
				<p class="validateTips error" style="display:none">
					비밀번호가 틀립니다.
				</p>
				<form>
					<input type="hidden" name="no" id="delete-no" value="">
					<input type="password" name="password" id="delete-password" value="">
					<input type="submit" tabindex="-1" style="position:absolute; top: -1000px">
				</form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax"/>
		</c:import>
		<c:import url="/WEB-INF/views/include/footer.jsp"/>
	</div>
</body>
</html>