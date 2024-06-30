package com.poscodx.jblog.security;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.poscodx.jblog.vo.UserVo;



public class UserDetailsImpl extends UserVo implements UserDetails  {
	private static final long serialVersionUID = 1L;

	
	 @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return null; // You can return authorities if needed
	    }

	 @Override
	    public String getPassword() {
	        return super.getPassword(); // UserVo의 getPassword() 메서드 호출
	    }

	@Override
	public String getUsername() {
		return getId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}