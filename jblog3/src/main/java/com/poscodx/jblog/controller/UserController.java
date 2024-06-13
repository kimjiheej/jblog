package com.poscodx.jblog.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poscodx.jblog.service.UserService;
import com.poscodx.jblog.vo.UserVo;



@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	// 회원가입 할 수 있는 화면으로 전환하게 해주기 
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(UserVo vo) {
		return "user/join";
	} 
	
	  @RequestMapping(value="/join", method = RequestMethod.POST)
	    public String join( UserVo vo, Model model) {

	        userService.join(vo);
	        return "redirect:/user/joinsuccess";
	    }

	@RequestMapping(value="/joinsuccess", method=RequestMethod.GET)
	public String joinsuccess() {
		return "user/joinsuccess";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	

}
