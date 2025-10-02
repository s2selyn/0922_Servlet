package com.kh.java.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		
		// INSERT를 두 번 수행
		// BOARD 테이블에 한 번 => 무조건 가는것, 게시글 등록 요청이니까(제목+내용)
		int boardResult = bd.insertBoard(sqlSession, board);
		
		// ATTACHMENT 테이블에 한 번 => 파일이 존재할 때만 가야함(첨부했을수도/안했을수도)
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
		
		// 트랜잭션처리까지 끝내고 난 후 성공실패여부를 반환 -> 컨트롤러에게 반환
		return (boardResult * atResult);
		// 둘 다 성공하면 1*1, 하나라도 실패하면 0이 곱해져서 0이 돌아간다
		
	}
	
	public Map<String, Object> selectBoard(int boardNo) {
		
		SqlSession sqlSession = Template.getSqlSession();
		// 여기까지는 확정
		
		// SELECT 두 번 하기 + 조회수 증가
		// select 두번도 확정이긴 한데 KH_BOARD에 COUNT라는 컬럼이 있음
		// 이 컬럼의 존재의의는 누군가 이 게시글을 상세조회하면 이 컬럼의 값을 현재 값에서 1 증가시킬것임, 조회수
		// 조회수 증가는 SQL문 update 써야함(테이블에 값을 넣는건 insert/update 두개뿐, insert는 한 행 넣는것, 지금은 하나 바꾸는거니까 update)
		// 일부러 안 알려주신게 있긴한데 그럼 그것만 사용하는 경향이 너무 커서 ㅎ 일반적으로는 insert/update 두개밖에 없다
		// 결론은 select 두번 update 한번
		// 총 DB에 3번 가야함
		// 그럼 뭐부터 할지가 이슈
		// UPDATE BOARD(얘는 DML, 데이터 수정구문) => COMMIT(트랜잭션 처리) 해줘야함
		// SELECT BOARD(얘는 조회구문)
		// 추후의 과정들을 살펴봤을 때, UPDATE에 실패하면 조회하러 갈 필요가 없음
		// UPDATE에 성공하면 SELECT는 웬만하면 실패할일이 없음
		// UPDATE에 성공하면 BOARD_NO, STATUS가 있다는 뜻이니까
		// 만약에 SELECT먼저 하고 UPDATE도 가능하지만 DML이기 때문에 실패하면 이걸 롤백해줘야함을 고려해야함
		// 그러면 UPDATE를 먼저하는게 낫지않나? 그래서 COMMIT 햇을때만 조회 두번을 하는것으로
		// SELECT BOARD는 둘다 조회해야하는 상황 / UPDATE는 커밋됐을때만 아래작업을 해야하고
		// UPDATE 실패하면 두개는 안해도 되는거고 최소시행횟수 한번
		// SELECT는 최소시행횟수 2번
		
		// SELECT ATTACHMENT(지금경우에는 얘가 우선순위가 낮을 것 같다, 있을수도 있고 없을수도 있음)
		// ??? 12:30 작업순서 생각과정 요약
		
		int result = bd.increaseCount(sqlSession, boardNo);
		
		if(result > 0) {
			
			sqlSession.commit(); // 조회수 증가하면 커밋
			
			// 이게 성공했다면 boardNo가 무조건 있다는 듯이고 지워지지도 않았다는 뜻임
			// 안전하게 select 하러 갈 수 있다
			Board board = bd.selectBoard(sqlSession, boardNo);
			
			// 그리고 첨부파일 조회도 해야함
			Attachment at = bd.selectAttachment(sqlSession, boardNo);
			
			// -----
			Long userNo = bd.selectBoardWriter(sqlSession, boardNo);
			// -----
			
			// 조회 되는지 먼저 확인, sql 잘못되면 sql exception 예외 일어나니까 예외 일어나지 않는지 확인한다
			// System.out.println(board);
			// System.out.println(at);
			
			// 이걸 jsp에게 보내야한다
			// 댓글도 들고가야하는데 아직 작성 안만들었으니까 board, at 들고 jsp 가야함
			// 일단 컨트롤러로 다시 보내야하는데 두개라서 어떻게 돌려주는게 좋을까?
			// 하나밖에 못돌려주니까 하나에 담는게 좋을 것 같은데 어디에 담을까?
			// 리퀘스트에 담으려면 컨트롤러에 가야함, 어딘가에 담겨서 컨트롤러로 리턴되어야, 컨트롤러에서 변수에 담아서 또 jsp에 보내겠지
			// list도 방법 중의 하나
			// 서로 다른 자료형 두개를 어딘가에 담고싶음, 자바인데 자바에서 서로 다른 자료형 두개의 값을 어디에 담고싶은것
			// 그러면 선택지 네개뿐, object형 배열, list, set, map
			// 이거 말고는 자바에서 여러개 자료형 담을수있는게 없다, 그게 싫다면 새로 DTO 만들어서 담든가.. 그거 만들어서 하는거 좋긴한데 다른사람이 공부해야함
			// 이미 있는거 쓰는게 좋다, list, set, map이라면 자바개발자는 누구나 알겠지, 여러개 담으려고 썼구나 하고
			// 지금은 어디에 담는게 좋을까? 사실 object형 배열이랑 arraylist는 같고 문제가 index 개념이 있어서 나중에 뽑을 때 get 0, 1 이런거 해야해서 불편해서 패스
			// set, map이 남는다, 지금은 조금 더 적합한게 map, 어디 담아도 담아서 넘기는건 문제가 없음
			// 컬렉션은 각각 특징이 있으니까, 리스트는 순서 필요할 때, 순서가 보장되어야 할 때 필요함
			// set은 집합, 중복 없애고 싶을 때, 똑같은거 안들어가게 하고싶을때
			// map은 key-value로 데이터 관리하고싶을때, 값에 대한 인지를 명확하게 시키고 싶을 때
			// 지금 board, attachment가 누가 먼저가야하는 순서필요한건아님, 중복을 없애야 하는 것도 아님
			// board, attachment라는 식별값을 달아주면 좋은 상황이니 map이 적합하다고 할 수 있다, 각각 주소값이니까 주소값에 key값을 달아줄 수 있음
			/*
			Map<String, Object> map = Map.of("board", board,
											 "at", at);
		    */
			
			Map<String, Object> map = new HashMap();
			map.put("board", board);
			map.put("at", at);
			
			// -----
			map.put("boardWriter", userNo);
			// -----
			
			return map;
			// 조회에 성공했을 때는 결과를 담은 map을 반환
			
		}
		
		// if에 못들어갔다면 안된거니까
		// 업데이트에 실패했으면 돌려줄게 없음
		return null;
		
	}
	
	public int deleteBoard(Board board) {
		
		SqlSession sqlSession = Template.getSqlSession();
		
		int result = bd.deleteBoard(sqlSession, board);
		
		// 지우는거 성공했으면 나중에 커밋도 해줘야함
		
		Attachment at = bd.selectAttachment(sqlSession, board.getBoardNo().intValue());
		// 근데 아까 만들 때 생각안한게, 매개변수 타입이 int, boardNo는 Long
		// Long을 또 int로 바꿔줘야함? 이건 래퍼클래스와 기본자료형이라 형변환 안됨
		// intValue : Long의 메소드, int로 변환(정확히는 Integer -> int로 거쳐가는것)
		
		int result2 = 1;
		// 게시글 지우는데 성공하면 첨부파일도 지워야함
		if(at != null) { // result > 0 에서 조건 수정
			
			result2 = bd.deleteAttachment(sqlSession, board.getBoardNo());
			// 둘다 성공하면 커밋
			
			// 둘중 하나라도 실패하면 별개의 경우
			// 애초에 파일이 없었다면 result가 0이 돌아오니 아까처럼 체크하면 안됨
			// 아까처럼 1로 초기화하고 대입해도 문제생길까?
			// 파일이 있을때만 SQL문 실행해야겠다면? 뭘 해봐야 게시글에 파일이 있는지 없는지 알수있음? DB가서 조회해야함
			// 게시글 번호를 가지고 파일이 있나없나 조회하는건 이미 만들어뒀다, selectAttachment, 이건 게시글 번호만 있으면 파일이 있나없나 알수있음
			// null이면 파일이 없는거고, null이 아닐때만 서비스에서 이걸 수행하면 된다
			// result2 초기화 위에 파일 있는지 확인하는 코드 추가
			
		}
		
		if(result * result2 > 0) {
			sqlSession.commit();
		} else {
			sqlSession.rollback();
		}
		
		return result * result2;
		// 둘다 잘됐을때만 1, 둘중 하나라도 잘못되면 0이 반환됨
		// 아까랑 동일하게 트랜잭션 처리방식 구현함
		
	}
	
	public int update(Board board, Attachment at) {
		
		SqlSession sqlSession = Template.getSqlSession();
		
		// 하나는 확정, board 테이블 update 한번은 무조건 한다, 파일과는 전혀 상관이 없는 게시글 업데이트
		int boardResult = bd.updateBoard(sqlSession, board);
		
		// Attachment~
		// 이건 경우가 세개
		// 1. 첨부파일이 없다, 새롭게 첨부를 안했다면 할일없음 => 새 첨부파일이 없을 때(어쨌든 묶어서 트랜잭션 처리 해줘야함)
		// 결과 받을 변수 미리 선언
		int atResult = 1;
		
		// 새 첨부파일이 존재할 경우 -> if, 일단 유일하게 생각없이 쓸 수 있는거, 조건이라면 if 쓰고 시작
		// 새 파일이 있는지 없는지는 앞에 Attachment at를 봐야함, null만 아니면 생성하니까 null 아니면 있다는 뜻
		if(at != null) {
			
			// 경우의 수가 또 나뉜다
			// case 1
			// 2. 기존파일이 있었다 업데이트
			if(at.getFileNo() != null) {
				// fileNo 자료형이 Wrapper클래스(참조자료형)인데 왜 null과 비교? 초기화 안하면 기본값이 null이라서
				// 지역변수와 필드의 가장 큰 차이, 지역변수는 초기화를 안하면 못씀
				// 필드는 초기화 안해도 쓸수있음, 기본값이 들어가있음, 기본자료형은 0, 0.0이런거고 참조자료형은 null이 들어있음
				// 이건 기존에 첨부파일이 있따는 뜻 => UPDATE
				atResult = bd.updateAttachment(sqlSession, at);
				
			} else {
				
				// case 2
				// 3. 기존파일이 없었다가 생겼다 인서트
				// 기존 첨부파일 없음 => INSERT
				atResult = bd.insertAttachment(sqlSession, at);
				// 기존에 만들어둔것들 사용하면 된다 아주 간단
				// mapper의 insertAttachment도 selectKey 쓰는 버전으로 업그레이드 했으니 아주 좋음
				
			}
			
		}
			
		// 첨부파일 없으면 뭐 할거없음
		
		// 둘 다 성공했을 때 만 commit;
		// 하나라도 실패했으면 rollback;
		if(boardResult * atResult > 0) {
			sqlSession.commit();
		} else {
			sqlSession.rollback();
		}
		
		sqlSession.close();
		
		return (boardResult * atResult);
		
	}
	
	public int searchedCount(Map<String, Object> map) {
		
		SqlSession sqlSession = Template.getSqlSession();
		
		int count = bd.searchedCount(sqlSession, map);
		
		sqlSession.close();
		
		// 조회라서 딱히 다른건 필요없고 count만 잘 돌려주면 될듯
		return count;
		
	}
	
	// 진짜 게시글 검색 메소드
	public List<Board> selectSearchList(Map<String, Object> map) {
		
		SqlSession sqlSession = Template.getSqlSession();
		
		List<Board> boards = bd.selectSearchList(sqlSession, map);
		
		sqlSession.close();
		
		return boards;
		
	}
	
	// 게시판은 board, 파일은 attachment에 insert할거임, 둘 다 만들어놨음
	// 그럼 우리가 이걸 만들어놓은걸 쓸 수 있지 않을까? 라고 생각할 수 있음
	public int insertImage(Board board, List<Attachment> files) {
		
		SqlSession sqlSession = Template.getSqlSession();
		
		// 보드에 INSERT하는거 만들어놨음
		// Attachment에 INSERT하는거 만들어놨음
		// 매퍼에서 확인해보자 -> 다시 돌아옴, 새로 만들기로 함
		
		// 일단 결과값 선언, 초기화하고 반환하는 코드를 만들어둠
		int result = 0;
		
		// 맨날 insert 하다가 예외가 생김... insert 하는게 댓글만 남음, 게시판 레벨에선 이게 마지막
		// 그러니까 이번엔 예외처리를 한번 해보자 본격적! insert맨날 똑같이 하면 지겨워
		try {
			
			// 1. 게시글 INSERT
			// 결과는 result로 받아옴
			result = bd.insertImageBoard(sqlSession, board);
			// Mapper까지 갔다왔음
			
			// 2. 게시글 INSERT가 성공 시 첨부파일들 INSERT
			if(result > 0) {
				
				// 첨부파일 개수만큼 INSERT
				for(Attachment file : files) {
					
					// boardNo가 왔으니까 이걸 넣어야함
					file.setRefBno(board.getBoardNo());
					
					result = bd.insertAttachmentList(sqlSession, file);
					// 파일이 들어가다가 실패할수도 있음, 문제가 생겨서 결과가 0이 올것이다
					// 그러면 반복문 탈출하도록 if문 작성
					// 올바르게 성공하면 1, 실패하면 0(게시글 실패도 마찬가지일거임)
					// 이 위에서 예외 발생해서 아래의 if로 못들어간다면 -> catch에서 롤백해야함
					// catch에서 result = 0; 해주는 이유는 이 안에 들어올 때 반복문 이전의 if에 의해서 들어온 것이므로 result에 0보다 큰 값(아마도 1)이 들어있음
					// 그 상태에서 예외가 발생하면 0이 대입되지 못하고 catch구문으로 넘어간다, 그러면 return 할 때 result가 1인 상태로 돌아가버리니까 실패했는데도 성공값이 반환됨
					// 그러면 안되니까 catch에서 0을 대입해서 실패한 값이 반환되도록 해줌!
					if(result == 0) {
						break;
					}
					
				}
				
			}
			
			// 3. 다성공했으면 Commit
			if(result > 0) {
				sqlSession.commit();
			} else {
				sqlSession.rollback();
			}
			
		} catch(Exception e) {
			
			sqlSession.rollback();
			e.printStackTrace();
			result = 0;
			
		} finally {
			sqlSession.close(); // 자원반납
		}
		
		return result;
		// 이렇게 작성하면 결과 곱할 필요도 없음 깔끔, DAO에 insertAttachmentList 작성하러감
		
	}

}
