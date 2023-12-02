package com.jme.shareride.service.imageDataServices;

import com.jme.shareride.Others.ResponseHandler;
import com.jme.shareride.entity.others.ImageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageDataService{


    @Autowired
    private ImageDataRepository imageDataRepository;
    private String basePath ="C:/Users/test/Desktop/share-ride/share-ride/src/main/resources/static/images/";
    @Override
    public ResponseEntity<Object> uploadImage(MultipartFile multipartFile) throws IOException {
        var filePath  = basePath+ multipartFile.getOriginalFilename();
        ImageData imageData= ImageData.builder()
                .name(multipartFile.getOriginalFilename())
                .type(multipartFile.getContentType())
                .filePath(filePath)
                .build();
        imageDataRepository.save(imageData);
        multipartFile.transferTo(new File(filePath).toPath());
        return ResponseHandler.handle(201, "Your Image has been Uploaded :-", filePath);
    }

    public byte[] viewImage(String fileName) throws IOException {
        Optional<ImageData> imageData = imageDataRepository.findByName(fileName);

        String imagePath = imageData.get().getFilePath();

        byte[] image = Files.readAllBytes((new File(imagePath).toPath()));
        return image;
    }
}
