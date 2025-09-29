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

@WebServlet("/update.me")
public class UpdateMemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateMemberController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1) GET ? POST ?
		// 요청방식 생각 -> POST => 인코딩
		request.setCharacterEncoding("UTF-8");
		// 중복인데 안쓸수도없고.. 메소드로 빼서 작업 처리하는데 클래스 하나 생성해서 한줄 빼서 메소드 import하면 이게 더 낭비같은데?
		// 어떻게 하는게 좋을까? 일단 문제가 있음을 인지
		
		// 2) 요청 시 전달 값 뽑기(흔히 뽑는다고 많이 표현함)
		// 키값을 알아야한다, userName, email
		String userName = request.getParameter("userName");
		String email = request.getParameter("email");
		
		// 문제 해결하러 돌아와서 과정을 추가함
		// 2_2) 세션에서 값 뽑기
		HttpSession session = request.getSession();
		// 세션에서 가져올거라면 id보다는 pk가 낫다, 각 행을 식별하는 제약조건이기 때문
		// userNo를 뽑아내고싶다면? userInfo의 값은? setAttribute 하면서 userInfo에 DB에서 조회성공한 member 타입 객체의 주소값을 담음
		// 실제 필요한 userNo는 어디에 있나요?
		Member member = (Member)session.getAttribute("userInfo");
		// 타입캐스팅해서 Object를 Member로 변환
		
		// 필요한것은 Member객체가 아님
		// Member에서 userNo만 빼내야함
		Long userNo = member.getUserNo();
		
		// 한줄로 줄이고싶은데 이상태로는 안됨, Object에는 메소드가 없으니 형변환 추가
		/*
		Long l = ((Member)session.getAttribute("userInfo"))
								 .getUserNo();
		*/
		// ??? 15:15 이상태에서 뭐함?
		
		// 뽑았더니 값이 두개가 넘는다 -> Member에 담을까?
		// 3) 어따담기
		// 오랜만에 map 같은거에 담아볼까? Member에 담는게 맞긴 한데 안써본거 써보고싶음
		// map에 담으려면? map이 있어야겠지? 어케만들어?
		// 자꾸 까먹으니까 다시 해봐야함
		// 나중에 넘어갈 값들이 String이니까 key, value 전부 String으로 generic
		/*
		Map<String, String> map = new HashMap();
		map.put("userName", userName);
		map.put("email", email);
		
		Map.of() : K-V 10개까지 초기화 가능
				   불변맵 반환
		*/
		// setter보다는 편할지도, 순수 VO로 했다고 가정하면 setter가 없었을테니 map을 쓰는게 나쁘지않을지도?
		// key값 쓰는게 더 번거로운가..? 잘 모루겠음
		// HashMap 잘 기억나지 않음
		// Map은 솔직히 이런 식으로 쓴다
		Map<String, String> map = Map.of(
								  "userName", userName,
								  "email", email,
								  "userNo", String.valueOf(userNo));
		// 이러면 key-value 세트 10개까지 넣을 수 있고
		// 반환을 immutable map으로 반환된다, value값이 한번 들어가면 바뀌지 않는다는 보장이 되는 메소드
		// System.out.println(map); 확인해봤음! null pointer exception -> input요소 name 속성값 잘못 적어서 발생했음
		
		// System.out.println(request.getParameter("userId"));
		
		// userNo 타입이 Long이라서 형변환 해야 Map에 넣을 수 있다
		// ??? 15:16 userNo+"" 문자열 더하기라 메모리 낭비
		// ??? 15:26 mapper에서 작업한 내용(sysdate, status)
		
		// 4) 요청처리 -> Service
		// 나중에 뭐가 돌아가야할까? update 했으니까 int? 시행착오를 겪으러 ㄱㄱ
		// 개발자 되는 방법 => 자격증 따기 X
		// 개발자 == 자격 X
		int result = new MemberService().update(map);
		
		// 5) 결과값에 따라서 응답화면 지정
		if(result > 0) {
			
			// ??? 15:40 SQL문 에러 확인하고 설명하는 부분
			
			// 우리에게 생긴 문제점
			
			// Update에 성공했는데
			// session의 userInfo키 값에는
			// 로그인 당시 / 마이페이지 포워딩 당시 조회했던
			// 값들이 필드에 담겨 있기 때문에
			// Update수행 전 값들이 마이페이지에서 출력됨
			
			// session에 값을 담은 시점과 실제 테이블에 반영된 값이 다름
			// 화면에 업데이트값을 보여주려면 테이블에 다녀와서 session의 값을 갱신해야함
			// 목표 => DB가서 갱신된 회원정보 들고오기
			// 	   => 들고온 회원정보 userInfo로 덮어씌우기
			
			// 조회하는거 성공해서 들고오고싶었으면 컨트롤러에서 하는게 아니라 서비스에서 했어야함
			// 반환이 int가 아니라 Member였어야함, 성공했으면 Member를 조회해서 돌려보내고, 실패했으면 null
			// 이게 설계의 중요성, update니까 int에 받아오면 되겠다고 생각했음
			// 하지만 update에 성공하더라도 최종적으로 화면에 보여주고싶은 내용이 있음
			// 우리는 계속 int로 받아서 성공실패여부만 출력했지만 화면이 바뀌었음
			// 성공 시 바뀐 정보, 가입 시 보여줄 정보, 탈퇴했을 때 보낼곳 등 화면상에 보일 값들을 미리 생각하고 작업해야함
			// login 호출하려면 또 id, pwd 있어야하니 map에 없어서 또 뜯어고쳐야하고...
			// 시간이 너무 오래걸리니까
			/*
			Member loginInfo = new MemberService().login(member);
			session.setAttribute("userInfo", loginInfo);
			
			// 성공했을 때 마이페이지로 다시 돌려준다고 가정
			request.getRequestDispatcher("/WEB-INF/views/member/my_page.jsp")
				   .forward(request, response);
			*/
			// 여기 너무 찝찝함 -> MyInfoController에 똑같은 코드가 있음
			// 새로운 사용자의 정보를 담아서 반환해주는 코드 ??? 16:05 를 MyInfoController에서 작성해뒀음
			// 손해다 같은 코드 두번 써서, 동작하는데는 문제가 없지만
			// 나중에 jsp가 바뀔 수 있음, 그때마다 포워딩해주는 코드를 이걸 고쳐줘야함
			// 이미 세션을 덮어써서 가는 코드를 만들어뒀다
			// /my_page 하는 코드가 있음
			// 이런 상황에서 포워딩으로 보내는 것 보다 재요청 방식을 생각해볼 수 있음
			// ??? 16:05 계속 작성과정 설명
			// sendRedirect
			// /kh/myPage
			// 사용자가 다시 요청을 보내도록, context root 변할 수 있으니 String 리터럴 말고
			
			// -----
			session.setAttribute("alertMsg", "정보 변경 성공!");
			// -----
			
			response.sendRedirect(request.getContextPath() + "/myPage");
			
			// 잘됐는데 티가 별로 안나니까 뭔가를 더 할까?
			// 성공했을 때는? 메세지 보내서 alert 띄우기 해보자
			// 실패했을 때 메세지 보내서 결과보여주듯이
			// 메세지 보내려면 attribute에 담아야함
			// attribute 담으려면 scope 정해야함 application, session, request 셋중하나에서 골라야함
			// 실패했을때는 한번 보여주고 말 내용이라 계속 유지될 필요가 없으니 request에 담았음
			// 로그인된 사용자의 정보면? 로그인이 되어있다면 어딜 가든 유지가 되어야함 -> session에 담았다
			// 여기서도 alert 메세지 한번 띄울거고 계속 띄울건 아닌데 개념상으론 request에 담는게 맞음
			// 문제는? 로그인 컨트롤러에 가자
			
		} else {
			
			request.setAttribute("msg", "정보 수정에 실패했습니다.");
			// 결과가 0보다 크지 않다면 앞에 만들어둔 페이지로 포워딩, 중복이긴하네
			request.getRequestDispatcher("/WEB-INF/views/common/result_page.jsp")
				   .forward(request, response);
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
