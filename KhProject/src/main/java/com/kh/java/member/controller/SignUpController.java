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

@WebServlet("/members")
public class SignUpController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SignUpController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 작업은 여기에서 한다!
		// 처음 생각해야 할 점 : get/post중 어떤 방식?
		// 비밀번호 포함된 중요한 정보로 post로 보냈음
		// POST
		// 1) 인코딩 설정
		request.setCharacterEncoding("UTF-8");
		
		// 2) request객체로부터 요청 시 전달값 받기 / 뽑기 / 가져오기
		// 4개 줄게 회원가입시켜줘 insert해줘~
		// 키값은 input요소의 name 속성값, 변수로 뽑아두자
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		String userName = request.getParameter("userName");
		String email = request.getParameter("email");
		// 앞에서 넘긴 값 네개 빼온것이다!
		// 컬럼에 제약조건 어떻게 달았는지 꼼꼼히 생각해야함, not null이면 무조건 값이 있어야함, 이걸 서비스단에서 검증해야함
		// 컨트롤러에서 체크가 가능하다면 좋겠다, not null, unique 있는데 값이 없다면 가공해서 서비스 넘길 필요가 없음, 아무의미없는데 굳이 왜넘겨요
		// 어차피 서비스에서 유효성검사를 해야하니 중복이 될 수 있어서 굳이 여기서 하지 않는걸로
		
		// 넘겨야 할 값이 네개인데 이걸 어디까지 들고가야함? DAO(DB가는건 마이바티스가 해주잖아여)
		// 네개 다 안들고가고 이쁘게해서 갈것이다
		// 3) 이쁘게 이쁘게
		// member 테이블에 한 행 insert 할 값이니까 member VO에 담겠지(사실은 DTO에 담는것이 맞다)
		// 이렇게 담아서 서비스에서 VO로 바꾸는거 시간 오래걸리니 생략하고 다음 챕터에서 하자
		Member member = new Member();
		member.setUserId(userId);
		member.setUserPwd(userPwd);
		member.setUserName(userName);
		member.setEmail(email);
		
		// 4) 서비스로 간다...?(요청 처리)
		// 나중에 응답이 어떻게 올까? 작업해놔야 서비스에서 메소드 반환타입쓰고 DAO에서도 쓴다
		// insert하는거니까 sqlSession이 처리된 행의 개수를 반환할것임, 보통 그것을 int로 사용
		// insert하려면 사용자가 입력한 값이 있어야하니 인자값으로 전달
		int result = new MemberService().signUp(member);
		
		// 서비스에 다녀와서 해야 할 일
		// 5) 회원가입 성공했는지 안했는지에 따라서
		// 	  응답화면을 다르게 지정
		// 성공실패여부에 따라 다르게 할거니까 if-else
		if(result > 0) { // 성공
			
			HttpSession session = request.getSession();
			session.setAttribute("alertMsg", "회원가입 성공 ~ !");
			
			// 응답 페이지를 보내줘야하는데 딱히 없다 로그인 페이지를 모달에 해둬서.. 따로 만들었다면 거기로 보내면 됨
			// 웰컴파일말고 보낼데가 없네? 여기 보내려면 sendRedirect, /kh로 작성하면 바뀔 수 있으니 request 객체에서 context root를 얻도록 작성
			response.sendRedirect(request.getContextPath());
			
		} else { // 실패
			
			// 실패 페이지로 포워딩하기로 했음, 그전에 메세지만
			// msg라는 key로 requestScope에 attribute로 set해줘서 보내기로 했음
			request.setAttribute("msg", "회원가입 실패 ~ 약오르지 메롱~");
			
			// 포워딩해야함, 경로를 requestDispatcher안에 넣어줘야하는데 이건 request에 참조해서 get
			request.getRequestDispatcher("/WEB-INF/views/common/result_page.jsp")
				   .forward(request, response);
			
			// 여기 지금 예외처리 안해서 이곳으로 들어올 수 없음, 그냥 500에러 난다
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
