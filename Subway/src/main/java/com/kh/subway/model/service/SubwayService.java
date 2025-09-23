package com.kh.subway.model.service;

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

}
