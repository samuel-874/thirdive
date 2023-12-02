package com.jme.shareride.service.categoryServices;

import com.jme.shareride.entity.transport.Category;
import com.jme.shareride.requests.transport.category.CategoryRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    Category findByName(String name);
    ResponseEntity addCategory(HttpServletRequest httpServletRequest,CategoryRequest categoryRequest);
}
