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

@WebServlet("/delete.me")
public class DeleteMemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DeleteMemberController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 이번엔 회원 탈퇴닷
		// 앞에서 CRUD, DB작업했음
		// view가 바뀌어서 생각할게 많아졌다
		// 회원 탈퇴란 뭘까?
		
		// 1. STATUS컬럼으로 탈퇴여부를 지정 N으로 바꿔줘야지
		
		// 2. 회원(행)을 식별할 식별값이 있어야겠네
		// N으로 바꿀 때 앞에서 넘어오는 값이 비밀번호값 뿐이라서, 이걸로만 가능?
		// 비밀번호만 조건 달면 동일한 비밀번호인 사람들 정보가 다 삭제된다
		
		// 3. 탈퇴에 성공했다면 로그인 => 로그아웃
		//    마이페이지 => 웰컴파일
		// 식별값으로 회원탈퇴해 성공했다고 가정
		// 컨트롤러는 성공실패 여부로 응답화면 지정해야함
		// 실패했다면 실패페이지로 보내고
		// 탈퇴에 성공했다면? 마이페이지로 다시오는건 안됨 좀 이상함
		// 마이페이지는 로그인된 상태여야 올 수 있음, 탈퇴는 자동으로 로그아웃이 되야겠따
		// 마이페이지에서 탈퇴 성공하면 웰컴파일로 보내자
		
		// 현재 탈퇴 요청을 보낸 회원의 정보!!!
		// 물론 앞에서 넘길수도 있음, 직접 사용한건 아니지만 input type="hidden" name="userNo" value="${ userInfo.userNo }"
		// 이런걸로 html 요소에 값을 포함시켜 보낼 수 있다, post 방식이라 url에 찍히지도 않음
		// 몰래 보낼 수 있지만 문제는 악의적 사용자가 html 값을 마음대로 수정이 가능함
		// 관리자 번호 알아내서 보낸다거나 할 수 있겠지
		// 아무튼 이런 방법이 존재하지만 일반적으로는
		// session에서 뽑아오는것이 안정적
		// session에서 뽑아오려면 request로 session을 얻어야함
		HttpSession session = request.getSession();
		// 변수로 두고 나중에 성공했을 때 session에 담아서 메세지 보내려고 함
		
		// 요청방식 get / post 생각, incoding 해줘야함
		request.setCharacterEncoding("UTF-8");
		// 비밀번호 전부 영숫자로 올거라 필요없지만 혹시모르니 해둬서 손해볼건없음 자원소모가 아니니까
		
		// 앞단에서 사용자가 입력한 비밀번호 값을 뽑아와야함
		// 메소드 호출하면서 인자값으로 키값 전달
		String userPwd = request.getParameter("userPwd");
		
		// 현재 요청 보낸 사용자를 식별할 수 있는 값이 필요
		// Session에서 가져와야함
		Long userNo = ((Member)session.getAttribute("userInfo")).getUserNo();
		
		// 값이 두개니까 서비스 넘어가기전에 어디 하나에 담을 선택을 해야함
		// VO / Map? Member에 담아보자
		Member member = new Member();
		member.setUserPwd(userPwd);
		member.setUserNo(userNo);
		
		// 요청처리는 서비스에게, 나중에 올 결과는...? 3번을 생각해서 출력해줄 무언가는 없는 것 같고, 세션에 담아서 성공실패를 보여주면 될것같고
		// 따로 조회해올 값은 없을 듯 -> 이럴때는 그냥 int로 받아도 되겠다
		int result = new MemberService().delete(member);
		
		if(result > 0) {
			
		} else {
			
			request.setAttribute("msg", "비밀번호를 확인하세요!");
			// 지금 경우에는 예외가 발생하지 않는다면 비밀번호가 잘못되었을 수밖에 없음
			request.getRequestDispatcher("/WEB-INF/views/common/result_page.jsp");
			// 얘도 지금 똑같은 페이지에 보내는 코드 계속 중복이라 참기가 힘들군
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
