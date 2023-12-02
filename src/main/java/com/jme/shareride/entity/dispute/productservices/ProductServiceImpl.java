//package com.jme.shareride.entity.dispute.productservices;
//
//import com.jme.shareride.Others.ResponseHandler;
//import com.jme.shareride.external.google.service.DistanceCalculationService;
//import com.jme.shareride.external.jwt.JwtService;
//import com.jme.shareride.entity.transport.Category;
//import com.jme.shareride.entity.transport.Product;
//import com.jme.shareride.entity.user_and_auth.UserEntity;
//import com.jme.shareride.repository.*;
//import com.jme.shareride.entity.dispute.product.FindProductsByCategoryRequest;
//import com.jme.shareride.entity.dispute.product.ProductRequest;
//import com.jme.shareride.dto.ProductDto;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.AllArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@AllArgsConstructor
//public class ProductServiceImpl implements ProductService {
//
//    private final JwtService jwtservice;
//    private final ProductRepository productRepository;
//    private final CategoryRepository categoryRepository;
//    private final LocationRepository locationRepository;
//    private final UserRepository userRepository;
//    private final DistanceCalculationService distanceCalculationService;
//    private final DeliveryInfoRepository deliveryInfoRepository;
//    private final Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
//
//
//    @Override
//    public ResponseEntity addProduct(ProductRequest request) {
//        Product product = new Product();
//                String categoryName = request.getCategory().toLowerCase();
//                Category category = categoryRepository.findByCategoryName(categoryName);
//                product.setCategory(category);
//            product.setProductName(request.getProductName().toLowerCase());
//            product.setPhotoUrl(request.getPhotoUrl());
//            product.setFuelType(request.getFuelType());
//            product.setGearTypes(request.getGearTypes());
//            product.setProductSeatCount(request.getProductSeatCount());
//
//        return ResponseHandler.handle(201, "Your Product has been added",mapProductToDto(productRepository.save(product)));
//    }
//
//    @Override
//    public ResponseEntity<Object> findAllProducts(HttpServletRequest request,Pageable pageable) {
//        Page<Product> products = productRepository.findAll(pageable);
//        List<ProductDto> productDto = products.stream().map(product -> mapProductToDto(product)).collect(Collectors.toList());
//        String usersAddress = extractUser(request);
//        List<ProductDto> completed_productDto = setTheDistanceBetweenOfficeAndUserFromProductDto(productDto, usersAddress);
//
//        return ResponseHandler.handle(201, "Product retrieved successfully", productDto);
//    }
//
//    @Override
//    public ResponseEntity<?> findProductsByCategory(HttpServletRequest request, Pageable pageable, FindProductsByCategoryRequest categoryName) {
//            Category category = categoryRepository.findByCategoryName(categoryName.getCategoryName());
//        if(category != null){
//            Page<Product> products_under_category = productRepository.findByCategory(pageable,category);
//            List<ProductDto> productDto = products_under_category.stream().map(product -> mapProductToDto(product)).collect(Collectors.toList());
//               UserEntity user = extractUser(request);
//                List<ProductDto> completed_productDto = setTheDistanceBetweenOfficeAndUserFromProductDto(productDto, user);
//
//            return ResponseHandler.handle(200, productDto.size() + " " + categoryName.getCategoryName() + "s" + " found", completed_productDto);
//        }
//        return ResponseHandler.handle(404, "No" +categoryName.getCategoryName() + " found", null);
//    }
//
//
//    public static ProductDto mapProductToDto(Product product) {
//        ProductDto productDto = ProductDto.builder()
//                .id(product.getId())
//                .productName(product.getProductName())
//                .photoUrl(product.getPhotoUrl())
//                .category(product.getCategory().getCategoryName())
//                .productGearType(product.getGearTypes())
//                .productSeatCount(product.getProductSeatCount())
//                .fuelType(product.getFuelType())
//                .build();
//        return productDto;
//    }
//
//    @Override
//    public UserEntity extractUser(HttpServletRequest request){
//        String authHeader = request.getHeader("Authorization");
//        String jwt = authHeader.substring(7);
//        String userEmail = jwtservice.extractUsername(jwt);
//
//        UserEntity user = userRepository.findByEmail(userEmail);
//        if(user == null){
//            throw  new UsernameNotFoundException("No user must be logged in");
//        }
//
//        return  user;
//    }
//
//
//    @Override
//    public ProductDto setTheDistanceBetweenOfficeAndUserFromProductDto(ProductDto productDto,UserEntity user){
//
//
//
//
//        return productDto;
//    }
//
//}
//
//
//
//
//
