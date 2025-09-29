package com.kh.java.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LogoutController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// a태그로 요청 보내는건 무조건 get방식, post로도 우리는 여기서 작업
		// 로그아웃을 구현해야함
		// 로그아웃이란 뭘까???
		// 로그인이 뭘까, 어떻게 구현했을까 먼저 생각
		// 로그인은 DB에서 사용자가 입력한 인증값과 일치하는것을 찾아서 
		// 우리는 어떤 상태면 로그인인가? 세션의 setAttribute에 userInfo라는것이 비어있지 않으면, 존재한다면 로그인된 상태
		// 로그아웃을 시켜주고싶으면 session의 userInfo라는 속성(attribute)을 null이거나 지우거나
		// 비어있지 않거나 null이 아니면 로그인이 아니다, 방법이 2개일 수 있음
		
		// 1. 지워버리기
		// request.getSession().removeAttribute("userInfo");
		
		// 2. 세션을 만료시키기(session만료시키는 방법 (==무효화한다), 이러면 세션날리기
		// 주의점은 userInfo 말고도 세션에 있는 모든것이 날아간다
		// 로그아웃하면 다 날리는게 맞을수도?
		request.getSession().invalidate();
		// 이 시점에서 할 수 있는 실수 / 잘못 생각할수도 있는 부분
		// 요청 url logout해서 서블릿한테 받음
		// 이 다음에 응답을 해준게 없음, 끝 하고 말았음 클라이언트에게 아무것도 안돌려줘서 아무것도 안나옴
		// 흰 화면 나온거 지금은 이게 이상한게 아니라 당연한것
		
		// 컴퓨터는 멍청하다, 사람이 시킨 일만 한다. 내가 시키지 않은 일은 하지 않은 기계깡통
		// 세션 날린 다음에 여기서는 응답데이터 뭘 줄지 생각해야함
		// 응답페이지 따로 안만들었으니까
		// 응답데이터 => 웰컴파일
		// 포워딩 방식이 있고, 웰컴파일이 바뀔 수 있으니 sendRedirect 방식 배운걸로 해보자
		// 각각 어떤 상황에서 다르게 쓰는지는 이번주에 천천히 배워보고
		// sendRedirect()
		// System.out.println(request.getContextPath());
		response.sendRedirect(request.getContextPath());
		// 클라이언트가 어떻게 웰컴파일을 받을 수 있는지? /kh -> 이러고 테스트 성공!
		
		// 근데 문제가 있음, index 파일도 이름 계속 바꿀 수 있고 context root도 바뀔 수 있음
		// 그럼 또 /kh라고 쓴 부분을 바뀐 내용으로 갱신해야하는 귀찮음
		// redirect 위에 출력문 하나 써보자
		// ??? 10:45 request에는 사용자가 보낸 요청정보, 요청시 전달값, referer가 어디냐, 요청방식, ??? 등이 있는데
		// getContextPath 이런게있음 -> 메소드 호출 반환타입이 String -> "/kh" 대신 사용가능
		// 이제 context root 변경되어도 이 코드를 수정하지 않아도 된다!
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
