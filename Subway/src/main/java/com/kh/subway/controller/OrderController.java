package com.kh.subway.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.subway.model.service.SubwayService;
import com.kh.subway.model.vo.Subway;

// 여기 매핑값 바꿔줘야하는데 어디가서 뭐 확인해야함?
// 요청을 보내는 html이 만들어져있는 파일, 지금은 index.jsp(웰컴파일)
/*
 * post방식 써야하지만 수업용으로 <form action="order.do" method="get">
 * 요청값 보내지는거 확인하려고
 * 
 * action에 슬래시 없으면 상대방식인데?
 * 이러면 쉬울수도 있고
 * 
 * 기본적으로 요청을 보내면 contextRoot로 간다, 여기가선 자원을 못얻는다 이 의미는 webapp 폴더 내놔라는 뜻
 * 프로젝트에 접근한거라 html 못받음
 * 
 * ???
 * http 통신이기 때문에 궁극적으로 돌려주는건 html 데이터다
 * 
 * webapp 폴더 아래까지 들어가야 뭘 얻을 수 있으니 최소한 슬래시를 붙여야함
 * http://localhost:4000/subway/
 * 이거 사용자는 안써도 브라우저가 붙여준다 그게 브라우저의 일
 * 
 * 그럼 이제 우리가 웰컴파일 만들어두니까 자기이름과 맞는걸 응답해준다. index.jsp도 어차피 html로 돌아감
 * 
 * 현재 웰컴파일은 이렇고 폼태그 액션에 /없이 작성
 * 상대경로는 현재 url의 제일 뒤에 붙은 슬래시(마지막) 뒤에 상대경로가 추가됨
 * 
 * 그럼 자동으로 http://localhost:4000/subway/order.do 이렇게 submit 요청 시 전달될것
 * ??? 주소의 의미
 * 
 * ??? 상대경로와 절대경로의 차이, url
 * 
 * 절대방식이 안헷갈리고 좋다.
 * 아무튼 이번의 서블릿 매핑값은 order.do 니까 WebServlet 애노테이션 뒤의 값 변경해줌 -> @WebServlet("/order.do")
 * 
 */
@WebServlet("/order.do")
public class OrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public OrderController() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 어느 방식이든 여기에서 작업
		
		// 제일 처음 할 일은?
		// 0) 요청전송방식이 뭐지?
		// GET / POST 이걸 먼저 떠올려야함
		// POST 방식이면 인코딩 해야함, 지금은 GET이니 스킵
		
		// 1) 요청 시 전달값이 있는가?
		// 없을수도 있음 전체조회 이런거면
		// 오늘은 있는 상황을 만들어내서 있음, 9개 값이 있다
		// => 값뽑기
		// request.getParameter("키값")
		// request.getParameterValues("키값")
		// request.getParameterNames() 이건 키값뽑는거 별 필요없음
		// request.getParameterMap()은 map 형태로 뽑는거라 iterator 돌려서 반복해서 또 뽑아야함 필요없음
		// 아무튼 둘다 인자는 키값
		
		// 값을 뽑아서 변수에 대입
		// 주문자정보
		// 뽑아내는 방법은 정해져있고, 변수대입도 고정, 뽑아내기 위해서는? key값을 알아야함, 이건 jsp의 form태그 안의 input요소의 name 속성값을 알아야한다
		String name = request.getParameter("userName");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String message = request.getParameter("message");
		
		// 주문정보
		String sandwich = request.getParameter("sandwich");
		
		// 얘네는 체크박스라 값이 여러개니까 getParameterValues
		// checkbox == 체크된게 하나도 없을 시 null값
		String[] vegetable = request.getParameterValues("vegetable");
		String[] sauce = request.getParameterValues("sauce");
		String[] cookie = request.getParameterValues("cookie");
		String payment = request.getParameter("payment");
		
		// 우리가 기억해둘것은 String 배열 삼총사가 체크박스, 선택의 영역이라 null값이 대입된다
		// ??? 17:05 이유가 생각이 안남, 나머지는 폼태그 안에 있어서 null값일 수 없음?
		
		// 값 다 뽑은 다음에 해야 할 일
		// 2) 가공 -> VO클래스를 객체로 생성해서 필드에 담기
		// 테이블에 1행 insert하려고 값을 받았는데 이걸 DAO까지 멀리 보내야하니까 담아야함, 한행 값은 어디에 담나요?
		// 마이바티스 쓸거면 애초에 하나밖에 못넘기니까 무조건 어딘가 하나에 담아야함
		// 뷰가 할 일은 다 끝났고 컨트롤러에 온거에여! 서비스로 어디 담아서 넘기는게 할일입니다.
		// 서비스도 넘길거임, DB SQL 실행할거니까
		// 테이블에 넣으려고 한행 데이터 받은거야, 어디 담아도 별 상관없어 사실, 근본으로 따지면 DTO지만 따로 안만들었음
		// VO의 필드가 테이블 한 행 담기 위한 녀석이다, 값을 담는 역할이잖아, VO에 담자
		// 바뀐건 view말고 없다, 브라우저에서 볼수있게 방법만 다른거, 스캐너 안쓰고 input요소 바꾼겨
		// 이거했다고 그동안의 작업이 드라마틱하게 바뀐게 아님! 그거때문에 늘어난 절차는 어쩔 수 없는거고 뷰 이외의 나머지는 변하지 않는다.
		Subway order = new Subway();
		// 생성자를 SYSDATE 제외하고 만든게 아니라 setter로 담자
		order.setName(name);
		order.setPhone(phone);
		order.setAddress(address);
		order.setRequest(message);
		order.setSandwich(sandwich);
		
		// 처음에 얘기했듯이 테이블 모양이 안예쁘다, 여러개의 값을 컬럼 하나에 담으려고 일부러 VO 필드를 String으로 해뒀음
		// 지금 setter를 이용해서 vegetable 변수 그대로 담을 수는 없음, 배열이니까 매개변수 타입 불일치(매개변수는 String으로 넣어야함)
		// order.setVegetable(vegetable); 이렇게는 안된다
		// join 쓰면 편함, 그게 기억안나면 반복문 돌려서 붙이고
		
		// 이것도 그대로 쓸수없음, 선택안하면 null이 들어오기 때문에 NPE 발생한다, 굳이 if를 쓰지는 않고 삼항연산자 사용
		// if(vegetable != null) {}
		order.setVegetable(vegetable != null ? String.join(",", vegetable) : "선택안함");
		// 간단하면 if도 귀찮은 느낌이 있다면 삼항연산자 사용, 이런상황
		order.setCookie(cookie != null ? String.join(",", cookie) : "선택안함");
		order.setSauce(sauce != null ? String.join(",", sauce) : "선택안함");
		order.setPayment(payment);
		// ??? 17:10 이렇게 VO에 값 담아줌
		
		// key값은 오타 많이 날 수 있으니까 값 잘 들어오는지 확인해봐야함
		// System.out.println(order);
		// toString 오버라이딩 했으니까 이걸로 확인가능
		
		// 3) 요청처리 -> Service호출
		// 사용자가 이런 정보를 입력해서 주문할거랬으니 입력한 값들을 DB에 TB_SUBWAY 테이블에 1행 insert하는게 최종목적
		// 컨트롤러가 못한다, 서비스 호출해야함, 객체생성해야 호출가능, 생성하면서 메소드 호출해야 처리할수있나여? 매개변수로 넘겨줘야함
		// ??? 17:21 작성하는 생각과정
		int result = new SubwayService().insertOrder(order);
		// Service에 이 메소드 만들러 간다
		
		// 4) 응답결과 출력
		if(result > 0) {
			response.getWriter().append("success :)");
		} else {
			response.getWriter().append("fail :(");
		}
		
		// 값뽑기 말고는 앞에서 마이바티스 하던거 그대로임! 하나도 안바뀜! 뷰 바뀌고 컨트롤러에서 값뽑기만 늘어나고 나머지는 안바뀌고 그대로이다.
		// 이 다음에는 컨트롤러를 또 바꿀거야, 나머지는 또 똑같을거고 그러니까 마이바티스 잘해잉, 컨트롤러 바꾸면 DAO바꿀거
		// 앞에꺼 잘해놔야 뒤에 붙여서 할거니 복습 열심히하쇼
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
