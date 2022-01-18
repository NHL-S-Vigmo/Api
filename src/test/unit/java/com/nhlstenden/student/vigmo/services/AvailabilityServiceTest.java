package unit.java.com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.AvailabilityDto;
import com.nhlstenden.student.vigmo.dto.UserDto;
import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.exception.IdProvidedInCreateRequestException;
import com.nhlstenden.student.vigmo.models.Availability;
import com.nhlstenden.student.vigmo.models.TestEntity;
import com.nhlstenden.student.vigmo.repositories.AvailabilityRepository;
import com.nhlstenden.student.vigmo.repositories.TestEntityRepository;
import com.nhlstenden.student.vigmo.services.AvailabilityService;
import com.nhlstenden.student.vigmo.services.UserService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

public class AvailabilityServiceTest {
    @Mock
    private UserService userServiceMock;

    @Mock
    private AvailabilityRepository repository;

    @Mock
    private MappingUtility mapper;

    @Mock
    private AvailabilityDto availabilityDtoMock;

    @Mock
    private UserDto userMock;

    @Mock
    private Availability availabilityMock;

    @InjectMocks
    private AvailabilityService availabilityService;


    @BeforeEach
    void setup() {
        openMocks(this);

        //mocks for user service
        when(userServiceMock.get(1L)).thenReturn(userMock);
        when(userServiceMock.get(999L)).thenThrow(DataNotFoundException.class);

        //mocks for the repo
        when(repository.save(any(Availability.class))).
                thenReturn(availabilityMock);
        when(repository.findById(1L)).
                thenReturn(Optional.of(availabilityMock));
        when(repository.findById(2L)).
                thenReturn(Optional.empty());

        //mocks for the mapper
        when(mapper.mapObject(any(Availability.class), eq(AvailabilityDto.class))).
                thenReturn(availabilityDtoMock);
        when(mapper.mapObject(any(AvailabilityDto.class), eq(Availability.class))).
                thenReturn(availabilityMock);
    }

    @Test
    void testCreateAvailabilityWithValidUserId() {
        AvailabilityDto availabilityDto = new AvailabilityDto();
        availabilityDto.setUserId(1L);
        Long id = availabilityService.create(availabilityDto);

        assertThat(id)
                .isNotNull();

        verify(repository).
                save(any());
        verify(mapper).
                mapObject(availabilityDto, Availability.class);
    }

    @Test
    void testCreateAvailabilityWithInvalidUserId() {
        AvailabilityDto availabilityDto = new AvailabilityDto();
        //setting user id to a non-existent user
        availabilityDto.setUserId(999L);

        assertThatThrownBy(() -> availabilityService.create(availabilityDto)).isInstanceOf(DataNotFoundException.class);

        verify(repository, Mockito.never())
                .save(any());
    }

    @Test
    void testUpdateAvailabilityWithValidUserId() {
        AvailabilityDto availabilityDto = new AvailabilityDto();
        availabilityDto.setUserId(1L);

        availabilityService.update(availabilityDto, 1L);

        verify(repository).
                findById(anyLong());
        verify(repository).
                save(any());
        verify(userServiceMock).
                get(anyLong());
        verify(mapper).
                mapObject(availabilityDto, Availability.class);
    }

    @Test
    void testUpdateAvailabilityWithInvalidUserId() {
        AvailabilityDto availabilityDto = new AvailabilityDto();
        //setting user id to a non-existent user
        availabilityDto.setUserId(999L);
        
        assertThatThrownBy(() -> availabilityService.update(availabilityDto, 1L)).isInstanceOf(DataNotFoundException.class);

        verify(userServiceMock).
                get(anyLong());
        verify(repository, Mockito.never()).
                save(any());
    }
}
