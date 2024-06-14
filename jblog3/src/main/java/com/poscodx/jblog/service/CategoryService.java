package com.poscodx.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.poscodx.jblog.repository.CategoryRepository;
import com.poscodx.jblog.vo.CategoryVo;

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


	

	
}