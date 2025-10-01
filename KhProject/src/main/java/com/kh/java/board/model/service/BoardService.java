package com.kh.java.board.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.kh.java.board.model.dao.BoardDao;
import com.kh.java.board.model.vo.Attachment;
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
	
	public int insert(Board board, Attachment at) {
		
		SqlSession sqlSession = Template.getSqlSession();
		
		int boardResult = bd.insertBoard(sqlSession, board);
		
		int atResult = 1;
		
		if(at != null) {
			at.setRefBno(board.getBoardNo());
			atResult = bd.insertAttachment(sqlSession, at);
		}
		
		// 두 개의 DML구문을 하나의 트랜잭션으로 묶어서 처리
		// atResult 변수를 사용하려면 if문안에서 선언하면 밖에서 쓸 수 없으므로 if문 위에서 초기화
		// atResult 0으로 초기화 먼저 해두면 첨부파일 없으면 커밋 못하는데? -> 1로 초기화
		// 없으면 무조건 true, 첨부파일 있으면 1, 실패하면 0이 되니까 간단
		if((boardResult > 0) && (atResult > 0)) {
			// 가독성을 위해 연산을 괄호로 묶는
			// 이런 상황에서는 boardResult * atResult > 0 이렇게 작성하는 것을 선호함
			// 하나라도 0이면 0이 되고 둘 다 1이면 1이니까
			// 롤백하려면 실패했을때 0, 성공했을때 1을 보내야하는데 이걸 생각해서
			
			sqlSession.commit();
			
		} else {
			
			// 두개중에 하나라도 실패하면 롤백
			sqlSession.rollback();
			
		}
		
		return (boardResult * atResult);
		// 둘 다 성공하면 1*1, 하나라도 실패하면 0이 곱해져서 0이 돌아간다
		
	}

}
