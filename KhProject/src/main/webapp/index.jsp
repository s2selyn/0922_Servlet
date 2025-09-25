<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>웰컴웰컴</title>
</head>
<body>

	<!--
		* WEB환경에서의 CRUD
		
		* 회원서비스
		로그인/로그아웃, 회원가입(아이디중복체크), 내정보조회(마이페이지), 내정보변경,
		비밀번호변경, 회원탈퇴
		
		* 공지사항서비스
		공지사항 등록, 공지사항 목록조회, 공지사항 상세조회, 공지사항 수정, 공지사항 삭제
		
		* 일반게시판서비스
		게시글목록조회(페이징처리), 상세조회, 게시글 작성(첨부파일 1개 업로드), 게시글 수정
		, 게시글 삭제, 댓글서비스, 게시글 검색
		페이징처리는 한번에 다 보여주는게 아니라 밑에 페이지 버튼 만들어서 5, 10개씩 나눠서 보기
		
		* 사진게시판서비스
		사진게시글목록조회(썸네일이미지), 상세조회, 게시글 작성(다중파일업로드)
		수정삭제는 똑같을거라 안할거임
		
		대충 9일 정도 예상(코드자체는 이틀이면 짜지만 설명이 필요하니 이정도), 근데 9일 투자하려니 뒤에 할게 너무많음
		다 하고싶지만 그건 안될것같고 하다가 적당히 조절
		이틀만에 해야하긴한데 그러기엔 애매하고...
		여기서 배울 새로운 코드들이 있지만 그런게 중요한게 아니라
		수업을 들을 때 집중할 부분은 웹에서의 전체 얼개 이해하기! 작동과정, 흐름전달
		
		MVC역할은 항상 동일하지만 view가 바뀌었음, 할일은 명확함(값입력받기, 응답결과출력하기)
		어떻게 C로 넘어가고 C는 어떻게 사용할지, 뒤는 마이바티스 어쩌고 똑같으니 신경끄고
		V <-> C 사이 신경쓰고
		??? 11:43 requestScope 등 어떤 객체 사용하는지에 집중
		
	-->
	
	<%-- header.jsp include하기 --%>
	<jsp:include page="WEB-INF/views/include/header.jsp" />
	
	<%-- main.jsp include하기 --%>
	<jsp:include page="WEB-INF/views/include/main.jsp" />
	
	<%-- footer include하기, 상대경로로 작성 --%>
	<jsp:include page="WEB-INF/views/include/footer.jsp" />

</body>
</html>