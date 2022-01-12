package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.AvailabilityDto;
import com.nhlstenden.student.vigmo.models.Availability;
import com.nhlstenden.student.vigmo.repositories.AvailabilityRepository;
import com.nhlstenden.student.vigmo.services.logic.AbstractVigmoService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AvailabilityService extends AbstractVigmoService<AvailabilityRepository, AvailabilityDto, Availability> {
    public AvailabilityService(AvailabilityRepository repo, MappingUtility mapper, LogService logService) {
        super(repo, mapper, AvailabilityDto.class, Availability.class, logService);
    }
}
