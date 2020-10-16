<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<link href="${pageContext.request.contextPath }/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form"
					action="${pageContext.request.contextPath }/board" method="get">
					<input type="text" id="kwd" name="kwd" value="${map.keyword }">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:forEach items="${map.list }" var="vo" varStatus="status">
						<tr>
							<td>${map.totalCount = (map.currentPage-1)* map.listSize - status.index}</td>
							<c:choose>
								<c:when test="${vo.depth >0 }">
									<!-- 댓글 작업 -->
								</c:when>
								<c:otherwise>
									<td class="left"><a
										href="${pageContext.request.contextPath }/board/view/${vo.no}?p=${map.currentPage}&kwd=${map.keyword}">${vo.title }</a>
									</td>
								</c:otherwise>
							</c:choose>
							<td>${vo.userName }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<td>
								<c:choose>
									<c:when test="${not empty authUser && authUser.no == vo.userNo }">
										<!-- 자기가 쓴 글이면 삭제 -->
									</c:when>
									<c:otherwise>
										&nbsp;
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</table>
				<!-- 페이징 -->
				<div class="pager">
					<c:if test="${map.prevPage > 0 }">
						<li><a href="${pageContext.request.contextPath }/board?p=${map.prevPage}&kwd=${map.keyword}">◀</a></li>
					</c:if>
					
					<c:forEach begin="${map.beginPage }" end="${map.beginPage + map.listSize-1 }" var="page">
						<c:choose>
							<c:when test="${map.endPage < page }">
								<li>${page }</li>
							</c:when>
							<c:when test="${map.currentPage == page }">
								<li class="selected">${page }</li>
							</c:when>
							<c:otherwise>
								<li><a href="${pageContext.request.contextPath }/board?p=${page }&kwd=${map.keyword }"></a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:if test="${map.nextPage > 0 }">
						<li><a href="${pageContext.request.contextPath }/board?p=${map.nextPage}&kwd=${map.keyowrd}">▶</a></li>
					</c:if>
				</div>
				<div class="bottom">
					<!-- 글쓰기 작업 -->
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/include/footer.jsp"/>	
	</div>
</body>
</html>