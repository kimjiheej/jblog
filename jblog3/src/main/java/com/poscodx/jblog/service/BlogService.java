package com.poscodx.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poscodx.jblog.repository.BlogRepository;

@Service
public class BlogService {

	
	 @Autowired  
	 private BlogRepository blogRepository;


	public void make(String id, String title, String logo) {
		blogRepository.makeBlog(id,title,logo);
	}
 
	 
}
