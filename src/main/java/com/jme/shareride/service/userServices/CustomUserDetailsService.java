package com.jme.shareride.service.userServices;


import com.jme.shareride.entity.user_and_auth.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private  UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String usernameOrPhoneNumber) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmailOrPhoneNumber(usernameOrPhoneNumber, usernameOrPhoneNumber);
        if (user != null) {
            User authUser = new User(
                    user.getEmail(),
                    user.getPassword(),
                    Stream.of(user.getRoles())
                            .map(role -> new SimpleGrantedAuthority("ROLE_USER"))
                            .collect(Collectors.toList())
            );

            return authUser;
        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }
}
