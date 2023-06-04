package com.vmware.ics.service;

import com.vmware.ics.model.ImageTagLink;
import com.vmware.ics.repository.ImageTagLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageTagLinkService {

    private final ImageTagLinkRepository linkRepo;

    @Autowired
    public ImageTagLinkService(ImageTagLinkRepository linkRepo) {
        this.linkRepo = linkRepo;
    }

    public ImageTagLink addImageTagLinkConnection(ImageTagLink imageTagLink){
        return this.linkRepo.saveAndFlush(imageTagLink);
    }
}