package com.kh.java.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.java.board.model.dto.ImageBoardDto;
import com.kh.java.board.model.service.BoardService;

@WebServlet("/images")
public class ImageListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ImageListController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// ImageListController 만들었는데 게시글 없어서 작성하기 위해서 포워딩하는곳만 작성해둠
		// jsp 가기전에 DB에 들러서 실제 들어있는 board 들고가야하니 여기서 db에 갈수있도록 작업해야함
		
		// Service에 가야함, 컨트롤러에 돌아올 반환타입 생각
		// 뭐가 돌아와야함? 디비버에서 sql문 작성, imageBoardDto 생성
		List<ImageBoardDto> boards = new BoardService().selectImageList();
		
		// 다녀온 목적 -> 화면에 보여주기
		// boards안의 필드값을 출력하고싶으니까 request에 담아
		request.setAttribute("boards", boards);
		
		request.getRequestDispatcher("/WEB-INF/views/image_board/thumbnail_list.jsp")
			   .forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
