package com.kh.java.ajax;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.java.board.model.service.BoardService;

@WebServlet("/ajax3.do")
public class AjaxController3 extends HttpServlet {
	// 옛날에는 Ajax 요청 처리하는 클래스에 이름을 이렇게 붙임(10~15년전의 관례, 7~8년 전까지도 이렇게했다네요), 요새는 이렇게 안합니다
	private static final long serialVersionUID = 1L;
       
    public AjaxController3() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 게시글 목록을 조회해와서 study.jsp에 출력해준다고 하자
		// 한번에 많이 하려고 하지말고 게시물 한개라고 가정해보자 -> Board 객체 하나가 필요할듯
		// 목록을 조회하려면 pageInfo도 만들어야 하니 귀찮고 번거롭다
		Map<String, Object> board1 = new BoardService().selectBoard(1); // 만들어놓은 게시글 번호 주면 하나 조회해오는 기능 다시 쓰기
		System.out.println(board1);// 조회 잘 되는지 출력해보기
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
