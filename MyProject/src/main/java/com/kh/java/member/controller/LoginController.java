package com.kh.java.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.java.member.model.service.MemberService;
import com.kh.java.member.model.vo.Member;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		
		Member member = new Member();
		member.setUserId(userId);
		member.setUserPwd(userPwd);
		
		Member loginMember = new MemberService().login(member);
		
		if(loginMember != null) {
			
			HttpSession session = request.getSession();
			
			session.setAttribute("userInfo", loginMember);
			
			request.getRequestDispatcher("/index.jsp")
				   .forward(request, response);
			
		} else {
		
			request.setAttribute("msg", "로그인에 실패했습니다."); // 여기가 스텝 1
			
			request.getRequestDispatcher("/WEB-INF/views/common/result_page.jsp")
				   .forward(request, response); // 여기가 스텝 2
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
