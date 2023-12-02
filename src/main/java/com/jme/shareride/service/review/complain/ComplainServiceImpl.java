package com.jme.shareride.service.review.complain;

import com.jme.shareride.Others.ResponseHandler;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import com.jme.shareride.entity.user_and_auth.review.Complain;
import com.jme.shareride.external.jwt.JwtService;
import com.jme.shareride.requests.ComplainRequest;
import com.jme.shareride.service.userServices.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ComplainServiceImpl implements ComplainService{
    private final ComplainRepository complainRepository;
    private final  JwtService jwtService;
    private final UserRepository userRepository;

    @Autowired
    public ComplainServiceImpl(
            ComplainRepository complainRepository,
             UserRepository userRepository,
            JwtService jwtService
    ) {
        this.complainRepository = complainRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity fileAComplain(
            HttpServletRequest servletRequest,
            ComplainRequest complainRequest
    ) {
        Complain complain = new Complain();
        UserEntity user = extractUser(servletRequest);
        complain.setUser(user);
        complain.setContent(complainRequest.getContent());
        complain.setSubject(complainRequest.getSubject());
        long driversId = complainRequest.getDriversId();
        UserEntity driver = userRepository.findById(driversId).get();
        complain.setDriver(driver);
        complainRepository.save(complain);

        return ResponseHandler.handle(
                200,
                "You complain has been filed against " + driver.getUsername(),
                null );
    }

    public UserEntity extractUser(
            HttpServletRequest servletRequest
    ) {
        String authHeader =
                servletRequest.getHeader("Authorization");
        if (authHeader == null) {
            throw new UsernameNotFoundException("User must be logged In");
        }
        String jwt = authHeader.substring(7);

        String username = jwtService.extractUsername(jwt);


        UserEntity user = userRepository.
                findByEmailOrPhoneNumber(username, username);
        return user;
    }
}
