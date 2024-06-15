package com.poscodx.jblog.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.CategoryService;
import com.poscodx.jblog.service.UserService;
import com.poscodx.jblog.vo.UserVo;


@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired 
	private BlogService blogService;
	
	@Autowired 
    private CategoryService categoryService;
	
	
	// 회원가입 할 수 있는 화면으로 전환하게 해주기 
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(UserVo vo) {
		return "user/join";
	} 
	
	  @RequestMapping(value="/join", method = RequestMethod.POST)
	    public String join(UserVo vo, Model model) {
		  
	        userService.join(vo);
	    	
	        // 회원의 id 를 가져온다 
		    String id  = vo.getId();
		    System.out.println();
		    // 회원가입 할때에 블로그를 하나 만들어준다 ! 
		    
		    blogService.make(id, id+"님의 블로그" , "loopy.png");
		    
		    // 기본 미분류 카테고리도 하나 설정해주어야 한다 
		    
		    
		   categoryService.insertCategory("미분류","카테고리를 지정하지 않은 경우",id);
		    
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
