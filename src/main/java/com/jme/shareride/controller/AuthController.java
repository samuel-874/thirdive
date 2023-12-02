package com.jme.shareride.controller;

import com.jme.shareride.Others.ResponseHandler;
import com.jme.shareride.dto.OrderDto;
import com.jme.shareride.entity.transport.Order;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import com.jme.shareride.service.OrderServices.OrderRepository;
import com.jme.shareride.service.userServices.UserRepository;
import com.jme.shareride.requests.auth.*;
import com.jme.shareride.service.imageDataServices.ImageDataService;
import com.jme.shareride.service.userServices.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static com.jme.shareride.service.OrderServices.OrderServiceImpl.mapOrderToDto;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class AuthController {

     private final UserService userService;
     private final ImageDataService imageDataService;
     private final UserRepository userRepository;
     private final OrderRepository orderRepository;



    @PostMapping("/signup")
    public ResponseEntity<Object> getAndSendOtpToNumber(
            @RequestBody @Valid SignUpRequest request
    ){
        return userService.getAndSendOtpToNumber(request);
    }


    @PostMapping("/verify")
    public  ResponseEntity<Object> PasswordCheck(
            @RequestBody
            @Valid PasswordCheck passwordCheck,
             @RequestParam("num") String mobile_num,
              @RequestParam String otp)
    {
        return userService.PasswordCheck(passwordCheck, mobile_num, otp);
    }
    @PostMapping("/register")
    public ResponseEntity<Object> complete_registration(
            @RequestBody @Valid RegCompletion regCompletion
    ) throws MessagingException,
            UnsupportedEncodingException {
        return userService.completeRegistration(regCompletion);
    }

    @PostMapping("/signIn")
    public ResponseEntity<Object> login(
            @RequestBody @Valid AuthenticationRequest
                    authenticationRequest
    )    {
        return userService.logUserIn(authenticationRequest);
    }

    @PostMapping("/upload")
    public ResponseEntity uploadPhoto(
            @RequestParam("image") MultipartFile multipartFile
    ) throws IOException {
        return imageDataService.uploadImage(multipartFile);
    }
    @GetMapping("/{fileName}")
    public ResponseEntity viewImage(
            @PathVariable String fileName
    ) throws IOException {
        var body = imageDataService.viewImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/jpg"))
                .body(body);
    }

    @PostMapping("/password-reset")
    public ResponseEntity<Object> PasswordReset(
            @RequestBody @Valid PasswordResetRequest resetRequest
    ){
        return userService.resetPassword(resetRequest);
    }


    @GetMapping("/order")
    public OrderDto getOrder(
            @RequestParam String username
    ){
        UserEntity user = userRepository.findByUsername(username);
        Order byCustomer = orderRepository.findByCustomer(user);
        return mapOrderToDto(byCustomer);
    }

    @GetMapping("/hello")
    public ResponseEntity hello(){
        return ResponseHandler.handle(
                200, "This is to confirm that you project startup was successful",
                null);
    }

}
