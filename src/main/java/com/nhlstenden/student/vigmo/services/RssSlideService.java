package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.RssSlideDto;
import com.nhlstenden.student.vigmo.models.RssSlide;
import com.nhlstenden.student.vigmo.repositories.RssSlideRepository;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;

public class RssSlideService extends AbstractVigmoService<RssSlideRepository, RssSlideDto, RssSlide> {
    public RssSlideService(RssSlideRepository repo, MappingUtility mapper) {
        super(repo, mapper, RssSlideDto.class, RssSlide.class);
    }
}
