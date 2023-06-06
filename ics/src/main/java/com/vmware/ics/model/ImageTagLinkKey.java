package com.vmware.ics.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ImageTagLinkKey implements Serializable {
    @Column(name = "image_id")
    private Long imageId;

    @Column(name = "tag_id")
    private Long tagId;

    public ImageTagLinkKey(Long imageId, Long tagId) {
        this.imageId = imageId;
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageTagLinkKey that = (ImageTagLinkKey) o;
        return Objects.equals(imageId, that.imageId) && Objects.equals(tagId, that.tagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageId, tagId);
    }

    public ImageTagLinkKey() {

    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }
}