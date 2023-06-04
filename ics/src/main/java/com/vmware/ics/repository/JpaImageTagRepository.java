package com.vmware.ics.repository;

import com.vmware.ics.model.Image;
import com.vmware.ics.model.ImageTag;
import com.vmware.ics.model.ImageTagKey;
import com.vmware.ics.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface JpaImageTagRepository extends JpaRepository<ImageTag, ImageTagKey> {
    Optional<Set<Tag>> getConnectionByImageId(Long image_id);
    Optional<Set<Image>> getConnectionByTagId(Long tag_id);
    Optional<ImageTag> getConnectionById(Long id);
    //Optional<ImageTag> getConnectionByImageTagKey(ImageTagKey connectionKey);
}
