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

@WebServlet("/myPage")
public class MyInfoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MyInfoController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 내정보 조회란 뭘까??
		// 마이페이지에 정보를 출력해줘야할텐데 어떻게 구현하지?
		// 마이페이지 포워딩 작성 -> 조건문으로 분리 -> login 메소드 호출해서 비교하는 부분 추가 -> if문 조건식 수정
		
		HttpSession session = request.getSession();
		
		// 만약에 로그인하고나서 마이페이지로 이동하는데, 그 이전에 무슨 일이 일어났다면?
		// 관리자가 회원 정지시켰다면? 친구가 장난친다고 다른 컴퓨터로 로그인해서 회원 탈퇴했다면?
		// 그러면 마이페이지를 사용할 수 없어야함
		// 로그인 후, 마이페이지 이용 전 그사이에 회원의 상태가 변화되었다면..!
		// 만약에 데이터값이 변화되었다면 변동된 내용으로 갱신해서 변경된 내용을 출력해줘야함
		// 데이터가 변경되었다면 최신 내용은 DB에 반영되었을것이고, 그 내용은 select해서 조회해와야 한다는 뜻
		// getAttribute 내용을 분리하러 세션 아래에 작성
		
		Member member = (Member)session.getAttribute("userInfo");
		// 이렇게 하면 사용자 아이디/비밀번호가 member에 들어가있겠지
		
		// 우리에겐 잘 만들어둔 바퀴가 있다, 사용자의 아이디/비밀번호로 정보를 조회해올 수 있는 바퀴 -> 로그인
		Member userInfo = new MemberService().login(member);
		// ??? 14:13 login 메소드의 역할
		// status 컬럼값까지 알 수 있다
		// 최신의 정보가 들어있게 됨!
		
		if(userInfo != null) { // ??? 14:13 조건식 변경
			// 로그인된친구만 포워딩하고싶음, 어딜봐야아나? sessionScope에 있는 값을 뽑아봐야 안다
			// session 객체 얻는 것을 if 상위에 작성
			
			session.setAttribute("userInfo", userInfo);
			// ??? 14:13 수정 후 요약
		
			// 일단 응답 오면 포워딩 해준다고 작성
			request.getRequestDispatcher("/WEB-INF/views/member/my_page.jsp")
				   .forward(request, response);
		
		} else {
			
			// result_page.jsp로 연결해도 됨
			// null 이면 로그인을 하든지 시켜야겠지
			request.setAttribute("msg", "로그인 다시하셈 ㅋ");
			request.getRequestDispatcher("/WEB-INF/views/common/result_page.jsp")
				   .forward(request, response);
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
