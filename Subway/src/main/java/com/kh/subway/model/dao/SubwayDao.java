package com.kh.subway.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.kh.subway.model.vo.Subway;

public class SubwayDao {
	
	public int insertOrder(SqlSession session, Subway order) {
		
		// mapper 있어야한다.
		return session.insert("orderMapper.insertOrder", order);
		
	}
	
	public List<Subway> findAll(SqlSession session) {
		
		// mapper의 namespace속성 참조해서 태그의 id속성값 적을건데, 아직 안만들었지만 메소드명과 동일하게 적을거임
		return session.selectList("orderMapper.findAll");
		
	}

}
