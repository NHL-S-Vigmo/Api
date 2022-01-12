package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.MediaSlideDto;
import com.nhlstenden.student.vigmo.models.MediaSlide;
import com.nhlstenden.student.vigmo.repositories.MediaSlideRepository;
import com.nhlstenden.student.vigmo.services.logic.AbstractVigmoService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.springframework.stereotype.Service;

@Service
public class MediaSlideService extends AbstractVigmoService<MediaSlideRepository, MediaSlideDto, MediaSlide> {
    public MediaSlideService(MediaSlideRepository repo, MappingUtility mapper, LogService logService) {
        super(repo, mapper, MediaSlideDto.class, MediaSlide.class, logService);
    }
}
