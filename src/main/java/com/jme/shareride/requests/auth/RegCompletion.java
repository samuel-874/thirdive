package com.jme.shareride.requests.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class RegCompletion {
    @NotBlank(message = "Full name must Not me null")
    private String fullName;
    @NotBlank(message = "Your Street is Necessary!")
    private String Street;
    @NotBlank(message = "Your mobile number  is Necessary!")
    @Length(message = "Invalid mobile number" , min = 14,max = 14)
    private String phoneNumber;
    @NotBlank(message = "Your district is Necessary!")
    private String district;
    @NotBlank(message = "Your City is Necessary!")
    private String city;
    private String image;
    private String deviceId;
    private String deviceToken;
}
