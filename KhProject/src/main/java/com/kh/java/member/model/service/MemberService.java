package com.kh.java.member.model.service;

import org.apache.ibatis.session.SqlSession;

import com.kh.java.common.Template;
import com.kh.java.member.model.dao.MemberDao;
import com.kh.java.member.model.vo.Member;

public class MemberService {
	
	// 여기서 DAO를 계속 사용할것임
	// Controller에서는 Service를 필드로 빼놔도 쓸모없음, 한번 쓰고 끝남
	// Service는 모든 메소드에서 DAO를 호출할것이므로 필드로
	private MemberDao md = new MemberDao();
	
	// 로그인 하는거 비밀번호 암호화를 뺐는데... 고민중
	// 반환형 아직 안정해서 void
	public Member login(Member member) {
		
		// 컨트롤러가 서비스에 요청처리해줌, 자기가 못하니까 얘보고 로그인해달라고
		// 그럼 이 메소드가 해야할일은 로그인 처리, DAO가 검증 ??? 여기도 확인해야해!!!
		// 로그인 처리 -> DAO에 보내서 있나없나 -> 결과값 반환
		
		// validateMember(member); 비즈니스 로직
		// ??? 15:46
		// CRUD 작업을 한다면 여기서 할 작업은 달라지는 게 없다
		// 세션객체 만들거고
		// 근데 DAO에서 세션만들어도 상관없지만 굳이 여기서 만드는 이유는 여기서 트랜잭션 처리 하려고 그런거임
		// 트랜잭션 처리 할생각 없으면 DAO에서 세션생성가능
		// 게시글 작성한다면 insert, 테이블 두개쓸거임, 게시글내용 + 파일내용
		// 한 기능을 위해 insert 두번 수행할거임, 그러면 게시글작성 성공해도 첨부파일 실패하면 게시글도 취소할거고, 게시글 실패하면 파일첨부도 실패처리, 두개의 트랜잭션을 하나로 묶어서 처리할거라서
		// ??? 트랜잭션 처리?
		SqlSession sqlSession = Template.getSqlSession();
		// 이거 받아오고 나서 서비스에서 할 작업은 DAO 호출, 메소드명은 똑같아야 안헷갈림, 호출하면서 인자 넘겨줘야한다
		Member loginMember = md.login(sqlSession, member);
		
		sqlSession.close();
		
		return loginMember;
		
		/*
		if(member.getUserId() == null || member.getUserId().trim().isEmpty()) {
			
			return;
			// NPE 발생 방지
			
		}
		
		// 사용자가 이상한 값을 보냈을 수 있음, 우리의 의도는 영숫자, 글자수제한이 있는데
		// 한글같은걸 보냈을 수 있음, 이건 빠졌는데 원래 앞단에 붙어있어야함
		// 정규표현식 배웠으니까 헤더의 자바스크립트 코드로 구현해야함, id, password 밸류로 유효한 값인지 체크하는게 있어야함
		// 앞단에서 체크 한번 하는데,
		// 웹서버로 요청하는 작업이 꼭 브라우저만으로 이루어지는것은 아님, 명령프롬프트로도 할 수 있고
		// 많은 개발자들이 기본적으로 사용하는 프로그램도 있다, 서버로 요청을 보내서 응답을 받아볼 수 있는 프로그램
		// 얼마든지 앞단을 우회해서 요청 보내기 가능
		// 뒤에서 항상 의도한 값이 맞는지, 가지고 DB가도 되는지 검증 해줘야함
		// 우리 아는 그거 그대로 쓰면 된다
		String pattern = "^[a-zA-Z0-9]{4,20}$";
		
		// 아이디가 이 패턴을 만족하는가? 를 하고싶으면
		// 아이디는 멤버객체 userId 필드에 있으니 거기다가 matches 메소드 쓰면 됨, 인자로 정규표현 달라고 써있음
		if(!member.getUserId().matches(pattern)) {
			return;
		}
		
		// 패턴이 문제가 아니라 안보냈거나 빈문자열이거나 null이거나, 이건 앞에서 또 검증해줘야함
		// 패턴코드 위에작성
		
		// ??? 15:43 뭔가 있음
		
		// 비밀번호 검증 로직
		
		// 백엔드 개발자에게 중요한건 검증로직인데 지금은 기능구현 중심으로 하고 나중에 추가
		// 지금은 로직이외의것 탄탄히하기
		
		// 하는김에 좀 꼼꼼히 하자면? 지금 로그인 메소드인데 로그인 자체랑은 관계없음, 값을 검증하는 로직일뿐
		// 하나의 메소드는 하나의 책임만을 가지고 하나의 작업만을 수행해야함
		// 그러니까 지금 검증로직 코드는 여기 있으면 안된다. 분리해야해!
		// validate 메소드 추가하고 코드 옮김
		 * 
		 * ??? 15:45 앞으로 할 일
		 * 
		 */
		
	}
	
	// 하나의 클래스는 하나의 책임을 가져야하니까 이것도 여기 있으면 안된다
	public void validateMember(Member member) {
		
		if(member.getUserId() == null || member.getUserId().trim().isEmpty()) {
			return;
		}
		
		String pattern = "^[a-zA-Z0-9]{4,20}$";
		
		if(!member.getUserId().matches(pattern)) {
			return;
		}
		
	}

}
