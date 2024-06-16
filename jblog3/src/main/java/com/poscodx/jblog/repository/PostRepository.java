package com.poscodx.jblog.repository;

import java.util.List;
import java.util.Optional;

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



	public List<PostVo> getAllPosts(Optional<Long> categoryNo) {
		// TODO Auto-generated method stub
		
		return sqlSession.selectList("post.getAllPosts", categoryNo);
		
	}

	public PostVo getOnePost(Optional<Long> postNo) {
		return sqlSession.selectOne("post.getOnePost",postNo);
	}



	public PostVo getSmallPost(Long no) {
		return sqlSession.selectOne("post.getSmallPost", no);
	}



	public List<PostVo> getAllPosts(Long categoryNo) {
		return sqlSession.selectList("post.getAllPosts", categoryNo);
	}

	public PostVo getSmallPost(Optional<Long> categoryNo) {
		return sqlSession.selectOne("post.getSmallPost", categoryNo);
	}



	public Long countPostsByCategory(Long no) {
	
	    return sqlSession.selectOne("post.countPostsByCategory", no);
	}

}
