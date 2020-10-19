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
	
</script>
</head>
<body>

</body>
</html>