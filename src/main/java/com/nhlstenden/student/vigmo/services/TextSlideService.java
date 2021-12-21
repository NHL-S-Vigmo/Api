package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.TextSlideDto;
import com.nhlstenden.student.vigmo.models.TextSlide;
import com.nhlstenden.student.vigmo.repositories.TextSlideRepository;
import com.nhlstenden.student.vigmo.services.logic.AbstractVigmoService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.springframework.stereotype.Service;

@Service
public class TextSlideService extends AbstractVigmoService<TextSlideRepository, TextSlideDto, TextSlide> {
    public TextSlideService(TextSlideRepository repo, MappingUtility mapper) {
        super(repo, mapper, TextSlideDto.class, TextSlide.class);
    }
}
