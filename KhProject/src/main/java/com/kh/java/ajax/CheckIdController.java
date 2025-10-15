package com.kh.java.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.java.member.model.service.MemberService;

@WebServlet("/checkId")
public class CheckIdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CheckIdController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 이번에 우리가 할 일 :
		// 앞에서 AJAX요청을 통해 넘어온 사용자가 입력한 문자열 데이터를
		// DB까지가서 KH_MEMBER테이블에 USER_ID컬럼에 있는값인가 없는값인가 조회를 해보고
		// 조회결과를 다시 앞단으로 응답해주기
		
		// 값뽑기!
		String id = request.getParameter("id");
		// data 속성에 작성한 속성명을 통해 속성값을 가져올 수 있다
		// System.out.println(id); 잘 가져와지는지 확인함
		
		// 요청 처리 해야함, 최종적으로 할건 DB서버에서 조회
		// select는 확정, 나중에 받을 응답은 있나없나, 반환타입이 어떻게 될까?
		// SQL문을 어떻게 쓸까? <- 이게 정해져있어야함
		// 네이버는 NNNNN, NNNNY 이렇게 오더라 -> String으로 받아야겠군
		// 그럼 SQL문은 어떻게 써야 저게올까?
		// 지금 하고싶은건 아이디가 있는지 없는지 -> DBeaver에서 SQL 작성해보기
		
		String result = new MemberService().checkId(id);
		// System.out.println(result); 잘 동작하는지 확인
		// 쓸수없으면 NNNNN, 쓸수있으면 NNNNY가 돌아온다
		
		// 요청 보낸 ajax에게 다시 보내줘야함, 2단계로 나눠서 보내야한다
		// 1) 응답데이터가 뭔데이터인지
		// 지금은 문자열인데 우리가 오늘 JSON도 만들어서 보내야함, 문자열인지 JSON인지 XML인지 서버에서 지정해줘야함, 인코딩도 정해줘야함 -> 이건 response 사용해서 설정
		response.setContentType("text/html; charset=UTF-8"); // charset이 원래 encoding, 만들 때 생각이 안나서 이렇게 만들었는데 하도 이렇게 많이 써서 고칠 수 없게 되어버림
		
		// 2) 응답
		// 데이터를 내보내야하므로 출력 스트림이 필요함, 단순 문자열은 print 메소드로 보내면 됨, PrintWriter이므로 한글도 잘 보내진다
		response.getWriter().print(result);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
