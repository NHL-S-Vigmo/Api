package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.RssSlideDto;
import com.nhlstenden.student.vigmo.dto.SlideshowVariableDto;
import com.nhlstenden.student.vigmo.dto.TextSlideDto;
import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.models.TextSlide;
import com.nhlstenden.student.vigmo.repositories.TextSlideRepository;
import com.nhlstenden.student.vigmo.services.logic.AbstractVigmoService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.springframework.stereotype.Service;

@Service
public class TextSlideService extends AbstractVigmoService<TextSlideRepository, TextSlideDto, TextSlide> {
    SlideshowService slideshowService;

    public TextSlideService(TextSlideRepository repo, MappingUtility mapper, LogService logService, SlideshowService slideshowService) {
        super(repo, mapper, TextSlideDto.class, TextSlide.class, logService);
        this.slideshowService = slideshowService;
    }

    @Override
    public long create(TextSlideDto textSlideDto, long userId, String username) {
        //Will throw a data not found runtime exception if screen does not exist
        if(slideshowService.existsById(textSlideDto.getSlideshowId())){
            return super.create(textSlideDto, userId, username);
        }else{
            throw new DataNotFoundException("Slideshow service could not find " + textSlideDto.getSlideshowId());
        }
    }

    @Override
    public void update(TextSlideDto textSlideDto, long id, long userId, String username) {
        //Will throw a data not found runtime exception if screen does not exist
        if(slideshowService.existsById(textSlideDto.getSlideshowId())){
            super.update(textSlideDto, id, userId, username);
        }else{
            throw new DataNotFoundException("Slideshow service could not find " + textSlideDto.getSlideshowId());
        }
    }
}
