package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.repositories.TestEntityRepository;
import com.nhlstenden.student.vigmo.models.TestEntity;
import com.nhlstenden.student.vigmo.dto.TestEntityDto;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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

    @InjectMocks
    private TestEntityService service;

    @BeforeEach
    void setup() {
        openMocks(this);

        //mocks for the repo
        when(repository.save(any(TestEntity.class))).thenReturn(testEntityMock);
        when(repository.findById(1L)).thenReturn(Optional.of(testEntityMock));
        when(repository.findById(2L)).thenReturn(Optional.empty());

        //mocks for the mapper
        when(mapper.mapObject(any(TestEntity.class), eq(TestEntityDto.class))).thenReturn(testEntityDtoMock);
        when(mapper.mapObject(any(TestEntityDto.class), eq(TestEntity.class))).thenReturn(testEntityMock);
    }

    @Test
    void testGetEntities() {
        //todo: add mockito verify checks
        List<TestEntityDto> testEntityDtoList = new ArrayList<>();
        testEntityDtoList.add(TestEntityDto.builder().id(1L).build());
        when(mapper.mapList(anyList(), eq(TestEntityDto.class))).thenReturn(testEntityDtoList);

        List<TestEntityDto> entities = service.getList();

        assertThat(entities.isEmpty())
                .isFalse();
    }

    @Test
    void testGetEntitiesWhenEmpty() {
        //todo: add mockito verify checks
        List<TestEntityDto> entities = service.getList();

        assertThat(entities.isEmpty())
                .isTrue();
    }

    @Test
    void testGetEntity() {
        //todo: add mockito verify checks
        TestEntityDto entity = service.get(1L);

        assertThat(entity)
                .isNotNull();
        assertThatThrownBy(() -> service.get(2L))
                .isInstanceOf(DataNotFoundException.class);
    }

    @Test
    void testCreateEntity() {
        //todo: add mockito verify checks
        TestEntityDto testEntityDto = new TestEntityDto();
        Long id = service.create(testEntityDto);

        assertThat(id)
                .isNotNull();
    }

    @Test
    void testUpdateEntity() {
        service.update(testEntityDtoMock, 1L);

        //verify that the find, mapper and save functions were called
        verify(repository).findById(anyLong());
        verify(mapper).mapObject(testEntityDtoMock, TestEntity.class);
        verify(repository).save(testEntityMock);

        //assert that when you update a non-existing item, it throws a data not found exception.
        assertThatThrownBy(() -> service.update(testEntityDtoMock, 2L))
                .isInstanceOf(DataNotFoundException.class);
    }

    @Test
    void testDeleteEntity() {
        service.delete(1L);

        //verify that the delete method and the find method were called on the repository
        verify(repository).findById(anyLong());
        verify(repository).deleteById(anyLong());

        //assert that when you delete a non-existing item, it throws a data not found exception.
        assertThatThrownBy(() -> service.delete(2L))
                .isInstanceOf(DataNotFoundException.class);
    }

    @Test
    void createWithLog() {
        //todo: write test
    }

    @Test
    void updateWithLog() {
        //todo: write test
    }

    @Test
    void deleteWithLog() {
        //todo: write test
    }
}