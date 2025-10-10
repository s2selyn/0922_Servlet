package com.kh.java.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.kh.java.board.model.dao.BoardDao;
import com.kh.java.board.model.dto.BoardDto;
import com.kh.java.board.model.dto.ImageBoardDto;
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
	
	public List<ImageBoardDto> selectImageList() {
		
		SqlSession sqlSession = Template.getSqlSession();
		// ??? 10:45 복습! 커넥션 만드는법: ojdbc라이브러리를 추가해서 드라이버매니저로 드라이버등록하고, 접속정보를 인자값으로 전달하면서 커넥션 객체 생성
		// sql세션은 커넥션 역할도 겸함, 얘도 접속해야하니 ojdbc 필요, 마이바티스 필요하니 설치, 동작을 위한 설정파일을 만들어야하니 mybatis-config.xml을 생성해서 그안에 environments > environment접속정보 url 이름 패스워드 드라이버 정보를 써서
		// 데이터 소스가 객체로 올라가면서 내가적은 데이터들이 필드에 세터를 이용해서 값들이 주입됨
		// 외부 파일(xml파일)인데 읽기 위해서 마이바티스에서 제공하는 resources를 사용해서 파일데이터를 읽어와서 그다음 팩토리, 빌드를 사용해서 sqlSession객체를 만드는것
		// 이것을 하기 위한 흐름과 절차(DB접속하려면 접속정보가 있어야함, 이걸 하려면 jdbc가 있어야 자바에서 커넥션할수있음)
		
		// sqlSession으로 sql문 실행하고 결과 받아오기, 뭘로 돌아올지 정해둠
		List<ImageBoardDto> boards = bd.selectImageList(sqlSession);
		
		// select문이라 갔다와서 할 작업은 딱히 없음
		sqlSession.close();
		
		return boards;
		
	}
	
	public BoardDto selectImageDetail(Long boardNo) {
		
		SqlSession sqlSession = Template.getSqlSession();
		
		// 이것도 누가 대신 반납해주면 좋겠다
		
		// DB에 몇번가야함? 순서도 정해야함
		// SELECT ONE KH_BOARD
		// SELECT LIST KH_ATTACHMENT
		// 화면을 보면 뭔가 더 해야할텐데? 상세보기하면 조회수가 1 증가해야함
		// 이걸 하려면 기존 게시글의 count 컬럼값 갱신해야하니 업데이트해야해, 사실 세번가야하는거지
		// UPDATE KH_BOARD
		// 그럼 뭐부터 할까? 뭐부터 하는게 좋을지 생각해서 정해야함
		// 웬만하면 업데이트부터 함, 트랜잭션이 생김
		// 조회 다 했는데 업데이트 실패하면 조회한게 의미가 없음 괜히가져온게됨
		// 데이터의 변동 작업을 먼저 수행, 실패한다면 SELECT에 문제가 있을 확률이 높음, 없는 게시글(삭제)일 수 있음
		// 이런 경우는 웬만하면 UPDATE부터 수행
		// 웬만하면 이니까 지키면 좋은데 무조건 그렇게 해야하는건 또 아님, 특별한 이유(생각, 계획)가 있다면야..
		// 그냥 하고싶어서 하는건 좀 이상하고, 웬만하면 웬만한걸 따라가는게 좋고 확고한 이유가 있다면 뒤로가도 괜찮다
		
		// 1. UPDATE KH_BOARD -> 이미 구현한것을 생각, updateBoard 메소드, 쓸수있는지 sql문 확인해보기 -> 매퍼에 increaseCount 서도될듯?
		int updateResult = bd.increaseCount(sqlSession, boardNo.intValue());
		// 앗 근데 우리 boardNo는 지금 Long, 만들어둔 increaseCount는 int를 매개변수로 받는다
		// Long은 int로 바꿀 수 없음, Long 클래스에 있는 메소드 중에 ㅇㅅㅇ? ㄴㄴ boardNo에 참조해서 intValue
		// 이러고 update에 성공했을때만 커밋
		if(updateResult > 0) {
			
			sqlSession.commit();
			// 커밋하고 또 고민
			
			// 2. SELECT ONE KH_BOARD
			// 만들어둔 바퀴가 있을까? dao에 selectBoard가 있음, sql문 확인해야함
			// 이건 안될듯? 왜안될까? 234번이 있는데 안나오는 이유 -> inner join으로 작성했음
			// inner join은? 다른 말로 equal join인데, 일치하는 것만 조회한다, 뭐가 일치? join할 때 쓰는 매칭시키는 컬럼의 값이 같다, 일치해야한다
			// 사진게시판에 insert 할때는 category를 넣지 않았음, 일반 게시글은 insert할때 카테고리를 넣음
			// 그러니 무조건 join할때 category 일치하는게 있음
			// 사진게시판은 category를 안넣으니 null이 들어감, equal인 컬럼이 없으므로 조회되지않는다
			// 선택할 수 있는 방법 두가지 -> 만들어둔 바퀴를 쓸 수 없으니 1번 새 바퀴를 만들자
			// 2번 만들어둔 SQL문 수정 -> 안되는 이유가 inner join이라서였음, 일치하는게 없어도 당기고 싶은거니까 left
			// -> 매퍼의 join을 left join으로 바꾼다, 그래도 기존것이 영향을 받지 않음
			// 선택의 영역이다, 새 바퀴를 만들면 기능 최적화를 해서 가져올 수 있음, 작성자나 카테고리는 굳이 필요없음
			// 한 행이라 큰 영향은 없지만 성능이슈가 있을수도?
			// 만든거 있으니 left만 달아서 호출해서 사용하는 방법이 있음
			
			// 보통은 기존것을 다른사람이 만들었을 확률이 있어서, 이걸 건드리면 원래 잘되던 기능이 망가질수있음
			// 지금 우리 상황에서는 문제 없을 것 같다고 생각할수도 있겠지
			// 보통 많은 기업들이 일을 하던 방식은 새로 만드는 방식, 그러면 한두번은 괜찮겠지만 변동마다 코드가 덕지덕지...
			// 아마 회사에 가면 덕지덕지 코드가 많을것이다, 그걸 손댈수가 없음, 어쩔 수 없이 또 덧대게 될것이고
			// 서비스기업은 이게 덜하고, 그냥 개발회사라면 이게 심해짐
			// 개발자로 취업해서 간다면 일을 하게되는 방식이 크게 두가지 -> 신규개발(없는거 새로 만들기) 또는 고도화 작업
			// 덕지덕지해서 더이상 어떻게 할 수 없는, 뜯어고칠수없어서 싹갈아없고 새세대로 가자
			// 고도화가 어려울수있음, 기존코드와 새것을 다 알아야함, 그걸로 먹고사는 경우도 많다
			
			// 영업-운영-개발 사이가 좋지않은 트리오... 영업할때 다 된다고 해놓고 개발한테 되게 해달라고 사죄
			// 개발이랑 운영도 사이가 안좋음.. 영업에서 새로운걸 들고오거나 위에서 시켜서 새로운거 개발함
			// 새로개발하면 항상 문제는 생긴다 근데 그건 운영잘못이 되어서 퇴근불가, 운영은 보수적으로 가길 원함, 기존대로 하면 사고가 적으니
			// 개발은 위에서 새로만들라하건 영업때문이건 만들어야하니까
			
			/*
			// 우리는 LEFT 조인으로 변경하는걸로 하자 -> 커밋을 하고 기록을 남겨놓자, 영향이 있을 수 있고, 문제가 생기면 되돌릴수있음
			// 딱히 영향받을것이 없으니 그대로 호출하도록 하자
			Board board = bd.selectBoard(sqlSession, boardNo.intValue());
			
			// 3. SELECT LIST KH_ATTACHMENT
			// 이것도 바퀴가 있나 고민 -> selectAttachment sql문 확인 -> order by만 붙이면 대표이미지부터 순서대로 조회가능할듯
			// 매퍼 수정
			// 일반게시판에서 쓰던거 order by에 의한 영향이 없을 수 있다
			// service에서 attachment 전부를 들고가야하는데 select하면? 일반게시판 조회할때 쓰던거라 이건 메소드를 selectOne으로 써서 이 메소드를 호출할수가 없음
			// sql문은 만들어둔걸 써야하는데 메소드를 쓸 수 없으니, dao에 메소드만 새로 만들자
			List<Attachment> files = bd.selectAttachmentList(sqlSession, boardNo.intValue());
			
			// select 해온 board와 첨부파일을 map에 담아서 돌아가자
			Map<String, Object> map = new HashMap();
			map.put("board", board);
			map.put("files", files);
			
			마이바티스 collection 쓰기전에 주석처리
			*/
			
			// -----
			BoardDto boards = bd.selectBoardAndAttachment(sqlSession, boardNo);
			// System.out.println(boards);
			// 한번만 가서 가공매핑 잘해서 끌고오기가 가능해짐, 결과는 동일하지만 과정만 바뀌었다
			// 대부분은 오토매핑으로 간단히 가능하지만 이런 상황(일대다관계)에서 resultMap 아주굿
			// 파일만 달린게 아니라 후기 댓글 연관게시글 등 List 필드가 여러개면 select여러번해야하는데 그렇게 안하고 테이블 여러개 조인하고 collection만 추가하면 한번에 퉁
			// sql문이 복잡하면 복잡할수록 result map의 효용이 좋아진다
			// 공식문서를 잘 읽어봅시다
			// 오토매핑할때 달아주는 별칭 조금 귀찮다면? 세팅에 mapUnderScoreToCamelCase 이런거 추가해서 가능하기도함
			// typeAliases도 그냥 사용하는게 아니라 패키지로 쓰면 풀클래스명 필요없이 vo로 쓰고 안에꺼 클래스명만 가져다 쓸수도있고, mapper도 패키지안에 한번에 등록
			// 동적 sql에도 foreach 쓰는법도 있다
			// 페이징처리 오라클 offset으로 했지만 마이바티스 RowBounds로도 가능, 이러면 오라클말고 MySQL 쓰는 회사에서도 쓸수있고
			// SQL문도 xml로 안쓰고 그냥 코드로 바로쓰기도 가능하고... 공식문서가 보물창고로군
			// -----
			
			return boards;
			
			// DB에 두번가야한다는 사실이 마음에 안든다, 돈이 많이 나오긴 싫음
			// 만명이 한번하면 만번인걸.. 사용자가 많을수록 DB에 갈 횟수가 기하급수적으로 늘어나서 돈이많이들어
			// 한번 가는것이 조금 더 경제적일듯! -> 실제 금전적인것도 포함이고 컴퓨팅 자원도 포함임
			// 커넥션을 두번 왔다갔다 하는동안 쓰고있음, DB는 커넥션 사용제한이 있으니 앞사람이 써야 다음사람이 쓰니 시간도 오래걸리니 비경제적
			// 어떤 생각을 해야할까? 조회를 하긴 할건데, 조회할 때 한번에 가져가면 안될까? 그럴수도있잖아
			// boardNo가 조인에 쓸 수 있으니까! REF_BNO랑 BOARD_NO가 같은거니까 어떻게 잘안될까?
			// 문제가 있음
			// select의 결과는 BOARD 결과가 한행인데 ATTACHMENT 결과는 최대 4행이니, 조인해서 조회해버리면 첨부 다른 행에 의해 같은 BOARD가 나온다
			// 조회 RESULT 행 수가 맞지 않음, 돌아갈때 BOARD는 하나로 퉁치고 첨부는 리스트에 담아야하니까 이게 아주 근본적인 문제
			// 자바라는 프로그래밍 언어는 데이터를 객체로 관리하는게 목적 <- 여기서 문제가 생김
			// 오라클뿐만 아니라 관계형 DBMS는 데이터 관리를 테이블로 하는게 추구하는 방법
			// 결국 자바랑 RDBMS는 데이터관리방식이 안맞음 -> 있어보이는 표현 패러다임 불일치
			// 자바로 웹개발하는데 관계형 데이터 베이스 쓰는건 별로 좋은 선택이 아님, 데이터 관리방법이 안맞아서
			// 근데 써.. 대체재가 없고 안정적이고 오래씀, 더욱 안정적인 무언가가 없어서 어쩔수없이 쓰고있음
			// 객체형 DB로 옮기기엔 너무 많이 해뒀고, 기존게 안정적이라서 못바꿈. RDBMS 안정적이라 쓰는것뿐
			// 이런 상황에서 문제 해결하기 -> VO에 SET으로 담으면 중복제거되니 가능하지만 좀더 똑똑한 방법 써보자
			
			// 마이바티스의 result maps
			// 마땅히 매핑할때가 없을 때 map으로 리턴하면 key-value가 담기는데 별로임
			// 이런 상황에서 대부분 자바빈이나 플레인 오브젝트(VO라고 생각, plain old java object)를 씀
			// POJO는 자카르타 EE라는 개념이 있는데, 자바로 웹개발을 할 때 개발자들이 자기마음대로 했더니 유지보수가 안됨
			// 만든사람만 코드를 알아볼 수 있어서 문제가 생기면 고칠 때 만든 사람을 데려와야함, 돈이 많이 든다
			// 개발할 때 규격을 만들자 -> 웹개발 명세를 만들어서 사용할것과 규격을 정해두게됨, 엄청 엄격하게 정해버림
			// DB에서 조회해서 리턴할때 우리가 만든 VO를 못쓰고 규격을 상속받고 오버라이딩해서 써야하니 불편했다
			// 스프링으로 넘어가면서 사람들이 만들어서 쓸수있게 해줌, POJO -> 똑같이 getter/setter, 생성자 이런게 있음
			// 오토매핑으로 쓰는거 알려주는게 있고.. 우리가 할 건 복잡한 결과매핑
			// DB는 본인이 만들어쓰는경우가 아닐수있으니 정규화가 잘 되어있지 않을 수 있어서 매핑을 VO에 완벽히 못함
			// 조인이 여러개 증가하면 각 매핑해야할 VO가 전부 다르게됨, 이럴때 사용 가능한 result map
			// 도구들이 많다 그중에 collection을 써보자
			// 1:1은 association, 1:다는 collection
			// 지금처럼 하나의 게시글에 여러개 첨부파일 -> 일대다 관계, 실제로 가장 많이 표현되는 관계
			// 하나의 게시글에 여러개의 댓글, 하나의 상품에 여러개의 리뷰, 하나의 게시글에 여러개의 첨부파일 등
			// 한명의 회원이 여러개의 게시글 이런것도 전부 일대다관계
			// 한꺼번에 조회할때 collection을 써서 알차게 조회가능 -> 맞춤 클래스 생성하러 감, dto에 생성
			
		}
		
		return null;
		
	}

}
