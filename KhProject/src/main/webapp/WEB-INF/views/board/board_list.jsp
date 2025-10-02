<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>


<style>

header.masthead {
   display: none;
}   
.row{
	height : 800px;
}
tr:hover{
	cursor : pointer;
}

</style>

<br/><br/> 
 
   <jsp:include page="../include/header.jsp"/>

   <!-- Begin Page Content -->
   <div class="container">
      <div class="row">
         <div class="col-lg-1">
         </div>
         <div class="col-lg-10">
            <div class="panel-body">
            <h2 class="page-header"><span style="color: #52b1ff;">KH</span> 자유 게시판
               <a href="enrollForm.board" class="btn float-right" style="background-color: #52b1ff; margin-top: 0; height: 40px; color: white; border: 0px solid #f78f24; opacity: 0.8">글쓰기</a>
            </h2>
               <table class="table table-bordered table-hover">
                  <thead>
	                  <tr style="background-color: #52b1ff; margin-top: 0; height: 40px; color: white; border: 0px solid #f78f24; opacity: 0.8">
	                     <th width="100">번호</th>
	                     <th width="150">카테고리</th>
	                     <th width="150">작성자</th>
	                     <th width="450">제목</th>
	                     <th width="200">작성일</th>
	                     <th width="100">조회수</th>
	                  </tr>
                  </thead>
                  <tbody>
	           
	                <c:forEach var="board" items="${ boards }">
                    <tr style="color: #52b1ff;"
                        class="board"
                        id="${ board.boardNo }">
                        <td>
                        ${ board.boardNo }
                        </td>
                        <td>
                        ${ board.category }
                        </td>
                        <td>
                        ${ board.boardWriter }
                        </td>
                        <td style="color: #52d6ffcc;">
                        ${ board.boardTitle } &nbsp;
                        <!--
                        <a href="detail.board?boardNo=${ board.boardNo }">
                        </a>
                        -->
                        </td>
                        <td>
                        ${ board.createDate }
                        </td>
                        <td>
                        ${ board.count }
                        </td>
                    </tr>    
                    </c:forEach>
	        
                  </tbody>
                  
               </table>
               
<%-- 검색기능 구현용 div 붙여넣기 --%>
<div id="search-area" class="form-group">
			<form action="search.board" method="get">
				<select name="condition" class="form-control">
					<option value="writer">작성자</option>
					<option value="content">내용</option>
					<option value="title">제목</option>
				</select>
				<input type="text" name="query" class="form-control" />
				<input type="hidden" name="page" value="1" />
				<button type="submit" class="btn btn-block" style="background:#52b1ff; color:white">검색</button>
			</form>
	     </div>
<%-- 검색기능 구현용 div 붙여넣기 --%>
                              
            </div>            
         </div>
      </div>
      <script>
      		$(function() {
      			
      			$(".board").click(e => {
      				
      				// console.log(e.currentTarget.id);
      				// 현재 내가 클릭한 이벤트가 발생한 이벤트 타겟 요소객체가 선택된다 --> e.currentTarget
      				// 요소객체가 필요한게 아니라 요소객체의 id속성값이 필요함
      				// 이걸 얻고싶으면 여기서 currentTarget의 id 속성값을 얻어내고 싶다면 객체의 속성값을 얻어내고 싶다면
      				// 객체명을 쓴다, 참조를 한다, 속성명을 쓴다(객체에 참조해서 속성명을 적는다) --> e.currentTarget.id
      				const targetId = e.currentTarget.id;
      				location.href = `detail.board?boardNo=\${targetId}`;
      				// jsp에서 백틱문법 쓰면 el구문으로 해석하고 서버에서 돌려버림, 빈문자열이 된다
      				// 이럴 때 js 파일로 분리하거나 $앞에 \을 붙여준다
      				
      				// location.href = `detail.board`;
      				
      			});
      			
      		});
      </script>
         <div class="paging-area" align="center" >
         
				<button 
	       		class="btn btn-outline-primary" style="color:#52b1ff;"
	       		onclick="location.href='boards?page=${pi.startPage - 1}'">이전전</button>
        	
        		<c:if test="${ pi.currentPage > 1 }">
		        	<button 
		       		class="btn btn-outline-primary" style="color:#52b1ff;"
		       		onclick="location.href='boards?page=${pi.currentPage - 1}'">이전</button>
        		</c:if>
        		
				<c:forEach var="i" begin="${ pi.startPage }"
								   end="${ pi.endPage }">
	                <button 
	                class="btn btn-outline-primary" style="color:#52b1ff;"
	                onclick="location.href='boards?page=${i}'">${i}</button>
				</c:forEach>
	        			
	        	<c:if test="${ pi.currentPage ne pi.maxPage }">
		        	<button 
		       		class="btn btn-outline-primary" style="color:#52b1ff;"
		       		onclick="location.href='boards?page=${pi.currentPage + 1}'">다음</button>
	       		</c:if>
	       		
				<button 
	       		class="btn btn-outline-primary" style="color:#52b1ff;"
	       		onclick="location.href='boards?page=${pi.endPage + 1}'">다다음</button>
        	
        </div>
      
      
      
      
   </div>
   
     
     
   <jsp:include page="../include/footer.jsp"/>




</head>
<body>

</body>
</html>

