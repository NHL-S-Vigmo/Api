package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.RssSlideDto;
import com.nhlstenden.student.vigmo.dto.SlideshowVariableDto;
import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.models.SlideshowVariable;
import com.nhlstenden.student.vigmo.repositories.SlideshowVariableRepository;
import com.nhlstenden.student.vigmo.services.logic.AbstractVigmoService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SlideshowVariableService extends AbstractVigmoService<SlideshowVariableRepository, SlideshowVariableDto, SlideshowVariable> {
    SlideshowService slideshowService;
    public SlideshowVariableService(SlideshowVariableRepository repo, MappingUtility mapper, LogService logService, SlideshowService slideshowService) {
        super(repo, mapper, SlideshowVariableDto.class, SlideshowVariable.class, logService);
        this.slideshowService = slideshowService;
    }

    @Override
    public long create(SlideshowVariableDto slideshowVariableDto, long userId, String username) {
        //Will throw a data not found runtime exception if screen does not exist
        if(slideshowService.existsById(slideshowVariableDto.getSlideshowId())){
            return super.create(slideshowVariableDto, userId, username);
        }else{
            throw new DataNotFoundException("Slideshow service could not find " + slideshowVariableDto.getSlideshowId());
        }
    }

    @Override
    public void update(SlideshowVariableDto rssSlideDto, long id, long userId, String username) {
        //Will throw a data not found runtime exception if screen does not exist
        if(slideshowService.existsById(rssSlideDto.getSlideshowId())){
            super.update(rssSlideDto, id, userId, username);
        }else{
            throw new DataNotFoundException("Slideshow service could not find " + rssSlideDto.getSlideshowId());
        }
    }
}
