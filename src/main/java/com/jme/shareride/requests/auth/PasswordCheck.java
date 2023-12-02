package com.jme.shareride.requests.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PasswordCheck {

    @Length(min = 6,max = 50 ,message = "Password must not be less than 6")
    private String password;
    @Length(min = 6,max = 50 ,message = "Password must not be less than 6")
    private String confirmPassword;
}
