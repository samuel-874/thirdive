package com.jme.shareride.external.twillio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "twilio")
public class TwilioConfig {
    private String authToken= System.getenv("twilio.account.auth_token");
    private String accountSid=System.getenv("twilio.account.sid");
    private String phoneNumber=System.getenv("twilio.account.trial_number");
}
