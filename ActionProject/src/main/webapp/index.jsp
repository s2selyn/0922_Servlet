<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>호오오오잇~</title>
</head>
<body>
	<h1>안녕 나는 웰컴파일임~~ 반가워~~~</h1>
	
	<h2>* EL(Expression Language)</h2>
	
	<p>
		JSP상에서는 출력하고 싶으면 출력식 써야함 => &lt;%= 변수 %> <br>
		<%-- 설명쓰고싶어서 저렇게 작성했는데 변수 없어서 500에러남
		&lt; 이렇게 쓰면 less than으로 인식해서 출력될때만 < 으로 출력된다 --%>
		EL구문을 사용하면 \${ 변수 } 형식으로 작성할 수 있음
		<%-- 이것도 $만 써버리면 와장창됨, \붙이면 된다 --%>
		<%-- 이런식으로 출력식 안쓸 수 있다 --%>
	</p>
	
	<h3>* EL구문 간단하게 공부해보기~</h3>
	
	<%-- 원래 views를 webapp에 만들어서 했는데, WEB-INF에 만들기로 하자 --%>
	<a href="/action/el.do">01_EL</a>
	<%-- href="WEB-INF/views/01_el.jsp" 이건 직접 접근이라 404, 서블릿으로 포워딩해서 보내는 방법을 써야함, 서블릿 매핑값 작성해줘야함
	절대경로/상대경로 두가지가 있다
	절대경로는 /로 시작하면 절대경로방식이라고 함, 여기 바로 뒤에는 contextroot를 적어줘야함, 이건 프로젝트 만들때 정했음, action
	기억안나면 서버 열어서 모듈에 패스에 적혀있음
	/하고 그뒤에 진짜 서블릿 매핑값을 적는다, 내맘대로가능
	적어놓고 웹에서 확인하면 404, 아직 서블릿 안 만들어서 그렇거든요?
	이제 서블릿 만들러 ㄱㄱ
	--%>
	<br><br>
	
	${ classRoom }, ${ academy }
	<%-- classRoom는 request에 담아서 출력안된다, academy 는 출력되는 이유 ??? 12:44 --%>

</body>
</html>