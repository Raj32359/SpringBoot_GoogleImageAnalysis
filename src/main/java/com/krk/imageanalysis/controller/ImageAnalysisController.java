package com.krk.imageanalysis.controller;
/*
 *  Rajkumar Kalavacherla
 *  15-06-2024
 */

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.vision.v1.ImageAnnotatorSettings;
import com.krk.imageanalysis.configuration.StaticConfiguration;
import com.krk.imageanalysis.dto.ImageResponse;
import com.krk.imageanalysis.service.ImageAnalysisService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/google/api/v1")
public class ImageAnalysisController {

    private final ImageAnalysisService imageAnalysisService;

    public ImageAnalysisController(ImageAnalysisService imageAnalysisService) {

        this.imageAnalysisService = imageAnalysisService;
    }


    @GetMapping("/analysimage")
    public ResponseEntity<?> getImageAnalysis(@RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        String currentPath = System.getProperty("user.dir");
        try {
            if (imageFile.isEmpty()) {
                return new ResponseEntity<>("Please provide an image file", HttpStatus.BAD_REQUEST);
            }
            String filePath = currentPath.concat(StaticConfiguration.GOOGLE_IMAGE_STORAGE_VALUT.concat(imageFile.getOriginalFilename()));
            String finalPath = currentPath.concat(StaticConfiguration.GOOGLE_IMAGE_STORAGE_VALUT);
            File file = new File(finalPath.concat(imageFile.getOriginalFilename()));
            imageFile.transferTo(file);


            GoogleCredentials credentials = null;
            File credentialsPath = new File(StaticConfiguration.GOOGLE_CREDENTIALS_VALUT);
            FileInputStream serviceAccountStream = new FileInputStream(credentialsPath);
            credentials = ServiceAccountCredentials.fromStream(serviceAccountStream);
            ImageAnnotatorSettings imageAnnotatorSettings = ImageAnnotatorSettings.newBuilder().setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();
            imageAnalysisService.detectLocalizedObjects(filePath, imageAnnotatorSettings);


        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Successfully Detected Image Labels", HttpStatus.OK);
    }

}
