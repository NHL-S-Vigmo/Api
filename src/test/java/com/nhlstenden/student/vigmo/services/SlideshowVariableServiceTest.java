package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.SlideshowVariableDto;
import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.models.SlideshowVariable;
import com.nhlstenden.student.vigmo.repositories.SlideshowVariableRepository;
import com.nhlstenden.student.vigmo.services.SlideshowService;
import com.nhlstenden.student.vigmo.services.SlideshowVariableService;
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

public class SlideshowVariableServiceTest {

    @Mock
    private SlideshowVariableRepository repo;

    @Mock
    private MappingUtility mapper;

    @Mock
    private SlideshowService slideshowServiceMock;

    @Mock
    private SlideshowVariableDto slideshowVariableDtoMockWithId;

    @Mock
    private SlideshowVariableDto slideshowVariableDtoMockWithoutId;

    @Mock
    private SlideshowVariable slideshowVariableMock;

    @InjectMocks
    private SlideshowVariableService slideshowVariableService;


    @BeforeEach
    void setup() {
        openMocks(this);

        //mocks for slideshow variable service
        when(repo.getById(1L)).thenReturn(slideshowVariableMock);
        when(repo.getById(999L)).thenThrow(DataNotFoundException.class);

        //throw an exception when a non-existent slideshow gets retrieved
        when(slideshowServiceMock.get(999L)).thenThrow(DataNotFoundException.class);

        //mocks for the repo
        when(repo.save(any(SlideshowVariable.class))).
                thenReturn(slideshowVariableMock);
        when(repo.findById(1L)).
                thenReturn(Optional.of(slideshowVariableMock));

        //mocks for the mapper
        when(mapper.mapObject(any(SlideshowVariable.class), eq(SlideshowVariableDto.class))).
                thenReturn(slideshowVariableDtoMockWithId);
        when(mapper.mapObject(any(SlideshowVariableDto.class), eq(SlideshowVariable.class))).
                thenReturn(slideshowVariableMock);

        //Mocks for dto objects
        when(slideshowVariableDtoMockWithoutId.getId()).thenReturn(null);
        when(slideshowVariableDtoMockWithId.getId()).thenReturn(1L);
    }

    @Test
    void testCreateWithExistingSlideshow() {
        //id is of an existing slideshow
        when(slideshowVariableDtoMockWithoutId.getSlideshowId()).thenReturn(1L);
        Long id = slideshowVariableService.create(slideshowVariableDtoMockWithoutId);

        assertThat(id)
                .isNotNull();
        //verify that the slideshow variable got saved and mapped correctly
        verify(repo).
                save(any());
        verify(mapper).
                mapObject(slideshowVariableDtoMockWithoutId, SlideshowVariable.class);
    }

    @Test
    void testCreateWithNonExistingSlideshow() {
        //id is of a non-existing slideshow
        when(slideshowVariableDtoMockWithoutId.getSlideshowId()).thenReturn(999L);

        Assertions.assertThatThrownBy(() -> slideshowVariableService.create(slideshowVariableDtoMockWithoutId)).isInstanceOf(DataNotFoundException.class);

        //verify that the save was not called
        verify(repo, Mockito.never()).save(slideshowVariableMock);
    }

    @Test
    void testUpdateWithExistingSlideshow() {
        //id is of an existing slideshow
        when(slideshowVariableDtoMockWithoutId.getSlideshowId()).thenReturn(1L);

        slideshowVariableService.update(slideshowVariableDtoMockWithoutId, 1L);

        //verify slideshow variable was saved
        verify(repo).save(slideshowVariableMock);
    }

    @Test
    void testUpdateWithNonExistingSlideshow() {
        //id is of a non-existing slideshow
        when(slideshowVariableDtoMockWithoutId.getSlideshowId()).thenReturn(999L);

        Assertions.assertThatThrownBy(() -> slideshowVariableService.update(slideshowVariableDtoMockWithoutId, 1L)).isInstanceOf(DataNotFoundException.class);

        //verify that the save was not called
        verify(repo, Mockito.never()).save(slideshowVariableMock);
    }
}
