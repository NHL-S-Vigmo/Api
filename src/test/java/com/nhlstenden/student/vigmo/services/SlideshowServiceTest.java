package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.SlideshowDto;
import com.nhlstenden.student.vigmo.dto.SlideshowVariableDto;
import com.nhlstenden.student.vigmo.dto.UserDto;
import com.nhlstenden.student.vigmo.models.Slide;
import com.nhlstenden.student.vigmo.models.Slideshow;
import com.nhlstenden.student.vigmo.models.User;
import com.nhlstenden.student.vigmo.repositories.SlideshowRepository;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class SlideshowServiceTest {

    @Mock
    SlideshowRepository slideshowRepositoryMock;

    @Mock
    Slideshow slideshowEntityMock;

    @Mock
    private MappingUtility mapper;

    @InjectMocks
    SlideshowService slideshowService;

    @Captor
    private ArgumentCaptor<List<SlideshowVariableDto>> slidesDtoCaptor;

    @Captor
    private ArgumentCaptor<List<Slide>> slidesCaptor;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void getVariables() {
        when(slideshowRepositoryMock.findById(anyLong())).thenReturn(Optional.of(slideshowEntityMock));
        when(mapper.mapList(slidesCaptor.capture(), eq(SlideshowVariableDto.class))).thenReturn(slidesDtoCaptor.capture());

        List<SlideshowVariableDto> variables = slideshowService.getVariables(1L);

        //check if there is actually something returned.
        assertThat(variables).isEqualTo(slidesDtoCaptor.getValue());

        //verify that the find and getSlideShowVariables functions were called
        verify(slideshowRepositoryMock).findById(anyLong());
        verify(slideshowEntityMock).getSlideshowVariableList();

        verify(mapper).mapObject(slidesCaptor.capture(), any());
        //fixme: needs completions
    }

    @Test
    void getSlides() {
        //todo: write test
    }
}