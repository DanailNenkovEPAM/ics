package com.vmware.ics.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@JsonPropertyOrder({"id", "url", "addedOn", "tags", "width", "height"})
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "url", unique = true, nullable = false)
    private String url;

    @Column(name = "added_on")
    private Timestamp addedOn;

    @Column(name = "service")
    private String service;

    @Column(name = "width")
    private double width;

    @Column(name = "height")
    private double height;
    @OneToMany(mappedBy = "image", fetch = FetchType.EAGER)
    private Set<ImageTag> imageTags;

    public Image(Long id, String url, Timestamp addedOn, double width, double height) {
        this.id = id;
        this.url = url;
        this.addedOn = addedOn;
        this.service = "Imagga";
        this.width = width;
        this.height = height;
        this.imageTags = new HashSet<>();
    }

    public Image() {
        this.imageTags = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Timestamp getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(Timestamp addedOn) {
        this.addedOn = addedOn;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Set<ImageTag> getImageTags() {
        return imageTags;
    }

    public void setImageTags(Set<ImageTag> imageTags) {
        this.imageTags = imageTags;
    }

    public void addImageTags(ImageTag imageTag){
        this.imageTags.add(imageTag);
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", addedOn=" + addedOn +
                ", service='" + service + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}