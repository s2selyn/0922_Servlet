package com.kh.java.board.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.java.board.model.service.BoardService;

@WebServlet("/detail.image")
public class ImageDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ImageDetailController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 궁극적으로 DB에 셀렉트 두번하기 -> 넘버가 필요
		// Long boardNo = Long.parseLong(request.getParameter("boardNo"));
		// 사실 얘도 이렇게 하면 안됨, 악의적인 사용자가 악의적인 목적으로 요청을 보낼 때 번호를 지워서 보내면 NumberFormatException이 발생함, null값이 오기 때문
		// 이걸 먼저 문자열로 뽑고
		// String boardNo = request.getParameter("boardNo");
		
		// 빈 문자열이거나 널이 아니라면? 널체크 해야하니까 boardNo != null 이건 확정
		// boardNo. 참조하면 isEmpty, isBlank 메소드가 있음
		// if(/*!("".equals(boardNo)) ||*/ boardNo != null || !boardNo.isEmpty()) {
		// Long num = Long.parseLong(boardNo);
		// }
		// 이런식으로 하거나 하다못해 try-catch로 묶어야했음, 우리는 흐름을 중요하게 함
		Long num = Long.parseLong(request.getParameter("boardNo"));
		
		// 가공할건 없고 boardNo만 넘어가면 되니 service로 넘길거고
		// 나중에 받아줘야하는 돌아올값은 어떤 모양? 지금의 dto는 못쓴다, board는 하나지만 첨부파일이 여러개 있을수있음
		// board에 달린 첨부파일이 몇개인지는 알 수 없음, 하나부터 네개까지 랜덤이고 개수만큼 들고와야함
		// 대표이미지가 있었다면 board 1 + at 1
		// 이미지가 네개라면 board 1 + at 4
		// board 1은 고정, at는 1~4니까 list에 담자, 애초에 dao에서 호출할 수 있는 메소드가 selectOne or selectList
		// board하나에 list하나를 같이 들고가야함
		// 일반게시글 상세보기 어떻게 했는지 생각 -> board조회, at조회, 한번에 가려고 map에 담음
		// 지금도 같은 케이스, board 하나는 확정, 나머지는 list, 서비스에 하나로 돌아가야함 -> map
		
		// 문제해결상황 대부분은 비슷한 경험이 있으니 앞에서 해본 것을 바탕으로 적용해서 해결가능
		// 최종목적은 db에 뭘 넣거나 가져오거나, 지금 하고있는건 그런 방법들이니 생각하고 복습하기, 기억이 있어야 메소드하지
		
		Map<String, Object> map = new BoardService().selectImageDetail(num);
		
		// System.out.println(map);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
