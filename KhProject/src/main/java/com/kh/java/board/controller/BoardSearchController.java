package com.kh.java.board.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.java.board.model.service.BoardService;
import com.kh.java.board.model.vo.Board;
import com.kh.java.common.vo.PageInfo;

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
		
		// 앞단에서 넘어올 condition 가져오기
		String condition = request.getParameter("condition");
		// "writer", "title", "content" 셋중하나, input요소에 value 속성값 달아두셨음
		
		String keyword = request.getParameter("query");
		// 사용자가 입력한 값, 뭐가올지 모름
		
		// 페이징 처리가 들어가야하니 변수 8개 있어야함
		
		// 나중에 쿼리문 가서 필드명이든 키값이든 써서 #{} 해야하니 맵이 좋것다
		Map<String, Object> map = new HashMap();
		map.put("condition", condition);
		map.put("keyword", keyword);
		
		int listCount = new BoardService().searchedCount(map); // 전체게시글수 -> 전체게시글 조회하는거 만들어뒀음, 근데 이거 못씀, 이건 모든 게시글 개수고 우리가 필요한건 검색 결과의 게시글 개수 -> 새로 만들어야함
		// DBeaver에서 SQL문 작성해보고 넘길 값 생각했음, condition, keyword를 같이 넘겨야한다, 어디에 담아서 넘기지? -> map으로 결정!
		
		int currentPage = Integer.parseInt(request.getParameter("page")); // 현재요청페이지 -> 그냥 1로 해두면 검색결과 보여주는 페이징버튼 눌렀을때 달아줘도 항상 1페이지일듯?
		// 앞에서 어떻게함? 폼태그 요청인데 1페이지 보편적으로 보여줄듯, 지금 넘기는 값 없으니 추가해주러 board_list.jsp 다녀옴
		// 이 값을 뽑아서 쓰도록 하자
		
		int pageLimit = 5; // 한번에 보여줄 페이징버튼 개수
		int boardLimit = 5; // 한페이지에 게시글 몇개 보여줄지
		int maxPage = (int)(Math.ceil((double)listCount / boardLimit));// 최대 페이지 몇번 -> 이건 공식
		int startPage = (currentPage - 1) / pageLimit * pageLimit + 1; // 시작페이지 몇번 -> 이것도 공식
		int endPage = startPage + pageLimit - 1; // 마지막 페이지 몇번 -> 이것도 공식
		int offset = (currentPage - 1) * boardLimit; // db 어떻게? -> 이것도 공식
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		// 아 이거 중복코드.. 아 이거 책임분리 아 이거... 아으아아아아ㅏ아!!!
		PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, startPage, endPage, maxPage, offset);
		
		// 검색된 결과에 대한 조회 결과를 들고가야함
		// 검색할때도 필요하고
		// 페이징 처리 끝난 내용도 들고가야함
		// offset 구문을 쓰기 위해서는 condition, keyword 들고가야하고
		// + 전체아니고 특정몇개 들고갈거니까 select할때 offset쓰려면 offset이랑 pageLimit을 넘겨줘야함
		// map 제네릭 수정
		map.put("offset", offset);
		map.put("limit", boardLimit);
		
		// 진짜 검색결과 가지러
		List<Board> boards = new BoardService().selectSearchList(map);
		
		// 응답페이지 만들어둔거라서 키값만 제대로 전달하면 jsp가 알아서 해준다
		request.setAttribute("boards", boards);
		request.setAttribute("pi", pi);
		
		request.getRequestDispatcher("/WEB-INF/views/board/board_list.jsp")
			   .forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
