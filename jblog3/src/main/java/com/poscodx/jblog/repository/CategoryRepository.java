package com.poscodx.jblog.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;

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

	public List<CategoryVo> getAll(String id) {
		return sqlSession.selectList("category.findAll",id);
	}

	public void deleteCategory(Long categoryId) {
		
		sqlSession.delete("category.delete", categoryId);
		
	}
	
	public int makeBlog(String id, String title, String logo) {
		return sqlSession.insert(
				"blog.make",
				Map.of("id", id, "title", title,  "logo", logo));
	}

	public Long getFirstCategory(String id, String unregistered) {
		
		return sqlSession.selectOne("category.getFirstCategory", Map.of("id",id,"un",unregistered));
		
	}

	
}
