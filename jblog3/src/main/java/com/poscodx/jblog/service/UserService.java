package com.poscodx.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poscodx.jblog.repository.BlogRepository;
import com.poscodx.jblog.repository.UserRepository;
import com.poscodx.jblog.vo.UserVo;


@Service
@Transactional
public class UserService {

	@Autowired 
	private UserRepository userRepository;
	
	@Autowired 
	private BlogRepository boardRepository;
	

	@Transactional
	public void join(UserVo vo) {
		  userRepository.insert(vo);
	}
	
	public UserVo getUser(String id, String password) {
		 return userRepository.findByIdAndPassword(id, password);
	}
	
	

}
