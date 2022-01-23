package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.TextSlideDto;
import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.models.TextSlide;
import com.nhlstenden.student.vigmo.repositories.TextSlideRepository;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class TextSlideServiceTest {
    @Mock
    private TextSlideRepository repo;

    @Mock
    private MappingUtility mapper;

    @Mock
    private SlideshowService slideshowService;

    @Mock
    private TextSlide textSlideMock;

    @InjectMocks
    private TextSlideService textSlideServiceMock;

    @BeforeEach
    void setup() {
        openMocks(this);
        //Throw exception when trying to retrieve a non-existing slideshow
        when(slideshowService.get(999L)).thenThrow(DataNotFoundException.class);
        //mocks for the repo
        when(repo.save(any(TextSlide.class))).
                thenReturn(textSlideMock);
        when(repo.findById(1L)).
                thenReturn(Optional.of(textSlideMock));

        //mocks for the mapper
        when(mapper.mapObject(any(TextSlideDto.class), eq(TextSlide.class))).
                thenReturn(textSlideMock);
        when(textSlideMock.getId()).thenReturn(1L);
    }

    @Test
    void testTextSlideCreateWithExistingSlideshow(){
        //uses a dto instead of mocked dto as using a mocked dto causes getIdFieldValue
        //from AbstractVigmoService to no longer be able to find the id field
        TextSlideDto textSlideDto = new TextSlideDto();
        //Slideshow id is of an existing slideshow
        textSlideDto.setSlideshowId(1L);

        assertThat(textSlideServiceMock.create(textSlideDto)).isEqualTo(1L);

        //verify text slide was saved
        verify(repo).save(textSlideMock);
    }

    @Test
    void testTextSlideCreateWithNonExistingSlideshow(){
        TextSlideDto textSlideDto = new TextSlideDto();
        //Slideshow id is of a non-existing slideshow
        textSlideDto.setSlideshowId(999L);

        assertThatThrownBy(() -> textSlideServiceMock.create(textSlideDto)).isInstanceOf(DataNotFoundException.class);

        //verify that the save was not called
        verify(repo, Mockito.never()).save(textSlideMock);
    }

    @Test
    void testTextSlideUpdateWithExistingSlideshow(){
        TextSlideDto textSlideDto = new TextSlideDto();
        //Slideshow id is of an existing slideshow
        textSlideDto.setSlideshowId(1L);

        textSlideServiceMock.update(textSlideDto, 1L);

        //verify text slide was saved
        verify(repo).save(textSlideMock);
    }

    @Test
    void testTextSlideUpdateWithNonExistingSlideshow(){
        TextSlideDto textSlideDto = new TextSlideDto();
        //Slideshow id is of an existing slideshow
        textSlideDto.setSlideshowId(999L);

        assertThatThrownBy(() -> textSlideServiceMock.update(textSlideDto, 1L)).isInstanceOf(DataNotFoundException.class);

        //verify that the save was not called
        verify(repo, Mockito.never()).save(textSlideMock);
    }
}
