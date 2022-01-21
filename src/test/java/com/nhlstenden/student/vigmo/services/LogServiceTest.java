package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.LogDto;
import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.models.Log;
import com.nhlstenden.student.vigmo.models.User;
import com.nhlstenden.student.vigmo.repositories.LogRepository;
import com.nhlstenden.student.vigmo.repositories.UserRepository;
import com.nhlstenden.student.vigmo.services.LogService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class LogServiceTest {

    @Mock
    private LogRepository repo;

    @Mock
    private MappingUtility mapper;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private LogDto logDtoMockWithId;

    @Mock
    private LogDto logDtoMockWithoutId;

    @Mock
    private Log logMock;

    @Mock
    private User userMock;

    @InjectMocks
    private LogService logService;


    @BeforeEach
    void setup() {
        openMocks(this);

        //mocks for log service
        when(repo.getById(1L)).thenReturn(logMock);
        when(repo.getById(999L)).thenThrow(DataNotFoundException.class);

        //throw an exception when a non-existent user gets retrieved
        when(userRepositoryMock.findById(999L)).thenReturn(Optional.empty());
        when(userRepositoryMock.findById(1L)).thenReturn(Optional.of(userMock));

        //mocks for the repo
        when(repo.save(any(Log.class))).
                thenReturn(logMock);
        when(repo.findById(1L)).
                thenReturn(Optional.of(logMock));

        //mocks for the mapper
        when(mapper.mapObject(any(Log.class), eq(LogDto.class))).
                thenReturn(logDtoMockWithId);
        when(mapper.mapObject(any(LogDto.class), eq(Log.class))).
                thenReturn(logMock);

        //Mocks for dto objects
        when(logDtoMockWithoutId.getId()).thenReturn(null);
        when(logDtoMockWithId.getId()).thenReturn(1L);
    }

    @Test
    void testCreateWithExistingUser() {
        //id is of an existing user
        when(logDtoMockWithoutId.getUserId()).thenReturn(1L);
        Long id = logService.create(logDtoMockWithoutId);

        assertThat(id)
                .isNotNull();
        //verify that the log got saved and mapped correctly
        verify(repo).
                save(any());
        verify(mapper).
                mapObject(logDtoMockWithoutId, Log.class);
    }

    @Test
    void testCreateWithNonExistingUser() {
        //id is of a non-existing user
        when(logDtoMockWithoutId.getUserId()).thenReturn(999L);

        Assertions.assertThatThrownBy(() -> logService.create(logDtoMockWithoutId)).isInstanceOf(DataNotFoundException.class);

        //verify that the save was not called
        verify(repo, Mockito.never()).save(logMock);
    }

    @Test
    void testUpdateWithExistingUser() {
        //id is of an existing user
        when(logDtoMockWithoutId.getUserId()).thenReturn(1L);

        logService.update(logDtoMockWithoutId, 1L);

        //verify log was saved
        verify(repo).save(logMock);
    }

    @Test
    void testUpdateWithNonExistingUser() {
        //id is of a non-existing user
        when(logDtoMockWithoutId.getUserId()).thenReturn(999L);

        Assertions.assertThatThrownBy(() -> logService.update(logDtoMockWithoutId, 1L)).isInstanceOf(DataNotFoundException.class);

        //verify that the save was not called
        verify(repo, Mockito.never()).save(logMock);
    }
}
