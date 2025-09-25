package com.kh.java.member.model.vo;

import java.util.Date;

public class Member {
	
	// 변수명은 _빼고 카멜케이스 할거임, 접근제한자는 private, 제약조건은 신경쓰지않아도 되고
	// CHAR자료형을 자바에서 char로 해야할텐데 그러면 불편하니까 String으로 작업하자
	
	private Long userNo; // USER_NO NUMBER PRIMARY KEY, 시퀀스로 만들거고 PK라 숫자 들어가는데 보통 Long 사용한다
	// int vs long이면 long이 우세하다. int는 4바이트, 표현값이 -21억9천 뭐시기부터 해서 43억개정도, 시퀀스로 증가시킨다면 음수범위는 표현불가, 시퀀스는 1부터니까 실질적으로 사용가능한 int의 범위는 21억개정도
	// 사실 테이블의 모든 데이터가 21억개 넘을 수 있음, 사용자 21억명 넘을 수 있지, 게시글도 잘나가는 사이트라면, 댓글들 하면 수백만개씩 하루에 달린다
	// 로그데이터 1초에 수십개씩 찍힘, 그럼 int로는 부족함, 표현할 수 없는 범위까지 숫자가 생기는 경우가 굉장히 많음
	// 이거 통계 있다, 웹서비스 처음부터 2018까지 만들어진 데이터보다 2018이후부터 만들어진 데이터가 훨씬많다, 코로나탓
	// 옛날이라면 int로 퉁쳐지지만 오늘날은 long써야함, 이건 죽었다깨나도 다못씀, 920경 정도 표현가능
	// 나중에 long으로 고치지말고 처음부터 long쓰자는게 디폴트, 게시글 카테고리 만드는 그정도 간단한거만 int로
	// 대부분의 상황에서는 long이 압도적으로 우세
	// long vs Long 이게 그럼 다음 문제(primitive or Wrapper 중에 고르는것)
	// 보편적으로는 Wrapper를 선호(Long), 가장 큰 이유는 프레임워크 호환성, 마이바티스 쓰는데 자바의 가장 대표적인 ORM은 하이버네이트? 이게있는데 그쪽이 Wrapper가 호환성이 더 잘 맞음
	// 하이버네이트는 래퍼가 강제되는 경우가 있음, 뒷단 레포지토리 보면 그런경우가 있다
	// 마이바티스의 경우도 공식문서와 실제로 둘다 Long이 더 호환잘됨
	// 나중에 데이터를 앞단에 보내줄때 JS 객체 형태로 보낼것임, 하나의 VO를 하나의 JS객체로
	// 그럼 모양이 long의 경우는 { "userNo" : "0" } 또는 Long의 경우는 { "userNo" : null }이렇게 보낼 수 있는데 0인 경우에 값이 있는건지 null인지 헷갈림, 진짜 0인지 값이 없어서 0으로 넣은건지
	// 마이바티스 쓰면 어차피 Long 클래스로 변환되기 때문에 상관없음 그래서 과감하게 Long으로 선택
	// ??? 15:23 PK는 Long으로 쓴다, PK가 아니면 다른 선택지가 있음, 솔직히 데이터 낭비라서 손해볼 수 있지만 결국 PK는 한테이블에 하나뿐이라 이런경우는 Long을 사용하는것이 압도적으로 선호된
	private String userId; // USER_ID VARCHAR2(30) UNIQUE NOT NULL, 이건 아무생각없이 String 가능
	private String userPwd; // USER_PWD VARCHAR2(30) NOT NULL,
	private String userName; // USER_NAME VARCHAR2(20) NOT NULL,
	private String email; // EMAIL VARCHAR2(30) NOT NULL,
	private Date enrollDate; // ENROLL_DATE DATE DEFAULT SYSDATE,
	private Date modifyDate; // MODIFY_DATE DATE DEFAULT SYSDATE,
	private String status; // STATUS CHAR(1) DEFAULT 'Y' CHECK(STATUS IN ('Y', 'N'))
	
	public Member() {
		super();
	}
	
	public Member(Long userNo, String userId, String userPwd, String userName, String email, Date enrollDate,
			Date modifyDate, String status) {
		super();
		this.userNo = userNo;
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.email = email;
		this.enrollDate = enrollDate;
		this.modifyDate = modifyDate;
		this.status = status;
	}

	public Long getUserNo() {
		return userNo;
	}

	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Member [userNo=" + userNo + ", userId=" + userId + ", userPwd=" + userPwd + ", userName=" + userName
				+ ", email=" + email + ", enrollDate=" + enrollDate + ", modifyDate=" + modifyDate + ", status="
				+ status + "]";
	}

}
