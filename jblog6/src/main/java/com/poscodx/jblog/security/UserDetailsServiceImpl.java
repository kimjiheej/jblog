package com.poscodx.jblog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.poscodx.jblog.repository.UserRepository;
import com.poscodx.jblog.vo.UserVo;


public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		return userRepository.findBySecurityId(id ,  UserDetailsImpl.class);
	}

}