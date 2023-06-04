package com.vmware.ics.repository;

import com.vmware.ics.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> getImageById(Integer id);
    Optional<Image> getImageByUrl(String url);
}
