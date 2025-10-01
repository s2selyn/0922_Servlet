package com.kh.java.board.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.java.board.model.service.BoardService;
import com.kh.java.board.model.vo.Category;

@WebServlet("/updateForm.board")
public class UpdateFormController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateFormController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 시~원하게 해 보겠 읍 니다~~~~~~~~~ 수정하기 ~~
		// 자 일단 슬랙에 수업자료 탭에서 update_form.html을 가져오시기 바랍니다 ~
		// /WEB-INF/views/board밑에다가 update_form.jsp를 만들고
		// 슬랙에서 받은걸 참고해서 잘 한 번 만들어보시기 바랍니다~ 예 ~ 오예 ~
		// 기회를 드리겠음 빨리해보셈 그리고 16일에 자리바꿀꺼니까
		// 짝꿍들하고 작별인사하셈 그동안 즐거웠어 흑흑 하셈 시간을 드리겠셈
		// 참고로 5인 1조셈 한조당 두 줄 먹는거셈 3명 앉고 2명 앉는거셈
		// 님들 왜 작별인사안하셈? 사실 짝꿍이 별로였던거셈????
		
		// --------------------------------------------------
		// 우리는 지금 자바코드 다루는 중
		
		// -----
		// 게시글 수정양식 보여주기
		// boardNo가 있어야 어떤 보드인지 알아서 보여줄 수 있음, 이건 다행히 앞에서 넘겨옴
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		
		// 원본게시글 가져와야함, boardNo들고 DB 테이블 가야함, board만 가져오나? 첨부파일도 가져와야함
		// 그러다가 수정하기 하면서 카테고리 바꾸고싶을수도 있으니 카테고리도 가져와야함
		// 테이블 세개니까 select 세번은 해야함
		// 우와 할일많다, 어떻게해? 다 만들어놨잖아여~ 부르기만 하면 된다
		// 그렇지만 boardNo가 있으면 selectBoard 호출해서 게시글 가져올 수 있는데 이거 하면 조회수가 1 증가함
		// 음.. 신경안쓰고 그냥 해볼까?
		// 일단 서비스에 두번 가야해서 미리 만들어놓고 쓰자
		BoardService boardService = new BoardService();
		
		List<Category> category = boardService.selectCategory();
		Map<String, Object> map = boardService.selectBoard(boardNo);
		
		request.setAttribute("category", category);
		request.setAttribute("map", map);
		// ??? 17:40 조회수를 신경 쓴다면 어떻게 하면 되나?
		// -----

		// 와이거 중복코드 어쩌지?? 참을 수가없는걸?? 이걸 대체 어쩐담??
		// 이거 너무 없애고 싶다. 중복코드 제거해버리고 싶다. 아주 그냥 사라지게만들고싶다.
		request.getRequestDispatcher("/WEB-INF/views/board/update_form.jsp")
			   .forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
