<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
<br/><br/>

	<jsp:include page="../include/header.jsp" />
	
	<div class="outer">
		<div class="container">
		
		<div class="row">
		  <div class="offset-lg-2 col-lg-8">
		    <div class="card">
		      <div class="card-header text-white" style="background-color: #52b1ff;">${ map.board.boardNo }번 게시물 내용</div>
		      <div class="card-body"> 
		
		          <div class="form-group">
		            <label>카테고리</label><br>
		            <span>${ map.board.category }</span>
		          </div>      
		        
		          <div class="form-group">
		            <label>작성자</label>
		            <input type="text" class="form-control" name='writer' value="${ map.board.boardWriter }" readonly>
		          </div>
		          
		          <div class="form-group">
		            <label>제목</label>
		            <input type="text" class="form-control" name='title' value="${ map.board.boardTitle }" readonly>
		          </div>
		
		          <div class="form-group">
		            <label>내용</label>
		            <textarea class="form-control" rows="5" name='content' readonly style="resize:none;">${ map.board.boardContent }</textarea>
		          </div>
		
		          <div class="form-group">
		            <label>첨부파일</label>

					<c:choose>
						<c:when test="${ map.at ne null }">
						<!-- 
						??? 15:30
						localhost:4000/kh/resources/board_upfiles/KHacacemy_20251001143059_514.jpg
						a 태그에 이 경로를 이용하도록 속성을 작성해줘야함
						앞의 부분은 filePath필드, 뒤는 무슨 필드? 에 담아옴
						 -->
						
			            	<!-- 첨부파일은 있을수도있음 -->
			            	<a 
			            	download="${ map.at.originName }"
			            	href="${ map.at.filePath }/${ map.at.changeName }"
			            	>${ map.at.originName }</a><br>
			            	
			            	<img src="${ map.at.filePath }/${ map.at.changeName }" width="360" height="240"/>
						</c:when>
						<c:otherwise>
			            	<!-- 첨부파일은 없을수도있음 -->
			            	&nbsp;&nbsp;<span>첨부파일이 존재하지 않습니다.</span>
			            </c:otherwise>
			       </c:choose>
		          </div>
		         
		          <a class="btn" href="boards?currentPage=1"
		             style="background-color: #52b1ff; height: 40px; color: white; border: 0px solid #388E3C; opacity: 0.8"
		          >목록</a>&nbsp;&nbsp;
		          
		          <!--
		          	버튼 두 개를 게시글 작성자만 볼 수 있게 하고 싶음
		          	사용자의 식별 : MEMBER -> PK(USER_NO), UNIQUE(USER_ID) 값으로 할 수 있음
		          	현재 로그인된 사용자의 것과 board table에 있는 컬럼값과 비고해야함
		          	멤버테이블의 userId는 안됨?
		          	조인해서 들고온거랑 비교해야함
		          	지금은 userNo, userId 둘 다 없어서 비교가 안됨
		          	어쩔 수 없이 가서 다시 조회해와야한다
		          	그게 싫다면 여기서 요청을 보낼 때 게시글 번호를 같이 보내서 작성자(게시글에 달려있는 보드라이터를 조회)를 조회해서 현재 세션에 담긴 사용자
		          	근데 a 태그라 get방식으로 보내니 악의적인 사용자가 바꾸어 보낼 수 있으니까 안된다
		          	지금은 boardWriter로 식별불가
		          	명확한걸 위해 USER_NO를 가져오는게 좋겠다
		          -->
		          
		          <c:if test="${ userInfo.userNo eq map.boardWriter }">
			          <a 
			            class="btn" 
			            href="updateForm.board?boardNo=${ map.board.boardNo }"
			      		style="background-color: orange; height: 40px; color: white; border: 0px solid #388E3C; opacity: 0.8"
			      		>수정</a>&nbsp;&nbsp;
			          
			          <a 
			            class="btn" 
			            href="delete.board?boardNo=${ map.board.boardNo }" onclick="return confirm('정말로 삭제하시겠습니까?')"
			      		style="background-color: red; height: 40px; color: white; border: 0px solid #388E3C; opacity: 0.8"
			      		>삭제</a>&nbsp;&nbsp;
			      </c:if>

		      </div>
		    </div>
		  </div>
		</div>
		</div>
		<div id="reply-area">
			
			<table class="form-group" align="center">
				<thead>
					<tr>
						<%-- 댓글을 작성하는 영역, 로그인 전후로 보여줄 영역이 다름
						댓글 작성부터 구현할건데, 로그인 전에 보여줄 녀석, 로그인 하고 나서 보여줄 녀석 -> 조건문
						jsp상에서 조건문 쓰려면 선택지가 두개, choose when otherwise 아니면 if
						지금은 choose가 좋겠지? 두개니까
						로그인한 상태는 sessionScope에 담김 userInfo가 null이 아니면 로그인한 상태임
						 --%>
						<th>댓글작성</th>
						
							<c:choose>
								<c:when test="${ sessionScope.userInfo ne null }">
									<td>
										<textarea id="replyContent" cols="50" rows="3" style="resize:none;" class="form-control"></textarea>
									</td>
									<td><button onclick="insertReply();" class="btn" style="width : 100%; height : 100%; background-color: #52b1ff; color : white;">댓글등록</button></td>
									<%-- 이 버튼을 클릭하면 요청이 가도록 만들자 -> 맨 아래에 script 태그에서 작업 --%>
								</c:when>
								<c:otherwise>
									<td>
										<textarea readonly cols="50" rows="3" style="resize:none;" class="form-control">로그인 후 이용가능한 서비스입니다.</textarea>
									</td>
									<td><button class="btn" style="width : 100%; height : 100%; background-color: #52b1ff; color : white;">댓글등록</button></td>
								</c:otherwise>
							</c:choose>
					</tr>
				</thead>
				<tbody>
				<%-- 게시글에 달린 댓글들을 조회하는 영역 --%>
				</tbody>
			</table>
			<br><br><br><br>
	    </div>

	</div>
	
	<jsp:include page="../include/footer.jsp" />
	
	<script>
	
		function insertReply() {
			
			// 댓글작성 요청 -> KH_REPLY 한 행 INSERT
			// 게시글번호, 댓글 내용, 작성자 번호
			// DEFAULT 값 넣을 컬럼들(내용, 게시글 번호, 작성자정보), 게시글번호는 SEQ 이용할 계획
			
			const content = $("#replyContent").val();
			
			content.replaceAll("이승철바보", "사실바보아님"); // 욕설 고치기 등 필터링
			
			// 요청을 보내려면?
			$.ajax({
				
				url : "insert.reply",
				type : "POST", // insert는 post로 가는 것이 좋다
				// get은 url에 노출됨, 전송길이에 제한이 있어 크기가 큰 데이터는 절삭됨, 캐싱되어 메모리에 올라가있으므로 같은 url로 요청되면 재사용됨?
				// 만약에 댓글이 get방식이라면? 나중에 요청이 전송될 때 url에 전부 노출됨
				// 보통은 필터링이 된다, get 방식이라면 내가 url을 고쳐서 요청을 보낸다면 replaceAll을 이용할 수 없게 됨
				// 앞에서 1차 거름망을 한번 할 수 없게 된다, 어차피 2차에서 해야하긴 하지만 기본적으로 insert는 POST
				
				data : {
					
					replyContent : content,
					boardNo : ${map.board.boardNo}
					
				},
				
				success : function(result) {
					
					// console.log(result);
					
					if(result === 'success') {
						$('#replyContent').val(''); // 작성하면 일단 없어지게 하고 댓글 몇개 달기
						selectReply();
					}
					
				}
				
			});
			
		};
		
		$(function() {
			
			selectReply(); // 댓글 작성했을 때도 작성한 댓글 보이도록 호출하기 위해서 따로 분리함
			// 첨부파일처럼 조회를 구현하면 페이지를 새로고침하고 전체 내용을 다시 조회하도록 해야함
			// 네이버 웹툰 같은것도 새로고침 버튼으로 전체를 로드하는게 아니라 댓글만 ajax로 요청 보내서 읽어오게 되어있음
			// 객체 형태로 응답데이터를 만들어서 돌려줌
			// 분리하면 댓글로 따로 조회하는 기능으로 구현해서 새로 댓글을 작성하거나 새로고침하지 않아도 댓글만 새로 가져올 수있음
			// 그러니 뭉치지 않고 분리하는것이 더 나을수도 있음
			
		});
		
		function selectReply() {
			
			$.ajax({
				
				// select해오고 gson으로 뿌려주기
				url : "list.reply",
				type : "get",
				data : {
					boardNo : ${map.board.boardNo} // 게시글 번호를 들고가야 현재 게시글에 달린 댓글을 가져올 수 있음
				},
				
				success : function(result) {
					
					// console.log(result);
					
					const str = result.map(e => `
												 <tr>
													 <td>\${e.replyWriter}</td>
													 <td>\${e.replyContent}</td>
													 <td>\${e.createDate}</td>
												 </tr>
											    `).join('');
					$("tbody").html(str);
					
				}
				
			});
			
		}
	
	</script>
	
	
	
</body>
</html>