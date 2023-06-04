package com.vmware.ics.repository;

import com.vmware.ics.model.ImageTag;
import com.vmware.ics.model.ImageTagKey;
import org.springframework.data.repository.CrudRepository;

public interface ImageTagRepository extends CrudRepository<ImageTag, ImageTagKey> {
}
