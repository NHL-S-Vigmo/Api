package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.ScreenDto;
import com.nhlstenden.student.vigmo.models.Screen;
import com.nhlstenden.student.vigmo.repositories.ScreenRepository;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.List;
import java.util.Optional;


class ScreenServiceTest {

    @InjectMocks
    private ScreenService service;

    @Mock
    private ScreenRepository repository;

    @Mock
    private MappingUtility mapper;

    @BeforeEach
    void setup(){
        openMocks(this);

        //mocks for the repo
        when(repository.save(any(Screen.class))).thenReturn(new Screen());
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(new Screen()));

        //mocks for the mapper
        when(mapper.mapObject(any(Screen.class), eq(ScreenDto.class))).thenReturn(new ScreenDto());
    }

    @Test
    void getScreens() {
        List<ScreenDto> screens = service.getScreens();

        assertThat(screens).isNotNull();
    }

    @Test
    void getScreen() {
        ScreenDto screen = service.getScreen(1L);
        assertThat(screen).isNotNull();
    }

    @Test
    void createScreen() {
    }

    @Test
    void updateScreen() {
    }

    @Test
    void deleteScreen() {
    }
}