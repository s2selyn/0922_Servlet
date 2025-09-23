package com.kh.first.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/post.do")
public class RequestPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RequestPostServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 진짜 post로 해도 여기가 잘 호출되는지 확인
		// System.out.println("여기서 작업");
		// 아주굿 콘솔에 잘 나옴
		
		// 1. 데이터 값 뽑기
		// 2. 원래라면 요청처리 --> 오늘은 패스(아마 내일까지?? DB붙여서 작업하기전에 해야할 공부가 많다, 문법이랑 등등 새로운 내용 학습에 시간투자할거임)
		// 3. 응답화면 지정
		
		// 0단계 )
		// POST방식일 경우 인코딩 설정이 ISO-8859-1방식으로 바뀌기 때문에
		// 값을 뽑기전에 미리 UTF-8방식으로 변경해주어야함!!!
		// 우리 지금 뭐하고싶음? 인코딩을 UTF-8로 변경해주고싶으니까, 변경할때는 set을 쓴다
		// 누구걸 set해야함? 어디 담겨있음? request아니면 response 두개뿐
		// request안의 인코딩을 set하고싶음, set 하니까 두개뿐이네요? 눈치보니 밑에꺼군
		request.setCharacterEncoding("UTF-8");
		
		// 서블릿 나온지 거의 30년인데, 30년전부터 자바개발자들은 내가 뭘 바꿔야한다면 set, 얻어야한다면 get으로 작업
		// 개발방식이 그때부터 정해져서 쭉 이어져내려옴. 기본적으로 자바를 학습하는 사람들은 이 개념이 머리에 잡혀있다
		// 결국 자바는 할수있는게 field에 뭐 하는거, 클래스기반이니까, 그거말고 읎어
		// 값 넣으려면 set, 빼오려면 get
		// 모든 개발자가 똑같은 생각을 한다면 메소드 처음봐도 뭔 일 하는지 대충 이해됨, 그런 생각을 하면서 코드를 짜시오
		// 생각하는 버릇을 들이면 새롭게 공부하거나 이해하는게 아니라 자연스럽게 똑같이 쓰고 안까봐도 제작자의 의도파악 생각파악 가능
		// 초보개발자여 생각을 습관을 미리 잡아두시게, 실제로 나중에 무언가를 만들 때 자기 생각으로 잘 만들 수 있을 것이네
		
		// 1단계 ) 값 뽑아서 변수에 대입하고 출력해보기
		// HttpServletRequest request에서 메소드 호출하고 문자열 반환받기 가능
		// request.getParameter("키값") 또는 request.getParameterValues("키값")
		String name = request.getParameter("name"); // 폼태그안의 내가 현재 값을 뽑고싶은 input요소의 name속성값을 인자로 전달
		System.out.println(name);
		// post 방식은 (전세계 인코딩 표준이 UTF-8이지만) 인코딩 방식이 바뀜
		// 0단계 추가해야함
		// 값 뽑고나서는 변환할 수 없음, 제일 먼저 0번 작업 해야함
		
		// 0번 작업 추가하고 요청보내면 잘 되고, URL에 값이 보이지 않는다
		
		// 실습 나머지 input요소 값들도 getParameter를 이용해서 반환 받아
		// 콘솔창에 출력하기 시~~~~~~~~~~작
		String gender = request.getParameter("gender"); // name 속성값 복붙추천
		System.out.println(gender);
		
		// 하나씩 확인해보면서 하시오. 한꺼번에 하다가 터지면 뭐하다가 잘못됐는지 찾기힘들다
		// 내가뭐하는지 생각하기 + 문제생겼을때 해결능력 기르기
		
		int age = Integer.parseInt(request.getParameter("age"));
		System.out.println(age);
		
		String city = request.getParameter("city");
		System.out.println(city);
		
		// 개발자 이찌방 능력 ==> 문제해결능력
		// 별로 안중요한거 ==> 최신 기술 배우기
		// 언제적 기술이든 사용해서 동작시킬 수 있으면 훌륭한거죠
		// 기본기 베이스가 중요하다
		
		double height = Double.parseDouble(request.getParameter("height"));
		System.out.println(height);
		
		String[] foods = request.getParameterValues("food");
		// System.out.println(Arrays.toString(foods));
		// 이것도 다른 방법이 있나? 생각해봐야함
		// 선택 안했을 수도 있으니까 null이 아닐 때 작업을 추가해줄 수 있다. 배운거 써보기도 하고 이것저것 나혼자 생각해서 쳐보기 츄라이하시오
		if(foods != null) {
			System.out.println(String.join("-", foods));
		} else {
			System.out.println("없음");
		}
		
		// 2단계
		// 요청 처리
		// Service -> DAO -> DB 이렇게 다녀오면
		// List / VO / Int 등의 형태로 돌아온다
		
		// 3단계
		// 응답데이터
		// 아까 서블릿으로 만드는거 너무 힘들었음, 실제로 사용되는 기술도 아니고, 써보는 이유는 클라이언트에게 보내는것이 결국 문자열이라는것을 이해하기 위함
		// 다른 방식으로 응답데이터를 만들어서 돌려줘보자
		
		// 3_1. JSP를 이용해서 응답 페이지 만들기
		
		// JSP(Java Server Page -> 2017년도 까지,
		// 	   Jakarta Server Page -> 2017년도 부터)
		// JAVA기반의 서버 사이드 웹 페이지 생성 기술
		// 특징 : 서버에서 실행되어 동적으로 웹페이지를 생성할 수 있음
		// 프로젝트 하나 파서 JSP 공부할건데, 지금은 임시로 하나 만들어서 설명하자 -> views 디렉토리에 response_page.jsp 추가하고 작업
		// 자바를 이용해서 돌리는 스크립트 언어라고 하기는 좀 그러니까 템플릿? 생성 기술? 언어로 받아들이면 안되는데 걍 스크립트 언어로 합시다, 뭐라고 하든 다 괜찮음
		// 아냐 걍 나중에 리액트 배울 때 차이 설명할건데, 웹 페이지 생성 기술 이렇게 설명해놔야 나중을 위해서 맞을듯
		// 아무 의미 없는데 알아놔야함, 옛날에 만들때는 Java로, 근데 만든회사를 오라클이 인수해서 라이센스가 오라클에 있음
		// 2017년도에 이클립스파운데이션에 넘김, 판매라기보단 관리권한을 줌, 자바는 상표권이 자기네들거라고 주장하면서 이름을 바꾸라고 함
		// 자바는 인도네시아 자바섬에서 따온거니까, 인도네시아 수도 자카르타 이름 따서 바꾼것, 쓰잘데없는짓 한다고 말이 많았지만 아무튼 관리주체가 바뀌면서 이름도 변경된것
		// 앞으로 자바공부하다가 자카르타보면 원래 자바였겠구나 오라클이 또 돈가지고.. 라고 생각하쇼
		// 아무의미없이 바뀌어서 실무에서 그냥 Java라고 하는 사람들 만날수도 있음, 현재의 정식명칭은 자카르타
		// PHP, ASP 등 서버에서 사용자가 볼 응답데이터를 만들어서 앞에 문자열 데이터를 전송하는 친구들은 다 이런 역할을 가지고 있음(서버에서 실행되어 동적으로 웹페이지를 생성하는 역할)
		
		// --------------------------------------------------
		// forward 이전에 작업해야해서 여기로 돌아왔음
		
		// 응답화면(JSP)에서 필요한 데이터를 넘겨줄 것(request에 담아서)
		// 값을 뽑아올 때 parameter에서 뽑았으니 거기가 공간이라고 생각하면 되는데, 굳이 번역하면 매개변수
		// 호출할 때 받아오는거라서 직접 세팅할수는 없음
		// parameter는 getParameter("키값") / setter는 존재하지 않음
		// 누가 악의적으로 사용자 요청값에 setter 포함해버리면 위험할수도 있음
		// 웹에서는 이런 공격들이 굉장히 많다, 한번 터질때마다 전세계가 난리남(하트블리드, Log4j 등)
		// 그래서 위변조 방지로 setter자체를 안만들어둔다
		// attribute => (자바스크립트 property 생각, 엄밀히는 차이가 있음) 키 - 밸류 세트로 묶어서 값을 만들어낼 수 있음
		// 만약에 객체에 새로운 property 추가하고싶으면? 객체에 접근해서 점찍고 속성명 대입연산자 속성값 해서 키밸류 세트로 추가한다, 이것도 똑같음
		// request에 attribute를 추가해줄거야
		// 새로운 attribute를 setting, 만들어주고싶은거니까 set뭐시기 하는거지
		// request.setAttribute("키", "밸류");
		// 앞의 키는 무조건 문자열, 두번째 인자는 타입이 Object인데, 뭘 보낼지 모르니까 최상위객체로 모든 자료형을 담을 수 있게 다형성 적용시키려고
		request.setAttribute("msg", "요청처리에 성공했습니다.");
		// 리퀘스트에 새로운 속성을 키밸류로 추가했다! 아래에서 이걸 jsp로 포워딩해주도록 만들어진것
		// jsp에 다시 가서 작업한다.
		// 나머지 attribute 추가하러 돌아왔음
		request.setAttribute("name", name); // 안헷갈리게 똑같이 작성함
		request.setAttribute("gender", gender);
		request.setAttribute("age", age);
		request.setAttribute("city", city);
		request.setAttribute("height", height);
		request.setAttribute("foods", foods);
		// 출력해줄, 보여줄 값 attribute로 set해준다
		// --------------------------------------------------
		
		// 원래는 응답데이터를 보내줘야하는데 아직 안보내줘서 콘솔창에 출력만 하고 응답 보낸 브라우저에서는 아무것도 안보이는게 정상임
		// .jsp 파일을 통해서 응답해줄건데,
		// 응답 데이터 생성과정을 JSP에게 위임(배정)
		// 뭐가됐든 웹통신 요청의 최종응답은 서블릿이 한다. 스트림을 이용해서 html 출력으로 문자열로 응답한다
		// jsp를 사용하면 그 문자열을 알아서 만들어준다니까?
		// 그럼 응답데이터 만드는 과정을 jsp한테 넘겨줄거임
		
		// 배정 시 필요한 객체 : RequestDispatcher
		// 컴퓨터 공학적 표현의 dispatching은 컴퓨터에 달려있는 연산장치(CPU)가 하는 작업이 디스패치, 가장 중요한 업무중에 하나
		// 컴퓨터 자원은 물리적으로 한정적이니까 부품 살때도 더 많은 자원, 빠른 자원의 장치를 사려면 가격이 올라감
		// 하나의 물리적 장치에서 사용가능한 자원은 한정적
		// 하나의 연산장치가 처리할 수 있는 작업의 양과 시간도 한정적
		// CPU라는 연산장치는 내가가진 자원으로 알고리즘을 사용해서 해야할 업무를 배정하는 역할이 필요함(어떤 프로세스를 쓸지 한정자원을 배치) -> 이걸 디스패칭한다고 표현
		// 그래서 이 객체의 이름 그대로 요청에대해 서블릿이 응답을 만들어줄텐데, 누가만들지 배정하는것
		// 아니라면 또다른 서블릿으로 요청을 전달해서 처리하도록 작업을 처리할수도 있음
		// 그래서 jsp에게 응답 데이터 생성 과정 위임할거임
		// 디자인 패턴 중 팩토리패턴 이용해서 한다
		// request 객체를 이용해서 얻어올 수 있다, 얻어오고 싶으니까 get, 인자값으로 내가 응답 과정 처리를 위임할 jsp 파일의 경로를 전달
		// request.getRequestDispatcher("JSP파일의 경로");
		RequestDispatcher view = 
		request.getRequestDispatcher("/views/response_page.jsp");
		// 절대/상대경로 가능, a태그 상대했으니 이번엔 절대해보자
		// ??? 14:13 시작폴더는 jsp는 view와 관련된 친구기때문에 반드시 webapp아래에 있어야하므로 rootdirectory ???
		// /를 적고 시작하면 절대경로방식이라고 한다
		// /로 시작시 webapp/ 을 의미함
		// jsp파일 위치까지 찾아가려면 일단 views 폴더에 들어가야하니까 폴더명 작성 -> /views
		// 이 안으로(밑으로) 들어간다면 / 추가 -> /views/
		// 그럼 jsp가 존재하고 있으니 그대로 파일명 작성 -> /views/response_page.jsp
		// 반환타입 보면 RequestDispatcher object가 돌아온다고 되어있음, 반환타입 지정한 변수로 받아둔다
		
		// 받아온 객체를 참조해서 메소드 호출해줘야 끝난다.
		// jsp도 결국 서블릿으로 변환될텐데, 응답을 해주기 위해서는 요청을 보낸 사용자가 누군지가 필요함
		// 누가 요청을 보냈는지 알아야 그걸 가지고 거기로 응답을 보낸다.
		// jsp도 서블릿으로 변환되어 응답되니까, 응답할 때 사용하는 response 객체가 있어야한다.
		// ??? 14:15 text/html은 생략이 가능하고 인코딩은 jsp 상단에 적혀있음, 페이지 지시어로 대체가 되어버린다.
		// 요청시 사용자 정보와 응답 객체를 전달해줘야한다.
		// 결국 객체주소값 두개 넘기고싶은거임, 자바에서 값 넘기는 방법은? 메소드 호출 시 인자값으로 넘기기
		// 이번에 전달하는거니까 forward
		// ??? 14:18 인자값 설명
		view.forward(request, response);
		// Forwards a request from a servlet to another resource (servlet, JSP file, orHTML file) on the server.
		
		// 이러고 서버 재부팅하고 요청했을 때 jsp 파일 데이터가 응답 데이터로 잘 돌아오는지 확인
		// 아까 서블릿으로 만든것처럼 비슷하게 받을 수 있게 만들어보자. 비슷한 응답코드 돌려줄건데 자바코드 아니라 jsp 이용해서 만들어보자.
		// jsp에서 작업 -> 돌아와서 forward 이전에 작업하러 올라감, forward 이전에 해야함!!
		// ??? 14:24
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 진짜 얘가 불리는지 출력해보자 나는 모범시민이다
		// System.out.println("진짜야?? 이거 여기서 돌아가는거야?? POST면 나 부르는거야?");
		// 서버 재부팅하고 요청보내기 누르면 이거 콘솔에 잘 나오는지 확인
		
		// 얜 뭐야? doGet메소드 호출하고있음
		// post에서 부르든 get에서 부르든 귀찮으니까 doGet에서만 작업해도 되게 편의성을 위해서 넣어준것임
		// 여기서 작업할거면 이걸 지우고 하면 된다. 근데 굳이? 우리도 똑같이 doGet에서 작업하면 된다
		// 쓰라고 만들어줬는데 굳이 거절할 이유가 없음
		doGet(request, response);
		
	}

}
