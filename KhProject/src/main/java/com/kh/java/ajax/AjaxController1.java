package com.kh.java.ajax;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ajax1.do")
public class AjaxController1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AjaxController1() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// System.out.println("doGet메소드 호출!!");
		// 요청보내기에 의해 이 메소드가 호출되고 이클립스에서 출력문이 출력됨
		// 브라우저에서도 응답받을 곳이 생겼으므로 요청이 잘 전달되므로 success와 complete가 호출된다
		// 차이점은? 동기식이라면 서버가 아무것도 돌려주지 않으므로 띄울게 없으니 흰 화면으로 변경됨 -> location.href 주석 풀어서 확인하면됨
		// ajax, 비동기식 요청으로 보내면 화면에는 아무일도 일어나지 않음
		
		// 요청 시 전달값이 있을 때는??
		// 값 뽑기
		// request.getParameter()
		String value = request.getParameter("value"); // form태그였다면 input요소의 name속성값을 적는데, ajax로 보낸 요청이므로 jQuery로 선택한것의 속성명을 작성 -> 지금은 value로 작성했으므로 value
		System.out.println("요청 시 전달값 : " + value);
		
		// 비즈니스로직 처리~~~
		// 잘했음 요청처리 잘함
		
		// 응~~~~~답~~ -> 내가 원래 응답 어떻게 했지? 라고 생각
		// 응답할 값이 있다 == scope.setAttribute("키", "밸류");
		// 최종적으로 서버가 클라이언트(사용자)에게 보내야 할 것은 브라우저가 파싱해야 할 전체 HTML + CSS + JS를 만들어서 보내야함
		// 그건 여기서 만드는게 아니라 jsp로 만드니까 jsp로 포워딩을 해줌
		// 응답화면을 만들어낼 JSP로 포워딩(지금은 응답을 포워딩할게 아니라 화면 그대로 쓰고 출력만 바꿔주고싶음
		// 기존방식은 setAttribute, 포워딩 하겠지만 지금은 아님
		
		// 옛날로 돌아가자. 맨 처음 서블릿 할때 어떻게했는지? 개인정보 입력받아서 응답을 해줬는데, response객체를 가지고 HTML 형식 지정, Stream 생성, 출력 <- 이 방식으로 돌아갈것임
		// 응답데이터 -> 왔다갔다해서 이런 데이터를 돌려준다고 가정하자
		String responseData = "요청처리 성공!";
		
		// 1) 응답데이터 정보 설정 ☆★☆★☆
		response.setContentType("text/html; charset=UTF-8"); // 구분자 세미콜론 써야함, 콤마 ㄴㄴ
		
		// 2) 출력 스트림을 이용해서 응답
		// 옛날에는 화면전체를 만들어줬어야했지만 이번에는 responseData만 넘길것임, 메소드 체이닝 이용
		response.getWriter().print(responseData);
		// 브라우저 개발자도구의 network에 보면 응답 받았음
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
