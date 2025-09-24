package com.kh.subway.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.kh.subway.common.Template;
import com.kh.subway.model.dao.SubwayDao;
import com.kh.subway.model.vo.Subway;

public class SubwayService {
	
private SubwayDao sd = new SubwayDao();
	
	public int insertOrder(Subway order) {
		
		SqlSession session = Template.getSqlSession();
		
		int result = sd.insertOrder(session, order);
		
		if(result > 0) {
			session.commit();
		}
		
		session.close();
		
		return result;
		
	}
	
	// 컨트롤러에서 호출해야하니까 접근제한자 public, 나중에 반환타입은 List<Subway>, 메소드명 정함, 받아온거 딱히없음
	public List<Subway> findAll() {
		
		// 여기서는 일단 마이바티스 객체 SqlSession 받아오기
		SqlSession session = Template.getSqlSession();
		
		// DAO호출, 필드로 빼놨음
		List<Subway> orderList = sd.findAll(session);
		// 나중에 반환형도 완성시켜두자
		
		// 나중에 반환받고나면 세션객체 반납
		session.close();
		
		// 컨트롤러로 결과값 반환
		return orderList;
		
	}

}
