package com.kh.java.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.kh.java.common.MyRenamePolicy;
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
			
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
