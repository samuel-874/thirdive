//package com.jme.shareride.entity.dispute.productservices;
//
//import com.jme.shareride.dto.ProductDto;
//import com.jme.shareride.entity.user_and_auth.UserEntity;
//import com.jme.shareride.entity.dispute.product.FindProductsByCategoryRequest;
//import com.jme.shareride.entity.dispute.product.ProductRequest;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public interface ProductService {
//
//    ResponseEntity addProduct(ProductRequest request);
//
//    ResponseEntity<Object> findAllProducts(HttpServletRequest request,Pageable pageable);
//
//
//    ResponseEntity<?> findProductsByCategory(HttpServletRequest request, Pageable pageable, FindProductsByCategoryRequest categoryName);
//
//    UserEntity extractUser(HttpServletRequest request);
//    List<ProductDto> setTheDistanceBetweenOfficeAndUserFromProductDto(List<ProductDto> productDto, UserEntity usersAddress);
//
//    ProductDto setTheDistanceBetweenOfficeAndUserFromProductDto(ProductDto productDto,UserEntity usersAddress);
//}
