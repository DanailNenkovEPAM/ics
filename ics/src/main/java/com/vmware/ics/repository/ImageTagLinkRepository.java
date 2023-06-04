package com.vmware.ics.repository;

import com.vmware.ics.model.Image;
import com.vmware.ics.model.ImageTagLink;
import com.vmware.ics.model.ImageTagLinkKey;
import com.vmware.ics.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface ImageTagLinkRepository extends JpaRepository<ImageTagLink, ImageTagLinkKey> {
    Optional<Set<Tag>> getConnectionByImageId(Long image_id);
    Optional<Set<Image>> getConnectionByTagId(Long tag_id);
    Optional<ImageTagLink> getConnectionById(Long id);
}
