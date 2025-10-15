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
			if는 있을때만 뭘 하고싶다 또는 없을때만 뭘 하고싶다에 좋음(하나만 하고싶은게 있을경우)
			지금은 있을때도 뭘 하고싶고 없을때도 뭘 하고싶으니까
			--%>
			<c:choose>
			
				<c:when test="${ empty boards }">
					등록된 게시글이 존재하지 않습니다.<br>
				</c:when>
				
				<c:otherwise>
				
					<c:forEach items="${ boards }" var="board">
						<%-- otherwise안에서 반복, 이 안에 들어왔다는것은 무조건 하나라도 있다는 뜻임 --%>
						<div class="thumbnail" align="center" onclick="detail(${ board.boardNo });">
							<%-- 상세보기 구현을 위해 생각해야 할일들 -> 어떻게 구현할지 스스로 생각하기, 이제 마지막 기능이니까
							실질적으로 이것을 구현할 때 해야할 작업의 본질이 무엇일까? 무슨 작업을 해야할까?
							접근방법: 우리가 이용하는 웹사이트를 떠올려보기가 제일 첫번째
							평상시에 이용하는 웹사이트는 어떻게 돌아가지?
							백지에서 내머릿속에서 하려고 하지말고 세상의 많은 상세보기를 생각, 상세보기 하고싶으면 목록에서 뭔가 하나를 클릭함, 그럼 상세보기가 나온다
							웬만하면 어디를 보느냐? url, get방식이므로 key-value가 붙어있음
							눈치코치로 알 수 있는 pk, 이것만 바꿔서 url 요청보내면 다른 상세보기가 계속 나온다, 쇼핑몰이라 웬만하면 이미지로 작업한것이 나온다
							우리도 이미지가 여러개
							게시글에 대한 제목, 상품가격, 옵션, 상품에 달린 상세이미지들을 화면에 출력해준다, 리뷰도있음
							상세보기는 게시글 목록에서 내가 어떤 게시글을 클릭했을 때 게시글에 대한 내용과 게시글에 달린 이미지, 상세이미지들을 전부 다 화면상에 출력해주면 상세보기로군
							
							코드 쓸 생각보다, 기능을 구현하려면 사이트 들어가서 먼저 이용해보기, 어떻게 해야 만들 수 있는지
							아무것도 없는 상태에서 생각해내기는 힘들다, 만들어진것을 보고 가이드 얻기, 모든 웹사이트가 가이드라인이 된다
							사용하기 편한거, 별로인거 참고하기, 없는걸 생각하면 어렵고 추상적이된다, 다른사람이 만든걸 써보고 살펴봄으로써 구체화 가능
							
							kh board 테이블에 있는 한줄, kh attachment 테이블에 있는 board에 달린 여러줄(최소 한줄에서 최대 네줄)을 들고가야겠군
							데이터베이스에가서 select해서 다시 앞단으로 돌아가면서 request어쩌구해서 setAttribute해서 다시 앞에 넘겨줘서 화면상에 출력해주면 상세보기가 끝나겠군
							그럼 게시글 목록이 있는데, 거기서 클릭하면 서블릿 가서 Board 1행, Attachment 여러행 반환해서 출력해야겠다
							(게시글 목록 → 클릭 → 서블릿 → Board 1행, At 여러행 → 반환 → 출력)
							
							이번에는 board중에서 사용자가 클릭한 한개만 조회하고싶으니 뭘 클릭했는지 식별해야함
							사진게시판에 조회해온 목록이 있음, image 관련내용, 게시글제목, 조회수 이런게있음
							제목가지고 식별이 안됨, 같은 제목이 있을 수 있음
							조회수도 마찬가지, 지금 상세보기 안해서 조회수 전부 0
							이미지는 attachment에 있으니 실질적으로 board에서 식별할값은 boardNo
							클릭했을 때 나중에 select할때 조건을 where에서 써야 board 여러개중 하나만 가져올수있으니 이것을 뒤로 넘겨줘야겠따, get방식으로 갈것임 조회니까
							그럼 요청시 전달값에 클릭한 boardNo를 조회할 수 있도록 넘겨야겠다 그걸로 나중에 조건 달아야지
							at도 식별해야하지만 이것은 refBno랑 같은값이므로 boardNo만 넘겨도 가능
							
							사용자가 클릭했을 때 일어나야하는 일은?
							요청이 가야함, 조회니까 get방식으로 보내면 될듯
							클릭했을때 요청이 가게 하려면? a태그로 감싸면서 href 속성을 부여해서 가능
							css 고쳐서 스타일 부여하면 가능
							a는 인라인, p는 블럭이라 좀..
							폼태그 만들어서 상세보기 버튼 만들어주는 방법도 있고
							onclick 이벤트 속성 이용해서 이벤트 핸들러 달아서 클릭할 때 url 요청이 가게끔 하는 방법도 있음
							지금은 onclick이 이뻐보인다 div에 달면되니까
							div에 달아서 해보자
							--%>
						
							<img src="${ board.filePath }/${ board.changeName }" alt="대표이미지">
							<%-- img src 작성할 때 경로 만들어주려면 /는 ${} 밖에서 따로 작성해야함, EL구문 내부에서 /는 나누기 연산으로 인식됨 --%>
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
			function detail(num) {
				
				// 클릭했을 때 => URL로 요청
				// url 바꿔서 요청이 가게끔 하고싶으니 어떤 객체의 무슨 속성을? 브라우저의 주소입력하는 친구를 건드리고 싶다면?
				// location.href
				// location 객체가 현재 웹에서의 위치를 가지고 있음, 이 친구 속성의 값을 바꿔줘야함
				// 주소가 들어있는 속성은 href 속성, 이 값을 바꿔주겠다면? 이 객체의 속성값을 바꾸고싶다면?
				// 1. 객체명 2. 참조 3. 속성명 4. 대입연산자 5. 새로운 대입하고싶은값
				// detail.image?boardNo= 이런 매핑값으로 boardNo를 보낸다고 하자
				location.href = `detail.image?boardNo=\${num}`;
				// 사용자가클릭한게시글의번호를 넘겨줘야하는데 이건 어떻게 알아낼 수 있음? 방법이 너무 많음
				// 힌트 : detail()이 선언적함수
				// 클릭했을 때, 클릭된 친구의 번호를 넘겨주고싶다...  매개변수로 EL구문 써버리기
				// 서버에서 만들어서 응답해줄 수 있음 boardNo 필드값 뽑아서
				// 이러면 함수선언부에서 매개변수 쓰면된다 jsp니까 \추가해서 가능
				// 이러지 않고도 가능, div요소에 id를 부여할 식별값을 속성값으로 넣음 id="${board.boardNo}"
				// pk니까 안겹침
				// 선언적 함수는 이벤트 부여할때 this키워드 써서 이벤트 일어난 친구 지정후 .id 하면 속성값 얻을 수 있음 this.id
				// 아예 this만 받아와서 사용해도 되고
				// 클래스 속성 써서 이벤트 매개변수 엘리먼트 받아서 값만 뽑아서 사용가능
				// 자식요소 제일첫번째 선택해서 input hidden으로 넘길수도 있음
				// 진짜 어렵게 하려면 div자식 p자식 label 컨텐트영역값 스플릿으로 잘라서 뒤의값만 정규표현식으로 숫자뽑아서 써야지 등등
				// 게시글 번호를 얻기 위한 방법은 정말 많다
				// 스프링할때까지는 자바스크립트로 이렇게
				
				// 스프링은 게시판/회원가입 정도만 하고 넘어갈거고
				// 스프링부트를 또 배울것임, 현시대 개발표준은 스프링부트
				// 서포팅 기간이 지나면 기술지원이 끊긴다, 프로그램이라 나중에 문제가 일어나도 기간중엔 해결해주지만 끝나면 안해줌
				// 그냥 스프링은 5.3버전대
				// 6버전대는 클라우드에 맞춰진 친구들, 돌아가는 환경이 다름, 기업들이 많이 쓰는건 5.3버전인데 하도 많이 써서 기간이 좀 길고
				// 언젠가는 끝날 예정이 되어있어서 5.3 스프링 프레임워크로 신규개발을 하지않음
				// 이제는 스프링부트가 3버전이 표준, 3은 3이면 다 호환됨, 3.2에서 3.5로가도 문제가 없어
				// 그러므로 스프링은 오래안하고 부트를 열심히할것이에요
				// 이건 차이가 많이나는 부분이 request 서블릿 이런거 쓰는데 jsp 웬만하면 쓰지마라고 되어있음
				// 부트는 jsp쓰지말라고 공식문서에서 저장함(톰캣 충돌난다네요)
				// 그러니 스프링까지만 jsp쓰고 졸업할거임, 권장사항이 아님
				// 그럼 화면을 자바스크립트로 만들어야하는데 다음주 주말정도부터는 자바스크립트 복습해야함
				// 스프링도 제품이라 사용법 배우는거고 지금하는게 중요
				// 공식문서는 친절하게 다써주지만 영어라서 시간이 걸림, 시간날때 읽어보셔
				
				// 아무튼 이렇게 값을 넘겨보자! 요청 보내기 매핑값 처리할수있는 서블릿 만들러
				// 두가지방식으로 해보자 -> ImageDetailController에서 작업
				
			};
		
		</script>
	
	</div>
	
	<jsp:include page="../include/footer.jsp" />

</body>
</html>