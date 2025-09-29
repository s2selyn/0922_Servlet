package com.kh.java.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/join")
public class JoinForwardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public JoinForwardController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 나 할 일 뭐임?? 나 뭐해야됨???
		// 회원가입 양식 띄워주기 쩌기 enroll_form.jsp가 응답하게 만들기
		// 서블릿에서 응답데이터 지정하는 방식 두 가지
		// 1. RequestDispatcher 이용해서 forwarding해주기
		// 2. response 이용해서 sendRedirec(재요청 보내게 하는 방식, url 재요청하도록)
		// 지금 상황에서는 뭘 써야하는지? 선택지가 없음, 앞에서 2번을 사용할 수 있던건 이미 url을 만들어둔 상태였음
		// 지금은 enroll_form.jsp로 갈 수 있는 방법이 없음
		// 파일명에 경로를 제시해서 응답을 보내게 하려면 1번을 써야함
		// 1을 쓰려면 request객체로 만들어내야함, 참조해서 get, 인자로 경로와 파일명, 그리고 참조해서 전달
		request.getRequestDispatcher("/WEB-INF/views/member/enroll_form.jsp")
			   .forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
