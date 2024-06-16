package com.poscodx.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.poscodx.jblog.repository.CategoryRepository;
import com.poscodx.jblog.repository.PostRepository;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private PostRepository postRepository;

 
    @Transactional
    public void insertCategory(String name, String description, String userId) {
        categoryRepository.insertCategory(name, description, userId);
    }
    

	   public List<CategoryVo> getCategories(String id) {
	        List<CategoryVo> categories = categoryRepository.getAll(id);

	        for (CategoryVo category : categories) {
	            Long postCount = postRepository.countPostsByCategory(category.getNo());
	            category.setPostCount(postCount);
	        }
	        return categories;
	    }
	
	// 카테고리 삭제하기 
  @Transactional
	public void deleteCategory(Long categoryId) {
	  
	  postRepository.deletePosts(categoryId);
		categoryRepository.deleteCategory(categoryId);
		
		
	}

public Long getFirstCategory(String id) {
 return 	categoryRepository.getFirstCategory(id);
}
}