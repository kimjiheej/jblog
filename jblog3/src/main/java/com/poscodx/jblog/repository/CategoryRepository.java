package com.poscodx.jblog.repository;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepository {

	
private SqlSession sqlSession;
	
	public CategoryRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	  public int insertCategory(String name, String description, String id) {
		  System.out.println("insert Category" +name + description + id);
	        return sqlSession.insert("category.insert", Map.of("name", name, "description", description, "id", id));
	    }

}
