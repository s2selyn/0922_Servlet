package com.kh.java.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.java.member.model.dto.MemberDto;
import com.kh.java.member.model.service.MemberService;

@WebServlet("/join")
public class JoinController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public JoinController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		String userName = request.getParameter("userName");
		String email = request.getParameter("email");
		
		// 디버깅코드
		System.out.println("=== 파라미터 확인 ===");
	    System.out.println("userId: [" + userId + "]");
	    System.out.println("userPwd: [" + userPwd + "]");
	    System.out.println("userName: [" + userName + "]");
	    System.out.println("email: [" + email + "]");
	    // 디버깅코드 끝
		
		MemberDto joinMember = new MemberDto();
		joinMember.setUserId(userId);
		joinMember.setUserPwd(userPwd);
		joinMember.setUserName(userName);
		joinMember.setEmail(email);
		
		int result = new MemberService().join(joinMember);
		*/
		// 지금 상태에서 500에러 났음! SQL Syntax Error
		
		// result는 DML 수행이니까 행의 수로 돌아오는데 지금회원가입 한명할거니까 성공하면 1행, 실패하면 0행이 돌아온다
		// result에 따라 Dispatcher 어디로 보낼 지 결정해야함. 지금은 다시 회원가입 페이지로 보내는 상태임.
		request.getRequestDispatcher("/WEB-INF/views/member/join.jsp")
			   .forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// POST 요청만 회원가입 처리
        String userId = request.getParameter("userId");
        String userPwd = request.getParameter("userPwd");
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        
        System.out.println("=== 파라미터 확인 ===");
        System.out.println("userId: [" + userId + "]");
        System.out.println("userPwd: [" + userPwd + "]");
        System.out.println("userName: [" + userName + "]");
        System.out.println("email: [" + email + "]");
        
        MemberDto joinMember = new MemberDto();
        joinMember.setUserId(userId);
        joinMember.setUserPwd(userPwd);
        joinMember.setUserName(userName);
        joinMember.setEmail(email);
        
        int result = new MemberService().join(joinMember);
        
        if(result > 0) {
            // 성공
            response.sendRedirect(request.getContextPath());
        } else {
            // 실패
            request.setAttribute("errorMsg", "회원가입 실패");
            request.getRequestDispatcher("/WEB-INF/views/member/join.jsp")
                   .forward(request, response);
        }
	}

}
