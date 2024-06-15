package com.poscodx.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.poscodx.jblog.repository.CategoryRepository;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

 
    @Transactional
    public void insertCategory(String name, String description, String userId) {
        categoryRepository.insertCategory(name, description, userId);
    }
    
	public List<CategoryVo> getCategories(String id) {
		return categoryRepository.getAll(id);
	}
	
	// 카테고리 삭제하기 
  @Transactional
	public void deleteCategory(Long categoryId) {
		
		categoryRepository.deleteCategory(categoryId);
		
	}

public Long getFirstCategory(String id, String unregistered) {
 return 	categoryRepository.getFirstCategory(id,unregistered);
}
}