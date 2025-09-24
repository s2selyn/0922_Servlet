<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>인크루드~~</title>
</head>
<body>

	<h1>include</h1>
	
	<p>
		다른 페이지를 포함할 때 사용
	</p>
	
	<%-- ??? 16:16 이건 jsp 컴파일할때 포함된다. 정적 include --%>
	<%--
	<%@ include file="footer.jsp" %>
	<%@ include file="footer.jsp" %>
	--%>
	<%-- 똑같은거 또하는건 안됨 Duplicate local variable year
	이 방식은 코드 자체를 전부 포함시켜버리니까 위에서 이미 year를 선언하고 사용하니까
	밑에 선언하는 부분이 중복되어버림
	컴파일 시점에 하기때문에 이게 중복되는것, 불편하다 변수명 얼마나 중복되것어요 --%>
	
	<%-- 그래서 표준 액션 태그 쓰자는게 주류 의견 --%>
	<h4>JSP표준 액션 태그를 이용한 방식 (동적 include)</h4>
	
	<%-- include 하려는 파일의 경로와 파일명을 작성하는데 지금은 같은 경로에 있어서 그냥 파일명만 --%>
	<jsp:include page="footer.jsp"></jsp:include>
	<%-- 이상태로는 바로 500에러 나옵니다. 이 기반 기술은 xml이라서 실제 여기서 적는것도 html이 아니고 그냥 문자열이지
	xml의 특성: html처럼 넉넉하지 않고 명확한 문법이 있음, 그중에 하나가 여는 태그가 있으면 반드시 닫는 태그가 있어야한다는 것
	html은 안닫아도 뭐 큰 문제가 없음, 그냥 화면 조금 깨짐
	xml은 안지켜주면 와장창 닫는태그 만들어줘라 --%>
	<!--
		XML기반기술이기 때문에 반드시 시작태그와 종료태그가 쌍으로 존재해야함!!!
		닫는 태그를 작성하지 않는다면 500에러 발생!! 
	-->
	<%-- 적을거 없으면 여는태그에서 닫아주면 되고 얘는 동적 방식이라 include 여러번 하는만큼 계속 나온다 --%>
	<jsp:include page="footer.jsp" />
	<jsp:include page="footer.jsp" />
	<jsp:include page="footer.jsp" />
	<jsp:include page="footer.jsp" />
	
	<hr>
	
	<%-- 값 전달이 가능함 --%>
	
	<!-- name은 내맘대로 밸류도 내맘대로 -->
	<!-- Content영역에 주석넣지않기! 그럼 500에러 날 수도 있음, 내가 아까 그래서 겪은거였군, 여는태그 닫는태그 사이에 주석넣지마시오 -->
	<jsp:include page="footer.jsp">
		<jsp:param name="test" value="Hi" />
	</jsp:include>
	
	<br>
	
	<jsp:include page="footer.jsp">
		<jsp:param name="test" value="Bye" />
	</jsp:include>

</body>
</html>