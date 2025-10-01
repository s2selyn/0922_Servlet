package com.kh.java.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.java.board.model.service.BoardService;
import com.kh.java.board.model.vo.Board;
import com.kh.java.member.model.vo.Member;

@WebServlet("/delete.board")
public class BoardDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardDeleteController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// boardNo를 넘겼으니 뽑아내야함
		Long boardNo = Long.parseLong(request.getParameter("boardNo"));
		// 단순하게 이거들고 DB가서 STATUS 업데이트하면 되는데 그럼 재미없으니까
		// 현재 로그인된 사용자의 정보를 세션에서 뽑아서 userNo와 어디에 담아서 DB간다음
		// 업데이트 조건절에 사용자 비교해서 실제 작성자일때만 업데이트 하도록 구현
		HttpSession session = request.getSession();
		Long userNo = ((Member)session.getAttribute("userInfo")).getUserNo();
		
		// 어딘가에 담아서 보내야하는데 board에 담으려고 Long으로 값을 뽑음
		Board board = new Board();
		board.setBoardNo(boardNo);
		board.setBoardWriter(String.valueOf(userNo));
		// String으로 뽑았다면 이걸 안해도 됐겠죠? ㅎㅎ
		System.out.println(board); // 처음에 setBoardWriter가 아니라 setBoardContent로 써서 삭제 실패함
		
		int result = new BoardService().deleteBoard(board);
		// delete인척하는 update이기 때문에 나중에 int로 받아올 것이다
		
		// 게시글 삭제에 실패, 성공할 수 있음, 그것에 따라 응답화면 다르게 보여줄 것
		if(result > 0) {
			
			response.sendRedirect(request.getContextPath() + "/boards?page=1");
			
		} else {
			
			// 삭제실패했을때 실패한 페이지로 보내보자(result페이지 만들어놓은거 말고
			// 35번게시글 삭제 실패
			// 35번 게시글 상세조회 페이지로 보내고싶음
			// response.sendRedirect("/kh/detail.board?boardNo=35");
			response.sendRedirect(request.getContextPath() + "/detail.board?boardNo=" + boardNo);
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
