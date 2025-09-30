package com.kh.java.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.java.board.model.service.BoardService;

@WebServlet("/boards")
public class BoardListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardListController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 게시글 목록 조회, select 해서 단순히 했던것을 페이징처리작업추가, 버튼 만들어서 페이지에 해당하는 목록만 조회할것임
		// - 페이징 처리 -
		// 필요한 변수들
		int listCount; // 현재 일반게시판의 총 게시글 개수
		// 현재 테이블에 있는 총 게시글의 개수가 필요함, 그래야 버튼을 몇 개 만들지 정할 수 있음
		// 30개 있으면 한페이지 10개 보여준다면 버튼 3개 / 5개 보여준다면 버튼 6개 -> 이런걸 정하기 위함
		// 지금은 listCount 30개(더미데이터 insert로 넣었음)
		// 그냥 생각하면 listCount = 30이지만 항상 30이 아님, 생성/삭제에 따라 변한다
		// 이 게시글 조회요청이 들어올때마다 변하는 유동적인 값
		// 이걸 알아내려면 현재 일반게시판 총 게시글 개수를 알아내려면?
		// => BOARD테이블에서 COUNT(*) (STATUS='Y' AND BOARD_TYPE = 1) 조회
		// 그룹함수(행 개수), 삭제된건 조회되면 안됨, 일반/사진 하나의 게시판을 쓰고 있으므로 보드타입이 1(일반게시판)인것만(지금 insert된것은 전부 1임)
		// 누가 쓰고 지울때마다 바뀌는 값이라 db 갔다와야함
		
		int currentPage; // 현재 사용자가 요청한 페이지
		// 일반적으로 사용자는 신경쓰고 살지 않는다
		// 페이지 번호 누르면 url에 page=번호로 요청했는지 보인다, 이걸 알아야 그것에 맞는 게시글들을 조회해서 보여준다
		// 이걸 얻어내려면? 앞단에서 넘겨줘야함, 버튼에 달아줘야해, 그럼 앞단에서 넘겨준걸 뒤에서 getParameter
		// get 방식의 key밸류에서 parameter 뽑아내기
		// => request.getParameter("page")로 뽑아서 씀
		// String page = request.getParameter("page");
		// System.out.println(page);
		// 버튼에도 몇번 페이지 요청인지 전부 달아서 요청 보낼 수 있게 구현할것임, 똑같이 page 라는 key값으로 넘겨서 쓸것
		
		int pageLimit; // 페이지 하단에 페이징버튼 개수 => 5개
		// 보여주고 싶은 버튼 개수가 전부 다를 수 있음, 이걸 정해줘야함(최대 몇개 보여줄지)
		// 이건 그냥 보여주고 싶은 개수로 정하는 것
		
		int boardLimit; // 한 페이지에 보여질 게시글의 최대 개수 => 10개
		// 한 페이지에 게시글 몇개씩 보여줄건지(20개 10개 등) -> 이것에 따라(바꾸느냐에 따라) 전체 페이징 개수가 영향을 받는다
		// 15개씩 보여주는데 총 5페이지였던것을 30개씩 보여준다면 5페이지가 끝이었던게 3페이지로 줄어들수도있고 계속 변한다
		// 네이버 카페처럼 사용자에게 선택받을수도 있고 직접 정할수도 있는데
		// 처음이니까 우리가 정하는걸로 해보자
		
		int maxPage; // 가장 마지막페이지가 몇 번 페이지인지(총 페이지의 개수)
		int startPage; // 페이지 하단에 보여질 페이징바의 시작 수
		int endPage; // 페이지 하단에 보여질 페이징바의 끝 수
		
		// 총 7개의 정수형 변수로 페이징 처리를 할것이다
		// 값을 하나하나 구해서 하나하나 대입하고 그 다음에 특정 게시글들만 조회해서 화면에 출력하는 것 까지가 오전의 목표
		
		// * listCount : 총 게시글의 수
		// 현재 테이블에 게시글이 몇개나 있나? -> DB 가야함, 여기서는 못함 -> BoardService에 요청을 보내야함
		listCount = new BoardService().selectListCount();
		// System.out.println(listCount); 콘솔에 더미만큼 잘 나온다
		
		currentPage = Integer.parseInt(request.getParameter("page")); // 앞에서 넘기는거 받아온다, 반환타입 String이라서 대입못함 -> parseInt 호출 -> Integer의 메소드이다
		
		
		
		
		
		// jsp로 연결되는지 확인하기 위해서 requestDispatcher
		request.getRequestDispatcher("/WEB-INF/views/board/board_list.jsp")
			   .forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
