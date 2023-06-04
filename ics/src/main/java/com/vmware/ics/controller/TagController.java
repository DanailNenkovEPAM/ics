package com.vmware.ics.controller;

import com.vmware.ics.dto.TagDTO;
import com.vmware.ics.model.*;
import com.vmware.ics.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("tags")
@CrossOrigin
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<Tag> getAllTagsController() {
        return tagService.getAllTags();
    }

    @GetMapping("{name}")
    public List<Image> getImagesByTagNameController(@PathVariable String name) {
        try {
            List<ImageTagLink> links = tagService.getImageTagLinksByTagName(name);
            List<Image> imagesToReturn = new ArrayList<>();
            for (ImageTagLink currLink: links) {
                imagesToReturn.add(currLink.getImage());
            }

            return imagesToReturn;
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException(String.format("Tag with name %d not found!", name));
        }
    }

    @PostMapping
    public ResponseEntity<Void> createTag(@RequestBody final TagDTO tagDTO) {
        if (!StringUtils.hasLength(tagDTO.name)) {
            throw new IllegalArgumentException("Tag name must be specified!");
        }

        final Tag tagToCreate = new Tag();
        //System.out.println(tagDTO.name);
        tagToCreate.setName(tagDTO.name);

        tagService.addTag(tagToCreate);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTagById(@PathVariable final Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Tag ID must be specified.");
        }
        try {
            tagService.deleteTag(id);
        } catch (final EmptyResultDataAccessException e) {
            throw new NoSuchElementException(
                    String.format("No name with this ID %d found!", id));
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> edit(@PathVariable final Long id,
                                     @RequestBody final TagDTO tagDTO) {
        if (!StringUtils.hasLength(tagDTO.name)) {
            throw new IllegalArgumentException("Tag title must be specified!");
        }
        if (id == null) {
            throw new IllegalArgumentException("Tag ID must be specified!");
        }

        final Tag tagToUpdate = new Tag();
        tagToUpdate.setName(tagDTO.name);
        tagService.updateTag(id, tagToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
