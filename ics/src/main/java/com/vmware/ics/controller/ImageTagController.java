package com.vmware.ics.controller;

import com.vmware.ics.exception.ImageTaggingException;
import com.vmware.ics.service.ImageTaggingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
public class ImageTagController {

    private final ImageTaggingService imageTaggingService;

    @Autowired
    public ImageTagController(ImageTaggingService imageTaggingService) {
        this.imageTaggingService = imageTaggingService;
    }

    @GetMapping("/images")
    public ResponseEntity<String> getTags(@RequestParam String imageUrl) throws Exception {
        String tags = imageTaggingService.getImageTags(imageUrl);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @ExceptionHandler(ImageTaggingException.class)
    public ResponseEntity<String> handleImageTaggingException(ImageTaggingException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @PostMapping("/images")
//    public ResponseEntity<String> postImage(@RequestBody String imageUrl) throws Exception {
//        String tags = imageTaggingService.getImageTags(imageUrl);
//        return new ResponseEntity<>(tags, HttpStatus.OK);
//    }

}