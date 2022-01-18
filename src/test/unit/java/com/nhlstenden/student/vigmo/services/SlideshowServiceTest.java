package unit.java.com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.SlideshowVariableDto;
import com.nhlstenden.student.vigmo.models.Slide;
import com.nhlstenden.student.vigmo.models.Slideshow;
import com.nhlstenden.student.vigmo.models.SlideshowVariable;
import com.nhlstenden.student.vigmo.repositories.SlideshowRepository;
import com.nhlstenden.student.vigmo.services.SlideshowService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
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
    SlideshowVariable slideshowVariableEntityMock;

    @Mock
    SlideshowVariableDto slideshowVariableDtoMock;

    @Mock
    private MappingUtility mapper;

    @InjectMocks
    SlideshowService slideshowService;



    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void getVariables() {
        List<SlideshowVariableDto> slideshowVariableDtosMock = Arrays.asList(slideshowVariableDtoMock, slideshowVariableDtoMock, slideshowVariableDtoMock);
        List<SlideshowVariable> slideshowVariablesMock = Arrays.asList(slideshowVariableEntityMock, slideshowVariableEntityMock, slideshowVariableEntityMock);

        when(slideshowRepositoryMock.findById(anyLong())).thenReturn(Optional.of(slideshowEntityMock));
        when(mapper.mapList(eq(slideshowVariablesMock), eq(SlideshowVariableDto.class))).thenReturn(slideshowVariableDtosMock);
        when(slideshowEntityMock.getSlideshowVariableList()).thenReturn(slideshowVariablesMock);

        List<SlideshowVariableDto> variables = slideshowService.getVariables(1L);

        //check if there is actually something returned.
        assertThat(variables).isEqualTo(slideshowVariableDtosMock);

        //verify that the find and getSlideShowVariables functions were called
        verify(slideshowRepositoryMock).findById(anyLong());
        verify(slideshowEntityMock).getSlideshowVariableList();

        verify(mapper).mapList(eq(slideshowVariablesMock), any());
    }

    @Test
    void getSlides() {
        //todo: add mockito verify checks
        //todo: write test
    }
}