package com.vmware.ics.serviceTests;

import com.vmware.ics.controller.ImageTagController;
import com.vmware.ics.exception.ImageTaggingException;
import com.vmware.ics.service.ImageTaggingService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ImageTagControllerTest {

    @InjectMocks
    ImageTagController imageTagController;

    @Mock
    ImageTaggingService imageTaggingService;

    @Test
    void getTagsTest() throws Exception {
        String imageUrl = "http://example.com/image.jpg";
        String expectedTags = "tag1, tag2, tag3";
        when(imageTaggingService.getImageTags(imageUrl)).thenReturn(expectedTags);

        ResponseEntity<String> response = imageTagController.getTags(imageUrl);

        assertEquals(expectedTags, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getTagsTest_ImageTaggingException() throws Exception {
        String imageUrl = "http://example.com/image.jpg";
        when(imageTaggingService.getImageTags(imageUrl)).thenThrow(new ImageTaggingException("Error"));

        ResponseEntity<String> response = imageTagController.getTags(imageUrl);

        assertEquals("Error", response.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}