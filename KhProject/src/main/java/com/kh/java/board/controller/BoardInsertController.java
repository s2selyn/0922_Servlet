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
		
		// 1) 인코딩 설정(UTF-8)
		// UTF-8이 전세계 표준, 전세계 모든 개발자, 자바개발자들 전부 UTF-8로 작업함
		request.setCharacterEncoding("UTF-8");
		
		// 2) 값뽑기
		// 사용자가 인풋요소 제목에 입력한거 뽑아보기
		// String title = request.getParameter("title");
		// System.out.println(title);
		// 원래라면 입력값이 나와야하는데 null값이 나옴, 지금 이게 정상
		// enctype="multipart/form-data"로 요청을 보내면 리퀘스트에서 getParameter로 값을 뽑을 수 없음
		
		// form 태그 요청 시 multipart/form-data형식으로 전송한 경우
		// request.getParameter로는 값을 뽑아낼 수 없음
		// 그래서 이번에 컨트롤러에서 주로 할 내용은 값을 어떻게 뽑아내야 하는지임
		// 멀티파트 방식으로 데이터를 전송했을 때 값을 뽑는 방법
		// enctype 속성값 오타가 나서 요청이 잘 안들어오거나 요청방식이 post가 아니거나 그런 문제가 일어날때가 있음
		// 스텝 0) 요청이 multipart방식으로 잘 왔는가부터 확인
		if(ServletFileUpload.isMultipartContent(request)) {
			// 이걸 위해서 아까 라이브러리를 추가했음, ServletFileUpload 클래스의 메소드 사용
			// 매개변수 타입이 HttpServletRequest임, 인자로 넣으면 멀티파트 방식으로 잘 들어왔는지를 true/false 값으로 반환해줌
			// 이걸 먼저 쓰고 시작한다, 안쓴다고 안돌아가는건 아닌데 이 작업을 보통 먼저한다
			
			// 멀티파트 방식으로 요청을 보낸다는 것은 파일을 첨부하겠다는 뜻, 안에서 하는 작업 추가(원래 클래스 빼서 작업해야함, 그냥 처음이니 여기서함)
			// 스텝 1) 전송된 파일을 위한 작업
			// 1_1. 전송파일 용량 제한(최대 어느정도 크기의 파일까지 받을건지)
			// 서버로 파일을 받을건데, 각자의 서버 컴퓨터에 저장을 하겠다는 의미, 그럼 하염없이 큰 용량의 파일을 다 받을 수 없음, 용량의 한계가 있다
			// 어느정도 크기의 파일까지 받을건지 정함
			/*
			 * 컴퓨터 용량 단위 정리
			 * 
			 * bit X 8 => Byte => KByte => MByte => GByte => TByte => PByte => ...
			 * 
			 * 가장 작은 단위는 비트, 0 또는 1이 들어갈 수 있는 한칸을 1비트라고 표현
			 * 비트 8개를 모으면 바이트, 1바이트(바이트에 8씩 곱하면 비트, 4바이트는 32비트 이런식)
			 * 개발자들은 이쪽이 가깝고 위쪽은 멀다
			 * 바이트를 1024개 모으면(비트 8개짜리를 1024개 모으면) 킬로바이트
			 * 킬로바이트 1024개 모으면 메가바이트
			 * 메가바이트 1024개 모으면 기가바이트
			 * 기가바이트 1024개 모으면 테라바이트
			 * 테라바이트 1024개 모으면 펜타바이트
			 * 뒤에 더 있는데 흔히 말하는 데이터센터도 PByte정도, 그이상은 보기힘들다
			 * 개발자들은 일반적으로 MByte까지 갖고논다, 효율충이라서 작은쪽 신경써야함 큰쪽은 몰라 알빠야
			 * 영상은 용량이 크다, 원본, 컷편집, 일러스트반영, 화질계산 등 많이 필요함
			 * 우리는 10메가만 쓰자...
			 * 이거 정해놓으면 이것보다 큰 파일 올라왔을 때 예외발생함
			 * 
			 * 10MegaByte
			 * 
			 * int형으로 정한다, 10메가 할건데 바이트 단위라서 10메가 하려면? 어림잡아 0 일곱개 정도
			 * 이런 계산은 컴퓨터가 해라, 구글가서 10메가가 몇바이트냐 이런거 치지말고
			 * 
			 */
			int maxSize = 10 * 1024 * 1024;
			// 1 * 1024 * 1024 하면 1 MByte, 1을 10으로 바꿔서 10 MByte
			// 지금 선생님은 용량없어서 10메가, 100메가 정도 잡아도 된다
			
			// 1_2. 서버의 저장할 폴더 경로 알아내기
			// 파일 업로드하면 넣을 곳 -> webapp/resources/board_upfiles에 저장할것임
			// 폴더의 경로를 알아내야하는데 자바 클래스에서 바깥의 경로를 알아낼 방법? 소스폴더도 아님, 그냥 바깥에 있는 웹앱 폴더 밑에있음
			// 클래스에서 접근 가능할까? 프로젝트 레벨이라 아예 클래스 밖으로 나감
			// 이런 조건범위를 가진 객체가 있을까? 있음! 스코프 객체 배웠잖아
			// pageContext는 자기가 사용되는 jsp에서 벗어날 수 없음
			// HttpServletRequest는 맨날 서블릿에 두개 붙어있음, 이건 어디부터 어디까지? 요청받은 서블릿의 doGet메소드부터 jsp까지 포워딩해준다, 요청 받은 서블릿부터 응답 만들어주는 포워딩 jsp까지 접근가능
			// HttpSession은 더 큼, 어디부터 어디까지 접근가능? 이거 활용했던 방법 기억, 사용자의 정보를 어디에서 어떻게 활용했더라? 로그인이라고 가정하면 로그인 처리하는 서블릿에서 로그인된 사용자를 세션스코프에 담음
			// 그리고 마이페이지든 board_enroll form이든 모든 jsp에서 세션에 담은것을 쓸수있고, 다른 모든 서블릿에서도 세션에 담은 것을 꺼내서 쓸 수 있음
			// 내가 뭐 생각해야 할 때 어떻게 활용해서 어디서 어떻게 썼는지 내가 작업한 내용으로 생각
			// 세션에 한번 담았으면 모든 서블릿에서 다 쓸 수 있고 jsp에서 다 쓸수있음, 우리가 그렇게 썼음
			// 세션 얘기를 제일 처음에 ElServlet에서 했음, 시작하기 전에 써보기전에 설명해야해서, 뭔지도 모르고 따라쓰면 안되니까
			// 이때 얘기해봤자 이해는 안감, 그래도 설명안적고 따라치라고 하면 좀 그럴까봐 일단 써주심ㅋㅋ
			// 적었는데 우리가 기억못하고 연관못할것같으면 또 써주시는데 그래봤자 이해안되고 내가 계속 사용해봐야 이해가 된다
			// 그때가 돼야 이해가 된다, 그러니까 복습좀 하자, 일단 적어놓고 설명이 이해가 안돼도 나중에 많이 써보고 활용하고 써보고 다시보면 복습하면 완성됨
			// 선행보다는 복습이 중요, 기억력은 3주가 끝, 루틴 지키기는 쉽지않지만 3주마다 해야함 ,복습좀 열심히하자
			// 이론적으론 OK
			// !=
			// 칠려면 아 어렵다.. 아리까리하다..
			// 이거 두개는 완전히 별개, 언젠가는 상호보완적인 관계가 되지만 최소 1년정도 걸린다, 그전까지는 별개, 책많이 보면 느는것과 많이 치면 느는것이니 둘다 해야함, 상호보완적 관계가 될때까지, 벽이있음
			// 1년안에 되는 그런내용은 아니고 최소 1년은 해야, 쌍방향으로 따로 시간 투자하시오
			// 세션이 서블릿, 모든 jsp 다 쓸 수 있고
			// 세션보다 큰 친구 -> ServletContext, 얘는 Application Scope
			// 모든 프로젝트, 전체 프로젝트 모든 자원에 접근해서 다 사용하고 다 가능, 클래스건 서블릿이건 제일 다 상관없이, 심지어 접근하려고 하는게 자바랑 관계없이 상관없이 프로젝트 안의 무언가다 그럼 싹 다 접근 해버릴수있음
			// 이번에는 ServletContext를 이용해서 applicationScope를 사용해야함
			// 이 타입이 가진 메소드 중에 getRealPath() 메소드를 호출해서 사용해야함
			// 일단 ServletContext타입 객체 컨테이너가 필요
			// 어떻게 얻나? 세션 얻을 때 request를 쓴다
			// 그럼 서블릿 컨텍스트 얻을때는 누구쓸까? 세션에 보면 getServletContext 메소드가 있다, 이게 ServletContext타입 반환
			// 근데 사실 request로도 얻을 수 있음, request에도 ServletContext 똑같이 있음
			// 뭘로 해도 상관없는데 session도 사용해야해서 미리 받아놓는다
			HttpSession session = request.getSession();
			// request.getServletContext(); 이렇게 해도 되는데 안할것이여
			ServletContext application = session.getServletContext();
			// 애플리케이션 객체 생성완
			
			// getRealPath메소드는 물리적 경로를 얻어내는 메소드, RealPath는 실제 진짜 하드웨어(저장장치)에 있는 경로를 얻어냄
			// 인자(파라미터)로 버추얼 패스(가상의 경로)를 받음, 이걸 물리적 경로로 변환해주는 메소드
			// board_upfiles는 가상의 경로, 웹브라우저에 프로젝트에서 사용할 수 있는 가상의 경로
			// 이걸 쓰면 실제 물리적 경로를 반환해준다는데 감이 안오니까 찍어보자, 서버 다시 시작하고 게시글 작성하고 테스트
			String savePath = application.getRealPath("/resources/board_upfiles");
			// System.out.println(savePath); 이걸로 경로를 얻고싶은것임
			// 귀찮은데 그냥 쓰면 되지 않느냐고 생각할수있다, 써도되는데 그러면 로컬에서만 잘 된다, 사용자마다 경로가 다 다름
			// 운영, 배포환경이 달라진다면 내용이전부 달라짐, 이 경로가 존재하는 컴퓨터만 사용 가능한 코드, 그래서 이걸 작성해서 자기의 경로를 얻음
			// 장점 : 동적으로 실제 경로 확인해서 서버 환경에 상관없이 동작 가능
			// 단점 : 나중에 압축해서 배표할수도 있는데(WAR파일 배포 시) 그럼 파일이 사라질수도 있음
			// 온프레미스환경? 이라면 아예 외부 디렉토리를 가지고 작업하는게 좋고
			// 현시대 파일 업로드 표준은 아마존 s3, 표준은 s3버킷을 사용하는것, 그건 세미프로젝트 이후에 배우는거라서 그전까지는 로컬환경에서 작업
			// 이것은 이런 개발용 테스트용으로 사용하는 코드
			// 파일 저장할 경로 알아냈음
			
			// 이제 진짜 파일 업로드 해보자
			// 스텝 2) 파일 업로드하기
			// 문제가 있음, 폴더안에 파일 올리는건데 사용자가 많을 수 있음, 다른사람이 똑같은 이름의 파일을 업로드할수도 있다
			// 폴더 내부에 동일한 이름의 파일이 존재할 수 없음. 그대로 올라가면 예외가 일어나거나 기존 파일이 새 파일로 덮어쓰기됨
			// 다행인점 : 아까 cos 라이브러리 추가해뒀음(무슨약자? Cloud Object Storage)
			// COS는 파일이 똑같은 이름이라면 이름을 바꿔준다, 충돌이 안나야함
			// a.jpg a2.jpg a3.jpg
			// 근데 구림, 흔히 이걸 넘버링한다고 하는데 좋은 방식은 아님
			// 그래서 일반적으로 개발자들은 파일 업로드가 된다면 파일이름을 다 바꿔버림
			// 카톡으로 받은 파일명 같은경우에 파일명이 바뀌어서 온다, 카톡의 이름 바꾸기 정책인거지
			// 만약에 a.jpg를 보냈는데 파일명이 KakaoTalk_20241205_181108376.jpg
			// 이렇게 온것을 구조 파악해보자
			// 앞은 겉멋ㅋㅋ 자기네꺼 카톡붙인거, 언더바하고 연월일, 그리고 다시 언더바 이후에 시분초인듯
			// 그리고 같은 년월일시분초에 동시전송할 경우 구분을 위한 랜덤값인듯, 확장자는 원본것을 따온ㄴ듯
			// 이런식으로 바꾸고싶은 느낌으로 바꾼다, 우리도 이름을 바꿔보자
			// 테이블도 파일의 정보를 저장할 테이블을 만들었음, 원래 올린 업로드된 원본파일명 저장하고, 바꾼 파일명을 저장할 컬럼 두개
			// 그럼 예쁘게 하려고 파일 저장경로도 두개만들었음, 첨부파일 올릴 일반게시판 파일업로드용/사진게시판 파일업로드용
			// 올라갈 경로 저장할 컬럼도 만들었어, 우리도 해보자!
			// 파일 이름 바꾸기용 클래스 생성 -> 파일 업로드 할 여러 게시판에서 동시에 사용할수있게 common 패키지에 생성 -> MyRenamePolicy에서 작업
			// 작업하고 돌아와서 파일 업로드 작업 계속하자, 거기다가 객체 변환작업 추가로 해야함
			// enctype 멀티파트 폼 데이터 방식으로 데이터 요청을 보내면 요청시 전달값, 파일 같은 경우를 이 순서, http서블렛 리퀘스트 타입이라 값을 뽑아낼 수 없음
			// 값을 뽑아내려면 멀티파트리퀘스트로 이관한다고 표현, 내용을 이관한다
			// 변환해서 값을 뽑을 수 있으니 바꾸기는 객체를 만들어, 생성자 호출해서 하면됨
			/*
			 * HttpServletRequest
			 * =>
			 * MultipartRequest객체로 변환
			 * 
			 * [ 표현법 ]
			 * MultipartRequest multiRequest = 
			 * new MultiRequest(request, 저장경로, 용량제한,
			 * 					인코딩, 파일명을 수정해주는 객체);
			 * 
			 * 기본생성자 호출이 안되고 생성자 인자값으로 다섯개의 값을 넘겨줘야함
			 * 다 준비해놨음, request를 첫번째로 넘겨준다, 여기있는것을 옮겨야함
			 * 두번째는 애플리케이션으로 얻어온 패스, 저장 경로
			 * 세번째는 파일 용량 제한, 얼마나 되는 파일까지 받을건지
			 * 네번째는 인코딩 방식
			 * 다섯번째는 파일명 수정해주는 객체를 만들어서 넣어줌
			 * 
			 * 생성자 호출해서 객체 생성인데, 멀티파트 객체를 생성하면 파일이 업로드됨
			 * 이 코드를 만나는 순간 이 저장경로에 업로드한 파일이 올라감
			 * 
			 * Multipart객체를 생성하면 파일이 업로드!
			 * 
			 * 사용자가 올린 파일명은 이름을 바꿔서 업로드하는게 일반적인 관례
			 * 
			 * Q) 파일명 왜 바꾸나요?
			 * A) 똑같은 파일명 있을 수 있으니까
			 * 	  파일명에 한글 / 특수문자 / 공백문자 포함경우 서버에 따라 문제가발생
			 * 
			 * 개발할때는 한글 피해야함, 윈도우<->맥 이러면 난리남, 중국어 일본어 등 마찬가지
			 * 문제 발생 상황들을 막기 위해서 이름 변경 필수
			 * 
			 * 개발할때는 한글 없다고 생각하쇼
			 * 
			 * 워크스페이스 경로에 한글 있으면 나중에 파일도 안올라가고
			 * 컴퓨터 사용자계정도 영어여야함
			 * 파일명, 경로, 폴더명에 한글특문공백은 전부 존재하면 안되는 것들
			 * 집 컴퓨터 사용자 이름 한글이면 밀어버리쇼
			 * 
			 * 윈도우 운영체제 내장 프로그램중에 원격 접속 프로그램이 있음
			 * 그걸로 나중에 클라우드 서비스에 원격접속 작업해야하는데 한글 사용자 이름은 접속이 안됨, 저장할때마다 경고뜨고 브라우저 계속 열리고 문제생김
			 * 영어권 국가 사람들은 이런 문제가 일어나지 않아... 아무도 그런일을 겪지 못한다
			 * 근데 원인이 이건지 알려면 밀어봐야함
			 * 근데 밀어서 사용자이름 영어로 하니 다 해결된적이 있다네요 ㅎ
			 * 아무튼 한글 항상 회피. 무시무시한 친구임, 개발할때 한글 피해야한다
			 * 
			 */
			// 생성자 호출해서 객체 올릴건데 기본생성자 없음, 반드시 인자값 전달해야함
			// 리퀘스트 객체 보낼것임, 저장경로(아까 변수로 빼뒀음), 최대용량, 인코딩방식, 이름바꿔주는객체 클래스로 만들어뒀음
			MultipartRequest multiRequest = new MultipartRequest(request,
																 savePath,
																 maxSize,
																 "UTF-8",
																 new MyRenamePolicy());
			// 여기까지가 1절
			// 이 객체 생성이 파일 업로드하는것이므로 board_upfiles에 올라왔는지 확인, 이름 바뀌어서 저장됨
			// 정적 자원 배포하는 경로
			// kh/가 webapp을 의미하니까 이 아래의 resources/board_upfiles/파일명 으로 정적 자원에 접근가능
			// 이미지 태그, 다운로드 받게해주기 등을 이 주소를 이용해서 서버에 올라와있는것을 브라우저에 띄워줄수있게됨
			// 이건 파일 업로드 기능, 잘못된 코드, 보드 insert와 상관이 없다, 우리는 board insert 요청이 와서 여기로 들어온것임
			// -- 파일업로드 --
			// 무슨 요청이 와서 여기 왔더라? 클라이언트가 게시글 작성할래 요청을 했음
			// 파일은 부가적인것, 제목내용쓰고 파일첨부도 하고싶어서(파일첨부는 할수도있고 안할수도 있음)
			// 주어는 테이블에 1행 insert, 파일업로드까지는 테이블에 1행 insert와는 아무런 연관이 없음
			// 사용자가 파일을 첨부했을 때 파일을 서버에 올리는 과정! 구분 잘해야함
			
			// 실질적으로 우리가 해야 할 일
			// BOARD테이블에 INSERT하기
			
			// 리퀘스트에서 값이 안뽑혔음, 이제 MultipartRequest이걸로 하면 얘로부터 값을 뽑을 수 있음
			// 2) 값뽑기
			String title = multiRequest.getParameter("title");
			// System.out.println(title); 잘 뽑히는지 체크
			String content = multiRequest.getParameter("content");
			String category = multiRequest.getParameter("category"); // 이건 select
			
			// 사용자 번호가 있어야함, 이건 세션에서 얻음
			Long userNo = ((Member)session.getAttribute("userInfo")).getUserNo();
			
			// 3) 가공해야디~
			// 뭘로 가공할지 고민, 맵? 리스트? board로 결정, 이거 하려고 board 만든거임
			Board board = new Board();
			board.setBoardTitle(title);
			board.setBoardContent(content);
			board.setCategory(category);
			board.setBoardWriter(String.valueOf(userNo));
			// 여기까진 많이 해봤다
			
			// 업로드한 파일을 나중에 다시 사용할 수 있게 정보 저장을 해야함
			// 이걸 위해서 테이블도 따로만들었음 -> 파일의 정보를 테이블에 insert 하기 위해서 Attachment VO 생성
			
			// 3_2) 첨부파일의 경우 => 선택적
			// 첨부할수도 있고 안할수도있음, 첨부안했는데 insert할수는 없음
			Attachment at = null; // 그래서 선언만 해뒀음
			
			// 첨부파일의 유무를 파악
			// 원본파일 이름을 얻으려면 input 요소의 name 속성값을 메소드의 인자로 전달한다
			// System.out.println(multiRequest.getOriginalFileName("upfile"));
			// 코드를 만난 순간 파일이 올라감, 이 메소드를 호출했을 때,
			// 첨부파일이 있다면 "원본파일명" / 없다면 null값을 반환
			if(multiRequest.getOriginalFileName("upfile") != null) {
				// 첨부파일이 있을때만 작업하고싶은 내용
				
				// 첨부파일이 있다!!!!! => VO로 만들기
				at = new Attachment();
				
				// 필드에 값 담기
				// originName, if문에 들어왔다는 것은 이게 무조건 100% 있다는 뜻
				at.setOriginName(multiRequest.getOriginalFileName("upfile"));
				
				// changeName, multiRequest에서 얻어낼 수 있음
				at.setChangeName(multiRequest.getFilesystemName("upfile"));
				
				// filePath
				at.setFilePath("resources/board_upfiles");
				
			}
			
			// 4) 요청처리 Service 호출
			// 서비스에서는 insert 최소한번, 최대두번
			// 파일첨부 실패했을 때 게시글 작성도 실패하게 할 것임 -> 이걸 하나의 트랜잭션으로
			// 보드 테이블, 어태치먼트 테이블에 인서트, 둘 중 하나라도 실패하면 둘 다 롤백 -> 하나의 트랜잭션으로 묶음
			// 서비스는 DAO 메소드를 호출해서 insert 할것임, 그렇지만 DAO는 하나의 메소드가 하나의 스킬만을 수행하게 만들어야함(마이바티스 쓰니까 그렇게밖에 못함)
			// 호출 결과를 따로 받아서 그걸로 트랜잭션 처리
			// 컨트롤러에서 서비스를 두번 부르는게 아니라 컨트롤러에서는 하나의 서비스 메소드를 부르면서 board, attachment 두개를 같이 옮겨줌
			new BoardService().insert(board, at);
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
