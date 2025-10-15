-- 제목으로 검색

SELECT BOARD_TITLE, BOARD_NO
  FROM KH_BOARD
 WHERE
       BOARD_TITLE LIKE '%' || '테' || '%';

SELECT BOARD_TITLE, BOARD_NO
  FROM KH_BOARD
 WHERE
       BOARD_CONTENT LIKE '%' || '다' || '%';

SELECT BOARD_TITLE, BOARD_NO
  FROM KH_BOARD
  JOIN KH_MEMBER ON (BOARD_WRITER = USER_NO)
 WHERE
       USER_NAME LIKE '%' || '홍' || '%';

-- 사용자가 조건을 내용으로 키워드를 다 라고 입력한 게시글의 개수
SELECT COUNT(*)
  FROM KH_BOARD JOIN KH_MEMBER ON (BOARD_WRITER = USER_NO)
 WHERE
       KH_BOARD.STATUS = 'Y'
   AND
       BOARD_CONTENT LIKE '%다%';

-- 사용자가 조건을 제목으로 키워드를 다 라고 입력한 게시글의 개수
SELECT COUNT(*)
  FROM KH_BOARD JOIN KH_MEMBER ON (BOARD_WRITER = USER_NO)
 WHERE
       KH_BOARD.STATUS = 'Y'
   AND
       BOARD_TITLE LIKE '%다%';

-- 이걸 마이바티스 동적 sql써서 하려고 했으니 무슨 조건을 선택했는지, 검색어를 뭘 입력했는지 같이 넘겨야 매퍼에서 이걸로 검색할수있음

SELECT
	   BOARD_NO
	 , BOARD_TITLE
	 , COUNT
  FROM
       KH_BOARD
 WHERE
       BOARD_TYPE = 2
   AND
       STATUS = 'Y'
 ORDER
    BY
       BOARD_NO DESC;

SELECT * FROM KH_BOARD ORDER BY BOARD_NO DESC;

SELECT
       FILE_PATH
     , CHANGE_NAME
     , REF_BNO
  FROM
       KH_ATTACHMENT
 WHERE
       STATUS = 'Y'
   AND
       FILE_LEVEL = 1;

SELECT
       BOARD_NO
     , BOARD_TITLE
     , COUNT
     , FILE_PATH
     , CHANGE_NAME
  FROM
       KH_BOARD
  JOIN
       KH_ATTACHMENT ON (REF_BNO = BOARD_NO)
 WHERE
       BOARD_TYPE = 2
   AND
       KH_BOARD.STATUS = 'Y'
 ORDER
    BY
       BOARD_NO DESC;

SELECT
       BOARD_NO boardNo
     , USER_NAME boardWriter
     , CATEGORY_NAME category
     , BOARD_TITLE boardTitle
     , BOARD_CONTENT boardContent
  FROM
       KH_BOARD
  LEFT
  JOIN
       KH_CATEGORY USING (CATEGORY_NO)
  JOIN
       KH_MEMBER ON (BOARD_WRITER = USER_NO)
 WHERE
       KH_BOARD.STATUS = 'Y'
   AND
       BOARD_NO = 71;

SELECT
       FILE_NO fileNo
     , ORIGIN_NAME originName
     , CHANGE_NAME changeName
     , FILE_PATH filePath
  FROM
       KH_ATTACHMENT
 WHERE
       STATUS = 'Y'
   AND
       REF_BNO = 71
 ORDER
    BY
       FILENO ASC;

SELECT
       BOARD_NO boardNo
     , BOARD_TITLE boardTitle
     , BOARD_CONTENT boardContent
     , FILE_NO fileNo
     , ORIGIN_NAME originName
     , CHANGE_NAME changeName
     , FILE_PATH filePath
  FROM
       KH_BOARD
  JOIN
       KH_ATTACHMENT ON (BOARD_NO = REF_BNO)
 WHERE
       KH_BOARD.STATUS = 'Y'
   AND
       REF_BNO = 71
 ORDER
    BY
       FILENO ASC;

--------------------------------------------------

SELECT
       USER_ID
  FROM
       KH_MEMBER
 WHERE
       USER_ID = 'admin';
-- 있을땐 아이디값(1행), 없을 땐 null(selectOne 쓸거니까)

SELECT
       COUNT(*)
  FROM
       KH_MEMBER
 WHERE
       USER_ID = 'admin';
-- 있을땐 1, 없을 땐 0(그룹함수니까 결과가 오긴 온다)

/*
 * String userId = bd.checkId(sqlSession, id);
 * int result = bd.checkId(sqlSession, id);
 * 
 * if(userId != null || result > 0) {
 * 		return "NNNNN"
 * } else {
 * 		return "NNNNY"
 * }
 * 
 */

SELECT DECODE(COUNT(*), 0, 'NNNNY', 1, 'NNNNN') FROM KH_MEMBER WHERE USER_ID = 'admin3';
-- return bd.checkId(sqlSession, id);
-- 서버자원을 덜쓰고 DB에서 SQL문으로 바로 가능, 자바에서 쓸 코드를 줄일 수 있다, 빠르기도 함
-- 아무튼 미리 SQL을 생각해둬야하는것