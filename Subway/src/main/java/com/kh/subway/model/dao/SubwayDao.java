package com.kh.subway.model.dao;

import org.apache.ibatis.session.SqlSession;

import com.kh.subway.model.vo.Subway;

public class SubwayDao {
	
	public int insertOrder(SqlSession session, Subway order) {
		
		// mapper 있어야한다.
		return session.insert("orderMapper.insertOrder", order);
		
	}

}
