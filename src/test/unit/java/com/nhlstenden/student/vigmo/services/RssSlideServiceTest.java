package unit.java.com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.RssSlideDto;
import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.models.RssSlide;
import com.nhlstenden.student.vigmo.repositories.RssSlideRepository;
import com.nhlstenden.student.vigmo.services.RssSlideService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class RssSlideServiceTest {
    @Mock
    private RssSlideRepository repo;

    @Mock
    private MappingUtility mapper;

    @Mock
    private SlideshowService slideshowService;

    @Mock
    private RssSlide rssSlideMock;

    @InjectMocks
    private RssSlideService rssSlideServiceMock;

    @BeforeEach
    void setUp() {
        openMocks(this);

        //mocks for the slideshow service
        when(slideshowService.existsById(1L)).thenReturn(true);
        when(slideshowService.existsById(999L)).thenReturn(false);

        //mocks for the repo
        when(repo.save(any(RssSlide.class))).
                thenReturn(rssSlideMock);
        when(repo.findById(1L)).
                thenReturn(Optional.of(rssSlideMock));

        //mocks for the mapper
        when(mapper.mapObject(any(RssSlideDto.class), eq(RssSlide.class))).
                thenReturn(rssSlideMock);
        when(rssSlideMock.getId()).thenReturn(1L);
    }

    @Test
    void testRssSlideCreateWithExistingSlideshow(){
        RssSlideDto rssSlideDto = new RssSlideDto();
        //Slideshow id is of an existing slideshow
        rssSlideDto.setSlideshowId(1L);

        assertThat(rssSlideServiceMock.create(rssSlideDto)).isEqualTo(1L);

        //verify rss slide was saved
        verify(repo).save(rssSlideMock);
    }

    @Test
    void testRssSlideCreateWithNonExistingSlideshow(){
        RssSlideDto rssSlideDto = new RssSlideDto();
        //Slideshow id is of a non-existing slideshow
        rssSlideDto.setSlideshowId(999L);

        assertThatThrownBy(() -> rssSlideServiceMock.create(rssSlideDto)).isInstanceOf(DataNotFoundException.class);

        //verify that the save was not called
        verify(repo, Mockito.never()).save(rssSlideMock);
    }

    @Test
    void testRssSlideUpdateWithExistingSlideshow(){
        RssSlideDto rssSlideDto = new RssSlideDto();
        //Slideshow id is of an existing slideshow
        rssSlideDto.setSlideshowId(1L);

        rssSlideServiceMock.update(rssSlideDto, 1L);

        //verify rss slide was saved
        verify(repo).save(rssSlideMock);
    }

    @Test
    void testRssSlideUpdateWithNonExistingSlideshow(){
        RssSlideDto rssSlideDto = new RssSlideDto();
        //Slideshow id is of an existing slideshow
        rssSlideDto.setSlideshowId(999L);

        assertThatThrownBy(() -> rssSlideServiceMock.update(rssSlideDto, 1L)).isInstanceOf(DataNotFoundException.class);

        //verify that the save was not called
        verify(repo, Mockito.never()).save(rssSlideMock);
    }
}
