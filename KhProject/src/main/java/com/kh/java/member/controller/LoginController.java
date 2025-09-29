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
		
		// 3) 그러면 이제 뭐 해야할까요?(요청 처리)
		Member loginMember = new MemberService().login(member);
		// 메소드 이름만 봐도 알수있도록 작성
		// select 조건으로 사용할 인자값 전달, id, pwd를 담은 member를 전달
		// MemberService 가서 작업
		// 그리고 돌아오는 값을 변수로 받는다 -> Member loginMember
		
		// 성공했을 경우 : 조회성공한 컬럼값을 필드에 담은(컬럼값을 필드에 담은) 멤버객체 주소값
		// 실패했을 경우 : null값
		// System.out.println(loginMember);
		// 잘됐다면 내가 toString 오버라이딩 해놨으니까 필드에 있는 값 나와야하고, 잘못된 값 넣어서 실패했다면 null값이 나오는지 테스트
		// 성공실패 경우 전부 체크하고 다음으로 넘어간다
		
		// 이제 바뀐걸 해보자~
		// 4) 응답화면 지정
		// 스텝 1. request속성에 출력할 값 추가 => setAttribute()
		// 스텝 2. RequestDispatcher를 만들어서 => 뷰 지정(어떤 jsp 쓸건지)
		// 스텝 3. RequestDispatcher이용해서 forward()호출
		
		// 로그인에 성공할 수 도있음 / 실패할 수 도있음
		// 2. 성공실패 여부에 따라서 응답 페이지 다르게 보내주기
		if(loginMember != null) {
			// 로그인에 성공했따!!
			
			// 사용자의 정보를 앞단에 넘기기
			// 로그인한 회원의정보를 로그아웃하거나
			// 브라우저를 종료하기 전까지는 계속해서 유지하고 사용할 것이기 때문에
			// sessionScope에 담기
			
			// 할말 많은데 일단 세션에 담는것부터
			// 스텝 1. session의 Attribute로 사용자 정보 추가
			// session의 타입 : HttpSession
			// => 현재 요청 보내는 Client의 Session : HttpServletRequest request에 있음, 여기서 session을 얻는 방법은 request.getSession();
			HttpSession session = request.getSession();
			
			// attribute 추가방법은 참조해서 setAttribute
			session.setAttribute("userInfo", loginMember);
			
			// 로그인 성공 페이지는 따로 안만들었기 때문에 웰컴파일 다시 보여주는걸로 가정하자.
			// 스텝 2. RequestDispatcher get해오기, 인자로 웰컴파일 경로 작성(webapp 바로 아래니까 / 적고 바로 파일명), forward 메소드 호출해서 전달
			request.getRequestDispatcher("/index.jsp")
				   .forward(request, response);
			
			// 로그인 했는데 로그인이랑 회원가입은 로그인전에 보여야할것같고
			// 내정보랑 로그아웃은 로그인하면 보여야할것같은데
			// 한 화면에 존재하고, 변화없으니 위화감이 든다
			// 그럼 로그인 전에 보고싶은거, 로그인 후에 보고싶은게 다름 -> 조건
			// 보이는 부분에 대한 조건은 헤더에 적어놨으니까 헤더를 수정해야함 -> header.jsp
			
		} else {
		
			// 성공실패에 따라서 화면지정하려면 if-else 해서 각각 써줘야함, 따로따로 작업하자 -> 나중에 조건문으로 분리
			// 실패했다고 가정하고 작업, 실패용 페이지 따로 만들기, 만약에 로그인페이지 따로 만들었다면 거기로 이동하면 되는데 모달로 만들었기 때문에 페이지 따로 추가
			// 실패했따!
			// msg라는 키값으로 결과페이지에 포워딩해야함, result_page.jsp에서 정하고왔음
			request.setAttribute("msg", "로그인에 실패했습니다."); // 여기가 스텝 1
			// 메세지 자체는 보내기 나름
			
			request.getRequestDispatcher("/WEB-INF/views/common/result_page.jsp")
				   .forward(request, response); // 여기가 스텝 2
			
		}
		// 실패했을 때 request에 담아서 보낸다, 근데 로그인에 실패했습니다. 를 다른곳에서 사용할 일이 있을까?
		// 한번 보여주고 쓰면 끝
		// 만약에 로그인된 사용자의 정보를 request에 이런식으로 담아서 보내면 어떻게될까?
		// 네이버에서 봤듯이 로그인하면 사용자정보가 나오는데, request에 담아서 보냈다면?
		// 다른 요청을 보냈을 때 request에 더이상 존재하지 않는다, 계속 로그인된 상태를 유지못하고 한번 보여주고 끝나버림
		// request는 사라지니까!
		// 보편적으로 로그인은 내가 로그아웃하거나 브라우저를 끄기 전까지는 계속 로그인된 상태가 유지되고 상태를 확인할 수 있다
		// 그럼 request에 담으면 안될것같은데?
		
		// 어제복습
		// 1. 로그인된 사용자의 정보를 어딘가에 담을 것(application, session, request, page)
		// 선택지 네개
		/*
		 * 크다
		 * 1) application : 웹 애플리케이션 전역에서 사용 가능
		 * 					(일반 자바 클래스에서 값을 사용할 수 있음)
		 * 여기 담으면 서블릿이고 jsp고 상관없음, Service, VO, DAO에서도 가져다 쓸 수 있음
		 * 
		 * 2) session : 모든 JSP와 Servlet에서 꺼내 쓸 수 있음
		 * 				단, session에 값이 지워지기 전까지
		 * 				세션 종료 시점 : 브라우저 종료, 서버 멈춤, 코드로 지움(보통 로그아웃 기능 구현)
		 * 
		 * 3) request : 해당 request를 포워딩한 응답 JSP에서만 쓸 수 있음
		 * 				요청 부터 응답까지만 딱 쓸 수 있음
		 * 
		 * 4) page : 값을 담은 JSP에서만 쓸 수 있음
		 * 작다
		 * 
		 * => session, request를 많이 사용함
		 * 
		 */
		// 지금은 request에 담아서 보내면 한번쓰고 땡, 다른 페이지(요청)에서는 성공정보가 남아있지않음
		// 세션을 이용한 로그인 방식은.. ㅋㅋ 세션에 담습니다
		// ??? 17:08 그럼 여기 있으면 JSP, Servlet 모두 사용 가능
		// 아무튼 우리가 이번에 구현할건 세션 방식의 로그인(다른 방식도 있다)
		// 정확히 표현하면 세션 및 쿠키를 이용한 방식인데 아직 쿠키 안배웠으니 세션만 써보자
		
		// 성공했다고 가정하고 작업
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
