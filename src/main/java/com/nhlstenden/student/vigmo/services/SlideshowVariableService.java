package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.SlideshowVariableDto;
import com.nhlstenden.student.vigmo.models.SlideshowVariable;
import com.nhlstenden.student.vigmo.repositories.SlideshowVariableRepository;
import com.nhlstenden.student.vigmo.services.logic.AbstractVigmoService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.springframework.stereotype.Service;

@Service
public class SlideshowVariableService extends AbstractVigmoService<SlideshowVariableRepository, SlideshowVariableDto, SlideshowVariable> {
    private final SlideshowService slideshowService;

    public SlideshowVariableService(SlideshowVariableRepository repo, MappingUtility mapper, LogService logService, SlideshowService slideshowService) {
        super(repo, mapper, SlideshowVariableDto.class, SlideshowVariable.class, logService);
        this.slideshowService = slideshowService;
    }

    @Override
    public long create(SlideshowVariableDto slideshowVariableDto) {
        //Will throw a data not found runtime exception if screen does not exist
        slideshowService.get(slideshowVariableDto.getSlideshowId());
        return super.create(slideshowVariableDto);
    }

    @Override
    public void update(SlideshowVariableDto slideshowVariableDto, long id) {
        //Will throw a data not found runtime exception if screen does not exist
        slideshowService.get(slideshowVariableDto.getSlideshowId());
        super.update(slideshowVariableDto, id);
    }
}
