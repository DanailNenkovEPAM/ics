package com.vmware.ics.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;


    @OneToMany(mappedBy = "tag", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<ImageTag> imageTags;

    public Tag() {
        this.imageTags = new ArrayList<>();
    }

    public Tag(String name) {
        this.name = name;
        this.imageTags = new ArrayList<>();
    }

    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
        this.imageTags = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ImageTag> getImageTags() {
        return imageTags;
    }

    public void setImageTags(List<ImageTag> imageTags) {
        this.imageTags = imageTags;
    }

    public void addImageTags(ImageTag imageTag){
        this.imageTags.add(imageTag);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
