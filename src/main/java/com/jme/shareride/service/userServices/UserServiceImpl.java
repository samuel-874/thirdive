package com.jme.shareride.service.userServices;

import com.jme.shareride.Others.ResponseHandler;
import com.jme.shareride.entity.others.ImageData;
import com.jme.shareride.external.jwt.JwtService;
import com.jme.shareride.external.twillio.TwilioService;
import com.jme.shareride.mail.MailSenderService;
import com.jme.shareride.service.imageDataServices.ImageDataRepository;
import com.jme.shareride.entity.user_and_auth.Location;
import com.jme.shareride.entity.user_and_auth.OtpEntity;
import com.jme.shareride.entity.enums.Role;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import com.jme.shareride.service.locationservices.LocationRepository;
import com.jme.shareride.external.twillio.OtpRepository;
import com.jme.shareride.requests.auth.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;



@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private  JwtService jwtService;
    private  TwilioService twilioService;
    private final MailSenderService mailSender;
    private  PasswordEncoder passwordEncoder;
    private   UserRepository userRepository;
    private  LocationRepository locationRepository;
    private  OtpRepository otpRepository;
    private  AuthenticationManager authenticationManager;
    private Authentication authentication;
    private ImageDataRepository imageDataRepository;


    @Override
    public UserEntity findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public ResponseEntity<Object> getAndSendOtpToNumber(SignUpRequest request) {
        UserEntity existingUser = userRepository.findByEmailOrPhoneNumber(request.getEmail(),request.getEmail());
        UserEntity anotherExistingUser = userRepository.findByPhoneNumber(request.getPhoneNumber());
        if(existingUser != null || anotherExistingUser != null){
            return ResponseHandler.handle(403, "Email or Number has been registered already", null);
        }
            UserEntity user = new UserEntity();
            user.setEmail(request.getEmail());
            user.setUsername(request.getUsername());
            user.setPhoneNumber(request.getPhoneNumber());
            user.setGender(request.getGender());
            user.setRoles(Role.ROLE_USER);
            userRepository.save(user);
            twilioService.SendOtp(request.getPhoneNumber());
            return ResponseHandler.handle(200, "An otp has been sent to " + request.getPhoneNumber(),null);
    }

    @Override
    public ResponseEntity<Object> PasswordCheck(PasswordCheck passwordCheck, String mobile_num, String otp) {
        Boolean verified = twilioService.verifyOtp(mobile_num, otp);
        if(verified){
            UserEntity user = userRepository.findByPhoneNumber(mobile_num);
            user.setAccountEnabled(true);
            if(passwordCheck.getPassword().equals(passwordCheck.getConfirmPassword())){
                user.setPassword(passwordEncoder.encode(passwordCheck.getPassword()));
                userRepository.save(user);
                OtpEntity otpEntity = otpRepository.findByOwnersNumber(mobile_num);
                otpRepository.delete(otpEntity);
                return ResponseHandler.handle(202,"Password set Successfully",null);
            }
            else{
                return ResponseHandler.handle(400, "Passwords Don't match",null);
            }
        }
        return ResponseHandler.handle(409, "Invalid Otp!",null);
    }

    @Override
    public ResponseEntity becomeDriver(HttpServletRequest servletRequest) {
        UserEntity user = extractUser(servletRequest);

        user.setRoles(Role.ROLE_DRIVER);
        userRepository.save(user);
        return ResponseHandler.handle(200, "User has been Made A driver go futher to upload your vehicle details to get Started",null );
    }

    @Override
    public ResponseEntity<Object> completeRegistration(RegCompletion regCompletion) throws MessagingException, UnsupportedEncodingException {
        UserEntity user = userRepository.findByPhoneNumber(regCompletion.getPhoneNumber());

        if( user == null){
            return ResponseHandler.handle(403, "You are expected to use the number enter the number used earlier to get an Otp", null);
        }
        Boolean enabled = user.isAccountEnabled();
        if(enabled == true){
            user.setFullName(regCompletion.getFullName());
            //another entity
            Location location = new Location();
            location.setStreet(regCompletion.getStreet());
            location.setCity(regCompletion.getCity());
            location.setDistrict(regCompletion.getDistrict());

            //saving location
            locationRepository.save(location);

            ImageData pic = imageDataRepository.findByName(regCompletion.getImage()).get();
            user.setProfilePics(pic);

            //setting the users location
            user.setLocation(location);
            user.setDeviceToken(regCompletion.getDeviceToken());
            user.setDeviceId(regCompletion.getDeviceId());
            userRepository.save(user);

                mailSender.sendWelcomeMail(user.getEmail(), user.getUsername());

            return ResponseHandler.handle(201, "Account created successfully",findByPhoneNumber(user.getPhoneNumber()));
        }else
        return ResponseHandler.handle(451, "You account has not yet been enabled. Verify you phoneNumber first",null);
    }


    @Override
    public ResponseEntity<Object> logUserIn(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsernameOrPhoneNumber(),
                        request.getPassword()
                )
        );

        UserEntity user= userRepository.findByEmailOrPhoneNumber(request.getUsernameOrPhoneNumber(),request.getUsernameOrPhoneNumber());
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user.getEmail());
        return ResponseHandler.handle(200, "Login Successful", new AuthenticationResponse(jwtToken));
    }


    @Override
    public ResponseEntity<Object> resetPassword(PasswordResetRequest resetRequest){
        twilioService.SendOtp(resetRequest.getPhoneNumber());
        try {
            UserEntity user = userRepository.findByPhoneNumber(resetRequest.getPhoneNumber());
            if (user == null) {
                return ResponseHandler.handle(404, "Mobile number is no tied to an account", null);
            }
        }
        catch (NullPointerException e){
            return ResponseHandler.handle(404, "Mobile number is no tied to an account", null);

        }
        return ResponseHandler.handle(200, "An otp has been send to : " + resetRequest.getPhoneNumber(), null);
    }

    @Override
    public ResponseEntity deleteAccount(HttpServletRequest request) {
        UserEntity user =extractUser(request);
        userRepository.delete(user);
        return ResponseHandler.handle(200, "Account deleted", null);
    }
    @Override
    public ResponseEntity deleteAccount(long userId) {
        UserEntity user =userRepository.findById(userId).get();
        userRepository.delete(user);
        return ResponseHandler.handle(200, "Account deleted", null);
    }
    @Override
    public ResponseEntity deleteAccount(UserEntity user) {
        userRepository.delete(user);
        return ResponseHandler.handle(200, "Account deleted", null);
    }

    @Override
    public ResponseEntity updateLocation(HttpServletRequest servletRequest,LocationUpdateRequest locationUpdateRequest) {
        UserEntity user = extractUser(servletRequest);
        Location location = new Location();
        location.setStreet(locationUpdateRequest.getStreet());
        location.setDistrict(locationUpdateRequest.getDistrict());
        location.setCity(locationUpdateRequest.getCity());

        locationRepository.save(location);
        user.setLocation(location);
        userRepository.save(user);
        return ResponseHandler.handle(200, "Location updated successfully", null);
    }

    public UserEntity extractUser(HttpServletRequest servletRequest){
        String authHeader = servletRequest.getHeader("Authorization");
        if(authHeader == null){
            throw  new UsernameNotFoundException("User must be logged In");
        }

        String jwt = authHeader.substring(7);
        String username = jwtService.extractUsername(jwt);

        UserEntity  user = userRepository.findByEmailOrPhoneNumber(username,username);
        return user;
    }
}
