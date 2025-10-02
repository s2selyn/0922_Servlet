package com.kh.java.board.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

@WebServlet("/insert.image")
public class ImageInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ImageInsertController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1) 인코딩 -> 이거 중복이라서 너무 속상하다, 중복 없애고싶어!
		request.setCharacterEncoding("UTF-8");
		
		// 2) 첨부파일 -> multi/form-data -> 조건문 -> 서버로 파일을 올려주자
		if(ServletFileUpload.isMultipartContent(request)) {
			// 얘는 파일이 required라서 꼭 있어야함
			
			// 1) MultipartRequest 생성 -> 이걸 코드로 빼놓으면 메소드 호출로 쓸 수 있었을텐데, 안빼놔서 계속 써야하네ㅠ
			// 1_1. 용량 -> 대충 0 여덟개면 100메가여~ 이미지니까 100메가 ㄱㄱ
			int maxSize = 100000000;
			
			// 1_2. 경로
			String savePath = request.getServletContext()
									 .getRealPath("/resources/image_upfiles");
			// 이름 변경 규칙도 바꾸고싶지만 그냥 하기로 함
			
			// 2) 객체 생성과 동시에 파일 업로드
			MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new MyRenamePolicy());
			// 이까지 하고 파일 잘 올라가는지 테스트
			// 파일업로드 끝!
			
			// 뽀드랑 아따치먼트랑 인서트
			
			// 3) multiRequest참조해서 값뽑기 => getParameter() 호출
			String boardTitle = multiRequest.getParameter("title");
			String boardContent = multiRequest.getParameter("content");
			
			// boardWriter not null이라서 뽑아서 보내줘야함 -> session의 userInfo에 들었음
			// ??? 16:12 한줄로 쓰는 법
			
			// 여러 줄 쓰기
			HttpSession session = request.getSession();
			Member member = (Member)session.getAttribute("userInfo");
			Long userNo = member.getUserNo();
			String boardWriter = String.valueOf(userNo);
			
			// 4) 가공
			Board board = new Board();
			board.setBoardTitle(boardTitle);
			board.setBoardContent(boardContent);
			board.setBoardWriter(boardWriter);
			
			// Attachment
			// => 사진게시글 작성 양식 required
			// 게시글 당 최소 한 개의 첨부파일은 존재, 최대 네개
			// 한개는 무조건 있고, 두개부터 네개까지는 있을수도 있고 없을수도 있음
			// 요청을 보낼 때 1번에 넣고 4번에 넣어서 보낼수도 있음.. 전부 체크는 해야함(name 속성 1~4까지)
			// if로 체크하나요?
			/*
			Attachment at1 = null;
			Attachment at2 = null;
			Attachment at3 = null;
			Attachment at4 = null;
			if(multiRequest.getOriginalFileName("file1") != null) {
				at1 = new Attachment();
				at.setOrignsd
			}
			if(multiRequest.getOriginalFileName("file2") != null) {
			}
			if(multiRequest.getOriginalFileName("file3") != null) {
			}
			if(multiRequest.getOriginalFileName("file4") != null) {
			}
			*/
			List<Attachment> files = new ArrayList();
			// ??? 16:20 리스트 쓰는 이유
			
			// 키값 file1 ~ file4
			for(int i = 1; i <= 4; i++) {
				
				String key = "file" + i;
				// System.out.println(key);
				// 되긴 함
				// 반복이 5개 안넘으면 그냥 if 여러번 하는게 성능적인 측면에서 이득이 좋다
				
				// 조건검사 name속성값을 이용해서 파일이 있는가? 없는가?
				if(multiRequest.getOriginalFileName(key) != null) {
					// 파일이 존재한다.는 뜻
					
					// 파일이 존재하면 list에 넣어야하는데, list에는 attachment밖에 못들어가니까 -> 생성
					Attachment at = new Attachment();
					at.setOriginName(multiRequest.getOriginalFileName(key));
					at.setChangeName(multiRequest.getFilesystemName(key));
					at.setFilePath("resources/image_upfiles");
					
					// files.add(at); -> 파일레벨 지정하고 추가하는것으로 옮김
					// 리스트에 파일 추가
					
					// 설명 할 것이 있음
					// 대표이미지 목록에서 보여줄거라서 이 파일이 대표이미지 인지 아닌지 구분을 해주는 체크를 해야함
					// 이걸 컬럼으로 구분하려고 FILE_LEVEL을 만들었음
					// 대표이미지는 FILE_LEVEL = 1, 나머지는 2로 해줌
					// 2, 3, 4 부여하면 출력위치까지 지정할 수 있게 구현하지만 지금은 그냥 대표이미지만
					// 어쨌든 대표이미지의 NAME 속성갑 == file1
					// key값이 file1이면 filelevel을 1로 넣고
					// 나머지는 set을 2로
					// 조건을 써야 한다
					/*
					if(i == 1) {
						// "file1".equals(key)
						// 문자열 비교보다 정수값 비교가 경제적이지 않나? 해서 조건을 바꿈
						// 반복문 첫바퀴에는 i가 1일테니까
						
						at.setFileLevel(1);
						
					} else {
						at.setFileLevel(2);
					}
					*/
					
					// 근데 이것도 최종은
					at.setFileLevel(i == 1 ? 1 : 2);
					// 이렇게 가능하다
					
					// 한번에 한줄쓰기를 도전하려고 하지말고 차근차근
					// 옛날에 쓴 코드 다시보고 새로배운거로 어떻게 바꿀지 생각해보고
					// 나중에 이해안됐던걸 이해하는 과정도 필요
					
					files.add(at);
					
				}
				
				// 요청처리 -> 서비스단으로 전달(이미지 무조건 있으니 이 스코프에서 한다)
				// 블럭 관리에 유의
				new BoardService().insertImage(board, files);
				
			}
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
