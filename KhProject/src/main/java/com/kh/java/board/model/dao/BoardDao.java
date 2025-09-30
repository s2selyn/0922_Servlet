package com.kh.java.board.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.kh.java.board.model.vo.Board;
import com.kh.java.common.vo.PageInfo;

public class BoardDao {
	
	public int selectListCount(SqlSession sqlSession) {
		
		// 그룹 함수의 결과는 행 개수를 세서 하나로 반환 -> selectOne 메소드 호출
		// 아직 없지만 이렇게 작업할 것이다~ 하고 매개변수 작성
		return sqlSession.selectOne("boardMapper.selectListCount");
		
	}
	
	public List<Board> selectBoardList(SqlSession sqlSession, PageInfo pi) {
		return sqlSession.selectList("boardMapper.selectBoardList", pi);
	}

}
