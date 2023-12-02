package com.jme.shareride.requests.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SignUpRequest {
        @NotBlank(message = "Your username is Required!")
        private String username;
        @Email(message = "Invalid email")
        private String email;
        @NotBlank(message = "Your mobile number  is Required!")
        private String phoneNumber;
        @NotBlank(message = "Your gender is Required!")
        private String gender;
}
