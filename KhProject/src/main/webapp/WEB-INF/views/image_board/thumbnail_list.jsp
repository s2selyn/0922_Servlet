<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style> 
	.list-area{
		text-align : center;
	}

	.thumbnail{
		box-shadow : 1px 1px 2px #0000002e;
		width : 300px;
		padding : 12px;
		margin : 25px;
		display : inline-block;
		background-color: #ffffffb0;
		color:#000000b0;
		font-weight: bold;
		border-radius: 12px;
	}

	.thumbnail > img{
		width : 270px;
		height : 180px;
		margin-bottom : 8px;
		border : 1px solid rgb(172 205 255 / 57%);
		border-radius: 12px;
	}

	.thumbnail:hover{
		cursor:pointer;
		opacity:0.8;
	}


</style>
</head>
<body>

	<jsp:include page="../include/header.jsp" />
	
	<div class="outer">
		
		<div style="margin-top : 15px; width : 400px; height : 280px; margin:auto;">
			<img width="100%" height="100%" src="https://kh-academy.co.kr/resources/images/main/logo.svg" alt="">
		</div>
		
			<div style="align:right;" >
				<a class="btn btn-sm btn-primary"
				   style="background:rgb(193 229 255); border:none; width:100%"
				   href="form.image"
				>글작성</a>
			</div>

		<div class="list-area">
			
				<br>
				
			<%-- 더미로 있던 div 5개중에 4개 지우고 하나만 남김, 고정값이 아니라 DB에서 조회해준 값으로 변경하자
			선생님이랑 나랑도 게시글 다를거고, 어쨌든 조회된 개수만큼 출력해주고싶음 -> 반복해야함
			jsp에서 반복하기 위해서 사용해야 할 것은? 스크립틀릿 for문 또는 jstl로 코어라이브러리 쓰기
			jstl쓰려면 forEach 태그 써야하는데 생각할게 있음
			게시글이 없을수도 있음! 반복하기 전에 항상 게시글이 없다고 응답 보낼 경우를 생각해야함
			게시글 있을때만 forEach 돌려서 출력해줘야함
			그러니 forEach전에 조건을 먼저 걸어야함, 있을때 없을때 출력하고싶은 내용이 다르니까 스크립틀릿 if문, jstl if태그, choose when otherwise 셋중하나
			지금은 뭐가 좋은가? 없을땐 없읍니다, 있을땐 있읍니다 출력하고싶으니 choose when otherwise
			if는 있을때만 뭘 하고싶다에 좋음(하나만 하고싶은게 있을경우)
			지금은 있을때도 뭘 하고싶고 없을때도 뭘 하고싶으니까
			--%>
			<c:choose>
			
				<c:when test="${ empty boards }">
					등록된 게시글이 존재하지 않습니다.<br>
				</c:when>
				
				<c:otherwise>
				
					<c:forEach items="${ boards }" var="board">
						<%-- otherwise안에서 반복, 이 안에 들어왔다는것은 무조건 하나라도 있다는 뜻임 --%>
						<div class="thumbnail" align="center" onclick="detail();">
							<img src="${ board.filePath }/${ board.changeName }" alt="대표이미지">
							<p>
								<label>No. ${ board.boardNo }</label> / <span>${ board.boardTitle }</span> <br>
								<label>조회수</label> : <span>${ board.count }</span>
							</p>
						<%-- 반복을 통해 boardDto에 있는 내용을 뽑아서 출력 --%>
						</div>
					</c:forEach>
					
				</c:otherwise>
				
			</c:choose>

		</div>
		
		<script>
		
			// detail() 정의하기 위해서는 script 태그가 필요, 함수 정의할때는 키워드를 function으로 작성
			function detail() {
				
				// 클릭했을 때 => URL로 요청
				// url 바꿔서 요청이 가게끔 하고싶으니 어떤 객체의 무슨 속성을? 브라우저의 주소입력하는 친구를 건드리고 싶다면?
				// location.href
				// location 객체가 현재 웹에서의 위치를 가지고 있음, 이 친구 속성의 값을 바꿔줘야함
				// 주소가 들어있는 속성은 href 속성, 이 값을 바꿔주겠다면? 이 객체의 속성값을 바꾸고싶다면?
				// 1. 객체명 2. 참조 3. 속성명 4. 대입연산자 5. 새로운 대입하고싶은값
				// detail.image?boardNo= 이런 매핑값으로 boardNo를 보낸다고 하자
				location.href = '';
				
			};
		
		</script>
	
	</div>
	
	<jsp:include page="../include/footer.jsp" />

</body>
</html>