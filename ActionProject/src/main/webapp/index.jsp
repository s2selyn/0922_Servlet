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
	
	<a>01_EL</a>
	<br><br>
	
	<%-- 원래 views를 webapp에 만들어서 했는데, WEB-INF에 만들기로 하자 --%>

</body>
</html>