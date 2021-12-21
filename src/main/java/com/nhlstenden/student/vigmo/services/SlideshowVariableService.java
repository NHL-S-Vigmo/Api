package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.SlideshowVariableDto;
import com.nhlstenden.student.vigmo.models.SlideshowVariable;
import com.nhlstenden.student.vigmo.repositories.SlideshowVariableRepository;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;

public class SlideshowVariableService extends AbstractVigmoService<SlideshowVariableRepository, SlideshowVariableDto, SlideshowVariable> {
    public SlideshowVariableService(SlideshowVariableRepository repo, MappingUtility mapper) {
        super(repo, mapper, SlideshowVariableDto.class, SlideshowVariable.class);
    }
}
