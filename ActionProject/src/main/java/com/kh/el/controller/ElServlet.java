package com.kh.el.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.el.model.vo.Person;

@WebServlet("/el.do")
public class ElServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ElServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 만약 서블릿에서 응답데이터가 존재했다면,
		// JSP로 포워딩할 때 request객체에 담아서 보내줬음! --> Servlet내장객체 / Scope객체
		// 서블릿 내장객체 9개중에 request가 하나
		// 살면서 9개 다보기는..? 4가지정도는 볼테고 2가지는 꼭 알아야함, 4가지 얘기를 해보자
		
		// application / session / request / page
		// 보통은 이 뒤에 scope를 붙여서 말함
		// 얘네를 서블릿에서 쓸수도 있고, jsp도 결국 서블릿으로 변환되기 때문에 jsp에서도 쓸 수 있다
		// 범위가 큰 순서부터 적어봄, 순수 자바에서 다루면 자료형, 클래스가 정해짐(자바타입)
		/*
		 * 1. application scope : ServletContext
		 * 한 애플리케이션 당(지금 우리로 따지면 하나의 프로젝트로 이해), 단 한 개 존재하는 객체
		 * 이 영역에 데이터를 담으면 애플리케이션 전역에서 사용 가능
		 * ??? 12:13 어디서 활용가능?
		 * 
		 * 2. session scope 	: HttpSession
		 * 하나의 브라우저 당, 한 개 존재하는 객체
		 * 세션이 다양한 의미로 사용되는데, end point끼리 연결되어있다면 그런의미로 사용
		 * 예를들어 브라우저 사용 시 첫 탭에서 네이버에 로그인을 하고, 두번째 탭을 켜보면 로그인된 상태임
		 * 네이버가 사용하는 로그인방식은 나중에 배우긴할건데 다름
		 * 일단 이런 식으로 동작하는건 세션스코프에 담아두면 로그인된 상태가 브라우저상에서 공유되어 사용되고 있는것
		 * 이 영역에 데이터를 담으면 JSP/Servlet단에서 사용 가능
		 * 값이 한 번 담기면 서버를 멈추거나, 세션을 비우거나, 브라우저가 닫히기 전까지는 사용 가능
		 * 정확이는 이 방식으로 로그인을 구현한다면 쿠키를 같이 사용해야함
		 * 우리는 세션방식 인증 구현하고 나중에 토큰방식으로 구현할거임(두가지 방식의 인증인가절차 써볼거)
		 * 
		 * 3. request scope		: HttpServletRequest
		 * 요청 및 응답 시 매 번 생성되는 객체
		 * request는 사용자가 뭔가 요청을 해야만 생기는 객체
		 * 소멸은 서블릿이 클라이언트에 응답을 해주고 나면 request, response가 함께 소멸
		 * 메소드에서 끝나면 죽는다, 지역변수니까 더이상 사용못함
		 * 한번 요청에 대한 응답이 끝나면 끝! 1회성
		 * 이 영역에 데이터를 담으면 해당 request객체를 포워딩 받는 응답 JSP에서만 사용가능(1회성)
		 * setAttribute해서 담은 데이터가 유지되지 않고 응답 보내면 끝남
		 * 
		 * 4. page scope		: PageContext
		 * JSP페이지 내에서만 사용 가능
		 * 
		 * 4번은 좀 섭섭하니 껴주고 2, 3을 실질적으로 많이 사용함(request, session)
		 * SqlSession과는 전혀 연관이 없슴다, SqlSession은 DB와의 연결이고
		 * 여기 session은 서버 통신연결과 관련된 http session임
		 * 뒤에 또 session 나오는데 각 session들은 서로 연관없고 전혀 다른 친구들
		 * 
		 * => 위 객체들에 속성을 추가할 때는 .setAttribute("키", 밸류);
		 * 							  .getAttribute("키");
		 * 							  .removeAttribute("키");
		 * 이름 다 다르고 타입도 다른데 사용법은 동일함, 값 추가, 값 뽑기, 값 삭제
		 * ??? 12:21 jsp에선 서블릿에 담은 값 출력이 목적일테니까 여기서 뭔가를 하고...?
		 * 
		 */
		
		// requestScope
		// 속성을 추가하고싶으면 request 객체 참조해서 setAttribute, 첫번째 인자는 반드시 스트링 타입으로 key값
		request.setAttribute("classRoom", "A강의장");
		
		// VO하나 만들어서 해보자 -> Person 생성하고왔음
		request.setAttribute("student", new Person("홍길동", 15, "한양"));
		
		// sessionScope
		// 여기도 속성 추가해보자
		HttpSession session = request.getSession();
		// 현재 요청한 사용자와의 세션을 얻어올 때는 request에서 getter로 얻어온다
		// Attribute 추가는 방법이 다 같으니까 session 참조해서 setAttribute
		session.setAttribute("academy", "KH 아카데미");
		session.setAttribute("lecture", new Person("고길동", 40, "마포"));
		// request, session스코프에 값담아봤따
		
		// 응답 뷰 배정 -> 포워딩
		// 배정하려면 requestDispatcher 필요, 인자값으로 응답 파일로 사용할 jsp경로를 전달
		// webapp에서 시작하니 슬래시부터 파일명까지 작성
		request.getRequestDispatcher("/WEB-INF/views/01_el.jsp")
			   .forward(request, response);
		// 이게 어려우면 응답으로 쓸 녀석을 우클릭, properties 확인하면 path가 이렇게 /ActionProject/src/main/webapp/WEB-INF/views/01_el.jsp
		// 그럼 webapp 다음의 /부터 복사해서 붙여넣어버려, 이게 더 안전할수도, 오타 상관없게됨
		// 만든걸로 포워딩해줘야함, 참조해서 forward메소드 호출
		// 이제 01_el.jsp 가서 작업!
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
