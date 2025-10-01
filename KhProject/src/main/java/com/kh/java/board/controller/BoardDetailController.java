package com.kh.java.board.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.java.board.model.service.BoardService;

@WebServlet("/detail.board")
public class BoardDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardDetailController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 지구야 아프지마
		// 항상 내가 뭘해야하는지 뭘하고 있는지 생각하자
		// 이걸 해야 부채가 안쌓인다. 생각 안하면 부채가 계속 쌓임
		// 정답이든 오답이든 중요하지 않아, 생각이 맞으면 맞은거고 오답이면 잘못생각했구나 아는게 좋은거고
		// 생각하는것 자체가 중요한것 생각을 안하고 놔버리면 문제가 되는것
		// 생각하고 질문하고, 중요한 내용이든 아니든 그게 중요한게 아니라 계속 생각하는게 중요한것, 생각하는 정신
		// 얘는 왜 protected인가? 이런 코드를 보고 생기는 의문점, throws 떤지기 왜한거지?
		// 유심히 보고 궁금해하고 생각하고
		// 앞에서 하는대로 따라가면 인지부채(빚)가 된다 공부는 항상 내가 하는 것 누군가 대신할 수 없다
		
		// -----
		// 일단 KH_BOARD에서 게시글 내용 조회하고
		// 첨부파일은 KH_ATTACHMENT에서 조회해가야겠다.
		// ??? 12:18
		
		// GET방식 요청임
		// ??? 12:18 location.href
		// 앞에서 boardNo 키값으로 선택된 게시글의 정보를 넘김, 이걸 뽑는게 선행작업
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		
		// 가공할건 없음(값이 하나뿐)
		
		// 요청처리 -> 서비스단으로 boardNo넘김, 넘기는것 자체는 쉽지만...
		Map<String, Object> map = new BoardService().selectBoard(boardNo);
		// 나중에 어떻게 받을지가 문제, 하나만 받는거면 각각 받느면 되는데 지금은 board + attechment 두개가 있을 수 있는게 문제
		// 조회두번은 서비스에서 할거고
		// 일단 어떻게 돌아올지 나중에 정하고 서비스 가서 작업 먼저 하자
		
		// ??? 14:46
		request.setAttribute("map", map);
		
		// -----
		
		// 지금 상세보기 하러 서블릿에 왔다, DB에 다녀와야함
		// ??? 12:10 내 머릿속에 계획이 완벽하다면?
		// 일단 화면에 뭘 보여줄지 정해져 있어야 DB에 가는것(어떤 것들을 출력해줄지) -> 그래야 DB에 가서 select할 컬럼이 정해진다
		// 보여줄 화면이 생겨야 DB에 가서 뭘 들고올지를 정할 수 있다.
		
		// jsp 생성 후 포워딩 먼저 해둠
		request.getRequestDispatcher("/WEB-INF/views/board/board_detail.jsp")
			   .forward(request, response);
		// ??? 12:13 여기서 생각해야 할 내용
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
