package com.poscodx.jblog.controller;

import java.util.List;
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
import com.poscodx.jblog.service.PostService;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;
import com.poscodx.jblog.vo.UserVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {
    
    @Autowired 
    private BlogService blogService;
    
    @Autowired 
    private CategoryService categoryService;
    
    @Autowired 
    private PostService postService;
    
    
    /**
     * 블로그 첫 페이지 접근
     */
    @RequestMapping({"", "/{categoryNo}", "/{categoryNo}/{postNo}" })
    public String index(
        @PathVariable("id") String id,
        @PathVariable Optional<Long> categoryNo,
        @PathVariable Optional<Long> postNo, 
        HttpSession session, Model model
    ) {
        // 카테고리 번호가 있는지 체크
        if (categoryNo.isPresent()) {
            Long categoryNumber = categoryNo.get();
            // 카테고리 번호가 있는 경우의 처리 로직
        } else {
            // 카테고리 번호가 없는 경우의 처리 로직
        }

        // 세션에서 유저 정보를 가져와서 블로그 정보를 조회
        UserVo user = (UserVo) session.getAttribute("authUser");
        String userId = user.getId();
        BlogVo blog = blogService.getBlog(userId);
        model.addAttribute("blog", blog);
        
        return "blog/main";
    }
    
    /**
     * 블로그 기본 설정 페이지 접근
     */
    @Auth
    @RequestMapping(value="/admin/basic", method=RequestMethod.GET)
    public String adminBasic(@PathVariable("id") String id) {
        return "blog/admin-basic";
    }

    /**
     * 블로그 카테고리 관리 페이지 접근
     */
    @Auth
    @RequestMapping(value="/admin/category", method=RequestMethod.GET)
    public String adminCategory(@PathVariable("id") String id, Model model) {
        // 카테고리 리스트를 조회하여 모델에 추가
        List<CategoryVo> list = categoryService.getCategories(id);
        model.addAttribute("list", list);
        return "blog/admin-category";
    }
    
    /**
     * 카테고리 등록 처리
     */
    @Auth
    @RequestMapping(value="/admin/category/register", method=RequestMethod.POST)
    public String categoryRegister(CategoryVo vo, @PathVariable("id") String id) {
        // 카테고리 정보를 저장
        categoryService.insertCategory(vo.getName(), vo.getDescription(), id);
        return "redirect:/" + id + "/admin/category";
    }

    /**
     * 블로그 글 작성 페이지 접근
     */
    @Auth
    @RequestMapping(value="/admin/write", method=RequestMethod.GET)
    public String adminWrite(@PathVariable("id") String id, Model model) {
        // 모든 카테고리를 조회하여 모델에 추가
        List<CategoryVo> list = categoryService.getCategories(id);
        model.addAttribute("list", list);
        
        return "blog/admin-write";
    }
    
    @Auth 
    @RequestMapping(value="/admin/write", method=RequestMethod.POST) 
    public String adminWrite(PostVo postvo, @PathVariable("id") String id) {
    	
    	// 폼에서 값을 가져와서 설정을 해주어야 한다 !! 
    	// 어떻게 하면 할 수 있을까 
    	
       postService.addPost(postvo);
       return "redirect:/" + id + "/admin/category";
    	
    }
    
    // 삭제하기 
   
    @Auth
    @RequestMapping(value="/admin/category/delete/{categoryId}", method=RequestMethod.GET)
    public String deleteCategory(@PathVariable("id") String id, @PathVariable("categoryId") Long categoryId) {
        categoryService.deleteCategory(categoryId);
        postService.deletePostByCategory(categoryId);
        
        return "redirect:/" + id + "/admin/category"; // redirect 경로에서 id를 사용
    }
}
