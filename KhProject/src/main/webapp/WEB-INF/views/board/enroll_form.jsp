<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>딸깍</title>
<style>
	form {
		width : 90%;
		margin : auto;
	}

</style>
</head>
<body>
	<jsp:include page="../include/header.jsp" />
	
	<%-- 아래에서 뭘 하기 전에 조건문을 써서 로그인이 되어있는지 안되어있는지 체크할것임
	if 태그를 써야하는데 jsp에서 if태그 쓰려면 태그라이브러리 추가해야함 -> 3행에 추가
	
	userInfo가 null이면 쫓아내는것도 스크립트태그 이용해서
	location.href 이용해서 /kh로 돌아가도록
	이것저것 해보는게 재밌군요
	일부러 뒤로가기도 history.back()으로 구현해두셨음 자바스크립트 이렇게저렇게 써보려고
	이제 로그인을 해야 글쓸수있게 됨, 확인하면 1절 끝
	--%>
	<c:if test="${ userInfo eq null }">
		<script>
			alert("글작성은 로그인 이후 가능합니다.");
			location.href = "/kh";
		</script>
	</c:if>


	<div class="outer">

        <h2 align="center">게시글 작성하기</h2>
        <br><br> 
		
        <form action="insert.board" method="post" id="insert-form"
        	  enctype="multipart/form-data">
        <%--
       	파일 첨부는 input 요소의 타입을 파일로 해서 첨부
		폼태그가 있어야함, input type이 파일임
		요청 전송 방식이 post여야함
		get으로는 파일을 못보냄
		폼태그에 못보던 속성 → enctype
		이 속성을 “multipart/form-data” 라는 형식으로 적어야함
		파일첨부할때는 무조건 똑같이 적어야함!
		일반적인 폼태그 요청은 데이터가 문자열만 넘어감
		input key-value 이건 그래봤자 문자열
		이번에는 문자열+파일 데이터 두가지 방식을 같이 보낸다는 뜻, 멀티파트가 키밸류만 보내는게 아니라 파일데이터도 같이 보내겠다는 의미
		그래서 파일 첨부하는 폼태그에는 반드시 enctype multipart form-data 꼭 적혀있음 , 안그러면 파일을 서버에서 받아서 사용할 수 없음
		--%>
        <!-- 파일첨부요청을 보낼 때 form태그에 반드시
        	enctype="multipart/form-data" 라고 적혀있어야함
         -->
         <%-- form action 빈칸채우기, 매핑값 제일 좋은건 boards인데 벌써 쓰고있어서 다른거 써야함
         insert 하는거니까 insert.board로 하자
         
         서버에서 파일 받을 수 있게 라이브러리를 추가해야함
         jar파일 슬랙에서 받아서 dev에 넣고 lib에 추가
         servlets.com에서 받을 수 있음
         요청 받아줄 서블릿 생성 -> BoardInsertController 생성 후 작업
         --%>
        
	        <%-- 2절, select 태그에 공용밖에 없음
	        DB에 보면 categoryNo 컬럼이 있음, 나중에 조회할 때 categoryName을 조회함
	        넣을때는 No를 집어넣어야함, 이건 외래키
	        카테고리가 name으로 보이는데 넣을때는 번호로 넣어야함
	        카테고리 컬럼에는 10부터 70까지 7개 있음, 내용은 의미없고
	        이거 아니면 데이터 못들어갈거임, 외래키니까
	        그래서 select 태그에 내용을 카테고리 테이블에서 조회한 내용으로 넣어줌
	        그러면 카테고리 테이블의 추가/삭제 변동사항이 반영된다 -> select 태그 내부의 옵션을 카테고리 테이블에서 조회한 내용으로 채워주겠다는것이 2절
	        일단 조회한거로 만드려면? 화면에 출력해주는걸 하려는데? 접근방법생각! jsp에 온것은 conroller에서 보내서 왔음
	        갈 때 뭔가 출력할것을 보내주고싶음, 출력해줄 내용은 db의 카테고리에 있음, 작업은 컨트롤러에서 해야한다, 조회한것을 담아서 포워딩해야함
	        글쓰기 화면은 board_list.jsp에서 글쓰기를 눌러서 온다, 그러면 컨트롤러에 갔다가 enroll_form.jsp로 가는것임
	        DB에 들를 수 있는 곳이 컨트롤러뿐 -> EnrollFormController에서 작업
	        --%>
        	<div class="form-group">
	        	<select name="category" class="form-control">
		        	<c:forEach items="${category}" var="c">
	                    <option value="${ c.categoryNo }">
	                        ${ c.categoryName }
	                    </option>
               		</c:forEach>
	        	</select>
        	</div>
        	<%-- 옵션을 반복으로 감싼다, forEach 태그 사용, 카테고리를 테이블에서 조회해서 넣음 --%>

            <div class="form-group">
                <label for="usr">제목</label>
                <input type="text" class="form-control" id="usr" name="title">
            </div>

            <div class="form-group">
                <label for="comment">내용</label>
                <textarea class="form-control" name="content" rows="15" id="comment" style="resize:none;"></textarea>
            </div>
            
            <div class="form-group">
            	<input type="file" name="upfile">
            </div>

            <div align="center">
                <button type="submit" class="btn btn-sm btn-info">등록하기</button>
                <button type="button" class="btn btn-sm btn-secondary"
                onclick="history.back();">뒤로가기</button>
            </div>

        </form>
        
    </div>
    
    <jsp:include page="../include/footer.jsp" />

</body>
</html>