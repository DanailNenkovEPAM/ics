package com.vmware.ics.service;

import com.vmware.ics.model.Image;
import com.vmware.ics.model.ImageTagLink;
import com.vmware.ics.repository.ImageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class ImageService {

    private final ImageRepository imageRepo;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepo = imageRepository;
    }

    public List<Image> getAllImages() {
        return this.imageRepo.findAll();
    }

    public Image getImageByURLService(String URL) {
        Optional<Image> image = this.imageRepo.getImageByUrl(URL);
        if (image.isPresent()) {
            return image.get();
        }

        throw new NoSuchElementException("Image with this URL: " + URL + " was not found!");
    }

    public Image getImageByIdService(Long id) {
        Optional<Image> image = this.imageRepo.getImageById(id);
        if (image.isPresent()) {
            return image.get();
        }

        throw new NoSuchElementException("Image with this ID: " + id + " was not found!");
    }

    public List<Image> getImagesByIds(Set<Long> ids) {
        return this.imageRepo.findAllById(ids);
    }

    public Image addImage(Image image) {
        if (this.imageRepo.getImageByUrl(image.getUrl()).isEmpty()) {
            return this.imageRepo.saveAndFlush(image);
        }

        throw new IllegalArgumentException("Already exists!");
    }

    @Transactional
    public Image updateImage(Long id, Image newImage) {
        Optional<Image> existingImage = imageRepo.getImageById(id);

        if (existingImage.isPresent()) {
            Image currImage = existingImage.get();

            currImage.setUrl(newImage.getUrl());
            currImage.setService(newImage.getService());
            currImage.setAddedOn(newImage.getAddedOn());
            currImage.setHeight(newImage.getHeight());
            currImage.setWidth(newImage.getWidth());
            currImage.setImageTagLinks(newImage.getImageTagLinks());

            return this.imageRepo.saveAndFlush(currImage);
        }

        throw new NoSuchElementException("Image with this ID: " + id + " doesn't exist!");
    }

    public List<Image> saveAll(List<Image> images) {
        return this.imageRepo.saveAll(images);
    }

    public void deleteImageById(Long id) {
        this.imageRepo.deleteById(id);
    }

    public List<ImageTagLink> getImageTagLinksByImageId(Long id) {
        return this.getImageByIdService(id).getImageTagLinks();
    }

    public List<ImageTagLink> getImageTagLinksByImageURL(String URL) {
        return this.getImageByURLService(URL).getImageTagLinks();
    }

    public void addImageTagLinkToImage(ImageTagLink imageTagLink) {
        Image image;

        try {
            image = getImageByIdService(imageTagLink.getImage().getId());
            image.addImageTagLinks(imageTagLink);

            this.updateImage(image.getId(), image);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("No such image exists!");
        }
    }

}