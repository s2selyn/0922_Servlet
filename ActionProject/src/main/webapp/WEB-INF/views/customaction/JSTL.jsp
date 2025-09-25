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
	
	<c:set var="result" scope="request">9999</c:set>
	<%-- value 속성을 안적고 content 영역에 쓸 수 있다 --%>
	<%-- value속성 말고 시작태그와 종료태그 사이에도 대입할 값을 기술할 수 있음 --%>
	
	<hr>
	
	<pre>
		* 속성 삭제(&lt;c:remove var="제거하려고하는 키값" scope="스코프영역(생략가능)"/>)
		<%-- 태그 prefix로 시작, key를 입력하면 key-value가 같이 날아간다 --%>
		
		- 해당 Attribute를 Scope영역에서 제거하는 태그
		- scope속성을 작성하지 않으면 모든 scope에서 해당 Attribute를 찾아서 다 제거함
	</pre>
	
	삭제 전 result : ${ result } <br>
	<%-- EL구문에서 그냥 result라고 작성했으니까 pageScope부터 찾아올라가는데 거기ㅣ 안써서 requestScope를 찾음, 우리가 9999 작성했으니까 그걸로 나옴, sessionScope까지 못올라갔음 --%>
	
	<%-- 속성 var, scope 2개 적을 수 있는데 보통은 scope를 적는다, 안적으면 다날아가니까 --%>
	
	<hr>
	
	requestScope에서 result속성을 삭제 <br>
	
	<%-- attribute는 key-value 세트니까 get/remove 할때 key가 필요 --%>
	<c:remove var="result" scope="request" />
	삭제 후 result : ${ result } <br>
	<%-- 30은 왜나오냐? session에 담았거든요, 이건 아직 안지웠음, scope를 request로 지정했으니까 9999만 삭제됨 --%>
	
	<hr>
	
	<pre>
		* 속성 출력(&lt;c:out value="출력할값" default="기본값" escapeXml="t/f"/>)
		- 데이터를 출력하려고 할 때 사용하는 태그
		- default : 기본값, value속성에 출력하고자 하는 값이 없을 경우 대체해서 출력할
					내용물을 쓸 수 있음(생략가능)
		- escapeXml : HTML태그를 인식시킬수도있음
	</pre>
	
	num1을 출력! : <c:out value="${ num1 }" /> <br>
	이걸 왜이렇게 씀 ? : ${ num1 } <br>
	
	<br>
	
	<%-- 귀찮아보이는데 default 속성을 이용하면 꿀같음 --%>
	requestScope result : ${ requestScope.result } <br>
	<%-- 우리가 지워버려서 빈문자열이 출력됨 --%>
	out태그를 써볼까? : <c:out value="${ requestScope.result }" default="값이 없어용..." />
	<%-- default 속성은 value에 값이 없을 때 대체해서 보여줄 기본값을 만들어주는것
	원래 이거 하려면 if문 썼어야했음, 값이 있으면 출력, 값이 없으면 없다는 코멘트 출력 등
	이거써서 굳이 if문 안쓰고도 출력가능
	--%>
	
	<br>
	
	<c:set var="outStr" value="<strong>강한정보</strong>" />
	<%-- scope지정 안했으니까 pageScope에 들어간다, 이런 동적 요소를 화면에 쓰고싶은 일이 생겼음 --%>
	<br>
	${ outStr } <br>
	<%-- EL구문으로 그냥 출력하면 strong 태그 적용됨 --%>
	
	<%-- out 태그로 이용하면? --%>
	<c:out value="${ outStr }" />
	&lt;strong>강한정보&lt;/strong>
	<%-- ??? 9:35 escapeXml을 쓰면 일어나는 일 설명 --%>
	
	<hr>
	
	<h3>2. 조건문 - if</h3>
	
	<pre>
	
		[표현법]
		&lt;c:if test="조건식">
			(content영역에)조건식이 true일 경우 실행할 내용(우리는 보통 출력하겠지)
		&lt;/c:if>
		<%-- xml 기반 기술이라 반드시 닫는 태그 필요함 --%>
		
		- Java의 단일 if문과 비슷한 역할을 수행하는 태그
		- 조건식은 test라는 속성에 작성(보통 여기서 많이 실수)
		  (조건식을 만들때는 반드시 EL구문으로 작성해야함!!!!!!!!!!)(어제 배운 논리연산, 비교연산 할 때 배운 EL구문으로 써야함, 자바코드 안된다)
	</pre>
	
	<%-- ??? 9:40 예시
	만약에 스크립틀릿이었다면 조건식을 쓸 때 뒷단에서 넘긴 값을 받아와서
	<% if((int)request.getAttribute("v1") > (int)request.getAttribute("v2")) { %>
		value1이 value2보다 큽니다.
	<% } %>
	이렇게 작성, 이거 쓰기 싫어서 배운다
	--%>
	<c:if test="${ num1 gt num2 }">
		<strong>num1이 num2보다 큽니다.</strong> <br>
	</c:if>
	
	<c:if test="${ num1 le num2 }">
		<strong>num1이 num2보다 작거나 같습니다.</strong> <br>
	</c:if>
	<%-- 이건 단일 if 쓸 때 사용하는 태그 --%>
	
	<br>
	
	<%-- 조건이 여러개일때 사용하는 조건태그 --%>
	<h3>3. 조건문 - choose, when, otherwise</h3>
	<%-- oracle에서 case when then? 이런거 했던거랑 비슷함..인데 기억력 이슈 --%>
	
	<pre>
		[ 표현법 ]
		&lt;c:choose>
			&lt;c:when test="조건1">
				어쩌고저쩌고~
			&lt;/c:when>
			&lt;c:when test="조건2">
				어쩌고저쩌고~
			&lt;/c:when>
			&lt;c:otherwise>
				어쩌고저쩌고~
			&lt;/c:otherwise>
		&lt;/c:choose>
		
		- Java의 if-else문, switch문 비슷한 역할을 하는 태그
		각 조건들은 choose의 하위요소로 when태그를 이용해서 작성
		otherwise는 조건을 작성하지 않음
		
		조건 EL구문으로 작성하기!
	</pre>
	<%-- choose안에는 when, otherwise만 들어갈 수 있다, when, otherwise가 choose의 자식요소, choose로 전체를 감싸고 시작
	when 태그가 if역할을 함
	if 마지막의 else, switch의 default 쓰듯이 otherwise 사용
	--%>
	
	<%-- 조건 여러개써서 화면출력을 다르게하고싶다면 아마 이런 스크립틀릿 --%>
	<%--
	<% if(조건1) { %>
	
	<% } else if(조건2) { %>
	
	<% } else if(조건3) { %>
	
	<% } else { %>
	
	<% } %>
	--%>
	<%-- 귀찮고 위화감드니까 이걸 안쓰고싶다 --%>
	
	<%-- DB에서 데이터 조회해왔는데 회원의 포인트라고 하자
	이사람의 포인트가 100점보다 아래면 일반회원, 300점보다 아래면 우수회원, 300점보다 넘어간다면 아주 훌륭한 회원 이런식으로 출력해주고싶다 --%>
	<c:set var="point" value="200" />
	
	회원 등급 출력 :
	<c:choose>
		<c:when test="${ point le 100 }">
			일반회원 <br>
		</c:when>
		<c:when test="${ point le 300 }">
			우수회원 <br>
		</c:when>
		<c:otherwise>
			<strong>최우수회원</strong>
		</c:otherwise>
	</c:choose>
	<%-- point value 200이니까 두번째 when을 만족하고 우수회원 출력된다 --%>
	<%-- choose는 when과 otherwise 말고는 가질 수 없음, 그래서 '회원 등급 출력 :' <- 이런거, 주석 넣어버리면 바로 500에러남
	주석을 when 안, otherwise 안에 넣어버리면 또 그건 가능하긴함
	아무튼 실수 많이 하니까 기억하쇼
	--%>
	
	<hr>
	
	<h3>4. 반복문 - forEach</h3>
	
	<%--
	제어변수 역할은 var에 작성한 속성명(변수명이라고 생각)
	향상된 for문의 varStatus는 잘 쓰지 않는다
	--%>
	<pre>
		[ 표현법 ]
		for loop문
		&lt;c:forEach var="속성명" begin="초기값" end="끝값" step="몇씩증가">
			반복시킬 내용
		&lt;/c:forEach>
		
		step 은 생략 시 기본값이 1(보통 잘 안적음)
		
		향상된 for문(배열이나 collection의 모든 요소 순차반복)
		&lt;c:forEach var="속성명" items="순차적으로접근할배열/컬렉션" varStatus="상태값">
			반복시킬 내용
		&lt;/c:forEach>
		
		var로 선언된 제어변수를 사용할때는 반드시 EL구문으로 접근해야함!!
	</pre>
	
	<%-- 만약에 반복문 써서 0부터 9까지 html에 출력하고 싶은 상황이 있다면 --%>
	<% for(int i = 0; i < 10; i++) { %>
		<%= i %>
	<% } %>
	<%-- 제어변수 찍을때는 출력식을 사용
	하지만 스크립틀릿도 싫고 출력식도 싫그등요? --%>
	
	<br>
	
	<c:forEach var="i" begin="0" end="9">
		${ i }
	</c:forEach>
	
	<br>
	
	<c:forEach var="i" begin="1" end="6">
		<h${ i }>이런것도 가능</h${ i }>
	</c:forEach>
	<%-- 태그의 이름이 들어가는 부분에 EL구문을 써서 반복문 적용 가능 --%>
	
	<c:set var="color">
		red, orangered, orange, yellow, yellowgreen, greenyellow, forestgreen
	</c:set>
	<%-- 이렇게 작성하면 attribute를 배열 형태로 선언한것이 된다 --%>
	
	color : ${ color }
	
	<br>
	
	<ul>
		<%-- 향상된 for문을 써서 c를 이용해서 접근하도록 함 --%>
		<c:forEach var="c" items="${ color }">
			<li style="color: ${ c }">${ c }</li>
		</c:forEach>
	</ul>
	
	<hr>
	
	<%-- 서블릿에서 DB갔다온척함, 그럼 request에 담겨서 왔을것임 --%>
	<%-- table태그는 정보를 정돈해서 보여주고 싶을 때 쓴다, tbody에서는 tag library 써보고
	tfoot도 용도가 있으니 배워보자
	출력하고싶은 데이터는 DB에서 조회해서 requestScope에 추가해둠, 뽑아서 출력해야하는데
	조회결과가 없을 수 있음, 테이블에 insert가 되지않아서 비었거나 모든데이터 삭제해서 비었거나 뒷단에 특정 문제가 생겨서 정상적으로 데이터가 넘어오지 않았거나
	있을수도있고 없을수도있는 상황을 구분해서 데이터 보여줘야하니까 조건문 만들어야함
	두가지모두 하고싶은일이 있음(있는거출력/없다고출력)
	이럴때 보통 choose when otherwise사용
	--%>
	<table border="1">
		<thead>
			<tr>
				<th>이름</th>
				<th>나이</th>
				<th>주소</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${ empty list }">
					<%-- 여기 들어왔다는 것은 list가 없다, 비어있다는 의미이므로 --%>
					<tr>
						<th colspan="3">조회결과가 존재하지 않습니다.</th>
					</tr>
				</c:when>
				<c:otherwise>
					<%-- list가 비어있지 않을 경우를 처리할 내용, list의 요소가 있긴 있는데 몇개인지 모름, 조회해온 내용 모두 출력하고싶으니 반복문
					for loop문 또는 향상된 for문이 있는데 일반적으로 이런 상황에서는 향상된 for문 사용(list 또는 배열)
					??? 10:44 loop도 EL구문 써서 하면 된다
					toString 오버라이딩 안해둬서 person객체 주소값 출력된다.
					person 객체를 출력하고싶은게 아니라 필드값을 출력하고싶으니 EL구문 + 각 person객체를 접근할 제어변수 p + 참조연산자 + 필드명
					스크립틀릿은 getter메소드명
					EL구문 쓰고있으니 필드명만 쓰면 네이밍 컨벤션에 맞춰서 getter 메소드를 호출한다
					--%>
					<c:forEach var="p" items="${ requestScope.list }">
						<tr>
							<td>${ p.name }</td>
							<td>${ p.age }</td>
							<td>${ p.address }</td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>

</body>
</html>