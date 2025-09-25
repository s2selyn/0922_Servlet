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
	
	<hr>
	
	<h3>EL 구문을 이용해서 request / session에 담긴 값들을 출력</h3>
	
	<p>
		학원명 : ${ academy } <br><%-- $ 다음의 중괄호 안에 setAttribute 할 때 담았던 key값만 적는다 --%>
		강의장 : ${ classRoom } <br>
		강사 : ${ lecture.name }, ${ lecture.age }, ${ lecture.address } <%-- 강사는 Person 객체에 넘겼음, 이건 key값을 먼저 적음, 참조해서 필드명만 작성 --%>
	</p>
	<%--
		뒷단에서 넘긴 key값만 적으면 앞단에 출력된다. 변수로 만들지도 않았고 메소드 호출하지도 않았음
		거기다가 이거 접근제한자 public도 아닌데 왜 되는거임? private인데.. 자바문법체계로 될수없는방법
		
		실제로 필드에 직접 접근한게 아님
		VO의 getter / setter 삭제하면 500에러남, 이건 자바코드 에러니까 콘솔창을 봐야함
		외부도구 많이 사용할거라 stacktrace 복잡한데 아무튼 여기 봐야해
		무슨 예외가 일어났는지 제일 먼저 최우선으로 봐야함 PropertyNotFoundException이 생김
		lecture.name이 문제된다고 함
		name 필드는 그대로 있는데, getter / setter가 없으니 안되는것, 그럼 얘가 getter / setter를 이용했다는 뜻이겠지
		getter가 필드값 반환하는 애니까 얘를 썼겠다.
		EL구문은 getter메소드를 이용하는것!
		만약에 name이라고 적는다면 이것을 보고 메소드를 찾음
		
		name -> getName()
		naming convention 문제
		간혹 필드명을 지을 때 person이 아니라 student로 가정, studentName이 귀찮아서 sName이라고 해버렸다 쳐
		그리고 getter/setter를 만들면 getter -> getsName(), setter -> setsName() 이렇게 만들어진다
		이상태에서 EL구문을 쓴다고 ${ student.sName } 라고 써도
		getSName(); 이걸 찾아버림, 클래스에 없으니 500 에러 난다
		귀찮다고 하고싶은대로 마음대로 했다가는 사용해야하는 기술이 작동하지 않을 수 있다. 이런거 절대 하면 안됨
		변수이름 성의없이 짓거나 외자로 시작하거나.. 전혀 예상치 못한데서 문제가 생길 수 있음
		사용할 기술은 일반적인 자바 개발자의 생각으로 만들어져있으니 개인생각으로 하면 곤란함
		코드짤때 기본적으로 지켜야할것들은 지켜서 작업
		
		내부적으로 getter메소드를 찾아서 호출해서 값을 출력해주는 구조(그래서 맨날 getter 만들자고 했음)
		
		lecture에 접근했을 때 value는 Person타입 객체(앞으로 수많은 VO가 생길것임)
		해당 Person객체의 각 필드값을 출력하려고 한다면 키값.필드명을 작성하면
		해당 필드에 네이밍컨벤션에 부합하는 getter를 찾아서 호출해줌
		
		그럼 출력식 쓸때는 getter 직접 호출했지만 귀찮음, 변수빼기도 귀찮고, 출력식 쓰기도 귀찮고 다 귀찮다
		EL구문으로 귀찮은 작업을 날려버릴 수 있음
	--%>
	
	<%--
		int num = (int)request.getAttribute("num");
		// 이런 코드가 있다고 가정, 서블릿에서는 num을 넘기지 않았다.
		// 없는걸 넘겼거나 오타가 났다면?
		출력식의 경우에는 바로 500에러
		EL구문의 경우에는 그냥 빈 문자열이 출력된다.
		EL구문은 스코프를 가리지 않고 알아서 출력하는데, 서블릿으로 다시 돌아가서 포워딩 이전에 작업
	--%>
	
	<p>없는 키값을 출력하는 경우</p>
	<p>출력식을 사용함 : &lt;%= num %></p>
	<p>EL구문을 사용함 : ${ asdfasdf }</p>
	<%-- EL구문은 없는게 아니고 빈 문자열이 출력됨 --%>
	
	<h3>3. EL 사용 시 키값이 동일한 경우</h3>
	
	key 라는 키값에 담긴 밸류 : ${ key } <br>
	<%-- request scope에 담은 key값이 나온다 --%>
	<%-- 이 상태에서 pageScope에 속성을 추가해보자 --%>
	<%
		// pageScope에 속성추가
		pageContext.setAttribute("key", "pageKey");
	%>
	
	다시 출력해보기 : ${ key } <br>
	<%-- 이건 pageKey가 출력된다. 그럼 눈치상 아래있는 녀석일수록 우선순위가 높았던거겠군
	best는 key값 안겹치는게 제일 좋겠지만
	여러사람이 작업을 한다고 쳤을 때 이런 변수명, 필드명, 속성명, 파일명 등을 비슷하게 지을수도있음
	--%>
	
	<!--
		EL 구문은 가장 작은 범위의 scope부터 키값을 검색함
		
		page => request => session => application 순으로 키값을 찾음
		
		모든영역에 키가 존재하지않았다??
		아무일도 일어나지 않음 오류안남~
		오류나면 잘못된걸 알수있으니까 고치면 되고, 안나온다? 이러면 key값 잘못쓴거라고 생각하면된다
		스프링 할때까지 90%는 key값 잘못, 오타
	-->
	
	pageScope에 값이 담겨있는데, 다른 곳에 있는 값을 쓰고싶을 수 있지, 다른 Scope의 값을 사용하고 싶다면 ?? <br>
	
	==> Scope에 직접 접근하기 <br><br>
	<%-- page에 담은걸 쓰고싶다면 그냥 아무것도 안써도됨 --%>
	
	requestScope : ${ requestScope.key } <br>
	sessionScope : ${ sessionScope.key } <br>
	applicationScope : ${ applicationScope.key } <br>
	
	<%-- 실제 프로젝트에서는 jsp 안쓰고 view를 바꿔서 쓸텐데 회사에서는 jsp를 얼마든지 만날 수 있다. 이걸 볼 확률이 더 높지롱
	프로젝트에서 쓰는거 만날 확률보다는 jsp가 더 확률높고 중요함 --%>
	<%-- 다른데 안담고 session에 담았다면 그냥 key값만 써도 잘 되겠지만, session을 안써주면 page에서부터 찾아나서니까 연산이 추가된다..?
	처음부터 session을 지정해주면 자원소모 필요없이 바로 값을 찾지 않겠느냐, 그러니까 웬만하면 붙여쓰는게 좋다는 이야기인데
	솔직히 실제로는 티도안남ㅋ 이 연산은 뭐 그렇지. 누가 옳은 말을 하느냐? 조금이라도 자원을 아끼는게 맞는말이긴 하지, 코드를 봐도 명확하고
	그러니까 이런 상황에서는 웬만하면 Scope를 붙이는게 좋다
	
	아무튼 출력식을 바꿔봤다, 좋아진점 ??? 14:35
	--%>
	
	<hr>
	
	<h2>EL구문을 이용해서 연산해보기</h2>
	<%-- ??? 14:37 나중에 할일과 연산 필요성 관련설명 --%>
	<%-- Servlet에서 값을 추가로 넘기는 작업을 추가하자 --%>
	
	<p>
		산술연산
		
		big + small = ${ big + small } <br>
		big - small = ${ big - small } <br>
		big X small = ${ big * small } <br>
		big / small = ${ big / small } 또는 ${ big div small } <br>
		big % small = ${ big % small } 또는 ${ big mod small } <br>
	</p>
	<%-- 소인배 판별법 행이동 시켜서 빨간줄 없어지면 소인배 --%>
	<%-- 나누기랑 모듈러 연산 /, % 이거 별로안좋아하고 다른방법을 선호함, EL구문은 키워드로 가능한건 키워드를 적는것을 선호한다
	이거 한눈에 보기좋다는데 영어쓰는사람들이나 그렇겠지만 걔네가 기준인걸 ㅜ 걔네한테 맞춰준다 --%>
	
	<hr>
	
	<h3>숫자간의 대소비교 연산</h3>
	
	<p>
		big이 small보다 크니? : ${ big > small } 또는 ${ big gt small } <br>
		big이 small보다 작니? : ${ big < small } 또는 ${ big lt small } <br>
		big이 small보다 크거나 같니? : ${ big >= small } 또는 ${ big ge small } <br>
		big이 small보다 작거나 같니? : ${ big <= small } 또는 ${ big le small } <br>
	</p>
	<%-- 이런거 우리가 비교연산 조건식으로 써먹을거라 중요, 사실 산술연산 여기서 왜함 뒤에서 해야지 --%>
	<%-- 근데 또 기호를 선호하지 않는다네요, 뭐 잘못쓰면 html에서 태그로 인식할수도? 그래서 이해해줄수있을듯 --%>
	
	<hr>
	
	<h3>동등비교 연산</h3>
	
	<p>
		big과 small이 같습니까? : ${ big == small } 또는 ${ big eq small } <br>
		<%-- 지금 넘어온값으로 하고있는데 상수값(정수리터럴)으로도 할수있어 --%>
		big과 10이 같습니까? : ${ big == 10 } 또는 ${ big eq 10 } <br>
		strOne과 strTwo가 같습니까? : ${ strOne == strTwo } 또는 ${ strOne eq strTwo } <br>
		<%-- 자바였다면 equals 또는 == 동등비교연산자,
		동등비교 연산자에 의한것이면 결과는 주소값 비교니까 false나올것임(두개는 저장위치가 다르다)
		EL구문은 == 로도 자바의 equals 메소드 쓴것처럼 true가 나온다 --%>
		<%-- EL구문에서의 문자열 == 비교는 자바에서의 equals()와 같은 동작을 함 --%>
		strOne에 담긴값과 "안녕"이 일치하나요? : ${ strOne == "안녕" } 또는 ${ strOne == '안녕' } <br>
		<%-- 자바 문자열은 쌍따옴표 고정이지만 EL문법은 따옴표 안가림 --%>
		strOne과 strTwo가 같지 않습니까? : ${ strOne != strTwo } 또는 ${ strOne ne strTwo } <br>
																	<!-- ne : not equals -->
		<%-- jsp는 적폐다! 자바인기는 얘가 다 끌어올렸지만...
		한국은 나라에 전자정부프레임워크가 있음, 나라에서 정해준 나라개발 시 표준 규격
		여기서 스프링과 jsp를 표준으로 만들었는데 나온게 2009년, 그당시에는 괜찮은 표준이었음, 이것때문에 웹개발자 많아지고 인기도 늘고
		그럼 한국에서 웹개발로 먹고살려면 자바 스프링 jsp 해야겠다고 고정관념이 되어버림
		최근에는 프론트엔드 기술이 좋아져서 뷰를 jsp로 만들던걸 대체해주면 좋으니까...
		우리나라 특. 어쩔수없이 비중이 높음 2009년부터 16년동안 jsp가 시장을 꽉꽉(jQuery도 비슷한 인기기간)
		그래서 기존 회사들 전부 전자정부프레임워크로 먹고사니까 jsp로 만들었을것임
		공부안하는 개발자도 많아서 그냥 가진기술 쓰는경우도 많아 한번 배워두고 계속 써먹는거지
		jsp를 버릴수가없다 만날 확률도 굉장히 높다
		그래도 jsp를 막 공부해서 잘할 필요는 없는데 최소 필요한 부분 쓸때 남들이 써놓은거 알아보고 쓸 수 있는 정도로만
		--%>
	</p>
	
	<hr>
	
	<h3>4. 객체가 null인지 또는 리스트가 비어있는 체크</h3>
	
	<p>
		* 기존방식 <br>
		
		스크립틀릿으로 if()
		객체 == null
		리스트.isEmpty()
	</p>
	
	<p>
		* EL구문 <br>
		<%-- 뒤에서 Person을 꽉 채워넘김, SELECT 한거라면 결과가 없을때 null이 돌아갈테니 여기서 체크해줘야한다 --%>
		
		obj가 null과 일치합니까 ? <br>
		${ obj == null } 또는 ${ obj eq null } 또는 ${ empty obj } <br>
		<%-- 아무래도 키워드 사용을 선호하고 empty로도 확인가능 --%>
		<%-- 앞에 두개는 순수하게 null값만 가지고 비교하는거고 empty는 기능이 추가됨, null인지 빈문자열인지까지 추가해서 비교하는 진화된 버전 --%>
		
		list가 비어있습니까 ? : ${ empty list } <br>
		list가 비어있지않습니까 ? : ${ !empty list } <br>
	</p>
	
	<hr>
	
	<h3>5. 논리연산자</h3>
	
	<p>
		AND 연산 : ${ true && true } 또는 ${ true and true } <br>
		OR  연산 : ${ true || true } 또는 ${ true or false } <br>
	</p>
	
</body>
</html>