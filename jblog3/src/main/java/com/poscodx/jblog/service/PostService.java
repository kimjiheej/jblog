package com.poscodx.jblog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poscodx.jblog.repository.PostRepository;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;

@Service
public class PostService {


	@Autowired
	private PostRepository postRepository;
	
	@Transactional
	public void addPost(PostVo postvo) {
		postRepository.addPost(postvo);
	}

	@Transactional
	public void deletePostByCategory(Long categoryId) {
		postRepository.deletePostByCategory(categoryId);
	}

	public List<PostVo> getAllPosts(Optional<Long> categoryNo) {
	  return 	postRepository.getAllPosts(categoryNo);
	}

	public PostVo getPost(Optional<Long> postNo) {
		return postRepository.getOnePost(postNo);
	}

	public PostVo getSmallPost(Long categoryNo) {
		return postRepository.getSmallPost(categoryNo);
	}

	public List<PostVo> getAllPosts(Long categoryNo) {
		  return 	postRepository.getAllPosts(categoryNo);
	}

	public PostVo getSmallPost(Optional<Long> categoryNo) {
		return postRepository.getSmallPost(categoryNo);
	}


	
	
}
