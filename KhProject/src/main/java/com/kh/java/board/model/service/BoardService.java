package com.kh.java.board.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.kh.java.board.model.dao.BoardDao;
import com.kh.java.board.model.vo.Board;
import com.kh.java.board.model.vo.Category;
import com.kh.java.common.Template;
import com.kh.java.common.vo.PageInfo;

public class BoardService {
	
	// DAO 계속 써야하니 필드로 둔다
	private BoardDao bd = new BoardDao();
	
	// 행이 몇개인지 count해서 int로 돌려줘야함, 컨트롤러에게 따로 전달받은 것은 없음
	public int selectListCount() {
		
		// sqlSession 필요
		SqlSession sqlSession = Template.getSqlSession();
		
		// 딱히 할 작업은 없음
		
		int listCount = bd.selectListCount(sqlSession);
		
		sqlSession.close();
		
		return listCount;
		
	}
	
	public List<Board> selectBoardList(PageInfo pi) {
		
		SqlSession sqlSession = Template.getSqlSession();
		
		List<Board> boards = bd.selectBoardList(sqlSession, pi);
		
		sqlSession.close();
		
		return boards;
		
	}
	
	public List<Category> selectCategory() {
		
		SqlSession sqlSession = Template.getSqlSession();
		
		List<Category> categories = bd.selectCategory(sqlSession);
		
		sqlSession.close();
		
		return categories;
		
	}

}
