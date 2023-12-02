package com.jme.shareride.requests.contactus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactSupportRequest {
    private String name;
    private String email;
    private String mobileNumber;
    private String message;
}
