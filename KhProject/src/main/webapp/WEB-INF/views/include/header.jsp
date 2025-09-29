<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="utf-8">

  <title>웹개발 시작하기</title>
  
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>

  <!-- Custom fonts for this template -->
  <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
  <link href='https://fonts.googleapis.com/css?family=Kaushan+Script' rel='stylesheet' type='text/css'>
  <link href='https://fonts.googleapis.com/css?family=Droid+Serif:400,700,400italic,700italic' rel='stylesheet' type='text/css'>
  <link href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700' rel='stylesheet' type='text/css'>

  <!-- Custom styles for this template -->
  <link href="resources/css/agency.min.css" rel="stylesheet">
  <script type="text/javascript" src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
  
  <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
  <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>

<style>
    #mainNav .navbar-nav .nav-item .nav-link {
        font-weight: 600;
    }

    #sub-bg{
        width : 80%;
        margin : auto;
        height : 1200px;
        padding-top : 60px;
        padding-bottom : 60px;
        margin-top : 300px;
    }

    #sub-1{
        width : 70%;
        height : 40%;
        margin-right : auto;
        background-image: url(https://www.kh-academy.co.kr/resources/images/main/main_renewal/sub/sub02/educationinfo/jongro/04.jpg);
        background-size: cover;
        background-repeat: none;
    }

    #sub-2{
        margin-top : 120px;
        width : 70%;
        margin-left : auto;
        height : 40%;
        background-image: url(https://www.kh-academy.co.kr/resources/images/main/main_renewal/sub/sub02/educationinfo/jongro/05.jpg);
        background-size: cover;
        background-repeat: none;
    }

    footer{
        border-top: #52b1ff28 1px solid;
    }

    .dropdown:hover > .dropdown-menu { 
        display: block;  
    }
</style>


</head>

<body id="page-top">

  <!-- Navigation -->
  <nav class="navbar navbar-expand-lg navbar-light fixed-top" id="mainNav">
    <div class="container">
      <a class="navbar-brand" href="#">
      	<img class="img-fluid" src="https://www.kh-academy.co.kr/resources/images/main/logo.svg" alt="로고없음" style="width:130px; height:50px;" />
      </a>
      <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive">
        메뉴
        <i class="fas fa-bars"></i>
      </button>
      
      <div class="collapse navbar-collapse" id="navbarResponsive">
        <ul class="navbar-nav text-uppercase ml-auto">
          <li class="nav-item">
            <a class="nav-link js-scroll-trigger" href="#">HOME</a>
          </li>
          <li class="nav-item">
            <a class="nav-link js-scroll-trigger" href="#">공지사항</a>
          </li>
          <li class="nav-item">
            <a class="nav-link js-scroll-trigger" href="#">게시판</a>
          </li>
          <li class="nav-item">
            <a class="nav-link js-scroll-trigger" href="#">사진게시판</a>
          </li>
          
          <!--
          	로그인 전 : 로그인 / 회원가입
          	로그인 후 : 내정보 / 로그아웃
          -->
          
          <%-- 조건문 중에 오늘 배운 choose when otherwise를 쓸건데 선행작업이 필요함
          3행에 올라가서 작업
          prefix는 taglib의 속성
          스크립틀릿 + @ + 지시어는 taglib + prefix="c" + uri
          --%>
          
          <%-- 조건을 적용할 부분을 choose태그로 전체 감싸야함
          첫번째 조건은 when으로 감싼다
          조건식은 test 속성에 작성
          조건식은 EL구문으로 써야함!
          로그인 했는지 안했는지는 어떻게 알아?
          로그인을 했다면 sessionScope에 userInfo라는 Attribute로 값이 담겨있을것이다
          session에 참조해서 userInfo
          이게 없어야 로그인 안된 상황이니까 없으면?은 empty
          있으면, 없지 않으면, 주소밖에 들어갈게 없는데 딱히 다른 조건은 필요없으니 otherwise
          지금 로그아웃은 오늘 남은시간에 구현 못해서 하고싶으면 서버 껐다켜거나 브라우저 껐다켜야함
          --%>
          <%-- <c:when> 얘는 여기 choose에 들어가면 안된다 --%>
          <c:choose>
          
          	  <c:when test="${ empty sessionScope.userInfo }">
		          <li class="nav-item">
		          <a class="nav-link js-scroll-trigger" data-toggle="modal" data-target="#log-in">로그인</a>
		          </li>
		          <li class="nav-item">
		          <a class="nav-link js-scroll-trigger" href="join">회원가입</a>
		          </li>
	          </c:when>
	         
	          <c:otherwise>
		          <li class="nav-item">
		          <a class="nav-link js-scroll-trigger" href="myPage">내정보</a>
		          </li>
		          <li class="nav-item">
		          <a class="nav-link js-scroll-trigger" href="logout" onclick="return confirm('진짜로 로그아웃 하려고?')">로그아웃</a>
		          </li>
	          </c:otherwise>
	          
          </c:choose>
          
        </ul>
      </div>
    </div>
  </nav><br><br><br>
  
	<script>
	
		const a = 2;
		
		if(a == 3) { // 이 안으로는 들어갈 수 없다. 자바스크립트 if문
			
		<%
		
			System.out.println("이거 왜 됨??");
			// 근데 된다... 진짜 유명한 질문, Stack overflow에 올라온 핫한질문
			// 처음 웹개발하는 사람들에게 아리까리하고 어려운 개념
			
			// 전체의 흐름과 얼개에서 생각해야함
			/*
			클라이언트가 컴퓨터로 웹 서비스를 이용하겠다고 브라우저를 켜서 URL을 입력함 -> localhost:4000/kh
			엔터를 입력하면 일어나는 일은 서버에 요청을 보냄(파일보고 DNS서버가고 브라우저가 작업하고 이런일이 일어나긴하지만 어쨌든)
			요청을 받는 것은 서버(서비스를 제공하는 컴퓨터 == 깡통, ip는 깡통의 주소), 깡통이 받은 다음에는 포트번호(요청을 처리할 프로세스, 우리는 지금 Tomcat)
			Tomcat이 listening 상태로 돌고있음, localhost:4000에 의해 Tomcat까지 간 것
			Tomcat은? WAS중의 한 종류이고, 이녀석을 다른 표현으로 Servlet Container라고 함
			서블릿 만들기 전에 context root 이야기 빼먹음, 이거먼저 해야함
			서버에 올라가있는 프로젝트들이 포트번호 4000번 안에서 여러개 있을 수 있는데, 그것들을 구분해줘야하니까, 어떤 애플리케이션인지
			Context root가 지금은 kh, 이게 어떤 Project인지 구분해주는 역할
			우리가 만드는 Servlet들이 Project 내부에 들어간다
			Servlet은 자바에서 매핑값 달아서 클래스로 구현해서 만든다.
			Static resource(정적자원) : HTML, CSS, JS, image, file(모든 사용자들이 똑같이 받아야하는 자원)
			동적 자원은 서블릿으로 생성한 후에 응답
			통신의 흐름이 어떻게 흘러가고있는건가, 어떤식으로 어디에 뭐가 어떻게 이루어지는지가 중요
			*/
		
		%>
		
		}
		
	</script>

  <!-- 로그인 Modal-->
<div class="modal fade" id="log-in">
	<div class="modal-dialog">
		<div class="modal-content">

			<!-- Modal Header -->
			<div class="modal-header">
				<h4 class="modal-title">
					<span style="color: #52b1ff;">KH</span> 로그인
				</h4>
				<button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>

			<!-- Modal body -->
			<div class="modal-body">

				<form action="login" name="sign-in" method="post" id="signInForm"
					style="margin-bottom: 0;">
					<table style="cellpadding: 0; cellspacing: 0; margin: 0 auto; width: 100%">
						<tr>
							<td style="text-align: left">
								<p><strong>아이디를 입력해주세요.</strong>&nbsp;&nbsp;&nbsp;<span id="idCheck"></span></p>
							</td>
						</tr>
						<tr>
							<td><input type="text" name="userId" id="signInId"
								class="form-control tooltipstered" maxlength="10"
								required="required" aria-required="true"
								style="margin-bottom: 25px; width: 100%; height: 40px; border: 1px solid #d9d9de"
								placeholder="최대 15자"></td>
						</tr>
						<tr>
							<td style="text-align: left">
								<p><strong>비밀번호를 입력해주세요.</strong>&nbsp;&nbsp;&nbsp;<span id="pwCheck"></span></p>
							</td>
						</tr>
						<tr>
							<td><input type="password" size="17" maxlength="20" id="signInPw"
								name="userPwd" class="form-control tooltipstered" 
								maxlength="20" required="required" aria-required="true"
								style="ime-mode: inactive; margin-bottom: 25px; height: 40px; border: 1px solid #d9d9de"
								placeholder="최소 8자"></td>
						</tr>
						<tr>
							<td style="padding-top: 10px; text-align: center">
								<p><strong>로그인하셔서 서비스를 이용해보세요~~!</strong></p>
							</td>
						</tr>
						<tr>
							<td style="width: 100%; text-align: center; colspan: 2;"><input
								type="submit" value="로그인" class="btn form-control tooltipstered" id="signIn-btn"
								style="background-color: #52b1ff; margin-top: 0; height: 40px; color: white; border: 0px solid #f78f24; opacity: 0.8">
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</div>


</body>
</html>