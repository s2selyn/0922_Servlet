package com.kh.java.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/myPage")
public class MyInfoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MyInfoController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 내정보 조회란 뭘까??
		// 마이페이지에 정보를 출력해줘야할텐데 어떻게 구현하지?
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("userInfo") != null) {
			// 로그인된친구만 포워딩하고싶음, 어딜봐야아나? sessionScope에 있는 값을 뽑아봐야 안다
			// session 객체 얻는 것을 if 상위에 작성
		
			// 일단 응답 오면 포워딩 해준다고 작성
			request.getRequestDispatcher("WEB-INF/views/member/my_page.jsp")
				   .forward(request, response);
		
		} else {
			
			// result_page.jsp로 연결해도 됨
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
