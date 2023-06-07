package com.vmware.ics.service;

import com.vmware.ics.model.Image;
import com.vmware.ics.repository.ImageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {

    @InjectMocks
    private ImageService imageService;

    @Mock
    private ImageRepository imageRepo;

    @Test
    public void testGetAllImages() {
        when(imageRepo.findAll()).thenReturn(new ArrayList<>());
        imageService.getAllImages();
        verify(imageRepo).findAll();
    }

    @Test
    public void testGetImageByURLService() {
        String url = "https://example.com/image.jpg";
        Image image = new Image();
        image.setUrl(url);

        when(imageRepo.getImageByUrl(url)).thenReturn(Optional.of(image));
        Image returnedImage = imageService.getImageByURLService(url);

        verify(imageRepo).getImageByUrl(url);
        assertEquals(url, returnedImage.getUrl());
    }

    @Test
    public void testAddImage() {
        Image image = new Image();
        when(imageRepo.getImageByUrl(image.getUrl())).thenReturn(Optional.empty());
        when(imageRepo.saveAndFlush(any(Image.class))).thenReturn(image);
        imageService.addImage(image);
        verify(imageRepo).saveAndFlush(any(Image.class));
    }

    // Add more tests as needed...
}
