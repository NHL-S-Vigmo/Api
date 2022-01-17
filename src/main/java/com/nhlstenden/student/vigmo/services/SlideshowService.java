package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.SlideshowDto;
import com.nhlstenden.student.vigmo.dto.SlideshowSlidesDto;
import com.nhlstenden.student.vigmo.dto.SlideshowVariableDto;
import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.models.Slide;
import com.nhlstenden.student.vigmo.models.Slideshow;
import com.nhlstenden.student.vigmo.repositories.SlideshowRepository;
import com.nhlstenden.student.vigmo.services.logic.AbstractVigmoService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class SlideshowService extends AbstractVigmoService<SlideshowRepository, SlideshowDto, Slideshow> {

    private final ScreenService screenService;

    public SlideshowService(SlideshowRepository repo, MappingUtility mapper, LogService logService, ScreenService screenService) {
        super(repo, mapper, SlideshowDto.class, Slideshow.class, logService);
        this.screenService = screenService;
    }

    public List<SlideshowVariableDto> getVariables(Long id){
        Slideshow slideshow = repo.findById(id)
                .orElseThrow(() -> new DataNotFoundException(getClass().getSimpleName() + " could not find " + id));

        return mapper.mapList(slideshow.getSlideshowVariableList(), SlideshowVariableDto.class);
    }

    public List<SlideshowSlidesDto> getSlides(Long id){
        Slideshow slideshow = repo.findById(id)
                .orElseThrow(() -> new DataNotFoundException(getClass().getSimpleName() + " could not find " + id));

        List<Slide> slides = slideshow.getSlideList();

        List<SlideshowSlidesDto> returnList = new ArrayList<>();
        //go through each slide and add details about it.
        for(Slide slide: slides){
            SlideshowSlidesDto slideDto = new SlideshowSlidesDto();
            slideDto.setSlideId(slide.getId());
            slideDto.setDuration(slide.getDuration());
            slideDto.setIsActive(slide.getIsActive());

            String pathName = slide.getClass().getSimpleName().toLowerCase(Locale.ROOT).replace("slide", "_slides");
            slideDto.setPath(String.format("/%s/%d", pathName, slide.getId()));
            returnList.add(slideDto);
        }

        return returnList;
    }

    @Override
    public long create(SlideshowDto slideshowDto) {
        //Will throw a data not found runtime exception if screen does not exist
        if(screenService.existsById(slideshowDto.getScreenId())){
            return super.create(slideshowDto);
        }else{
            throw new DataNotFoundException("Screen service could not find " + slideshowDto.getScreenId());
        }
    }

    @Override
    public void update(SlideshowDto slideshowDto, long id) {
        //Will throw a data not found runtime exception if screen does not exist
        if(screenService.existsById(slideshowDto.getScreenId())){
            super.update(slideshowDto, id);
        }else{
            throw new DataNotFoundException("Screen service could not find " + slideshowDto.getScreenId());
        }
    }
}
