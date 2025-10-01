package com.kh.java.board.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.kh.java.board.model.service.BoardService;
import com.kh.java.board.model.vo.Attachment;
import com.kh.java.board.model.vo.Board;
import com.kh.java.common.MyRenamePolicy;
import com.kh.java.member.model.vo.Member;
import com.oreilly.servlet.MultipartRequest;

@WebServlet("/insert.board")
public class BoardInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardInsertController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		// String title = request.getParameter("title");
		// System.out.println(title);
		
		if(ServletFileUpload.isMultipartContent(request)) {
			
			int maxSize = 10 * 1024 * 1024;
			
			HttpSession session = request.getSession();
			// request.getServletContext();
			ServletContext application = session.getServletContext();
			String savePath = application.getRealPath("/resources/board_upfiles");
			// System.out.println(savePath);
			
			MultipartRequest multiRequest = new MultipartRequest(request,
																 savePath,
																 maxSize,
																 "UTF-8",
																 new MyRenamePolicy());
			
			// System.out.println(title);
			String title = multiRequest.getParameter("title");
			String content = multiRequest.getParameter("content");
			String category = multiRequest.getParameter("category");
			Long userNo = ((Member)session.getAttribute("userInfo")).getUserNo();
			
			// 3) 가공해야디~
			Board board = new Board();
			board.setBoardTitle(title);
			board.setBoardContent(content);
			board.setCategory(category);
			board.setBoardWriter(String.valueOf(userNo));
			
			// 3_2) 첨부파일의 경우 => 선택적
			Attachment at = null;
			
			// 첨부파일의 유무를 파악
			// System.out.println(multiRequest.getOriginalFileName("upfile"));
			// 첨부파일이 있다면 "원본파일명" / 없다면 null값을 반환
			
			if(multiRequest.getOriginalFileName("upfile") != null) {
				
				// 첨부파일이 있다!!!!! => VO로 만들기
				at = new Attachment();
				
				// originName
				at.setOriginName(multiRequest.getOriginalFileName("upfile"));
				
				// changeName
				at.setChangeName(multiRequest.getFilesystemName("upfile"));
				
				// filePath
				at.setFilePath("resources/board_upfiles");
				
			}
			
			// 4) 요청처리 Service 호출
			new BoardService().insert(board, at);
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
