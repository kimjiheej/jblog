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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.jblog.security.Auth;
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
	@Auth
	@RequestMapping(value="/admin/basic", method=RequestMethod.GET)
	public String adminBasic(@PathVariable("id") String id, Model model, HttpSession session) {


		UserVo user = (UserVo) session.getAttribute("authUser");
		if(id.equals(user.getId())) {

			
			   BlogVo blog = blogService.getBlog(id);
		        model.addAttribute("blog", blog);
			return "blog/admin-basic";
		}
		else {
			return "redirect:/";
		}
	}

	

	@Auth
	@RequestMapping(value="/admin/basic/update", method=RequestMethod.POST)
	public String update(Model model,BlogVo vo, @PathVariable("id") String id, @RequestParam("logo-file") MultipartFile file, 
			HttpSession session) {

		UserVo user = (UserVo) session.getAttribute("authUser");
		if(id.equals(user.getId())) {

			// 기존 데이터 로드
			BlogVo existingBlog = blogService.getBlog(id);
			if (existingBlog == null) {
				throw new RuntimeException("Blog not found for id: " + id);
			}

			// 타이틀 업데이트 확인
			String title = Optional.ofNullable(vo.getTitle()).orElse(existingBlog.getTitle());

			// 로고 파일 업데이트 확인
			String logo = Optional.ofNullable(fileUploadService.restore(file))
					.filter(str -> !str.isEmpty())
					.orElse(existingBlog.getLogo());

			vo.setUser_id(id);
			vo.setTitle(title);
			vo.setLogo(logo);

			blogService.updateBlog(vo.getUser_id(), vo.getTitle(), vo.getLogo());

			//  model.addAttribute("updatedvo", vo);
			servletContext.setAttribute("updatedvo", vo);
			   BlogVo blog = blogService.getBlog(id);
		        model.addAttribute("blog", blog);
			return "redirect:/" + id + "/admin/basic";
		} else {
			return "redirect:/";
		}
	}




	/**
	 * 블로그 카테고리 관리 페이지 접근
	 */
	@Auth
	@RequestMapping(value="/admin/category", method=RequestMethod.GET)
	public String adminCategory(@PathVariable("id") String id, Model model, HttpSession session) {
		// 카테고리 리스트를 조회하여 모델에 추가

		UserVo user = (UserVo) session.getAttribute("authUser");
		if(id.equals(user.getId())) {


			List<CategoryVo> list = categoryService.getCategories(id);
			model.addAttribute("list", list);
			   BlogVo blog = blogService.getBlog(id);
		        model.addAttribute("blog", blog);
			return "blog/admin-category";
		} else {
			return "redirect:/";
		}
	}

	/**
	 * 카테고리 등록 처리
	 */
	@Auth
	@RequestMapping(value="/admin/category/register", method=RequestMethod.POST)
	public String categoryRegister(CategoryVo vo, @PathVariable("id") String id, HttpSession session) {
		// 카테고리 정보를 저장

		UserVo user = (UserVo) session.getAttribute("authUser");
		if(id.equals(user.getId())) {


			categoryService.insertCategory(vo.getName(), vo.getDescription(), id);
			return "redirect:/" + id + "/admin/category";
		} else {
			return "redirect:/";
		}
	}

	/**
	 * 블로그 글 작성 페이지 접근
	 */
	@Auth
	@RequestMapping(value="/admin/write", method=RequestMethod.GET)
	public String adminWrite(@PathVariable("id") String id, Model model, HttpSession session) {
		// 모든 카테고리를 조회하여 모델에 추가

		UserVo user = (UserVo) session.getAttribute("authUser");
		if(id.equals(user.getId())) {


			List<CategoryVo> list = categoryService.getCategories(id);
			model.addAttribute("list", list);
			   BlogVo blog = blogService.getBlog(id);
		        model.addAttribute("blog", blog);

			return "blog/admin-write";
		} else {
			return "redirect:/";
		}
	}

	@Auth 
	@RequestMapping(value="/admin/write", method=RequestMethod.POST) 
	public String adminWrite(PostVo postvo, @PathVariable("id") String id, HttpSession session) {

		// 폼에서 값을 가져와서 설정을 해주어야 한다 !! 
		// 어떻게 하면 할 수 있을까 
		UserVo user = (UserVo) session.getAttribute("authUser");
		if(id.equals(user.getId())) {

			postService.addPost(postvo);
			return "redirect:/" + id;

		} else {
			return "redirect:/";
		}

	}

	// 삭제하기 

	@Auth
	@RequestMapping(value="/admin/category/delete/{categoryId}", method=RequestMethod.GET)
	public String deleteCategory(@PathVariable("id") String id, @PathVariable("categoryId") Long categoryId, HttpSession session) {

		UserVo user = (UserVo) session.getAttribute("authUser");
		if(id.equals(user.getId())) {
			categoryService.deleteCategory(categoryId);
			return "redirect:/" + id + "/admin/category"; // redirect 경로에서 id를 사용
		} 
		else {
			return "redirect:/";
		}  
	}
}