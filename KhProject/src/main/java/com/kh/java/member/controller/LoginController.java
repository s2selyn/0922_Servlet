package com.kh.java.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
