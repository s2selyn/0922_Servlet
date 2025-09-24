package com.kh.el.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/include.do")
public class IncludeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public IncludeServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 이 서블릿의 생성목적, 이유, a태그 클릭 시 jsp가 응답하도록 하고싶은데 직접접근이 불가능하므로
		// ??? 15:46 jsp에게 보내줄 수 있도록 requestDispatcher 필요
		// Forwarding
		// 뭐가 있어야 forward가능? requestDispatcher, 이건 request로 만들 수 있다
		// 누구한테 배정할건지 문자열값으로 인자전달할 때 경로를 붙인 파일명으로 작성, webapp 다음의 슬래시부터 작성
		// 어떤 파일에 배정할건지 속성에서 path 복사해올 수 있음
		request.getRequestDispatcher("/WEB-INF/views/include.jsp")
			   .forward(request, response);
		// 작업 저장해야 컴파일 새로하고 서버 껐다켜야 컴파일 새로한게 반영되고 새로고쳐야 서버에서 바뀐내용을 받아온다
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
