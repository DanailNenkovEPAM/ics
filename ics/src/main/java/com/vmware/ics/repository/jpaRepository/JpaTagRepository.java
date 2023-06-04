package com.vmware.ics.repository.jpaRepository;

import com.vmware.ics.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaTagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> getTagByName(String name);
    Optional<Tag> getTagById(Long id);
}
