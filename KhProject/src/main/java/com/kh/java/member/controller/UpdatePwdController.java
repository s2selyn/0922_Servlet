package com.kh.java.member.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.java.member.model.service.MemberService;
import com.kh.java.member.model.vo.Member;

@WebServlet("/updatePwd.me")
public class UpdatePwdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdatePwdController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1) POST -> 인코딩
		request.setCharacterEncoding("UTF-8");
		
		// 2) 값뽑기
		String userPwd = request.getParameter("userPwd"); // 기존 사용자의 비밀번호
		String updatePwd = request.getParameter("changePwd"); // 사용자가 바꾸고싶은 새 비밀번호

		// 3) 식별할 수 있는 값이 필요함
		// UPDATE MEMBER SET USER_PWD = '새비밀번호'
		//  WHERE USER_PWD = '기존비밀번호
		// 현재 있는 값으로는 회원을 식별할 수 없음
		// USER_NO가 제일 좋은데 여기 없으니 session에서 가져오자
		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute("userInfo");
		Long userNo = member.getUserNo(); // where절에 추가해서 각 행 식별이 가능해짐
		
		// 값이 세개가 있다
		// 어딘가에 담아가고싶다 -> Member? Map? -> Map! Member에는 새 비밀번호를 담을 곳이 없다
		Map<String, String> map = Map.of("userPwd", userPwd,
										 "updatePwd", updatePwd,
										 "userNo", String.valueOf(userNo));
		// 기존 비밀번호, 바꿀 비밀번호, 사용자 식별값(세션에서 뽑음) 세개를 맵에 담아쓰
		
		int result = new MemberService().updatePwd(map);
		
		// -----
		// 비밀번호 바꾸면 로그인 다시 하라고 한다
		// ??? 17:38 지금 비밀번호 바꼈는데 mypage controller에서 세션에서 값을 뽑아가니까 null이기때문?
		if(result > 0) {
			member.setUserPwd(updatePwd);
		}
		// 변경된 비밀번호를 받아오도록 하는 코드 추가
		// -----
		
		session.setAttribute("alertMsg", result > 0 ? "변경 성공 ~ " : "변경 실패...");
		
		// 성공이든 실패든 마이페이지로 응답
		response.sendRedirect(request.getContextPath() + "/myPage");
		/*
		if(result >0) {
			
		} else {
			
			// request.setAttribute("msg", "비밀번호 변경 실패 ㅠ"); 이거 말고 다른 스타일로 해보자
			// 변경 성공 실패 상관없이 마이페이지로 보내자, 마이페이지에서 요청을 보냈으니까
			
		}
		*/
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
