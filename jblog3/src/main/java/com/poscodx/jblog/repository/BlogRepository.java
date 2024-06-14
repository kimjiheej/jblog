package com.poscodx.jblog.repository;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.BlogVo;

@Repository
public class BlogRepository {

private SqlSession sqlSession;
	
	public BlogRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	
	public int makeBlog(String id, String title, String logo) {
		return sqlSession.insert(
				"blog.make",
				Map.of("id", id, "title", title,  "logo", logo));
	}

	public BlogVo makeBlog(String userId) { 
		return sqlSession.selectOne("blog.findByUserId", userId);
	}

	
}
