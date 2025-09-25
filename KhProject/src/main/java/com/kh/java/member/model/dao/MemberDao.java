package com.kh.java.member.model.dao;

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

}
