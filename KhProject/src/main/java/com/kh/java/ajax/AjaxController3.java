package com.kh.java.ajax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
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
		// System.out.println(board1); 조회 잘 되는지 출력해보기
		
		// -----
		Map<String, Object> board2 = new BoardService().selectBoard(2);
		Map<String, Object> board3 = new BoardService().selectBoard(3);
		// -----
		
		List<Map<String, Object>> boards = new ArrayList();
		boards.add(board1);
		boards.add(board2);
		boards.add(board3);
		
		/*
		// 실제로 화면에 보여주고싶은건 글번호, 제목, 작성자
		// board1이 map이라서 Board라는 key로 객체 안에 boardNo 필드에 게시글번호, 제목은 boardTitle, 작성자는 boardWriter 필드에 들어있음
		// 아무튼 개발자는 이런 값들을 key-value로 앞으로 보내고싶음
		// JSONObject가 필요하다
		JSONObject b1 = new JSONObject();
		b1.put("boardNo", ((Board)board1.get("board")).getBoardNo()); 형변환 해서 get으로 가져가야함
		b1.put("boardTitle", ((Board)board1.get("board")).getBoardTitle());
		b1.put("boardWriter", ((Board)board1.get("board")).getBoardWriter());
		// response.getWriter().print(b1);
		// 제이슨 형태로 응답을 보낸다는 뜻, b1을 하나 담아서 보냄 -> 하나의 자바스크립트 객체의 형태로 전달됨
		
		// 객체 하나만 넘길 시 JSONObject형태로 만들어서 응답
		// List객체는 응답 시 JSONArray형태 안에 요소로 JSONObject를 만들어서 응답
		
		// 하나만 보내고 싶은게 아니라 여러게시글 정보를 보내고싶음, 여러개의 JSONObject, 배열에 담아보낼까?
		// JONArray array = new JSONArray();
		// array.add(b1);
		
		Map<String, Object> board2 = new BoardService().selectBoard(2);
		JSONObject b2 = new JSONObject();
		b2.put // 제이슨 오브젝트 객체 여러개를 보내려면 이런 번거로운걸 계속 해야함 -> 구글에서 gson을 제공
		*/
		
		// gson을 써보자
		// Gson : Google이 만든 JSON라이브러리
		// Gson객체 생성후 toJson()를 호출
		Gson gson = new Gson();
		response.setContentType("application/json; charset=UTF-8");
		
		// gson.toJson(응답할 객체, 응답용 스트림)
		// gson.toJson(board1, response.getWriter());
		// JSON 객체를 안만들어도 자동으로 JSON형태로 만들어서 앞으로 보내줬음
		// 지금 VO를 넘긴 것인데, VO가 넘어갈때는 자바스크립트의 객체 형태로 넘어감
		// 속성명이 전부 VO의 필드명으로 작성되고, map은 value의 key값으로 넘어갔음
		// response의 형태가 "board" : { 객체("속성명" : "값", ..., "속성명" : "값") }, JSON이므로 속성명과 값 앞뒤로 쌍따옴표 붙어있음
		// 자동으로 키값이 전달하는 객체의 속성명이 됨!
		
		// 여러 게시글을 조회한다면 selectList 메소드를 호출해서 여러개의 게시글이 담길것임
		// board2부터 board3까지 추가로 선언, List 선언하고 add로 boards에 board1, board2, board3 객체들을 담음
		// 첫번째 인자로 board1 대신 boards 전달
		gson.toJson(boards, response.getWriter());
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
