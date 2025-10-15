package com.kh.java.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
		
		// response.setContentType("text/html; charset=UTF-8");
		// response.getWriter().print(name); print는 인자를 하나밖에 못받음
		// response.getWriter().print(email); 그러니까 또 따로 담아봄
		
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
		// ["홍길동2", "hong@kh.com"] <- 데이터를 이렇게 만들어서 보내고싶다
		// String responseData = "[\"" + name + "\",\"" + email + "\"]"; // 자바에서 문자열 만들기(자바스크립트 배열 모양의 문자열)
		// System.out.println(responseData); 잘 출력되나 확인
		// 배열도 객체임, Object 타입의 객체
		
		// JSON 형태로 보내주고싶다면?
		// response.setContentType("application/json; charset=UTF-8");
		// response.getWriter().print(responseData);
		
		// JSON에서는 문자열, 날짜 전부 쌍따옴표로 묶어야함
		// JSON은 문법이 아주 엄격함, 배열에 요소를 넣을때도 key를 쌍따옴표로 감싸줘야함, 안그러면 파싱할수없음
		// 우리가 AjaxController2에서 홑따옴표를 써서 담았기 때문, 하지만 자바에서 문자열 쓰는거라 또 바로 못쓰니 역슬래시 달아주고 쌍따옴표 써야함 ' -> \"
		// \" 이렇게 매번 써줘야할까..? 해결할 도구들이 있음! -> 지난주에 lib에 json, gson 추가했음
		// 복잡한 코드를 쉽게 해줄 라이브러리
		/*
		 * 라이브러리를 사용해서 JSON형태의 데이터 만들기
		 * 
		 * 1. JSONArray
		 * 2. JSONObject
		 * 
		 */
		// 배열의 형태로 만들고 싶으니 JSONArray 클래스를 사용해보기
		// 기본생성자 호출해서 객체 생성
		JSONArray array = new JSONArray(); // 자바의 ArrayList랑 비슷하게 생김, 결국 ArrayList로 toString 쓸수있게 구현되어있음, ArrayList를 상속받아 오버라이딩한것
		// 요소를 추가(name, email) -> list에 요소 추가는 add 메소드 호출
		array.add(name); // ["홍길동2"] <- 이렇게 알아서 쌍따옴표 앞뒤로 달아줌
		array.add(email); // ["홍길동2", "hong@kh.com"]
		
		response.setContentType("application/json; charset=UTF-8");
		// response.getWriter().print(array);
		// 기존에 손수 \" 해야했던것을 JSONArray를 이용해서 작업하면 문자열을 번거롭게 만들지 않아도 됨
		
		// 개발자들은 보통 이런 상황에서 배열을 선호하지 않는다, 인덱스를 보고 작업해야하는데, 프론트-엔드 작업자가 다르면 파트, 시간이 다르므로 소통과 문서가 많이 필요하게 됨
		// 지금은 값이 정해져서 상관없지만 숫자가 많다면? 판매가, 할인가, 특별세일가, 쿠폰적용가 등등 숫자가 많아져버리면 인덱스로 관리했을 때 의미를 파악하기(구분하기) 어려워짐
		
		// 이런 상황에서 여러개의 값을 다룰 때 선호하는 방식이 인덱스 개념 사용보다는 value에 key값을 달아서 명확하게 값을 구분해서 사용하는것으로
		// 자바에서는 이런 역할을 map이 해주고, 자바스크립트에서는 객체가 해줌 -> 배열 대신 객체의 형태로 key값을 달아서 사용
		// -> JSONObject 이용해서 객체 모양으로 앞단에 값을 넘겨보자
		JSONObject obj = new JSONObject(); // 기본생성자 호출해서 객체 생성, HashMap 상속받아서 구현되어있음
		// map에 key-value 형태로 요소 추가할 때 사용하는 메소드 -> put()
		obj.put("name", name);
		obj.put("email", email);
		
		response.getWriter().print(obj);
		// 내가 map에 담을 때 쓴 속성명이 같이 나온다
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
