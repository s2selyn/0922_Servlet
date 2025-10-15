package com.kh.java.notice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("*.notice")
public class NoticeFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public NoticeFrontController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// notice 관련된 작업을 하는 서블릿들의 중복 코드가 생겨날텐데,
		// 이 컨트롤러를 그 코드들의 중복을 제거하는 용도로 사용
		// 매핑값을 패턴을 만들어서 넣어줌
		// System.out.println("앞이 뭐로 시작하든 notice로 끝나면 출동");
		
		String uri = request.getRequestURI();
		// System.out.println(uri);
		String mapping = uri.substring(uri.lastIndexOf("/") + 1, uri.lastIndexOf("."));
		System.out.println(mapping);
		
		NoticeController nc = new NoticeController();
		
		String view = "";
		switch(mapping) {
		case "insert" : view = nc.insert(request, response);
		case "select" : view = nc.select(request, response);
		}
		
		request.getRequestDispatcher(view).forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
