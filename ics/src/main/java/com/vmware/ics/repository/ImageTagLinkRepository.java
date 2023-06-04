package com.vmware.ics.repository;

import com.vmware.ics.model.ImageTagLink;
import com.vmware.ics.model.ImageTagLinkKey;
import org.springframework.data.repository.CrudRepository;

public interface ImageTagLinkRepository extends CrudRepository<ImageTagLink, ImageTagLinkKey> {
}
