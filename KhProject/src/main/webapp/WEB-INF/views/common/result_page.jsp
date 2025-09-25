<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>실패용 페이지</title>
<style>
	h1{
		color: red;
		font-size: 64px;
		text-align: center;
		height: 600px;
		line-height: 600px;
	}
</style>
</head>
<body>

	<!-- 상대경로 방식으로 찾아가게 해보기 common에서 한단계 상위 view로 올라갔다가 include로 들어가야함
	하나 올라가고싶으면 ../ 라고 작성
	./는 내가 있는 경로라는 뜻, ..이면 상위폴더 하나로 이동하겠다는 뜻
	아 복잡하네 60년전에 만든놈한테 따지러가자
	윈도우 전부터 있던거라네요
	문자열 에러는 경로/파일명 오타 무조건임, 다시 컨트롤러 간다
	-->
	<jsp:include page="../include/header.jsp" />

	<h1>${ msg }</h1>
	
	<jsp:include page="../include/footer.jsp" />
	
</body>
</html>