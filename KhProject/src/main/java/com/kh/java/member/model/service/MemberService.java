package com.kh.java.member.model.service;

import java.util.Map;

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
	
	// 하이 사비스 ~
	// 로그인 할 때 검증절차 잘 만들어뒀으면 여기서 또 쓸 수 있지만 다음에 하기로 했음
	// 아우 알차
	// 컨트롤러에서 불러야하니까 접근제한자 public, 반환형이랑 메소드명도 정하고 왔음
	public int signUp(Member member) {
		
		// DB와 접속해주는 sqlSession부터
		// 템플릿이란?
		
		// 애매한 부분이 있다...
		// 개발을 잘한다, 일을 잘한다는 표현
		// 선생님이 봤을 때는 세가지로 나뉜다고 주관적인 생각을 하신다네요
		// 1. 천재파(딱 보면 싹싹 머리에 컴퓨터가 달려있는 사람들, 무슨생각하는지 모르겠따 보법이 다른 사람들)
		// 2. 눈치코치파(감이 좋음, 천재파는 전자두뇌를 돌린다면 눈치파는 경험이 많음, 개발을 많이해봐서 이래저래 많이해서 머릿속에 쌓인 빅데이터로, 다른 엔지니어 파트로 가면 경험이 많으니 장인이 되는 것)
		// 3. 단순노력파(때려박음)
		// 1은 살면서 몇 보기도 힘든 말도안되는 케이스니 넘어가고
		// 2도 시간을 쏟아야함, 경험이 많아야하고 좋은 코드를 많이 보고 좋은 사람이 잘 알려주는 시간이 많이 쌓여서.. 근데 이것도 쉽지않아
		// 우리는 3으로 가야한다 근데 여기서도 두가지로 나뉜다
		// 쌩노력파, 쌩으로 노력하는 방법
		// 차곡차곡파
		// 이건 사람마다 다른 것 같다. 뭐가 더 좋고 올바른것은 취업이 목표라면 정답이 없다고 생각
		// 여기가 애매한 것, IT, 코딩, 개발 공부는 수학공부법이랑 똑같음, 전산학에서 왔기 때문
		// 수학 공부법은 두가지, 때려박기케이스(쌩노력파), 대치동에서 하루죙일 수학문제 풀게해서 유형자체를 머릿속에 넣는 경우
		// 왜 이렇게 되는거지? 질문을 계속 해서 원리로 올라가는 경우
		// 두개중에 잘 맞는게 사람마다 다름, 원리 동작 몰라도 때려박아서 방법을 익혀버리면 빠르게 많은 작업이 가능할수도 있고
		// 차곡차곡은 왜이렇게 되는지 세월아 네월아여서 퍼포먼스는 훨씬 안나올지도, 차곡차곡 시간을 들여봤자 결과가 돌아온다는 보장도 없음
		// 우리 목표는 지식 습득이 아니라 취업이기 때문에 차곡차곡 하기에는 좀...
		// 정답은 없지만, 장기/단기목표에 따라 전략도 달라야하고
		// 근데 둘중에 어느걸 선호하느냐는 선생님은 차곡차곡파
		// 지식은 나중에 쌓고 동작시키기만 해도 상관은 없지만 어쨌든 지나고 나서 생각해보시니 아쉬우심
		// 당시에 알고있었으면 일을 할 때 필요한 상황에서 도움이 됐을 것 같다고 하십니다. 그런것들을 많이
		// 멀리 본다, 차곡차곡파 해야겠다면 계속 질문을 해야 함, 스스로 여기 왜 이거 이렇세 써야하는지 왜이렇게 되는지
		// 외워서 쓰는게 안되는건 아니지 뭐 취업하려면 시간 때려넣어서 해야지
		
		SqlSession sqlSession = Template.getSqlSession();
		
		// DAO 메소드 호출해서 정수 받아오겠지(insert니까 처리된 행의 개수가 온다)
		// insert할때 사용할 값은 member에 setter 이용해서 필드에 담아뒀으니 같이 전달
		int result = md.signUp(sqlSession, member);
		
		if(result > 0) {
			
			// 결과가 있다면 커밋(트랜잭션 처리)
			sqlSession.commit();
			
		}
		
		// 사용이 끝난 자원 반납
		sqlSession.close();
		
		// 결과값 반환
		return result;
		
	}
	
	public int update(Map<String, String> map) {
		
		SqlSession session = Template.getSqlSession();
		
		int result = md.update(session, map);
		
		// 일반적인 DML이 성공했다면 커밋
		if(result > 0) {
			session.commit();
		}
		
		// 자원반납
		session.close();
		
		return result;
		
	}

}
