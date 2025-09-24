<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>안녕 나는 푸터야~</title>
</head>
<body>

	<script>
		const date = new Date();
	</script>
	
	<%
		String year = new java.text.SimpleDateFormat("yyyy").format(new java.util.Date());
	%>

	Copyright © 1998-<%= year %> KH Information Educational Institute All Right Reserved
	
	<br>
	
	<%-- include 시 넘긴 값을 쓰기 위해서는 EL구문을 사용한다 --%>
	
	<%-- param 태그에서 줬기때문에 param에 접근(참조)해서 key값 작성 --%>
	include.jsp로부터 전달받은 test라는 키값의 value ==> ${ param.test } <br>
	<%-- 이러고 브라우저에서 확인하면 param을 작성해서 전달해준 부분만 출력됨, 나머지는 빈문자열이라 없는것처럼 보인다 --%>
	
</body>
</html>