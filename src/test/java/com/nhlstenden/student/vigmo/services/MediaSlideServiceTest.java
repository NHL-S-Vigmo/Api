package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.MediaSlideDto;
import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.models.MediaSlide;
import com.nhlstenden.student.vigmo.repositories.MediaSlideRepository;
import com.nhlstenden.student.vigmo.services.MediaSlideService;
import com.nhlstenden.student.vigmo.services.SlideshowService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class MediaSlideServiceTest {
    @Mock
    private MediaSlideRepository repo;

    @Mock
    private MappingUtility mapper;

    @Mock
    private SlideshowService slideshowService;

    @Mock
    private MediaSlide mediaSlideMock;

    @InjectMocks
    private MediaSlideService mediaSlideServiceMock;

    @BeforeEach
    void setup() {
        openMocks(this);
        //Throw exception when trying to retrieve a non-existing slideshow
        when(slideshowService.get(999L)).thenThrow(DataNotFoundException.class);
        //mocks for the repo
        when(repo.save(any(MediaSlide.class))).
                thenReturn(mediaSlideMock);
        when(repo.findById(1L)).
                thenReturn(Optional.of(mediaSlideMock));

        //mock for the mapper
        when(mapper.mapObject(any(MediaSlideDto.class), eq(MediaSlide.class))).
                thenReturn(mediaSlideMock);

        when(mediaSlideMock.getId()).thenReturn(1L);
    }

    @Test
    void testMediaSlideCreateWithExistingSlideshow(){
        //uses a dto instead of mocked dto as using a mocked dto causes getIdFieldValue
        //from AbstractVigmoService to no longer be able to find the id field
        MediaSlideDto mediaSlideDto = new MediaSlideDto();
        //Slideshow id is of an existing slideshow
        mediaSlideDto.setSlideshowId(1L);

        assertThat(mediaSlideServiceMock.create(mediaSlideDto)).isEqualTo(1L);

        //verify media slide was saved
        verify(repo).save(mediaSlideMock);
    }

    @Test
    void testMediaSlideCreateWithNonExistingSlideshow(){
        MediaSlideDto mediaSlideDto = new MediaSlideDto();
        //Slideshow id is of a non-existing slideshow
        mediaSlideDto.setSlideshowId(999L);

        assertThatThrownBy(() -> mediaSlideServiceMock.create(mediaSlideDto)).isInstanceOf(DataNotFoundException.class);

        //verify that the object was not saved
        verify(repo, Mockito.never()).save(mediaSlideMock);
    }

    @Test
    void testMediaSlideUpdateWithExistingSlideshow(){
        MediaSlideDto mediaSlideDto = new MediaSlideDto();
        //Slideshow id is of an existing slideshow
        mediaSlideDto.setSlideshowId(1L);

        mediaSlideServiceMock.update(mediaSlideDto, 1L);

        //verify media slide was saved
        verify(repo).save(mediaSlideMock);
    }

    @Test
    void testMediaSlideUpdateWithNonExistingSlideshow(){
        MediaSlideDto mediaSlideDto = new MediaSlideDto();
        //Slideshow id is of an existing slideshow
        mediaSlideDto.setSlideshowId(999L);

        assertThatThrownBy(() -> mediaSlideServiceMock.update(mediaSlideDto, 1L)).isInstanceOf(DataNotFoundException.class);

        //verify that the save was not called
        verify(repo, Mockito.never()).save(mediaSlideMock);
    }
}
