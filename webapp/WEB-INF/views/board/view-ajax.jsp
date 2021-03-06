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
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	<style type="text/css">
		.ui-dialog .ui-dialog-buttonpane .ui-dialog-buttonset{
			float: none;
			text-align: center
		}
		.ui-dialog .ui-dialog-buttonpane button{
			margin-left: 10px;
			margin-right: auto
		}
		#dialog-message p{
			padding: 20px 0;
			font-weight: bold;
			font-size: 1.0em
		}
		#dialog-delete-form p{
			padding: 10px;
			font-weight: bold;
			font-size: 1.0em;
		}
		#dialog-delete-form p.error{
			color: #f00
		}
		#dialog-delete-form input[type="password"]{
			padding: 5px;
			border: 1px solid #888;
			outline: none;
			width: 180px
		}
	</style>
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
		if(vo.userName == null){
			vo.userName = "삭제된 글"
		}
		var rreply ="	<h4>@ "+vo.userName+"</h4>" 
		if(vo.replyNo === null){
			rreply = "";
		}
		var html = 
			"<li data-no='" + vo.no + "'>" +
			"	<strong>" + vo.name+ "</strong>" +
			rreply+
			"	<p>" +vo.message.replace(/\n/gi, "<br>")+ "</P>" + 
			"   <a href='' data-no='" + vo.no + "'>삭제</a>"+ 
			"	<button id='add-reply' data-no='"+vo.no+"'>댓글 달기</button>" +
			"	<div id='guestbook' style='display: none'>" +
			"   <form name='reply-add-form' action='' method='post'>" +
			"	<input type='hidden' name='groupNo' value='"+vo.groupNo+"'>" +
			"	<input type='hidden' name='orderNo' value='"+vo.orderNo+"'>" +
			"	<input type='hidden' name='depth' value='"+vo.depth+"'>" +
			"	<input type='text' name='reply-input-name' placeholder='이름'>" +
			"	<input type='password' name='reply-input-password' placeholder='비밀번호'>" +
			"	<textarea name='reply-ta-message' placeholder='내용을 입력해주세요'></textarea>" +
			"	<input data-no='" + vo.no + "' type='submit' name='addReply' value='답글 달기'>" +	
			"   <input type='button' name='reply-cancle' data-no='"+ vo.no+"' value='취소'>"+
			"	</form>" +
			"</li>";
		
		if (mode === true) {
			$("#list-guestbook").prepend(html);
		} else {
			$("#list-guestbook").append(html);
		}
	}
	
	var fetchList = function( mode ){
		if( isEnd === true){
			return ;
		}
		
		var startNo = $("#list-guestbook li").last().data( "no" ) || 0;
		if(mode === true){
			startNo = 0;	
		}
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
				console.error( status+" : "+jqXHR.responseText);
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
				"삭제":function(){
					var no = $("#delete-no").val();
					var password = $("#delete-password").val();
					
					$.ajax({
						url: "${pageContext.request.contextPath}/board/api/delete",
						type: "post",
						dataType: "json",
						data: "no="+no+"&"+
							  "password="+password,
							  
						success: function(response){
							if( response.result === "fail"){
								console.error( response.message);
								return ;
							}
							
							if(response.data === -1){
								$("#dialog-delete-form .validateTips").hide();
								$("#dialog-delete-form .validateTips.error").show();
								$("#delete-password").val("");
								return ;
							}
							
							$("#list-guestbook li[data-no='"+response.data+"']").remove();
							dialogDelete.dialog("close");
						},
						error: function( jqXHR, status, e){
							console.error(status+ " : "+ jqXHR.responseText);
						}
					});
				},
				"취소" : function(){
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
			
		//reply-ajax
		$(document).on("click", "input[name=addReply]", function(event){
			event.preventDefault();
			var replyVo = {};
			var selectedLi = $("#list-guestbook li[data-no='"+ $(this).data("no") +"'] form");
			replyVo.groupNo = selectedLi.children('input[name=groupNo]').val();
			replyVo.depth = selectedLi.children('input[name=depth]').val();
			replyVo.orderNo = selectedLi.children('input[name=orderNo]').val();
			replyVo.replyNo = $(this).data("no");
			replyVo.name = selectedLi.children('input[name=reply-input-name]').val();
			if( replyVo.name === ""){
				messageBox("방명록에 글 남기기", "이름은 필수 입력 항목 입니다.", function(){
					selectedLi.children('input[name=reply-input-name]').focus();
				});
				return;
			}
			replyVo.password = selectedLi.children('input[name=reply-input-password]').val();
			if(replyVo.password === ""){
				messageBox("방명록에 글 남기기","비밀번호는 필수 입력 항목 입니다.", function(){
					selectedLi.children('input[name=reply-input-password]').focus();
				});
				return;
			}
			
			replyVo.message = selectedLi.children('textarea[name=reply-ta-message]').val();
			if( replyVo.message === ""){
				messageBox("방명록에 글 남기기", "내용은 필수 입력 항목 입니다.", function(){
					selectedLi.children('textarea[name=reply-ta-message]').focus();
				});
				return;
			}
			console.log(replyVo);
			replyVo.boardNo = ${boardVo.no};
			$.ajax({
				url: "${pageContext.request.contextPath}/board/api/add",
				type: "post",
				dataType: "json",
				data: JSON.stringify(replyVo),
				contentType: 'application/json',
				success: function( response ){
					if( response.result === "fail"){
						console.error( "data.response"+response.message);
						return ;
					}
					console.log(response.data);
					
					fetchList(true);
					selectedLi[0].reset();
					selectedLi.children('div').hide();
				},
				error: function( jqXHR, status, e){
					console.error( status + " : "+ jqXHR.responseText);
				}
			});
		});
		
		$(document).on("click", "#list-guestbook li a", function(event){
			event.preventDefault();
			
			window.no = $(this).data("no");
			$("#delete-no").val(no);
			dialogDelete.dialog("open");
		});
		
		$(document).on("click", "#add-reply", function(event){
			event.preventDefault();
			window.no = $(this).data("no");
			console.log(no);
			$("#list-guestbook li[data-no='"+ no +"']").children('div').show();
		});
		
		$(document).on("click", "input[name=reply-cancle]", function(event){
			event.preventDefault();
			window.no = $(this).data("no");
			$("#list-guestbook li[data-no='"+ no +"']").children('div').hide();
		});
		
		$("#btn-next").click(function(){
			fetchList(false);
		});
		
		fetchList(true);
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
				<div id="dialog-message" title="" style="display:none">
					<p></p>
				</div>
				
				<div id="dialog-delete-form" title="댓글 삭제" style="display:none">
					<p class="validateTips normal">
						작성시 입력했던 비밀번호를 입력하세요.
					</p>
					<p class="validateTips error" style="display:none">
						비밀번호가 틀립니다.
					</p>
					
					<form>
						<input type="hidden" name="no" id="delete-no" value="">
						<input type="password" name="password" id="delete-password" value="" class="text ui-widget-content ui-corner-all">
						<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
					</form>
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