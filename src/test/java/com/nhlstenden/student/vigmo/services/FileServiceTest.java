package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.FileDto;
import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.models.File;
import com.nhlstenden.student.vigmo.repositories.FileRepository;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class FileServiceTest {
    @Mock
    private FileRepository repo;

    @Mock
    private MappingUtility mapper;

    @Mock
    private File fileMock;

    @Mock
    private FileDto fileDtoMockWithoutId;
    @Mock
    private FileDto fileDtoMockWithId;

    @InjectMocks
    FileService fileService;

    private final String randomKey = "6bee29d663436193f2ecf49869fca7f0c265c9e14b2dacdcace5af70cb59693d";

    @BeforeEach
    void setUp() {
        openMocks(this);

        //Mock mapper functions
        when(mapper.mapObject(any(File.class), eq(FileDto.class))).thenReturn(fileDtoMockWithId);
        when(mapper.mapObject(any(FileDto.class), eq(File.class))).thenReturn(fileMock);
        //Mock repository functions
        when(repo.findById(anyLong())).thenReturn(Optional.of(fileMock));
        when(repo.save(fileMock)).thenReturn(fileMock);
        //Make sure the id is not set in dto and is set in de entity
        when(fileDtoMockWithoutId.getId()).thenReturn(null);
        when(fileMock.getId()).thenReturn(1L);
        when(fileDtoMockWithId.getId()).thenReturn(1L);
        when(fileDtoMockWithId.getKey()).thenReturn(randomKey);
    }

    @Test
    void getList() {
        //Make the mapper return a list with a file with binary data
        List<FileDto> fileDtoList = new ArrayList<>();
        fileDtoList.add(fileDtoMockWithoutId);

        when(repo.findAll()).thenReturn(new ArrayList<>());
        when(mapper.mapList(anyList(), eq(FileDto.class))).
                thenReturn(fileDtoList);

        fileService.getList();

        verify(repo).findAll();
        verify(mapper).mapList(anyList(), eq(FileDto.class));
        //verify that the data has been removed
        verify(fileDtoMockWithoutId).setData("<removed-binary-data>");
    }

    @Test
    void get() {
        assertThat(fileService.get(1L)).isEqualTo(fileDtoMockWithId);

        verify(repo).findById(anyLong());
        verify(mapper).mapObject(any(File.class), eq(FileDto.class));
        //verify that the data has been removed
        verify(fileDtoMockWithId).setData("<removed-binary-data>");
    }

    @Test
    void create() {
        assertThat(fileService.create(fileDtoMockWithoutId)).isEqualTo(1L);
        //verify object was saved in the database and the key was set
        verify(repo).save(any(File.class));
        //capture set key and check that the size is equal to 64
        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        verify(fileDtoMockWithoutId).setKey(argument.capture());
        assertThat(argument.getValue()).hasSize(64);
    }

    @Test
    void update() {
        fileService.update(fileDtoMockWithoutId, 1L);
        //verify object was saved in the database and the key got set
        verify(repo).save(any(File.class));
        verify(fileDtoMockWithoutId).setKey(randomKey);

    }

    @Test
    void getExistingRawEntityByKey() {
        //repository will return a key when asked for one
        when(repo.findByKey(anyString())).thenReturn(Optional.of(fileMock));
        assertThat(fileService.getRawEntityByKey(randomKey)).isEqualTo(fileMock);
        //verify that the key got collected from the repository
        verify(repo).findByKey(anyString());
    }

    @Test
    void getNonExistingRawEntityByKey() {
        //repository will return no key when asked for one
        when(repo.findByKey(anyString())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> fileService.getRawEntityByKey(randomKey)).isInstanceOf(DataNotFoundException.class);
        //verify that the key got collected from the repository
        verify(repo).findByKey(anyString());
    }
}