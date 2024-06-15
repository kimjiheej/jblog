package com.poscodx.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.PostVo;
import com.poscodx.jblog.vo.UserVo;

@Repository
public class PostRepository {

private SqlSession sqlSession;
	
	public PostRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	
	
	public void addPost(PostVo postvo) {
		sqlSession.insert("post.insert", postvo);
	}



	public void deletePostByCategory(Long categoryId) {
		// TODO Auto-generated method stub
		sqlSession.delete("post.deletePostByCategory", categoryId);
		
	}
	
	


}
