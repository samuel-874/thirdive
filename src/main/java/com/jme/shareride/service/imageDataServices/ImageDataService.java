package com.jme.shareride.service.imageDataServices;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface ImageDataService {

    ResponseEntity<Object> uploadImage(MultipartFile multipartFile) throws IOException;
    public byte[] viewImage(String fileName) throws IOException;
}
