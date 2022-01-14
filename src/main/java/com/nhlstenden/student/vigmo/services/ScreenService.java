package com.nhlstenden.student.vigmo.services;


import com.nhlstenden.student.vigmo.dto.ScreenDto;
import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.models.Screen;
import com.nhlstenden.student.vigmo.repositories.ScreenRepository;
import com.nhlstenden.student.vigmo.services.logic.AbstractVigmoService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ScreenService extends AbstractVigmoService<ScreenRepository, ScreenDto, Screen> {
    public ScreenService(ScreenRepository repo, MappingUtility mapper, LogService logService) {
        super(repo, mapper, ScreenDto.class, Screen.class, logService);
    }

    public ScreenDto getScreenByAuthKey(String authKey){
        Screen o = repo.findByAuthKey(authKey)
                .orElseThrow(() -> new DataNotFoundException(getClass().getSimpleName() + " could not find " + authKey));
        return mapper.mapObject(o, dtoType);
    }
}
