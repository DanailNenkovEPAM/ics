package com.vmware.ics.controller;

import com.vmware.ics.model.*;

import com.vmware.ics.service.ImageService;
import com.vmware.ics.service.ImageTagLinkService;
import com.vmware.ics.service.ImageTaggingService;
import com.vmware.ics.service.TagService;
import jakarta.transaction.Transactional;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("images")
public class ImageController {
    private static final int MIN_CONFIDENCE = 40;
    private final ImageService imageService;
    private final TagService tagService;
    private final ImageTagLinkService imageTagLinkService;

    @Autowired
    public ImageController(ImageService imageService, TagService tagService, ImageTagLinkService imageTagLinkService) {
        this.imageService = imageService;
        this.tagService = tagService;
        this.imageTagLinkService = imageTagLinkService;
    }

    @GetMapping
    public List<Image> getImagesController(@RequestParam(required = false) Optional<List<Tag>> tags) {
        if (tags.isPresent()) {
            List<Tag> availableTags = tags.get();
            List<Image> imagesToReturn = new ArrayList<>();

            for (Tag currTag: availableTags) {
                List<ImageTagLink> currlinks = currTag.getImageTagLinks();

                for (ImageTagLink currLink: currlinks) {
                    imagesToReturn.add(currLink.getImage());
                }
            }

            return imagesToReturn;
        }

        return imageService.getAllImages();
    }

    @GetMapping("{id}")
    public Image getImageByIdController(@PathVariable Long id) {
        try {
            return imageService.getImageByIdService(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Image with this ID: %d not found!", id));
        }
    }

    @GetMapping("url")
    @ResponseBody
    public Image getImageByURLController(@RequestParam String url) {
        try {
            System.out.println(url);
            return imageService.getImageByURLService(url);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Image with this URL: %d not found", url));
        }
    }

    @GetMapping("id/{id}/tags")
    public Map<String, Double> getTags(@PathVariable final Long id) {
        List<ImageTagLink> links = this.imageService.getImageTagLinksByImageId(id);
        Map<String, Double> pairs = new HashMap<>();

        for (ImageTagLink connection: links) {
            Tag tag = this.tagService.getTagByIdService(connection.getTag().getId());
            pairs.put(tag.getName(), connection.getConfidence());
        }

        return pairs;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody final String imageURL) throws Exception {
        //Check if URL has Text
        if (!StringUtils.hasText(imageURL)) {
            throw new IllegalArgumentException("Image URL must be specified!");
        }


        //System.out.println(imageURL);

        //Creating fields to input into Image
        Long datetime = System.currentTimeMillis();
        URL url = new URL(imageURL);
        java.awt.Image realImage = new ImageIcon(url).getImage();
        Image newImageToAdd;

        //Trying to add the new image to the database
        try {
            newImageToAdd = new Image(imageURL, new Timestamp(datetime), "Imagga", realImage.getWidth(null),
                    realImage.getHeight(null));

            this.imageService.addImage(newImageToAdd);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        //Getting tags with confidence rate
        String jsonResponse = ImageTaggingService.getImageTags(imageURL);

        //System.out.println(jsonResponse);

        //Creating JSON objects and arrays
        JSONObject obj = new JSONObject(jsonResponse);
        JSONObject result = obj.getJSONObject("result");
        JSONArray arr = result.getJSONArray("tags");

        for (int i = 0; i < arr.length(); i++) {
            JSONObject conf = arr.getJSONObject(i);
            double confidence = conf.getDouble("confidence");

            //Exclude tags with confidence rate lower than MIN_CONFIDENCE
            if (confidence < MIN_CONFIDENCE) {
                break;
            }


            JSONObject tag = conf.getJSONObject("tag");
            String name = tag.getString("en");

            Tag myTag = tagService.addTag(new Tag(name));

            imageTagLinkService.addImageTagLinkConnection(new ImageTagLink(newImageToAdd.getId(), myTag.getId(), newImageToAdd, myTag, confidence));
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
