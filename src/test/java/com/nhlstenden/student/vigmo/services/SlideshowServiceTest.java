package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.SlideshowDto;
import com.nhlstenden.student.vigmo.dto.SlideshowSlidesDto;
import com.nhlstenden.student.vigmo.dto.SlideshowVariableDto;
import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.models.*;
import com.nhlstenden.student.vigmo.repositories.SlideshowRepository;
import com.nhlstenden.student.vigmo.services.ScreenService;
import com.nhlstenden.student.vigmo.services.SlideshowService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class SlideshowServiceTest {

    @Mock
    SlideshowRepository repo;

    @Mock
    Slideshow slideshowMock;

    @Mock
    SlideshowVariable slideshowVariableEntityMock;

    @Mock
    SlideshowVariableDto slideshowVariableDtoMock;

    @Mock
    Slide slideMock;

    @Mock
    SlideshowDto slideshowDtoMockWithId;

    @Mock
    SlideshowDto slideshowDtoMockWithoutId;

    @Mock
    SlideshowSlidesDto slideshowSlidesDtoMock;

    @Mock
    ScreenService screenServiceMock;

    @Mock
    private MappingUtility mapper;

    @InjectMocks
    SlideshowService slideshowService;



    @BeforeEach
    void setup() {
        openMocks(this);
        //get slideshow from repository
        when(repo.findById(1L)).thenReturn(Optional.of(slideshowMock));
        //get empty optional from non-existent slideshow id
        when(repo.findById(999L)).thenReturn(Optional.empty());

        when(repo.save(any(Slideshow.class))).
                thenReturn(slideshowMock);
        when(mapper.mapObject(slideshowDtoMockWithoutId, Slideshow.class)).thenReturn(slideshowMock);
        when(mapper.mapObject(slideshowMock, SlideshowDto.class)).thenReturn(slideshowDtoMockWithId);

        when(slideshowMock.getId()).thenReturn(1L);

        //Screen id belongs to an existing screen
        when(slideshowDtoMockWithoutId.getScreenId()).thenReturn(1L);
        when(slideshowDtoMockWithoutId.getId()).thenReturn(null);

        when(screenServiceMock.get(999L)).thenThrow(DataNotFoundException.class);
    }

    @Test
    void testGetVariables() {
        List<SlideshowVariableDto> slideshowVariableDtosMock = Arrays.asList(slideshowVariableDtoMock, slideshowVariableDtoMock, slideshowVariableDtoMock);
        List<SlideshowVariable> slideshowVariablesMock = Arrays.asList(slideshowVariableEntityMock, slideshowVariableEntityMock, slideshowVariableEntityMock);

        when(mapper.mapList(eq(slideshowVariablesMock), eq(SlideshowVariableDto.class))).thenReturn(slideshowVariableDtosMock);
        when(slideshowMock.getSlideshowVariableList()).thenReturn(slideshowVariablesMock);

        List<SlideshowVariableDto> variables = slideshowService.getVariables(1L);

        //check if there is actually something returned.
        assertThat(variables).isEqualTo(slideshowVariableDtosMock);

        //verify that the find and getSlideShowVariables functions were called
        verify(repo).findById(anyLong());
        verify(slideshowMock).getSlideshowVariableList();

        verify(mapper).mapList(eq(slideshowVariablesMock), any());
    }

    @Test
    void testGetSlides() {
        //return a list of mocked slides on getSlideList
        List<Slide> slidesMock = Arrays.asList(slideMock, slideMock, slideMock);
        when(slideshowMock.getSlideList()).thenReturn(slidesMock);
        //Mock getters for creating the slideshow slide dtos
        when(slideMock.getId()).thenReturn(1L).thenReturn(2L).thenReturn(3L);
        when(slideMock.getDuration()).thenReturn(1);
        when(slideMock.getIsActive()).thenReturn(true);
        //make sure that all three slide mocks got added to the list
        assertThat(slideshowService.getSlides(1L).size()).isEqualTo(3);
        //verify that the slideshow got retrieved from the repository
        verify(repo).findById(anyLong());

    }

    @Test
    void testGetSlidesWithANonExistentSlideshow() {
        //Slideshow service should throw a data not found exception when given an id that doesn't belong to any slideshow
        assertThatThrownBy(() -> slideshowService.getSlides(999L)).isInstanceOf(DataNotFoundException.class);
        //verify that the slideshow got retrieved from the repository
        verify(repo).findById(anyLong());

    }

    @Test
    void testCreateWithExistingScreen() {
        Long id = slideshowService.create(slideshowDtoMockWithoutId);

        assertThat(id)
                .isNotNull();
        //verify that the slideshow got saved and mapped correctly
        verify(repo).
                save(any());
        verify(mapper).
                mapObject(slideshowDtoMockWithoutId, Slideshow.class);
    }

    @Test
    void testCreateWithNonExistingScreen() {
        //Screen id is of a non-existing screen
        when(slideshowDtoMockWithoutId.getScreenId()).thenReturn(999L);

        Assertions.assertThatThrownBy(() -> slideshowService.create(slideshowDtoMockWithoutId)).isInstanceOf(DataNotFoundException.class);

        //verify that the save was not called
        verify(repo, Mockito.never()).save(slideshowMock);
    }

    @Test
    void testUpdateWithExistingScreen() {
        //Screen id is of an existing screen
        when(slideshowDtoMockWithId.getScreenId()).thenReturn(1L);

        slideshowService.update(slideshowDtoMockWithoutId, 1L);

        //verify slideshow was saved
        verify(repo).save(slideshowMock);
    }

    @Test
    void testUpdateWithNonExistingScreen() {
        //Screen id is of a non-existing screen
        when(slideshowDtoMockWithoutId.getScreenId()).thenReturn(999L);

        Assertions.assertThatThrownBy(() -> slideshowService.update(slideshowDtoMockWithoutId, 1L)).isInstanceOf(DataNotFoundException.class);

        //verify that the save was not called
        verify(repo, Mockito.never()).save(slideshowMock);
    }
}