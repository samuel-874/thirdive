package com.jme.shareride.requests.auth;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetRequest {
    @NotBlank(message = "Your mobile number  is Necessary!")
    @Length(message = "Invalid mobile number" , min = 14,max = 14)
    private String phoneNumber;


}
