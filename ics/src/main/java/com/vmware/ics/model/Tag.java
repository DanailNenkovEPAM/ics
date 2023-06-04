package com.vmware.ics.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

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
    private Set<ImageTag> imageTags;

    public Tag() {
        this.imageTags = new HashSet<>();
    }

    public Tag(String name) {
        this.name = name;
        this.imageTags = new HashSet<>();
    }

    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
        this.imageTags = new HashSet<>();
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
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
