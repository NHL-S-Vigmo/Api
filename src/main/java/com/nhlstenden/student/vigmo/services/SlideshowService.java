package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.SlideshowDto;
import com.nhlstenden.student.vigmo.models.Slideshow;
import com.nhlstenden.student.vigmo.repositories.SlideshowRepository;
import com.nhlstenden.student.vigmo.services.logic.AbstractVigmoService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class SlideshowService extends AbstractVigmoService<SlideshowRepository, SlideshowDto, Slideshow> {
    public SlideshowService(SlideshowRepository repo, MappingUtility mapper) {
        super(repo, mapper, SlideshowDto.class, Slideshow.class);
    }
}
