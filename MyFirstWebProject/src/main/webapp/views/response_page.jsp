<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 이 구문은 스크립틀릿(scriptlet)이라고 함
	// 자바코드를 그냥 쓸 수 있음
	// JSP상에서 자바코드를 쓸 수 있는 영역, int a = 0; 이런거 아무거나 전부
	// 옛날에는 여기에 JDBC 코드를 적었다네요. DB 가서 처리하고 응답받아온걸로 밑에 출력하고. 이게 굉장히 오래전에는 그런 규격으로 작업함
	// 이제 그게 발전하고 나뉘어서? 정확히는 Controller, Service가 도입되어서 바뀐거고
	// 아무튼 어쩌고저쩌고 ??? 14:40 DAO에서 하는 작업도 여기서 적어서 똑같이 동작시킬 수 있음

	// 혹시 JSP로 개발하는데 스크립틀릿을 쓴다면 상단에서 하는 작업은 서블릿에서 리퀘스트에 set 해둔 attribute를 뽑는 작업을 한다
	// 자바에서도 위에서 아래로 순서대로 작업되니까 출력하고싶으면 위에서 뽑아줘야함, 밑에서 뽑으면 위에서 출력못함
	// 그러니 attribute를 변수로 뽑아내는 작업을 여기서 한다.
	
	// 현재 이 JSP상에서 필요한 데이터들을 => request의 Attribute에서 뽑아내기
	// set할때 set했으니 얻고싶으니 get / key-value 쌍이니까 인자로 key값 전달, 아까 setAttribute 할때 썼던 첫번째인자
	// request.getAttribute("키값") : Object
	// 이러면 반환타입은 무조건 Object일수밖에 없음 메소드입장에서 받아온것도 오브젝트니까 이걸로 뽑을수밖에 없음 컴퓨터는 바보다
	// name key값의 attribute value를 얻고싶으니까, 그리고 인자 무조건 전달하라고 경고뜬다
	String name = (String)request.getAttribute("name");
	// 변수 타입은 String으로 받을건데 이런 빨간줄은 신경써야지 --> Type mismatch: cannot convert from Object to String
	// Object는 부모인데, 자식이 누군지 모른다, 우리는 알지 String으로 쓸거니까 강제형변환 해줘야한다
	
	String gender = (String)request.getAttribute("gender");
	// 얘도 메소드 반환타입이 오브젝트니까 강제로 스트링으로 형변환
	
	int age = (int)request.getAttribute("age");
	// Type mismatch: cannot convert from Object to int
	// (int)request.getAttribute("age"); 이건 말이 안된다. 참조자료형이 기본자료형으로 형변환 불가능(주소값이 정수로?)해야함 원래 안돼야함, 말이안된다.
	// 실제로는 된다. 왜 되냐?
	// 매개변수 자료형은 오브젝트인데 실제 자료형은 int, 참조자료형에 기본자료형 못들어감, 그러니까 사실 아까 servlet에서 attribute에 넣는것부터 말이안되는거였음
	// 두단계로 나누어져서 이게 가능해진다. setAttribute 할때 참조자료형으로 변환되어야 전달이 가능함
	// 결국 이미 참조자료형으로 바뀌어서 들어갔다는 이야기. 우리는 int형 넣었는데? 여기서 boxing이 일어남, Integer로 포장되어서 들어간것
	// ??? 14:50 뺄때도 마찬가지, 먼저 int로 바꿀거야-하기 전에 Integer로 나온다, 이건 unboxing ???으로 Integer로 뺀 다음에 int로 형변환한다.
	// 하나로 보이지만 사실 두단계 작업, 기본자료형인데 참조자료형으로 다루고 싶다면 Wrapper클래스를 쓴다, 래퍼크래스의 존재의의
	// ??? 15:04 아무튼 박싱언박싱으로 래퍼클래스 알차게 써먹음 쓰는진 안보임 아무튼 기본자료형 참조자료형 왔다갔다 가능
	double height = (double)request.getAttribute("height");
	String city = (String)request.getAttribute("city");
	String[] foods = (String[])request.getAttribute("foods");
	
	// 같은 자료형으로 정렬하고 묶고싶은 욕심이 있다면 -> age, height, city, name, gender, foods 순으로 나열가능
	// 나는 주석있어서 걍함
			
%>
<!DOCTYPE html>
<html>
<head>
<!-- 얼핏 보면 html같이 생김, 그렇게 생각하고 작업하면 되긴 함, 근데 맨위에 보면 java라고 되어있음
정체는 자바소스코드로 돌아가는 녀석
어떻게 동작하는지 직접 수행해봤음
나중에 동작할 때 얘(jsp)를 서블릿으로 변환을 시킴, 그리고 클라이언트에 응답을 해주는데,
우리가 response객체를 만들어서 보냈던 방식으로 응답해줌
이렇게 써놓으면 서블릿으로 알아서 변환해서 우리가 썼던 코드를(아까 개노가다로 스트림 이용해서 html쓴거) 서블릿으로 변환돼서 얘가 대신 써준다는 것
쉽게 생각하면 서블릿 쉽게 쓸 수 있는 도구가 jsp인것
이 코드는 전부 서블릿으로 변환이 된 뒤에 응답으로 보내진다
항상 우리가 클라이언트에게 응답을 보낼 때는 문자열 형태의 데이터를 응답한다.
jsp할때 머리속에 항상 생각할것, 아까 html 한땀씩 쓴거 너무 힘들었던거 편하게 쓰는 기술이다 -->
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<!-- 여기에서 XXX님은 이런거 해야함, 문제는 여기에서 XXX님을 사용할 방법이...? 있는데 있다고 하면 수업진행 안됨ㅋ
	딱히 없음^^! 솔직히 아무작업도 안해도 되지만 그럼 진짜로 진행안되니까 다른걸로 해보자
	사용자한테 입력받은 값이었는데, 이번엔 DB에서 조회한 값이라고 가정, 조회했더니 화면출력값이 6개가 돌아왔다
	컨트롤러 입장에서 jsp에게 보내주고싶은거임. 그래서 값들을 화면상에 출력시키고 싶다. 그걸 해보자~ -> RequestPostServlet에서 작업
	-->
	
	<h1>${ msg }</h1>
	<!-- 이건 지금 다른얘기, expression language에 해당한다. 순수 jsp 요소로 다시 확인해보자, 결국은 이걸할거지만 jsp 문법 학습해야하니까 불편해도 써볼거임 -->
	<!-- 아무튼 나머지 앞단에서 출력해주고싶은것들도 request attribute에 추가해주러 다시 RequestPostServlet에서 작업 -->
	
	<!-- request에 name으로 attribute 추가해둔걸 여기서 출력하고싶다면 보편적으로 3행에 공간을 만들어준다(DOCTYPE 위에) -->
	
	<!-- 변수로 뽑은값을 화면에 출력해주고싶다 -> 꺾쇠 안에 %= % 안에 변수명 쓰면됨 -->
	<h3><%= name %>님의 정보~</h3>
	<!-- 금융권에서는 이거 많이 쓴다네요, 기존거 유지보수 -->
	<!-- 잘 나오는지 확인했으니 나머지도 똑같이 출력ㄱㄱ -->
	나이 : <%= age %> <br>
	키 : <%= height %> <br>
	지역 : <%= city %> <br><br>
	
	성별 :
	<!-- 경우가 나뉜다 각 경우에 출력하고싶은 데이터가 다름 -->
	<!-- if 쓸라고 자바코드 스크립틀릿 쓰면 어떻게 써야하나? 블록을 분리해서 따로쓰고 그 사이에 html 내용을 작성함 -->
	<!-- 성별을 선택하지 않았을 경우 -->
	<% if(gender == null) { %>
		선택안함쓰~~~ <br>
	<% } else if("남".equals(gender)) { %>
		<!-- 성별을 선택했을 경우 -->
		남자쓰~~~~~~~~ <br>
	<% } else { %>
		여자쓰~~~~~~~~ <br>
	<% } %>
	
	좋아하는 음식쓰~~~~
	<!-- 선택했을수도 있고 안했을수도있고, 한거 보여주고, 안했다면 안했다고 보여주고싶으니 또 if -->
	<!-- ??? 15:15 무슨 생각을 해야하는지? -->
	<% if(foods == null) { %>
		안골랐스~~~~
	<% } else { %>
		<ul>
			<!-- 몇개일지 모르지만 갯수만큼 반복해야한다 -->
			<% for(int i = 0; i < foods.length; i++) { %>
				<li><%= foods[i] %></li>
			<% } %>
			
		</ul>
		입니다.
	<% } %>
	
</body>
</html>