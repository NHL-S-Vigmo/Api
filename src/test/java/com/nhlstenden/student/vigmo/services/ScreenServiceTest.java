package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.ScreenDto;
import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.models.Screen;
import com.nhlstenden.student.vigmo.repositories.ScreenRepository;
import com.nhlstenden.student.vigmo.services.ScreenService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.assertj.core.api.Assertions.assertThat;

class ScreenServiceTest {

    @Mock
    private ScreenRepository repo;

    @Mock
    private MappingUtility mapper;

    @Mock
    private Screen screenMock;

    @Mock
    private ScreenDto screenDtoMock;

    @InjectMocks
    private ScreenService screenService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        when(mapper.mapObject(screenMock, ScreenDto.class)).thenReturn(screenDtoMock);
    }

    @Test
    void getScreenByAuthKey() {
        //Auth key belongs to a screen
        when(repo.findByAuthKey(anyString())).thenReturn(Optional.of(screenMock));

        assertThat(screenService.getScreenByAuthKey("abc")).isNotNull();
        //verify that the repository got checked and the screen mapped to a dto
        verify(repo).findByAuthKey(anyString());
        verify(mapper).mapObject(screenMock,ScreenDto.class);
    }

    @Test
    void getNonExistentScreenByAuthKey() {
        //Auth key does not belong to a screen
        when(repo.findByAuthKey(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> screenService.getScreenByAuthKey("abc")).isInstanceOf(DataNotFoundException.class);

        //verify that the repository got checked
        verify(repo).findByAuthKey(anyString());
    }
}