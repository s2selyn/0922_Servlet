package com.kh.java.member.model.dao;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.kh.java.member.model.vo.Member;

public class MemberDao {
	
	public Member login(SqlSession sqlSession, Member member) {
		
		// 설명할게 꽤 많습니다.
		// 어떤 내용을, 뭘 돌려줄것인지 고민해야함
		// select 했는데 있다 -> 인증성공 / 없다 -> 인증실패
		// 무슨내용을 돌려줄지는 사람마다 다를건데 개발자보다는 기획자에게 달린 문제
		// 네이버에서 구경해보자, 인증에 성공하면 이름, 메일주소, 메일 몇개 왔는지 화면에 출력해준다
		// 내가 가입했을 때 쓴 이름, 메일주소, 현재 와있는 메일 개수 등의 정보를 출력해줌
		// 인증에 성공했을 때 이 값들을 들고온다는 뜻, 이걸 select 해왔으니까 화면에 보여줄 수 있겠지
		// 네이버는 이게 나오는데 kh 로그인했을때는? 실제로 로그인했을때 들고가는 정보는 내가 어떤 사이트를 만드느냐에 따라,
		// 인증성공하면 어떤걸 보여줄건지, 어떤 서비스를 제공하는 사이트인지에 따라 달라진다.
		// 가입 시 적은 아이디, 이메일은 회원정보 테이블에 있을거고
		// 읽지않은 메일의 개수는 메일 테이블에 따로있겠지, 내가 읽지 않은 메일 컬럼을 where조건절로 걸러서 count해서 온거겠지?
		// 결국 화면에 어떤 정보가 필요할지 생각해야 내가 가져갈 정보를 정할 수 있음
		// 정답이 있는 문제는 아니고
		
		// 지금은 인증에 성공하면 인증한 사용자의 정보, 테이블에 존재하는 멤버 한 행의 값을 다 들고가보자
		// 그러면 반환타입이 한행정보는 하나의VO로 다루기로 했으니까 VO타입으로 돌려줘야한다
		// 사실 비밀번호 돌려주면 큰일나지만 수업이니까
		
		// 이안에서 해야할일은?
		// sql문 실행하고 응답결과 받아오기(pstmt, stmt로 executequery, executeupdate로 했음)
		// 여기서는 sqlSession으로 대신하는거고 참조해서 수행할 sql문 태그 호출하면서 돌아갈 값 생각해서(하나로 밖에 못들어감 조건일 아이에 unique 제약조건이 있음)
		// 전체 얼개를 생각!
		// 하나만 돌아올거니까 selectOne()
		// 첫번째 인자는 실행할 sql이 존재하는 매퍼파일의 namespace값, 참조해서 그안의 여러개 태그들 중에서 식별할 id 속성값, 이건 메소드명이랑 맞춰서 쓸거니까 login
		// 두번째 인자는 sql 실행을 위한 member를 전달
		return sqlSession.selectOne("memberMapper.login", member);
		// 실행한 결과를 반환해줘야하니까 return
		
	}
	
	public int signUp(SqlSession sqlSession, Member member) {
		return sqlSession.insert("memberMapper.signUp", member);
	}
	
	public int update(SqlSession sqlSession, Map<String, String> map) {
		return sqlSession.update("memberMapper.update", map);
	}
	
	public int delete(SqlSession sqlSession, Member member) {
		
		return sqlSession.update("memberMapper.delete", member);
		// 메소드명은 update로 동일하게, memberMapper의 id만 다르게 호출
		// 이게 가능한 이유 -> 로그인, 정보수정 기능 구현할 때 조건을 status column이 y일것으로 달아놨음
		// status 컬럼값이 y가 아니라면 정보수정도 못하고 로그인도 못하기 때문에 사실상 이용할 수 있는 기능이 없어서 탈퇴나 다름없음
		// delete 하면 데이터를 살리기 굉장히 귀찮기 때문에 update로 기능들만 이용 못하는 상태로 못하게 해두겠다
		// 못살리는건 아님, 살릴 수 있음, delete의 기록도 다 남고 기본적으로 회사에 가면 서버관리자분들이 많다, 회사에 없어도 외주하기도 하고
		// 보통 중요한 정보는 외부에서 접근 불가능해서, 특히 케이블 문제도 전부 사람이 가서 해야함
		// 서버문제는 언제 생길지 알 수 없음, 서버관리자는 너무 힘들다 ...
		// 순수서버관리자도 있고 시스템 만지는 사람도 있고
		// 백업 시간이 보통 26시, 28시, 31시 이렇게 된다, 이건 무슨말이냐? 새벽2시에 백업, 새벽4시에 백업, 아침7시에 백업, 문제 생길 수 있으니 집에 못가 끝나야 감
		// 이걸 매일 함... 매우 힘든 작업, 이런거 하려면 돈이라도 많이 주는 데를 가야만 해...
		// delete하면 이 서버관리자분들이 살려.. 너무 힘든 일이야, 데이터 날리기는 너무 위험한 작업임, delete는 금단의 주문, 너무 위험하다
		// userNo는 session에서 뽑을테니 잘못될리없고
		// 비밀번호 입력이 틀리면 실패한다
		
	}

}
