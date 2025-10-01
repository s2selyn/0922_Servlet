package com.kh.java.board.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.kh.java.board.model.vo.Attachment;
import com.kh.java.board.model.vo.Board;
import com.kh.java.board.model.vo.Category;
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
	
	public List<Category> selectCategory(SqlSession sqlSession) {
		return sqlSession.selectList("boardMapper.selectCategory");
	}
	
	public int insertBoard(SqlSession sqlSession, Board board) {
		return 0;
	}
	
	public int insertAttachment(SqlSession sqlSession, Attachment at) {
		return sqlSession.insert("boardMapper.insertAttachment", at);
	}
	
	public int increaseCount(SqlSession sqlSession, int boardNo) {
		return sqlSession.update("boardMapper.increaseCount", boardNo);
	}
	
	public Board selectBoard(SqlSession sqlSession, int boardNo) {
		return sqlSession.selectOne("boardMapper.selectBoard", boardNo);
	}
	
	public Attachment selectAttachment(SqlSession sqlSession, int boardNo) {
		return sqlSession.selectOne("boardMapper.selectAttachment", boardNo);
	}
	// 마이바티스를 사용한다면 사용하는 문법이 정해져있기 때문에 DAO에서 쓸것들도 자동으로 거의 다 정해진다

}
