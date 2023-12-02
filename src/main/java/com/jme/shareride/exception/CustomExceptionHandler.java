package com.jme.shareride.exception;

import com.jme.shareride.Others.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> validator(MethodArgumentNotValidException exception){
            Map<String,String> errorMap = new HashMap<>();
            exception.getBindingResult().getFieldErrors().forEach(errors ->{
                errorMap.put(errors.getField(), errors.getDefaultMessage());
            });

            return ResponseHandler.handle(400,"An Error Occurred",errorMap);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UnknownHostException.class)
    public ResponseEntity<Object> unKnownHostHandler(UnknownHostException exception){
        return ResponseHandler.handle(500,"Invalid Request or connection error",null);
    }



}