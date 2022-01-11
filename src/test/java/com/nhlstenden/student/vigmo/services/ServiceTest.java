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
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class ServiceTest {
    @Mock
    private TestEntityRepository repository;

    @Mock
    private MappingUtility mapper;

    @InjectMocks
    private TestEntityService service;

    @BeforeEach
    void setup() {
        openMocks(this);

        //mocks for the repo
        when(repository.save(any(TestEntity.class))).thenReturn(TestEntity.builder().id(1L).build());
        when(repository.findById(1L)).thenReturn(Optional.of(TestEntity.builder().id(1L).build()));
        when(repository.findById(2L)).thenReturn(Optional.empty());


        //mocks for the mapper
        when(mapper.mapObject(any(TestEntity.class), eq(TestEntityDto.class))).thenReturn(TestEntityDto.builder().id(1L).build());
        when(mapper.mapObject(any(TestEntityDto.class), eq(TestEntity.class))).thenReturn(TestEntity.builder().id(1L).build());
    }

    @Test
    void testGetEntities() {
        List<TestEntityDto> testEntityDtoList = new ArrayList<>();
        testEntityDtoList.add(TestEntityDto.builder().id(1L).build());
        when(mapper.mapList(anyList(), eq(TestEntityDto.class))).thenReturn(testEntityDtoList);

        List<TestEntityDto> entities = service.getList();

        assertThat(entities.isEmpty())
                .isFalse();
    }

    @Test
    void testGetEntitiesWhenEmpty() {
        List<TestEntityDto> entities = service.getList();

        assertThat(entities.isEmpty())
                .isTrue();
    }

    @Test
    void testGetEntity() {
        TestEntityDto entity = service.get(1L);

        assertThat(entity)
                .isNotNull();
        assertThatThrownBy(() -> service.get(2L))
                .isInstanceOf(DataNotFoundException.class);
    }

    @Test
    void testCreateEntity() {
        TestEntityDto testEntityDto = new TestEntityDto();
        Long id = service.create(testEntityDto);

        assertThat(id)
                .isNotNull();
    }

    @Test
    void testUpdateEntity() {
        TestEntityDto testEntityDto = new TestEntityDto();
        service.update(testEntityDto, 1L);

        assertThatThrownBy(() -> service.update(testEntityDto, 2L))
                .isInstanceOf(DataNotFoundException.class);
    }

    @Test
    void testDeleteEntity() {
        service.delete(1L);

        assertThatThrownBy(() -> service.delete(2L))
                .isInstanceOf(DataNotFoundException.class);
    }
}