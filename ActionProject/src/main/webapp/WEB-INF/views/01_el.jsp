<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.kh.el.model.vo.Person" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>EL구문 짱조와용 오홍홍</title>
</head>
<body>

	<h1>EL구문 배울거임 진짜조와용</h1>
	
	<%-- 일단 기존방식처럼 스트립틀릿, 출력식 써서 해보기 --%>
	<h3>기존방식 사용</h3>
	<%--
	<%
		// requestScope => classroom, student 속성으로 값 두개 담음
		// 뽑을때는? get
		String classRoom = (String)request.getAttribute("classRoom");
		Person student = (Person)request.getAttribute("student");
		// import는 페이지 지시어에 추가
		
		// sessionScope => academy, lecture 속성으로 값 두개 담음
		String academy = (String)session.getAttribute("academy");
		Person lecture = (Person)session.getAttribute("lecture");
		
		// request, session을 만든적이 없는데 어떻게 쓸 수 있음...?
		// 내장객체! 서블릿 내부라면 어떤 서블릿이든 쓸 수 있음(page는 제외, 걘 jsp에서)
		// 다 지정이 되어있음, 매칭되어있음, 내장이니까, 굳이 만들지 않아도 사용가능
		
		// 일단 넘어온거 출력 -> 출력식으로 함(??? 12:40 pre 태그 쓰는 이유가..?)
		
		// request는 서블릿에서 받았는데 session은? 아무 연관이 없는데 사용이 가능함
		// scope는 사용가능한 범위/영역에 대한 구분
		// session 스코프는 서블릿으로 요청 보내서 담았으니까 굳이 이 jsp가 아니라, index.jsp에서도 사용가능
		// ??? 12:44 범위 이야기 정리해야함
				
		// 강사정보, 수강생정보는 객체에 담아왔으므로 확인을 위해서 필드값을 알아내려면 getter 메소드 호출해야함
	%>
	
	<p>
		학원명 : <%= academy %> <br>
		강의장 : <%= classRoom %> <br>
		강사정보 : <%= lecture.getName() %>, <%= lecture.getAge() %>, <%= lecture.getAddress() %> <br><br>
		
		수강생의 정보
		<ul>
			<li>이름 : <%= student.getName() %></li>
			<li>나이 : <%= student.getAge() %></li>
			<li>주소 : <%= student.getAddress() %></li>
		</ul>
	</p>
	--%>
	
</body>
</html>