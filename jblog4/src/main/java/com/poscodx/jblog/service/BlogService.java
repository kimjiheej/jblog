package com.poscodx.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poscodx.jblog.repository.BlogRepository;
import com.poscodx.jblog.vo.BlogVo;

@Service
public class BlogService {

	
	 @Autowired  
	 private BlogRepository blogRepository;


	 @Transactional
	public void make(String id, String title, String logo) {
		blogRepository.makeBlog(id,title,logo);
	}


	public BlogVo getBlog(String userId) {
	 return   blogRepository.getBlog(userId);
	}

	@Transactional
	public void updateBlog(String id, String title, String logo) {
		blogRepository.update(id, title, logo);
		
	}
 
	 
}
