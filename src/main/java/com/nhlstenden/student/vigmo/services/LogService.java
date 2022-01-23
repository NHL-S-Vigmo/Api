package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.LogDto;
import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.models.Log;
import com.nhlstenden.student.vigmo.repositories.LogRepository;
import com.nhlstenden.student.vigmo.repositories.UserRepository;
import com.nhlstenden.student.vigmo.services.logic.AbstractVigmoService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class LogService extends AbstractVigmoService<LogRepository, LogDto, Log> {
    private final UserRepository userRepository;

    public LogService(LogRepository repo, MappingUtility mapper, UserRepository userRepository) {
        super(repo, mapper, LogDto.class, Log.class, null);
        this.userRepository = userRepository;
    }

    @Override
    public long create(LogDto logDto) {
        //Will throw a data not found runtime exception if screen does not exist
        if(logDto.getUserId() != null)
            findUser(logDto.getUserId());

        if(logDto.getDatetime() == null) logDto.setDatetime(Instant.now().getEpochSecond());
        return super.create(logDto);
    }

    @Override
    public void update(LogDto logDto, long id) {
        //Will throw a data not found runtime exception if screen does not exist
        if(logDto.getUserId() != null)
            findUser(logDto.getUserId());
        if(logDto.getDatetime() == null) logDto.setDatetime(Instant.now().getEpochSecond());
        super.update(logDto, id);
    }

    private void findUser(long userId){
        userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(getClass().getSimpleName() + " could not find user " + userId));
    }
}
