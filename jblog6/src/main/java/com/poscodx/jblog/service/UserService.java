package com.poscodx.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	
	 
	 @Autowired
		private PasswordEncoder passwordEncoder;

	 
	@Transactional
	public void join(UserVo vo) {
		vo.setPassword(passwordEncoder.encode(vo.getPassword()));
		  userRepository.insert(vo);
	}
	
	public UserVo getUser(String id, String password) {
		 return userRepository.findByIdAndPassword(id, password);
	}
	
	public UserVo getUser(String id) {
		return userRepository.findBySecurityId(id, UserVo.class);
	}
	
}
