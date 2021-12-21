package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.MediaSlideDto;
import com.nhlstenden.student.vigmo.models.MediaSlide;
import com.nhlstenden.student.vigmo.repositories.MediaSlideRepository;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;

public class MediaSlideService extends AbstractVigmoService<MediaSlideRepository, MediaSlideDto, MediaSlide> {
    public MediaSlideService(MediaSlideRepository repo, MappingUtility mapper) {
        super(repo, mapper, MediaSlideDto.class, MediaSlide.class);
    }
}
