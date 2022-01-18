package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.AvailabilityDto;
import com.nhlstenden.student.vigmo.dto.LogDto;
import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.models.Log;
import com.nhlstenden.student.vigmo.repositories.LogRepository;
import com.nhlstenden.student.vigmo.repositories.UserRepository;
import com.nhlstenden.student.vigmo.services.logic.AbstractVigmoService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class LogService extends AbstractVigmoService<LogRepository, LogDto, Log> {
    private final UserRepository userRepository;

    public LogService(LogRepository repo, MappingUtility mapper, UserRepository userRepository) {
        super(repo, mapper, LogDto.class, Log.class, null);
        this.userRepository = userRepository;
    }

    @Override
    public long create(LogDto availabilityDto) {
        //Will throw a data not found runtime exception if screen does not exist
        findUser(availabilityDto.getUserId());
        return super.create(availabilityDto);
    }

    @Override
    public void update(LogDto availabilityDto, long id) {
        //Will throw a data not found runtime exception if screen does not exist
        findUser(availabilityDto.getUserId());
        super.update(availabilityDto, id);
    }

    private void findUser(long userId){
        userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(getClass().getSimpleName() + " could not find user " + userId));
    }
}
