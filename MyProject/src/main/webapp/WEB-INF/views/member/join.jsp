<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>나의 프로젝트의 회원가입 페이지</title>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.min.js"></script>
</head>
<body>

	<%-- 회원가입 페이지에서 입력받을 내용 : userId, userPwd, userName, email --%>
	
	<%-- 폼태그 내부에 인풋요소 4개, 가입버튼, 초기화버튼, 취소버튼 --%>
	<form>
	
		<fieldset>
		
			사용할 아이디 입력 : <input type="text" required /> <br>
			
			<label for="inputPassword5" class="form-label">사용할 비밀번호 입력 : </label>
			<input type="password" id="inputPassword5" class="form-control" required /> <br>
			
			이름 입력 : <input type="text" required /> <br>
			
			<label for="exampleFormControlInput1" class="form-label">이메일 입력 : </label>
			<input type="email" class="form-control" id="exampleFormControlInput1" placeholder="name@example.com" required />
			
		</fieldset>
		
		<input type="submit" /> <br>
		<input type="reset" /> <br>
		<button id="cancel">취소</button> <br>
	</form>
	
	<script>
	
		const btn1 = document.getElementById("cancel");
		
		btn1.onclick = function () {
			// 취소버튼 클릭 시 하고싶은 일
			// 확인창 출력
			// 확인하면 메인으로 돌아가기
			// 취소하면 하던 작업 계속하기(확인창만 닫음)
			alert("취소눌렀니?");
		};
	
	</script>
	
	<%-- 정규표현식으로 프론트엔드 검증절차 -> 유효성 검사 함수 호출 -> 반환값 이용해서 가입절차 진행 또는 재입력 --%>
	
	<%-- 아이디 입력하고 사용할 수 있는 아이디인지 검증
	-> 양식에 맞는지? 여기서는 정규표현식?
	-> 사용중인 아이디인지? 이건 SELECT 해봐야하지않나? --%>

</body>
</html>