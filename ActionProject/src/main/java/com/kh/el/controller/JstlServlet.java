package com.kh.el.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.el.model.vo.Person;

@WebServlet("/jstl.do")
public class JstlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public JstlServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// forwarding 하기 전에 DB 갔다온척 -> SELECT해옴
		// List가 돌아왔다고 침
		// Service -> DAO -> DB 갔다가 다시 Service에서 Controller로 돌아온것
		List<Person> list = new ArrayList();
		Person p1 = new Person("홍길동", 15, "한양");
		Person p2 = new Person("고길동", 40, "스울");
		Person p3 = new Person("허균", 22, "서울");
		list.add(p1);
		list.add(p2);
		list.add(p3);
		// 이렇게 우리가 만들었지만 사실 DB에서 온거임 DB라이팅
		
		// 출력하려고 Attribute에 추가해서 앞단으로 forwarding했음!
		request.setAttribute("list", list);
		
		request.getRequestDispatcher("/WEB-INF/views/customaction/JSTL.jsp")
		       .forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
