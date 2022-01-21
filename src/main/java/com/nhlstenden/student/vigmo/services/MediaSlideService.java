package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.MediaSlideDto;
import com.nhlstenden.student.vigmo.models.MediaSlide;
import com.nhlstenden.student.vigmo.repositories.MediaSlideRepository;
import com.nhlstenden.student.vigmo.services.logic.AbstractVigmoService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.springframework.stereotype.Service;

@Service
public class MediaSlideService extends AbstractVigmoService<MediaSlideRepository, MediaSlideDto, MediaSlide> {

    private final SlideshowService slideshowService;

    public MediaSlideService(MediaSlideRepository repo, MappingUtility mapper, LogService logService, SlideshowService slideshowService) {
        super(repo, mapper, MediaSlideDto.class, MediaSlide.class, logService);
        this.slideshowService = slideshowService;
    }

    @Override
    public long create(MediaSlideDto mediaSlideDto) {
        //Will throw a data not found runtime exception if screen does not exist
        slideshowService.get(mediaSlideDto.getSlideshowId());
        return super.create(mediaSlideDto);
    }

    @Override
    public void update(MediaSlideDto mediaSlideDto, long id) {
        //Will throw a data not found runtime exception if screen does not exist
        slideshowService.get(mediaSlideDto.getSlideshowId());
        super.update(mediaSlideDto, id);
    }
}
