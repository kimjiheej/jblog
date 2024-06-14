package com.poscodx.jblog.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poscodx.jblog.security.Auth;
import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.CategoryService;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.UserVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {
	
	@Autowired 
	private BlogService blogService;
	
	@Autowired 
	private CategoryService categoryService;
	
	
	
       // 블로그 처음 접속 
	   @RequestMapping({"", "/{categoryNo}", "/{categoryNo}/{postNo}" })
	    public String index(
	        @PathVariable("id") String id,
	        @PathVariable Optional<Long> categoryNo,
	        @PathVariable Optional<Long> postNo, 
	        HttpSession session, Model model
	        ) {

	        // categoryNo.isPresent()를 사용하여 값이 있는지 체크할 수 있습니다.
	        if (categoryNo.isPresent()) {
	            // 값이 존재하면 해당 값을 사용
	            Long categoryNumber = categoryNo.get();
	            // 처리 로직 작성
	        } else {
	            // 값이 존재하지 않으면 기본 처리 로직을 수행
	            // 기본값 설정 또는 다른 처리 방법 작성
	        }
	        
	
		   UserVo user =  (UserVo) session.getAttribute("authUser");
		   String userId = user.getId();
		   BlogVo blog = blogService.getBlog(userId);
	       model.addAttribute("blog",blog);
		   
	        

	        
	        return "blog/main";
	    }
	   
	 @Auth
	@RequestMapping(value="/admin/basic", method=RequestMethod.GET)
	public String adminBasic(@PathVariable("id") String id) {
		return "blog/admin-basic";
	}

	@Auth
	@RequestMapping(value="/admin/category", method=RequestMethod.GET)
	public String adminCategory(@PathVariable("id") String id) {
		return "blog/admin-category";
	}
	
	@Auth 
	@RequestMapping(value="/admin/category/register", method=RequestMethod.POST) 
	public String categoryRegister(CategoryVo vo, @PathVariable("id") String id) {
		 categoryService.insertCategory(vo.getName(), vo.getDescription(), id);
		  return "redirect:/" + id + "/admin/category";
	}
	
	
	
	
	
	@Auth
	@RequestMapping("/admin/write")
	public String adminWrite(@PathVariable("id") String id) {
		return "blog/admin-write";
	}

}

