package com.vmware.ics.repository;

import com.vmware.ics.model.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image, Long> {
}
