package com.nhlstenden.student.vigmo.services;


import com.nhlstenden.student.vigmo.dto.ScreenDto;
import com.nhlstenden.student.vigmo.models.Screen;
import com.nhlstenden.student.vigmo.repositories.ScreenRepository;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.springframework.stereotype.Service;

@Service
public class ScreenService extends AbstractVigmoService<ScreenRepository, ScreenDto, Screen> {
    public ScreenService(ScreenRepository repo, MappingUtility mapper) {
        super(repo, mapper, ScreenDto.class, Screen.class);
    }
}
