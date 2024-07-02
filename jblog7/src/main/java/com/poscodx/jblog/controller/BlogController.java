package com.poscodx.jblog.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.jblog.security.UserDetailsImpl;
import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.CategoryService;
import com.poscodx.jblog.service.FileUploadService;
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

	@Autowired 
	private FileUploadService fileUploadService;

	@Autowired
	private ServletContext servletContext;


	   @RequestMapping({"", "/{categoryNo}", "/{categoryNo}/{postNo}"})
	    public String index(
	            @PathVariable("id") String id,
	            @PathVariable Optional<Long> categoryNo,
	            @PathVariable Optional<Long> postNo,
	            Model model, HttpSession session
	            ) {

		    
		    UserVo authUser = (UserVo) session.getAttribute("authUser");

		//    boolean check = false;

//		    if(authUser != null && id.equals(authUser.getId())) {
//		        check = true;
//		    }
	        // 블로그 정보 가져오기
	        BlogVo blog = blogService.getBlog(id);
	        model.addAttribute("blog", blog);
	        model.addAttribute("blogId", id);  // 블로그 ID를 모델에 추가
	        
	 
	    //    model.addAttribute("check" , check);

	        // 무조건 있어야 하는 카테고리 리스트 가져오기
	        List<CategoryVo> list = categoryService.getCategories(id);
	        model.addAttribute("list", list);

	        if (categoryNo.isPresent() && postNo.isPresent()) {
	            // categoryNo와 postNo가 모두 존재할 때
	            Long catNo = categoryNo.get();
	            Long postNoValue = postNo.get();

	            PostVo post = postService.getPost(postNoValue);
	            List<PostVo> postList = postService.getAllPosts(catNo);

	            model.addAttribute("blogId", id);
	            model.addAttribute("categoryNo", catNo);
	            model.addAttribute("firstPage", post);
	            model.addAttribute("postList", postList);

	        } else if (categoryNo.isPresent()) {
	            // categoryNo만 존재할 때
	            Long catNo = categoryNo.get();

	            List<PostVo> postList = postService.getAllPosts(catNo);
	            PostVo post = postService.getSmallPost(catNo);

	            model.addAttribute("blogId", id);
	            model.addAttribute("categoryNo", catNo);
	            model.addAttribute("firstPage", post);
	            model.addAttribute("postList", postList);

	        } else {
	            // 카테고리나 포스트 번호가 없는 경우
	            // '미분류' 카테고리의 번호를 가져와 해당 카테고리의 모든 포스트를 가져옴
	            Long undefinedCategoryNo = categoryService.getUnDefinedCategory(id);

	            List<PostVo> mainPost = postService.getAllPosts(undefinedCategoryNo);
	            PostVo latestPost = mainPost.stream()
	                                        .max(Comparator.comparing(PostVo::getReg_date))
	                                        .orElse(null);
                
	            model.addAttribute("blogId", id);
	            model.addAttribute("firstPage", latestPost);
	            model.addAttribute("categoryNo", undefinedCategoryNo);
	            model.addAttribute("postList", mainPost);
	        }

	        return "blog/main";
	    }
	
	

	/**
	 * 블로그 기본 설정 페이지 접근
	 */
	
	   @RequestMapping(value="/admin/basic", method=RequestMethod.GET)
	   public String adminBasic(Authentication authentication, @PathVariable("id") String id, Model model) {
	       // 인증 정보가 없으면 로그인 페이지로 리다이렉트
	       if (authentication == null) {
	           return "redirect:/";
	       }

	       UserVo authUser = (UserVo) authentication.getPrincipal();

	       // 로그인한 사용자의 ID와 요청된 ID가 일치하는지 확인
	       if (!id.equals(authUser.getId())) {
	           return "redirect:/";
	       }

	       // 블로그 정보를 가져와서 모델에 추가
	       BlogVo blog = blogService.getBlog(id);
	       model.addAttribute("blog", blog);

	       return "blog/admin-basic";
	   }
	
	
    
	   @PostMapping("/admin/basic/update")
	    public String update(Authentication authentication, Model model, BlogVo vo, @PathVariable("id") String id, @RequestParam("logo-file") MultipartFile file, HttpSession session) {
	        // Check if user is authenticated
	        if (authentication == null || !authentication.isAuthenticated()) {
	            return "redirect:/";
	        }

	        // Get authenticated user details
	        UserVo authUser = (UserVo) authentication.getPrincipal();

	        // Check if the logged-in user matches the requested ID
	        if (!id.equals(authUser.getId())) {
	            return "redirect:/";
	        }

	        // Load existing blog data
	        BlogVo existingBlog = blogService.getBlog(id);
	        if (existingBlog == null) {
	            throw new RuntimeException("Blog not found for id: " + id);
	        }

	        // Update title if provided, otherwise keep existing
	        String title = vo.getTitle() != null ? vo.getTitle() : existingBlog.getTitle();

	        // Update logo if provided, otherwise keep existing
	        String logo = file.isEmpty() ? existingBlog.getLogo() : fileUploadService.restore(file);

	        // Set updated values to BlogVo object
	        vo.setUser_id(id);
	        vo.setTitle(title);
	        vo.setLogo(logo);

	        // Update blog in database
	        blogService.updateBlog(vo.getUser_id(), vo.getTitle(), vo.getLogo());

	        // Set updated blog information in session attribute
	        servletContext.setAttribute("updatedvo", vo);

	        // Redirect to the admin basic settings page
	        return "redirect:/" + id + "/admin/basic";
	    }

	    /**
	     * Access to the admin category management page.
	     */
	    @GetMapping("/admin/category")
	    public String adminCategory(Authentication authentication, @PathVariable("id") String id, Model model) {
	        // Check if user is authenticated
	        if (authentication == null || !authentication.isAuthenticated()) {
	            return "redirect:/";
	        }

	        // Get authenticated user details
	        UserVo authUser = (UserVo) authentication.getPrincipal();

	        // Check if the logged-in user matches the requested ID
	        if (!id.equals(authUser.getId())) {
	            return "redirect:/";
	        }

	        // Fetch categories and add to model
	        List<CategoryVo> list = categoryService.getCategories(id);
	        model.addAttribute("list", list);

	        // Load blog information and add to model
	        BlogVo blog = blogService.getBlog(id);
	        model.addAttribute("blog", blog);

	        return "blog/admin-category";
	    }

	    /**
	     * Handle category registration.
	     */
	    @PostMapping("/admin/category/register")
	    public String categoryRegister(Authentication authentication, CategoryVo vo, @PathVariable("id") String id, HttpSession session) {
	        // Check if user is authenticated
	        if (authentication == null || !authentication.isAuthenticated()) {
	            return "redirect:/";
	        }

	        // Get authenticated user details
	        UserVo authUser = (UserVo) authentication.getPrincipal();

	        // Check if the logged-in user matches the requested ID
	        if (!id.equals(authUser.getId())) {
	            return "redirect:/";
	        }

	        // Insert category
	        categoryService.insertCategory(vo.getName(), vo.getDescription(), id);
	        return "redirect:/" + id + "/admin/category";
	    }

	    /**
	     * Access to the blog post writing page.
	     */
	    @GetMapping("/admin/write")
	    public String adminWrite(Authentication authentication, @PathVariable("id") String id, Model model, HttpSession session) {
	        // Check if user is authenticated
	        if (authentication == null || !authentication.isAuthenticated()) {
	            return "redirect:/";
	        }

	        // Get authenticated user details
	        UserVo authUser = (UserVo) authentication.getPrincipal();

	        // Check if the logged-in user matches the requested ID
	        if (!id.equals(authUser.getId())) {
	            return "redirect:/";
	        }

	        // Fetch categories and add to model
	        List<CategoryVo> list = categoryService.getCategories(id);
	        model.addAttribute("list", list);

	        // Load blog information and add to model
	        BlogVo blog = blogService.getBlog(id);
	        model.addAttribute("blog", blog);

	        return "blog/admin-write";
	    }

	    /**
	     * Handle blog post submission.
	     */
	    @PostMapping("/admin/write")
	    public String adminWrite(Authentication authentication, PostVo postvo, @PathVariable("id") String id, HttpSession session) {
	        // Check if user is authenticated
	        if (authentication == null || !authentication.isAuthenticated()) {
	            return "redirect:/";
	        }

	        // Get authenticated user details
	        UserVo authUser = (UserVo) authentication.getPrincipal();

	        // Check if the logged-in user matches the requested ID
	        if (!id.equals(authUser.getId())) {
	            return "redirect:/";
	        }

	        // Add post
	        postService.addPost(postvo);
	        return "redirect:/" + id;
	    }

	    /**
	     * Delete a category.
	     */
	    @GetMapping("/admin/category/delete/{categoryId}")
	    public String deleteCategory(Authentication authentication, @PathVariable("id") String id, @PathVariable("categoryId") Long categoryId, HttpSession session) {
	        // Check if user is authenticated
	        if (authentication == null || !authentication.isAuthenticated()) {
	            return "redirect:/";
	        }

	        // Get authenticated user details
	        UserVo authUser = (UserVo) authentication.getPrincipal();

	        // Check if the logged-in user matches the requested ID
	        if (!id.equals(authUser.getId())) {
	            return "redirect:/";
	        }

	        // Delete category
	        categoryService.deleteCategory(categoryId);
	        return "redirect:/" + id + "/admin/category";
	    }
	}