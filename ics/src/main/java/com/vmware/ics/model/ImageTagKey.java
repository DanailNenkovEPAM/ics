package com.vmware.ics.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ImageTagKey implements Serializable {
    @Column(name = "image_id")
    private Integer imageId;

    @Column(name = "tag_id")
    private Integer tagId;

    public ImageTagKey(Integer imageId, Integer tagId) {
        this.imageId = imageId;
        this.tagId = tagId;
    }

    public ImageTagKey() {

    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }
}
