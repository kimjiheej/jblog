package com.poscodx.jblog.security;

import com.poscodx.jblog.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.poscodx.jblog.vo.*;
public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private UserService userService;

	public LoginInterceptor() {
		
	}
	public LoginInterceptor(UserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String id = request.getParameter("id");
		String password = request.getParameter("password");
		UserVo authUser = 	userService.getUser(id,password);

		
		if(authUser == null ) {
			request.setAttribute("id", id);
			request.setAttribute("result", "fail");
			request.getRequestDispatcher("/WEB-INF/views/user/login.jsp")
			.forward(request, response);
			return false;
		}

		/* 로그인 처리 */ 
		

	    HttpSession session = request.getSession(true);
	    session.setAttribute("authUser", authUser);
	    response.sendRedirect(request.getContextPath());
	    return false;
	}


}
