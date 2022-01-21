package com.nhlstenden.student.vigmo.services;


import com.nhlstenden.student.vigmo.dto.ScreenDto;
import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.models.Screen;
import com.nhlstenden.student.vigmo.repositories.ScreenRepository;
import com.nhlstenden.student.vigmo.services.logic.AbstractVigmoService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import liquibase.repackaged.org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
public class ScreenService extends AbstractVigmoService<ScreenRepository, ScreenDto, Screen> {
    public ScreenService(ScreenRepository repo, MappingUtility mapper, LogService logService) {
        super(repo, mapper, ScreenDto.class, Screen.class, logService);
    }

    @Override
    public long create(ScreenDto screenDto) {
        screenDto.setAuthKey(generateRandom32LengthKey());
        return super.create(screenDto);
    }

    @Override
    public void update(ScreenDto screenDto, long id) {
        screenDto.setAuthKey(this.get(id).getAuthKey());
        super.update(screenDto, id);
    }

    public ScreenDto getScreenByAuthKey(String authKey){
        Screen o = repo.findByAuthKey(authKey)
                .orElseThrow(() -> new DataNotFoundException(getClass().getSimpleName() + " could not find " + authKey));
        return mapper.mapObject(o, dtoType);
    }

    protected String generateRandom32LengthKey() {
        int length = 32;
        return RandomStringUtils.random(length, true, true);
    }
}
