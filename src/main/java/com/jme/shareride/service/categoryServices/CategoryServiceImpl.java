package com.jme.shareride.service.categoryServices;

import com.jme.shareride.Others.ResponseHandler;
import com.jme.shareride.dto.CategoryDto;
import com.jme.shareride.entity.enums.Role;
import com.jme.shareride.entity.others.ImageData;
import com.jme.shareride.entity.transport.Category;
//import com.jme.shareride.service.imageDataServices.ImageDataRepository;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import com.jme.shareride.external.jwt.JwtService;
import com.jme.shareride.service.imageDataServices.ImageDataRepository;
import com.jme.shareride.requests.transport.category.CategoryRequest;
import com.jme.shareride.service.userServices.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ImageDataRepository imageDataRepository;


    @Override
    public Category findByName(
            String name
    ) {
        return
                categoryRepository.findByCategoryName(name);
    }

    @Override
    public ResponseEntity addCategory(
            HttpServletRequest httpServletRequest,
            CategoryRequest categoryRequest
    ) {

        UserEntity user = extractUser(httpServletRequest);
        if(user.getRoles() != Role.ROLE_ADMIN){
            return ResponseHandler.handle(401, "Only admins can access this endpoint", null);
        }

        Category category =
                mapCategoryRequestToCategory(categoryRequest);
        Category savedCategory = categoryRepository.save(category);
        CategoryDto categoryDto =mapCategoryObjectToDto(savedCategory);
        return ResponseHandler.handle(
                201,
                "Category Added",
                categoryDto);
    }


    public Category mapCategoryRequestToCategory(
            CategoryRequest request
    ){
        ImageData catPhoto =
                imageDataRepository.findByName(request.getImage()).get();

        Category category = Category.builder()
                .categoryName(request.getCategoryName())
                .image(catPhoto)
                .build();
        return category;
    }

    public  CategoryDto mapCategoryObjectToDto(
            Category category
    ){
        CategoryDto categoryDto =
                CategoryDto.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .image(category.getImage())
                .build();
        return categoryDto;
    }


    public UserEntity extractUser(
            HttpServletRequest servletRequest
    ) {
        String authHeader = servletRequest.getHeader("Authorization");
        if (authHeader == null) {
            throw new UsernameNotFoundException("User must be logged In");
        }

        String jwt = authHeader.substring(7);
        String username = jwtService.extractUsername(jwt);

        UserEntity user = userRepository.findByEmailOrPhoneNumber(username, username);
        return user;
    }
}
