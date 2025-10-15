<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>업데이트~~~</title>
<style>

    
    form{
    	width : 80%;
    	margin : auto;
    }
</style>
</head>
<body>

	<jsp:include page="../include/header.jsp" />
	
	<div class="outer">

        <h2 align="center">게시글 수정하기</h2>
        <br><br> 

        <form action="update.board" method="post" id="update-form"
        	  enctype="multipart/form-data">
        	<input type="hidden" name="boardNo" value="${ map.board.boardNo }" />
        	<%-- 원래의 인풋요소의 value 속성에, UpdateFormController에서 포워딩 할 때 select한 것을 request에 담아서 보낸것을 받았으므로 속성값으로 넣어준다
        	원래 있던 게시글의 정보를 update(갱신)하는 것이므로 update문의 where절에 boardNo 안넣으면 모든 게시글이 바뀜
        	요청 보낼 때 몇번 게시글이 바뀌는건지 값을 보내줘야함, 그게 없으면 뭔지 몰라서 못바꾼다
        	BOARD_NO를 넘겨야함, 방법 2개정도 있을듯? input type hidden으로 사용해보자
        	화면상에 아예 안보이게 만들어서 넘기면 좋다, 이럴 때 얘를 쓰라고 있는거지
        	이러면 몇번 게시글인지 아니까 업데이트 가능
        	??? 9:30 다른 방법은 어떻게? --%>
        	<div class="form-group">
	        	<select name="category" class="form-control">
	        		<c:forEach items="${ category }" var="c">
	        			<option value="${ c.categoryNo }" class="${ c.categoryName }">
	        				${ c.categoryName }
	        			</option>
	        		</c:forEach>
	        		
	        	</select>
        	</div>
        	
        	<%-- ??? 17:45 스크립트 태그 작성 설명 있음 --%>
        	<script>
        		$(function() {
        			$('option[class="${map.board.category}"]').attr("selected", true);
        			// alert("${map.board.category}");
        			// $("option").each(function() {
        				// ${map.board.category}
        				// console.log($(this).text().trim());
        				// if("${map.board.category}" === $(this).text().trim()) {
        					// $(this).attr("selected", true);
        				// }
        			// })
        		})
        	</script>
        	
			<%-- 제목, 내용, 원본 첨부파일명을 원본 것으로 보여줘야함 --%>
            <div class="form-group">
                <label for="usr">제목</label>
                <input type="text" class="form-control" id="usr" name="title" value="${ map.board.boardTitle }">
            </div>

            <div class="form-group">
                <label for="comment">내용</label>
                <textarea class="form-control" name="content" rows="15" id="comment" style="resize:none;">${ map.board.boardContent }</textarea>
            </div>
            
            <div class="form-group">
            	<input type="file" name="reUpfile">
            	
            	<%-- 첨부파일도 있었는지 없었는지 보여주고, 수정 가능하게 구현해야함
            	첨부파일이 있었을때만 원본파일명 보여주고싶으니 조건문 써야함, jsp, 서버에서 돌리고싶은거니까 core library의 if태그로 감싼다
            	map의 at이 비어있지 않다면 첨부파일이 존재한다는 것
            	원본파일명은 map의 at에 originName에 담았음 --%>
            	<c:if test="${ not empty map.at }">
	            	<!-- 기존 첨부파일이 존재했을 경우 원본파일명 보여주기 -->
	            	첨부파일 : <label>${ map.at.originName }</label>
	            	<%-- 첨부파일 수정도 문제가 있는 것 같은데? 부족할 것 같은데? 이따가 다시 와야겠다 --%>
	            	<input type="hidden" name="fileNo" value="${ map.at.fileNo }" />
	            	<%-- 다시와서 원본 파일정보 넘기는 코드 작성, 기존 첨부파일이 없었을때는 상관없음, 있었을때만 기존 파일의 번호를 보내줘야함
	            	사용자에게 보여줄 필요는 없을 것 같으니 몰래 넘기고싶다 -> 아까처럼 input type hidden 사용 --%>
	            	<input type="hidden" name="changeName" value="${ map.at.changeName }" />
	            	<%-- 사용자가 변경한 파일명도 전달해준다, 사용자에게 보여줄 필요는 없고, 있었을때만 changeName을 넘겨줌 --%>
            	</c:if>
            </div>

            <div align="center">
                <button type="submit" class="btn btn-sm btn-info">수정하기</button>
                <button type="button" class="btn btn-sm btn-secondary"
                onclick="history.back();">뒤로가기</button>
            </div>

        </form>
        
    </div>
    
	<jsp:include page="../include/footer.jsp" />
    

</body>
</html>