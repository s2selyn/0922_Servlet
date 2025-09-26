package com.kh.java.member.model.service;

import org.apache.ibatis.session.SqlSession;

import com.kh.java.common.Template;
import com.kh.java.member.model.dao.MemberDao;
import com.kh.java.member.model.dto.MemberDto;
import com.kh.java.member.model.vo.Member;

public class MemberService {
	
	private MemberDao md = new MemberDao();
	
	public Member login(Member member) {
		
		SqlSession sqlSession = Template.getSqlSession();
		
		Member loginMember = md.login(sqlSession, member);
		
		sqlSession.close();
		
		return loginMember;
		
	}
	
	public int join(MemberDto joinMember) {
		return 0;
		
	}
	
	public void validateMember(Member member) {
		
		if(member.getUserId() == null || member.getUserId().trim().isEmpty()) {
			return;
		}
		
		String pattern = "^[a-zA-Z0-9]{4,20}$";
		
		if(!member.getUserId().matches(pattern)) {
			return;
		}
		
	}

}
