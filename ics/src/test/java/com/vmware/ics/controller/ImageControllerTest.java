package com.vmware.ics.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vmware.ics.model.Image;
import com.vmware.ics.model.Tag;
import com.vmware.ics.service.ImageService;
import com.vmware.ics.service.ImageTagLinkService;
import com.vmware.ics.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ImageControllerTest {
    @InjectMocks
    ImageController imageController;

    @Mock
    ImageService imageService;

    @Mock
    TagService tagService;

    @Mock
    ImageTagLinkService imageTagLinkService;

    @BeforeEach
    void setUp() {
        imageService = mock(ImageService.class);
        tagService = mock(TagService.class);
        imageTagLinkService = mock(ImageTagLinkService.class);

        imageController = new ImageController(imageService, tagService, imageTagLinkService);
    }

    @Test
    void testGetImages() throws JsonProcessingException {
//        List<Tag> tags = new ArrayList<>();
//        tags.add(new Tag(1L, "cat"));
//        tags.add(new Tag(2L, "fluffy"));
//        tags.add(new Tag(3L, "fur"));
//
        Optional<List<Tag>> optionalTags = Optional.empty();

        List<Image> expectedImageList = new ArrayList<>();
        expectedImageList.add(new Image(1L, "https://cat.com", new Timestamp(1), 100, 200));

        when(imageService.getAllImages()).thenReturn(expectedImageList);

        ResponseEntity<List<Image>> response = imageController.getImages(optionalTags);

        assertEquals(response.getBody(), expectedImageList);
    }
}