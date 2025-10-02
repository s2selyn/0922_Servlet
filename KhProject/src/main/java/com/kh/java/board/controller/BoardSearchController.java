package com.kh.java.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/search.board")
public class BoardSearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardSearchController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// GET 방식이라 인코딩 필요없음
		
		// 사용자가 선택한 옵션과 사용자가 입력한 키워드를 가지고
		// DB상에서 검색해서 나온 조회 결과를
		// 페이징처리까지 끝내서 들고 갈 것
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
