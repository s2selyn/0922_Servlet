package com.kh.java.board.controller;

import java.io.File;
import java.io.IOException;

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

@WebServlet("/update.board")
public class BoardUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardUpdateController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1) POST 인코딩
		request.setCharacterEncoding("UTF-8");
		
		// 2) 값뽑기전
		// 원래대로라면 우리가 값뽑기를 해야하는데, multipart로 전송되니까 request에서 getParameter로 뽑을 수 없음
		// 값뽑기 전에 multipart 방식으로 전송되면 항상 해야할것 if문 써서 잘 왔는지 확인
		// multipart방식으로 요청이 잘 왔는가 확인
		if(ServletFileUpload.isMultipartContent(request)) {
			// 오타없는지, 요청보내다문제생겨서잘못된게 없는지 등 양방향으로 문제가 없는지 체크하고
			
			// 파일 업로드 | MultipartRequest객체 생성
			// 수정하기 요청할 때 파일을 선택할수도 있음(새로운 첨부파일이 있을수도 있음, 기존첨부파일 바꾸거나 없던것을 추가하거나)
			// 파일 업로드 작업할 때 미리 세팅해야할 두가지
			// 1. 전송파일 용량 제한
			int maxSize = 1024 * 1024 * 10;
			
			// 2. 파일을 저장할 물리적인 경로(논리적인 경로를 전달해서 알아냄)
			// ??? 9:38 request 객체를 이용하기까지 생각하는 과정
			String savePath = request.getServletContext()
									 .getRealPath("/resources/board_upfiles");
			
			// Multipart객체 생성
			MultipartRequest multiRequest = new MultipartRequest(request,
																 savePath,
																 maxSize,
																 "UTF-8",
																 new MyRenamePolicy());
			// 이 코드를 만나는 순간 요청 보낼 때 첨부한 파일이 savePath로
			// 파일을 서버에 업로드하기 끝
			// 우리 맘대로 이름 바꾸기 규칙을 만들었음 -> MyRenamePolicy
			// 이건 파일 업로드임, 파일을 올리는 작업, 우리는 여기 board, attachment 업데이트하러온건뎅...
			// 그거랑은 연관이 없고 파일을 서버에 올리는 작업, 사실 여기서 하면 안됨
			// 그리고 이 코드가 insert board에도 똑같이 있음, 중복코드를 만들어버린것
			// 얘는 파일을 업로드 담당하는 클래스를 만들어서 빼놓고 메소드 호출해서 하는게 맞다
			// 아직은 익숙하지 않으니 한번더해본것, 전에한거 복사해서 클래스 만들어서 거기 빼놓고 메소드 호출해서 하면됨
			
			// 그리고 나서 원래대로 했어야 했던 값뽑기작업을 해야함
			
			// 이 서블릿에 온 목적
			// KH_BOARD UPDATE
			// KH_ATTACHMENT 무조건 UPDATE는 아님! 경우의 수 생각해야함
			
			// case 1. 첨부파일이 없음(수정요청을 하는데 첨부파일 없이 요청을 보냄) => BOARD UPDATE + AT X
			// case 2. 첨부파일 O, 기존 첨부파일 O => BOARD UPDATE + AT UPDATE
			// case 3. 첨부파일 O, 기존 첨부파일 X => BOARD UPDATE + AT INSERT
			// 뭘 하든 BOARD UPDATE는 무조건 하는데, 첨부파일 조건에 따라 KH_ATTACHMENT 해야하는 작업이 다 달라짐
			// case 2를 어떤 사람은 삭제하고 새로 INSERT 해야지 할수도 있지만 그럼 작업이 두번이니 한번으로 줄이자
			// 각 작업을 할 때 필요한 값이 다르기 때문에 생각을 잘해야해
			// 지금단계에서 우리는 case 2는 못한다. 왜? BOARD랑 똑같음, BOARD는 update 하려고 앞에서 번호 들고왔음
			// 그러니까 update하려면 기존 첨부파일이 뭔지 식별할 값이 있어야 첨부파일 테이블에 가서 업데이트 가능
			// 헤헤 아까 돌아가기로 했으니 다시 돌아가자 -> update_form.jsp ㄱㄱ
			// fileNo 넘기는 코드 작성하고 돌아왔음
			
			// 이제 multipart객체가 생성되었으므로 값뽑기 수행가능
			// 값뽑기
			String category = multiRequest.getParameter("category");
			String boardTitle = multiRequest.getParameter("title");
			String boardContent = multiRequest.getParameter("content");
			Long boardNo = Long.parseLong(multiRequest.getParameter("boardNo"));
			// board update용 값들
			
			// -----
			HttpSession session = request.getSession();
			Member member = (Member)session.getAttribute("userInfo");
			// -----
			
			// 하나에 담아야하니까 board 객체로 가공
			Board board = new Board();
			board.setCategory(category);
			board.setBoardTitle(boardTitle);
			board.setBoardContent(boardContent);
			board.setBoardNo(boardNo);
			
			// -----
			board.setBoardWriter(String.valueOf(member.getUserNo()));
			// -----
			
			// 아까 생각한 경우의 수, attachment 작업 해야할 수 있음
			// Attachment객체 선언만!!!
			// 실제 첨부파일이 존재할 경우에만 => 객체 생성
			// 파일이 첨부되었을때만 작업하자, 없으면 테이블에 작업할 필요가 없으니 null로 초기화
			Attachment at = null;
			
			// 존재할때만 -> 조건
			// 있는지없는지 확인은 뭘해봐야 알수있음? 파일 이름이 있으면 된다
			// -> 이건 multiRequest에 getOriginalFileName메소드 호출하면서 인자값으로 input type="file"의 name 속성값
			if(multiRequest.getOriginalFileName("reUpfile") != null) {
				
				// 새로운 첨부파일이 존재하면 객체 생성 후 원본명, 바꾼명, 경로 담기
				at = new Attachment();
				at.setOriginName(multiRequest.getOriginalFileName("reUpfile"));
				at.setChangeName(multiRequest.getFilesystemName("reUpfile"));
				at.setFilePath("resources/board_upfiles");
				
				// 이제 첨부파일이 있는데 update할지 insert할지 두개로 나뉜다 -> 해야할 작업이 달라짐
				// INSERT / UPDATE
				// INSERT : 이 첨부파일은 어떤 게시글에 달리는가? 몇번 게시글에 달릴지 필요함, ref_bno 컬럼에 들어갈 값이 필요
				// UPDATE : 원래 있던 파일이 몇 행이냐??
				
				// 내가 무슨값으로 어떤연산을 해서 어떤 조건을 걸 지 이걸 생각
				// Attachment 객체에 업데이트 하려면 파일번호, 인서트 하려면 REF_BNO를 넣어줘야함
				// 뭘 봐야 INSERT/UPDATE 구분가능? 앞에서 원본파일이 있으면 파일넘버를 넘김, 그러므로 파일넘버가 널이 아니면 업데이트
				// 파일넘버가 몇번일지는 모르지만, 원본파일이 없었다면 파일넘버는 null, 그럼 insert 해줘야함
				// 파일 넘버가 몇이든 이것은 원본파일이 있다는것이니까 update 해줘야함
				// 파일넘버가 있을때/없을때 다른작업
				// 파일넘버가 널인지 아닌지는 뭘 봐야 알수있나? multiRequest
				if(multiRequest.getParameter("fileNo") != null) {
					
					// 새로운 첨부파일이 있음 + 원본파일도 있었음
					// ATTACHMENT => UPDATE => 원본파일번호가 필요함
					// 기존 파일이 가지고 있던 fileNo at의 fileNo필드에 담아줄것
					at.setFileNo(Long.parseLong(multiRequest.getParameter("fileNo")));
					
					// 업데이트하면 첨부파일 테이블에 올라가있던 정보를 바꾸는거니까 원래 서버에 올라온 파일은 접근을 못함, 필요없음
					// 그러면 지워주는(삭제) 작업을 할까?
					// 뭐가 있어야 삭제할 수 있나? 경로랑 파일명, 경로는 같으니까 이미 갖고있음
					// 원본 파일의 changeName이 없다, 이게있어야 지우는데 어떻게하지?
					// 1. DB가서 들고온다, 2. 앞에서 넘긴다(changeName이 있음, update_from.jsp에서)
					// 앞에서 넘기는걸로 하자 , 사용자에게 보여줄 필요없음 -> update_form.jsp 가서 파일명 넘기는 코드 작성
					
					// 기존에 존재하던 첨부파일 삭제
					// 언제 해봤음? insert board 할 때 실패하면 파일 지우는거
					new File(savePath + "/" + multiRequest.getParameter("changeName"))
							.delete();
					
				} else {
					
					// 새로운 첨부파일이 있음 + 원본파일은 없었음
					// ATTACHMENT => INSERT
					// 어떤 게시글의 첨부파일인지 (REF_BNO)
					at.setRefBno(boardNo);
					
				}
				
			}
			
			// 요청처리
			// UPDATE 1
			// UPDATE 2
			// UPDATE 1 + INSERT 1
			// 작업 자체는 서비스에서 해야함
			int result = new BoardService().update(board, at);
			// 결과는 int로 받음
			
			// 돌아온 결과로 응답화면 지정
			if(result > 0) {
				
				// 성공했다면 alert메세지 출력
				session.setAttribute("alertMsg", "게시글 수정 성공~");
				
				// 상세보기 페이지로 redirect
				// http://localhost:4000/kh		/detail.board?boardNo=번호
				response.sendRedirect(request.getContextPath() + 
									  "/detail.board?boardNo=" + 
									  boardNo);
				
			} else {
				
				request.setAttribute("msg", "게시글 수정에 실패했어요..");
				request.getRequestDispatcher("/WEB-INF/views/common/result_page.jsp")
					   .forward(request, response);
				
			}
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
