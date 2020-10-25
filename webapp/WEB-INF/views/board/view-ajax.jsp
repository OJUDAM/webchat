<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="${pageContext.request.contextPath }/assets/css/board.css"
	rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/assets/css/guestbook-ajax.css"
	rel="stylesheet" type="text/css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">
	var isEnd = false;
	
	var mesasgeBox = function(title, message, callback){
		$( "#dialog-message").attr("title",title);
		$("#dialog-message p").text(message);
		$("#dialog-message").dialog({
			modal: true,
			buttons: {
				"확인": function(){
					$( this ).dialog("close");
				}
			},
			close: callback || function(){}
		});
	}
	
	var render = function(vo, mode) {
		var html = 
			"<li data-no='" + vo.no + "'>" +
			"	<strong>" + vo.name+ "</strong>" + 
			"	<p>" + vo.message.replace(/\n/gi, "<br>")+"</P>" + 
			"   <a href='' data-no='" + vo.no + "'>삭제</a>"+ 
			"	<button id='add-reply'data-no='"+vo.no+"'>댓글 달기</button>" +
			"	<div id='guestbook'>" +
			"   <form id='reply-add-form' action='' method='post' style='display: none'>" +
			"	<input type='text' id='reply-input-name' placeholder='이름'>" +
			"	<input type='password' id='reply-input-password' placeholder='비밀번호'>" +
			"	<textarea id='reply-ta-message' placeholder='내용을 입력해주세요'></textarea>" +
			"	<input type='submit' value='답글 달기'>" +	
			"   <input type='button' id='reply-cancle' data-no='"+ vo.no+"'>취소</button>"+
			"	</form>" +
			"</li>";
		
		if (mode === true) {
			$("#list-guestbook").prepend(html);
		} else {
			$("#list-guestbook").append(html);
		}
	}
	
	var fetchList = function(){
		if( isEnd === true){
			return ;
		}
		
		var startNo = $("#list-guestbook li").last().data( "no" ) || 0;
		$.ajax({
			url: "${pageContext.request.contextPath}/board/api/list?sno="+startNo+"&bno=${boardVo.no}",
			type: "get",
			dataType: "json",
			data: "",
			success: function( response ){
				if( response.result === "fail"){
					console.warn( response.message );
					return ;
				}
				
				if( response.data.length < 5){
					isEnd = true;
					$("#btn-next").prop("disabled",true);
				}
				
				$.each( response.data, function( index, vo ){
					render( vo, false );
				});
			},
			error: function( jqXHR, status, e){
				console.error( status+" : "+e);
			}
		});
	}
	
	$(function(){
		$( "#add-form").submit(function(event){
			event.preventDefault();
			
			var replyVo = {};
			
			replyVo.name = $("#input-name").val();
			if( replyVo.name === ""){
				messageBox("방명록에 글 남기기", "이름은 필수 입력 항목 입니다.", function(){
					$("#input-name").focus();
				});
				return;
			}
			replyVo.password = $("#input-password").val();
			if(replyVo.password === ""){
				messageBox("방명록에 글 남기기","비밀번호는 필수 입력 항목 입니다.", function(){
					$("#input-password").focus();
				});
				return;
			}
			
			replyVo.message = $("#ta-message").val();
			if( vo.message === ""){
				messageBox("방명록에 글 남기기", "내용은 필수 입력 항목 입니다.", function(){
					$( "#ta-message").focus();
				});
				return;
			}
			/* $.ajax({
				url: "${pageContext.request.contextPath}/board/api/add",
				type: "post",
			
			}) */
			
			
		});
		
		$( "#add-reply").click(function(){
			window.no = $(this).data("no");
			console.log("------------"+no);
			$("#list-guestbook li[data-no='"+ no +"']" .reply-add-form).show();
		});
		$( "#reply-cancle").click(function(){
			window.no = $(this).data("no");
			$("#list-guestbook li[data-no='"+ no +"']" .reply-add-form).hide();
		});
		fetchList();
	});
	
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp" />
		<div id="content">
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${boardVo.title}</td>
					</tr>
					<tr>
						<td class="label">이름</td>
						<td>${boardVo.userName}</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">${fn:replace(boardVo.content, newLine, "<br>") }
							</div>
						</td>
					</tr>
				</table>
				<div class="bottom">
					<a
						href="${pageContext.request.contextPath }/board?p=${param.p }&kwd=${param.kwd }">글목록</a>
					<c:if test="${authUser.no == boardVo.userNo }">
						<a
							href="${pageContext.request.contextPath }/board/modify/${boardVo.no}?p=${param.p}&kwd=${param.kwd}">수정하기</a>
					</c:if>
				</div>
				<div id="guestbook">
					<form id="add-form" action="" method="post">
						<input type="text" id="input-name" placeholder="이름"> 
						<input type="password" id="input-password" placeholder="비밀번호">
						<textarea id="ta-message" placeholder="내용을 입력해주세요"></textarea>
						<input type="submit" value="답글 달기">
					</form>
					<ul id="list-guestbook"></ul>
					<div style="margin: 20px 0; text-align: center">
						<button id="btn-next" style="padding: 10px 20px">다음</button>
					</div>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp">
			<c:param name="menu" value="board-ajax" />
		</c:import>
		<c:import url="/WEB-INF/views/include/footer.jsp" />
	</div>
</body>
</html>