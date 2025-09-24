<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.kh.subway.model.vo.Subway"%>
<%
	// jsp의 요소
	// 스크립틀릿 : 자바코드를 그대로 작성하는 영역(세미콜론을 포함한 형태, 클래스에서 작업하듯이)
	// request.set 한걸 여기서 request.get 해서 출력해봤다
	// 오늘도 order타입으로 담아서 여기서 get할것, 메소드 호출하면서 인자로 set할때 String타입으로 넘겼던 key값
	Subway order = (Subway)request.getAttribute("order");
	// 뽑아서 담을 변수의 자료형은 Subway인데 빨간줄, Subway cannot be resolved to a type(무슨자료형인지 모름)
	// 얘는 결국 서블릿으로 변환되어야하는 클래스, 클래스에서 다른 패키지에 존재하는 클래스를 사용하고싶은것이지
	// 이거하려면 원래 import 해와야함
	// JSP는 Servlet으로 변환되어 동작하기 때문에 클래스 작성과 동일한 방식을
	// 생각해야함
	// 여기엔 패키지 선언부, import 적는 부분이 지금 없음, 이건 어디에? -> 스크립틀릿에 @붙은것을 지시어라고 함
	// 첫번째줄을 페이지 지시어(@다음 page)라고함, 여기 속성에 import라고 적을 수 있음, import 해야하는것을 이 속성으로 적을 수 있음
	// 보통은 여러개 하니까 따로 import만 분리해서 추가적인 페이지지시어에 적는다
	// 이제 타입 빨간줄 없음
	// 뒤쪽 value(우항)에서 빨간줄, Type mismatch: cannot convert from Object to Subway(반환타입이 Object인 메소드이므로)
	// 클래스캐스팅 해줘야함 -> 캐스팅 연산자, 형변환 연산자 라고 표현합니다. 괄호열고가 아니고

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주문확인 페이지</title>
</head>
<style>
	#wrap{
		width: 1200px;
		margin: auto;
	}
	
	h1{
		text-align: center;
	}
</style>
<body>

	<div id="wrap">
		<h1>주문내역</h1>
		
		<%-- order의 필드값을 출력해줄것임, 거기에 사용자가 입력한걸 담아뒀다. 여긴 문자열 쓰는데고, order는 자바의 객체
		자바의 값을 html에 출력하고싶으면? -->
		<%-- 오 이게 진짜 jsp 주석 --%>
		<%--
			JSP주석
			
			JSP내용들은 여기다 주석 처리
			
			HTML에 자바의 것을 출력하고 싶다면
			출력식 : <%= %>
			이렇게 작성하고 안에 ??? 9:48 뭐??? 자바의 변수 등을 작성
			??? order에 참조해서 getXXX메소드 호출(접근제한자)
			출력식에는 세미콜론을 달지 않는다
			스크립트에는 세미콜론을 무조건 달아야함
		--%>
		<h3>주문자정보</h3>
		이름 : <%= order.getName() %> <br>
		전화번호 : <%= order.getPhone() %> <br>
		주소 : <%= order.getAddress() %> <br>
		요청사항 : <%= order.getRequest() %> <br><br>
		
		<h3>메뉴정보</h3>
		샌드위치 : <br>
		채소 : <br>
		소스 : <br>
		쿠키 : <br>
		결제 방식 : <br><br>
		
		위와 같이 주문하시겠습니까? <br><br>
		
		<button>확인</button><button>취소</button>
	</div>
</body>
</html>