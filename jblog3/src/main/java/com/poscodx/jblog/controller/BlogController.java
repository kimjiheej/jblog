package com.poscodx.jblog.controller;

import java.util.List;
import java.util.Optional;

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
  
    
     
    
    @RequestMapping({"", "/{categoryNo}", "/{categoryNo}/{postNo}" })
    public String index(
        @PathVariable("id") String id,
        @PathVariable Optional<Long> categoryNo,
        @PathVariable Optional<Long> postNo, 
        HttpSession session, Model model
    ) {
      
    	   // 세션에서 유저 정보를 가져와서 블로그 정보를 조회
  	  UserVo user = (UserVo) session.getAttribute("authUser");
        String userId = user.getId();
        BlogVo blog = blogService.getBlog(userId);
        model.addAttribute("blog", blog);
        
        // 무조건 있어야 한다 
        List<CategoryVo> list = categoryService.getCategories(id);
        model.addAttribute("list", list);
        
        if (categoryNo.isPresent() && postNo.isPresent()) {
           
        	// 둘다 있으면 해당 postno 에 해당하는 글을 그냥 주기 
        	
        	// 화면에 보이는 것 설정하기 
           
           PostVo post = postService.getPost(postNo);
           List<PostVo> postList = postService.getAllPosts(categoryNo);
           
           model.addAttribute("categoryNo", categoryNo);
           model.addAttribute("firstPage", post);
           model.addAttribute("postList", postList);
           

        } else if (categoryNo.isPresent()) {
            
        	// 그냥 해당 카테고리 번호에 해당하는 모든 글을 다 가져오기 
       
        	
        	 List<PostVo> postList = postService.getAllPosts(categoryNo);
             PostVo post = postService.getSmallPost(categoryNo);
        	 
             model.addAttribute("categoryNo", categoryNo);
             model.addAttribute("firstPage", post);
             model.addAttribute("postList", postList);
             
            
            // 해당 카테고리에 해당하는 모든 글을 불러준다 
           
        } else {
             // 그냥 처음에 들어오게 된다면 !
        	 // 해당 사람의 카테고리 no 중 미등록을 불러온다 (미등록) 
        	//  그리고 그 no 를 가져와서 모든 글을 다 불러온다. 
        	
        	// 이제 해당하는 미등록 number 를 가져왔고 
            Long no = categoryService.getFirstCategory(id, "미등록");
            
            // 미등록 category 에 해당하는 가장 첫번째 글 ! 
            
            
            // 그리고 그 number 를 가져와서 모든 글을 다 불러와준다 
            
            
            List<PostVo> posts = postService.getAllPosts(no);
            PostVo post = postService.getSmallPost(no);
            model.addAttribute("categoryNo", no);
            model.addAttribute("firstPage", post);
        	model.addAttribute("postList", posts);
        }
        return "blog/main";
    }
        
    
    /**
     * 블로그 기본 설정 페이지 접근
     */
    @Auth
    @RequestMapping(value="/admin/basic", method=RequestMethod.GET)
    public String adminBasic(@PathVariable("id") String id, Model model) {
    	
    	BlogVo vo = blogService.getBlog(id);
    	System.out.println("정답이 뭐냐" + vo);
    	model.addAttribute("updatedvo", vo);
        return "blog/admin-basic";
    }

    // update 를 하려면 사용자의 아이디도 xml 파일의 조건에 같이 들어가야 한다 
/*
    @RequestMapping(value="/admin/basic/update", method=RequestMethod.POST)
	public String update(Model model, BlogVo vo, @PathVariable("id") String id,  @RequestParam("logo-file") MultipartFile file) {
		String logo = fileUploadService.restore(file);
		
		System.out.println("사용자 아이디 !! " + id);
		
	    System.out.println("뭐냐" + id  +" " +vo.getTitle()+" "+ logo);
		    blogService.updateBlog(id,vo.getTitle(),logo);
		
		model.addAttribute("updatedvo", vo);
		servletContext.setAttribute("updatedvo", vo);	
	    return "redirect:/" + id + "/admin/basic";
	}
	*/
    
    
       @RequestMapping(value="/admin/basic/update", method=RequestMethod.POST)
    public String update(Model model,BlogVo vo, @PathVariable("id") String id, @RequestParam("logo-file") MultipartFile file) {
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

        model.addAttribute("updatedvo", vo);
        servletContext.setAttribute("updatedvo", vo);
        return "redirect:/" + id + "/admin/basic";
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
        
        return "redirect:/" + id + "/admin/category"; // redirect 경로에서 id를 사용
    }
    
    
    
}
