package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.ScreenDto;
import com.nhlstenden.student.vigmo.models.Screen;
import com.nhlstenden.student.vigmo.repositories.ScreenRepository;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.List;
import java.util.Optional;


class ScreenServiceTest {
    @Mock
    private ScreenRepository repository;

    @Mock
    private MappingUtility mapper;

    @InjectMocks
    private ScreenService service;

    @BeforeEach
    void setup(){
        openMocks(this);

        //mocks for the repo
        when(repository.save(any(Screen.class))).thenReturn(Screen.builder().id(1L).build());
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(Screen.builder().id(1L).build()));

        //mocks for the mapper
        when(mapper.mapObject(any(Screen.class), eq(ScreenDto.class))).thenReturn(ScreenDto.builder().id(1L).build());
        when(mapper.mapObject(any(ScreenDto.class), eq(Screen.class))).thenReturn(Screen.builder().id(1L).build());
    }

    @Test
    void getScreens() {
        List<ScreenDto> screens = service.getList();

        assertThat(screens).isNotNull();
    }

    @Test
    void getScreen() {
        ScreenDto screen = service.get(1L);
        assertThat(screen).isNotNull();
    }

    @Test
    void createScreen() {
        ScreenDto screenDto = new ScreenDto(null, "Screen1", "Top", "no-key-needed");
        Long id = service.create(screenDto);

        assertThat(id).isNotNull();
    }

    @Test
    void updateScreen() {
    }

    @Test
    void deleteScreen() {
    }
}