package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.RssSlideDto;
import com.nhlstenden.student.vigmo.dto.SlideshowVariableDto;
import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.models.RssSlide;
import com.nhlstenden.student.vigmo.repositories.RssSlideRepository;
import com.nhlstenden.student.vigmo.services.logic.AbstractVigmoService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RssSlideService extends AbstractVigmoService<RssSlideRepository, RssSlideDto, RssSlide> {
    SlideshowService slideshowService;

    public RssSlideService(RssSlideRepository repo, MappingUtility mapper, LogService logService, SlideshowService slideshowService) {
        super(repo, mapper, RssSlideDto.class, RssSlide.class, logService);
        this.slideshowService = slideshowService;
    }
    @Override
    public long create(RssSlideDto rssSlideDto) {
        //Will throw a data not found runtime exception if screen does not exist
        if(slideshowService.existsById(rssSlideDto.getSlideshowId())){
            return super.create(rssSlideDto);
        }else{
            throw new DataNotFoundException("Slideshow service could not find " + rssSlideDto.getSlideshowId());
        }
    }

    @Override
    public void update(RssSlideDto rssSlideDto, long id) {
        //Will throw a data not found runtime exception if screen does not exist
        if(slideshowService.existsById(rssSlideDto.getSlideshowId())){
            super.update(rssSlideDto, id);
        }else{
            throw new DataNotFoundException("Slideshow service could not find " + rssSlideDto.getSlideshowId());
        }
    }
}
