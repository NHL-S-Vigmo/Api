package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.SlideshowDto;
import com.nhlstenden.student.vigmo.dto.SlideshowSlidesDto;
import com.nhlstenden.student.vigmo.dto.SlideshowVariableDto;
import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.models.Slide;
import com.nhlstenden.student.vigmo.models.Slideshow;
import com.nhlstenden.student.vigmo.models.SlideshowVariable;
import com.nhlstenden.student.vigmo.repositories.SlideshowRepository;
import com.nhlstenden.student.vigmo.services.logic.AbstractVigmoService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SlideshowService extends AbstractVigmoService<SlideshowRepository, SlideshowDto, Slideshow> {
    public SlideshowService(SlideshowRepository repo, MappingUtility mapper) {
        super(repo, mapper, SlideshowDto.class, Slideshow.class);
    }

    public List<SlideshowVariableDto> getVariables(Long id){
        Slideshow slideshow = repo.findById(id)
                .orElseThrow(() -> new DataNotFoundException(getClass().getSimpleName() + " could not find " + id));
        Hibernate.initialize(slideshow.getSlideshowVariableList());

        return mapper.mapList(slideshow.getSlideshowVariableList(), SlideshowVariableDto.class);
    }

    public List<SlideshowSlidesDto> getSlides(Long id){
        List<SlideshowSlidesDto> returnList = new ArrayList<>();
        Slideshow slideshow = repo.getById(id);
        List<Slide> slides = slideshow.getSlideList();

        return returnList;
    }
}
