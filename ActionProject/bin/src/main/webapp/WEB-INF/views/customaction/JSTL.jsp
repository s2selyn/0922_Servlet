<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--
	URI(Uniform Resource Identifier)
	식별자 - 고유한 이름(테이블의 PK 제약조건 컬럼 같은것) -> 실제위치가 아니라 논리적인 식별값
	제공해주는 쪽에서 어떻게 적어야할 지 나와있음
	http://java.sun.com/jsp/jstl/core 이건 실제 URL이 아니라 라이브러리 구분하는 식별값
	이안에서 쓸것에 따라 또 / 붙여서 작성함
	아무튼 진짜 웹사이트 주소가 아님!
	버전이 바뀌면 uri를 바꿔서 제공해준다.
	
	URL(Uniform Resource Locator)
	위치정보(자원의 위치를 식별) -> 실제 파일의 경로나 웹 주소(맨날 주소창에 적던거, 웹상에서 얻고싶은 자원의 경로, webapp 밑에 만든 html을 직접접근하기도하고, mapping값으로 처리가능한곳을 찾아가기도하고)
-->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자신과의싸움</title>
</head>
<body>

	<h1>JSTL이란..?</h1>
	
	<p>
		Jakarta Server Page Standard Tag Library의 약어로 <br>
		JSP에서 사용할 수 있는 커스텀 액션 태그 <br>
		공통적으로 사용해야하는 코드들의 집합을 보다 쉽게 사용할 수 있도록 태그로 만들어서 <br>
		표준으로 제공하는 라이브러리(클래스 모음집)
	</p>
	<%--
	태그 쓰는 라이브러리들의 표준, JSP에 쓴다는 뜻, EL구문 쓰니까 스크립틀릿, 출력문 안써도되는데
	if문 반복문 쓰려니 스크립틀릿 무조건 써야겠는데?
	앞단 작업할때도 개발자들이 코드를 쓰는데 최종컨펌자가 모를 수 있음, jsp 열어서 작업할텐데 스크립틀릿, if, for이런거
	화면파트 담당자는 자바언어를 학습하는게 아니라 보기가 불편할 수 있겠지
	그사람들은 HTML, CSS, JS 전문이라, 여기서 getter, setter 막 이런거 호출하면 힘들수 있으니 괴리를 줄이자는 목표
	앞단에서 스크립틀릿 이런것좀 빼자는 목적
	막상 배우고 쓰면? 편함~
	import 하듯이 사용법이 있음
	이름에 Standard? 어마어마한 친구로군
	웹프로젝트에 라이브러리 추가하려면? webapp/WEB-INF/lib에 .jar인 파일들을 쏙쏙
	--%>		
	
	<hr>
	
	<h3>* 라이브러리를 추가</h3>
	
	<%-- 이번에는 같이 다운받아보자 --%>
	1) https://tomcat.apache.org로 접속 <br>
	2) Standard-1.2.5.jar파일 4개다 다운받기 <br>
	3) WEB-INF/lib폴더에 추가해주기 <br>
	<%-- 아주 근본이군 톰캣에서 제공하는 표준이라니, 공신력있도다, JSP 사용하는 자바개발자라면 누구든지 사용할듯? 우리나라 전세계 개발자들의 필수사용템 --%>
	
	<h4>* JSTL 선언</h4>
	<%-- 라이브러리 추가했으면 선언 먼저 해야함, 선언안하면 사용불가 --%>
	
	<p>
		JSTL을 사용하고자하는 해당 JSP파일 상단에 <br>
		(보통 page지시어 바로 밑에) <br>
		taglib지시어를 사용해서 선언함 <br><br>
		<%-- 스크립틀릿 + @ + taglib --%>
		
		[ 표현법 ] <br>
		&lt;@ taglib prefix="접두어" uri="파일uri">
		<%-- 많이 쓰는 거 3가지 있는데 하나는 꼭할거고 하나더는 진도상황보고하고 나머지는 버림 --%>
		<%-- 접두어는 tag 앞에 붙는 접두사를 말함, 여러개 등록할 때 구분할 용도로, 우리가 지금 쓸 건 core library
		이건 보통 100이면 98명이 c 라고 쓴다
		아무튼 쓰고싶은 JSP마다 이건 작성해줘야한다 include로 사용할수는 없음
		--%>
	</p>
	
	<hr>
	
	<h4>JSTL Core Library(오늘의 주인공)</h4>
	
	<p>
		변수와 조건문, 반복문 등의 로직과 관련된 태그들을 제공
	</p>
	
	<%-- 자바문법 써도되는데 중간에 갑자기 변수쓸일이 생길수있음, 그럼 스크립틀릿 만들어서 쓰겠지?
	이걸 하면 또 출력식 써야하고.. 너무 불편하니 편하게 할 수 있도록 태그로 써보자 --%>
	
	<h5>변수(사실 변수아님 속성선언하기 == Attribute)</h5>
	
	<pre>
		솔직하게 이야기하겠습니다. 사실 그냥 아까배운 Scope들에 새로운 속성 추가하는 방법입니다.
		
		[ 표현법 ]
		&lt;c:set var="키값" value="리터럴값" scope="스코프영역지정(생략가능)" />
		- Scope에 새로운 Attribute를 추가할 수 있는 태그
		- 더 나아가서 어떤 Scope에 추가할건지도 지정 가능(생략 시 pageScope에 담김)
		
	</pre>
	<%-- JSTL 라이브러리는 prefix로 시작한다 아까 c로 만들어둬서 <c: 이렇게 시작
	setAttribute 할때 key-value로 두개 적었으니까 여기서도 두개 작성해줘야함
	scope영역은 생략 가능하지만 지정해줄수있음
	그냥 setAttribute 하는건데 원래라면 스크립틀릿 열어서 했겠지만 태그로 하는것
	--%>
	
	<%-- 항상 prefix로 시작한다! 100% c는 아님, core로 쓰기도 함 우리는 c라고 썼으니까
	xml기반이니 반드시 닫는태그, 컨텐트 없으니 빈태그 양식으로 닫아줌 --%>
	<c:set var="num1" value="10" />
	<c:set var="num2" value="20" scope="request" />
	<%-- 스크립틀릿보단 당연히 시각적인 위화감이 없다 모르면 이해못하것지 --%>
	<c:set var="result" value="${ num1 + num2 }" scope="session" />
	
	<%-- 출력은 EL구문으로 해보자 --%>
	num1의 값 : ${ num1 } <br>
	num2의 값 : ${ num2 } <br>
	result의 값 : ${ result } <br>

</body>
</html>