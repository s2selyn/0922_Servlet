package com.kh.subway.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.subway.model.service.SubwayService;
import com.kh.subway.model.vo.Subway;

@WebServlet("/orderList.sandwich")
public class OrderListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// 서블릿이 버전업 했을 때 이력관리를 위한 상수필드, 1을 하나씩 올리는거임, 얘를 바꿔놔야 나중에 깃허브에서 보고 파악가능
       
	// 생성자
    public OrderListController() {
        super();
    }

    // 메소드1
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 우리는 여기서 작업, 무슨방식일지 먼저 생각
		// GET? POST?
		// a태그 요청 == 100% GET방식
		// location.href 건드렸다면 GET방식, form태그 action속성 get이면 GET방식
		// 그럼 인코딩 작업 필요없음
		
		// 그 다음 생각
		// 요청 시 전달값이 있는가?
		// a태그도 요청 시 전달값을 보낼 수 있음
		// 태그가 뭔지 중요한게 아니라 url이 뭔지가 중요
		// href='orderList.sandwich/?value=value'로 보낸다면?
		// a태그로 요청을 보내지만, url에 붙어서 보내진다, 그럼 요청 시 전달값이 생기는 것
		// a, form태그, JS로 보냈는지가 중요한게 아니라(어떤방법이 중요한게 아니라) url에 이게 있느냐가 중요
		// 지금은 딱히 전달값이 없음
		
		// 1) 데이터 가공 => X
		// 없을때도 건너뛰고 하나일때도 가공은 건너뛰고 가공안해줘도 될것같음
		
		// 2) 요청처리 -> Service단 호출
		// 나중에 어떻게 돌아오나? SQL문은 SELECT를 보낼것, 결과는 ResultSet이지만 몇행일지 모름
		// 한행의 정보는 하나의 VO에 담기로 했음, 몇개인지는 알수없음, 하나도 없을수도 있고 100개일수도있고
		// 몇개든 담을 List를 써보자, VO(Subway)를 담아올것임
		List<Subway> orderList = new SubwayService().findAll();
		
		// 미리 여기 콘솔에서 확인해보기
		// System.out.println(orderList);
		
		// list에 담긴 Subway(VO)안의 필드에 담긴것을 확인했다!
		// 얘네를 화면에 출력할거니까 orderList를 jsp에 보내줘야함, 어딘가에 담아둬야한다 -> request
		// 정확히는 9개 있는데 다 안할거고 지금 이거 하나배웠음, 두개는 최소 명확히 알아야함, 모르면 개발못함, 그중에 하나가 request
		request.setAttribute("orders", orderList);
		
		// 조회결과가 있을수도있음 / 조회결과가 없을 수도있음
		// 그때마다 값을 추가로 보내주자...? 하려다가 말음
		
		// 아직 jsp 안만들었는데 포워딩 작업 먼저 해두자, 보통 RD객체는 포워딩 하려고 만든다
		request.getRequestDispatcher("/views/list.jsp")
			   .forward(request, response);
		// 보통은 포워드 끝나면 쓸일이 없어서 변수로 담지않고 그냥 메소드 호출로 써버림
		// 저 경로에 만들거라 미리 저렇게 작성
		// jsp 파일 생성해서 작업하러감
		
	}

	// 메소드2
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
