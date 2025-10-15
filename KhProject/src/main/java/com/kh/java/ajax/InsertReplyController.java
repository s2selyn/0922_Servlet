package com.kh.java.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.java.board.model.service.BoardService;
import com.kh.java.board.model.vo.Reply;
import com.kh.java.member.model.vo.Member;

@WebServlet("/insert.reply")
public class InsertReplyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public InsertReplyController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// POST => 인코딩
		// request.setCharacterEncoding("UTF-8"); <- 아 이녀석 중복코드라 더이상 쓰고싶지 않다
		// java에 패키지 생성 -> EncodingFilter 작업
		
		// request로부터 값뽑기
		Long boardNo = Long.parseLong(request.getParameter("boardNo"));
		String content = request.getParameter("replyContent");
		
		// session에서 뽑기
		Long userNo = ((Member)request.getSession().getAttribute("userInfo"))
									  .getUserNo();
		
		// 데이터 가공
		Reply reply = new Reply();
		reply.setRefBno(boardNo);
		reply.setReplyContent(content);
		reply.setReplyWriter(String.valueOf(userNo));
		// 세개만 들고가고 나머지는 SEQ 이용해서 NEXTVAL 또는 DEFAULT 값 사용할것임
		
		// 원래라면 ReplyService를 따로 뒀겠지만 작성조회만 할거니까 BoardService를 이용하기로함
		// Service로 요청
		int result = new BoardService().insertReply(reply);
		// 나중에 돌아올 응답은? insert니까 int로 받아보자
		
		// board-mapper까지 다녀왔음
		// 1) 어떤 형태로 응답해줄것인가 -> response
		// success / fail
		response.setContentType("text/html; charset=UTF-8");
		
		// 2) 응답 -> response에 getWriter로 지정
		response.getWriter().print(result > 0 ? "success" : "fail");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
