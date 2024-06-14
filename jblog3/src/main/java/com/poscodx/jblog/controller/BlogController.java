package com.poscodx.jblog.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poscodx.jblog.security.Auth;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {
	
	   @RequestMapping({"", "/{categoryNo}", "/{categoryNo}/{postNo}" })
	    public String index(
	        @PathVariable("id") String id,
	        @PathVariable Optional<Long> categoryNo,
	        @PathVariable Optional<Long> postNo) {

	        // categoryNo.isPresent()를 사용하여 값이 있는지 체크할 수 있습니다.
	        if (categoryNo.isPresent()) {
	            // 값이 존재하면 해당 값을 사용
	            Long categoryNumber = categoryNo.get();
	            // 처리 로직 작성
	        } else {
	            // 값이 존재하지 않으면 기본 처리 로직을 수행
	            // 기본값 설정 또는 다른 처리 방법 작성
	        }

	        return "blog/main";
	    }
	 @Auth
	@RequestMapping("/admin/basic")
	public String adminBasic(@PathVariable("id") String id) {
		return "blog/admin-basic";
	}

	@Auth
	@RequestMapping("/admin/category")
	public String adminCategory(@PathVariable("id") String id) {
		
		return "blog/admin-category";
	}
	
	@Auth
	@RequestMapping("/admin/write")
	public String adminWrite(@PathVariable("id") String id) {
		return "blog/admin-write";
	}

}

