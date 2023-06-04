package com.vmware.ics.service;

import com.vmware.ics.model.ImageTagLink;
import com.vmware.ics.model.Tag;
import com.vmware.ics.repository.TagRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TagService {

    private final TagRepository tagRepo;

    @Autowired
    public TagService(TagRepository tagRepo) {
        this.tagRepo = tagRepo;
    }

    public List<Tag> getAllTags() {
        return this.tagRepo.findAll();
    }

    public Tag getTagByNameService(String name) {
        Optional<Tag> tag = this.tagRepo.getTagByName(name);
        if (tag.isPresent()) {
            return tag.get();
        }

        throw new NoSuchElementException("Image with this name: " + name + "was not found!");
    }

    public Tag getTagByIdService(long id) {
        Optional<Tag> tag = this.tagRepo.getTagById(id);
        if (tag.isPresent()) {
            return tag.get();
        }

        throw new NoSuchElementException("Tag with this ID: " + id + "was not found!");
    }

    public List<Tag> getTagsByIds(Set<Long> ids) {
        return this.tagRepo.findAllById(ids);
    }

    public Tag addTag(Tag tag) {
        if (this.tagRepo.getTagByName(tag.getName()).isEmpty()) {
            return this.tagRepo.saveAndFlush(tag);
        }

        return this.tagRepo.getTagByName(tag.getName()).get();
    }

    @Transactional
    public Tag updateTag(long id, Tag tag) {
        Optional<Tag> myTag = tagRepo.getTagById(id);
        if (myTag.isPresent()) {
            Tag dbTag = myTag.get();
            dbTag.setName(tag.getName());
            dbTag.setImageTagLinks(tag.getImageTagLinks());
            return tagRepo.saveAndFlush(dbTag);
        }

        throw new NoSuchElementException("Tag with this id: " + id + " doesn't exist!");
    }

    public List<Tag> saveAll(List<Tag> tags) {
        return this.tagRepo.saveAll(tags);
    }

    public void deleteTag(Long id) {
        this.tagRepo.deleteById(id);
    }

    public List<ImageTagLink> getImageTagLinksByTagId(Long id) {
        return this.getTagByIdService(id).getImageTagLinks();
    }

    public List<ImageTagLink> getImageTagLinksByTagName(String name) {
        return this.getTagByNameService(name).getImageTagLinks();
    }

    public void addImageTagLinkToTag(ImageTagLink imageTagLink) {
        Tag tag;

        try {
            tag = getTagByIdService(imageTagLink.getTag().getId());
            tag.addImageTagLinks(imageTagLink);

            this.updateTag(tag.getId(), tag);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("No such name exists!");
        }
    }
}