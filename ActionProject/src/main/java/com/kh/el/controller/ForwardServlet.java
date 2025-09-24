package com.kh.el.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/forward.do")
public class ForwardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ForwardServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 얘의 목적도 jsp 만들어서 걔로 포워딩해주고싶은거죠
		// 포워딩할 jsp 먼저 만들자
		// 포워딩해서 forward.jsp가 응답되게하기
		// requestDispatcher가 필요한데 이건 request 참조해서 get으로 얻을 수 있음
		// 메소드 인자로 jsp 경로와 파일명 작성해서 전달하고
		// 참조한 다음 forward 메소드 호출
		// 응답데이터 니가 만들어라 하고 전달
		request.getRequestDispatcher("/WEB-INF/views/forward.jsp")
			   .forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
