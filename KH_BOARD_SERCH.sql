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