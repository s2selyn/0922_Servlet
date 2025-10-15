package com.kh.java.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.java.member.model.service.MemberService;
import com.kh.java.member.model.vo.Member;

@WebServlet("/ajax2.do")
public class AjaxController2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AjaxController2() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 작업은 여기서 할건데 post방식으로 요청을 보냈음
		// POST => 인코딩
		request.setCharacterEncoding("UTF-8");
		
		// 값 뽑기
		String id = request.getParameter("id"); // 인자값에 적어야 하는 것은? data 속성의 속성값으로 있는 객체 안에서 key-value 형태로 가진 값 중에 : 앞의 key값
		String pwd = request.getParameter("pwd");
		
		// 만들어둔 로그인 기능 사용하자 -> 로그인 메소드 호출할건데 그러려면 하나에 담아야함 -> Member에 담음, 생성자 없으니 setter로 사용
		Member member = new Member();
		member.setUserId(id);
		member.setUserPwd(pwd);
		
		Member loginMember = new MemberService().login(member);
		// System.out.println(loginMember); 잘 받아오는지 확인
		
		// 하나만 보내는건 쉽다
		String name = loginMember.getUserName();
		
		// 두개를 보내려면 이렇게
		String email = loginMember.getEmail();
		
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(name); // print는 인자를 하나밖에 못받음
		response.getWriter().print(email); // 그러니까 또 따로 담아봄
		
		/*
		 * 옛날방식
		 * response.setContentType("text/xml; charset=UTF-8");
		 * response.getWriter().print("<name>" + name + "</name>");
		 * response.getWriter().print("<email>" + email + "</email>");
		 * 
		 */
		
		// AJAX를 활용하는데 실제 값을 여러 개 응답하고 싶음
		// JSON(오늘날의 웹표준) 형태로 데이터를 가공해서 앞단으로 응답해주기
		// 응답하는곳에서 JS로 가공해서 쓸거면 처음부터 보낼 때 자바스크립트 객체모양으로 보내면 편하겠네?
		// 그렇게 보내면 브라우저에서 자바스크립트 이용해서 속성에 접근하면 value값을 얻을 수 있으니까
		// 진짜 객체를 보내는게 아니고 앞단에서 JS처럼 사용하기 위해서 JS객체 모양의 문자열을 보내는것
		// 지금 여기 백엔드는 자바코드 쓰는곳이라 JS객체 만들 수 없음
		
		// 자바스크립트에서 여러개의 데이터를 다룰 두가지 형태
		// 1. 배열
		// 2. 객체
		
		// [name, email]
		// ['홍길동2', 'hong@kh.com'] <- 데이터를 이렇게 만들어서 보내고싶다
		String responseData = "['" + name + "','" + email + "']"; // 자바에서 문자열 만들기(자바스크립트 배열 모양의 문자열)
		// System.out.println(responseData); 잘 출력되나 확인
		// 배열도 객체임, Object 타입의 객체
		
		// JSON 형태로 보내주고싶다면?
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(responseData);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
