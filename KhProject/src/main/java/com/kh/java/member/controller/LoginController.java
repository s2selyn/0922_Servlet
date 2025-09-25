package com.kh.java.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.java.member.model.service.MemberService;
import com.kh.java.member.model.vo.Member;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*
		 * <HttpServletRequest, HttpServletResponse>
		 * 
		 * - request : 서버로 요청 보낼 때의 정보(요청 시 전달값(parameter), 요청 전송방식, 사용자정보)
		 * - response : 요청에 대한 응답데이터를 만들 때 사용
		 * 
		 * 값 == data(쓸데없는것도 포함됨)
		 * 정보 == data 중에서 의미가 있는 값들
		 * 
		 */
		
		// 얘가 컨트롤러 역할을 하면서 절차가 한두개 늘어남
		// 웬만하면 데이터 가공은 뷰에서 하지마시고 컨트롤러에서 하세요 라고 이렇게 했었지, 수업시간에도 뷰에서 그런작업을 안하려고 회피함
		// 일일히 매개변수 하나씩 해서 다 넘기고 컨트롤러에서 가공하는 작업을 했음
		// 뷰에서 가공해서 만들수가 없음, member vo를 만들 수 없어, 값이 따로 들어오기때문에 여기서 가공해야하니까
		// 이런 환경에 미리 맞춰서 작업한것
		// 이거 하면서도 그런 부분이 많다, 이다음, 다다음것들이 있는데 거기서는 그렇게 할수밖에없는 정해진 기술이 있음
		// 각 클래스, 메소드에서 어떤 작업, 절차, 전체적인 얼개, 흐름에 집중
		
		// 아무튼 여기에 들어왔다면 제일 먼저 생각해야 할 것은?
		// 절차
		// 1) GET? POST? => 요청 방식이 POST방식이라면 인코딩 작업
		request.setCharacterEncoding("UTF-8");
		// 이건 항상 값을 뽑아내기 전에 해줘야한다. getParameter 이전에!
		
		// 2) 요청 시 전달값이 있나? 없나? => POST는 무조건 해야함
		// 근데 POST방식은 무조건 이걸 하겠지, 전달값이 없는데 POST로 보낼리가 없음, 값을 보내는데 외부에 보이면 안되니까 POST로 보내는거지
		// 포스트는 일반적으로 DB SQL 작업에서 insert할때 사용하는데
		// 로그인은 예외, 인증은 좀 특별한 느낌, 얘는 SELECT 하는데도 post방식
		// 아무튼 값을 뽑아내야하는데, 현재 배운거 두개
		// 언급만 하고 지나간 기술 reflection인가?
		// request.getParameter("키값") : String
		// request.getParameterValues("키값") : String[]
		// 체크박스 여러개 있었는데 제외했고 getParameter로 해볼것임
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		// 앞단에서 인풋요소로 넣은거 name속성값을 인자로 전달
		
		// 값이 잘 넘어왔는지 출력해보자
		// System.out.println(userId);
		// System.out.println(userPwd);
		// 출력안해보고 확인방법이 있는데 초반엔 이게 좋음
		// 자바코드 수정하면 컴파일 다시 한 내용 서버가 다시 읽어야하니까 서버재부팅하고 그뒤에 로그인테스트
		
		// 이렇게 확인했는데 null값이 출력되는 경우? null일 수 있는 상황은 100% 키값을 못찾았다는 뜻
		// userId나 userPwd라는 키값으로 넘어온 값이 없다는 뜻, 그외에는 없어잉
		// 폼태그 안거치고 url 작동하면 null이 온다, 키값없이 가는것이므로
		// 지금 상황에서 null일 경우는 가장 의심할 수 있는 첫번째 원인은 로그인 페이지에 내가 name 속성 적은것이 갱신안된것(새로고침안해서 데이터를 갱신안했다는 뜻)
		// 일단 이걸 새로고침해서 잘나오면 코드상에는 문제없는것
		// 컴퓨터니까 결과를 뽑아낼 때 원인이 없을 수 없음
		// ??? 15:10 문제 발생 시 모든 상황에 대한 정답이 있음, 이걸 익히는게 중요함
		// 회사에서 할 일은 거기서 생기는 문제 고치는거고, 만드는건 도움받아서 다 할 수 있음, 회사 코드 참고해서 만들고
		// 사고방식, 문제접근방법 이런것들을 키우자
		// 문제가 생기면 좋아~ 해결방법을 모르겠다면 무언가를 습득할 기회인거지
		
		// 값이 넘어오는지 잘 확인했으니 두개의 값을 어디 하나에 담아가자
		// VO에 담기로 하고, Member VO 작성하러감
		
		// VO 작성하고 왔다
		Member member = new Member(); // 방금 만든 vo로 import 주의해서 할것
		member.setUserId(userId);
		member.setUserPwd(userPwd);
		// 데이터 가공완
		
		// 그러면 이제 뭐 해야할까요?(요청 처리)
		new MemberService().login(member);
		// 메소드 이름만 봐도 알수있도록 작성
		// select 조건으로 사용할 인자값 전달, id, pwd를 담은 member를 전달
		// MemberService 가서 작업
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
