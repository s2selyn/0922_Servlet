package com.kh.java.board.model.dao;

import org.apache.ibatis.session.SqlSession;

public class BoardDao {
	
	public int selectListCount(SqlSession sqlSession) {
		
		// 그룹 함수의 결과는 행 개수를 세서 하나로 반환 -> selectOne 메소드 호출
		// 아직 없지만 이렇게 작업할 것이다~ 하고 매개변수 작성
		return sqlSession.selectOne("boardMapper.selectListCount");
		
	}

}
