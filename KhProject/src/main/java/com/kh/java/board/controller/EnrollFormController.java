package com.kh.java.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.java.board.model.service.BoardService;
import com.kh.java.board.model.vo.Category;

@WebServlet("/enrollForm.board")
public class EnrollFormController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EnrollFormController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// -----
		// 포워딩 먼저 해놓고 카테고리 조회작업 하러 올라왔음
		// 생각! 나중에 결과가 어떤 모양으로 돌아오는지
		// 행이 여러개로 돌아옴, 카테고리 테이블의 컬럼 두개를 다 들고와야함, insert 할때 넣을 값과 출력할 때 보여줄 값이 다름
		// 7행 * 2 = 14개의 값을 들고와야함
		// 한행은 일단 뭐 하나로 다룰거임, 아직 안정함
		// 근데 일곱개 행이지만 이건 변할 수 있음, 몇개인지 정할 수 없음
		// select 했을 때 반환 타입이 리스트임, 그럼 선택지가 없음, 마이바티스 쓸거면 여러 행 조회할 때 list로 반환하게 만들어져있음
		// 우리가 정할 수 있는건 제네릭 정도, VO에 담기로 하고 VO 만들자 -> Category 클래스 생성하고 작업
		// 다시 돌아와서 작업
		List<Category> categories = new BoardService().selectCategory();
		// 여기까지 작성하고 Service에서 메소드 생성 -> Dao -> board-mapper갔다가 다시 돌아온다
		// 조회해온것을 화면에 줘야함, 어디다 넣어야할까? -> request
		request.setAttribute("category", categories);
		// -----
		
		// 이번에는 jsp로 응답데이터를 만들기 위해서, 사용자가 글쓰기 버튼을 누르면 보이게 하기 위해서 왔다
		// 그걸 하기 위해서는 request dispatch가 필요, 그리도 포워딩하고 글쓰기 클릭해서 화면 나오는지 확인
		request.getRequestDispatcher("/WEB-INF/views/board/enroll_form.jsp")
			   .forward(request, response);
		// 로그인을 안하고 왔다, 로그인하기 전인데 board 테이블에 insert 하려면 반드시 userNo가 있어야함, not null
		// 안들어가면 안된다, 로그인이 안되어있으면 값이 없어서 값을 넣지 못함
		// 글쓰기 화면에 왔을 때 로그인이 안되어있다면 쫓아낼것이다, 어디서 체크? 전에 컨트롤러에서 해봤음
		// 이번에는 jsp에서 체크하는걸로 해보자 -> enroll_form.jsp에서 작업
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
