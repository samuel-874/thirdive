package com.jme.shareride.Others;


import org.apache.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<Object> handle(int status_code, String message, Object responseBody){

        boolean hasErrors = status_code >= HttpStatus.SC_BAD_REQUEST;

        Map<String,Object> response = new LinkedHashMap<>();
        response.put("error", hasErrors);
        response.put("status_code", status_code);
        response.put("message", message);
        response.put("data", responseBody);

        return ResponseEntity.status(HttpStatusCode.valueOf(status_code)).body(response);
    }
}
