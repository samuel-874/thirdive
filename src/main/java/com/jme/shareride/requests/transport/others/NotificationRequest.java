package com.jme.shareride.requests.transport.others;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NotificationRequest {
    private String message;
    private String username;
    private String title;

}
