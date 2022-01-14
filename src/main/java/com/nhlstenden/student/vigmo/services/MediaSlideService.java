package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.MediaSlideDto;
import com.nhlstenden.student.vigmo.dto.RssSlideDto;
import com.nhlstenden.student.vigmo.dto.SlideshowDto;
import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.models.MediaSlide;
import com.nhlstenden.student.vigmo.repositories.MediaSlideRepository;
import com.nhlstenden.student.vigmo.services.logic.AbstractVigmoService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MediaSlideService extends AbstractVigmoService<MediaSlideRepository, MediaSlideDto, MediaSlide> {

    SlideshowService slideshowService;

    public MediaSlideService(MediaSlideRepository repo, MappingUtility mapper, LogService logService, SlideshowService slideshowService) {
        super(repo, mapper, MediaSlideDto.class, MediaSlide.class, logService);
        this.slideshowService = slideshowService;
    }

    @Override
    public long create(MediaSlideDto mediaSlideDto) {
        //Will throw a data not found runtime exception if screen does not exist
        if(slideshowService.existsById(mediaSlideDto.getSlideshowId())){
            return super.create(mediaSlideDto);
        }else{
            throw new DataNotFoundException("Slideshow service could not find " + mediaSlideDto.getSlideshowId());
        }
    }

    @Override
    public void update(MediaSlideDto mediaSlideDto, long id) {
        //Will throw a data not found runtime exception if screen does not exist
        if(slideshowService.existsById(mediaSlideDto.getSlideshowId())){
            super.update(mediaSlideDto, id);
        }else{
            throw new DataNotFoundException("Slideshow service could not find " + mediaSlideDto.getSlideshowId());
        }
    }
}
