<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>여기는 포워드 페이지야</h1>
	
	<jsp:forward page="footer.jsp" />
	<%-- 이렇게 작성하면 포워딩 페이지의 속성값이 출력된다, 뭘 해버림? 걍 포워딩 한거죠... 사실 이게 끝
	중요한건 포워딩 했을 때의 URL을 봐야함
	http://localhost:4000/action/forward.do
	서블릿 매핑값이 그대로 찍혀있는데 실제로 나오는건 포워딩해놓은 footer.jsp
	이것만 기억하면 된다!
	--%>
	
	<!--
		URL 에는 http://localhost:4000/action/forward.do 가 찍혀있음
		
		URL은 그대로고 화면만 바뀜
	-->

</body>
</html>