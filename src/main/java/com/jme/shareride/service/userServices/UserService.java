package com.jme.shareride.service.userServices;

import com.jme.shareride.entity.user_and_auth.Location;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import com.jme.shareride.requests.auth.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public interface UserService{

    Object findByPhoneNumber(String phoneNumber);

    ResponseEntity<Object> getAndSendOtpToNumber(SignUpRequest request);

    ResponseEntity<Object> PasswordCheck( PasswordCheck passwordCheck,String mobile_num,String otp);
    ResponseEntity becomeDriver(HttpServletRequest servletRequest);

    ResponseEntity<Object> completeRegistration(RegCompletion regCompletion) throws MessagingException, UnsupportedEncodingException;

    ResponseEntity<Object> logUserIn(AuthenticationRequest authenticationRequest);

    ResponseEntity<Object> resetPassword(PasswordResetRequest resetRequest);

    ResponseEntity deleteAccount(HttpServletRequest request);
    ResponseEntity deleteAccount(long userId);
    ResponseEntity deleteAccount(UserEntity user);
    ResponseEntity updateLocation(HttpServletRequest servletRequest,LocationUpdateRequest locationUpdateRequest);
}