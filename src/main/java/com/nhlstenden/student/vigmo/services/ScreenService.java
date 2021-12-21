package com.nhlstenden.student.vigmo.services;


import com.nhlstenden.student.vigmo.dto.ScreenDto;
import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.models.Screen;
import com.nhlstenden.student.vigmo.repositories.ScreenRepository;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScreenService implements VigmoService<ScreenDto> {
    private final MappingUtility mapper;
    private final ScreenRepository screenRepository;

    public ScreenService(ScreenRepository repository, MappingUtility mapper){
        this.screenRepository = repository;
        this.mapper = mapper;
    }

    public List<ScreenDto> getList(){
        return mapper.mapList(screenRepository.findAll(), ScreenDto.class);
    }

    public ScreenDto get(long id){
        Optional<Screen> dbObject = screenRepository.findById(id);
        if(dbObject.isPresent()){
            return mapper.mapObject(dbObject.get(), ScreenDto.class);
        }
        else{
            throw new DataNotFoundException("ScreenService could not find " + id);
        }
    }

    public long create(ScreenDto screenDto){
        Screen newObject = mapper.mapObject(screenDto, Screen.class);
        screenRepository.save(newObject);
        return newObject.getId();
    }

    public void update(ScreenDto screenDto, long id){
        Screen newObject = mapper.mapObject(screenDto, Screen.class);
        newObject.setId(id);
        screenRepository.save(newObject);
    }

    public void delete(long id){
        screenRepository.deleteById(id);
    }
}
