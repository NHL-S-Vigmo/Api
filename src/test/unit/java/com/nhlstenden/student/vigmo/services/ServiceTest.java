package unit.java.com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.LogDto;
import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.exception.EntityIdRequirementNotMetException;
import com.nhlstenden.student.vigmo.exception.IdProvidedInCreateRequestException;
import com.nhlstenden.student.vigmo.repositories.TestEntityRepository;
import com.nhlstenden.student.vigmo.models.TestEntity;
import com.nhlstenden.student.vigmo.services.LogService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import unit.java.com.nhlstenden.student.vigmo.dto.NoIdEntityDto;
import unit.java.com.nhlstenden.student.vigmo.dto.TestEntityDto;
import unit.java.com.nhlstenden.student.vigmo.services.custom.NoIdDtoService;
import unit.java.com.nhlstenden.student.vigmo.services.custom.TestEntityService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class ServiceTest {
    @Mock
    private TestEntityRepository repository;

    @Mock
    private MappingUtility mapper;

    @Mock
    private TestEntity testEntityMock;

    @Mock
    private TestEntityDto testEntityDtoMock;

    @Mock
    private LogService logServiceMock;

    @Mock
    private NoIdEntityDto noIdEntityDtoMock;

    @InjectMocks
    private TestEntityService testEntityService;

    @InjectMocks
    private NoIdDtoService noIdDtoService;

    @BeforeEach
    void setup() {
        openMocks(this);

        //mocks for the repo
        when(repository.save(any(TestEntity.class))).
                thenReturn(testEntityMock);
        when(repository.findById(1L)).
                thenReturn(Optional.of(testEntityMock));
        when(repository.findById(2L)).
                thenReturn(Optional.empty());

        //mocks for the mapper
        when(mapper.mapObject(any(TestEntity.class), eq(TestEntityDto.class))).
                thenReturn(testEntityDtoMock);
        when(mapper.mapObject(any(TestEntityDto.class), eq(TestEntity.class))).
                thenReturn(testEntityMock);
    }

    @Test
    void testGetEntities() {
        List<TestEntityDto> testEntityDtoList = new ArrayList<>();
        testEntityDtoList.add(testEntityDtoMock);
        when(mapper.mapList(anyList(), eq(TestEntityDto.class))).
                thenReturn(testEntityDtoList);

        List<TestEntityDto> entities = testEntityService.getList();

        assertThat(entities.isEmpty())
                .isFalse();

        verify(repository).
                findAll();
        verify(mapper).
                mapList(anyList(), eq(TestEntityDto.class));
    }

    @Test
    void testGetEntitiesWhenEmpty() {
        when(repository.findAll()).
                thenReturn(new ArrayList<>());
        when(mapper.mapList(anyList(), eq(TestEntityDto.class))).
                thenReturn(new ArrayList<>());
        List<TestEntityDto> entities = testEntityService.getList();

        assertThat(entities.isEmpty())
                .isTrue();
        verify(mapper).
                mapList(anyList(), eq(TestEntityDto.class));
        verify(repository).
                findAll();
    }

    @Test
    void testGetEntity() {
        TestEntityDto entity = testEntityService.get(1L);

        assertThat(entity)
                .isNotNull();
        //verify that the find and mapper functions were called
        verify(repository).
                findById(anyLong());
        verify(mapper).
                mapObject(any(), eq(TestEntityDto.class));
    }

    @Test
    void getNonExistentEntity() {
        assertThatThrownBy(() -> testEntityService.get(999L))
                .isInstanceOf(DataNotFoundException.class);
    }

    @Test
    void getWithNoIdField() {
        assertThatThrownBy(() -> noIdDtoService.create(noIdEntityDtoMock)).
                isInstanceOf(EntityIdRequirementNotMetException.class);
        verify(repository, Mockito.never()).save(any());
    }

    @Test
    void testCreateEntity() {
        when(testEntityDtoMock.getId()).
                thenReturn(null);
        Long id = testEntityService.create(testEntityDtoMock);

        assertThat(id)
                .isNotNull();
        //verify that the save and mapper functions were called
        verify(repository).
                save(testEntityMock);
        verify(mapper).
                mapObject(testEntityDtoMock, TestEntity.class);
    }

    @Test
    void testCreateEntityWithIdSet() {
        when(testEntityDtoMock.getId()).
                thenReturn(1L);
        assertThatThrownBy(() -> testEntityService.create(testEntityDtoMock)).
                isInstanceOf(IdProvidedInCreateRequestException.class);

        //verify that the save was not called
        verify(repository, Mockito.never()).save(testEntityMock);
    }

    @Test
    void testUpdateEntity() {
        testEntityService.update(testEntityDtoMock, 1L);

        //verify that the find, mapper and save functions were called
        verify(repository).
                findById(anyLong());
        verify(mapper).
                mapObject(testEntityDtoMock, TestEntity.class);
        verify(repository).
                save(testEntityMock);

        //assert that when you update a non-existing item, it throws a data not found exception.
        assertThatThrownBy(() -> testEntityService.update(testEntityDtoMock, 2L))
                .isInstanceOf(DataNotFoundException.class);
    }

    @Test
    void testDeleteEntity() {
        testEntityService.delete(1L);

        //verify that the delete method and the find method were called on the repository
        verify(repository).
                findById(anyLong());
        verify(repository).
                deleteById(anyLong());

        //assert that when you delete a non-existing item, it throws a data not found exception.
        assertThatThrownBy(() -> testEntityService.delete(2L))
                .isInstanceOf(DataNotFoundException.class);
    }

    @Test
    void createWithLog() {
        when(testEntityDtoMock.getId()).
                thenReturn(null);
        testEntityService.create(testEntityDtoMock, 1L, "user");

        //verify that the create log, save and mapper functions were called
        verify(logServiceMock).
                create(any(LogDto.class));
        verify(repository).
                save(testEntityMock);
        verify(mapper).
                mapObject(testEntityDtoMock, TestEntity.class);
    }

    @Test
    void updateWithLog() {
        testEntityService.update(testEntityDtoMock, 1L, 1L, "user");

        //verify that the create log, find, mapper and save functions were called
        verify(logServiceMock).
                create(any(LogDto.class));
        verify(repository).
                findById(anyLong());
        verify(mapper).
                mapObject(testEntityDtoMock, TestEntity.class);
        verify(repository).
                save(testEntityMock);
    }

    @Test
    void deleteWithLog() {
        testEntityService.delete(1L, 1L, "user");

        //verify that the create log method, delete method and the find method were called on the repository
        verify(logServiceMock).
                create(any(LogDto.class));
        verify(repository).
                findById(anyLong());
        verify(repository).
                deleteById(anyLong());

    }
}