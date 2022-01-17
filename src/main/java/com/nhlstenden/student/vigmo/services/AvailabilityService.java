package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.AvailabilityDto;
import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.models.Availability;
import com.nhlstenden.student.vigmo.repositories.AvailabilityRepository;
import com.nhlstenden.student.vigmo.services.logic.AbstractVigmoService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.springframework.stereotype.Service;

@Service
public class AvailabilityService extends AbstractVigmoService<AvailabilityRepository, AvailabilityDto, Availability> {
    UserService userService;

    public AvailabilityService(AvailabilityRepository repo, MappingUtility mapper, LogService logService, UserService userService) {
        super(repo, mapper, AvailabilityDto.class, Availability.class, logService);
        this.userService = userService;
    }

    @Override
    public long create(AvailabilityDto availabilityDto) {
        //Will throw a data not found runtime exception if screen does not exist
        if(userService.existsById(availabilityDto.getUserId())){
            return super.create(availabilityDto);
        }else{
            throw new DataNotFoundException("User service could not find " + availabilityDto.getUserId());
        }
    }

    @Override
    public void update(AvailabilityDto availabilityDto, long id) {
        //Will throw a data not found runtime exception if screen does not exist
        if(userService.existsById(availabilityDto.getUserId())){
            super.update(availabilityDto, id);
        }else{
            throw new DataNotFoundException("User service could not find " + availabilityDto.getUserId());
        }
    }
}
