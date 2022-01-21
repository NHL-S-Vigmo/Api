package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.TextSlideDto;
import com.nhlstenden.student.vigmo.models.TextSlide;
import com.nhlstenden.student.vigmo.repositories.TextSlideRepository;
import com.nhlstenden.student.vigmo.services.logic.AbstractVigmoService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.springframework.stereotype.Service;

@Service
public class TextSlideService extends AbstractVigmoService<TextSlideRepository, TextSlideDto, TextSlide> {
    private final SlideshowService slideshowService;

    public TextSlideService(TextSlideRepository repo, MappingUtility mapper, LogService logService, SlideshowService slideshowService) {
        super(repo, mapper, TextSlideDto.class, TextSlide.class, logService);
        this.slideshowService = slideshowService;
    }

    @Override
    public long create(TextSlideDto textSlideDto) {
        //Will throw a data not found runtime exception if screen does not exist
        slideshowService.get(textSlideDto.getSlideshowId());
        return super.create(textSlideDto);
    }

    @Override
    public void update(TextSlideDto textSlideDto, long id) {
        //Will throw a data not found runtime exception if screen does not exist
        slideshowService.get(textSlideDto.getSlideshowId());
        super.update(textSlideDto, id);
    }
}
