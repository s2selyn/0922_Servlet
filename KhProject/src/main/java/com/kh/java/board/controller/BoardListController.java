package com.kh.java.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.java.board.model.service.BoardService;
import com.kh.java.board.model.vo.Board;
import com.kh.java.common.vo.PageInfo;

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
		// System.out.println(currentPage);
		
		pageLimit = 10;
		boardLimit = 10;
		// 아까 정한 대로 대입하면 되는데 계산 편하게 하려고 10으로 둘다 작성 후 나중에 5로 고치자
		// 산수 해야함, maxPage 계산해야하거등
		// * maxPage : 가장 마지막페이지가 몇 번 페이지인지
		// 지금은 3이다 근데 항상 3이 아님, 게시글 하나라도 늘어나면 4, 100이 늘어나면 13이 된다?
		// 게시글의 개수에 따라서 유동적으로 변하는 값, 변하도록 만들어줘야한다
		/*
		 * 뭐랑 뭐로 생각했더니 maxPage가 나왔는지 생각
		 * listCount, boardLimit에 영향을 받음
		 * 총 게시글 개수와 한 페이지에 몇개씩 보여줄지(페이지 리밋은 페이징 버튼 개수니까 상관없음)
		 * 
		 * 처음 하는거니까 계산 쉽게 하자
		 * - 공식구하기
		 *   단, boardLimit이 10이라고 가정
		 * 
		 * 총 개수		한페이지		나눗셈 결과		마지막페이지
		 * 100		/	  10	=		10.0			10
		 * 107		/	  10	=		10.7			11
		 * 111		/	  10	=		11.1			12
		 * 
		 * 나눗셈만으로는 해결이 안되고
		 * 나머지라고 하면 안된다, 자바인것을 인지하고 자바를 생각해야함
		 * int / int이므로 결과가 int로밖에 나오지 않는다
		 * 우리가 필요한 것은 소숫점 자리가 있으면 될 것 같은데? 그걸 올림처리 하면 마지막페이지가 나올듯, 0만 아니면 올라간다
		 * => 나눗셈(listCount/boardCount)의 결과를 올림처리할경우 maxPage가 나옴
		 * 
		 * int/int로는 소수점이 있는 나눗셈 결과를 얻어낼 수 없다
		 * 결과가 실수형이라면 실수와 실수를 연산해야 실수 결과가 나온다
		 * 나눗셈을 하기 전에 정수를 실수로 바꿔줘야함, 둘중에 아무거나 하나만 실수로 바꿔주면 된다
		 * 하나가 double이면 나머지 하나가 double에 맞춰서 연산된다, 작은거랑 큰거 연산하면 작은게 큰거로 바뀌어서 연산(promotion)
		 * 앞에껄 바꾼다고 치자
		 * 스텝
		 * 1. listCount를 double로 변환 -> (double)listCount
		 * 2. listCount / boardLimit -> (double)listCount / boardLimit 이러면 나눗셈 결과가 나온다
		 * 3. 결과를 올림처리 => Math.ceil()
		 * 4. maxPage에 대입해야하는데 double인 상태이므로 int형으로 강제형변환
		 * 
		 * 자바라는 프로그래밍 언어의 특성을 생각해서 산수 해야한다
		 * 그럼 이제 한페이지에 무슨 값이 리터럴로 들어가든 상관없는 공식이 됨
		 * 
		 */
		maxPage = (int)Math.ceil((double)listCount / boardLimit);
		
		// * startPage : 페이지 하단에 보여질 페이징 버튼 중 시작 값
		// 이것도 고정값이 아니고 예를들어서 10을 넘어가면 11페이지부터 보인다, 그리고 20페이지를 넘어가면 21 이런식
		// 이 값은 어떤 숫자의 영향을 받아서 정해짐?
		/*
		 * currentPage, pageLimit에 영향을 받음
		 * 한 번 보여줄 때 페이지 버튼 몇개 보여줄지에 의해서도 영향을 받음
		 * 
		 * - 공식 구하기
		 * 	 단, pageLimit이 10이라고 가정
		 * 
		 * startPage : 1, 11, 21, 31 ... => n * 10 + 1(등차수열)
		 * 
		 * 만약에 pageLimit이 5라고 가정
		 * startPage : 1, 6, 11, 16 ... => n * 5 + 1
		 * 
		 * 10과 5의 자리에 우리가 설정하는 pageLimit이 들어간다
		 * 즉, startPage == n * pageLimit + 1;
		 * 
		 * n이 뭔지만 알면된다
		 * 이것도 쉽게 생각해보자
		 * 
		 * pageLimit이 10이라고 가정(한 페이지의 페이징 버튼은 10개씩 보여준다)
		 * currentPage			startPage
		 * 		1					1
		 * 		5					1
		 * 		10					1
		 * 		11					11
		 * 		13					11
		 * 		17					11
		 * 		20					11
		 * 		21					21
		 * 		30					21
		 * 
		 * 		1 ~ 10	/	10 => 0 ~ 1(10으로 나누면 1부터 9까지는 0이 나오다가 10으로 나누면 1이 나옴)
		 * 	   11 ~ 20  /   10 => 1 ~ 2
		 * 	   21 ~ 30  /	10 => 2 ~ 3
		 * 
		 * 마지막 친구들이 자기를 페이지 리밋값으로 나눈거라 1씩 올라가버림, 근데 이건 필요없어서 1씩 빼버리고싶음
		 * 그럼 나누기 전에(currentPage)에서 1씩 빼줌
		 * 
		 * 		0 ~ 9   /	10 => 0
		 * 	   10 ~ 19  /   10 => 1
		 * 	   20 ~ 29  /   10 => 2
		 * 
		 * n = (currentPage - 1) / pageLimit
		 * 
		 * startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		 * 
		 */
		startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		
		
		// * endPage : 페이지 하단에 보여질 페이지 버튼의 끝 수
		// 이건 startPage와 pageLimit을 보고 구하면 된다
		/*
		 * startPage, pageLimit에 영향을 받음
		 * (maxPage도 영향을 끼침)
		 * endPage가 maxPage보다 큰 숫자가 나온다면 maxPage를 바꿔줘야하는 상황이 된다
		 * 
		 * - 공식을 생각해보자
		 * 	 단, pageLimit이 10이라는 가정
		 * 
		 * startPage : 1 => endPage : 10
		 * startPage : 21 => endPage : 30
		 * 
		 * endPage = startPage + pageLimit - 1;
		 * 
		 */
		endPage = startPage + pageLimit - 1;
		// 현재 최대한 풀어서 하고 있는데 Math class 사용하면 더 간략하게 가능하지만 산수 레벨
		// 공식이기 때문에 boardLimit, pageLimit에 상관없이 구할 수 있게 된다.
		// 총 게시글, 현재페이지 번호에 상관없이 똑같이 쓸 수 있음
		
		// 근데 벌써 문제가 생김, pageLimit 10, boardLimit 5로 했는데 현재 게시글 30개라서 6페이지가 나올 수 있음
		// endPage가 10으로 나온다. 페이징 버튼 6개까지만 보여줄게 있고 7부터 10까지는 보여줄게 없음
		// endPage를 maxPage로 바꿔주자
		// startPage가 1이라서 endPage에 지금 10이 들어있는데
		// maxPage가 6이라면??(7 ~ 10에서 보여줄 게 없다)
		if(endPage > maxPage) {
			
			endPage = maxPage;
			// endPage에 maxPage를 대입해줘야함
			
		}
		
		// 현재 몇페이지를 요청한지, 한페이지에 몇개 보여줄지, 총 게시글수가 몇개인지에 따라 값이 바뀌니까 리터럴을 쓸 수는 없다
		// 산수해서 사용자가 요청한 페이지에 따라 시작 페이징 버튼을 만들어줄것을 생각, 마지막 버튼 등 시작값 끝값을 정하고 반복문을 통해 생성
		// startPage부터 endPage까지 버튼 생성
		
		// DB에 게시글이 30개가 있음
		// DBeaver에서 설명
		int offset = (currentPage - 1) * boardLimit;
		
		// 변수 8개 생김, DB도 가야하고 페이징 버튼도 만들어야하는데 관리하기 힘들다 -> 이 여덟게 담을 VO 클래스 생성
		// 게시판 만드는 사람들은 전부 사용가능한 친구, 페이징처리하려면 다 필요함 -> common에 vo 패키지를 만들자
		PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, startPage, endPage, maxPage, offset);
		// System.out.println(pi);
		
		// 이제 진짜 DB 가보자, 만든 변수들로 게시글 내용 조회해와서 출력해보기
		// 나중에 컨트롤러에 돌아왔을 때의 형태는? 5행 조회 -> List -> 뭐가 담김? Board 모양이 담긴 리스트(제네릭)
		// select로 시작하고싶으면 BoardList로 메소드 이름 작성, offset이랑 boardLimit이 필요하니 넘겨준다
		List<Board> boards = new BoardService().selectBoardList(pi);
		
		System.out.println(boards);
		
		
		
		
		
		
		
		
		
		
		
		
		// jsp로 연결되는지 확인하기 위해서 requestDispatcher
		request.getRequestDispatcher("/WEB-INF/views/board/board_list.jsp")
			   .forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
