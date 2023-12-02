package com.jme.shareride.requests.auth;

import jakarta.validation.constraints.NotBlank;


public class AuthenticationRequest {

    @NotBlank(message = "Either username or password must be provided")
    private String usernameOrPhoneNumber;
    @NotBlank(message = "Password is required")
    private String password;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String usernameOrPhoneNumber, String password) {
        this.usernameOrPhoneNumber = usernameOrPhoneNumber;
        this.password = password;
    }

    public String getUsernameOrPhoneNumber() {
        return usernameOrPhoneNumber;
    }

    public void setUsernameOrPhoneNumber(String usernameOrPhoneNumber) {
        this.usernameOrPhoneNumber = usernameOrPhoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
