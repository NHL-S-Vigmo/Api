package com.nhlstenden.student.vigmo.services;


import com.nhlstenden.student.vigmo.dto.ScreenDto;
import com.nhlstenden.student.vigmo.models.Screen;
import com.nhlstenden.student.vigmo.repositories.ScreenRepository;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScreenService {
    private final MappingUtility mapper;
    private final ScreenRepository screenRepository;

    public ScreenService(ScreenRepository repository, MappingUtility mapper){
        this.screenRepository = repository;
        this.mapper = mapper;
    }

    public List<ScreenDto> getScreens(){
        return mapper.mapList(screenRepository.findAll(), ScreenDto.class);
    }

    public ScreenDto getScreen(Long id){
        return mapper.mapObject(screenRepository.findById(id), ScreenDto.class);
    }

    public Long createScreen(ScreenDto screenDto){
        Screen newObject = mapper.mapObject(screenDto, Screen.class);
        screenRepository.save(newObject);
        return newObject.getId();
    }

    public void updateScreen(ScreenDto screenDto, Long id){
        Screen newObject = mapper.mapObject(screenDto, Screen.class);
        newObject.setId(id);
        screenRepository.save(newObject);
    }

    public void deleteScreen(Long id){
        screenRepository.deleteById(id);
    }
}
